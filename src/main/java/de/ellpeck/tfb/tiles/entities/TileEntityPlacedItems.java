package de.ellpeck.tfb.tiles.entities;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;

public class TileEntityPlacedItems extends TileEntity {

    public final TileInventory items = new TileInventory(this, 2, Collections.emptyList());

    public TileEntityPlacedItems(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        this.items.addChangeCallback((inv, slot) -> this.sendToClients());
    }

    public boolean add(ItemInstance instance) {
        for (var i = 0; i < this.items.getSlotAmount(); i++) {
            var item = this.items.get(i);
            if (item != null)
                continue;

            if (!this.world.isClient())
                this.items.set(i, instance);
            return true;
        }
        return false;
    }

    public ItemInstance remove() {
        for (var i = this.items.getSlotAmount() - 1; i >= 0; i--) {
            var item = this.items.get(i);
            if (item == null)
                continue;
            if (!this.world.isClient())
                this.items.set(i, null);
            return item;
        }
        return null;
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        this.items.save(set);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        this.items.load(set);
    }
}
