package com.stone.wemedia.controller.v1;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.dtos.SensitiveDto;
import com.stone.model.wemedia.pojos.WmSensitive;
import com.stone.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitiveController {
    @Autowired
    private WmSensitiveService wmSensitiveService;

    @PostMapping("/save")
    public ResponseResult insert(@RequestBody WmSensitive wmSensitive) {
        return wmSensitiveService.insert(wmSensitive);
    }

    @PostMapping("/list")
    public ResponseResult list(@RequestBody SensitiveDto dto) {
        return wmSensitiveService.list(dto);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmSensitive wmSensitive) {
        return wmSensitiveService.update(wmSensitive);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id) {
        return wmSensitiveService.delete(id);
    }

}
