package com.stone.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.article.pojos.ApArticleConfig;
import com.stone.model.comment.dtos.CommentConfigDto;
import com.stone.model.common.dtos.ResponseResult;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * 修改文章
     *
     * @param map
     */
    void updateByMap(Map map);

    /**
     * 修改文章评论状态
     *
     * @param dto
     * @return
     */
    ResponseResult updateCommentStatus(CommentConfigDto dto);

}
