package com.stone.user.controller.v1;

import com.stone.common.constants.UserConstants;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.user.dtos.AuthDto;
import com.stone.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController {
    @Autowired
    private ApUserRealnameService apUserRealnameService;

    @PostMapping("/list")
    public ResponseResult loadListByStatus(@RequestBody AuthDto dto) {
        return apUserRealnameService.loadListByStatus(dto);
    }

    @PostMapping("/authFail")
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatus(dto, UserConstants.FAIL_AUTH);
    }

    @PostMapping("/authPass")
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatus(dto, UserConstants.PASS_AUTH);
    }
}
