package de.ellpeck.tfb.ores;

import de.ellpeck.rockbottom.api.item.ItemInstance;

public interface IOreItem {

    MetalType getType(ItemInstance instance);
}
