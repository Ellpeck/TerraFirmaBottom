package de.ellpeck.tfb.world;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;
import de.ellpeck.tfb.tiles.Tiles;

import java.util.Collections;
import java.util.Set;

public class ClayGenerator extends WorldGenOre {

    @Override
    protected int getHighestGridPos() {
        return 10;
    }

    @Override
    protected int getMaxAmount() {
        return 10;
    }

    @Override
    protected int getClusterRadiusX() {
        return 8;
    }

    @Override
    protected int getClusterRadiusY() {
        return 5;
    }

    @Override
    protected TileState getOreState() {
        return Tiles.CLAY.getDefState();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    protected Set<TileState> getAllwedTiles() {
        return Collections.singleton(GameContent.TILE_SOIL.getDefState());
    }
}
