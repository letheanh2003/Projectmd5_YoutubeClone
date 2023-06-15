package rikkei.academy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.LikeDTO;
import rikkei.academy.dto.request.VideoRequest;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Like;
import rikkei.academy.model.Users;
import rikkei.academy.model.Videos;
import rikkei.academy.service.IChannelService;
import rikkei.academy.service.ILikesService;
import rikkei.academy.service.IUserService;
import rikkei.academy.service.IVideoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v4/videos")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VideoController {
    @Autowired
    private ILikesService likesService;
    @Autowired
    private IVideoService videoService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IUserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM')")
    public List<Videos> findAll() {
        List<Videos> list_video = videoService.findAll();
        return list_video;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChannel(@RequestBody VideoRequest videoRequest) {
        Long channel_id = videoRequest.getChannel();
        Long user_id = videoRequest.getChannel();
        Channel channel = channelService.findById(channel_id);
        Users users = userService.findById(user_id);
        Videos videos = Videos.builder()
                .title(videoRequest.getTitle())
                .url_videos(videoRequest.getUrl_videos())
                .description(videoRequest.getDescription())
                .channel(channel)
                .users(users)
                .create_at(new Date())
                .build();
        int status = channel.getStatusCode();
        if (status != 4) {
            switch (status) {
                case 1:
                    videos.setStatus(true);
                    videoService.save(videos);
                    return ResponseEntity.ok("First warning.Create new video successfully");
                case 2:
                    videos.setStatus(true);
                    videoService.save(videos);
                    return ResponseEntity.ok("Second warning.Create new video successfully");
                case 3:
                    videos.setStatus(true);
                    videoService.save(videos);
                    return ResponseEntity.ok("Final warning.Create new video successfully");
                case 4:
                    videos.setStatus(false);
                    return ResponseEntity.badRequest().body("Account locked.");
            }
        }

        videos.setStatus(true);
        videoService.save(videos);
        return ResponseEntity.ok("Create new video successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateChannel(@RequestBody VideoRequest videoRequest) {
        Long channel_id = videoRequest.getChannel();
        Channel channel = channelService.findById(channel_id);
        Videos videos = Videos.builder()
                .id(videoRequest.getVideo_id())
                .title(videoRequest.getTitle())
                .url_videos(videoRequest.getUrl_videos())
                .description(videoRequest.getDescription())
                .status(videoRequest.isStatus())
                .channel(channel)
                .create_at(new Date())
                .build();
        videoService.save(videos);
        return ResponseEntity.ok(new ResponseMessage("Update successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        videoService.deleteById(id);
        return ResponseEntity.ok(new ResponseMessage("Delete successfully"));
    }


    @PutMapping("/views/{id}")
    public ResponseEntity<?> views(@PathVariable Long id) {
        Videos video = videoService.findById(id);
        if (video != null) {
            video.setViews(video.getViews() + 1);
            videoService.save(video);
            return ResponseEntity.ok(new ResponseMessage("Video increased by 1 view"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("video not found");
    }
}
