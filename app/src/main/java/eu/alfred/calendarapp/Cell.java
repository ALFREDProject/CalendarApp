/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.alfred.calendarapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Cell {
	private static final String TAG = "Cell";
	protected Rect mBound = null;
	protected int mDayOfMonth = 1;	// from 1 to 31
	protected Paint mPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG
            |Paint.ANTI_ALIAS_FLAG);
	float dx, dy;
	public Cell(int dayOfMon, Rect rect, float textSize, boolean bold, Context context) {
		mDayOfMonth = dayOfMon;
		mBound = rect;
		mPaint.setTextSize(textSize/*26f*/);
		mPaint.setColor(Color.BLACK);
		if(bold) mPaint.setFakeBoldText(true);

		dx = convertPixelsToDp((float) mPaint.measureText(String.valueOf(mDayOfMonth)) / 2,context);
		dy = convertPixelsToDp((float) (-mPaint.ascent() + mPaint.descent()) / 2,context);
	}

	public Cell(int dayOfMon, Rect rect, float textSize, Context context) {
		this(dayOfMon, rect, textSize, false, context);
	}
	
	protected void draw(Canvas canvas) {
		canvas.drawText(String.valueOf(mDayOfMonth), mBound.centerX() - dx, mBound.centerY() + dy, mPaint);
	}
	
	public int getDayOfMonth() {
		return mDayOfMonth;
	}
	
	public boolean hitTest(int x, int y) {
		return mBound.contains(x, y); 
	}
	
	public Rect getBound() {
		return mBound;
	}
	
	public String toString() {
		return String.valueOf(mDayOfMonth)+"("+mBound.toString()+")";
	}

	public static float convertPixelsToDp(float px, Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
		return dp;
	}
}

