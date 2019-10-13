package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.ores.MetalType;

public final class Tiles {

    public static final Tile CLAY = new TileClay(TFB.createRes("clay")).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().register().setMaxAmount(50);
    public static final Tile STICK = new TileStick(TFB.createRes("stick")).setHardness(0).setForceDrop().register();
    public static final Tile PLACED_ITEMS = new TilePlacedItems(TFB.createRes("placed_items")).register();

    public static final Tile ZINC_ORE = new TileOre(TFB.createRes("zinc_ore"), MetalType.ZINC).setHardness(80).register().setMaxAmount(16);
    public static final Tile BISMUTH_ORE = new TileOre(TFB.createRes("bismuth_ore"), MetalType.BISMUTH).setHardness(90).register().setMaxAmount(16);
    public static final Tile COPPER_ORE = new TileOre(TFB.createRes("copper_ore"), MetalType.COPPER).setHardness(80).register().setMaxAmount(16);
}
