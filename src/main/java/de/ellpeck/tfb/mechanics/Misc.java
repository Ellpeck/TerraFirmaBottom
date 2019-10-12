package de.ellpeck.tfb.mechanics;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.TileDropsEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.items.ToolProperties;

public final class Misc {

    public static void init() {
        RockBottomAPI.getEventHandler().registerListener(TileDropsEvent.class, (result, event) -> {
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
    }
}
