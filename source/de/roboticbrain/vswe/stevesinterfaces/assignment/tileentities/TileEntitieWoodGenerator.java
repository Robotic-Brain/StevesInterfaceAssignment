package de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities;

import com.google.common.collect.ComputationException;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntitieWoodGenerator extends TileEntity implements IInventory {
	
	/**
	 * items[0] is bone meal slot
	 * items[1-9] are output slots
	 */
	private ItemStack[] items;
	
	/**
	 * the currently selected wood type
	 */
	private byte woodType;
	
	/**
	 * Number of abstract ticks needed to produce 1 wood
	 * (equals minecraft ticks without bone meal)
	 */
	private static final int TICKS_TILL_DONE = 200;
	
	/**
	 * Amount of ticks added per MC tick if bone meal is used
	 */
	private static final int BONE_MEAL_AMPLIFIER = 20;
	
	/**
	 * timer counting down, spawns wood when reaches exactly 0
	 */
	private int timer;
	
	public TileEntitieWoodGenerator() {
		items = new ItemStack[10];
		timer = TICKS_TILL_DONE;
	}
	
	@Override
	public int getSizeInventory() {
		return items.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}
	
	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack stack = getStackInSlot(i);
		
		if (stack != null) {
			if (stack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				stack = stack.splitStack(count);
				onInventoryChanged();
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack result = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return result;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		items[i] = itemstack;
	}
	
	@Override
	public String getInvName() {
		return "InventoryWoodGenerator";
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}
	
	@Override
	public void openChest() {}
	
	@Override
	public void closeChest() {}
	
	/**
	 * rejects everything except bone meal in slot 0
	 */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 0 && itemstack.getItem() == Item.dyePowder && itemstack.getItemDamage() == 15;
	}
	
	/**
	 * NBT Tags used to store inventory
	 */
	private static final String NBT_SLOT_ID = "Slot";
	private static final String NBT_INVENTORY = "Inventory";
	
	/**
	 * Current Wood Type
	 */
	private static final String NBT_WOOD_TYPE = "Wood";
	/**
	 * Current Timer Value
	 */
	private static final String NBT_TIMER_VAL = "Timer";
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList stackList = new NBTTagList();
		
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack currStack = getStackInSlot(i);
			
			if (currStack != null) {
				NBTTagCompound slot = new NBTTagCompound();
				slot.setByte(NBT_SLOT_ID, (byte)i);
				currStack.writeToNBT(slot);
				
				stackList.appendTag(slot);
			}
		}
		
		compound.setTag(NBT_INVENTORY, stackList);
		compound.setByte(NBT_WOOD_TYPE, woodType);
		compound.setInteger(NBT_TIMER_VAL, timer);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList stackList = compound.getTagList(NBT_INVENTORY);
		for (int i = 0; i < stackList.tagCount(); i++) {
			NBTTagCompound slot = (NBTTagCompound)stackList.tagAt(i);
			
			int slotID = slot.getByte(NBT_SLOT_ID);
			if (slotID >= 0 && slotID < getSizeInventory()) {
				setInventorySlotContents(slotID, ItemStack.loadItemStackFromNBT(slot));
			}
		}
		
		woodType = compound.getByte(NBT_WOOD_TYPE);
		timer = compound.getInteger(NBT_TIMER_VAL);
	}
	
	/**
	 * Adds A new piece of wood depending on current wood type
	 * 
	 * ( basicaly same as Container.mergeItemStack )
	 * 
	 * @param apply 	if set to false only simulate result
	 * @return 			false if inventory full
	 */
	private boolean addWood(boolean apply) {
		ItemStack newItem = new ItemStack(Block.wood, 1, woodType);
		
		for (int i = 1; i < items.length; i++) {
			ItemStack stack = getStackInSlot(i);
			
			if ((stack != null && stack.stackSize > 0) && stack.stackSize < stack.getMaxStackSize()
						&& stack.itemID == newItem.itemID
						&& stack.getItemDamage() == newItem.getItemDamage()
						&& ItemStack.areItemStackTagsEqual(stack, newItem)
					  )
			{
				if (apply) {
					stack.stackSize++;
				}
				return true;
			}
		}
		
		for (int i = 1; i < items.length; i++) {
			ItemStack stack = getStackInSlot(i);
			
			if (stack == null || stack.stackSize == 0) {
				if (apply) {
					setInventorySlotContents(i, newItem);
				}
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && addWood(false)) {
			ItemStack stack = getStackInSlot(0);
			if (stack != null 
					&& stack.stackSize >= 1
					&& isItemValidForSlot(0, stack) /* only allow bone meal  */
					&& timer >= BONE_MEAL_AMPLIFIER
				)
			{
				// if bone meal present (and amplifier doesn't get wasted)
				// use one piece per tick
				decrStackSize(0, 1);
				timer -= BONE_MEAL_AMPLIFIER;
			} else {
				// normal tick
				timer--;
			}
			
			if (timer == 0) {
				addWood(true);
				timer = TICKS_TILL_DONE;
			} else if (timer < 0) {
				// should never happen but just to be save (e.g. corrupted save file)
				timer = TICKS_TILL_DONE;
			}
		}
	}
	
	/**
	 * Returns the current timer value
	 * @return 0 = done, getMaxTime() = just restarted
	 */
	public int getTimer() {
		return timer;
	}
	
	/**
	 * Sets the current timer value by making sure no impossible value is set
	 * @param val
	 */
	public void setTimer(int val) {
		if (0 <= val && val <= getMaxTime()) {
			timer = val;
		}
	}
	
	/**
	 * Returns maximum timer value
	 * @return
	 */
	public int getMaxTime() {
		return TICKS_TILL_DONE;
	}
	
	/**
	 * Get Current Wood Type
	 * @return
	 */
	public byte getWoodType() {
		return woodType;
	}
	
	/**
	 * Set Current Wood Type
	 * @param type
	 */
	public void setWoodType(int type) {
		woodType = (byte)type;
	}
}
