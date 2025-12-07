TETRIS Game using JavaFX

A smooth, responsive implementation of the classic Tetris game, built using JavaFX.
The project uses Java object-oriented design to create an engaging gaming experience.

GitHub: https://github.com/yumnakif/TetrisCW2025.git

• Compilation Instructions:
1. Clone the repository from the link provided 
2. Open or import the folder as a Maven project (Recommended IDE IntelliJ or Eclipse)
3. Under File -> Project Structure -> Libraries set the JDK to Java 23
   
5. IntelliJ: Open the Maven toolbar (usually on right side)
6. Navigate to Plugins-> javafx-> javafx:run
7. Double click javafx:run to execute

8. Eclipse: Right-click the project, Run as Maven Build
9. In Goals enter: clean compile javafx:run
10. Click Run


**_FEATURES:-_**

• Implemented and Working Properly:

**Game Modes:**

Normal mode:
-Normal Tetris game begins when Start Game button is clicked in main menu
-The dynamic level system introduces random obstacles and challenges as the levels increase
-Speed increases by a fixed increase rate at each level

Timed mode:
-Timed Mode begins when Timed Mode button is clicked in main menu
-A 60-second timer begins when the game is started
-Player attempts to achieve maximum score within the time limit
-Game ends when the timer expires

**Level System:**
-The game levels increment after every 5 lines cleared
-The levels feature a progressive challenge system:
-Level 1-2 includes normal gameplay with increasing speed and no challenges
-Level 3-5 activates 1 random challenge from the Level types
-Level 6-8 combine 2 random challenges
-Level 9-12 activates 3 random challenges to operate concurrently
-Level 13+ reaches the maximum challenge amount and combines 4 random challenges until gameplay is over

Level Types:
-The level or challenge types include:
-Static obstacles: Randomly places 2 static obstacle blocks on the board
-Wind effect: The currently falling brick is pushed left or right randomly by a wind-like effect
-Fog overlay: A translucent gray overlay appears which reduces board visibility
-Dynamic obstacles: Randomly places 3 obstacles  that reposition every 10 seconds
-Reverse controls: Left and Right keys swap control functions at random intervals

Speed Progression:
-Brick falling speed increases by 5% with each level
-Challenges are applied along with continuing speed progression
-After the speed reaches 50, it is kept constant at 50 to prevent unplayable speeds


**Main menu screen**
A main menu screen with navigation buttons is displayed when the application is run:
-Start Game button begins a new Tetris game
-Timed Mode button starts a new 90-second timed game
-Exit button exits and closes the application
-Tetris logo image displays at the top of the menu


**Pause menu:**
Accessible Pause menu for in-game navigation:
-Pause button toggles into resume button when clicked
-Translucent overlay is displayed with a focused panel
-Restart Game button in pause menu to restart the current game without returning to main menu
-Main Menu button to exit the current game and return to the main menu
-Current game data is preserved during pause state


**Game over panel:**
Game over screen displaying game statistics and navigation options when the current game session is ended:
- Final score displayed, showing the total points achieved in the current game
- Displays the high score stored in the game and displays a message when the final score has exceeded the high score
- Displays the elapsed time in the current game in MM:SS format
- Final level reached and lines cleared data is visible in the right panel
- New game button starts a new game session
- Main menu button returns to the main menu screen


**High Score:**
Persistent scoring storage with comparative tracking:
-Real time updates of high score during gameplay
-Storage is persistent since scores are saved between application sessions
-Message in Game over panel when a new high score is achieved and the high score value is updated accordingly


**Next Brick preview:**
-Preview of the next block that will spawn to aid the user in creating gameplay strategy
-Clearly displays the next Tetromino shape
-Positioned in the side panel for user to reference and use in strategic planning
-All brick are consistent in their coloring
-Bricks are shown in their default orientation


**Ghost brick:**
Visual guide for users to view optimal piece placement:
-Shows where the current brick will land in real time
-Translucent "ghost" of the current falling brick
-Rotates according to the active brick
-Previews the placement accurately, accounting for the existing blocks or obstacles


**Hard drop button:**
Instant placement of brick:
-Immediately drops the falling brick when Enter button is pressed
-Bonus score added according to the drop height
-Works seamlessly with other control and allows more efficient gameplay for user


**Stopwatch:**
Time tracking system:
- Starts from 00:00 and times the game in real time, displaying in an MM:SS format in the right panel
- Automatically stops when game is paused, or when the game is over
- Restarts from 00:00 with every game
- Final elapsed time is displayed in the game over screen


• Implemented but Not Working Properly:
-Pop up notification when level is increased
-Remaining core features of the refactored game are functioning as intended


• Features Not Implemented:
-More extensive Modes/Levels not implemented due to time constraints.
-Visual Effects on clearing lines and hard drop animations were attempted
    however due to excessive bugs and priority given to code refactoring and functional implementations, it was removed.
-Audio effects not implemented due to excessive bugs and priority on more functional changes.
-Separate high score for timed mode


**• New Java Classes:-**

com.comp2042.ui:
1. GameRender: Responsible for visual rendering and display management of the Tetris game. It handles the graphical components
    including the game board, the current falling brick, ghost piece preview and the next block preview.
    GameRender class translates the logic into visual elements on the screen.

2. GameState: Manages the game state panels if the game is in Pause or Game over mode
    Handles show and hide functions for the panels depending on the current game state
    Manages the score and time elapsed display for the game over panel.

3. InputHandler: Handles user key press events and calls the game logic methods for the corresponding keys

4. MenuScreen: UI component that displays the main menu when the application is started.
    Displays a custom Tetris title image.
    Start Game button to start a new Tetris game, Timed Mode button to start a Timed game session and Exit button to exit the application cleanly.

5. PausePanel: UI overlay that appears when the pause button is clicked on the game screen.
    Displays a PAUSED title at the top of the screen. Pause button toggles to Resume button actions when the pause panel is displayed.
    Pause Panel contains Restart Game button to start a new game session in the current scene without returning to menu
    Main menu button to return to the menu screen.

com.comp2042.logic.board:
6. Stopwatch: Tracks the time during gameplay, initially starting from 00:00 and displayed in MM:SS format
    Handles stop, start, reset and restart and binding to label logic for the stopwatch.

com.comp2042.logic.modes:
7. TimedMode: Starts a timed game when Timed Mode button is clicked in main menu. Starts a 60-second timer when the game is started
    Handles stop, start, reset and restart and bind to label logic for the timer.
    The game ends when the timer expires.

com.comp2042.logic.bricks:
8. BrickOperations: Handles the rotation, transformation and state management of the Tetrominoes.
    Implements the rotation logic for all the bricks while including collision and wall checking.
    Maintains the current orientation and provides access to brick shape matrix in the current rotation state.

9. BrickShape: Enumeration defining the Tetromino shapes and condensing the original system which created 7 separate classes for each brick.
    Purpose of implementing this class is to centralize the shape definitions and keep all the data about the brick color and matrix in one place.
    Simplifies the brick creation and allows the code to be more maintainable.

com.comp2042.logic.levels:
10. Level: Abstract class providing the interface for the game levels.
    Declares methods for applying effects according to the levels, checks level properties, managing level transitions.
    SpeedLevel and Challenges classes extend this class which allows consistent behaviour.

11. Challenges: Implements concrete level system that combines multiple level types simultaneously.
    Allows levels to include any combination of the different level types: static obstacles, dynamic obstacles, fog, wind and reverse controls
        based on the level progression
    Manages the Timeline objects for the dynamic obstacles, control reverse and wind effect challenges
    Manages the current state of each active challenge
    Works with GameController to apply the gameplay modifications

12. LevelType: Enumeration defining the five challenges available in the game
    Includes: STATIC_OBSTACLES, DYNAMIC_OBSTACLES, WIND, REVERSE_CONTROLS, FOG
    -Static obstacles: Randomly places 2 static obstacle blocks on the board
    -Wind effect: The currently falling brick is pushed left or right randomly by a wind-like effect
    -Fog overlay: A translucent gray overlay appears which reduces board visibility
    -Dynamic obstacles: Randomly places 3 obstacles  that reposition every 10 seconds
    -Reverse controls: Left and Right keys swap control functions at random intervals

13. LevelController: Controls the overall level progression system and tracks the current and previous levels
    Handles transitions between levels to ensure the previous level effects are cleared up properly
    Determines when to advance levels based on lines cleared and calculates the number of challenges to be included
    Randomly selects challenges based on the levels to implement a gradual difficulty curve
    Provides levelProperty that allows UI component binding 

14. SpeedLevel: Implements the progressive speed increase within the levels, providing a foundation for more complex challenge levels
    Calculates an exponential speed increase using the formula speedMultiplier=increaseRate^(levelNumber-1) where increaseRate=1.05
        allowing for a 5% increase in speed per level
    Challenge class extends this class  to add challenge effects on top of the speed progression


**• Modified Java Classes:**

com.comp2042
-Main: Enhanced to display a menu screen before gameplay begins
    Includes buttons to Start game, start a timed mode game or Exit the application and handles the application accordingly. 
    This provides a structured entry point for the game features.

com.comp2042.input:
-EventType: Added HARD_DROP event for immediate placement of brick when Enter button is clicked in order to extend user control options
-InputEventListener: Extended with methods to get next shape, board matrix, and to handle hard drop event, 
    to make the input management more comprehensive

com.comp2042.controller:
-GameController: Major modifications made to integrate the new level system and challenge mechanics, including obstacle placement,
    wind effects, key controls reversal and fog overlay application.
    The movement methods were modified to manage the event of reverse control challenges in order to swap the key functions.
    Methods created to manage activating or ending challenge effects and transitioning between the levels
    Implemented lines removed counting and the level progression accordingly
    
-GuiController: Extensive overhaul to divide functionality into specialized classes
    Board rendering logic moved to GameRender, input processing included in InputHandler, pause and game over screens managed in GameState 
    Implemented JavaFX binding for better synchronization between the game state and the UI display
    Created a separate Timeline for user initiated down movement to create a smoother movement when down key is pressed and ensure smoother gameplay experience
    Added a method to handle the timed mode gameplay mode with the appropriate UI alterations
    Fog overlay effect handled according to the level challenges
    Created interfaces for connecting game logic updates with visual responses with the rendering system
    Manages integration of UI components and classes with reduced logic handling within the class
    Included Stopwatch Label component to make stopwatch visible in normal game mode
    Handles visibility of Stopwatch and Timer labels depending on the game mode

com.comp2042.ui:
-GameOverPanel: Improved CSS styling of game over screen
    Navigation buttons added to start new game or return to main menu
    Game over screen now displays the score achieved in the current game as well as the saved high score and the time elapsed during the game

-NotificationPanel: Refined visual effects to make the notifications appear smoother and displays combo message when more than one line is cleared

-ViewData: Replaced matrix based data with getBrick to return Brick object in order to work with the new brick system and provide better encapsulation

com.comp2042.logic.board:
-Board: Modified the interface with next shape preview method to allow display of the next piece preview
-NextShapeInfo: Changed to hold and get a Brick object instead of position based tracking to fit the refactored brick system
    As well as cleaner data representation and encapsulation of Brick objects

-Score: Enhanced by adding high score tracking, and improved game over message to show high score and current score achieved 

-SimpleBoard: Replaced usage of Point to Point2D for better precision. Removed BrickRotator class and object, as Brick handles its own rotation
    Removed creation of unnecessary currentGameMatrix copies within every movement method
    Rotation logic improved to also include wall parameters when rotating
    Changed initial brick positioning from Y=10 to Y=1 to make brick start at the top of the board
    Added getNextShape to show the next piece preview in the UI 

-MatrixOperations: Fixed indexing bug in intersect and merge method that caused collision detection issues
    Improved boundary checking in checkOutOfBound and improved checkRemoving method to initialize new empty rows with zero values
    Removed unused deepCopyList method to make the code cleaner


com.comp2042.logic.bricks:
-RandomBrickGenerator: Updated to work with the new brick system by providing random selection from the centralized shape definitions from the enum class
-Deleted Classes: BrickRotator, IBrick, JBrick, LBrick, OBrick, SBrick, TBrick, ZBrick
   In order to implement a new Brick system, the seven brick classes were condensed to instead define all bricks in a single BrickShape enum 
    Rotation logic is integrated in BrickOperations, this eliminates the code duplication and improves the hierarchy of the brick system


**• Unexpected Problems:** 

-Refactoring complications: 
    The original GuiController acted as a "god-class" which handled all the UI rendering, input processing, game state management, etc
    which made the code complicated and difficult to unravel.
    During my refactor of this controller was challenging due to the tightly coupled codes and excessively long methods with unclear boundaries.
    The biggest challenge was identifying clean separation points in the code
    I overcame this by decomposing the class systematically and extracting the functionalities in multiple steps. 
-Down Movement bug:
   After the refactor of GuiController there appeared a critical issue when moving the brick down with the user key input
   as the brick seemed to skip rows and move twice as fast as intended. This occurred because the automatic and user initiated down movements were being triggered
   simultaneously. 
   The solution implemented for this issue was to create a separate movement timelines for user and automatic down movement
   with a faster timeline for the user initiated movement that only activates when the down key is held.
   This ensures a smoother transition between the movements and an enhanced player experience.
-Level system implementation issues:
    The initial level system I designed had fixed levels effects that were only activated once at certain levels and were never revisited after those specific levels had ended
    This created a system where the levels were infinitely speed levels after the final challenge level was over which made the levels quite static.
    Along with that, the level effects also posed a lot of complications during the implementation:
        -Fog overlay caused several issues in the board and UI elements
        -Static and dynamic obstacles were initially created with brick int value of 3, which caused clearing them to clear any intersected bricks of the same value
    Solutions implemented:
        -Created a dedicated value 8 for the obstacles and modified brick handling functions to handle this new value and preserve obstacles when lines clear
        -Implemented layering renders in order to make the fog appear above the board but below the panels
    The solution for the static level system was to implement a new system with a modular approach to handling the levels.
    This included creating thresholds for unlocking certain challenges and creating an infinite challenge level system, which created a smoother difficulty curve
    To prevent overly difficult challenges, challenge validations were implemented.
