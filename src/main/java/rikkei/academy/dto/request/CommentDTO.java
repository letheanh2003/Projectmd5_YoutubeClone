package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentId;
    private String content;
    private Date create_at;
    private Long userId;
    private Long videoId;
}
