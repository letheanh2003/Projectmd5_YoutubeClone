package rikkei.academy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.ChannelRequest;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Users;
import rikkei.academy.service.IChannelService;
import rikkei.academy.service.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v4/channel")
public class ChannelController {
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM')")
    public List<Channel> findAll() {
        List<Channel> list_chanel = channelService.findAll();
        return list_chanel;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> createChannel(@RequestBody ChannelRequest channelRequest) {
        Long user_id = channelRequest.getUser();
        Users users = userService.findById(user_id);
        if (channelService.existsChannelByUser(users)) {
            return ResponseEntity.badRequest().body(new ResponseMessage("User already has a channel"));
        }

        Channel channel = Channel.builder()
                .chanel_name(channelRequest.getChanel_name())
                .user(users)
                .create_at(new Date())
                .build();
        channel.setStatusCode(0);
        channel.setStatus(true);
        channelService.save(channel);
        return ResponseEntity.ok(new ResponseMessage("Successfully created new channel"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> updateChannel(@RequestBody ChannelRequest channelRequest) {
        Long user_id = channelRequest.getUser();
        Users users = userService.findById(user_id);
        Channel channel = Channel
                .builder()
                .id(channelRequest.getChannel_id())
                .chanel_name(channelRequest.getChanel_name())
                .create_at(channelRequest.getCreate_at())
                .statusCode(channelRequest.getStatusCode())
                .status(channelRequest.isStatus())
                .user(users)
                .create_at(new Date())
                .build();
        channelService.save(channel);
        return ResponseEntity.ok(new ResponseMessage("Successfully edited information"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM') || hasAuthority('USER')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        channelService.deleteById(id);
        return ResponseEntity.ok(new ResponseMessage("Delete successfully"));
    }


}
