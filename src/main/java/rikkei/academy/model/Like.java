package rikkei.academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Boolean status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Videos videos;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
