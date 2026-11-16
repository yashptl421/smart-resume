package com.smartresume.controller;
import com.smartresume.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class MeController {
    public record UserProfile(Long id, String fullName, String email) {}
    @GetMapping("/me")
    public UserProfile me(@AuthenticationPrincipal AppUser user) {
        return new UserProfile(user.getId(), user.getFullName(), user.getEmail());
    }
}
