/**
 * Chronoid
 * Copyright (C) Carles Sentis 2012 <codeskrpas@gmail.com>
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

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener,
		OnPreferenceClickListener {
	private static final String TAG = PrefsActivity.class.getSimpleName();
	private static final String KEY_PLAYERONE = "playerOne_minutes";
	private static final String KEY_DONATION = "prefdonation";
	private static final String KEY_FEEDBACK = "preffeedback";
	// private static final String KEY_SAMETIME = "checkBox_sametime";
	// private static final String KEY_PLAYERTWO = "playerTwo_minutes";
	// private static final String KEY_GAMETYPE = "game_type";
	// private static final String KEY_TIMEDELAY = "time_delay";
	private final short STOPPED = 2;

	private ListPreference lstPlayerOneMinutes = null;

	// private ListPreference lstPlayerTwoMinutes = null;
	// private CheckBoxPreference chkSameTime = null;
	// private ListPreference lstGameType = null;
	// private ListPreference lstTimeDelay = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate Start'd");

		setTitle(R.string.prefsActivity_title);

		addPreferencesFromResource(R.xml.prefs);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);

		lstPlayerOneMinutes = (ListPreference) getPreferenceScreen().findPreference(KEY_PLAYERONE);
		((Preference) findPreference(KEY_DONATION)).setOnPreferenceClickListener(this);
		((Preference) findPreference(KEY_FEEDBACK)).setOnPreferenceClickListener(this);

		// lstPlayerTwoMinutes = (ListPreference)
		// getPreferenceScreen().findPreference(KEY_PLAYERTWO);
		// lstGameType = (ListPreference)
		// getPreferenceScreen().findPreference(KEY_GAMETYPE);
		// chkSameTime = (CheckBoxPreference)
		// getPreferenceScreen().findPreference(KEY_SAMETIME);
		// lstTimeDelay = (ListPreference)
		// getPreferenceScreen().findPreference(KEY_TIMEDELAY);

		updatePreferences();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
				this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, "onSharedPreferenceChanged");

		updatePreferences();
	}

	private void updatePreferences() {
		Log.d(TAG, "updatePreferences");

		ChronoidApplication gameClock = (ChronoidApplication) getApplication();
		Chronoid game = gameClock.getChronoid();
		game.setGameStarted(STOPPED);

		String summary = getString(R.string.player_minutes_summary);
		lstPlayerOneMinutes.setSummary(Integer.toString(game.getMinPlayerOne()) + " " + summary);

		// if (!chkSameTime.isChecked()){
		// lstPlayerTwoMinutes.setSummary(Integer.toString(game.getMinPlayerOne())
		// + " " + summary);
		// }
		// else
		// lstPlayerTwoMinutes.setSummary(Integer.toString(game.getMinPlayerTwo())
		// + " " + summary);
		//
		// summary = getString(R.string.gameType_summary);
		// lstGameType.setSummary(game.getGameType() + " " + summary);
		//
		// summary = getString(R.string.timeDelay_summary);
		// lstTimeDelay.setSummary(Integer.toString(game.getTimeDelay()) + " " +
		// summary);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals(KEY_DONATION)) {
			startActivity(new Intent(this, DonationActivity.class));
		} else if (preference.getKey().equals(KEY_FEEDBACK)) {
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

			String aEmailList[] = { "codeskraps@gmail.com" };

			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Chronoid - Feedback");
			emailIntent.setType("plain/text");

			startActivity(Intent.createChooser(emailIntent, "Send your feedback in:"));
		}
		return true;
	}
}
