package de.ellpeck.tfb.items;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemTool;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.tfb.TFB;

public final class Items {

    public static final Item STONE_KNIFE_BLADE = new ItemBasic(TFB.createRes("stone_knife_blade")).setMaxAmount(4).register();
    public static final Item STONE_AXE_HEAD = new ItemBasic(TFB.createRes("stone_axe_head")).setMaxAmount(4).register();

    public static final Item STONE_KNIFE = new ItemTool(TFB.createRes("stone_knife"), 1, 100, ToolProperties.KNIFE, 1).register();
    public static final Item STONE_AXE = new ItemTool(TFB.createRes("stone_axe"), 1, 100, ToolProperty.AXE, 1).register();

    public static final Item CLAY_VESSEL = new ItemBasic(TFB.createRes("clay_vessel")).setMaxAmount(1).register();

    public static final Item STRAW = new ItemBasic(TFB.createRes("straw")).setMaxAmount(32).register();

}
