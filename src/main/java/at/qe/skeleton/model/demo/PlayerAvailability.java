package at.qe.skeleton.model.demo;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;

public class PlayerAvailability {

    Game game;
    User user;
    boolean available;

    public PlayerAvailability(User user, Game game) {
        this.game = game;
        this.user = user;
        this.available = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getAvailableString() {
        return available ? "yes" : "assigned";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
