package de.ellpeck.tfb.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.OutputSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.tfb.gui.container.ContainerKnapping;

public class SlotKnapping extends OutputSlot {
    public SlotKnapping(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public ComponentSlot getGraphicalSlot(GuiContainer gui, int index, int xOffset, int yOffset) {
        return new ComponentSlot(gui, this, index, xOffset + this.x, yOffset + this.y) {
            @Override
            public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
                if (super.onMouseAction(game, button, x, y)) {
                    var gui = (GuiKnapping) this.gui;
                    var container = (ContainerKnapping) gui.getContainer();
                    container.onItemTakeout();
                    return true;
                }
                return false;
            }
        };
    }
}
