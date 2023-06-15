package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormRegister {
    private String userName;
    private String password;
    private String email;
    private Set<String> roles;
}
