package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.Events;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.gui.GuiVessel;
import de.ellpeck.tfb.gui.container.ContainerVessel;

import java.util.List;

public class ItemVessel extends ItemPlaceable implements IUpdatingItem {

    public ItemVessel(ResourceName name) {
        super(name);
    }

    public static Inventory loadInventory(ItemInstance instance) {
        var inventory = new Inventory(4);
        var data = instance.getAdditionalData();
        if (data != null) {
            var set = data.getDataSet(TFB.createRes("inventory"));
            if (!set.isEmpty())
                inventory.load(set);
        }
        return inventory;
    }

    public static void saveInventory(Inventory inventory, ItemInstance instance) {
        var data = instance.getOrCreateAdditionalData();
        var set = new DataSet();
        inventory.save(set);
        data.addDataSet(TFB.createRes("inventory"), set);
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced, boolean isRealItem) {
        super.describeItem(manager, instance, desc, isAdvanced, isRealItem);
        var inv = loadInventory(instance);
        for (var item : inv) {
            if (item == null)
                continue;
            desc.add(FormattingCode.GRAY + item.getDisplayName() + " x" + item.getAmount());
        }
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return player.openGuiContainer(new GuiVessel(player), new ContainerVessel(player, instance));
    }

    @Override
    public void update(IWorld world, ItemInstance instance) {
        var inv = loadInventory(instance);
        Events.updateInventory(inv, world);
    }
}
