package Core;

public class Member extends User {
    public static final String MEMBER = "member";

    public Member(String userId, String fullName, String username, String password, String email) {
        super(userId, fullName, username, password, email);
    }

    public String getRole() {
        return MEMBER;
    }
}
