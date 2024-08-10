package edu.dalhousieuniversity.socialmediaapp.service.impl;

import edu.dalhousieuniversity.socialmediaapp.model.Friendship;
import edu.dalhousieuniversity.socialmediaapp.model.User;
import edu.dalhousieuniversity.socialmediaapp.repository.FriendshipRepository;
import edu.dalhousieuniversity.socialmediaapp.repository.UserRepository;

import edu.dalhousieuniversity.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public String signUp(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Email is already registered";
        }

        if (!user.getEmail().endsWith("@dal.ca")) {
            return "Invalid email";
        }

        user.setJoinStatus(userRepository.count() == 0 ? "APPROVED" : "PENDING"); // Auto-approve the first user
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {

            String joinStatus = foundUser.getJoinStatus();
            if ("PENDING".equals(joinStatus)) {
                return "Your account is pending approval";
            }
            else if ("REJECTED".equals(joinStatus)) {
                return "Your account has been rejected";
            }
            return "Login successful";
        }
        return "Invalid email or password";
    }

    @Override
    public String forgotPassword(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null && foundUser.getSecurityAnswer().equals(user.getSecurityAnswer())) {
            foundUser.setPassword(user.getPassword()); // Update password directly
            userRepository.save(foundUser);
            return "Password reset successful";
        }
        return "Invalid email or security answer";
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String updateUserProfile(User user) {

        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setInterests(user.getInterests());
            existingUser.setStatus(user.getStatus());
            existingUser.setRole(user.getRole());
            userRepository.save(existingUser);
            return "Profile updated successfully";
        }
        logger.error("User not found for email: " + user.getEmail());
        return "User not found";
    }

    @Override
    public String addFriend(String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail);
        User friend = userRepository.findByEmail(friendEmail);
        if (user != null && friend != null) {
            if (friendshipRepository.findByUserAndFriend(user, friend) == null) {
                Friendship friendship = new Friendship();
                friendship.setUser(user);
                friendship.setFriend(friend);
                friendshipRepository.save(friendship);
                return "Friend added successfully";
            }
            return "Friendship already exists";
        }
        return "User or Friend not found";
    }

    @Override
    public String deleteFriend(String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail);
        User friend = userRepository.findByEmail(friendEmail);
        if (user != null && friend != null) {
            Friendship friendship = friendshipRepository.findByUserAndFriend(user, friend);
            if (friendship != null) {
                friendshipRepository.delete(friendship);
                return "Friend deleted successfully";
            }
            return "Friendship does not exist";
        }
        return "User or Friend not found";
    }
    
    @Override
    public List<User> getFriends(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            List<Friendship> friendships = friendshipRepository.findByUser(user);
            return friendships.stream().map(Friendship::getFriend).collect(Collectors.toList());
        }
        return null;
    }
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public String addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "User already exists";
        }
        userRepository.save(user);
        return "User added successfully";
    }

    @Override
    public String removeUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {

            List<Friendship> friendshipsAsUser = friendshipRepository.findByUser(user);
            List<Friendship> friendshipsAsFriend = friendshipRepository.findByFriend(user);

            friendshipRepository.deleteAll(friendshipsAsUser);
            friendshipRepository.deleteAll(friendshipsAsFriend);

            userRepository.delete(user);
            return "User removed successfully";
        }
        return "User not found";
    }

    @Override
    public List<User> getPendingRequests() {
        return userRepository.findByjoinStatus("PENDING");
    }

    @Override
    public String approveUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setJoinStatus("APPROVED");
            userRepository.save(user);
            return "User approved successfully";

        }
        return "User not found";
    }

    @Override
    public String rejectUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setJoinStatus("REJECTED");
            userRepository.save(user);
            return "User rejected successfully";
        }
        return "User not found";
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}