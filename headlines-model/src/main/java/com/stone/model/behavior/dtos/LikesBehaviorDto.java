package com.stone.model.behavior.dtos;

import lombok.Data;

@Data
public class LikesBehaviorDto {
    /**
     * 文章、动态、评论等ID
     */
    Long articleId;

    /**
     * like内容类型
     * 0 文章
     * 1 动态
     * 2 评论
     */
    Short type;

    /**
     * like操作方式
     * 0 点赞
     * 1 取消点赞
     */
    Short operation;
}
