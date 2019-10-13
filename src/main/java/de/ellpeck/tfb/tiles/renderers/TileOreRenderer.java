package de.ellpeck.tfb.tiles.renderers;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.ores.OreRichness;
import de.ellpeck.tfb.tiles.TileOre;

public class TileOreRenderer extends DefaultTileRenderer<TileOre> {

    public TileOreRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileOre tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        var richness = state.get(TileOre.RICHNESS);
        if (richness == OreRichness.SMALL) {
            manager.getTexture(this.texture.addSuffix("_small")).draw(renderX, renderY, scale, scale, light);
        } else {
            super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        }
    }
}
