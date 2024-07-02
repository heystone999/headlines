package com.stone.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.wemedia.dtos.ChannelDto;
import com.stone.model.wemedia.pojos.WmChannel;
import com.stone.wemedia.mapper.WmChannelMapper;
import com.stone.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 保存
     *
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult insert(WmChannel wmChannel) {
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmChannel channel = getOne(Wrappers.<WmChannel>lambdaQuery().eq(WmChannel::getName, wmChannel.getName()));
        if (channel != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "频道已存在");
        }

        wmChannel.setCreatedTime(new Date());
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findByNameAndPage(ChannelDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())) {
            lambdaQueryWrapper.like(WmChannel::getName, dto.getName());
        }
        page = page(page, lambdaQueryWrapper);

        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }
}