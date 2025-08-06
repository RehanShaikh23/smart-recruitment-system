package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JwtResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.LoginRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.RegisterRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.ConflictException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.UnauthorizedException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.UserRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public JwtResponse register(RegisterRequest r) {
        if (repo.findByEmail(r.getEmail()).isPresent()) throw new ConflictException("Email taken");
        User user = User.builder()
                .email(r.getEmail())
                .name(r.getName())
                .password(encoder.encode(r.getPassword()))
                .role(r.getRole())
                .build();
        repo.save(user);
        String token = jwt.generateToken(user.getEmail(), user.getRole());
        return JwtResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public JwtResponse login(LoginRequest r) {
        User u = repo.findByEmail(r.getEmail()).orElseThrow(() -> new UnauthorizedException("Bad credentials"));
        if (!encoder.matches(r.getPassword(), u.getPassword()))
            throw new UnauthorizedException("Bad credentials");
        return JwtResponse.builder()
                .token(jwt.generateToken(u.getEmail(), u.getRole()))
                .email(u.getEmail())
                .role(u.getRole())
                .build();
    }

}
