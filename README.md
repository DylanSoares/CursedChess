# CursedChess
Because we can.

COSC318 Final Project, a multiplayer chess game written in Java.

1. Launch ChessServer.java (If not running)
    1. Verify IP and PORT are correct
    2. Click "Start Server"
2. Launch ChessClient.java (Both players)
    1. If server info was modified, click "Change Server Info" at the bottom.
    2. Click "Continue", the UI will close waiting for both users.
    3. The server window should report the successful connection
   
Known issues:
1. UI when selecting squares can occasionally bug out until the continue button is pressed.
2. Draw by repetition is not checked.
3. Draw by fifty-move rule is not checked.
4. Losing client is not notified they have lost the game.

TODO:
1. Server Code
   1. Multithreading
   2. Restarting
   3. Forfeiting
   4. Handling disconnects
   5. More log messages