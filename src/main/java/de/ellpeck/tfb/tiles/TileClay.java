package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.tiles.renderers.TileClayRenderer;

import java.util.Collections;
import java.util.List;

public class TileClay extends TileBasic {

    public TileClay(ResourceName name) {
        super(name);
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new TileClayRenderer(name);
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player) {
        return false;
    }

    @Override
    public boolean canPlaceInLayer(TileLayer layer) {
        return false;
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        var amount = 2 + Util.RANDOM.nextInt(4);
        return Collections.singletonList(new ItemInstance(this, amount));
    }
}
