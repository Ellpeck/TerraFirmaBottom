package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.ItemInteractEvent;
import de.ellpeck.rockbottom.api.event.impl.RecipeLearnEvent;
import de.ellpeck.rockbottom.api.event.impl.TileDropsEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.tfb.gui.ContainerKnapping;
import de.ellpeck.tfb.gui.GuiKnapping;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.items.ToolProperties;
import de.ellpeck.tfb.packets.PacketOpenKnapping;
import de.ellpeck.tfb.recipes.KnappingType;

public final class Events {

    public static void init() {
        var ev = RockBottomAPI.getEventHandler();
        ev.registerListener(ItemInteractEvent.class, (result, event) -> {
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
        ev.registerListener(TileDropsEvent.class, (result, event) -> {
            if (event.tile != GameContent.TILE_GRASS_TUFT)
                return result;
            if (!(event.destroyer instanceof AbstractEntityPlayer))
                return result;
            var player = (AbstractEntityPlayer) event.destroyer;
            var held = player.getInv().get(player.getSelectedSlot());
            if (held == null || !held.getItem().getToolProperties(held).containsKey(ToolProperties.KNIFE))
                return result;

            event.drops.clear();
            event.drops.add(new ItemInstance(Items.STRAW));
            return EventResult.MODIFIED;
        });
        ev.registerListener(RecipeLearnEvent.class, (result, event) -> {
            if ("rockbottom".equals(event.recipe.getName().getDomain()))
                return EventResult.CANCELLED;
            return result;
        });
    }
}
