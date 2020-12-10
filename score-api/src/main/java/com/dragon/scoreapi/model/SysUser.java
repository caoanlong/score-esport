package com.dragon.scoreapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysUser implements UserDetails, CredentialsContainer {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private String loginIp;
    private Date loginTime;
    private List<Integer> roleIds;
    private List<SysRole> roles;
    private Integer createUserId;
    private Integer updateUserId;
    private Date createTime;
    private Date updateTime;
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
