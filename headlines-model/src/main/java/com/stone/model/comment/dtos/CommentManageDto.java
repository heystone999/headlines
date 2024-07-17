package com.stone.model.comment.dtos;

import com.stone.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class CommentManageDto extends PageRequestDto {
    /**
     * 文章id
     */
    private Long articleId;
}
