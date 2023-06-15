package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelRequest {
    private Long channel_id;
    private String chanel_name;
    private Date create_at;
    private int statusCode;
    private boolean status;
    private Long user;
}
