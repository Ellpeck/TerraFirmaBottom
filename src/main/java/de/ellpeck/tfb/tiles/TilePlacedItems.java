package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
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
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);

        var held = player.getSelectedItem();
        if (held != null) {
            if (entity.light(player, held))
                return true;

            if (!entity.addStrawOrWood(held))
                return false;
            if (!world.isClient())
                player.getInv().remove(player.getSelectedSlot(), 1);
            return true;
        } else {
            var instance = entity.removeStorage();
            if (instance == null)
                return false;
            if (world.isClient())
                return true;
            AbstractEntityItem.spawn(world, instance, x + Util.RANDOM.nextFloat(), y + 0.5, 0, 0);
            return true;
        }
    }

    @Override
    public int getLight(IWorld world, int x, int y, TileLayer layer) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        return entity.isLit ? 25 : 0;
    }

    @Override
    public void updateRandomlyInPlayerView(IWorld world, int x, int y, TileLayer layer, TileState state, IParticleManager manager) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        if (entity.isLit) {
            for (var i = Util.RANDOM.nextInt(5); i >= 0; i--) {
                var particleX = x + Util.RANDOM.nextFloat();
                var particleY = y + Util.RANDOM.nextFloat();
                var scale = (1 + Util.RANDOM.nextFloat() * 2) * 0.15F;
                RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, particleX, particleY, 0, 0, scale);
            }
        }
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player) {
        return world.getState(x, y - 1).getTile().isFullTile();
    }

    @Override
    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        if (entity.getPitKilnFillPercentage() > 0) {
            return isInPit(world, x, y);
        } else {
            return world.getState(x, y - 1).getTile().isFullTile();
        }
    }

    @Override
    public BoundBox getBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        var percentage = entity.getPitKilnFillPercentage();
        return new BoundBox(0, 0, 1, percentage);
    }

    public static boolean place(IWorld world, int x, int y, ItemInstance instance) {
        var state = world.getState(x, y);
        if (state.getTile() != Tiles.PLACED_ITEMS) {
            if (!state.getTile().canReplace(world, x, y, TileLayer.MAIN) || !Tiles.PLACED_ITEMS.canPlace(world, x, y, TileLayer.MAIN, null))
                return false;
            world.setState(x, y, Tiles.PLACED_ITEMS.getDefState());

            if (world.isClient())
                return true;
        }

        var entity = world.getTileEntity(x, y, TileEntityPlacedItems.class);
        return entity.addStorage(instance);
    }

    public static boolean isInPit(IWorld world, int x, int y) {
        for (var dir : Direction.ADJACENT) {
            if (dir == Direction.UP)
                continue;
            var state = world.getState(x + dir.x, y + dir.y);
            if (!state.getTile().isFullTile())
                return false;
        }
        return true;
    }
}
