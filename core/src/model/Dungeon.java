package model;

import java.util.*;

/**
 * Class representing a BSP dungeon generator.
 * This class generates a random dungeon layout using a binary space partitioning algorithm.
 * The dungeon consists of rooms and corridors connected by doors.
 *
 * @author Tiger Schueler
 * @version 1.2
 */
public class Dungeon {

    private final int MAP_SIZE = 50;
    private final Tile[][] MAP;
    private static final int MIN_ROOM_SIZE = 10;
    private final Node ROOT;
    private final List<Node> rooms;
    private final List<Room> roomList;
    private int myTotalRooms = 0;

    public Dungeon() {
        MAP = new Tile[MAP_SIZE][MAP_SIZE];
        ROOT = new Node(1, 1, MAP_SIZE - 1, MAP_SIZE - 1);
        rooms = new ArrayList<>();
        roomList = new ArrayList<>();
        generateDungeon();
    }

    /**
     * Initializes the ROOT to have null children when regenerating a dungeon.
     */
    private void initializeRoot() {
        ROOT.myLeftChild = null;
        ROOT.myRightChild = null;
    }

    /**
     * Initializes the dungeon map with walls.
     */
    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                MAP[i][j] = Tile.WALL;
            }
        }
    }

    /**
     * Recursively splits the map using BSP algorithm.
     *
     * @param theNode The current node to split.
     */
    private static void splitMap(final Node theNode) {
        if (theNode == null) {
            return;
        }

        if (theNode.canSplit()) {
            final boolean splitDirection = theNode.splitDirection();
            if (splitDirection) {
                theNode.splitHorizontally();
            } else {
                theNode.splitVertically();
            }
        }
        splitMap(theNode.myLeftChild);
        splitMap(theNode.myRightChild);
    }

    /**
     * Creates rooms at the leaf nodes of the BSP tree.
     *
     * @param theNode The current node to check for room creation.
     */
    private void createRooms(final Node theNode) {
        if (theNode == null) {
            return;
        }

        if (theNode.myLeftChild == null && theNode.myRightChild == null) {
            myTotalRooms++;
            rooms.add(theNode);
            roomList.add(new Room(theNode.getX(), theNode.getY(),
                    theNode.getWidth(), theNode.getHeight()));
            placeFloorTiles(theNode);
        } else {
            createRooms(theNode.myLeftChild);
            createRooms(theNode.myRightChild);
        }
    }

    /**
     * Creates doorways connecting adjacent doors on the North, South, East, and West walls.
     */
    private void createDoors() {
        for (final Room room : roomList) {
            createEastDoor(room);
            createSouthDoor(room);
        }
    }

    /**
     * Creates a door on the east wall of a given room.
     *
     * @param theRoom The node representing the room to create an east door in.
     */
    private void createEastDoor(final Room theRoom) {
        final int roomX = theRoom.getX();
        final int roomY = theRoom.getY();
        final int width = theRoom.getWidth();
        final int height = theRoom.getHeight();
        final int rand = new Random().nextInt(height / 2);
        for (int i = rand; i <= height; i++) {
            if (roomY + i < MAP_SIZE - 1
                    && roomX + width < MAP_SIZE - 1
                    && MAP[roomY + i][roomX + width - 2] == Tile.FLOOR
                    && MAP[roomY + i][roomX + width - 1] == Tile.WALL
                    && MAP[roomY + i][roomX + width] == Tile.FLOOR) {
                MAP[roomY + i][roomX + width - 1] = Tile.DOOR;
                theRoom.getDoors().add(new Door(new Position(roomX + width - 1,
                        roomY + i)));
                break;
            }
        }
    }

    /**
     * Creates a door on the south wall of a given room.
     *
     * @param theRoom The node representing the room to create a south door in.
     */
    private void createSouthDoor(final Room theRoom) {
        final int roomX = theRoom.getX();
        final int roomY = theRoom.getY();
        final int width = theRoom.getWidth();
        final int height = theRoom.getHeight();
        final int rand = new Random().nextInt(width / 2);
        for (int i = rand; i <= width; i++) {
            if (roomX + i < MAP_SIZE - 1
                    && roomY + height < MAP_SIZE - 1
                    && MAP[roomY + height - 2][roomX + i] == Tile.FLOOR
                    && MAP[roomY + height - 1][roomX + i] == Tile.WALL
                    && MAP[roomY + height][roomX + i] == Tile.FLOOR) {
                MAP[roomY + height - 1][roomX + i] = Tile.DOOR;
                theRoom.getDoors().add(new Door(new Position(roomX + i,
                        roomY + height - 1)));
                break;
            }
        }
    }

    /**
     * Creates a room within the node.
     *
     * @param theNode The node to create a room in.
     */
    private void placeFloorTiles(final Node theNode) {
        for (int i = theNode.getY(); i < theNode.getY() + theNode.getHeight() - 1; i++) {
            for (int j = theNode.getX(); j < theNode.getX() + theNode.getWidth() - 1; j++) {
                if (i > 0 && j > 0) {
                    MAP[i][j] = Tile.FLOOR;
                }
            }
        }
    }

    /**
     * Prints the dungeon map to the console.
     */
    public void printMap() {
        StringBuilder mapBuilder = new StringBuilder();
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (MAP[i][j] == Tile.WALL) {
                    mapBuilder.append('#');
                } else if (MAP[i][j] == Tile.FLOOR){
                    mapBuilder.append('.');
                } else {
                    mapBuilder.append('D');
                }
                mapBuilder.append(" ");
            }
            mapBuilder.append("\n");
        }
        System.out.println(mapBuilder);
    }

    /**
     * Gets the generated map of the dungeon.
     *
     * @return A 2D array representing the dungeon map.
     */
    public Tile[][] getMap() {
        return MAP;
    }

    /**
     * Generates the dungeon layout, including rooms and doors.
     * Regenerates the dungeon if the total number of rooms is less than 15.
     */
    private void generateDungeon() {
        rooms.clear();
        roomList.clear();
        initializeRoot();
        initializeMap();
        splitMap(ROOT);
        createRooms(ROOT);
        createDoors();
        if (myTotalRooms < 15) {
            myTotalRooms = 0;
            generateDungeon();
        } else {
            System.out.println("Dungeon Rooms:");
            for (Room room : roomList) {
                System.out.println(room);
            }
        }


    }

    /**
     * Class representing a node in the BSP tree.
     */
    private static class Node {
        private final int myX;
        private final int myY;
        private final int myWidth;
        private final int myHeight;
        private Node myLeftChild;
        private Node myRightChild;

        /**
         * Constructor for creating a Node.
         *
         * @param theX       The x-coordinate of the node.
         * @param theY       The y-coordinate of the node.
         * @param theWidth   The width of the node.
         * @param theHeight  The height of the node.
         */
        Node(final int theX, final int theY, final int theWidth, final int theHeight) {
            myX = theX;
            myY = theY;
            myWidth = theWidth;
            myHeight = theHeight;
            myLeftChild = null;
            myRightChild = null;
        }

        /**
         * Gets the x-coordinate of the node.
         *
         * @return The x-coordinate.
         */
        public int getX() {
            return myX;
        }

        /**
         * Gets the y-coordinate of the node.
         *
         * @return The y-coordinate.
         */
        public int getY() {
            return myY;
        }

        /**
         * Gets the width of the node.
         *
         * @return The width.
         */
        public int getWidth() {
            return myWidth;
        }

        /**
         * Gets the height of the node.
         *
         * @return The height.
         */
        public int getHeight() {
            return myHeight;
        }

        /**
         * Checks if the node can be split.
         *
         * @return True if the node can be split, false otherwise.
         */
        private boolean canSplit() {
            // ignore this red squiggly for readability purposes
            if (myLeftChild == null && myRightChild == null
                    && (getWidth() >= MIN_ROOM_SIZE || getHeight() >= MIN_ROOM_SIZE)
                    && (getWidth() > 1 && getHeight() > 1)
                    && getWidth() * getHeight() > MIN_ROOM_SIZE) {
                return true;
            }
            return false;
        }

        /**
         * Determines the split direction (horizontal or vertical) for the node.
         *
         * @return True if split horizontally, false otherwise.
         */
        private boolean splitDirection() {
            final boolean splitHorizontally;
            if (myWidth >= MIN_ROOM_SIZE && myHeight >= MIN_ROOM_SIZE) {
                splitHorizontally = new Random().nextBoolean();
            } else {
                splitHorizontally = myWidth < MIN_ROOM_SIZE;
            }
            return splitHorizontally;
        }

        /**
         * Splits the node horizontally.
         */
        private void splitHorizontally() {
            final int split = new Random().nextInt(myHeight) + myY + 1;
            if (myY + split + 1 < myY + myHeight
                    && split - 1 > myY + 1
                    && (myWidth * split > MIN_ROOM_SIZE)
                    && (myWidth * (myHeight - split) > MIN_ROOM_SIZE)) {
                myLeftChild = new Node(myX, myY, myWidth, split);
                myRightChild = new Node(myX, myY + split,
                        myWidth, myHeight - split);
            }
        }

        /**
         * Splits the node vertically.
         */
        private void splitVertically() {
            final int split = new Random().nextInt(myWidth) + myX + 1;
            if (myX + split + 1 < myX + myWidth
                    && myX + split - 1 > myX + 1
                    && (myHeight * split > MIN_ROOM_SIZE)
                    && (myHeight * (myWidth - split) > MIN_ROOM_SIZE)) {
                myLeftChild = new Node(myX, myY, split, myHeight);
                myRightChild = new Node(myX + split, myY,myWidth - split, myHeight);
            }
        }
    }
}
