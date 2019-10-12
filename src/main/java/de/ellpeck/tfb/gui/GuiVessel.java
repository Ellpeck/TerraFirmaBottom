package de.ellpeck.tfb.gui;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

public class GuiVessel extends GuiContainer {
    public GuiVessel(AbstractEntityPlayer player) {
        super(player, 135, 120);

        int playerSlots = player.getInv().getSlotAmount();
        ShiftClickBehavior behavior = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots + 3);
        this.shiftClickBehaviors.add(behavior);
        this.shiftClickBehaviors.add(behavior.reversed());
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("vessel");
    }
}
