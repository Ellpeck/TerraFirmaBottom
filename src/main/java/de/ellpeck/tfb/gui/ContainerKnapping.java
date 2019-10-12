package de.ellpeck.tfb.gui;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.recipes.KnappingType;

import java.util.Arrays;

public class ContainerKnapping extends ItemContainer {

    public final KnappingType type;
    public final boolean[][] pattern = new boolean[5][5];
    private final Inventory output = new Inventory(1);
    private boolean firstKnap;

    public ContainerKnapping(AbstractEntityPlayer player, KnappingType type) {
        super(player);
        this.type = type;

        for (var i = 0; i < 5; i++)
            Arrays.fill(this.pattern[i], true);

        this.addPlayerInventory(player, 0, 90);
        this.addSlot(new SlotKnapping(this.output, 0, 102, 32));
    }

    public void onItemTakeout() {
        for (var i = 0; i < 5; i++)
            Arrays.fill(this.pattern[i], false);
    }

    public void knap(int x, int y) {
        this.pattern[x][y] = false;

        if (!this.firstKnap) {
            this.player.getInv().remove(this.player.getSelectedSlot(), this.type.consumedAmount);
            this.firstKnap = true;
        }

        for (var recipe : this.type.registry.values()) {
            if (recipe.matches(this.pattern)) {
                this.output.set(0, recipe.output.copy());
                return;
            }
        }
        this.output.set(0, null);
    }

    @Override
    public ResourceName getName() {
        return TFB.createRes("knapping");
    }
}
