package de.ellpeck.tfb.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.items.ItemVessel;
import de.ellpeck.tfb.items.VesselInventory;

public class GuiVessel extends GuiContainer {

    private final VesselInventory inventory;

    public GuiVessel(AbstractEntityPlayer player, ItemInstance instance) {
        super(player, 135, 120);
        this.inventory = ItemVessel.loadInventory(instance);

        if (this.inventory.metal == null) {
            var playerSlots = player.getInv().getSlotAmount();
            var behavior = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots + 3);
            this.shiftClickBehaviors.add(behavior);
            this.shiftClickBehaviors.add(behavior.reversed());
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g) {
        super.render(game, manager, g);
        if (this.inventory.metal != null) {
            var font = manager.getFont();
            font.drawCenteredString(this.x + this.width / 2F, this.y, this.inventory.metal.getDisplayName(), 0.45F, false);
            font.drawCenteredString(this.x + this.width / 2F, this.y + 10, this.inventory.metalAmount + " Units", 0.35F, false);
        }
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("vessel");
    }
}
