package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.tiles.entities.TileEntityPlacedItems;
import de.ellpeck.tfb.tiles.renderers.TilePlacedItemsRenderer;

public class TilePlacedItems extends TileBasic {
    public TilePlacedItems(ResourceName name) {
        super(name);
    }

    @Override
    protected boolean hasItem() {
        return false;
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityPlacedItems(world, x, y, layer);
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new TilePlacedItemsRenderer(name);
    }

    @Override
    public boolean canBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, boolean isRightTool) {
        return false;
    }

    @Override
    public boolean canPlaceInLayer(TileLayer layer) {
        return layer == TileLayer.MAIN;
    }

    @Override
    public boolean isFullTile() {
        return false;
    }

    @Override
    public BoundBox getBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
        return null;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        var instance = entity.remove();
        if (instance == null)
            return false;
        if (world.isClient())
            return true;
        AbstractEntityItem.spawn(world, instance, x + Util.RANDOM.nextFloat(), y + 0.5, 0, 0);
        return true;
    }

    public static boolean place(IWorld world, int x, int y, ItemInstance instance) {
        var state = world.getState(x, y);
        if (state.getTile() != Tiles.PLACED_ITEMS) {
            if (!Tiles.PLACED_ITEMS.canPlace(world, x, y, TileLayer.MAIN, null))
                return false;
            world.setState(x, y, Tiles.PLACED_ITEMS.getDefState());

            if (world.isClient())
                return true;
        }

        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        return entity.add(instance);
    }
}
