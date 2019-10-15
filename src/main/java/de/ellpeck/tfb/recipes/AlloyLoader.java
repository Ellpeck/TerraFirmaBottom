package de.ellpeck.tfb.recipes;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.content.IContentLoader;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;
import de.ellpeck.tfb.ores.MetalType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AlloyLoader implements IContentLoader<KnappingRecipe> {

    private final Set<ResourceName> disabled = new HashSet<>();

    @Override
    public ResourceName getContentIdentifier() {
        return TFB.createRes("alloy");
    }

    @Override
    public void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception {
        if (this.disabled.contains(resourceName))
            return;
        var json = this.getRecipeObject(game, path + element.getAsString());
        var output = MetalType.byName(new ResourceName(json.get("output").getAsString()));

        var inputs = new ArrayList<AlloyRecipe.Input>();
        for (var entry : json.getAsJsonObject("inputs").entrySet()) {
            var metal = MetalType.byName(new ResourceName(entry.getKey()));
            var range = entry.getValue().getAsJsonArray();
            var min = range.get(0).getAsInt();
            var max = range.get(1).getAsInt();
            inputs.add(new AlloyRecipe.Input(metal, min, max));
        }

        AlloyRecipe.REGISTRY.register(resourceName, new AlloyRecipe(resourceName, inputs.toArray(new AlloyRecipe.Input[0]), output));
    }

    @Override
    public void disableContent(IGameInstance game, ResourceName resourceName) {
        this.disabled.add(resourceName);
    }
}
