package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

/**
 * 
 * Implemented by views with subviews
 *
 */
public interface IViewContainer {
	
	/**
	 * Adds a sub view to the internal view list
	 * @param view
	 */
	abstract void addView(GuiView view);
}
