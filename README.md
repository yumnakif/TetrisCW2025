TETRIS GAME using JavaFX

A smooth, responsive implementation of the classic Tetris game, built using JavaFX.
The project uses Java object-oriented design to create an engaging gaming experience.




GitHub: https://github.com/yumnakif/TetrisCW2025.git

• Compilation Instructions: 
///Provide a clear, step-by-step guide on how to compile the
code to produce the application. Include any dependencies or special settings
required.

FEATURES:-

• Implemented and Working Properly: 

**Main menu screen**
A main menu screen with navigation buttons:
-Start Game button begins a new Tetris game
-Exit button cleanly closes the application
-Tetris logo photo displays at the top of the menu

**Pause menu:**
Accessible Pause menu for in-game navigation:
-Pause button toggles into resume button when clicked
-Translucent overlay is displayed with a focused panel
-Restart Game button in pause menu to restart the current game without returning ot main menu
-Main Menu button to exit the game and return to the main menu
-Current game data saved during pause


**Game over panel:**
Game over screen displaying game statistics and navigation options:
- Final score displayed, showing the total points achieved in the current game
- Displays the high score stored in the game and displays a message when the final score has exceeded the high score
- Displays the elapsed time in the current game in MM:SS format
- Final level and lines cleared data is visible in the right panel
- New game button starts a new game session
- Main menu button returns to the main menu screen


**High Score:**
Persistent scoring storage with comparative tracking:
-Real time updates of high score during gameplay
-Storage is persistent since scores are saved between application sessions
-Message in Game over panel when a new high score is achieved


**Next Brick preview:**
-Preview of the next block that will spawn to aid the user in creating a strategy
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
-Immediately drops the falling brick when Enter button is clicked
-Bonus score added according to the drop height
-Works seamlessly with other control and allows more efficient gameplay for user

Stopwatch/Timer:
Time tracking system:
- Starts from 00:00 and times the game in real time, displaying in an MM:SS format in the right panel
- Automatically stop when game is paused, or when the game is over
- Restarts with every game
- Final time is displayed in the game over screen

General Speed levels:
Progressively increasing speeds in every level:
-The speed of the bricks increases per level
-The user must clear 5 rows to advance to a new level
-Special levels are applied at level 4,5,7,8 and 10, speed levels continue on after the special levels are passed


Special levels (4,5,7,8,10):
-Unique gameplay level variations,user will reach these at certain levels.
-- Level 4: Static obstacle level, the speed increases while two static obstacle will be randomly placed on the board
-- Level 5: Fog overlay appears, where a translucent gray screen overlays the board, disrupting the users visibility of the board
-- Level 7: Wind effect level, falling bricks will be randomly pushed left or right by a "wind" effect
            creating a progressively more difficult challenge for the player
-- Level 8: Dynamic obstacle level, 3 dynamic obstacles are placed and moved to new positions on the board every 10 seconds
-- Level 10: Left and Right keys switch directions after random intervals of time

-Speed continues to gradually increase during and after the special levels




• Implemented but Not Working Properly: List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.


• Features Not Implemented: Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.


• New Java Classes: Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.


• Modified Java Classes: List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.


• Unexpected Problems: Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them