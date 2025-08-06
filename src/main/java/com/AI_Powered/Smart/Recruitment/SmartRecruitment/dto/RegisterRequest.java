package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email
    private String email;
    @NotBlank
    private String name;
    @Size(min=6) private String password;
    @NotNull
    private User.Role role;


}
