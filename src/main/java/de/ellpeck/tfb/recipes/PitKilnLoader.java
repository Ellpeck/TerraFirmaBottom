package de.ellpeck.tfb.recipes;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.content.IContentLoader;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.HashSet;
import java.util.Set;

public class PitKilnLoader implements IContentLoader<KnappingRecipe> {

    private final Set<ResourceName> disabled = new HashSet<>();

    @Override
    public ResourceName getContentIdentifier() {
        return TFB.createRes("pit_kiln");
    }

    @Override
    public void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception {
        if (this.disabled.contains(resourceName))
            return;
        var json = this.getRecipeObject(game, path + element.getAsString());
        var input = this.readUseInfo(json.getAsJsonObject("input"));
        var output = this.readItemInstance(json.getAsJsonObject("output"));
        PitKilnRecipe.REGISTRY.register(resourceName, new PitKilnRecipe(resourceName, input, output));
    }

    @Override
    public void disableContent(IGameInstance game, ResourceName resourceName) {
        this.disabled.add(resourceName);
    }
}
