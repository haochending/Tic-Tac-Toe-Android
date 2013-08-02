Project: Tic-Tac-Toe Android Version

List of MVC components:
Model: The model for the whole game, include the OptionActivity and GameActivity.
OptionActivity: The view and embedded controller of the Game option window.
GameActivity: The main game activity includes the 3 game views and the embedded controllers.
ToolBarView: The view and embedded controller of the Toolbar component of the main game.
BoardView: The view and embedded controller of the Board component of the main game.
PlayerView: The view and embedded controller of the Player component of the main game.


Enhancement:
Added the person VS AI mode (Two players mode is player VS. player; One Player mode is player VS. AI), just click the button at the top right corner of the game board to switch between two modes.

The AI is designed to prevent the player to win the game , and the move for AI so fast that we can't observe the turn changing process in AI mode. If people let the AI do the first move, AI won't let people to choose the turn of first move again. People can only change the game mode in Game Option window.
