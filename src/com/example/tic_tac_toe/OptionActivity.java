package com.example.tic_tac_toe;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionActivity extends Activity implements Observer {

	Model model;
	Button start_game;
	Button one_player;
	Button two_player;
	RadioGroup group;
	RadioButton player1_turn;
	RadioButton player2_turn;
	EditText player1_name;
	EditText player2_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		setTitle("Tic-Tac-Toe");
		Log.d("Tic-Tac-Toe", "Game Option constructor");

		// setup the model
		model = new Model();
		model.addObserver(this);

		// setup the radio buttons and radio group
		group = (RadioGroup) findViewById(R.id.select_turn);

		player1_turn = (RadioButton) findViewById(R.id.player1_turn);
		player2_turn = (RadioButton) findViewById(R.id.player2_turn);

		// setup the edit texts
		player1_name = (EditText) findViewById(R.id.player1_name);
		player2_name = (EditText) findViewById(R.id.player2_name);

		// setup the game mode buttons
		one_player = (Button) findViewById(R.id.one_player);
		two_player = (Button) findViewById(R.id.two_player);
		
		// register the controller
		this.registerController();

	}

	@Override
	protected void onResume() {
		// retain the previous selection
		super.onResume();
		Log.d("Tic-Tac-Toe", "Clear: initTurn " + model.getInitTurn());
		if (model.getInitTurn() == -1)
		group.clearCheck();
		Log.d("Tic-Tac-Toe", "Clear");
	}

	/* the controller part */

	private void registerController() {

		player1_turn.setOnClickListener(new OnClickListener() {
			// if the radio button is clicked, set initial turn to player 1
			@Override
			public void onClick(View v) {
				model.setInitTurn(0);
			}
		});

		player2_turn.setOnClickListener(new OnClickListener() {
			// if the radio button is clicked, set initial turn to player 2
			@Override
			public void onClick(View v) {
				model.setInitTurn(1);
			}
		});

		player1_name.addTextChangedListener(new TextWatcher() {
			// if the player name is edited, record the player 1 name
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// set the player1 name
				if (model.getRotated() == 1) {
					return;
				}

				if (s.length() == (model.getPlayer1Name().length() + 1) || s.length() == (model.getPlayer1Name().length() - 1)) {
					model.setPlayer1Name(s.toString());
				} else {
					if (model.getPlayer1Name().equals(s.toString()))
						return;
					player1_name.setText(model.getPlayer1Name());
				}
			}
		});

		player2_name.addTextChangedListener(new TextWatcher() {
			// if the player name is edited, record the player 2 name
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// set the player2 name
				if (model.getRotated() == 1) {
					model.setRotated(0);
					return;
				}

				if (s.length() == (model.getPlayer2Name().length() + 1) || s.length() == (model.getPlayer2Name().length() - 1)) {
					model.setPlayer2Name(s.toString());
				} else {
					if (model.getPlayer2Name().equals(s.toString()))
						return;
					player2_name.setText(model.getPlayer2Name());
				}
			}
		});
		
		one_player.setOnClickListener(new OnClickListener() {
			// if one player button is pressed, set the game mode to 1
			// and start the game activity
			@Override
			public void onClick(View v) {
				model.setMode(1);
				// if not select turn yet, no effect
				if (model.getInitTurn() == -1)
					return;

				// start the game activity, and send the model to game activity
				Intent i = new Intent(v.getContext(), GameActivity.class);
				i.putExtra("Model", model);
				startActivity(i);
			}
		});
		
		two_player.setOnClickListener(new OnClickListener() {
			// if two player button is pressed, set the game mode to 0
			// and start the game activity
			@Override
			public void onClick(View v) {
				model.setMode(0);
				// if not select turn yet, no effect
				if (model.getInitTurn() == -1)
					return;

				// start the game activity, and send the model to game activity
				Intent i = new Intent(v.getContext(), GameActivity.class);
				i.putExtra("Model", model);
				startActivity(i);
			}
		});
	}

	
	// update interface
	@Override
	public void update(Observable observable, Object data) {
		group.clearCheck();
		Log.d("Tic-Tac-Toe", "InitTurn: " +  model.getInitTurn());

		// update player turn
		if (model.getInitTurn() == 0) {
			player1_turn.setChecked(true);
		} else if (model.getInitTurn() == 1) {
			Log.d("Tic-Tac-Toe", "InitTurn1: " +  model.getInitTurn());

			player2_turn.setChecked(true);
		}

		if (model.getRotated() == 1) {
			player1_name.setText(model.getPlayer1Name());
			player2_name.setText(model.getPlayer2Name());
		}
	}

	// save and restore state (need to do this to support orientation change)

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("Tic-Tac-Toe", "save state");

		outState.putInt("InitTurn", model.getInitTurn());
		outState.putString("Player1Name", model.getPlayer1Name());
		outState.putString("Player2Name", model.getPlayer2Name());

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("Tic-Tac-Toe", "restore state");
		super.onRestoreInstanceState(savedInstanceState);
		model.setInitTurn(savedInstanceState.getInt("InitTurn"));
		model.setPlayer1Name(savedInstanceState.getString("Player1Name"));
		model.setPlayer2Name(savedInstanceState.getString("Player2Name"));

		model.setRotated(1);
		model.setChanged();
		model.notifyObservers();
	}
}
