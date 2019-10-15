package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.tfb.ores.MetalType;

public interface ICast {

    MetalType getMetal(ItemInstance instance);

    void setMetal(ItemInstance instance, MetalType type);

    int getMetalAmount(ItemInstance instance);

    void setMetalAmount(ItemInstance instance, int amount);

    int getMaxMetalAmount(ItemInstance instance);
}
