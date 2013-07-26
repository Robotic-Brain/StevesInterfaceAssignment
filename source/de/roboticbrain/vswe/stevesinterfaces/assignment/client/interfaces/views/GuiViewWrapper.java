package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

/**
 * 
 * This View wraps around any normal view and takes care of screen space - to view space translation
 *
 */
public class GuiViewWrapper extends GuiView implements IViewContainer {
	
	/*private final int xOffset;
	private final int yOffset;*/
	
	/**
	 * Actual view
	 */
	private final ArrayList<GuiView> contentViews;

	public GuiViewWrapper(int x, int y, int w, int h) {
		//super(0, 0, w, h);
		super(x, y, w, h);
		
		/*xOffset = x;
		yOffset = y;*/
		
		contentViews = new ArrayList<GuiView>();
	}

	@Override
	public void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		//GL11.glTranslatef((float)xOffset, (float)yOffset, 0.0F);
		GL11.glTranslatef((float)x, (float)y, 0.0F);
		
		for (GuiView view : contentViews) {
			//view.drawBackground(gui, mouseX - xOffset, mouseY - yOffset);
			view.drawBackground(gui, convertXtoLocal(mouseX), convertYtoLocal(mouseY));
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		for (GuiView view : contentViews) {
			view.drawForeground(gui, convertXtoLocal(mouseX), convertYtoLocal(mouseY));
		}
	}

	@Override
	public void addView(GuiView view) {
		contentViews.add(view);
		view.setParentView(this);
	}

	@Override
	public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
		for (GuiView view : contentViews) {
			if (view.inRect(convertXtoLocal(mouseX), convertYtoLocal(mouseY))) {
				view.onMousePressed(convertXtoLocal(mouseX), convertYtoLocal(mouseY), mouseButton);
			}
		}
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
		for (GuiView view : contentViews) {
			if (view.inRect(convertXtoLocal(mouseX), convertYtoLocal(mouseY))) {
				view.onMouseReleased(convertXtoLocal(mouseX), convertYtoLocal(mouseY), mouseButton);
			}
		}
	}

	@Override
	public void onMouseDragged(int mouseX, int mouseY, int mouseButton, long timeSinceClick) {
		// TODO might cause problems if dragging between views
		
		for (GuiView view : contentViews) {
			if (view.inRect(convertXtoLocal(mouseX), convertYtoLocal(mouseY))) {
				view.onMouseDragged(convertXtoLocal(mouseX), convertYtoLocal(mouseY), mouseButton, timeSinceClick);
			}
		}
	}
	
	/**
	 * Reimplemented this parent view is always null
	 */
	@Override
	public int convertXtoScreen(int x) {
		return x;
	}
	
	/**
	 * Reimplemented this parent view is always null
	 */
	@Override
	public int convertYtoScreen(int y) {
		return y;
	}

	@Override
	public void drawHoverBoxes(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		for (GuiView view : contentViews) {
			view.drawHoverBoxes(gui, convertXtoLocal(mouseX), convertYtoLocal(mouseY));
		}
	}
}
