package de.ellpeck.tfb.mechanics.knapping;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.tfb.mechanics.knapping.gui.ContainerKnapping;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketOpenKnapping implements IPacket {

    private UUID playerId;

    public PacketOpenKnapping(UUID playerId) {
        this.playerId = playerId;
    }

    public PacketOpenKnapping() {
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeLong(this.playerId.getMostSignificantBits());
        buf.writeLong(this.playerId.getLeastSignificantBits());
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        this.playerId = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        var player = game.getWorld().getPlayer(this.playerId);
        player.openContainer(new ContainerKnapping(player));
    }
}
