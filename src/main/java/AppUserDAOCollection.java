import com.todoapp.AppUser;
import com.todoapp.data.AppUserDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class AppUserDAOCollection implements AppUserDAO {
    private Collection<AppUser> appUsers;

    public AppUserDAOCollection() {
        this.appUsers = new ArrayList<>();
    }

    @Override
    public AppUser persist(AppUser appUser) {
        if (appUser == null) {
            throw new IllegalArgumentException("AppUser cannot be null.");
        }
        if (findByUsername(appUser.getUsername()) != null) {
            throw new IllegalArgumentException("AppUser with this username already exists.");
        }
        appUsers.add(appUser);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        Optional<AppUser> foundUser = appUsers.stream()
                .filter(appUser -> appUser.getUsername().equalsIgnoreCase(username))
                .findFirst();
        return foundUser.orElse(null);
    }

    @Override
    public Collection<AppUser> findAll() {
        return new ArrayList<>(appUsers);
    }

    @Override
    public void remove(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        appUsers.removeIf(appUser -> appUser.getUsername().equalsIgnoreCase(username));
    }
}

