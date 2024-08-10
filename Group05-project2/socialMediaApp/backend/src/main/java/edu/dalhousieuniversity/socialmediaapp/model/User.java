package edu.dalhousieuniversity.socialmediaapp.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String interests;
    private String status;// "Busy, Away or Available"
    private String joinStatus;// " PENDING, APPROVED, REJECTED"
    private String role; // "employee" or "student"

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJoinStatus(String joinStatus) {this.joinStatus = joinStatus;}

    public String getJoinStatus() {return joinStatus;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public User() {}

    public User(int id, String username, String email, String password, String securityQuestion, String securityAnswer,String interests, String status,String joinStatus, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.interests = interests;
        this.status = status;
        this.role = role;
        this.joinStatus = joinStatus;
    }
}
