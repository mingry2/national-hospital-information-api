package com.mk.national_hospital_information.review.comment.presentation;

import com.mk.national_hospital_information.review.comment.application.interfaces.CommentLikeService;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Tag(name = "📑 6. CommentLike Controller", description = "리뷰의 댓글 좋아요")
public class CommentLikeRestController {

    private final CommentLikeService commentLikeService;
    private final UserService userService;

    @PostMapping("/{commentId}/comment-like")
    @Operation(summary = "✔ 댓글 좋아요", description = "📢 댓글을 '좋아요'합니다.(한번 클릭, 좋아요 / 두번 클릭, 좋아요 취소)")
    public ResponseEntity<String> toggleCommentLike(@PathVariable Long commentId) {
        Long loginId = getUserId();

        String result = commentLikeService.toggleCommentLike(loginId, commentId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
