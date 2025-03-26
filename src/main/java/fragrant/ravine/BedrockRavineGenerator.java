package fragrant.ravine;

import fragrant.utils.BedrockVersion;
import fragrant.random.BedrockRandom;
import fragrant.utils.Direction;
import fragrant.utils.Position;

public class BedrockRavineGenerator {
    private int startX, startY, startZ;
    private float thick, width, yaw, pitch;
    private boolean isGiant;
//        private int ravineLength;
//        private int lowerYGuess, upperYGuess;
//        private int xGuess, zGuess;

    public boolean generateBedrockRavine(long seed, int version, int chunkX, int chunkZ) {
        BedrockRandom.BedrockChunkRandom chunkSeed = new BedrockRandom.BedrockChunkRandom(seed);
        BedrockRandom mt = chunkSeed.ChunkRandom(chunkX, chunkZ);

        if (mt.nextInt(version >= BedrockVersion.V1_21_60 ? 100 : 150) != 0) return false;
        this.startX = 16 * chunkX + mt.nextInt(16);
        if (version >= BedrockVersion.V1_21_60) {
            this.startY = 10 + mt.nextInt(58);
            mt.nextInt(); // I don't get this role
        } else {
            this.startY = 20 + mt.nextInt(8 + mt.nextInt(40));
        }
        mt.nextInt(); // Not sure if this is length
        this.startZ = 16 * chunkZ + mt.nextInt(16);
        float TWO_PI = (float) (Math.PI * 2);
        this.yaw = mt.nextFloat() * TWO_PI;
        this.pitch = (mt.nextFloat() - 0.5F) * 0.25F; // = f - 0.5F) / 4.0F
        this.thick = 3 * (mt.nextFloat() + mt.nextFloat());
        if (mt.nextFloat() < 0.05F) {
            this.width = 2 * this.thick;
            this.isGiant = true;
        } else {
            this.width = this.thick;
        }
//        int middleMiddleY = (int) (this.startY + Math.sin(this.pitch) * this.ravineLength / 2);
//        this.lowerYGuess = (int) (middleMiddleY - this.thick);
//        this.upperYGuess = (int) (middleMiddleY + this.thick + 1);
//        float deltaHorizontal = (float) Math.cos(this.pitch);
//        this.xGuess = (int) (this.ravineLength / 2 * Math.cos(this.yaw) * deltaHorizontal);
//        this.zGuess = (int) (this.ravineLength / 2 * Math.sin(this.yaw) * deltaHorizontal);
        return true;
    }

    public Position getStartPos() {
        return new Position(this.startX, this.startY, this.startZ);
    }
    public float getThick() {
        return thick;
    }
    public float getWidth() {
        return width;
    }
    public float getYaw() {
        return yaw;
    }
    public float getPitch() {
        return pitch;
    }
    public Direction getDirection() {
        return new Direction(this.yaw, this.pitch);
    }
    public boolean isGiant() {
        return isGiant;
    }
//    public int getRavineLength() { return ravineLength; }
//    public int getLowerYGuess() { return lowerYGuess; }
//    public int getUpperYGuess() { return upperYGuess; }
//    public int getXGuess() { return xGuess; }
//    public int getZGuess() { return zGuess; }

    @Override
    public String toString() {
        return String.format("width=%.2f giant=%b yaw=%.2f pitch=%.2f", width, false, yaw, pitch);
    }
}