package rikkei.academy.security.userPrincipal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rikkei.academy.model.Roles;
import rikkei.academy.model.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;

    Collection<? extends GrantedAuthority> listRoles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.listRoles;
    }
    // custom lại đối tượng Users thành CustomUserDetail
    public static CustomUserDetails build(Users user) {
        List<GrantedAuthority> authorityList  = user.getRoles().stream().map(
                role->new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetails(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorityList);
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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

}
