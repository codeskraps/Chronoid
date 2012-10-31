/**
 * Chronoid
 * Copyright (C) Carles Sentis 2012 <codeskraps@gmail.com>
 *
 * Chronoid is free software: you can
 * redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later
 * version.
 *  
 * Chronoid is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *  
 * You should have received a copy of the GNU
 * General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.codeskraps.chronoid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.Toast;

public class ChronoidActivity extends Activity implements OnClickListener,
		OnChronometerTickListener {
	private static final String TAG = ChronoidActivity.class.getSimpleName();

	private static final String KEY_BLITZ = "Blitz Chess";
	private static final String KEY_FISCHER = "Fischer";
	private static final String KEY_BRONSTEIN = "Bronstein delay";
	private static final String KEY_SIMPLE = "Simple delay";
	private static final String KEY_WORD = "Word";
	private static final String KEY_HOUR = "Hour Glass";

	private final short STARTED = 0;
	private final short PAUSED = 1;
	private final short STOPPED = 2;
	private final short STOPPED_MENU = 3;

	private Chronometer chronometer = null;
	private Button btnOne = null;
	private Button btnTwo = null;

	private Chronoid chronoid = null;
	private Object data = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate Start'd");

		super.onCreate(savedInstanceState);

		ChronoidApplication gameClock = (ChronoidApplication) getApplication();
		chronoid = gameClock.getChronoid();

		setContentView(R.layout.main);

		chronometer = (Chronometer) findViewById(R.id.chronometer);
		btnOne = (Button) findViewById(R.id.btnOne);
		btnTwo = (Button) findViewById(R.id.btnTwo);

		btnOne.setOnClickListener(this);
		btnTwo.setOnClickListener(this);

		chronometer.setOnChronometerTickListener(this);

		data = getLastNonConfigurationInstance();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.d(TAG, "onRetainNonConfigurationInstance started");

		// ChangingOrientationSettings ch = new
		// ChangingOrientationSettings(btnOne.getText(), btnTwo.getText());
		List<CharSequence> ch = new ArrayList<CharSequence>();
		ch.add(btnOne.getText());
		ch.add(btnTwo.getText());
		Log.d(TAG, "one: " + ch.get(0) + ",two: " + ch.get(1));
		return ch;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		chronoid.setGameStarted(STOPPED);
		finish();
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		Log.d(TAG, "onKeyDown");

		if (keycode == KeyEvent.KEYCODE_MENU) {

			if (chronoid.getGameStarted() == STARTED) {
				chronometer.stop();
				chronoid.setPauseTime(SystemClock.elapsedRealtime());
				chronoid.setGameStarted(PAUSED);
			} else {
				chronoid.setGameStarted(STOPPED_MENU);
			}

			Intent i = new Intent(this, PausedActivity.class);
			startActivity(i);

		}
		return super.onKeyDown(keycode, event);
	}

	private void setGame() {
		Log.d(TAG, "setGame Start'd");

		btnOne.setEnabled(true);
		btnTwo.setEnabled(true);

		if (chronoid.getMinPlayerOne() > 9) btnOne.setText(chronoid.getMinPlayerOne() + ":00");
		else btnOne.setText("0" + chronoid.getMinPlayerOne() + ":00");

		if (chronoid.getMinPlayerTwo() > 9) btnTwo.setText(chronoid.getMinPlayerTwo() + ":00");
		else btnTwo.setText("0" + chronoid.getMinPlayerTwo() + ":00");

		chronoid.setGameStarted(STOPPED);
		chronometer.stop();

		Log.d(TAG, "setGame Finish'd");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume Start'd");

		super.onResume();

		ChronoidApplication gameClock = (ChronoidApplication) getApplication();
		chronoid = gameClock.getChronoid();

		switch (chronoid.getGameStarted()) {
		case PAUSED:
			chronoid.setGameStarted(STARTED);
			chronoid.setBaseTime(chronoid.getBaseTime()
					+ (SystemClock.elapsedRealtime() - chronoid.getPauseTime()));
			chronometer.start();
			break;
		case STARTED:
			// final Object data = getLastNonConfigurationInstance();
			if (data != null) {
				final List<CharSequence> ch = (List<CharSequence>) data;
				// Log.d(TAG, "one: " + ch.get(0) + ",two: " + ch.get(1));
				btnOne.setText(ch.get(0));
				btnTwo.setText(ch.get(1));
			}

			if (chronoid.getFirstPlayer()) btnOne.setEnabled(false);
			else btnTwo.setEnabled(false);
			// Log.d(TAG, "onResume after setting button enabled: " +
			// game.getBaseTime());
			chronometer.setBase(chronoid.getBaseTime());
			// Log.d(TAG, "onResume after setting chron setbase");
			chronometer.start();
			// Log.d(TAG, "onResume after starting chron");

			break;
		default:
			setGame();
		}
		Log.d(TAG, "onResume Finish'd");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOne:

			btnOne.setEnabled(false);
			btnTwo.setEnabled(true);

			chronoid.setFirstPlayer(true);

			if (chronoid.getGameType().equals(KEY_FISCHER)) {
				Log.d(TAG, "adding time to baseOne");
				chronoid.setPlayerOneBaseTime(chronoid.getPlayerOneBaseTime()
						+ (chronoid.getTimeDelay() * 1000));
			}

			break;

		case R.id.btnTwo:

			btnOne.setEnabled(true);
			btnTwo.setEnabled(false);

			chronoid.setFirstPlayer(false);

			if (chronoid.getGameType().equals(KEY_FISCHER)) {
				Log.d(TAG, "adding time to baseTwo");
				chronoid.setPlayerTwoBaseTime(chronoid.getPlayerTwoBaseTime()
						+ (chronoid.getTimeDelay() * 1000));
			}

			break;
		}

		if (chronoid.getGameStarted() == STOPPED) {
			chronoid.setBaseTime(SystemClock.elapsedRealtime());
			chronoid.setPlayerOneBaseTime(chronoid.getBaseTime());
			chronoid.setPlayerTwoBaseTime(chronoid.getBaseTime());
			chronometer.setBase(chronoid.getBaseTime());
			chronometer.start();
			chronoid.setGameStarted(STARTED);
		}
	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// Log.d(TAG, "onChronometerTick started");

		if (chronoid.getGameType().equals(KEY_BLITZ)) playingBlitz();
		else if (chronoid.getGameType().equals(KEY_FISCHER)) playingFischer();
		else if (chronoid.getGameType().equals(KEY_BRONSTEIN)) playingBronstein();
		else if (chronoid.getGameType().equals(KEY_SIMPLE)) playingSimple();
		else if (chronoid.getGameType().equals(KEY_WORD)) playingWord();
		else if (chronoid.getGameType().equals(KEY_HOUR)) playingHour();
	}

	private void playingBlitz() {
		Button btnPlayer = null;
		Button btnOtherPlayer = null;
		long minutes, minutesOther, startMinutes;
		long seconds, secondsOther;
		minutes = minutesOther = startMinutes = seconds = secondsOther = 0;

		if (!chronoid.getFirstPlayer()) {
			btnPlayer = btnOne;
			btnOtherPlayer = btnTwo;
		} else {
			btnPlayer = btnTwo;
			btnOtherPlayer = btnOne;
		}

		startMinutes = chronoid.getMinPlayerOne();

		/**
		 * Getting other player elapsed time
		 */
		try {
			minutesOther = startMinutes
					- Integer.parseInt((String) btnOtherPlayer.getText().subSequence(0, 2));
			if (Integer.parseInt((String) btnOtherPlayer.getText().subSequence(3, 5)) != 0) {
				secondsOther = 60 - Integer.parseInt((String) btnOtherPlayer.getText().subSequence(
						3, 5));
				minutesOther--;
			} else {
				secondsOther = 0;
			}
		} catch (StringIndexOutOfBoundsException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (NumberFormatException e) {
			Log.e(TAG, e.getMessage(), e);
		}

		/**
		 * Getting time
		 */
		minutes = startMinutes
				- (((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) / 60)
				+ minutesOther;
		if ((((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) % 60) > secondsOther) {
			seconds = (((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) % 60)
					- secondsOther;
			if (seconds != 0) seconds = 60 - seconds;
		} else {
			seconds = secondsOther
					- (((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) % 60);
			if (seconds != 0) minutes++;
		}
		if (seconds != 0) minutes--;

		Log.d(TAG, "ElapseTime: "
				+ (((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) / 60) + ":"
				+ (((SystemClock.elapsedRealtime() - chronoid.getBaseTime()) / 1000) % 60)
				+ ", time: " + minutes + ":" + seconds + ", other: " + minutesOther + ":"
				+ secondsOther);

		/**
		 * Displaying time
		 */
		if (minutes > 9) {
			if (seconds > 9) btnPlayer.setText(minutes + ":" + seconds);
			else btnPlayer.setText(minutes + ":" + "0" + seconds);
		} else {
			if (seconds > 9) btnPlayer.setText("0" + minutes + ":" + seconds);
			else btnPlayer.setText("0" + minutes + ":" + "0" + seconds);
		}

		if (minutes == 0 && seconds == 0) {
			chronometer.stop();
			Toast.makeText(this, "GameOver", Toast.LENGTH_SHORT).show();
		}
	}

	private void playingFischer() {
		Button btnPlayer = null;
		Button btnOtherPlayer = null;
		long minutes, minutesOther, startMinutes;
		long seconds, secondsOther;
		long playerBaseTime;

		if (!chronoid.getFirstPlayer()) {
			btnPlayer = btnOne;
			btnOtherPlayer = btnTwo;
			playerBaseTime = chronoid.getPlayerOneBaseTime();
		} else {
			btnPlayer = btnTwo;
			btnOtherPlayer = btnOne;
			playerBaseTime = chronoid.getPlayerTwoBaseTime();
		}

		startMinutes = chronoid.getMinPlayerOne();

		/**
		 * Getting other player elapsed time
		 */
		minutesOther = startMinutes
				- Integer.parseInt((String) btnOtherPlayer.getText().subSequence(0, 2));
		if (Integer.parseInt((String) btnOtherPlayer.getText().subSequence(3, 5)) != 0) {
			secondsOther = 60 - Integer.parseInt((String) btnOtherPlayer.getText()
					.subSequence(3, 5));
			minutesOther--;
		} else {
			secondsOther = 0;
		}

		/**
		 * Getting time
		 */
		minutes = startMinutes - (((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) / 60)
				+ minutesOther;
		if ((((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) % 60) > secondsOther) {
			seconds = (((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) % 60)
					- secondsOther;
			if (seconds != 0) seconds = 60 - seconds;
		} else {
			seconds = secondsOther
					- (((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) % 60);
			if (seconds != 0) minutes++;
		}
		if (seconds != 0) minutes--;

		Log.d(TAG, "ElapseTime: "
				+ (((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) / 60) + ":"
				+ (((SystemClock.elapsedRealtime() - playerBaseTime) / 1000) % 60) + ", time: "
				+ minutes + ":" + seconds + ", other: " + minutesOther + ":" + secondsOther);

		/**
		 * Displaying time
		 */
		if (minutes > 9) {
			if (seconds > 9) btnPlayer.setText(minutes + ":" + seconds);
			else btnPlayer.setText(minutes + ":" + "0" + seconds);
		} else {
			if (seconds > 9) btnPlayer.setText("0" + minutes + ":" + seconds);
			else btnPlayer.setText("0" + minutes + ":" + "0" + seconds);
		}

		if (minutes == 0 && seconds == 0) {
			chronometer.stop();
			Toast.makeText(this, "GameOver", Toast.LENGTH_SHORT).show();
		}
	}

	private void playingBronstein() {

	}

	private void playingSimple() {

	}

	private void playingWord() {

	}

	private void playingHour() {

	}
}