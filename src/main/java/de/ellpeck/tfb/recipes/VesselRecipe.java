package de.ellpeck.tfb.recipes;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.ItemVessel;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.ores.IOreItem;
import de.ellpeck.tfb.ores.MetalType;

import java.util.HashMap;

public class VesselRecipe extends PitKilnRecipe {
    public VesselRecipe() {
        super(TFB.createRes("vessel_smelting"), null, null);
        REGISTRY.register(this.name, this);
    }

    @Override
    public boolean matches(ItemInstance input) {
        return input.getItem() == Items.VESSEL;
    }

    @Override
    public ItemInstance getOutput(ItemInstance input) {
        var inventory = ItemVessel.loadInventory(input);

        var amounts = new HashMap<MetalType, Counter>();
        for (var instance : inventory.items) {
            if (instance == null)
                continue;
            var item = instance.getItem();
            if (!(item instanceof IOreItem))
                return input;
            var ore = (IOreItem) item;
            var counter = amounts.computeIfAbsent(ore.getType(instance), type -> new Counter(0));
            counter.add(ore.getAmount(instance));
        }

        if (amounts.size() == 1) {
            var entry = amounts.entrySet().iterator().next();
            inventory.metal = entry.getKey();
            inventory.metalAmount = entry.getValue().get();
        }

        for (var i = 0; i < inventory.items.getSlotAmount(); i++)
            inventory.items.set(i, null);

        ItemVessel.saveInventory(inventory, input);
        return input;
    }
}
