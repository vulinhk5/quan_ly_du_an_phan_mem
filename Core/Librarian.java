package Core;

public class Librarian extends User {
    public static final String LIBRARIAN = "librarian";

    public Librarian(String userId, String fullName, String username, String password, String email) {
        super(userId, fullName, username, password, email);
    }

    public String getRole() {
        return LIBRARIAN;
    }
}
