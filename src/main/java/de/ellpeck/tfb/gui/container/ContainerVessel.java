package de.ellpeck.tfb.gui.container;

import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.RestrictedSlot;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.ICast;
import de.ellpeck.tfb.items.ItemVessel;
import de.ellpeck.tfb.items.VesselInventory;

public class ContainerVessel extends ItemContainer {

    private final VesselInventory inventory;
    private final Inventory castSlot = new Inventory(1);

    public ContainerVessel(AbstractEntityPlayer player) {
        super(player);
        this.inventory = ItemVessel.loadInventory(player.getSelectedItem());

        this.addProtectedPlayerInv(player, 0, 50);

        if (this.inventory.metal == null) {
            this.addSlotGrid(this.inventory.items, 0, this.inventory.items.getSlotAmount(), 51, 0, 2);
        } else {
            this.addSlot(new RestrictedSlot(this.castSlot, 0, 59, 25, inst -> inst.getItem() instanceof ICast));
        }
    }

    public void updateCasting() {
        if (this.player.world.isClient())
            return;

        if (this.inventory.metalAmount <= 0)
            return;
        var inst = this.castSlot.get(0);
        if (inst == null)
            return;
        var cast = (ICast) inst.getItem();

        var metal = cast.getMetal(inst);
        if (metal == null) {
            cast.setMetal(inst, this.inventory.metal);
        } else if (metal != this.inventory.metal) {
            return;
        }

        var amount = cast.getMetalAmount(inst);
        if (amount >= cast.getMaxMetalAmount(inst))
            return;

        cast.setMetalAmount(inst, amount + 1);
        this.inventory.metalAmount--;
        if (this.inventory.metalAmount <= 0)
            this.inventory.metal = null;

        // so that it gets sent to the client
        ItemVessel.saveInventory(this.inventory, this.player.getSelectedItem());
    }

    @Override
    public void onClosed() {
        if (!this.player.world.isClient()) {
            ItemVessel.saveInventory(this.inventory, this.player.getSelectedItem());

            var cast = this.castSlot.get(0);
            if (cast != null)
                AbstractEntityItem.spawn(this.player.world, cast, this.player.getX(), this.player.getY(), 0, 0);
        }
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
