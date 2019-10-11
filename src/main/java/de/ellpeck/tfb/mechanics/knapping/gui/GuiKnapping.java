package de.ellpeck.tfb.mechanics.knapping.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

public class GuiKnapping extends GuiContainer {

    public GuiKnapping(AbstractEntityPlayer player) {
        super(player, 135, 150);
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        for (var x = 0; x < 5; x++) {
            for (var y = 0; y < 5; y++) {
                this.components.add(new KnappingSquare(this, x, y, 17, 0, 16));
            }
        }
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("knapping");
    }
}
