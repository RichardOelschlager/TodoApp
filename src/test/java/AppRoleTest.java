
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppRoleTest {

    @Test
    void testAppRoleValues() {
        AppRole[] roles = AppRole.values();
        assertEquals(2, roles.length);
        assertEquals(AppRole.ROLE_APP_USER, roles[0]);
        assertEquals(AppRole.ROLE_APP_ADMIN, roles[1]);
    }

    @Test
    void testAppRoleValueOf() {
        assertEquals(AppRole.ROLE_APP_USER, AppRole.valueOf("ROLE_APP_USER"));
        assertEquals(AppRole.ROLE_APP_ADMIN, AppRole.valueOf("ROLE_APP_ADMIN"));
    }

    @Test
    void testAppRoleInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            AppRole.valueOf("INVALID_ROLE");
        });
    }
}
