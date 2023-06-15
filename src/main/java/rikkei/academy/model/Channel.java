package rikkei.academy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;
    @Column(name = "chanel_name")
    private String chanel_name;
    @Column(name = "create_at")
    private Date create_at;
    private int statusCode;
    @Column(name = "status")
    private boolean status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        switch (this.statusCode) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public boolean setStatus() {
        switch (this.statusCode) {
            case 0:
                return false;
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }

    }
}

