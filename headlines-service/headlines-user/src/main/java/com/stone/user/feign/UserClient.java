package com.stone.user.feign;

import com.stone.apis.user.IUserClient;
import com.stone.model.user.pojos.ApUser;
import com.stone.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClient implements IUserClient {
    @Autowired
    private ApUserService apUserService;

    @GetMapping("/api/v1/user/{id}")
    @Override
    public ApUser findUserById(@PathVariable("id") Integer id) {
        return apUserService.getById(id);
    }
}
