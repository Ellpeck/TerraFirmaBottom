package de.ellpeck.tfb.tiles.renderers;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.tiles.TilePlacedItems;
import de.ellpeck.tfb.tiles.entities.TileEntityPlacedItems;

public class TilePlacedItemsRenderer extends DefaultTileRenderer<TilePlacedItems> {
    public TilePlacedItemsRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TilePlacedItems tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        var entity = world.getTileEntity(layer, x, y, TileEntityPlacedItems.class);
        for (var instance : entity.items) {
            if (instance == null)
                continue;
            var item = instance.getItem();
            var renderer = item.getRenderer();
            if (renderer != null)
                renderer.render(game, manager, g, item, instance, renderX, renderY + scale / 2, scale / 2, light[0]);
            renderX += scale / 2;
        }
    }
}
