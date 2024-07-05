package com.stone.model.article.dtos;

import com.stone.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class ArticleInfoDto {
    /**
     * 设备ID
     */
    @IdEncrypt
    Integer equipmentId;

    /**
     * 文章ID
     */
    @IdEncrypt
    Long articleId;

    /**
     * 作者ID
     */
    @IdEncrypt
    Integer authorId;
}
