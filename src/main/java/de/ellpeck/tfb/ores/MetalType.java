package de.ellpeck.tfb.ores;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.Locale;

public enum MetalType {

    BISMUTH,
    COPPER,
    ZINC;

    public final ResourceName name;

    MetalType() {
        this.name = TFB.createRes(this.name().toLowerCase(Locale.ROOT)).addPrefix("metal.");
    }

    public static MetalType byName(ResourceName name) {
        for (var type : values()) {
            if (type.name.equals(name))
                return type;
        }
        return null;
    }

    public String getDisplayName() {
        return RockBottomAPI.getGame().getAssetManager().localize(this.name);
    }
}
