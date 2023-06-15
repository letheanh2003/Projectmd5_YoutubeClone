package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Channel;
import rikkei.academy.service.IChannelService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v4/test")
public class AdminController {
    @Autowired
    private IChannelService channelService;

    @PutMapping("changeStatus/{idChannel}/{sttCode}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('PM')")
    public ResponseEntity<?> changeStatusChannel(@PathVariable Long idChannel, @PathVariable int sttCode) {
        Channel channel = channelService.findById(idChannel);
        channel.setStatusCode(sttCode);
        channelService.save(channel);
        switch (sttCode) {
            case 0:
                break;
            case 1:
                return ResponseEntity.ok("First warning.");
            case 2:
                return ResponseEntity.ok("Second warning.");
            case 3:
                return ResponseEntity.ok("Final warning.");
            case 4:
                channel.setStatus(false);
                channelService.save(channel);
                break;
            default:
                return ResponseEntity.badRequest().body("Account locked.");
        }
        channelService.save(channel);
        return ResponseEntity.ok(channel);
    }


    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }

    @GetMapping("/pm")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM')")
    public ResponseEntity<String> pm() {
        return ResponseEntity.ok("pm");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','PM')")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("user");
    }
}
