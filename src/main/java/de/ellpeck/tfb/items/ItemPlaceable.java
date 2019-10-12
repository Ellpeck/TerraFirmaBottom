package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.tiles.TilePlacedItems;

public class ItemPlaceable extends ItemBasic {
    public ItemPlaceable(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        if (TilePlacedItems.place(world, x, y, instance)) {
            player.getInv().set(player.getSelectedSlot(), null);
            return true;
        }
        return false;
    }
}
