package de.ellpeck.tfb.tiles.entities;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.CombinedInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.tiles.TilePlacedItems;

import java.util.Collections;

public class TileEntityPlacedItems extends TileEntity {

    public final TileInventory inventory = new TileInventory(this, 2, Collections.emptyList());
    public final TileInventory pitKilnInventory = new TileInventory(this, 2, Collections.emptyList());
    private final CombinedInventory combinedInventory = new CombinedInventory(this.inventory, this.pitKilnInventory);

    public TileEntityPlacedItems(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        this.inventory.addChangeCallback((inv, slot) -> this.sendToClients());
        this.pitKilnInventory.addChangeCallback((inv, slot) -> this.sendToClients());
    }

    @Override
    public IFilteredInventory getTileInventory() {
        return this.combinedInventory;
    }

    public float getPitKilnFillPercentage() {
        var pitKilnAmount = 0;
        for (var item : this.pitKilnInventory) {
            if (item == null)
                continue;
            pitKilnAmount += item.getAmount();
        }
        return pitKilnAmount / 8F;
    }

    public boolean addStorage(ItemInstance instance) {
        for (var i = 0; i < this.inventory.getSlotAmount(); i++) {
            var item = this.inventory.get(i);
            if (item != null)
                continue;

            if (!this.world.isClient())
                this.inventory.set(i, instance);
            return true;
        }
        return false;
    }

    public boolean addStrawOrWood(ItemInstance instance) {
        if (!TilePlacedItems.isInPit(this.world, this.x, this.y))
            return false;

        var straw = this.pitKilnInventory.get(0);
        if (instance.getItem() == Items.STRAW) {
            if (straw != null && straw.getAmount() >= 4)
                return false;
            if (!this.world.isClient()) {
                if (straw == null) {
                    this.pitKilnInventory.set(0, instance.copy().setAmount(1));
                } else {
                    straw.addAmount(1);
                }
            }
            return true;
        } else if (RockBottomAPI.getResourceRegistry().getNames(instance).contains(GameContent.RES_WOOD_RAW)) {
            if (straw == null || straw.getAmount() < 4)
                return false;
            var wood = this.pitKilnInventory.get(1);
            if (wood != null && wood.getAmount() >= 4)
                return false;
            if (!this.world.isClient()) {
                if (wood == null) {
                    this.pitKilnInventory.set(1, instance.copy().setAmount(1));
                } else {
                    wood.addAmount(1);
                }
            }
            return true;
        }
        return false;
    }

    public ItemInstance removeStorage() {
        for (var i = this.inventory.getSlotAmount() - 1; i >= 0; i--) {
            var item = this.inventory.get(i);
            if (item == null)
                continue;
            if (!this.world.isClient()) {
                this.inventory.set(i, null);

                if (i == 0)
                    this.world.setState(this.x, this.y, GameContent.TILE_AIR.getDefState());
            }
            return item;
        }
        return null;
    }

    @Override
    public void update(IGameInstance game) {
        if (this.world.isClient())
            return;

    }

    @Override
    public boolean doesTick() {
        return true;
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        this.inventory.save(set);
        this.pitKilnInventory.save(set);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        this.inventory.load(set);
        this.pitKilnInventory.load(set);
    }
}
