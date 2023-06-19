package rikkei.academy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.CommentDTO;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Comment;
import rikkei.academy.model.Users;
import rikkei.academy.model.Videos;
import rikkei.academy.service.ICommentService;
import rikkei.academy.service.IUserService;
import rikkei.academy.service.IVideoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v4/comment")
public class CommentController {
    @Autowired
    private IVideoService videoService;
    @Autowired
    private ICommentService commentServices;
    @Autowired
    private IUserService userService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM')")
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = commentServices.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.CREATED);
        }
    }

    @PostMapping("/createComment")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        Long userId = commentDTO.getUserId();
        Users user = userService.findById(userId);

        Long videoId = commentDTO.getVideoId();
        Videos videos = videoService.findById(videoId);
        Comment comment = Comment
                .builder()
                .content(commentDTO.getContent())
                .create_at(new Date())
                .user(user)
                .video(videos)
                .build();
        commentServices.save(comment);
        return ResponseEntity.ok(new ResponseMessage("Successfully created new Comment"));
    }

    @PutMapping("/update/id")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO) {
        Long userId = commentDTO.getUserId();
        Users user = userService.findById(userId);
        Long videoId = commentDTO.getVideoId();
        Videos videos = videoService.findById(videoId);
        Comment comment = Comment
                .builder()
                .commentId(commentDTO.getCommentId())
                .content(commentDTO.getContent())
                .create_at(new Date())
                .user(user)
                .video(videos)
                .build();
        commentServices.save(comment);
        return ResponseEntity.ok(new ResponseMessage("Successfully update new Comment"));

    }

    @DeleteMapping("deleteComment/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        commentServices.deleteById(id);
        return ResponseEntity.ok(new ResponseMessage("Deleted comment successfully"));
    }

    @GetMapping("/findCommentByUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM')")
    public ResponseEntity<Iterable<Comment>> findCommentByUser(@PathVariable("id") Long id){
        Optional<Users> user = Optional.ofNullable(userService.findById(id));
        if (user.isPresent()) {
            List<Comment> comments = (List<Comment>) commentServices.findCommentByUser(user.get());
            if (comments.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}