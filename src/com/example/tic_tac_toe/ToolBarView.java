package com.example.tic_tac_toe;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToolBarView extends RelativeLayout implements Observer {

	final static int FIRST_MOVE = 1;
	final static int NUM_MOVES = 2;
	final static int ILLEGAL = 3;
	final static int WIN = 4;
	final static int DRAW = 5;

	Model model;
	Button startGame;
	Button tournament;
	TextView message1;
	TextView message2;

	public ToolBarView(Context context, Model m) {
		super(context);

		Log.d("Tic-Tac-Toe", "ToolBarView constructor");

		// get the xml description of the view and "inflate" it
		// into the display (kind of like rendering it)
		View.inflate(context, R.layout.toolbar_view, this);

		// save the model reference
		model = m;
		// add this view to model's list of observers
		model.addObserver(this);

		// get a reference to start game button to manipulate on update
		startGame = (Button) findViewById(R.id.start_game3);
		startGame.setFocusable(false);

		// get a reference to textView message 1
		message1 = (TextView) findViewById(R.id.toolbar_text1);
		message1.setFocusable(false);

		// get a reference to textView message 2
		message2 = (TextView) findViewById(R.id.toolbar_text2);
		message2.setFocusable(false);

		this.registerController();
	}

	/* the controller part */

	private void registerController() {
		startGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// do this each time the button is clicked
				model.newGame();
			}
		});
	}

	// Observer interface
	@Override
	public void update(Observable arg0, Object arg1) {

		int gameStatus = model.getStatus();

		// set different message content according to the game status
		switch (gameStatus) {
		case FIRST_MOVE:
			this.message1.setText("Change which player starts,");
			this.message2.setText("or make first move.");
			break;
		case NUM_MOVES:
			int moves = 9 - model.getBlanks();
			this.message1.setText(moves + " moves.");
			this.message2.setText("");
			break;
		case ILLEGAL:
			this.message1.setText("Illegal move");
			this.message2.setText("");
			break;
		case WIN:
			int playerTurn = model.getTurn();
			if (playerTurn == 0) {
				if (model.getPlayer1Name().length() == 0) {
					this.message1.setText("O Wins");
				} else {
					this.message1.setText(model.getPlayer1Name() + " Wins");
				}
				this.message2.setText("");
			} else {
				if (model.getPlayer2Name().length() == 0) {
					this.message1.setText("X Wins");
				} else {
					this.message1.setText(model.getPlayer2Name() + " Wins");
				}
				this.message2.setText("");
			}
			break;
		case DRAW:
			this.message1.setText("Game over,");
			this.message2.setText("no winner.");
			break;

		default:
			break;
		}

	}
}
