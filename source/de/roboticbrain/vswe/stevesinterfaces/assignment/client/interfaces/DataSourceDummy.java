package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.DataSourceInt;

public class DataSourceDummy extends DataSourceInt {

	public int val;
	
	@Override
	public int getValue() {
		return val;
	}

	@Override
	public int getMinValue() {
		return 0;
	}

	@Override
	public int getMaxValue() {
		return 100;
	}

	@Override
	public boolean hasMinValue() {
		return true;
	}

	@Override
	public boolean hasMaxValue() {
		return true;
	}
	
}
