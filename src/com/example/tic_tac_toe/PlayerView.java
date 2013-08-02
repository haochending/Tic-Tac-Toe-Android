package com.example.tic_tac_toe;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class PlayerView extends RelativeLayout implements Observer {

	Model model;
	RadioGroup group;
	RadioButton player1;
	RadioButton player2;

	public PlayerView(Context context, Model m) {
		super(context);

		Log.d("Tic-Tac-Toe", "PlayerView constructor");

		// get the xml description of the view and "inflate" it
		// into the display (kind of like rendering it)
		View.inflate(context, R.layout.player_view, this);

		// save the model reference
		model = m;
		// add this view to model's list of observers
		model.addObserver(this);

		// get a reference to radio group to manipulate on update
		group = (RadioGroup) findViewById(R.id.turnGroup);

		// get a reference to radio buttons to manipulate on update
		player1 = (RadioButton) findViewById(R.id.player_1_turn);
		player2 = (RadioButton) findViewById(R.id.player_2_turn);
		player1.setFocusable(false);
		player2.setFocusable(false);
		
		// set the player names
		if (model.getPlayer1Name().length() != 0) {
			player1.setText(model.getPlayer1Name() + " (O)");
		} else {
			player1.setText("Player O");
		}
		
		if (model.getPlayer2Name().length() != 0) {
			player2.setText(model.getPlayer2Name() + " (X)");
		} else {
			player2.setText("Player X");
		}

		// register the controller
		this.registerController();

	}

	/* the controller part */

	private void registerController() {
		player1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if status is first move, only set the player turn
				if (model.getStatus() == 1) {
					model.setTurn(0);
				}

				// if other status disable the selection
				if (model.getStatus() != 1) {
					if (model.getTurn() == 0) {
						player1.setChecked(true);
					} else {
						player2.setChecked(true);
					}
				}
			}
		});

		player2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if status is first move, only set the player turn
				if (model.getStatus() == 1) model.setTurn(1);


				// if other status disable the selection
				if (model.getStatus() != 1) {
					if (model.getTurn() == 0) {
						player1.setChecked(true);
					} else {
						player2.setChecked(true);
					}
				}
			}
		});
	}

	@Override
	public void update(Observable observable, Object data) {

		// if new game, clear the player turn
		if (model.getStatus() == 1 && model.getRestart() == 1) {
			group.clearCheck();
			if (model.getInitTurn() == 0) {
				player1.setChecked(true);
			} else {
				player2.setChecked(true);
			}
			model.setRestart(0);
		} else if (model.getStatus() == 1 && model.getRestart() == 0) {
			if (model.getTurn() == 0) {
				player1.setChecked(true);
			} else {
				player2.setChecked(true);
			}
		}
		
		if (model.getMode() == 1 && model.getTurn() == 1 && model.getStatus() == 1) model.AIMove();

		// if game starts, update the player turn
		if (model.getStatus() >= 2) {
			if (model.getTurn() == 0) {
				player1.setChecked(true);
			} else {
				player2.setChecked(true);
			}
		}
	}

}
