package de.ellpeck.tfb.items;

import com.google.common.base.Strings;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.renderers.ItemCastRenderer;
import de.ellpeck.tfb.ores.MetalType;

import java.util.List;

public class ItemCast extends ItemBasic implements ICast {

    public ItemCast(ResourceName name) {
        super(name);
        this.setMaxAmount(1);
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name) {
        return new ItemCastRenderer(name);
    }

    @Override
    public MetalType getMetal(ItemInstance instance) {
        if (!instance.hasAdditionalData())
            return null;
        var metal = instance.getAdditionalData().getString(TFB.createRes("metal"));
        if (Strings.isNullOrEmpty(metal))
            return null;
        return MetalType.byName(new ResourceName(metal));
    }

    @Override
    public void setMetal(ItemInstance instance, MetalType type) {
        var data = instance.getOrCreateAdditionalData();
        data.addString(TFB.createRes("metal"), type.name.toString());
    }

    @Override
    public int getMetalAmount(ItemInstance instance) {
        if (!instance.hasAdditionalData())
            return 0;
        return instance.getAdditionalData().getInt(TFB.createRes("amount"));
    }

    @Override
    public void setMetalAmount(ItemInstance instance, int amount) {
        var data = instance.getOrCreateAdditionalData();
        data.addInt(TFB.createRes("amount"), amount);
    }

    @Override
    public int getMaxMetalAmount(ItemInstance instance) {
        return 100;
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced, boolean isRealItem) {
        super.describeItem(manager, instance, desc, isAdvanced, isRealItem);

        var metal = this.getMetal(instance);
        if (metal == null)
            return;
        var amount = this.getMetalAmount(instance);
        var max = this.getMaxMetalAmount(instance);
        desc.add(FormattingCode.GRAY + metal.getDisplayName() + " x" + amount + " / " + max);
    }
}
