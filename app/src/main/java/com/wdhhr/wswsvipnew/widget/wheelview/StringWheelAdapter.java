/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wdhhr.wswsvipnew.widget.wheelview;


/**
 * Numeric Wheel adapter.
 */
public class StringWheelAdapter implements WheelAdapter {

	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;

	// Values
	private int minValue;
	private int maxValue;
	private String[] data;
	// format
	private String format;

	/**
	 * Default constructor
	 */
	public StringWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public StringWheelAdapter(int minValue, int maxValue) {
		this(minValue, maxValue, null);
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public StringWheelAdapter(int minValue, int maxValue, String[] data) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.data = data;
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
//			int value = minValue + index;
			return data[index];
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}
	
	@Override
	public int getMaximumLength() {
		int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
		int maxLen = Integer.toString(max).length();
		if (minValue < 0) {
			maxLen++;
		}
		return maxLen;
	}
}
