package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.IRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;

public final class ContentStripper {

    public static void strip() {
        TFB.logger.info("Stripping game content...");
        clearRegistry(Registries.MANUAL_CONSTRUCTION_RECIPES);
        clearRegistry(Registries.CONSTRUCTION_TABLE_RECIPES);
        clearRegistry(Registries.SMITHING_RECIPES);
        clearRegistry(Registries.MORTAR_RECIPES);
        Registries.STRUCTURE_REGISTRY.unregister(new ResourceName("rockbottom/start_hut"));
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
