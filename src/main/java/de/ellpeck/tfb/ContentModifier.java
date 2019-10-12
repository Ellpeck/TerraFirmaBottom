package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.RecipeLearnEvent;
import de.ellpeck.rockbottom.api.util.reg.IRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;

public final class ContentModifier {

    public static void init() {
        TFB.logger.info("Modifying game content...");

        clearRegistry(Registries.MANUAL_CONSTRUCTION_RECIPES);
        clearRegistry(Registries.CONSTRUCTION_TABLE_RECIPES);
        clearRegistry(Registries.SMITHING_RECIPES);
        clearRegistry(Registries.MORTAR_RECIPES);
        Registries.STRUCTURE_REGISTRY.unregister(new ResourceName("rockbottom/start_hut"));

        for (var tile : Registries.TILE_REGISTRY.values()) {
            try {
                var hardness = tile.getHardness(null, 0, 0, TileLayer.MAIN);
                if (tile.getName().getResourceName().contains("stone")) {
                    tile.setHardness(hardness * 10);
                } else {
                    tile.setHardness(hardness * 5);
                }
            } catch (Exception ignored) {
            }
        }

        RockBottomAPI.getEventHandler().registerListener(RecipeLearnEvent.class, (result, event) -> {
            if ("rockbottom".equals(event.recipe.getName().getDomain()))
                return EventResult.CANCELLED;
            return result;
        });
    }

    private static <U> void clearRegistry(IRegistry<ResourceName, U> registry) {
        var content = new ArrayList<ResourceName>();
        for (var name : registry.keySet()) {
            if ("rockbottom".equals(name.getDomain()))
                content.add(name);
        }
        content.forEach(registry::unregister);
    }
}
