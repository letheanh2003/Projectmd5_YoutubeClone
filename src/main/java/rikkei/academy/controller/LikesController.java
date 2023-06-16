package rikkei.academy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.LikeDTO;
import rikkei.academy.model.Like;
import rikkei.academy.model.Videos;
import rikkei.academy.service.ILikesService;
import rikkei.academy.service.IUserService;
import rikkei.academy.service.IVideoService;

import java.util.Optional;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v4/like")
public class LikesController {
    @Autowired
    private ILikesService likesService;
    @Autowired
    private IVideoService videoService;
    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findLikeByVideoId(@PathVariable Long id) {
        Long likeCount = likesService.countLikesByVideoId(id);
        return ResponseEntity.ok(likeCount);
    }

    @PostMapping("/createLike")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<?> createLike(@RequestBody LikeDTO likeDTO) {
        Optional<Like> check = likesService.findLikesByUserIdAndVideosId(likeDTO.getUserId(), likeDTO.getVideoId());
        Videos video = videoService.findById(likeDTO.getVideoId());
        if (video == null) {
            return ResponseEntity.badRequest().body("video not found");
        }
        if (!check.isPresent()) {
            video.setLike(video.getLike() + 1);
            videoService.save(video);
            Like newLikes = new Like();
            newLikes.setUser(userService.findById(likeDTO.getUserId()));
            newLikes.setVideos(videoService.findById(likeDTO.getVideoId()));
            likesService.save(newLikes);
            return ResponseEntity.badRequest().body("Thank you for liking the video");
        } else {
            video.setLike(video.getLike() - 1);
            likesService.deleteByLikeId(check.get().getId());
            videoService.save(video);
            return ResponseEntity.badRequest().body("You unliked the video");
        }
    }
}
