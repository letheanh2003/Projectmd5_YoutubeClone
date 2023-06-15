package rikkei.academy.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    private Long id;

    private Long videoId;

    private Long userId;
}
