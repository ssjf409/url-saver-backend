package com.jdh.urlsaver.config.security;

import com.jdh.urlsaver.api.repository.user.UserRefreshTokenRepository;
import com.jdh.urlsaver.api.service.AuthTokenService;
import com.jdh.urlsaver.config.properties.AppProperties;
import com.jdh.urlsaver.config.properties.CorsProperties;
import com.jdh.urlsaver.model.entity.account.RoleType;
import com.jdh.urlsaver.oauth.exception.RestAuthenticationEntryPoint;
import com.jdh.urlsaver.oauth.filter.TokenAuthenticationFilter;
import com.jdh.urlsaver.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.jdh.urlsaver.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.jdh.urlsaver.oauth.handler.TokenAccessDeniedHandler;
import com.jdh.urlsaver.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.jdh.urlsaver.oauth.service.CustomOAuth2UserService;
import com.jdh.urlsaver.oauth.service.CustomUserDetailsService;
import com.jdh.urlsaver.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthTokenService authTokenService;

    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final String[] ALLOWED_PATH =
            { "/hello", "/favicon**", "**.js", "**.html", "**.css", "/api/**/auth/login" };
    private final String[] ALLOWED_PATH_ON_DEV = {
            "/hello", "/favicon**", "**.js", "**.html", "**.css", "/api/**/auth/login",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
    private final String OAUTH_REQUEST_BASE_URI = "/oauth2/authorization";
    private final String OAUTH_RESPONSE_BASE_URI = "/*/oauth2/code/*";

    @Value("$spring.profiles.active")
    private String profile;

    /*
     * UserDetailsService 설정
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable();

        http.formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new RestAuthenticationEntryPoint())
            .accessDeniedHandler(tokenAccessDeniedHandler);

        http.authorizeRequests()
//            .antMatchers(PRODUCTION.name().equalsIgnoreCase(profile)? ALLOWED_PATH : ALLOWED_PATH_ON_DEV)
            .antMatchers(ALLOWED_PATH_ON_DEV)
            .permitAll();

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
            .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
            .anyRequest().authenticated();

        http.oauth2Login()
            .authorizationEndpoint()
            .baseUri(OAUTH_REQUEST_BASE_URI)
            .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
            .and()
            .redirectionEndpoint()
            .baseUri(OAUTH_RESPONSE_BASE_URI)
            .and()
            .userInfoEndpoint()
            .userService(oAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler())
            .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * auth 매니저 설정
     * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, authTokenService, appProperties);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
