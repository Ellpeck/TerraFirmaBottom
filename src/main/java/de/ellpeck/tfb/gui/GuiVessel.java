package de.ellpeck.tfb.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.ItemVessel;

public class GuiVessel extends GuiContainer {

    public GuiVessel(AbstractEntityPlayer player) {
        super(player, 135, 120);

        var inventory = ItemVessel.loadInventory(player.getSelectedItem());
        if (inventory.metal == null) {
            var playerSlots = player.getInv().getSlotAmount();
            var behavior = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots + 3);
            this.shiftClickBehaviors.add(behavior);
            this.shiftClickBehaviors.add(behavior.reversed());
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g) {
        super.render(game, manager, g);
        var inventory = ItemVessel.loadInventory(this.player.getSelectedItem());
        if (inventory.metal != null) {
            var font = manager.getFont();
            font.drawCenteredString(this.x + this.width / 2F, this.y, inventory.metal.getDisplayName(), 0.45F, false);
            font.drawCenteredString(this.x + this.width / 2F, this.y + 10, inventory.metalAmount + " Units", 0.35F, false);
        }
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("vessel");
    }
}
