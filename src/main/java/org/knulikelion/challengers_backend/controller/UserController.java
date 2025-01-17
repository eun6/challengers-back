package org.knulikelion.challengers_backend.controller;

import org.knulikelion.challengers_backend.data.dto.request.UserRequestDto;
import org.knulikelion.challengers_backend.data.dto.response.ResultResponseDto;
import org.knulikelion.challengers_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/get")
    public Object getUserById(Long id){
        Object result = userService.getUserById(id);
        return result;
    }
    @DeleteMapping("/remove")
    public ResultResponseDto removeUserById(Long id){
        return userService.removeUser(id);
    }
    @PutMapping("/update")
    public ResultResponseDto updateUser(@RequestBody UserRequestDto userRequestDto, Long userId) throws Exception {
        return userService.updateUser(userId,userRequestDto);
    }
}
