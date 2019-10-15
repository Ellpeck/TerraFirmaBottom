package de.ellpeck.tfb.ores;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.Locale;

public enum MetalType {

    BISMUTH(0xFF00a15e),
    COPPER(0xFFb34a00),
    ZINC(0xFFc9c9c9),
    BISMUTH_BRONZE(Colors.rgb(69, 119, 78));

    public final ResourceName name;
    public final int color;

    MetalType(int color) {
        this.name = TFB.createRes(this.name().toLowerCase(Locale.ROOT));
        this.color = color;
    }

    public static MetalType byName(ResourceName name) {
        for (var type : values()) {
            if (type.name.equals(name))
                return type;
        }
        return null;
    }

    public String getDisplayName() {
        return RockBottomAPI.getGame().getAssetManager().localize(this.name.addPrefix("metal."));
    }
}
