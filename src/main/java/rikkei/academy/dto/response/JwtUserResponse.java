package rikkei.academy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtUserResponse {
    private String userName;
    private String email;
    private String token;
    private String type = "Bearer";
    private List<String> listRoles;

    public JwtUserResponse(String userName, String email, String token, List<String> listRoles) {
        this.userName = userName;
        this.email = email;
        this.token = token;
        this.listRoles = listRoles;
    }
}
