package com.mk.national_hospital_information.user.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.presentation.dto.CheckUsernameResponseDto;
import com.mk.national_hospital_information.user.presentation.dto.UserRequestDto;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinResponseDto;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserRoleUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "📑 1. User Controller", description = "유저 등록, 로그인, username 중복 여부 체크, role(권한) 변경")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "✔ 유저 등록", description = "📢 username, password 정보를 이용하여 사용자를 등록합니다.")
    public ResponseEntity<Response<UserJoinResponseDto>> join(@RequestBody UserRequestDto userJoinDto) {
        User joinedUser = userService.join(userJoinDto);
        UserJoinResponseDto userJoinResponseDto = new UserJoinResponseDto(joinedUser.getId(), joinedUser.getUsername());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(userJoinResponseDto));
    }

    @PostMapping("/login")
    @Operation(summary = "✔ 로그인", description = "📢 이 API는 실제로 동작하지 않습니다. Security Filter(LoginFilter)에서 처리됩니다.(헤더에 jwt가 발급됩니다.)")
    public String login(@RequestBody UserRequestDto loginRequest) {
        return "이 API는 실제로 동작하지 않습니다. Security Filter(LoginFilter)에서 처리됩니다.";
    }

    @GetMapping("/check-username/{username}")
    @Operation(summary = "✔ username 중복 여부 체크", description = "📢 username으로 중복 여부를 확인합니다.")
    public ResponseEntity<Response<CheckUsernameResponseDto>> checkUsername(@PathVariable String username) {
        CheckUsernameResponseDto checkUsernameResponseDto = new CheckUsernameResponseDto(userService.isDuplicated(username));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(checkUsernameResponseDto));
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "✔ role(권한) 변경", description = "📢 유저의 role(권한)을 변경합니다.(ADMIN(관리자)만 가능합니다.)")
    public ResponseEntity<Response<String>> updateUserRole(@PathVariable long userId, @RequestBody UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
        String result = userService.updateUserRole(userId, userRoleUpdateRequestDto.userRole());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(result));

    }
}
