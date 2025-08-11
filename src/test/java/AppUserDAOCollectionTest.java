
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserDAOCollectionTest {

    private AppUserDAOCollection appUserDAO;

    @BeforeEach
    void setUp() {
        appUserDAO = new AppUserDAOCollection();
    }

    @Test
    void testPersistAndFindAll() {
        AppUser user1 = new AppUser("user1", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = new AppUser("user2", "pass2", AppRole.ROLE_APP_ADMIN);

        appUserDAO.persist(user1);
        appUserDAO.persist(user2);

        Collection<AppUser> allUsers = appUserDAO.findAll();
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    void testPersistDuplicateUsernameThrowsException() {
        AppUser user1 = new AppUser("user1", "pass1", AppRole.ROLE_APP_USER);
        appUserDAO.persist(user1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserDAO.persist(new AppUser("user1", "pass3", AppRole.ROLE_APP_ADMIN));
        });
        assertEquals("AppUser with this username already exists.", exception.getMessage());
    }

    @Test
    void testFindByUsername() {
        AppUser user1 = new AppUser("user1", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = new AppUser("user2", "pass2", AppRole.ROLE_APP_ADMIN);

        appUserDAO.persist(user1);
        appUserDAO.persist(user2);

        assertEquals(user1, appUserDAO.findByUsername("user1"));
        assertEquals(user2, appUserDAO.findByUsername("user2"));
        assertNull(appUserDAO.findByUsername("nonexistent"));
    }

    @Test
    void testRemove() {
        AppUser user1 = new AppUser("user1", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = new AppUser("user2", "pass2", AppRole.ROLE_APP_ADMIN);

        appUserDAO.persist(user1);
        appUserDAO.persist(user2);

        assertEquals(2, appUserDAO.findAll().size());

        appUserDAO.remove("user1");
        assertEquals(1, appUserDAO.findAll().size());
        assertNull(appUserDAO.findByUsername("user1"));
        assertNotNull(appUserDAO.findByUsername("user2"));

        appUserDAO.remove("nonexistent"); // Should not throw exception
        assertEquals(1, appUserDAO.findAll().size());
    }

    @Test
    void testPersistNullAppUserThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserDAO.persist(null);
        });
        assertEquals("AppUser cannot be null.", exception.getMessage());
    }

    @Test
    void testFindByUsernameNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserDAO.findByUsername(null);
        });
        assertEquals("Username cannot be null.", exception.getMessage());
    }
}