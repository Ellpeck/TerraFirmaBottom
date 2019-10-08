package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.logging.Logger;

public class TerraFirmaBottom implements IMod {

    public static TerraFirmaBottom instance;
    public static Logger logger;

    public TerraFirmaBottom() {
        instance = this;
    }

    public static ResourceName createRes(String name) {
        return new ResourceName(instance, name);
    }

    @Override
    public String getDisplayName() {
        return "TerraFirmaBottom";
    }

    @Override
    public String getId() {
        return "tfb";
    }

    @Override
    public String getVersion() {
        return "@VERSION@";
    }

    @Override
    public String getResourceLocation() {
        return "assets/" + this.getId();
    }

    @Override
    public String getDescription() {
        return "TerraFirmaCraft for Rock Bottom";
    }

    @Override
    public void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        logger = apiHandler.createLogger(this.getDisplayName());
    }

    @Override
    public void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        ContentStripper.strip();
    }

    @Override
    public void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        logger.info("Starting TerraFirmaBottom");
    }
}
