package de.ellpeck.tfb.tiles;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.tiles.renderers.TileOreRenderer;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TileOre extends TileBasic {

    public static final EnumProp<Richness> RICHNESS = new EnumProp<>("richness", Richness.REGULAR, Richness.class);

    public TileOre(ResourceName name) {
        super(name);
        this.addProps(RICHNESS);
        this.addEffectiveTool(ToolProperty.PICKAXE, 1);
    }

    @Override
    protected ItemTile createItemTile() {
        return new ItemTile(this.name) {
            @Override
            protected IItemRenderer createRenderer(ResourceName name) {
                return new DefaultItemRenderer(name);
            }

            @Override
            public String getLocalizedName(ItemInstance instance) {
                var name = super.getLocalizedName(instance);
                var richness = TFB.createRes(getItemRichness(instance).name().toLowerCase(Locale.ROOT));
                var richnessStrg = RockBottomAPI.getGame().getAssetManager().localize(richness);
                return richnessStrg + " " + name;
            }

            @Override
            public int getHighestPossibleMeta() {
                return Richness.values().length - 1;
            }
        };
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
        return richness == Richness.SMALL ? null : super.getBoundBox(world, state, x, y, layer);
    }

    @Override
    public boolean obscuresBackground(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness != Richness.SMALL;
    }

    @Override
    public boolean hasSolidSurface(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness != Richness.SMALL;
    }

    @Override
    public float getHardness(IWorld world, int x, int y, TileLayer layer) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        return richness == Richness.SMALL ? 1 : super.getHardness(world, x, y, layer);
    }

    @Override
    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        var richness = world.getState(layer, x, y).get(RICHNESS);
        super.onDestroyed(world, x, y, destroyer, layer, shouldDrop || richness == Richness.SMALL);
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
        return richness != Richness.SMALL || world.getState(layer, x, y - 1).getTile().hasSolidSurface(world, x, y - 1, layer);
    }

    public static Richness getItemRichness(ItemInstance instance) {
        return Richness.values()[instance.getMeta()];
    }

    public enum Richness {
        POOR,
        REGULAR,
        RICH,
        SMALL
    }
}
