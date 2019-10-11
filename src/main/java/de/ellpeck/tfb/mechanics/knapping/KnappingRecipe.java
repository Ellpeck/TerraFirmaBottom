package de.ellpeck.tfb.mechanics.knapping;

import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.Arrays;

public class KnappingRecipe implements IContent {

    public static final NameRegistry<KnappingRecipe> REGISTRY = new NameRegistry<>(TFB.createRes("knapping"), true).register();

    public final ResourceName name;
    public final boolean[][] pattern;
    public final ItemInstance output;

    public KnappingRecipe(ResourceName name, boolean[][] pattern, ItemInstance output) {
        this.name = name;
        this.pattern = pattern;
        this.output = output;
    }

    public boolean matches(boolean[][] pattern) {
        return Arrays.deepEquals(this.pattern, pattern);
    }
}
