package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.tfb.ores.MetalType;

public class VesselInventory {

    public final Inventory items = new Inventory(4);
    public MetalType metal;
    public int metalAmount;
}
