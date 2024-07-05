package com.stone.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.stone.behavior.service.ApReadBehaviorService;
import com.stone.common.constants.BehaviorConstants;
import com.stone.common.redis.CacheService;
import com.stone.model.behavior.dtos.ReadBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.user.pojos.ApUser;
import com.stone.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApReadBehaviorServiceImpl implements ApReadBehaviorService {
    @Autowired
    private CacheService cacheService;

    /**
     * 保存阅读行为
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto) {
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 更新阅读次数
        String readBehaviorJson = (String) cacheService.hGet(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        if (StringUtils.isNotBlank(readBehaviorJson)) {
            ReadBehaviorDto readBehaviorDto = JSON.parseObject(readBehaviorJson, ReadBehaviorDto.class);
            dto.setCount((short) (readBehaviorDto.getCount() + dto.getCount()));
        }

        // 保存当前key
        log.info("保存当前key:{} {} {}", dto.getArticleId(), user.getId(), dto);
        cacheService.hPut(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
