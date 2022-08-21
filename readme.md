### Swagger 문서화

```text
@PostMapping("/checkAppId")
@ApiOperation(value = "사용자별 인증 정보 CHECK", notes = "인증된 사용자인지 판단하는 API입니다.")
@ApiImplicitParams({
	@ApiImplicitParam(name = "appUserId", value = "사용자 식별 ID", required = true)
})
public MessageDTO checkAppId(@RequestBody UserRequestDTO userRequestDTO){
	......
	......
}



public static class MemberProfile {

    @JsonProperty("origin")
    @ApiModelProperty(example = "https://talk-plugin.kakao.com/otp/xxxxxx/profile?rest_api_key=yyyyyyyy")
    private String origin;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
```