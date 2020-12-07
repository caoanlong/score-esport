package com.dragon.scoreapp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterMemberDto {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{1,10}$", message = "用户名格式错误")
    private String username;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度为 8-20")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 4, max = 4, message = "验证码格式错误")
    private String code;
}
