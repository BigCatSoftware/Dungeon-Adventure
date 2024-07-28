package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in a dungeon.
 * A room has coordinates, dimensions, adjacent rooms, and doors.
 * This class is used in the dungeon generation process to define
 * the layout and connections between rooms.
 *
 * @author Tiger Schueler
 * @version 27JUL24
 */
public class Room {
    private final int myX;
    private final int myY;
    private final int myWidth;
    private final int myHeight;
    private final List<Room> myAdjacentRooms;
    private final List<Door> myDoors;

    /**
     * Constructs a room with the specified coordinates and dimensions.
     *
     * @param theX the x-coordinate of the room
     * @param theY the y-coordinate of the room
     * @param theWidth the width of the room
     * @param theHeight the height of the room
     */
    public Room(final int theX, final int theY, final int theWidth, final int theHeight) {
        myX = theX;
        myY = theY;
        myWidth = theWidth;
        myHeight = theHeight;
        myAdjacentRooms = new ArrayList<>();
        myDoors = new ArrayList<>();
    }

    /**
     * Adds an adjacent room to this room.
     * This method is private and is used to establish bidirectional connections
     * between rooms.
     *
     * @param theRoom the room to add as adjacent
     */
    private void addAdjacentRoom(final Room theRoom) {
        if (!myAdjacentRooms.contains(theRoom)) {
            myAdjacentRooms.add(theRoom);
            theRoom.getAdjacentRooms().add(this);
        }
    }

    /**
     * Finds a room in the given list of rooms that contains the specified coordinates.
     * This method is private and used internally for determining room locations.
     *
     * @param theRooms the list of rooms to search in
     * @param theX the x-coordinate to find
     * @param theY the y-coordinate to find
     * @return the room containing the specified coordinates, or null if no such room is found
     */
    private Room findRoom(final List<Room> theRooms, final int theX, final int theY) {
        for (final Room room : theRooms) {
            if (theX >= room.getX() && theX < room.getX() + room.getWidth()
            && theY >= room.getY() && theY < room.getY() + room.getHeight()) {
                return room;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the room.
     * The string includes the coordinates and dimensions of the room.
     *
     * @return a string representing the room's coordinates and dimensions
     */
    @Override
    public String toString() {
        return ("myX: " + myX + " myY: " + myY + " myWidth: " + myWidth + " myHeight: " + myHeight);
    }

    /**
     * Gets the x-coordinate of the room.
     *
     * @return the x-coordinate of the room
     */
    public int getX() {
        return myX;
    }

    /**
     * Gets the y-coordinate of the room.
     *
     * @return the y-coordinate of the room
     */
    public int getY() {
        return myY;
    }

    /**
     * Gets the width of the room.
     *
     * @return the width of the room
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the height of the room.
     *
     * @return the height of the room
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the list of rooms adjacent to this room.
     *
     * @return the list of adjacent rooms
     */
    public List<Room> getAdjacentRooms() {
        return myAdjacentRooms;
    }

    /**
     * Gets the list of doors in this room.
     *
     * @return the list of doors
     */
    public List<Door> getDoors() {
        return myDoors;
    }
}
