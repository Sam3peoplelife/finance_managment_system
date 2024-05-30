package domain;

public class VIPUser extends User {
    @Override
    public boolean isVIP() {
        return true;
    }
}

