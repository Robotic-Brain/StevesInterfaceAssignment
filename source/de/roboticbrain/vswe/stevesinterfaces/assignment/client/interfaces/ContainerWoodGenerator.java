package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWoodGenerator extends Container {
	
	private TileEntitieWoodGenerator generator;
	
	public ContainerWoodGenerator(InventoryPlayer playerInv, TileEntitieWoodGenerator te) {
		generator = te;
		
		// Hotbar
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInv, i, i * 18 + 8, 194));
		}
		
		// Inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(playerInv, 9 + x + y * 9, 8 + x * 18, 136 + y * 18));
			}
		}
		
		// Bone Meal Slot
		addSlotToContainer(new SlotRestricted(generator, 0, 26, 35));
		
		// Output slots
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				// Since the TileEntity allows no items to go in the output slots SlotBlocked is not necessary here
				
				//addSlotToContainer(new SlotBlocked(generator, 1 + x + y * 3, 115 + x * 18, 17 + y * 18));
				addSlotToContainer(new SlotRestricted(generator, 1 + x + y * 3, 115 + x * 18, 17 + y * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return generator.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		Slot slot = getSlot(id);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (id >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			} else {
				// Move from Player to Machine
				if (!mergeItemStackAdvanced(stack, 36, 36 + generator.getSizeInventory(), false)) {
					return null;
				}
			}
			
			if (stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			
			slot.onPickupFromSlot(player, stack);
			
			return result;
		}
		
		return null;
	}
	
	/**
	 * see mergeItemStack
	 * - copied from vanilla
	 * this version does not ignore isItemValid
	 */
	protected boolean mergeItemStackAdvanced(ItemStack stackToMerge, int fromIndex, int toIndex, boolean reverse) {
		boolean didPartiallyMerge = false;
        int k = fromIndex;

        if (reverse)
        {
            k = toIndex - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stackToMerge.isStackable())
        {
            while (stackToMerge.stackSize > 0 && (!reverse && k < toIndex || reverse && k >= fromIndex))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (slot.isItemValid(stackToMerge) && itemstack1 != null && itemstack1.itemID == stackToMerge.itemID && (!stackToMerge.getHasSubtypes() || stackToMerge.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stackToMerge, itemstack1))
                {
                    int l = itemstack1.stackSize + stackToMerge.stackSize;

                    if (l <= stackToMerge.getMaxStackSize())
                    {
                        stackToMerge.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        didPartiallyMerge = true;
                    }
                    else if (itemstack1.stackSize < stackToMerge.getMaxStackSize())
                    {
                        stackToMerge.stackSize -= stackToMerge.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stackToMerge.getMaxStackSize();
                        slot.onSlotChanged();
                        didPartiallyMerge = true;
                    }
                }

                if (reverse)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (stackToMerge.stackSize > 0)
        {
            if (reverse)
            {
                k = toIndex - 1;
            }
            else
            {
                k = fromIndex;
            }

            while (!reverse && k < toIndex || reverse && k >= fromIndex)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null && slot.isItemValid(stackToMerge))
                {
                    slot.putStack(stackToMerge.copy());
                    slot.onSlotChanged();
                    stackToMerge.stackSize = 0;
                    didPartiallyMerge = true;
                    break;
                }

                if (reverse)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return didPartiallyMerge;
	}
	
	/**
	 * progressBarUpdate value id of timer
	 */
	private static final short PROGRESS_ID = 0;
	/**
	 * progressBarUpdate value id of wood type
	 */
	private static final short WOOD_TYPE_ID = 1;
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, PROGRESS_ID, generator.getTimer());
		player.sendProgressBarUpdate(this, WOOD_TYPE_ID, generator.getWoodType());
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		// shorts here!
		switch (id) {
			case PROGRESS_ID:
				generator.setTimer(data);
				//System.out.println("Set Data: " + data);
				break;
			
			case WOOD_TYPE_ID:
				generator.setWoodType(data);
				break;
		}
	}
	
	private int oldTime;
	private int oldWoodType;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (Object player : crafters) {
			if (oldTime != generator.getTimer()) {
				((ICrafting)player).sendProgressBarUpdate(this, PROGRESS_ID, generator.getTimer());
			}
			
			if (oldWoodType != generator.getWoodType()) {
				((ICrafting)player).sendProgressBarUpdate(this, WOOD_TYPE_ID, generator.getWoodType());
			}
		}
		
		oldTime = generator.getTimer();
		oldWoodType = generator.getWoodType();
	}
	
	/**
	 * returns the generator
	 * @return
	 */
	public TileEntitieWoodGenerator getTileEntity() {
		return generator;
	}
	
}
