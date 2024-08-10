package edu.dalhousieuniversity.socialmediaapp.controller;

import edu.dalhousieuniversity.socialmediaapp.model.User;
import edu.dalhousieuniversity.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        return userService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody User user) {
        return userService.forgotPassword(user);
    }

    @GetMapping("/user-profile")
    public User getUserProfile(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/update-profile")
    public String updateUserProfile(@RequestBody User user) {
        return userService.updateUserProfile(user);
    }
    @PostMapping("/add-friend")
    public String addFriend(@RequestParam String userEmail, @RequestParam String friendEmail) {
        return userService.addFriend(userEmail, friendEmail);
    }

    @PostMapping("/delete-friend")
    public String deleteFriend(@RequestParam String userEmail, @RequestParam String friendEmail) {
        return userService.deleteFriend(userEmail, friendEmail);
    }

    @GetMapping("/friends")
    public List<User> getFriends(@RequestParam String userEmail) {
        return userService.getFriends(userEmail);
    }

    @GetMapping("/user-profile/{id}")
    public User getUserProfileById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/remove-user")
    public String removeUser(@RequestParam String email) {
        return userService.removeUser(email);
    }

    @GetMapping("/pending-requests")
    public List<User> getPendingRequests() {
        return userService.getPendingRequests();
    }

    @PostMapping("/approve-user")
    public String approveUser(@RequestParam String email) {
        return userService.approveUser(email);
    }

    @PostMapping("/reject-user")
    public String rejectUser(@RequestParam String email) {
        return userService.rejectUser(email);
    }

    @GetMapping("/search-user")
    public ResponseEntity<?> searchUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}