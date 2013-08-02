package com.example.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button gameStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Tic-Tac-Toe", "onCreate");

		// load the base UI (has places for the views)
		setContentView(R.layout.activity_main);
		setTitle("Tic-Tac-Toe");
				
		// Setup the buttons
		gameStart = (Button) findViewById(R.id.start_game);
		gameStart.setOnClickListener(new OnClickListener() {
			// if the button is clicked, start to set the game options
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), OptionActivity.class);
				v.getContext().startActivity(intent);
			}
		});
		
	}

	// save and restore state (need to do this to support orientation change)
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("Tic-Tac-Toe", "save state");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("Tic-Tac-Toe", "restore state");
		super.onRestoreInstanceState(savedInstanceState);
	}

}
