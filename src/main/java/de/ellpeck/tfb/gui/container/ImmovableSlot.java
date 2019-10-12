package de.ellpeck.tfb.gui.container;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ImmovableSlot extends ContainerSlot {
    public ImmovableSlot(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean canPlace(ItemInstance instance) {
        return false;
    }

    @Override
    public boolean canRemove(int amount) {
        return false;
    }
}
