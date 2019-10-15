package de.ellpeck.tfb.items.renderers;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.items.ItemCast;

public class ItemCastRenderer extends DefaultItemRenderer<ItemCast> {
    public ItemCastRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, ItemCast item, ItemInstance instance, float x, float y, float scale, int filter) {
        super.render(game, manager, g, item, instance, x, y, scale, filter);
        var metal = item.getMetal(instance);
        if (metal == null)
            return;
        var amount = item.getMetalAmount(instance);
        var max = item.getMaxMetalAmount(instance);

        var color = Colors.setA(metal.color, amount / (float) max);
        manager.getTexture(this.texture.addSuffix("_overlay")).draw(x, y, 1F * scale, 1F * scale, Colors.multiply(color, filter));
    }
}
