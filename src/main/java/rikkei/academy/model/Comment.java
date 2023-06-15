package rikkei.academy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;
    private Date create_at;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "videos_id")
    private Videos video;

}
