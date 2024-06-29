package com.stone.search.service;

import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.search.dtos.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchService {
    /**
     * es文章分页检索
     *
     * @return
     */
    ResponseResult search(UserSearchDto dto) throws IOException;
}
