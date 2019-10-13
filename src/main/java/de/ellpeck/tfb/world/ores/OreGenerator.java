package de.ellpeck.tfb.world.ores;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.tfb.ores.OreRichness;
import de.ellpeck.tfb.tiles.TileOre;

import java.util.Random;

public class OreGenerator implements IWorldGenerator {

    private final Random oreRandom = new Random();
    private final int clusterRadiusX;
    private final int clusterRadiusY;
    private final float perChunkChance;
    private final Tile ore;
    private long oreSeed;

    public OreGenerator(int clusterRadiusX, int clusterRadiusY, float perChunkChance, Tile ore) {
        this.clusterRadiusX = clusterRadiusX;
        this.clusterRadiusY = clusterRadiusY;
        this.perChunkChance = perChunkChance;
        this.ore = ore;
    }

    @Override
    public void initWorld(IWorld world) {
        this.oreSeed = Util.scrambleSeed(this.ore.getName().hashCode(), world.getSeed());
    }

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return true;
    }

    @Override
    public void generate(IWorld world, IChunk chunk) {
        if (chunk.getY() >= 0) {
            for (var x = 0; x < Constants.CHUNK_SIZE; x++) {
                var y = chunk.getHeightInner(TileLayer.MAIN, x);
                if (chunk.getY() + y >= world.getExpectedSurfaceHeight(TileLayer.MAIN, chunk.getX() + x)) {
                    if (y < Constants.CHUNK_SIZE && chunk.getStateInner(x, y).getTile().canReplace(world, chunk.getX() + x, chunk.getY() + y, TileLayer.MAIN)) {
                        this.oreRandom.setSeed(Util.scrambleSeed(x, y, this.oreSeed));
                        if (this.oreRandom.nextFloat() <= this.perChunkChance / 6) {
                            var theX = chunk.getX() + x;
                            var theY = chunk.getY() + y - 1;
                            if (world.isPosLoaded(theX, theY, false) && world.getState(theX, theY).getTile().hasSolidSurface(world, theX, theY, TileLayer.MAIN)) {
                                chunk.setStateInner(x, y, this.ore.getDefState().prop(TileOre.RICHNESS, OreRichness.SMALL));
                            }
                        }
                    }
                }
            }
        } else {
            this.oreRandom.setSeed(Util.scrambleSeed(chunk.getX(), chunk.getY(), this.oreSeed));
            if (this.oreRandom.nextFloat() > this.perChunkChance)
                return;

            var richnesses = OreRichness.values();
            var richness = richnesses[this.oreRandom.nextInt(richnesses.length - 1)];

            var radX = this.clusterRadiusX;
            var radY = this.clusterRadiusY;
            var radXHalf = Util.ceil((double) radX / 2);
            var radYHalf = Util.ceil((double) radY / 2);

            var startX = chunk.getX() + radX + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radX * 2);
            var startY = chunk.getY() + radY + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radY * 2);

            var thisRadX = this.oreRandom.nextInt(radXHalf) + radXHalf;
            var thisRadY = this.oreRandom.nextInt(radYHalf) + radYHalf;

            for (var x = -thisRadX; x <= thisRadX; x++) {
                for (var y = -thisRadY; y <= thisRadY; y++) {
                    if (world.getState(startX + x, startY + y).getTile() != GameContent.TILE_STONE)
                        continue;
                    if (this.oreRandom.nextInt(thisRadX) != x && this.oreRandom.nextInt(thisRadY) != y)
                        continue;

                    world.setState(startX + x, startY + y, this.ore.getDefState().prop(TileOre.RICHNESS, richness));
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return -100;
    }
}
