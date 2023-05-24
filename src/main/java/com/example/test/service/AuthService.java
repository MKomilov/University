package com.example.test.service;

import com.example.test.entity.Student;
import com.example.test.entity.enums.RoleEnum;
import com.example.test.exception.CustomRequestException;
import com.example.test.filter.JwtProvider;
import com.example.test.payload.LoginDTO;
import com.example.test.payload.UserDTO;
import com.example.test.repository.RoleRepository;
import com.example.test.repository.StudentRepository;
import com.example.test.service.email.EmailService;
import com.example.test.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    final StudentRepository userRepository;
    final JwtProvider jwtProvider;
    final PasswordEncoder passwordEncoder;
    final UserDetailsService userDetailsService;
    final EmailService emailService;
    final RoleRepository roleRepository;

    public HttpEntity<?> signUp(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new CustomRequestException("Email is already in use, Please register with another email");
        }
        Student user = new Student();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAuthorities(Collections.singleton(roleRepository.findAuthorityByRole(RoleEnum.USER)));
//        user.setCode(String.valueOf(new Random().nextInt(9999, 100000)));
        userRepository.save(user);
//        String emailPath = "userId=" + user.getId() + "&code=" + user.getCode();
//
//        emailService.sendSimpleMail(
//                EmailDetails.builder()
//                        .msgBody("http://localhost:8080/api/auth/check?" + emailPath)
//                        .subject("Account confirmation email")
//                        .recipient(userDTO.getEmail())
//                        .build()
//        );
        return ResponseEntity.ok("Saved");
    }


    public HttpEntity<?> signIn(LoginDTO loginDTO) {
        String token = "";
        Student user = (Student) userDetailsService.loadUserByUsername(loginDTO.getEmail());
//        if (!user.isEnabled())
//            throw new CustomRequestException("please confirm your account");
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            token = jwtProvider.generateJwt(user.getEmail());
            return ResponseEntity.ok(Utils.BEARER_AUTH + token);
        }
        throw new RuntimeException("Password not matched");
    }


//    public HttpEntity<?> confirmAccount(UUID userId, String code) {
//        Student user = userRepository.findById(userId).orElseThrow(() -> new CustomRequestException("wrong info"));
//        if (Objects.equals(user.getCode(), code)){
//            user.setEnabled(true);
//            userRepository.save(user);
//        }
//        return user.isEnabled()?ResponseEntity.ok("user enabled"):
//                ResponseEntity.status(HttpStatus.CONFLICT).body("something went wrong");
//    }
}
