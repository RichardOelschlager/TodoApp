import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppUserTest {

    @Test
    void testAppUserCreation() {
        AppUser user = new AppUser("testuser", "password123", AppRole.ROLE_APP_USER);
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(AppRole.ROLE_APP_USER, user.getRole());
    }

    @Test
    void testInvalidUsername() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AppUser(null, "password123", AppRole.ROLE_APP_USER);
        });
        assertEquals("Username cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new AppUser("", "password123", AppRole.ROLE_APP_USER);
        });
        assertEquals("Username cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AppUser("testuser", null, AppRole.ROLE_APP_USER);
        });
        assertEquals("Password cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new AppUser("testuser", "", AppRole.ROLE_APP_USER);
        });
        assertEquals("Password cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AppUser("testuser", "password123", null);
        });
        assertEquals("Role cannot be null.", exception.getMessage());
    }

    @Test
    void testSetters() {
        AppUser user = new AppUser("olduser", "oldpass", AppRole.ROLE_APP_USER);
        user.setUsername("newuser");
        assertEquals("newuser", user.getUsername());
        user.setPassword("newpass");
        assertEquals("newpass", user.getPassword());
        user.setRole(AppRole.ROLE_APP_ADMIN);
        assertEquals(AppRole.ROLE_APP_ADMIN, user.getRole());
    }

    @Test
    void testSetInvalidUsername() {
        AppUser user = new AppUser("testuser", "password123", AppRole.ROLE_APP_USER);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername(null);
        });
        assertEquals("Username cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetInvalidPassword() {
        AppUser user = new AppUser("testuser", "password123", AppRole.ROLE_APP_USER);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(null);
        });
        assertEquals("Password cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetInvalidRole() {
        AppUser user = new AppUser("testuser", "password123", AppRole.ROLE_APP_USER);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setRole(null);
        });
        assertEquals("Role cannot be null.", exception.getMessage());
    }

    @Test
    void testToString() {
        AppUser user = new AppUser("testuser", "password123", AppRole.ROLE_APP_USER);
        String expectedString = "AppUser{username=\'testuser\', role=ROLE_APP_USER}";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        AppUser user1 = new AppUser("user1", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = new AppUser("user1", "pass2", AppRole.ROLE_APP_USER); // Same username and role, different password
        AppUser user3 = new AppUser("user2", "pass1", AppRole.ROLE_APP_USER);
        AppUser user4 = new AppUser("user1", "pass1", AppRole.ROLE_APP_ADMIN);

        assertEquals(user1, user2); // Should be equal based on username and role
        assertEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(user1, user3);
        assertNotEquals(user1, user4);
    }
}