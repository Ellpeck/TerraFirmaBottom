package de.ellpeck.tfb.mechanics.knapping;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.tfb.tiles.Tiles;

public enum KnappingType {

    STONE(GameContent.TILE_PEBBLES.getItem(), 2, 1, KnappingRecipe.STONE_REGISTRY),
    CLAY(Tiles.CLAY.getItem(), 5, 5, KnappingRecipe.CLAY_REGISTRY);

    public final Item item;
    public final int requiredAmount;
    public final int consumedAmount;
    public final NameRegistry<KnappingRecipe> registry;

    KnappingType(Item item, int requiredAmount, int consumedAmount, NameRegistry<KnappingRecipe> registry) {
        this.item = item;
        this.requiredAmount = requiredAmount;
        this.consumedAmount = consumedAmount;
        this.registry = registry;
    }
}
