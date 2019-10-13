package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.items.ItemOre;
import de.ellpeck.tfb.ores.OreRichness;
import de.ellpeck.tfb.ores.MetalType;
import de.ellpeck.tfb.tiles.renderers.TileOreRenderer;

import java.util.Collections;
import java.util.List;

public class TileOre extends TileBasic {

    public static final EnumProp<OreRichness> RICHNESS = new EnumProp<>("richness", OreRichness.REGULAR, OreRichness.class);
    private final MetalType type;

    public TileOre(ResourceName name, MetalType type) {
        super(name);
        this.type = type;

        this.addProps(RICHNESS);
        this.addEffectiveTool(ToolProperty.PICKAXE, 1);
    }

    @Override
    protected ItemTile createItemTile() {
        return new ItemOre(this.name, this.type);
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new TileOreRenderer(name);
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
    public BoundBox getBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
        var richness = state.get(RICHNESS);
        return richness == OreRichness.SMALL ? null : super.getBoundBox(world, state, x, y, layer);
    }

    @Override
    public boolean obscuresBackground(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness != OreRichness.SMALL;
    }

    @Override
    public boolean hasSolidSurface(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness != OreRichness.SMALL;
    }

    @Override
    public float getHardness(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness == OreRichness.SMALL ? 1 : super.getHardness(world, x, y, layer);
    }

    @Override
    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        super.onDestroyed(world, x, y, destroyer, layer, shouldDrop || richness == OreRichness.SMALL);
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        var state = world.getState(layer, x, y);
        var meta = state.get(RICHNESS).ordinal();
        return Collections.singletonList(new ItemInstance(this, 1, meta));
    }

    @Override
    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness != OreRichness.SMALL || world.getState(layer, x, y - 1).getTile().hasSolidSurface(world, x, y - 1, layer);
    }

}
