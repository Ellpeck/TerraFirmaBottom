package de.ellpeck.tfb.recipes;

import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.ores.MetalType;

import java.util.Arrays;
import java.util.Map;

public class AlloyRecipe {

    public static final NameRegistry<AlloyRecipe> REGISTRY = new NameRegistry<>(TFB.createRes("alloys"), true).register();

    public final ResourceName name;
    public final Input[] inputs;
    public final MetalType output;

    public AlloyRecipe(ResourceName name, Input[] inputs, MetalType output) {
        this.name = name;
        this.inputs = inputs;
        this.output = output;
    }

    public boolean matches(Map<MetalType, Counter> inputs, float total) {
        if (inputs.size() != this.inputs.length)
            return false;
        for (var input : this.inputs) {
            var amount = inputs.get(input.type);
            if (amount == null)
                return false;
            var percentage = Util.floor((amount.get() / total) * 100);
            if (percentage < input.min || percentage > input.max)
                return false;
        }
        return true;
    }

    public static class Input {
        public final MetalType type;
        public final int min;
        public final int max;

        public Input(MetalType type, int min, int max) {
            this.type = type;
            this.min = min;
            this.max = max;
        }
    }
}
