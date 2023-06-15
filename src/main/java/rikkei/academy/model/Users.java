package rikkei.academy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName")
    @Size(min = 6)
    private String userName;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Size(min = 6)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> like;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Channel channel;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Videos> video;


    public Users(String userName, String email, String password, List<Comment> comment, List<Subscription> subscriptions, Set<Roles> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.comment = comment;
        this.subscriptions = subscriptions;
        this.roles = roles;
    }

    public Users(String userName, String email, String password, Set<Roles> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
