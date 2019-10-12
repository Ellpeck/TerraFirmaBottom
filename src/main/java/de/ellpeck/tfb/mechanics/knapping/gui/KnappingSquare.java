package de.ellpeck.tfb.mechanics.knapping.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.mechanics.knapping.KnappingType;
import de.ellpeck.tfb.mechanics.knapping.PacketKnap;

import java.util.Locale;

public class KnappingSquare extends ComponentButton {

    public final KnappingType type;
    public final int gridX;
    public final int gridY;
    private final ResourceName texture;

    public KnappingSquare(Gui gui, int gridX, int gridY, int xOff, int yOff, int size, KnappingType type) {
        super(gui, xOff + gridX * size, yOff + gridY * size, size, size, null, null);
        this.gridX = gridX;
        this.gridY = gridY;
        this.type = type;
        this.texture = TFB.createRes("knapping_" + this.type.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (this.isRemoved())
            return;

        var color = this.isMouseOverPrioritized(game) ? 0xFFFFFFFF : 0xFFBBBBBB;
        manager.getTexture(this.texture).draw(x, y, this.width, this.height, color);
    }

    private boolean isRemoved() {
        var container = (ContainerKnapping) ((GuiKnapping) this.gui).getContainer();
        return !container.pattern[this.gridX][this.gridY];
    }

    @Override
    public boolean onPressed(IGameInstance game) {
        if (!this.isRemoved()) {
            var gui = (GuiKnapping) this.gui;
            var container = (ContainerKnapping) gui.getContainer();
            container.knap(this.gridX, this.gridY);
            if (gui.player.world.isClient())
                RockBottomAPI.getNet().sendToServer(new PacketKnap(gui.player.getUniqueId(), this.gridX, this.gridY));
            return true;
        }
        return false;
    }
}
