package de.ellpeck.tfb.mechanics.knapping;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.ItemInteractEvent;
import de.ellpeck.tfb.mechanics.knapping.gui.ContainerKnapping;
import de.ellpeck.tfb.mechanics.knapping.gui.GuiKnapping;

public final class Knapping {

    public static void init() {
        new KnappingLoader().register();

        Registries.PACKET_REGISTRY.registerNextFree(PacketOpenKnapping.class);
        Registries.PACKET_REGISTRY.registerNextFree(PacketKnap.class);

        RockBottomAPI.getEventHandler().registerListener(ItemInteractEvent.class, (result, event) -> {
            if (event.instance.getItem() != GameContent.TILE_PEBBLES.getItem())
                return result;
            if (event.instance.getAmount() < 2)
                return result;

            event.player.openGuiContainer(new GuiKnapping(event.player), new ContainerKnapping(event.player));
            if (event.player.world.isClient())
                RockBottomAPI.getNet().sendToServer(new PacketOpenKnapping(event.player.getUniqueId()));
            return EventResult.CANCELLED;
        });
    }

}
