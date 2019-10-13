package de.ellpeck.tfb.ores;

import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.Locale;

public enum OreRichness {
    POOR(15),
    REGULAR(25),
    RICH(35),
    SMALL(10);

    public final ResourceName name;
    public final int amount;

    OreRichness(int amount) {
        this.name = TFB.createRes(this.name().toLowerCase(Locale.ROOT));
        this.amount = amount;
    }
}