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

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class UpsideDownButton extends Button {

	// The below two constructors appear to be required
	public UpsideDownButton(Context context) {
		super(context);
	}

	public UpsideDownButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UpsideDownButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// This saves off the matrix that the canvas applies to draws, so it can
		// be restored later.

		canvas.save();
		// super.onDraw(canvas);
		// now we change the matrix
		// We need to rotate around the center of our text
		// Otherwise it rotates around the origin, and that's bad.
		float py = this.getHeight() / 2.0f;
		float px = this.getWidth() / 2.0f;
		canvas.rotate(180, px, py);

		// draw the text with the matrix applied.
		super.onDraw(canvas);

		// restore the old matrix.
		canvas.restore();
	}
}
/*
 * <bab.foo.UpsideDownText android:text="Score: 0" android:id="@+id/tvScore"
 * android:layout_width="wrap_content" android:layout_height="wrap_content"
 * android:textColor="#FFFFFF" > </bab.foo.UpsideDownText>
 */