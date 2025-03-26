package fragrant.utils;

public class Position {
    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    public int[] toArray() {
        return new int[]{x, y, z};
    }

    @Override
    public String toString() {
        return String.format("{x=%d y=%d z=%d}", x, y, z);
    }

    public static class BlockPos extends Position {
        public BlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public ChunkPos toChunkPos() {
            return new ChunkPos(getX() >> 4, getZ() >> 4);
        }
    }

    public static class ChunkPos extends Position {
        public ChunkPos(int x, int z) {
            super(x, 0, z);
        }

        public int getChunkX() { return getX(); }
        public int getChunkZ() { return getZ(); }

        public BlockPos toBlockPos() {
            return new BlockPos(getX() * 16, 0, getZ() * 16);
        }

        @Override
        public String toString() {
            return String.format("{x=%d z=%d}", getX(), getZ());
        }
    }
}