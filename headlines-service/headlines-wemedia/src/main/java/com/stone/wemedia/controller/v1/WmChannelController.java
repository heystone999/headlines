package com.stone.wemedia.controller.v1;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.dtos.ChannelDto;
import com.stone.model.wemedia.pojos.WmChannel;
import com.stone.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {
    @Autowired
    private WmChannelService wmChannelService;

    @GetMapping("/channels")
    public ResponseResult findAll() {
        return wmChannelService.findAll();
    }

    @PostMapping("/save")
    public ResponseResult insert(@RequestBody WmChannel adChannel) {
        return wmChannelService.insert(adChannel);
    }

    @PostMapping("/list")
    public ResponseResult insert(@RequestBody ChannelDto dto) {
        return wmChannelService.findByNameAndPage(dto);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmChannel adChannel) {
        return wmChannelService.update(adChannel);
    }
}
