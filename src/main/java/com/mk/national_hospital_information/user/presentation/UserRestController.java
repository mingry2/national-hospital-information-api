package com.mk.national_hospital_information.user.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.presentation.dto.CheckUsernameResponseDto;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinRequestDto;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinResponseDto;
import com.mk.national_hospital_information.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Response<UserJoinResponseDto>> join(@RequestBody UserJoinRequestDto dto) {
        User joinedUser = userService.join(dto);
        UserJoinResponseDto userJoinResponseDto = new UserJoinResponseDto(joinedUser.getId(), joinedUser.getUsername());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(userJoinResponseDto));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Response<CheckUsernameResponseDto>> checkUsername(@PathVariable String username) {
        CheckUsernameResponseDto checkUsernameResponseDto = new CheckUsernameResponseDto(userService.isDuplicated(username));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(checkUsernameResponseDto));
    }
}
