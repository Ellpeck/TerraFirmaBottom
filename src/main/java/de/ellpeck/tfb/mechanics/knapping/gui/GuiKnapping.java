package de.ellpeck.tfb.mechanics.knapping.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.mechanics.knapping.KnappingType;

public class GuiKnapping extends GuiContainer {

    private final KnappingType type;

    public GuiKnapping(AbstractEntityPlayer player, KnappingType type) {
        super(player, 135, 150);
        this.type = type;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        for (var x = 0; x < 5; x++) {
            for (var y = 0; y < 5; y++) {
                this.components.add(new KnappingSquare(this, x, y, 17, 0, 16, this.type));
            }
        }
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("knapping");
    }
}
