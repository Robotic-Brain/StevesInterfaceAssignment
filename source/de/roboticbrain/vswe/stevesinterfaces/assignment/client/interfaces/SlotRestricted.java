package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * 
 * This Slot is intended for inventorys which overwrite isItemValidForSlot
 *
 */
public class SlotRestricted extends Slot {
	
	// Slot.slotIndex being private in superclass is ridiculous
	private final int slotId;
	
	public SlotRestricted(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
		slotId = id;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inventory.isItemValidForSlot(slotId, stack);
	}
	
}
