package de.ellpeck.tfb.mechanics.knapping;

import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.Arrays;

public class KnappingRecipe implements IContent {

    public static final NameRegistry<KnappingRecipe> STONE_REGISTRY = new NameRegistry<>(TFB.createRes("stone_knapping"), true).register();
    public static final NameRegistry<KnappingRecipe> CLAY_REGISTRY = new NameRegistry<>(TFB.createRes("clay_knapping"), true).register();

    public final ResourceName name;
    public final boolean[][] pattern;
    public final ItemInstance output;

    public KnappingRecipe(ResourceName name, boolean[][] pattern, ItemInstance output) {
        this.name = name;
        this.pattern = pattern;
        this.output = output;
    }

    public boolean matches(boolean[][] pattern) {
        // normal matching
        if (Arrays.deepEquals(this.pattern, pattern))
            return true;

        // mirrored matching
        for (var y = 0; y < 5; y++) {
            for (var x = 0; x < 5; x++) {
                if (this.pattern[4 - x][y] != pattern[x][y])
                    return false;
            }
        }
        return true;
    }
}
