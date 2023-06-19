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
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> createLike(@RequestBody LikeDTO likeDTO) {
        Videos videos = videoService.findById(likeDTO.getVideoId());
        Optional<Like> likesOptional = likesService.findLikesByUserIdAndVideosId(likeDTO.getUserId(), likeDTO.getVideoId());
        if (likesOptional.isPresent()) {
            // có bày tỏ cảm xúc r
            Like likes = likesOptional.get();
            if (likes.getStatus()) {
                // đang like
                likesService.deleteById(likes.getId());
                videos.setLike(videos.getLike() - 1);
                videoService.save(videos);
            } else {
                // đang dislike
                likes.setStatus(true);
                likesService.save(likes);
                videos.setLike(videos.getLike() + 1);
                videos.setDisLikes(videos.getDisLikes() - 1);
                videoService.save(videos);
            }
        } else {
            Like likes = Like.builder().user(userService.findById(likeDTO.getUserId()))
                    .videos(videos)
                    .status(true)
                    .build();
            likesService.save(likes);
            videos.setLike(videos.getLike() + 1);
            videoService.save(videos);
        }
        return ResponseEntity.badRequest().body("Thank you for liking the video");
    }
    @PostMapping("/createDislike")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> createDislike(@RequestBody LikeDTO likeDTO) {
        Videos videos = videoService.findById(likeDTO.getVideoId());
        Optional<Like> likesOptional = likesService.findLikesByUserIdAndVideosId(likeDTO.getUserId(), likeDTO.getVideoId());
        if (likesOptional.isPresent()) {
            // có bày tỏ cảm xúc r
            Like likes = likesOptional.get();
            if (!likes.getStatus()) {
                // đang like
                likesService.deleteById(likes.getId());
                videos.setDisLikes(videos.getDisLikes() - 1);
                videoService.save(videos);
            } else {
                // đang dislike
                likes.setStatus(false);
                likesService.save(likes);
                videos.setLike(videos.getLike() - 1);
                videos.setDisLikes(videos.getDisLikes() + 1);
                videoService.save(videos);
            }
        } else {
            Like likes = Like.builder().user(userService.findById(likeDTO.getUserId()))
                    .videos(videos)
                    .status(false)
                    .build();
            likesService.save(likes);
            videos.setDisLikes(videos.getDisLikes() + 1);
            videoService.save(videos);
        }
        return ResponseEntity.badRequest().body("You dislike the video");
    }

}
