package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

public abstract class DataSourceInt {
	
	/**
	 * Get data value
	 * @return value
	 */
	public abstract int getValue();
	
	/**
	 * Get minimum or maximum value
	 * @return min or max
	 */
	public abstract int getMinValue();
	public abstract int getMaxValue();
	
	/**
	 * Test if minimum or maximum values exist
	 * @return true if has limit
	 */
	public abstract boolean hasMinValue();
	public abstract boolean hasMaxValue();
	
}
