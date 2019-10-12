package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.IRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.function.Function;

public final class ContentModifier {

    public static void init() {
        TFB.logger.info("Modifying game content...");

        modifyRegistry(Registries.MANUAL_CONSTRUCTION_RECIPES);
        modifyRegistry(Registries.CONSTRUCTION_TABLE_RECIPES);
        modifyRegistry(Registries.SMITHING_RECIPES);
        modifyRegistry(Registries.MORTAR_RECIPES);
        modifyRegistry(Registries.WORLD_GENERATORS, WorldGenOre.class::isAssignableFrom);

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
    }

    private static <U> void modifyRegistry(IRegistry<ResourceName, U> registry) {
        modifyRegistry(registry, e -> true);
    }

    private static <U> void modifyRegistry(IRegistry<ResourceName, U> registry, Function<U, Boolean> predicate) {
        var content = new ArrayList<ResourceName>();
        for (var entry : registry.entrySet()) {
            var name = entry.getKey();
            if ("rockbottom".equals(name.getDomain()) && predicate.apply(entry.getValue()))
                content.add(name);
        }
        content.forEach(registry::unregister);
    }
}
