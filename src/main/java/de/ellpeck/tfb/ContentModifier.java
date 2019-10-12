package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.IRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;

public final class ContentModifier {

    public static void init() {
        TFB.logger.info("Modifying game content...");

        // clear registries
        clearRegistry(Registries.MANUAL_CONSTRUCTION_RECIPES);
        clearRegistry(Registries.CONSTRUCTION_TABLE_RECIPES);
        clearRegistry(Registries.SMITHING_RECIPES);
        clearRegistry(Registries.MORTAR_RECIPES);
        Registries.STRUCTURE_REGISTRY.unregister(new ResourceName("rockbottom/start_hut"));

        // modify break stuff
        GameContent.TILE_STONE.setHardness(100);
        GameContent.TILE_SANDSTONE.setHardness(80);
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
