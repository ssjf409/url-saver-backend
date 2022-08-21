package com.jdh.urlsaver.api.controller;

import com.jdh.urlsaver.api.application.NodeApplication;
import com.jdh.urlsaver.api.controller.dto.NodeDeleteRequest;
import com.jdh.urlsaver.api.controller.dto.NodeFetchResponse;
import com.jdh.urlsaver.api.controller.dto.NodeUpsertRequest;
import com.jdh.urlsaver.configuration.security.AuthUtils;
import com.jdh.urlsaver.model.entity.node.Node;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/nodes")
@RestController
public class NodeController {

    private final NodeApplication nodeApplication;

    @ApiOperation(value = "fetch nodes", notes = "노드 정보를 가져오는 api 입니다.")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "appUserId", value = "사용자 식별 ID", required = true)
//    })
    @GetMapping
    public ResponseEntity<NodeFetchResponse> fetch(@RequestParam Long parentNodeId,
                                                   @RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "20") Integer pageSize) {
        return ResponseEntity.ok(nodeApplication.fetch(parentNodeId, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Node> create(Authentication authentication,
                                       @Valid @RequestBody NodeUpsertRequest request) {
        Long userId = AuthUtils.getUserId(authentication);
        request.setUserId(userId);
        return ResponseEntity.ok(nodeApplication.create(request));
    }

    @PutMapping
    public ResponseEntity<Node> update(Authentication authentication,
                                       @Valid @RequestBody NodeUpsertRequest request) {
        Long userId = AuthUtils.getUserId(authentication);
        request.setUserId(userId);
        return ResponseEntity.ok(nodeApplication.update(request));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@Valid @RequestBody NodeDeleteRequest request) {
//        Long userId = AuthUtils.getUserId(authentication);
//        request.setUserId(userId);
        nodeApplication.delete(request);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
