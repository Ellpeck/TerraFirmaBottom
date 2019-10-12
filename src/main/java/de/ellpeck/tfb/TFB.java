package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.packets.PacketKnap;
import de.ellpeck.tfb.packets.PacketOpenKnapping;
import de.ellpeck.tfb.recipes.KnappingLoader;
import de.ellpeck.tfb.recipes.PitKilnLoader;
import de.ellpeck.tfb.tiles.Tiles;
import de.ellpeck.tfb.world.ClayGenerator;
import de.ellpeck.tfb.world.StickGenerator;
import de.ellpeck.tfb.world.ores.OreGenerators;

import java.util.logging.Logger;

public class TFB implements IMod {

    public static TFB instance;
    public static Logger logger;

    public TFB() {
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
    public String getContentLocation() {
        return this.getResourceLocation() + "/content";
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
    public void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        logger.info("Starting TerraFirmaBottom");
    }

    @Override
    public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        new Items();
        new Tiles();
        Events.init();

        new KnappingLoader().register();
        new PitKilnLoader().register();

        Registries.PACKET_REGISTRY.registerNextFree(PacketOpenKnapping.class);
        Registries.PACKET_REGISTRY.registerNextFree(PacketKnap.class);

        Registries.WORLD_GENERATORS.register(createRes("clay"), ClayGenerator.class);
        Registries.WORLD_GENERATORS.register(createRes("sticks"), StickGenerator.class);
        OreGenerators.init();
    }

    @Override
    public void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        ContentModifier.init();
    }
}
