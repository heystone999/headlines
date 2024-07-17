package com.stone.wemedia.controller.v1;

import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.comment.dtos.CommentConfigDto;
import com.stone.model.comment.dtos.CommentLikeDto;
import com.stone.model.comment.dtos.CommentManageDto;
import com.stone.model.comment.dtos.CommentRepaySaveDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.wemedia.service.CommentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/manage")
public class CommentManageController {
    @Autowired
    private CommentManageService commentManageService;

    @PostMapping("/find_news_comments")
    public PageResponseResult findNewsComments(@RequestBody ArticleCommentDto dto) {
        return commentManageService.findNewsComments(dto);
    }

    @PostMapping("/update_comment_status")
    public ResponseResult updateCommentStatus(@RequestBody CommentConfigDto dto) {
        return commentManageService.updateCommentStatus(dto);
    }

    @PostMapping("/list")
    public ResponseResult list(@RequestBody CommentManageDto dto) {
        return commentManageService.list(dto);
    }

    @PostMapping("/comment_repay")
    public ResponseResult saveCommentRepay(@RequestBody CommentRepaySaveDto dto) {
        return commentManageService.saveCommentRepay(dto);
    }

    @PostMapping("/like")
    public ResponseResult like(@RequestBody CommentLikeDto dto) {
        return commentManageService.like(dto);
    }

    @DeleteMapping("/del_comment/{commentId}")
    public ResponseResult delComment(@PathVariable("commentId") String commentId) {
        return commentManageService.delComment(commentId);
    }
}
