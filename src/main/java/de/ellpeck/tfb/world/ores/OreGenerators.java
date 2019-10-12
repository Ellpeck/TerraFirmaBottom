package de.ellpeck.tfb.world.ores;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.tiles.Tiles;

public final class OreGenerators {

    public static void init() {
        Registries.WORLD_GENERATORS.register(TFB.createRes("zinc"), Zinc.class);
        Registries.WORLD_GENERATORS.register(TFB.createRes("bismuth"), Bismuth.class);
        Registries.WORLD_GENERATORS.register(TFB.createRes("copper"), Copper.class);
    }

    public static class Zinc extends OreGenerator {

        public Zinc() {
            super(15, 12, 0.15F, Tiles.ZINC_ORE);
        }
    }

    public static class Bismuth extends OreGenerator {

        public Bismuth() {
            super(10, 10, 0.21F, Tiles.BISMUTH_ORE);
        }
    }

    public static class Copper extends OreGenerator {

        public Copper() {
            super(15, 14, 0.12F, Tiles.COPPER_ORE);
        }
    }

}
