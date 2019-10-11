package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemTool;
import de.ellpeck.tfb.TFB;

public final class Items {

    public static final Item STONE_KNIFE_BLADE = new ItemBasic(TFB.createRes("stone_knife_blade")).register();
    public static final Item STONE_KNIFE = new ItemTool(TFB.createRes("stone_knife"), 1, 100, ToolProperties.KNIFE, 1).register();

}
