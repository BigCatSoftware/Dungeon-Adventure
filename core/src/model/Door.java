package model;

public class Door {
    private final Position myPosition;
    private boolean isLocked;

    public Door(Position thePosition) {
        myPosition = thePosition;
        isLocked = false;
    }
}
