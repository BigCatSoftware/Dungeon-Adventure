package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int myX;
    private final int myY;
    private final int myWidth;
    private final int myHeight;
    private final List<Room> myAdjacentRooms;
    private final List<Door> myDoors;

    public Room(final int theX, final int theY, final int theWidth, final int theHeight) {
        myX = theX;
        myY = theY;
        myWidth = theWidth;
        myHeight = theHeight;
        myAdjacentRooms = new ArrayList<>();
        myDoors = new ArrayList<>();
    }

    private void addAdjacentRoom(final Room theRoom) {
        if (!myAdjacentRooms.contains(theRoom)) {
            myAdjacentRooms.add(theRoom);
            theRoom.getAdjacentRooms().add(this);
        }
    }

    private Room findRoom(final List<Room> theRooms, final int theX, final int theY) {
        for (final Room room : theRooms) {
            if (theX >= room.getX() && theX < room.getX() + room.getWidth()
            && theY >= room.getY() && theY < room.getY() + room.getHeight()) {
                return room;
            }
        }
        return null;
    }

    public String toString() {
        return ("myX: " + myX + " myY: " + myY + " myWidth: " + myWidth + " myHeight: " + myHeight);
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    public List<Room> getAdjacentRooms() {
        return myAdjacentRooms;
    }

    public List<Door> getDoors() {
        return myDoors;
    }
}
