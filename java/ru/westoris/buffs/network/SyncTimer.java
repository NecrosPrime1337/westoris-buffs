package ru.westoris.buffs.network;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import ru.westoris.buffs.events.BuffsEvents;
import ru.westoris.buffs.network.AbstractMessage.AbstractClientMessage;
import ru.westoris.buffs.player.BuffsPlayer;

public class SyncTimer extends AbstractClientMessage<SyncTimer> {

    private int count;

    public SyncTimer() {}

    public SyncTimer(int count) {

        this.count = count;
    }

    @Override
    protected void writeData(PacketBuffer buffer) throws IOException {

        buffer.writeInt(this.count);
    }

    @Override
    protected void readData(PacketBuffer buffer) throws IOException {

        this.count = buffer.readInt();
    }

    @Override
    public void performProcess(EntityPlayer player, Side side) {
        //BuffsEvents.counter = this.count;
    }
}
