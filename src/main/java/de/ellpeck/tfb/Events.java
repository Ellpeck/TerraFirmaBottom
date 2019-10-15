package de.ellpeck.tfb;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.*;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.tfb.gui.GuiKnapping;
import de.ellpeck.tfb.gui.container.ContainerKnapping;
import de.ellpeck.tfb.gui.container.ContainerVessel;
import de.ellpeck.tfb.items.IUpdatingItem;
import de.ellpeck.tfb.items.Items;
import de.ellpeck.tfb.items.ToolProperties;
import de.ellpeck.tfb.packets.PacketOpenKnapping;
import de.ellpeck.tfb.recipes.KnappingType;

public final class Events {

    public static void init() {
        var ev = RockBottomAPI.getEventHandler();
        ev.registerListener(ItemInteractEvent.class, (result, event) -> {
            for (var type : KnappingType.values()) {
                if (event.instance.getItem() != type.item)
                    continue;
                if (event.instance.getAmount() < type.requiredAmount)
                    continue;

                event.player.openGuiContainer(new GuiKnapping(event.player, type), new ContainerKnapping(event.player, type));
                if (event.player.world.isClient())
                    RockBottomAPI.getNet().sendToServer(new PacketOpenKnapping(event.player.getUniqueId(), type));
                return EventResult.CANCELLED;
            }
            return result;
        });
        ev.registerListener(TileDropsEvent.class, (result, event) -> {
            if (event.tile != GameContent.TILE_GRASS_TUFT)
                return result;
            if (!(event.destroyer instanceof AbstractEntityPlayer))
                return result;
            var player = (AbstractEntityPlayer) event.destroyer;
            var held = player.getInv().get(player.getSelectedSlot());
            if (held == null || !held.getItem().getToolProperties(held).containsKey(ToolProperties.KNIFE))
                return result;

            event.drops.clear();
            event.drops.add(new ItemInstance(Items.STRAW));
            return EventResult.MODIFIED;
        });
        ev.registerListener(RecipeLearnEvent.class, (result, event) -> {
            if ("rockbottom".equals(event.recipe.getName().getDomain()))
                return EventResult.CANCELLED;
            return result;
        });
        ev.registerListener(TileEntityTickEvent.class, (result, event) -> {
            var inv = event.tileEntity.getTileInventory();
            if (inv == null)
                return result;
            updateInventory(inv, event.tileEntity.world);
            return result;
        });
        ev.registerListener(EntityTickEvent.class, (result, event) -> {
            if (event.entity instanceof AbstractEntityPlayer) {
                var player = (AbstractEntityPlayer) event.entity;
                updateInventory(player.getInv(), player.world);

                var container = player.getContainer();
                if (container instanceof ContainerVessel)
                    ((ContainerVessel) container).updateCasting();
            } else if (event.entity instanceof AbstractEntityItem) {
                var itemEntity = (AbstractEntityItem) event.entity;
                var inst = itemEntity.getItem();
                var item = inst.getItem();
                if (item instanceof IUpdatingItem)
                    ((IUpdatingItem) item).update(itemEntity.world, inst);
            }
            return result;
        });
    }

    public static void updateInventory(IInventory inv, IWorld world) {
        for (var instance : inv) {
            if (instance == null)
                continue;
            var item = instance.getItem();
            if (item instanceof IUpdatingItem) {
                ((IUpdatingItem) item).update(world, instance);
            }
        }
    }
}
