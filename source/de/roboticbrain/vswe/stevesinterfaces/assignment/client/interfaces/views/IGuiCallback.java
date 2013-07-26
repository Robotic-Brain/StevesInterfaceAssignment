package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

/**
 * 
 * Callback interface for Views
 *
 */
public interface IGuiCallback {
	
	/**
	 * Called if view is clicked (aka pressed)
	 * @param sender
	 * @param aux
	 */
	public abstract void onViewClicked(GuiView sender, int aux);
	
}
