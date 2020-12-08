package com.dragon.scoreapp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateMemberDto {
    private MultipartFile avatarFile;
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,10}$", message = "用户名格式错误")
    private String username;

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String phone;

    @Length(min = 8, max = 20, message = "密码长度为 8-20")
    private String password;

    @Email
    private String email;

    private Integer gender;

}
