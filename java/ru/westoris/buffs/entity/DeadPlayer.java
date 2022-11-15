package ru.westoris.buffs.entity;

import net.minecraft.entity.Entity;
//import com.bioxx.tfc.Core.TFC_Core;
//import com.bioxx.tfc.Items.ItemTFCArmor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ru.westoris.buffs.entity.DeadPlayerContainer;
import ru.westoris.buffs.entity.RenderDeadPlayer;

public class DeadPlayer extends EntityMob
{
    private static final int numRows = 0;
    private static int defaultArmorLength = 4;
    // private static int defaultEquipableLength = TFC_Core.getExtraEquipInventorySize();
    private void inventory(IInventory p_71007_1_ ) {}
    private String PlayerName;
    private ItemStack[] armor;
    private ItemStack[] equipable;
    private float rotation;
    private Object inventorySlots;
    private double x,y,z; //coordinates of dead player


    public DeadPlayer(EntityPlayer player, World world)
    {
        super(world);
        this.entityInit();
        this.x=player.posX;
        this.y=player.posY;
        this.z=player.posZ;
        this.setSize(2f,1f);
        //this.PlayerName = playername;
        //noClip = false;
        this.armor = new ItemStack[defaultArmorLength];
        // this.equipable = new ItemStack[defaultEquipableLength];

        //this.setArmor(0, new ItemStack(TFCItems.SteelBoots,1,0));

    }
    public IInventory getInventory(World world, double x, double y, double z)
    {
        int posx = (int) x;
        int posy = (int) y;
        int posz = (int) z;
        Object object = world.getTileEntity(posx,posy,posz);

        if (object == null)
            return null;

        else
            return (IInventory)object;

    }

    @Override
    protected void updateAITasks()
    {
        ++this.entityAge;
    }

    @Override
    public boolean isEntityInvulnerable(){
        return true;
    }

    @Override
    protected void updateEntityActionState()
    {
        this.moveStrafing = 0.0F;
        this.moveForward = 0.0F;
        //this.despawnEntity();
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        int start = 20;

        for(int i = 0; i < defaultArmorLength;i++){
            this.dataWatcher.addObjectByDataType(start+i, 5);
        }
        //for(int i = 0; i < defaultEquipableLength;i++){
        //    this.dataWatcher.addObjectByDataType(start+i+defaultArmorLength,5);
        //}
        // this.dataWatcher.addObject(start + DeadPlayer.defaultEquipableLength + DeadPlayer.defaultArmorLength, new Float(1));
        // this.dataWatcher.addObject(start + DeadPlayer.defaultEquipableLength + DeadPlayer.defaultArmorLength + 1, Integer.valueOf(0));
    }

    @Override
    public boolean canBePushed(){
        return true;
    }

    //@Override
    //public boolean canDespawn(){
    //    return false;
    //}

    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        this.syncData();
    }

    public void syncData()
    {
        if(this.dataWatcher!= null)
        {
            if(this.worldObj.isRemote)
            {
                int start = 20;
                for(int i = 0; i < defaultArmorLength;i++){
                    this.armor[i] = this.dataWatcher.getWatchableObjectItemStack(start+i);
                }
                //for(int i = 0; i < defaultEquipableLength;i++){
                //    this.equipable[i] = this.dataWatcher.getWatchableObjectItemStack(start+i+defaultArmorLength);
                //}
                //this.rotation = this.dataWatcher.getWatchableObjectFloat(start + DeadPlayer.defaultEquipableLength + DeadPlayer.defaultArmorLength);

            }
            else
            {
                int start = 20;

                for(int i = 0; i < defaultArmorLength;i++){
                    this.dataWatcher.updateObject(start+i, this.armor[i]);
                }
                //for(int i = 0; i < defaultEquipableLength;i++){
                //     this.dataWatcher.updateObject(start+i+defaultArmorLength, this.equipable[i]);
                //}

                //this.dataWatcher.updateObject(start + DeadPlayer.defaultEquipableLength + DeadPlayer.defaultArmorLength, this.rotation);

            }
        }
    }

    @Override
    public void onUpdate()
    {
        if(this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox).size() >1){
            this.setDead();
        }

        double tempX, tempZ;    //temp x,y,z
        tempX = this.posX;
        //t_y = this.posY;
        tempZ = this.posZ;
        super.onUpdate();
        if(this.worldObj.isRemote) {
            this.setSize(1f,2f);
        } else {
            this.setSize(0.1F,2);
        }

        this.setLocationAndAngles(tempX, this.posY, tempZ, this.rotation, 0F);
        //this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotation, 90F);
        this.setRotation(this.rotation-90, 0);
        this.renderYawOffset = this.rotation;
        this.field_70741_aB = this.rotation;
        this.field_70764_aw = this.rotation;
        this.isCollidedHorizontally = true;
        this.limbSwing = 0;
        this.limbSwingAmount = 0;
        this.newRotationYaw = this.rotation;
        this.swingProgress = 0;
        this.swingProgressInt = 0;
        //this.renderYawOffset = 0;
        //this.rotationYaw = rotation;
        //this.getLookHelper().setLookPosition(this.posX + lookX, this.posY + (double)this.getEyeHeight(), this.posZ + lookZ, 10.0F, (float)this.getVerticalFaceSpeed());
        //this.getNavigator().tryMoveToXYZ(this.posX + lookX, this.posY, this.posZ + lookZ, 0.5);
    }


    private boolean mergeItemStack(ItemStack itemstack1, int i, int j, boolean b) {
        // TODO Auto-generated method stub
        return false;
    }

    //@Override
    @Override
    public boolean interact(EntityPlayer ep){
        if(!this.worldObj.isRemote){
            ItemStack is = ep.getCurrentEquippedItem();
            if(is == null){
                IInventory iinventory = this.getInventory(this.worldObj, this.x, this.y, this.z);
                if (iinventory != null)
                {
                    ep.displayGUIChest(iinventory);
                }

                return true;
            }
            //ep.setCurrentItemOrArmor(0, is);
            //}
        }
        else {
            Vec3 hitVec = this.getPlayerLook(ep, 1.0f);
            double angleTan = hitVec.yCoord / Math.sqrt(hitVec.xCoord * hitVec.xCoord + hitVec.zCoord * hitVec.zCoord);

            double xzDist = Math.sqrt(Math.pow(ep.posX - this.posX,2) + Math.pow(ep.posZ - this.posZ, 2));
            double yLevel = angleTan * xzDist + ep.eyeHeight + ep.posY;
            double y = yLevel - this.posY;

            int slot = -1;
            if(y >= 0 && y < 0.3){
                slot = 0;
            }
            else if(y >=0.3 && y < 1){
                slot = 1;
            }
            else if(y >= 1 && y < 1.4){
                slot = 2;
            }
            else if(y >= 1.4 && y < 2){
                slot = 3;
            }


            if(slot != -1){
                ItemStack i = this.armor[slot];
                this.armor[slot] = null;
                this.giveItemToPlayer(this.worldObj,ep,i);
            }
        }
        //}
        return true;
    }

    private Vec3 getPlayerLook(EntityPlayer ep, float f) {
        // TODO Auto-generated method stub
        return null;
    }
    public void giveItemToPlayer(World world, EntityPlayer ep, ItemStack is){
        if(world != null && ep != null && is != null){
            is.stackSize = 1;
            EntityItem ei = new EntityItem(world,ep.posX,ep.posY,ep.posZ,is);
            world.spawnEntityInWorld(ei);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        //posX = nbttagcompound.getDouble("X");
        //posY = nbttagcompound.getDouble("Y");
        //posZ = nbttagcompound.getDouble("Z");
        this.rotation = nbttagcompound.getFloat("Rotation");
        //woodType = nbttagcompound.getInteger("Wood");

        NBTTagList nbttaglist;
        int i;

        if (nbttagcompound.hasKey("Armor",9))
        {
            nbttaglist = nbttagcompound.getTagList("Armor",10);

            for (i = 0; i < DeadPlayer.defaultArmorLength; ++i)
            {
                this.armor[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
            }
        }

        if (nbttagcompound.hasKey("Equipable",9))
        {
            nbttaglist = nbttagcompound.getTagList("Equipable",10);

            // for (i = 0; i < defaultEquipableLength; ++i)
            // {
            //     this.equipable[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
            // }
        }
    }

    public float getRotation(){
        return this.rotation;
    }

    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        TileEntityChest tec = new TileEntityChest();
        return tec;
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        //nbttagcompound.setDouble("X", posX);
        //nbttagcompound.setDouble("Y", posY);
        //nbttagcompound.setDouble("Z", posZ);

        nbttagcompound.setFloat("Rotation", this.rotation);
        //nbttagcompound.setInteger("Wood",woodType);

        NBTTagList nbttaglist = new NBTTagList();
        NBTTagCompound nbttagcompound1;

        for (int i = 0; i < DeadPlayer.defaultArmorLength; ++i)
        {
            nbttagcompound1 = new NBTTagCompound();

            if (this.armor[i] != null)
            {
                this.armor[i].writeToNBT(nbttagcompound1);
            }

            nbttaglist.appendTag(nbttagcompound1);
        }

        nbttagcompound.setTag("Armor", nbttaglist);

        nbttaglist = new NBTTagList();
        //for (int i = 0; i < defaultEquipableLength; ++i)
        //{
        //  nbttagcompound1 = new NBTTagCompound();

        // if (this.equipable[i] != null)
        // {
        //   this.equipable[i].writeToNBT(nbttagcompound1);
        //}

        //nbttaglist.appendTag(nbttagcompound1);
        //}

        nbttagcompound.setTag("Equipable", nbttaglist);
    }

    @Override
    public ItemStack getHeldItem()
    {
        return null;
    }

    public ItemStack getEquipableInSlot(int i)
    {

        //   if(this.equipable != null && defaultEquipableLength > i)
        //       return this.equipable[i];
        return null;
    }

    public ItemStack getArmorInSlot(int i)
    {
        if(this.armor != null && defaultArmorLength > i)
            return this.armor[i];
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int p_71124_1_) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
        // TODO Auto-generated method stub

    }
    @Override
    public ItemStack[] getLastActiveItems() {
        // TODO Auto-generated method stub
        return null;
    }




    /*  public void setArmor(int i, ItemStack itemstack)
    {
        if(armor != null && defaultArmorLength > i){
            armor[i] = itemstack;
            //return true;
        }
        //return false;
    }

    public void setEquipable(int i, ItemStack itemstack)
    {
        if(equipable != null && defaultEquipableLength > i){
            equipable[i] = itemstack;
            //return true;
        }
        //return false;
    } */
}
