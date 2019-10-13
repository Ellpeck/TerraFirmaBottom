package de.ellpeck.tfb.gui.container;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.ItemVessel;
import de.ellpeck.tfb.items.VesselInventory;

public class ContainerVessel extends ItemContainer {

    private final VesselInventory inventory;
    private final ItemInstance instance;

    public ContainerVessel(AbstractEntityPlayer player, ItemInstance instance) {
        super(player);
        this.instance = instance;
        this.inventory = ItemVessel.loadInventory(instance);

        this.addProtectedPlayerInv(player, 0, 50);
        this.addSlotGrid(this.inventory.items, 0, this.inventory.items.getSlotAmount(), 51, 0, 2);
    }


    @Override
    public void onClosed() {
        ItemVessel.saveInventory(this.inventory, this.instance);
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("vessel");
    }

    private void addProtectedPlayerInv(AbstractEntityPlayer player, int x, int y) {
        this.addPlayerSlotGrid(player, 0, 8, x, y, 8);
        this.addPlayerSlotGrid(player, 8, player.getInv().getSlotAmount(), x, y + 20, 8);
    }

    private void addPlayerSlotGrid(AbstractEntityPlayer player, int start, int end, int xStart, int yStart, int width) {
        var x = xStart;
        var y = yStart;
        for (var i = start; i < end; i++) {
            if (i == player.getSelectedSlot()) {
                this.addSlot(new ImmovableSlot(player.getInv(), i, x, y));
            } else {
                this.addSlot(new ContainerSlot(player.getInv(), i, x, y));
            }

            x += 17;
            if ((i + 1) % width == 0) {
                y += 17;
                x = xStart;
            }
        }
    }
}
