package com.stone.comment.controller.v1;

import com.stone.comment.service.CommentService;
import com.stone.model.comment.dtos.CommentDto;
import com.stone.model.comment.dtos.CommentLikeDto;
import com.stone.model.comment.dtos.CommentSaveDto;
import com.stone.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public ResponseResult saveComment(@RequestBody CommentSaveDto dto) {
        return commentService.saveComment(dto);
    }

    @PostMapping("/like")
    public ResponseResult like(@RequestBody CommentLikeDto dto) {
        return commentService.like(dto);
    }

    @PostMapping("/load")
    public ResponseResult findByArticleId(@RequestBody CommentDto dto) {
        return commentService.findByArticleId(dto);
    }
}
