package ru.westoris.buffs.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DeadPlayerContainer extends ContainerChest {

    private IInventory invPlayer;
    private int numRows;

    public DeadPlayerContainer(IInventory invDead, IInventory invPlayer) {
        super(invDead, invPlayer);
        this.invPlayer = invPlayer;
        // TODO Auto-generated constructor stub
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                    return null;
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
                return null;

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.invPlayer.closeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        // TODO Auto-generated method stub
        return this.invPlayer.isUseableByPlayer(p_75145_1_);
    }

}
