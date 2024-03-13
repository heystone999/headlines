package com.stone.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.article.dtos.ArticleHomeDto;
import com.stone.model.article.pojos.ApArticle;
import com.stone.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * 加载文章列表
     *
     * @param dto
     * @param type
     * @return
     */

    public ResponseResult load(ArticleHomeDto dto, Short type);
}
