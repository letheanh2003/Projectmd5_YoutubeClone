package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.ChangePasswordRequest;
import rikkei.academy.dto.request.FormLogin;
import rikkei.academy.dto.request.FormRegister;
import rikkei.academy.dto.response.JwtUserResponse;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.Roles;
import rikkei.academy.model.Users;
import rikkei.academy.security.jwt.JwtTokenProvider;
import rikkei.academy.security.userPrincipal.CustomUserDetails;
import rikkei.academy.service.serviceIpm.RoleService;
import rikkei.academy.service.serviceIpm.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v4/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody FormLogin formLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(formLogin.getUserName(), formLogin.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // lấy ra đối tượng đang làm việc hiện tại
            CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
            // tạo token bằng jwt
            String token = tokenProvider.createToken(customUserDetail);
            List<String> roles = customUserDetail.getAuthorities().stream().map(
                    role -> role.getAuthority()
            ).collect(Collectors.toList());
            JwtUserResponse response = new JwtUserResponse(customUserDetail.getUsername(), customUserDetail.getEmail(), token, roles);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage("Invalid username or password"));
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> register(@RequestBody FormRegister formRegister) {
        try {
            if (userService.existsByUserName(formRegister.getUserName())) {
                return ResponseEntity.ok(new ResponseMessage("Username is existed"));
            }
            if (userService.existsByEmail(formRegister.getEmail())) {
                return ResponseEntity.ok(new ResponseMessage("Email is existed"));
            }

            Set<String> roles = formRegister.getRoles();
            Set<Roles> listRoles = new HashSet<>();
            if (roles == null || roles.isEmpty()) {
                listRoles.add(roleService.findByRoleName(RoleName.USER));
            } else {
                roles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            listRoles.add(roleService.findByRoleName(RoleName.ADMIN));
                        case "pm":
                            listRoles.add(roleService.findByRoleName(RoleName.PM));
                        case "user":
                            listRoles.add(roleService.findByRoleName(RoleName.USER));
                    }
                });
            }
            Users user = new Users(formRegister.getUserName(), formRegister.getEmail(), encoder.encode(formRegister.getPassword()), listRoles);
            userService.save(user);
            return ResponseEntity.ok(new ResponseMessage("Register success"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    @PutMapping("/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            // Lấy thông tin người dùng hiện tại
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            // Kiểm tra mật khẩu hiện tại của người dùng
            if (!encoder.matches(changePasswordRequest.getCurrentPassword(), customUserDetails.getPassword())) {
                return ResponseEntity.badRequest().body(new ResponseMessage("Current password is incorrect"));
            }
            // Cập nhật mật khẩu mới cho người dùng
            Users user = userService.findByUserName(customUserDetails.getUsername());
            user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            userService.save(user);

            return ResponseEntity.ok(new ResponseMessage("Password changed successfully"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
