package de.ellpeck.tfb.recipes;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.content.IContentLoader;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.tfb.TFB;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class KnappingLoader implements IContentLoader<KnappingRecipe> {

    private final Set<ResourceName> disabled = new HashSet<>();

    @Override
    public ResourceName getContentIdentifier() {
        return TFB.createRes("knapping");
    }

    @Override
    public void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception {
        if (this.disabled.contains(resourceName))
            return;
        var json = this.getRecipeObject(game, path + element.getAsString());

        var type = KnappingType.valueOf(json.get("type").getAsString().toUpperCase(Locale.ROOT));
        if (type.registry.get(resourceName) != null)
            return;

        var output = this.readItemInstance(json.getAsJsonObject("output"));

        var pattern = new boolean[5][5];
        var lines = json.getAsJsonArray("pattern");
        for (var y = 0; y < lines.size(); y++) {
            var line = lines.get(y).getAsString();
            for (var x = 0; x < line.length(); x++) {
                pattern[x][y] = !Character.isWhitespace(line.charAt(x));
            }
        }

        type.registry.register(resourceName, new KnappingRecipe(resourceName, pattern, output));
    }

    @Override
    public void disableContent(IGameInstance game, ResourceName resourceName) {
        this.disabled.add(resourceName);
    }
}
