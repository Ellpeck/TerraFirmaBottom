package de.ellpeck.tfb.items;

import com.google.common.base.Strings;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.Events;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.gui.GuiVessel;
import de.ellpeck.tfb.gui.container.ContainerVessel;
import de.ellpeck.tfb.ores.MetalType;

import java.util.List;

public class ItemVessel extends ItemPlaceable implements IUpdatingItem {

    public ItemVessel(ResourceName name) {
        super(name);
    }

    public static VesselInventory loadInventory(ItemInstance instance) {
        var inventory = new VesselInventory();
        var data = instance.getAdditionalData();
        if (data != null) {
            var set = data.getDataSet(TFB.createRes("inventory"));
            if (!set.isEmpty()) {
                var metal = set.getString("metal");
                if (!Strings.isNullOrEmpty(metal)) {
                    inventory.metal = MetalType.byName(new ResourceName(metal));
                    inventory.metalAmount = set.getInt("metal_amount");
                } else {
                    inventory.items.load(set);
                }
            }
        }
        return inventory;
    }

    public static void saveInventory(VesselInventory inventory, ItemInstance instance) {
        var data = instance.getOrCreateAdditionalData();
        var set = new DataSet();
        if (inventory.metal != null) {
            set.addString("metal", inventory.metal.name.toString());
            set.addInt("metal_amount", inventory.metalAmount);
        } else {
            inventory.items.save(set);
        }
        data.addDataSet(TFB.createRes("inventory"), set);
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced, boolean isRealItem) {
        super.describeItem(manager, instance, desc, isAdvanced, isRealItem);
        var inv = loadInventory(instance);
        if (inv.metal != null) {
            desc.add(FormattingCode.GRAY + inv.metal.getDisplayName() + " x" + inv.metalAmount);
        } else {
            for (var item : inv.items) {
                if (item == null)
                    continue;
                desc.add(FormattingCode.GRAY + item.getDisplayName() + " x" + item.getAmount());
            }
        }
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        if (super.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance))
            return true;
        return player.openGuiContainer(new GuiVessel(player), new ContainerVessel(player));
    }

    @Override
    public void update(IWorld world, ItemInstance instance) {
        var inv = loadInventory(instance);
        Events.updateInventory(inv.items, world);
    }
}
