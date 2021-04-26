package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.User;

public class PlayerAvailability {

    User user;
    boolean available;

    public PlayerAvailability(User user) {
        this.user = user;
        this.available = true;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isAvailable() {
        return available;
    }

    public String getAvailableString() {
        return available ? "yes" : "assigned";
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
