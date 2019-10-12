package de.ellpeck.tfb.packets;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.tfb.gui.container.ContainerKnapping;
import de.ellpeck.tfb.recipes.KnappingType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketOpenKnapping implements IPacket {

    private UUID playerId;
    private KnappingType type;

    public PacketOpenKnapping(UUID playerId, KnappingType type) {
        this.playerId = playerId;
        this.type = type;
    }

    public PacketOpenKnapping() {
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeLong(this.playerId.getMostSignificantBits());
        buf.writeLong(this.playerId.getLeastSignificantBits());
        buf.writeInt(this.type.ordinal());
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        this.playerId = new UUID(buf.readLong(), buf.readLong());
        this.type = KnappingType.values()[buf.readInt()];
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        var player = game.getWorld().getPlayer(this.playerId);
        player.openContainer(new ContainerKnapping(player, this.type));
    }
}
