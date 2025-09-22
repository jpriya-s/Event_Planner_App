package com.priya.eventplanner.dto;

/**
 * DTO representing user signup request details.
 * 
 * <p>This object is used as the request body for the signup API.</p>
 */
public class UserDetails {

    private String username;
    private String password;
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String securityQuestion;
    private String answer;
    private String notificationMethod; // SMS, EMAIL, BOTH, NONE

    // ----- Getters and Setters -----
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNotificationMethod() {
        return notificationMethod;
    }
    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }
}