package de.ellpeck.tfb.mechanics.knapping;

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
            for (var type : KnappingType.values()) {
                if (event.instance.getItem() != type.item)
                    continue;
                if (event.instance.getAmount() < type.requiredAmount)
                    continue;

                event.player.openGuiContainer(new GuiKnapping(event.player, type), new ContainerKnapping(event.player, type));
                if (event.player.world.isClient())
                    RockBottomAPI.getNet().sendToServer(new PacketOpenKnapping(event.player.getUniqueId(), type));
                return EventResult.CANCELLED;
            }
            return result;
        });
    }

}
