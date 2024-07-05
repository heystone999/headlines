package com.stone.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.stone.behavior.service.ApUnlikesBehaviorService;
import com.stone.common.constants.BehaviorConstants;
import com.stone.common.redis.CacheService;
import com.stone.model.behavior.dtos.UnLikesBehaviorDto;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.user.pojos.ApUser;
import com.stone.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApUnlikesBehaviorServiceImpl implements ApUnlikesBehaviorService {
    @Autowired
    private CacheService cacheService;

    /**
     * 不喜欢
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult unLike(UnLikesBehaviorDto dto) {
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (dto.getType() == 0) {
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut(BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        } else {
            log.info("删除当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hDelete(BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
