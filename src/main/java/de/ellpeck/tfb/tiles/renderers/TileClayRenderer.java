package de.ellpeck.tfb.tiles.renderers;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.tiles.TileClay;

public class TileClayRenderer extends DefaultTileRenderer<TileClay> {
    public TileClayRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, TileClay tile, ItemInstance instance, float x, float y, float scale, int filter) {
        manager.getTexture(this.texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
    }
}
