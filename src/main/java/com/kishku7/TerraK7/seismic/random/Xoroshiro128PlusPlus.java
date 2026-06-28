package com.kishku7.TerraK7.seismic.random;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;


/**
 * A self-contained Xoroshiro128++ {@link RandomGenerator}.
 *
 * <p>This exists so that callers can obtain a Xoroshiro128++ generator WITHOUT going through
 * {@code RandomGeneratorFactory.of("Xoroshiro128PlusPlus")}. That factory lookup resolves the
 * algorithm provider out of the JDK's {@code jdk.random} module via the service loader, which
 * fails under strongly-isolated plugin class loaders (e.g. Paper/Folia) with
 * "No implementation of the random number generator algorithm Xoroshiro128PlusPlus is available".
 * It is also fragile across JDK upgrades as module encapsulation tightens. Owning the algorithm
 * removes that dependency entirely.
 *
 * <p>Seeding matches the JDK: the 64-bit seed is expanded with the SplitMix64 (Stafford variant 13)
 * mixer at golden-ratio increments to fill the 128-bit state, with an all-zero state guarded to 1.
 */
public final class Xoroshiro128PlusPlus implements RandomGenerator {
    private static final long GOLDEN_RATIO_64 = 0x9e3779b97f4a7c15L;

    private long x0;
    private long x1;

    public Xoroshiro128PlusPlus(long seed) {
        long r = seed;
        r += Xoroshiro128PlusPlus.GOLDEN_RATIO_64;
        long s0 = Xoroshiro128PlusPlus.mixStafford13(r);
        r += Xoroshiro128PlusPlus.GOLDEN_RATIO_64;
        long s1 = Xoroshiro128PlusPlus.mixStafford13(r);
        if((s0 | s1) == 0L) {
            s0 = 1L;
        }
        this.x0 = s0;
        this.x1 = s1;
    }

    public Xoroshiro128PlusPlus() {
        this(ThreadLocalRandom.current().nextLong());
    }

    /**
     * Drop-in replacement for {@code RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create(seed)}.
     */
    public static RandomGenerator create(long seed) {
        return new Xoroshiro128PlusPlus(seed);
    }

    /**
     * Drop-in replacement for {@code RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create()}.
     */
    public static RandomGenerator create() {
        return new Xoroshiro128PlusPlus();
    }

    private static long mixStafford13(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }

    @Override
    public long nextLong() {
        long s0 = this.x0;
        long s1 = this.x1;
        long result = Long.rotateLeft(s0 + s1, 17) + s0;

        s1 ^= s0;
        this.x0 = Long.rotateLeft(s0, 49) ^ s1 ^ (s1 << 21);
        this.x1 = Long.rotateLeft(s1, 28);

        return result;
    }
}
