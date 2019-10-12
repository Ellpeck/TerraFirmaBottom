package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IUpdatingItem {

    void update(IWorld world, ItemInstance instance);
}
