package fragrant.random;

import java.util.Random;

public class BedrockRandom extends Random {
    private static final int N = 624;
    private static final int M = 397;
    private static final int MATRIX_A = 0x9908b0df;
    private static final int U_MASK = 0x80000000;
    private static final int L_MASK = 0x7fffffff;
    private static final int[] MAG_01 = {0, MATRIX_A};
    private static final double TWO_POW_M32 = 1.0 / (1L << 32);

    private int seed;
    private int[] mt = new int[N];
    private int mti;
    private int mtiFast;
    private boolean valid;

    public BedrockRandom() {
        this(new Random().nextInt());
    }

    public BedrockRandom(int seed) {
        valid = true;
        _setSeed(seed);
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        if (valid) setSeed((int) seed);
    }

    public void setSeed(int seed) {
        _setSeed(seed);
    }

    @Override
    public int nextInt() {
        return _genRandInt32() >>> 1;
    }

    @Override
    public int nextInt(int bound) {
        if (bound > 0) return (int) (Integer.toUnsignedLong(_genRandInt32()) % bound);
        else return 0;
    }

    public int nextInt(int a, int b) {
        if (a < b) return a + nextInt(b - a);
        else return a;
    }

    @Override
    public boolean nextBoolean() {
        return (_genRandInt32() & 0x8000000) != 0;
    }

    @Override
    public float nextFloat() {
        return (float) _genRandReal2();
    }

    public float nextFloat(float bound) {
        return nextFloat() * bound;
    }

    public float nextFloat(float a, float b) {
        return a + (nextFloat() * (b - a));
    }

    @Override
    public double nextDouble() {
        return _genRandReal2();
    }

    @Override
    protected int next(int bits) {
        return _genRandInt32() >>> (32 - bits);
    }

    private void _setSeed(int seed) {
        this.seed = seed;
        this.mti = N + 1;
        _initGenRandFast(seed);
    }

    private void _initGenRand(int initialValue) {
        this.mt[0] = initialValue;
        for (this.mti = 1; this.mti < N; this.mti++) {
            this.mt[mti] = 1812433253 * ((this.mt[this.mti - 1] >>> 30) ^ this.mt[this.mti - 1]) + this.mti;
        }
        this.mtiFast = N;
    }

    private void _initGenRandFast(int initialValue) {
        this.mt[0] = initialValue;
        for (this.mtiFast = 1; this.mtiFast <= M; this.mtiFast++) {
            this.mt[this.mtiFast] = 1812433253 * ((this.mt[this.mtiFast - 1] >>> 30) ^ this.mt[this.mtiFast - 1]) + this.mtiFast;
        }
        this.mti = N;
    }

    private int _genRandInt32() {
        if (this.mti == N) {
            this.mti = 0;
        } else if (this.mti > N) {
            _initGenRand(5489);
            this.mti = 0;
        }

        if (this.mti >= N - M) {
            if (this.mti == N - 1) {
                this.mt[N - 1] = MAG_01[this.mt[0] & 1] ^ ((this.mt[0] & L_MASK | this.mt[N - 1] & U_MASK) >>> 1) ^ this.mt[M - 1];
            } else {
                this.mt[this.mti] = MAG_01[this.mt[this.mti + 1] & 1] ^ ((this.mt[this.mti + 1] & L_MASK | this.mt[this.mti] & U_MASK) >>> 1) ^ this.mt[this.mti - (N - M)];
            }
        } else {
            this.mt[this.mti] = MAG_01[this.mt[this.mti + 1] & 1] ^ ((this.mt[this.mti + 1] & L_MASK | this.mt[this.mti] & U_MASK) >>> 1) ^ this.mt[this.mti + M];
            if (this.mtiFast < N) {
                this.mt[this.mtiFast] = 1812433253 * ((this.mt[this.mtiFast - 1] >>> 30) ^ this.mt[this.mtiFast - 1]) + this.mtiFast;
                this.mtiFast++;
            }
        }

        int ret = this.mt[this.mti++];
        ret = ((ret ^ (ret >>> 11)) << 7) & 0x9d2c5680 ^ ret ^ (ret >>> 11);
        ret = (ret << 15) & 0xefc60000 ^ ret ^ (((ret << 15) & 0xefc60000 ^ ret) >>> 18);
        return ret;
    }

    private double _genRandReal2() {
        return Integer.toUnsignedLong(_genRandInt32()) * TWO_POW_M32;
    }

    public static class BedrockChunkRandom {
        private long seed;

        public BedrockChunkRandom(long seed) {
            this.seed = seed;
        }

        public BedrockRandom ChunkRandom(int chunkX, int chunkZ) {
            BedrockRandom mt = new BedrockRandom((int) seed);
            int nextInt1 = mt.nextInt();
            int nextInt2 = mt.nextInt();
            long chunkSeed = seed ^ ((long) (nextInt1 | 1) * chunkX + ((long) (nextInt2 | 1) * chunkZ));
            mt = new BedrockRandom((int) chunkSeed);
            return mt;
        }
    }
}