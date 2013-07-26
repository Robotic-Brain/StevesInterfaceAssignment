package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.DataSourceBool;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;

/**
 * 
 * Data source for wood type
 *
 */
public class DataSourceWoodTypes extends DataSourceBool {
	
	private TileEntitieWoodGenerator te;
	
	/**
	 * requested type
	 */
	private int eqType;
	
	/**
	 * @param generator
	 * @param type		requested type
	 */
	public DataSourceWoodTypes(TileEntitieWoodGenerator generator, int type) {
		te = generator;
		eqType = type;
	}
	
	/**
	 * returns true if wood type of tile entity equals the one specified in constructor
	 */
	@Override
	public boolean getValue() {
		return te.getWoodType() == eqType;
	}
	
}
