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

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class ChronoidApplication extends Application implements OnSharedPreferenceChangeListener {
	private static final String TAG = ChronoidApplication.class.getSimpleName();
	private static final String KEY_PLAYERONE = "playerOne_minutes";
	private static final String KEY_PLAYERTWO = "playerTwo_minutes";
	private static final String KEY_SAMETIME = "checkBox_sametime";
	private static final String KEY_GAMETYPE = "game_type";
	private static final String KEY_TIMEDELAY = "time_delay";

	private SharedPreferences prefs = null;
	private Chronoid chronoid = null;

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "onCreate Start'd");

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		// SharedPreferences settings = getSharedPreferences(n, MODE_PRIVATE);

		chronoid = new Chronoid(this);

		Log.d(TAG, "onCreate Finish.d");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, "onSharedPreferenceChanged");

		updateChronoid();
	}

	public Chronoid getChronoid() {
		updateChronoid();
		return chronoid;
	}

	private void updateChronoid() {
		Log.d(TAG, "updateGame Start'd");

		String minPlayerOne = prefs.getString(KEY_PLAYERONE, "5");
		Log.d(TAG, "updateGame minPlayerOne: " + minPlayerOne);

		if (minPlayerOne.equals("10")) chronoid.setMinPlayerOne((short) 10);
		else if (minPlayerOne.equals("15")) chronoid.setMinPlayerOne((short) 15);
		else if (minPlayerOne.equals("20")) chronoid.setMinPlayerOne((short) 20);
		else if (minPlayerOne.equals("25")) chronoid.setMinPlayerOne((short) 25);
		else chronoid.setMinPlayerOne((short) 5);

		chronoid.setMinPlayerTwo(chronoid.getMinPlayerOne());

		// if (prefs.getBoolean(KEY_SAMETIME, true)) {
		// String minPlayerTwo = prefs.getString(KEY_PLAYERTWO, "5");
		// Log.d(TAG, "updateGame minPlayerTwo: " + minPlayerTwo);
		//
		// if (minPlayerTwo.equals("10")) chronoid.setMinPlayerTwo((short) 10);
		// else if (minPlayerTwo.equals("15")) chronoid.setMinPlayerTwo((short)
		// 15);
		// else if (minPlayerTwo.equals("20")) chronoid.setMinPlayerTwo((short)
		// 20);
		// else if (minPlayerTwo.equals("25")) chronoid.setMinPlayerTwo((short)
		// 25);
		// else chronoid.setMinPlayerTwo((short) 5);
		// Log.d(TAG, "updateGame - Different");
		// }
		// else {
		// chronoid.setMinPlayerTwo(chronoid.getMinPlayerOne());
		// Log.d(TAG, "updateGame - Same");
		// }

		String gameType = prefs.getString(KEY_GAMETYPE, "Blitz Chess");
		chronoid.setGameType(gameType);

		String timeDelay = prefs.getString(KEY_TIMEDELAY, "5");
		if (timeDelay.equals("1")) chronoid.setTimeDelay((short) 1);
		else if (timeDelay.equals("2")) chronoid.setTimeDelay((short) 2);
		else if (timeDelay.equals("3")) chronoid.setTimeDelay((short) 3);
		else if (timeDelay.equals("4")) chronoid.setTimeDelay((short) 4);
		else if (timeDelay.equals("5")) chronoid.setTimeDelay((short) 5);

		Log.d(TAG, "updateGame Finish'd");
	}
}
