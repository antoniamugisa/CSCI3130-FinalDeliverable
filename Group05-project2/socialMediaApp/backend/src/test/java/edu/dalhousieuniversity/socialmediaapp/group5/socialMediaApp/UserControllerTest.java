package edu.dalhousieuniversity.socialmediaapp.group5.socialMediaApp;

import edu.dalhousieuniversity.socialmediaapp.controller.UserController;
import edu.dalhousieuniversity.socialmediaapp.model.User;
import edu.dalhousieuniversity.socialmediaapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {
        User user = new User();
        when(userService.signUp(user)).thenReturn("User registered successfully");

        String response = userController.signUp(user);
        assertEquals("User registered successfully", response);

        verify(userService, times(1)).signUp(user);
    }

    @Test
    public void testSignUpFailure() {
        User user = new User();
        when(userService.signUp(user)).thenReturn("User registration failed");

        String response = userController.signUp(user);
        assertEquals("User registration failed", response);

        verify(userService, times(1)).signUp(user);
    }

    @Test
    public void testLogin() {
        User user = new User();
        when(userService.login(user)).thenReturn("Login successful");

        String response = userController.login(user);
        assertEquals("Login successful", response);

        verify(userService, times(1)).login(user);
    }

    @Test
    public void testLoginFailure() {
        User user = new User();
        when(userService.login(user)).thenReturn("Login failed");

        String response = userController.login(user);
        assertEquals("Login failed", response);

        verify(userService, times(1)).login(user);
    }

    @Test
    public void testForgotPassword() {
        User user = new User();
        when(userService.forgotPassword(user)).thenReturn("Password reset email sent");

        String response = userController.forgotPassword(user);
        assertEquals("Password reset email sent", response);

        verify(userService, times(1)).forgotPassword(user);
    }

    @Test
    public void testForgotPasswordFailure() {
        User user = new User();
        when(userService.forgotPassword(user)).thenReturn("Failed to send password reset email");

        String response = userController.forgotPassword(user);
        assertEquals("Failed to send password reset email", response);

        verify(userService, times(1)).forgotPassword(user);
    }

    @Test
    public void testGetUserProfile() {
        User user = new User();
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);

        User response = userController.getUserProfile("test@example.com");
        assertEquals(user, response);

        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testGetUserProfileNotFound() {
        when(userService.getUserByEmail("test@example.com")).thenReturn(null);

        User response = userController.getUserProfile("test@example.com");
        assertNull(response);

        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testUpdateUserProfile() {
        User user = new User();
        when(userService.updateUserProfile(user)).thenReturn("Profile updated successfully");

        String response = userController.updateUserProfile(user);
        assertEquals("Profile updated successfully", response);

        verify(userService, times(1)).updateUserProfile(user);
    }

    @Test
    public void testUpdateUserProfileFailure() {
        User user = new User();
        when(userService.updateUserProfile(user)).thenReturn("Profile update failed");

        String response = userController.updateUserProfile(user);
        assertEquals("Profile update failed", response);

        verify(userService, times(1)).updateUserProfile(user);
    }

    @Test
    public void testAddFriend() {
        when(userService.addFriend("user@example.com", "friend@example.com")).thenReturn("Friend added successfully");

        String response = userController.addFriend("user@example.com", "friend@example.com");
        assertEquals("Friend added successfully", response);

        verify(userService, times(1)).addFriend("user@example.com", "friend@example.com");
    }

    @Test
    public void testAddFriendFailure() {
        when(userService.addFriend("user@example.com", "friend@example.com")).thenReturn("Failed to add friend");

        String response = userController.addFriend("user@example.com", "friend@example.com");
        assertEquals("Failed to add friend", response);

        verify(userService, times(1)).addFriend("user@example.com", "friend@example.com");
    }

    @Test
    public void testDeleteFriend() {
        when(userService.deleteFriend("user@example.com", "friend@example.com")).thenReturn("Friend deleted successfully");

        String response = userController.deleteFriend("user@example.com", "friend@example.com");
        assertEquals("Friend deleted successfully", response);

        verify(userService, times(1)).deleteFriend("user@example.com", "friend@example.com");
    }

    @Test
    public void testDeleteFriendFailure() {
        when(userService.deleteFriend("user@example.com", "friend@example.com")).thenReturn("Failed to delete friend");

        String response = userController.deleteFriend("user@example.com", "friend@example.com");
        assertEquals("Failed to delete friend", response);

        verify(userService, times(1)).deleteFriend("user@example.com", "friend@example.com");
    }

    @Test
    public void testGetFriends() {
        List<User> friends = new ArrayList<>();
        when(userService.getFriends("user@example.com")).thenReturn(friends);

        List<User> response = userController.getFriends("user@example.com");
        assertEquals(friends, response);

        verify(userService, times(1)).getFriends("user@example.com");
    }

    @Test
    public void testGetFriendsFailure() {
        when(userService.getFriends("user@example.com")).thenReturn(null);

        List<User> response = userController.getFriends("user@example.com");
        assertNull(response);

        verify(userService, times(1)).getFriends("user@example.com");
    }

    @Test
    public void testGetUserProfileById() {
        User user = new User();
        when(userService.getUserById(1)).thenReturn(user);

        User response = userController.getUserProfileById(1);
        assertEquals(user, response);

        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testGetUserProfileByIdNotFound() {
        when(userService.getUserById(1)).thenReturn(null);

        User response = userController.getUserProfileById(1);
        assertNull(response);

        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        when(userService.addUser(user)).thenReturn("User added successfully");

        String response = userController.addUser(user);
        assertEquals("User added successfully", response);

        verify(userService, times(1)).addUser(user);
    }

    @Test
    public void testAddUserFailure() {
        User user = new User();
        when(userService.addUser(user)).thenReturn("Failed to add user");

        String response = userController.addUser(user);
        assertEquals("Failed to add user", response);

        verify(userService, times(1)).addUser(user);
    }

    @Test
    public void testRemoveUser() {
        when(userService.removeUser("test@example.com")).thenReturn("User removed successfully");

        String response = userController.removeUser("test@example.com");
        assertEquals("User removed successfully", response);

        verify(userService, times(1)).removeUser("test@example.com");
    }

    @Test
    public void testRemoveUserFailure() {
        when(userService.removeUser("test@example.com")).thenReturn("Failed to remove user");

        String response = userController.removeUser("test@example.com");
        assertEquals("Failed to remove user", response);

        verify(userService, times(1)).removeUser("test@example.com");
    }

    @Test
    public void testGetPendingRequests() {
        List<User> pendingRequests = new ArrayList<>();
        when(userService.getPendingRequests()).thenReturn(pendingRequests);

        List<User> response = userController.getPendingRequests();
        assertEquals(pendingRequests, response);

        verify(userService, times(1)).getPendingRequests();
    }

    @Test
    public void testGetPendingRequestsFailure() {
        when(userService.getPendingRequests()).thenReturn(null);

        List<User> response = userController.getPendingRequests();
        assertNull(response);

        verify(userService, times(1)).getPendingRequests();
    }

    @Test
    public void testApproveUser() {
        when(userService.approveUser("test@example.com")).thenReturn("User approved successfully");

        String response = userController.approveUser("test@example.com");
        assertEquals("User approved successfully", response);

        verify(userService, times(1)).approveUser("test@example.com");
    }

    @Test
    public void testApproveUserFailure() {
        when(userService.approveUser("test@example.com")).thenReturn("Failed to approve user");

        String response = userController.approveUser("test@example.com");
        assertEquals("Failed to approve user", response);

        verify(userService, times(1)).approveUser("test@example.com");
    }

    @Test
    public void testRejectUser() {
        when(userService.rejectUser("test@example.com")).thenReturn("User rejected successfully");

        String response = userController.rejectUser("test@example.com");
        assertEquals("User rejected successfully", response);

        verify(userService, times(1)).rejectUser("test@example.com");
    }

    @Test
    public void testRejectUserFailure() {
        when(userService.rejectUser("test@example.com")).thenReturn("Failed to reject user");

        String response = userController.rejectUser("test@example.com");
        assertEquals("Failed to reject user", response);

        verify(userService, times(1)).rejectUser("test@example.com");
    }

    @Test
    public void testSearchUserByEmail() {
        User user = new User();
        when(userService.findUserByEmail("test@example.com")).thenReturn(user);

        ResponseEntity<?> response = userController.searchUserByEmail("test@example.com");
        assertEquals(ResponseEntity.ok(user), response);

        verify(userService, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    public void testSearchUserByEmailNotFound() {
        when(userService.findUserByEmail("test@example.com")).thenReturn(null);

        ResponseEntity<?> response = userController.searchUserByEmail("test@example.com");
        assertEquals(ResponseEntity.status(404).body("User not found"), response);

        verify(userService, times(1)).findUserByEmail("test@example.com");
    }
}
