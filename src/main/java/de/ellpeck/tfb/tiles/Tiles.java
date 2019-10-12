package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.tfb.TFB;

public final class Tiles {

    public static final Tile CLAY = new TileClay(TFB.createRes("clay")).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().register().setMaxAmount(50);

}
