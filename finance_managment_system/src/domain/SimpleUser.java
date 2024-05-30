package domain;

public class SimpleUser extends User {
    @Override
    public boolean isVIP() {
        return false;
    }
}
