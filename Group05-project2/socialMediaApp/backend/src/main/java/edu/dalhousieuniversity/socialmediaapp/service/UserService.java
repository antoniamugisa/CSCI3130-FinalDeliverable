package edu.dalhousieuniversity.socialmediaapp.service;

import edu.dalhousieuniversity.socialmediaapp.model.User;
import edu.dalhousieuniversity.socialmediaapp.model.Friendship;

import java.util.List;

public interface UserService {
    String signUp(User user);
    String login(User user);
    String forgotPassword(User user);
    User getUserByEmail(String email);
    String updateUserProfile(User user);

    String addFriend(String userEmail, String friendEmail);
    String deleteFriend(String userEmail, String friendEmail);
    List<User> getFriends(String userEmail);
    User getUserById(int id);

    String addUser(User user);
    String removeUser(String email);

    List<User> getPendingRequests();
    String approveUser(String email);
    String rejectUser(String email);
    public User findUserByEmail(String email) ;

}
