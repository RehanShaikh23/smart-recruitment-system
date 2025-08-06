package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;


import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    private String token;
    private String email;
    private User.Role role;

}
