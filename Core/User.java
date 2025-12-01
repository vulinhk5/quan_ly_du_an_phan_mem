package Core;

import Database.Database;
import static Utility.SHA.convertToSHA512;

public abstract class User {
    private String userId;
    private String fullName;
    private String username;
    private String passwordHash;
    private String email;

    public User(String userId, String fullName, String username, String passwordHash, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = convertToSHA512(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return "User{" +
            "userId='" + userId + '\'' +
            ", fullName='" + fullName + '\'' +
            ", username='" + username + '\'' +
            ", passwordHash='" + getPasswordHash() + '\'' +
            ", email='" + email + '\'' +
            ", role='" + getRole() + '\'' +
            '}';
    }
}
