package model;

import java.util.*;

/**
 * Class representing a BSP dungeon generator.
 * This class generates a random dungeon layout using a binary space partitioning algorithm.
 * The dungeon consists of rooms and corridors connected by doors.
 *
 * @author Tiger Schueler
 * @version 28JUL24
 */
public class Dungeon {

    private final int MAP_SIZE = 50;
    private final Tile[][] MAP;
    private static final int MIN_ROOM_SIZE = 10;
    private final Node ROOT;
    //    private final List<Node> rooms;
    private final List<Room> myRoomList;
    private final List<Door> myDoorList;
    private int myTotalRooms = 0;

    public Dungeon() {
        MAP = new Tile[MAP_SIZE][MAP_SIZE];
        ROOT = new Node(1, 1, MAP_SIZE - 1, MAP_SIZE - 1);
//        rooms = new ArrayList<>();
        myRoomList = new ArrayList<>();
        myDoorList = new ArrayList<>();
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
            final String roomName = "Room " + myTotalRooms;
//            rooms.add(theNode);
            final Room newRoom = new Room(roomName, theNode.getX(), theNode.getY(),
                    theNode.getWidth(), theNode.getHeight());
            myRoomList.add(newRoom);
            placeFloorTiles(newRoom);
        } else {
            createRooms(theNode.myLeftChild);
            createRooms(theNode.myRightChild);
        }
    }

    /**
     * Creates a room within the node.
     *
     * @param theRoom The node to create a room in.
     */
    private void placeFloorTiles(final Room theRoom) {
        for (int i = theRoom.getY(); i < theRoom.getY() + theRoom.getHeight() - 1; i++) {
            for (int j = theRoom.getX(); j < theRoom.getX() + theRoom.getWidth() - 1; j++) {
                if (i > 0 && j > 0) {
                    MAP[i][j] = Tile.FLOOR;
                }
            }
        }
    }

    /**
     * Creates doorways connecting adjacent doors on the North, South, East, and West walls.
     */
    private void createDoors() {
        for (final Room room : myRoomList) {
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
                final Door newDoor = new Door(new Position(roomX + width - 1,
                        roomY + i));
                theRoom.getDoors().add(newDoor);
                theRoom.setHasDoors(true);
                break;
            }
        }
    }
    //

    /**
     * if room has doors add adjacent door to adjacency list.
     */
    private void createRoomAdjacency() {
        for (final Room room1 : myRoomList) {
            if(room1.containsDoors()) {
                for (final Door door : room1.getDoors()) {
                    int doorX = door.getPosition().getMyX();
                    int doorY = door.getPosition().getMyY();
                    if (MAP[doorY][doorX + 1] == Tile.FLOOR
                            && MAP[doorY + 1][doorX] == Tile.WALL) {
                        doorX++;
                    } else if (MAP[doorY + 1][doorX] == Tile.FLOOR
                            && MAP[doorY][doorX + 1] == Tile.WALL) {
                        doorY++;
                    }
                    for (final Room room2 : myRoomList) {
                        boolean isAdjacent = room1.findRoom(room2, doorX, doorY);
                        if (isAdjacent) {
                            if (!room1.getAdjacentRooms().contains(room2)) {
                                room1.getAdjacentRooms().add(room2);
//                                room2.getDoors().add(door);
//                                room2.setHasDoors(true);
                            }
                            if (!room2.getAdjacentRooms().contains(room1)) {
                                room2.getAdjacentRooms().add(room1);
                            }

//                            room2.setHasDoors(true);
//                            room2.getDoors().add(door);
                        }
                    }
                }
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
                final Door newDoor = new Door(new Position(roomX + width - 1,
                        roomY + i));
                theRoom.getDoors().add(newDoor);
                break;
            }
        }
    }

    private void placeKeys() {
        final int maxKeys = 4;
        int counter = 0;
        while(counter < maxKeys) {
            Random rand = new Random();
            final int min = 1;
            final int max = 48;
            final int randomNumberOne = rand.nextInt(max - min + 1) + min;
            final int randomNumberTwo = rand.nextInt(max - min + 1) + min;
            if (MAP[randomNumberTwo][randomNumberOne] == Tile.FLOOR) {
                MAP[randomNumberTwo][randomNumberOne] = Tile.KEY;
                counter++;
            }
        }
    }

    private void placeExit() {
        int exitCounter = 0;
        while (exitCounter < 2) {
            Random rand = new Random();
            final int min = 1;
            final int max = 48;
            final int randomNumberOne = rand.nextInt(max - min + 1) + min;
            final int randomNumberTwo = rand.nextInt(max - min + 1) + min;
            if (MAP[randomNumberTwo][randomNumberOne] == Tile.FLOOR) {
                MAP[randomNumberTwo][randomNumberOne] = Tile.EXIT;
                exitCounter++;
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
                } else if (MAP[i][j] == Tile.DOOR){
                    mapBuilder.append('D');
                } else if (MAP[i][j] == Tile.KEY){
                    mapBuilder.append('K');
                } else if (MAP[i][j] == Tile.EXIT){
                    mapBuilder.append('E');
                } else if (MAP[i][j] == Tile.OPEN_DOOR){
                    mapBuilder.append('O');
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
//        rooms.clear();
        myRoomList.clear();
        initializeRoot();
        initializeMap();
        splitMap(ROOT);
        createRooms(ROOT);
        createDoors();
        createRoomAdjacency();
        placeKeys();
        placeExit();
        if (myTotalRooms < 15) {
            myTotalRooms = 0;
            generateDungeon();
        } else {
            System.out.println("Dungeon Rooms " + myTotalRooms + ": ");
            int i = 0;
            for (Room room : myRoomList) {
                System.out.println();
                System.out.println(room);
//                System.out.println(i);
                System.out.print("Adjacent Rooms");
                System.out.println(room.getAdjacentRooms());
                i++;
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