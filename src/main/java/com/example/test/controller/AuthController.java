package com.example.test.controller;

import com.example.test.payload.LoginDTO;
import com.example.test.payload.UserDTO;
import com.example.test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;

    /**
     * For student sign up to program
     * @param userDTO
     * userDTO contains user's email, name and password
     * @return
     * return response from authService
     */
    @PostMapping("/signUp")
    HttpEntity<?> signUp(@RequestBody @Valid UserDTO userDTO){
        return authService.signUp(userDTO);
    }

    /**
     * For student sign in to program
     * @param loginDTO
     * loginDTO contains only user's email and password
     * @return
     * return Bearer token for that user when everything is successful
     */
    @PostMapping("/signIn")
    HttpEntity<?> signIn(@RequestBody LoginDTO loginDTO){
        return authService.signIn(loginDTO);
    }
    /**
     * This method created for checking user's email
     * if email is real, in signing up confirmation email is sent to user's email
     * when user confirms, user's enabled field in database is updated to true
     * then sign in works and returns bearer token to user
     */

//    @GetMapping("/check")
//    HttpEntity<?> confirmAccount(@RequestParam("userId") UUID userId,
//                                 @RequestParam("code") String  code){
//        return authService.confirmAccount(userId, code);
//    }

}

