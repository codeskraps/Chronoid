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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

public class PausedActivity extends Activity implements OnClickListener{

	private final short STOPPED = 2;
	
    private TextView pausedText = null;
    
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        setContentView(R.layout.paused);
        
        pausedText = (TextView) findViewById(R.id.paused_text);
        pausedText.setOnClickListener(this);
    }
    
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        openOptionsMenu();
    }

    @Override
	protected void onResume() {
		super.onResume();
		
		ChronoidApplication gameClock = (ChronoidApplication) getApplication();
        Chronoid game = gameClock.getChronoid();
        
		if (game.getGameStarted() == STOPPED)
			finish();
	}
    
    @Override
	public void onClick(View v) {
		PausedActivity.this.finish();
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
					
		switch (item.getItemId()) {
		case R.id.mnuReset:
			ChronoidApplication gameClock = (ChronoidApplication) getApplication();
	        Chronoid game = gameClock.getChronoid();
	        game.setGameStarted(STOPPED);
			finish();
			return true;
		
		case R.id.mnuOptions:
			Intent i = new Intent(this, PrefsActivity.class);
			startActivity(i);
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
