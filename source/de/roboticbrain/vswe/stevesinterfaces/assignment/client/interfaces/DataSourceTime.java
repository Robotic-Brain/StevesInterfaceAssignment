package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.DataSourceInt;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;


/**
 * 
 * Datasource for TileEntitieWoodGenerator.timer
 *
 */
public class DataSourceTime extends DataSourceInt {
	
	private final TileEntitieWoodGenerator generator;
	
	public DataSourceTime(TileEntitieWoodGenerator te) {
		generator = te;
	}

	@Override
	public int getValue() {
		return generator.getTimer();
	}
	
	@Override
	public int getMinValue() {
		return generator.getMaxTime();
	}
	
	@Override
	public int getMaxValue() {
		return 0;
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
