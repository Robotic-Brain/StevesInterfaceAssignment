package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;


/**
 * 
 * Renders an entity centered in view
 *
 */
//public class GuiViewEntityRender extends GuiView {
//	
//	private Entity entity;
//	
//	/**
//	 * @param e		Entity to display
//	 */
//	public GuiViewEntityRender(int x, int y, int w, int h, Entity e) {
//		super(x, y, w, h);
//		entity = e;
//	}
//	
//	private float rot;
//	
//	@Override
//	public void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
//		
//		GL11.glPushMatrix();
//		// Translate to origin of view
//		GL11.glTranslated((double)convertXtoScreen(0), (double)convertYtoScreen(0), 100.0);
//		// Translate to entity center
//		GL11.glTranslated(w / 2.0, h / 2.0, 0);
//		GL11.glScalef(-40, 40, 40);
//		
//		RenderHelper.enableStandardItemLighting();
//		
//		GL11.glColor4f(1, 1, 1, 1);
//		
//		GL11.glDisable(GL11.GL_CULL_FACE);
//		GL11.glRotatef(180, 0, 0, 1);
//		GL11.glRotatef(rot, 0, 1, 0);
//		GL11.glRotatef(10, 1, 0, 0);
//		
//		RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);
//		RenderHelper.disableStandardItemLighting();
//		
//		GL11.glEnable(GL11.GL_CULL_FACE);
//		
//		GL11.glPopMatrix();
//		
//		rot += 0.5;
//	}
//	
//	@Override
//	public void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
//	}
//	
//}
