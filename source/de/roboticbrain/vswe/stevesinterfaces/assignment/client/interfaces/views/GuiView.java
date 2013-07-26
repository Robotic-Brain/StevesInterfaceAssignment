package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;


import net.minecraft.client.gui.inventory.GuiContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.GuiWoodGenerator;

/**
 * Superclass of all Views
 * 
 * A View is an abstract notion of a drawable area with coordinate space
 * views can contain sub views, this makes it possible to define hierarchies of coordinate spaces
 */
@SideOnly(Side.CLIENT)
public abstract class GuiView {
	
	/**
	 * Initializes View
	 * @param x		x coordinate of view (in parent space)
	 * @param y		y coordinate of view (in parent space)
	 * @param w		width of view (in parent space)
	 * @param h		height of view (in parent space)
	 */
	public GuiView(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * View position and dimensions in parent space
	 */
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	
	/*public int getPosX() {
		
	}*/
	
	/**
	 * Converts x coordinate in parent space to local space
	 * @param x		in parent space
	 * @return x	in local space
	 */
	public int convertXtoLocal(int x) {
		return x - this.x;
	}
	
	/**
	 * Converts y coordinate in parent space to local space
	 * @param y		in parent space
	 * @return y	in local space
	 */
	public int convertYtoLocal(int y) {
		return y - this.y;
	}
	
	/**
	 * Every view must have a parent view
	 */
	protected GuiView parent;
	
	/**
	 * Get the parent view
	 * @return
	 */
	public GuiView getParentView() {
		return parent;
	}
	
	/**
	 * Set the parent view
	 */
	public void setParentView(GuiView p) {
		parent = p;
	}
	
	/**
	 * Converts x in local space to global screen space
	 * @param x		in local space
	 * @return x	in global screen space
	 */
	public int convertXtoScreen(int x) {
		return parent.convertXtoScreen(this.x) + x;
	}
	
	/**
	 * Converts y in local space to global screen space
	 * @param y		in local space
	 * @return y	in global screen space
	 */
	public int convertYtoScreen(int y) {
		return parent.convertYtoScreen(this.y) + y;
	}
	
	/**
	 * Draws itself and all subviews (if any)
	 * @param gui
	 * @param mouseX
	 * @param mouseY
	 */
	public abstract void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY);
	
	/**
	 * Draws all foreground stuff of itself and all subviews (if any)
	 * @param gui
	 * @param mouseX
	 * @param mouseY
	 */
	public abstract void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY);
	
	/**
	 * Draws all hover effects of itself and all subviews (if any)
	 * @param gui
	 * @param mouseX
	 * @param mouseY
	 */
	public abstract void drawHoverBoxes(GuiContainerViewContainer gui, int mouseX, int mouseY);
	
	/**
	 * Returns true if point is within view
	 * @param x		in parent space
	 * @param y		in parent space
	 * @return
	 */
	public boolean inRect(int x, int y) {
		return (	this.x <= x && x <= this.x + w
				 &&	this.y <= y && y <= this.y + h);
	}
	
	/**
	 * Fired when mouse button is pushed down (only once)
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 */
	public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}
	
	/**
	 * Fired when mouse button is released
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 */
	public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}
	
	/**
	 * Fired when mouse is moved while button is pressed
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 * @param timeSinceClick
	 */
	public void onMouseDragged(int mouseX, int mouseY, int mouseButton, long timeSinceClick) {}
}
