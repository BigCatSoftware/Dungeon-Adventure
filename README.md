# Dungeon Adventure

## Overview
Dungeon Adventure is a 2D dungeon RPG developed as part of a group project for the TCSS 360: Software Development and Quality Assurance Techniques course at the University of Washington Tacoma. This game features procedurally generated dungeons, strategic combat, and item management, all built using Java and the LibGDX framework.

This fork showcases my specific contributions to the project, focusing on key areas of development, including dungeon generation, item functionality, and gameplay mechanics.

## My Contributions

### 1. ASCII Dungeon Generation with BSP Algorithm
- **Procedural Dungeon Layouts:** Implemented ASCII dungeon generation using the Binary Space Partitioning (BSP) algorithm. This was a challenging but rewarding task that added significant depth to the game's replayability.
- **Integration with LibGDX:** Integrated the generated dungeons into LibGDX, enabling visual and interactive functionality within the game.

### 2. Item Classes and Tile Enums
- **Potion Tiles:** Developed Potion Tile Enums, including health potions and poison potions. Health potions were added to the inventory for combat use, while poison potions acted as traps that immediately caused damage to the player.
- **Bomb Functionality:** Created Bomb Tiles and a Bomb Icon, and implemented functionality for players to collect bombs by walking on bomb tiles and using them in combat.
- **Pit Traps:** Added pit traps that damage players when walked on, introducing additional hazards to dungeon exploration.

### 3. Visual Enhancements and Game Interactions
- **Tile PNGs and Variations:** Designed and added PNGs for different tiles, including walls, floors, and doors. Created variations of old tile PNGs to enhance visual diversity.
- **Door Logic:** Implemented door logic to ensure proper interactions, allowing doors to open and close based on player actions.

### 4. Game Interface and Documentation
- **Statistics Screen:** Developed a Statistics Screen that displays player stats after completing a dungeon, providing players with feedback on their performance.
- **Software Requirements Specification (SRS):** Contributed to the SRS document, ensuring clear and thorough documentation of the game's requirements.
- **UML Diagrams and Class Layout:** Assisted in brainstorming the initial UML diagrams and the class layout/hierarchy, helping to define the interactions between different components of the game.

## Technologies Used
- **Java:** The primary programming language used for game development.
- **LibGDX:** A cross-platform game development framework used for rendering, input handling, and more.

## How to Run
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/BigCatSoftware/Dungeon-Adventure.git
2. **Navigate to Launcher**
   navigate to `desktop/src/com.dungeonadventure.game` directory
   run `DesktopLauncher` file to start the game

## Screenshots
![DungeonLayoutWithBSPMap](https://github.com/user-attachments/assets/4e8ebc2b-3cd4-4a4a-884d-9113a5057f15)
![DungeonCombatScreen](https://github.com/user-attachments/assets/e2362f9d-dc12-4115-b6da-7c8439fe05e3)

## Contact
Feel free to reach out if you have any questions or are interested in collaboration:
[LinkedIn](www.linkedin.com/in/tigerschueler)
[Email](mailto:tigerschuelerr@gmail.com)

