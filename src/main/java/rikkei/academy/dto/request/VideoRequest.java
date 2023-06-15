package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {
    private Long video_id;
    private String title;
    private String description;
    private String url_videos;
    private int like;
    private int disLikes;
    private boolean status;
    private int views;
    private Date create_at;
    private Long channel;
    private Long users;
}
