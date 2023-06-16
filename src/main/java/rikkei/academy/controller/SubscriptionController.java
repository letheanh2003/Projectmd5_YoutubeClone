package rikkei.academy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.SubscriptionDTO;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Like;
import rikkei.academy.model.Subscription;
import rikkei.academy.model.Videos;
import rikkei.academy.service.IChannelService;
import rikkei.academy.service.ISubscriptionService;
import rikkei.academy.service.IUserService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v4/Subscription")
public class SubscriptionController {
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISubscriptionService subscriptionService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<?> findSubscriptionByChannelId(@PathVariable Long id) {
        Long SubCount = subscriptionService.countSubscriptionByChannelId(id);
        return ResponseEntity.ok(SubCount);
    }

    @PostMapping("/createSub")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<?> createSud(@RequestBody SubscriptionDTO subscriptionDTO) {
        Optional<Subscription> check = subscriptionService.findSubscriptionByUserIdAndChannelId(subscriptionDTO.getUserId(), subscriptionDTO.getChannelId());
        Channel channel = channelService.findById(subscriptionDTO.getChannelId());
        if (channel == null) {
            return ResponseEntity.badRequest().body("Channel not found");
        }
        if (!check.isPresent()) {
            channel.setSubscription(channel.getSubscription() + 1);
            channelService.save(channel);
            Subscription newSub = new Subscription();
            newSub.setUser(userService.findById(subscriptionDTO.getUserId()));
            newSub.setChannel(channelService.findById(subscriptionDTO.getChannelId()));
            subscriptionService.save(newSub);
            return ResponseEntity.badRequest().body("Thank you for subscribing to the channel.");

        } else {
            channel.setSubscription(channel.getSubscription() - 1);
            subscriptionService.deleteBySubId(check.get().getId());
            channelService.save(channel);
            return ResponseEntity.badRequest().body("You have unsubscribed from the channel! ");
        }

    }


//    @DeleteMapping("/deleteSub/{id}")
//    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
//    public void deleteSub(@PathVariable Long id) {
//        subscriptionService.deleteById(id);
//    }
}
