package com.example.tic_tac_toe;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class BoardView extends RelativeLayout implements Observer {

	Model model;
	Button tiles[][];

	public BoardView(Context context, Model m) {
		super(context);

		Log.d("Tic-Tac-Toe", "BoardView constructor");

		// get the xml description of the view and "inflate" it
		// into the display (kind of like rendering it)
		View.inflate(context, R.layout.board_view, this);

		// save the model reference
		model = m;
		// add this view to model's list of observers
		model.addObserver(this);

		// get a reference to widgets to manipulate on update
		tiles = new Button[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String row = Integer.toString(i);
				String col = Integer.toString(j);
				String tileId = "cell" + row + col;
				int id = getResources().getIdentifier(tileId, "id",
						getContext().getPackageName());
				tiles[i][j] = (Button) findViewById(id);
				tiles[i][j].setFocusable(false);
				tiles[i][j].getBackground().setColorFilter(Color.WHITE,
						PorterDuff.Mode.MULTIPLY);
			}
		}

		this.registerControllers();
	}

	/* the controller part */
	private void registerControllers() {
		tiles[0][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(0, 0);
			}
		});

		tiles[0][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(0, 1);
			}
		});

		tiles[0][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(0, 2);
			}
		});

		tiles[1][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(1, 0);
			}
		});

		tiles[1][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(1, 1);
			}
		});

		tiles[1][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(1, 2);
			}
		});

		tiles[2][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(2, 0);
			}
		});

		tiles[2][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(2, 1);
			}
		});

		tiles[2][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				model.playerMove(2, 2);
			}
		});
	}

	// Observer interface
	@Override
	public void update(Observable observable, Object data) {
		int changedX = model.getChangedX();
		int changedY = model.getChangedY();
		int changedValue = model.getChangedValue();

		if (changedValue == 0) {
			tiles[changedX][changedY].setText("O");
		} else if (changedValue == 1) {
			tiles[changedX][changedY].setText("X");
		}

		if (model.getStatus() == 1 && model.getRestart() == 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					tiles[i][j].setText("");
					tiles[i][j].getBackground().setColorFilter(Color.WHITE,
							PorterDuff.Mode.MULTIPLY);
				}
			}
			model.setRestart(0);
		}

		// handle repaint of the screen rotation
		if (model.getRotated() == 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (model.getTile(i, j) == -1) {
						tiles[i][j].setText("");
					} else if (model.getTile(i, j) == 0) {
						tiles[i][j].setText("O");
					} else {
						tiles[i][j].setText("X");
					}
				}
			}
			model.setRotated(0);
		}

		// if anyone wins we need to highlight
		if (model.getStatus() == 4) {
			int winType = model.getWinType();
			int winPos = model.getWinPos();

			switch (winType) {
			case 1: {
				for (int i = 0; i < 3; i++) {
					tiles[winPos][i].getBackground().setColorFilter(Color.RED,
							PorterDuff.Mode.MULTIPLY);
				}
				break;
			}
			case 2: {
				for (int i = 0; i < 3; i++) {
					tiles[i][winPos].getBackground().setColorFilter(Color.RED,
							PorterDuff.Mode.MULTIPLY);
				}
				break;
			}
			case 3: {
				for (int i = 0; i < 3; i++) {
					tiles[i][i].getBackground().setColorFilter(Color.RED,
							PorterDuff.Mode.MULTIPLY);
				}
				break;
			}
			case 4: {
				int counter = 2;
				while (counter >= 0) {
					tiles[2 - counter][counter].getBackground().setColorFilter(
							Color.RED, PorterDuff.Mode.MULTIPLY);
					counter--;
				}
				break;
			}
			default:
				break;
			}
		}
	}
}
