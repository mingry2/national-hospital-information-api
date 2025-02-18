package com.mk.national_hospital_information.review.comment.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.review.comment.application.interfaces.CommentService;
import com.mk.national_hospital_information.review.comment.domain.Comment;
import com.mk.national_hospital_information.review.comment.presentation.dto.CommentFindResponseDto;
import com.mk.national_hospital_information.review.comment.presentation.dto.CommentRequestDto;
import com.mk.national_hospital_information.review.comment.presentation.dto.CommentResponseDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/{reviewId}/comment")
    public ResponseEntity<Response<CommentResponseDto>> addComment(@PathVariable Long reviewId, @RequestBody CommentRequestDto commentAddRequestDto) {
        Long loginId = getUserId();

        Comment savedComment = commentService.save(reviewId, loginId, commentAddRequestDto);

        CommentResponseDto commentAddResponseDto = new CommentResponseDto(
            savedComment.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(commentAddResponseDto));
    }

    @PutMapping("/{reviewId}/comment/{commentId}")
    public ResponseEntity<Response<CommentResponseDto>> updateComment(@PathVariable Long reviewId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentUpdateRequestDto) {
        Long loginId = getUserId();

        Comment updateComment = commentService.update(reviewId, commentId, loginId,
            commentUpdateRequestDto);

        CommentResponseDto commentUpdateResponseDto = new CommentResponseDto(
            updateComment.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(commentUpdateResponseDto));
    }

    @PatchMapping("/{reviewId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        Long loginId = getUserId();

        String result = commentService.delete(reviewId, commentId, loginId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/{reviewId}/comment/{commentId}")
    public ResponseEntity<Response<CommentFindResponseDto>> getComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        Comment findComment = commentService.findComment(reviewId, commentId);

        CommentFindResponseDto commentFindResponseDto = new CommentFindResponseDto(
            findComment.getContent(), findComment.getReviewId(), findComment.getId(),
            findComment.getUserId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(commentFindResponseDto));
    }

    @GetMapping("/comments")
    public Page<Comment> findAllComment(Pageable pageable) {

        return commentService.findAll(pageable);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }


}
