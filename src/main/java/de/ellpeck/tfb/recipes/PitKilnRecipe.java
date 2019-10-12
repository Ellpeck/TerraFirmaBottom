package de.ellpeck.tfb.recipes;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

public class PitKilnRecipe {

    public static final NameRegistry<PitKilnRecipe> REGISTRY = new NameRegistry<>(TFB.createRes("pit_kiln"), true).register();

    public final ResourceName name;
    public final IUseInfo input;
    public final ItemInstance output;

    public PitKilnRecipe(ResourceName name, IUseInfo input, ItemInstance output) {
        this.name = name;
        this.input = input;
        this.output = output;
    }

    public boolean matches(ItemInstance input) {
        return this.input.containsItem(input);
    }
}
