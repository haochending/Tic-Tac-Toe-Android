package com.example.tic_tac_toe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

public class GameActivity extends Activity {
	Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		setTitle("Tic-Tac-Toe");

		// setup the model
		Intent intent = getIntent();
		model = (Model) intent.getExtras().get("Model");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		Log.d("Tic-Tac-Toe", "onPostCreate");
		// can only get widgets by id in onPostCreate for activity xml res

		// create the views and add them to the main activity
		ToolBarView toolbarView = new ToolBarView(this, model);
		ViewGroup toolbarViewGroup = (ViewGroup) findViewById(R.id.toolbar);
		toolbarViewGroup.addView(toolbarView);

		
		BoardView boardView = new BoardView(this, model);
		ViewGroup boardViewGroup = (ViewGroup) findViewById(R.id.gameboard);
		boardViewGroup.addView(boardView);
		
		PlayerView playerView = new PlayerView(this, model);
		ViewGroup playerViewGroup = (ViewGroup) findViewById(R.id.player);
		playerViewGroup.addView(playerView);


		// initialize views
		model.setChanged();
		model.notifyObservers();

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	// save and restore state (need to do this to support orientation change)
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("Tic-Tac-Toe", "save state");
		outState.putInt("Blank", model.getBlanks());
		outState.putInt("PlayerTurn", model.getTurn());

		outState.putInt("Status", model.getStatus());
		outState.putInt("Restart", model.getRestart());
		outState.putInt("WinType", model.getWinType());
		outState.putInt("WinPos", model.getWinPos());
		outState.putInt("ChangedX", model.getChangedX());
		outState.putInt("ChangedY", model.getChangedY());
		outState.putInt("InitTurn", model.getInitTurn());
		outState.putString("Player1Name", model.getPlayer1Name());
		outState.putString("Player2Name", model.getPlayer2Name());

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String tileKey = "Tile" + Integer.toString(i) + Integer.toString(j);
				outState.putInt(tileKey, model.getTile(i, j));
			}
		}
		
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("Tic-Tac-Toe", "restore state");
		super.onRestoreInstanceState(savedInstanceState);
		model.setBlanks(savedInstanceState.getInt("Blank"));
		model.setStatus(savedInstanceState.getInt("Status"));
		model.setRestart(savedInstanceState.getInt("Restart"));
		model.setWinType(savedInstanceState.getInt("WinType"));
		model.setWinPos(savedInstanceState.getInt("WinPos"));
		model.setChangedX(savedInstanceState.getInt("ChangedX"));
		model.setChangedY(savedInstanceState.getInt("ChangedY"));
		model.setInitTurn(savedInstanceState.getInt("InitTurn"));
		model.setTurn(savedInstanceState.getInt("PlayerTurn"));
		model.setPlayer1Name(savedInstanceState.getString("Player1Name"));
		model.setPlayer2Name(savedInstanceState.getString("Player2Name"));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String tileKey = "Tile" + Integer.toString(i) + Integer.toString(j);
				model.setTile(i, j, savedInstanceState.getInt(tileKey));
			}
		}
		model.setRotated(1);

	}

}
