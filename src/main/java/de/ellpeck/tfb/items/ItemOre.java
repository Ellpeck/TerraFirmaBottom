package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.ores.IOreItem;
import de.ellpeck.tfb.ores.OreRichness;
import de.ellpeck.tfb.ores.MetalType;

public class ItemOre extends ItemTile implements IOreItem {

    private final MetalType type;

    public ItemOre(ResourceName name, MetalType type) {
        super(name);
        this.type = type;
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name) {
        return new DefaultItemRenderer(name);
    }

    @Override
    public String getLocalizedName(ItemInstance instance) {
        var name = super.getLocalizedName(instance);
        var richness = getItemRichness(instance).name;
        var richnessStrg = RockBottomAPI.getGame().getAssetManager().localize(richness);
        return richnessStrg + " " + name;
    }

    @Override
    public int getHighestPossibleMeta() {
        return OreRichness.values().length - 1;
    }

    public static OreRichness getItemRichness(ItemInstance instance) {
        return OreRichness.values()[instance.getMeta()];
    }

    @Override
    public MetalType getType(ItemInstance instance) {
        return this.type;
    }
}
