package com.mk.national_hospital_information.review.comment.presentation;

import com.mk.national_hospital_information.review.comment.application.interfaces.CommentLikeService;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
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
public class CommentLikeRestController {

    private final CommentLikeService commentLikeService;
    private final UserService userService;

    @PostMapping("/{commentId}/comment-like")
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
