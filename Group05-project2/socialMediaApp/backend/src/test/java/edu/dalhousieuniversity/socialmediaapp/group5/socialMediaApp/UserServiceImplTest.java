package edu.dalhousieuniversity.socialmediaapp.group5.socialMediaApp;

import edu.dalhousieuniversity.socialmediaapp.model.Friendship;
import edu.dalhousieuniversity.socialmediaapp.model.User;
import edu.dalhousieuniversity.socialmediaapp.repository.FriendshipRepository;
import edu.dalhousieuniversity.socialmediaapp.repository.UserRepository;
import edu.dalhousieuniversity.socialmediaapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUpSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);
        when(userRepository.count()).thenReturn(0L);

        String result = userService.signUp(user);
        assertEquals("User registered successfully", result);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSignUpEmailAlreadyRegistered() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        String result = userService.signUp(user);
        assertEquals("Email is already registered", result);

        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void testSignUpInvalidEmail() {
        User user = new User();
        user.setEmail("test@gmail.com");

        String result = userService.signUp(user);
        assertEquals("Invalid email", result);

        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        User foundUser = new User();
        foundUser.setEmail("test@dal.ca");
        foundUser.setPassword("password");
        foundUser.setJoinStatus("APPROVED");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(foundUser);

        String result = userService.login(user);
        assertEquals("Login successful", result);
    }

    @Test
    public void testLoginInvalidCredentials() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.login(user);
        assertEquals("Invalid email or password", result);
    }

    @Test
    public void testLoginPendingApproval() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        User foundUser = new User();
        foundUser.setEmail("test@dal.ca");
        foundUser.setPassword("password");
        foundUser.setJoinStatus("PENDING");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(foundUser);

        String result = userService.login(user);
        assertEquals("Your account is pending approval", result);
    }

    @Test
    public void testForgotPasswordSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("newpassword");
        user.setSecurityAnswer("answer");

        User foundUser = new User();
        foundUser.setEmail("test@dal.ca");
        foundUser.setSecurityAnswer("answer");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(foundUser);

        String result = userService.forgotPassword(user);
        assertEquals("Password reset successful", result);
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    public void testForgotPasswordInvalidSecurityAnswer() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("newpassword");
        user.setSecurityAnswer("answer");

        User foundUser = new User();
        foundUser.setEmail("test@dal.ca");
        foundUser.setSecurityAnswer("wronganswer");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(foundUser);

        String result = userService.forgotPassword(user);
        assertEquals("Invalid email or security answer", result);
        verify(userRepository, times(0)).save(foundUser);
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        User result = userService.getUserByEmail("test@dal.ca");
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUserProfileSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setUsername("newUsername");

        User existingUser = new User();
        existingUser.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(existingUser);

        String result = userService.updateUserProfile(user);
        assertEquals("Profile updated successfully", result);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserProfileUserNotFound() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.updateUserProfile(user);
        assertEquals("User not found", result);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void testAddFriendSuccess() {
        User user = new User();
        user.setEmail("user@dal.ca");
        User friend = new User();
        friend.setEmail("friend@dal.ca");

        when(userRepository.findByEmail("user@dal.ca")).thenReturn(user);
        when(userRepository.findByEmail("friend@dal.ca")).thenReturn(friend);
        when(friendshipRepository.findByUserAndFriend(user, friend)).thenReturn(null);

        String result = userService.addFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("Friend added successfully", result);
        verify(friendshipRepository, times(1)).save(any(Friendship.class));
    }

    @Test
    public void testAddFriendAlreadyExists() {
        User user = new User();
        user.setEmail("user@dal.ca");
        User friend = new User();
        friend.setEmail("friend@dal.ca");

        when(userRepository.findByEmail("user@dal.ca")).thenReturn(user);
        when(userRepository.findByEmail("friend@dal.ca")).thenReturn(friend);
        when(friendshipRepository.findByUserAndFriend(user, friend)).thenReturn(new Friendship());

        String result = userService.addFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("Friendship already exists", result);
        verify(friendshipRepository, times(0)).save(any(Friendship.class));
    }

    @Test
    public void testAddFriendUserNotFound() {
        when(userRepository.findByEmail("user@dal.ca")).thenReturn(null);

        String result = userService.addFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("User or Friend not found", result);
        verify(friendshipRepository, times(0)).save(any(Friendship.class));
    }

    @Test
    public void testDeleteFriendSuccess() {
        User user = new User();
        user.setEmail("user@dal.ca");
        User friend = new User();
        friend.setEmail("friend@dal.ca");
        Friendship friendship = new Friendship();
        friendship.setUser(user);
        friendship.setFriend(friend);

        when(userRepository.findByEmail("user@dal.ca")).thenReturn(user);
        when(userRepository.findByEmail("friend@dal.ca")).thenReturn(friend);
        when(friendshipRepository.findByUserAndFriend(user, friend)).thenReturn(friendship);

        String result = userService.deleteFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("Friend deleted successfully", result);
        verify(friendshipRepository, times(1)).delete(friendship);
    }

    @Test
    public void testDeleteFriendNotExists() {
        User user = new User();
        user.setEmail("user@dal.ca");
        User friend = new User();
        friend.setEmail("friend@dal.ca");

        when(userRepository.findByEmail("user@dal.ca")).thenReturn(user);
        when(userRepository.findByEmail("friend@dal.ca")).thenReturn(friend);
        when(friendshipRepository.findByUserAndFriend(user, friend)).thenReturn(null);

        String result = userService.deleteFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("Friendship does not exist", result);
        verify(friendshipRepository, times(0)).delete(any(Friendship.class));
    }

    @Test
    public void testDeleteFriendUserNotFound() {
        when(userRepository.findByEmail("user@dal.ca")).thenReturn(null);

        String result = userService.deleteFriend("user@dal.ca", "friend@dal.ca");
        assertEquals("User or Friend not found", result);
        verify(friendshipRepository, times(0)).delete(any(Friendship.class));
    }

    @Test
    public void testGetFriendsSuccess() {
        User user = new User();
        user.setEmail("user@dal.ca");
        User friend = new User();
        friend.setEmail("friend@dal.ca");
        Friendship friendship = new Friendship();
        friendship.setUser(user);
        friendship.setFriend(friend);

        when(userRepository.findByEmail("user@dal.ca")).thenReturn(user);
        when(friendshipRepository.findByUser(user)).thenReturn(Collections.singletonList(friendship));

        List<User> result = userService.getFriends("user@dal.ca");
        assertEquals(1, result.size());
        assertEquals(friend, result.get(0));
    }

    @Test
    public void testGetFriendsUserNotFound() {
        when(userRepository.findByEmail("user@dal.ca")).thenReturn(null);

        List<User> result = userService.getFriends("user@dal.ca");
        assertEquals(null, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1);
        assertEquals(user, result);
    }

    @Test
    public void testAddUserSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.addUser(user);
        assertEquals("User added successfully", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddUserAlreadyExists() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        String result = userService.addUser(user);
        assertEquals("User already exists", result);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void testRemoveUserSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        String result = userService.removeUser("test@dal.ca");
        assertEquals("User removed successfully", result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testRemoveUserNotFound() {
        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.removeUser("test@dal.ca");
        assertEquals("User not found", result);
        verify(userRepository, times(0)).delete(any(User.class));
    }

    @Test
    public void testGetPendingRequests() {
        User user = new User();
        user.setJoinStatus("PENDING");

        when(userRepository.findByjoinStatus("PENDING")).thenReturn(Collections.singletonList(user));

        List<User> result = userService.getPendingRequests();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    public void testApproveUserSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setJoinStatus("PENDING");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        String result = userService.approveUser("test@dal.ca");
        assertEquals("User approved successfully", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testApproveUserNotFound() {
        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.approveUser("test@dal.ca");
        assertEquals("User not found", result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testRejectUserSuccess() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setJoinStatus("PENDING");

        when(userRepository.findByEmail("test@dal.ca")).thenReturn(user);

        String result = userService.rejectUser("test@dal.ca");
        assertEquals("User rejected successfully", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRejectUserNotFound() {
        when(userRepository.findByEmail("test@dal.ca")).thenReturn(null);

        String result = userService.rejectUser("test@dal.ca");
        assertEquals("User not found", result);
        verify(userRepository, times(0)).save(any(User.class));
    }
}
