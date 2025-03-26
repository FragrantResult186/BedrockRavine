package fragrant.ravine;

import fragrant.utils.Direction;
import fragrant.utils.Position;
import java.util.ArrayList;
import java.util.List;

public class BedrockRavine {
    /**
     * Checks if an Info can be generated
     *
     * @param seed    World seed
     * @param version Bedrock version
     * @param chunkX  ChunkX
     * @param chunkZ  ChunkZ
     * @return Info, Null
     */
    public static Info canGenerateRavine(long seed, int version, int chunkX, int chunkZ) {
        BedrockRavineGenerator generator = new BedrockRavineGenerator();
        if (generator.generateBedrockRavine(seed, version, chunkX, chunkZ)) {
            Position pos = generator.getStartPos();
            return new Info(
                    pos.getX(), pos.getY(), pos.getZ(),
                    chunkX, chunkZ,
                    generator.getThick(),
                    generator.getWidth(),
                    generator.getYaw(),
                    generator.getPitch(),
                    generator.isGiant(),
                    generator.getDirection().getCompassDirection(),
                    generator.getDirection()
            );
        }
        return null;
    }

    /**
     * Get the nearest Ravine in the chunk range
     *
     * @param seed         World seed
     * @param version      Bedrock version
     * @param centerChunkX Center chunkX
     * @param centerChunkZ Center chunkZ
     * @param chunkRange   Range of chunks to get
     * @return Nearest Ravine or Null
     */
    public static Info getFirstRavine(long seed, int version, int centerChunkX, int centerChunkZ, int chunkRange) {
        Info Ravine = null;
        double minDistance = Double.MAX_VALUE;

        for (int dx = -chunkRange; dx <= chunkRange; dx++) {
            for (int dz = -chunkRange; dz <= chunkRange; dz++) {
                int chunkX = centerChunkX + dx;
                int chunkZ = centerChunkZ + dz;

                Info ravine = canGenerateRavine(seed, version, chunkX, chunkZ);
                if (ravine != null) {
                    double distance = Math.sqrt(dx * dx + dz * dz);
                    if (distance < minDistance) {
                        minDistance = distance;
                        Ravine = ravine;
                    }
                }
            }
        }

        return Ravine;
    }

    /**
     * Get all Ravines within the chunk range
     *
     * @param seed         World seed
     * @param version      Bedrock version
     * @param centerChunkX Center chunkX
     * @param centerChunkZ Center chunkZ
     * @param chunkRange   Range of chunks to get
     * @return List of Info within the range
     */
    public static List<Info> getRavines(long seed, int version, int centerChunkX, int centerChunkZ, int chunkRange) {
        List<Info> Ravines = new ArrayList<>();

        for (int dx = -chunkRange; dx <= chunkRange; dx++) {
            for (int dz = -chunkRange; dz <= chunkRange; dz++) {
                int chunkX = centerChunkX + dx;
                int chunkZ = centerChunkZ + dz;

                Info ravine = canGenerateRavine(seed, version, chunkX, chunkZ);
                if (ravine != null) {
                    Ravines.add(ravine);
                }
            }
        }

        return Ravines;
    }

    public record Info(
            int blockX,
            int blockY,
            int blockZ,
            int chunkX,
            int chunkZ,
            float thick,
            float width,
            float yaw,
            float pitch,
            boolean isGiant, String compassDirection, Direction direction) {

        @Override
        public String toString() {
            return String.format(
                    "block{%d, %d, %d}, width=%.2f, giant=%b, direction=%s",
                    blockX, blockY, blockZ, width, isGiant, compassDirection
            );
        }
    }
}