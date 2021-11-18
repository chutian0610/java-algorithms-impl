package info.victorchu.algorithms.hash;

import java.nio.charset.Charset;

/**
 * MurmurHash 算法
 */
public class MurmurHash {

    /** 128 bits of Hash result */
    public static final class LongPair {
        public LongPair(long val1, long val2) {
            this.val1 = val1;
            this.val2 = val2;
        }

        public long val1;
        public long val2;

        @Override
        public String toString() {
            return Long.toHexString(val1)+Long.toHexString(val2);
        }
    }
    /** 32bits 的长度是 4Byte */
    private static final int CHUNK_SIZE = 4;
    /** 128bits 的长度是 16Byte */
    private static final int CHUNK_SIZE_128 = 16;

    // 使用模拟退火算法求出了最合适的参数
    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;

    private static final long C1_128 = 0x87c37b91114253d5L;
    private static final long C2_128 = 0x4cf5ad432745937fL;

    /**
     * JVM采用大端方式存多字节的数据,获取对应的小端存储值.
     * @param input 字节数组
     * @param offset 偏移量起点
     * @return
     */
    private static int getIntLittleEndian(byte[] input, int offset) {
        return input[offset + 3]& 0xff << 24
        | input[offset + 2]& 0xff << 16
        | input[offset + 1] & 0xff << 8
        |input[offset] & 0xff;
    }

    /** Gets a long from a byte buffer in little endian byte order. */
    private static final long getLongLittleEndian(byte[] buf, int offset) {
        return     ((long)buf[offset+7]    << 56)   // no mask needed
                | ((buf[offset+6] & 0xffL) << 48)
                | ((buf[offset+5] & 0xffL) << 40)
                | ((buf[offset+4] & 0xffL) << 32)
                | ((buf[offset+3] & 0xffL) << 24)
                | ((buf[offset+2] & 0xffL) << 16)
                | ((buf[offset+1] & 0xffL) << 8)
                | ((buf[offset  ] & 0xffL));        // no shift needed
    }

    /**
     * K mix
     * @param k
     * @return
     */
    private static int mixK(int k) {
        k *= C1;
        k = Integer.rotateLeft(k, 15);
        k *= C2;
        return k;
    }
    private static long mixK1(long k1) {
        k1 *= C1_128;
        k1  = Long.rotateLeft(k1,31);
        k1 *= C2_128;
        return k1;
    }

    private static long mixK2(long k2) {
        k2 *= C2_128; k2  = Long.rotateLeft(k2,33); k2 *= C1_128;
        return k2;
    }

    /**
     * h mix
     * @param h
     * @param k
     * @return
     */
    private static int mixHash(int h, int k) {
        h ^= k;
        h = Integer.rotateLeft(h, 13);
        h = h * 5 + 0xe6546b64;
        return h;
    }

    /**
     * byte 转 Int
     * @param value
     * @return
     */
    private static int toInt(byte value){
        return value & 0xff;
    }

    // Finalization mix - force all bits of a hash block to avalanche
    private static int fmix(int hash, int length) {
        hash ^= length;
        hash ^= hash >>> 16;
        hash *= 0x85ebca6b;
        hash ^= hash >>> 13;
        hash *= 0xc2b2ae35;
        hash ^= hash >>> 16;
        return hash;
    }
    private static final long fmix64(long k) {
        k ^= k >>> 33;
        k *= 0xff51afd7ed558ccdL;
        k ^= k >>> 33;
        k *= 0xc4ceb9fe1a85ec53L;
        k ^= k >>> 33;
        return k;
    }
    // --------------------------- 入口函数 ---------------------------
    /**
     * Murmur3_32的Hash求值函数
     * @param seed seed 种子（无符号数）
     * @param key 待求hash的key值（二进制数组）
     * @param offset start offset of the array to hash
     * @param len length length of the array to hash
     * @return
     */
    public static int murmurhash3_x86_32(byte[] key,int offset, int len,int seed){
        // 使用种子作为初始值
        int hash= seed;
        int i;
        // 每次读4个byte
        for (i = 0; i + CHUNK_SIZE <= len; i += CHUNK_SIZE) {
            int k= mixK(getIntLittleEndian(key,offset+i));
            hash = mixHash(hash, k);
        }
        // 当没有读净buffer时，处理尾部
        int k1 = 0;
        for (int shift = 0; i < len; i++, shift += 8) {
            k1 ^= toInt(key[offset + i]) << shift;
        }
        hash ^= mixK(k1);
        return fmix(hash, len);
    }

    public static LongPair murmurhash3_x64_128(byte[] key, int offset, int len, int seed){
        // The original algorithm does have a 32 bit unsigned seed.
        // We have to mask to match the behavior of the unsigned types and prevent sign extension.
        long h1 = seed & 0x00000000FFFFFFFFL;
        long h2 = seed & 0x00000000FFFFFFFFL;
        int i;
        for (i=0; i + CHUNK_SIZE_128<= len; i+=CHUNK_SIZE_128) {
            long k_1 = getLongLittleEndian(key, offset+i);
            long k_2 = getLongLittleEndian(key, offset+i+8);
            long k1 = mixK1(k_1);
            h1 ^= k1; h1 = Long.rotateLeft(h1,27);
            h1 += h2; h1 = h1*5+0x52dce729;
            long k2 = mixK2(k_2);
            h2 ^= k2; h2 = Long.rotateLeft(h2,31);
            h2 += h1; h2 = h2*5+0x38495ab5;
        }
        long k1 = 0;
        long k2 = 0;
        int roundedEnd = offset + (len & 0xFFFFFFF0);
        switch (len & 15) {
            case 15: k2  = (key[roundedEnd+14] & 0xffL) << 48;
            case 14: k2 |= (key[roundedEnd+13] & 0xffL) << 40;
            case 13: k2 |= (key[roundedEnd+12] & 0xffL) << 32;
            case 12: k2 |= (key[roundedEnd+11] & 0xffL) << 24;
            case 11: k2 |= (key[roundedEnd+10] & 0xffL) << 16;
            case 10: k2 |= (key[roundedEnd+ 9] & 0xffL) << 8;
            case  9: k2 |= (key[roundedEnd+ 8] & 0xffL);
                k2 *= C2_128;
                k2  = Long.rotateLeft(k2, 33);
                k2 *= C1; h2 ^= k2;
            case  8: k1  = ((long)key[roundedEnd+7]) << 56;
            case  7: k1 |= (key[roundedEnd+6] & 0xffL) << 48;
            case  6: k1 |= (key[roundedEnd+5] & 0xffL) << 40;
            case  5: k1 |= (key[roundedEnd+4] & 0xffL) << 32;
            case  4: k1 |= (key[roundedEnd+3] & 0xffL) << 24;
            case  3: k1 |= (key[roundedEnd+2] & 0xffL) << 16;
            case  2: k1 |= (key[roundedEnd+1] & 0xffL) << 8;
            case  1: k1 |= (key[roundedEnd  ] & 0xffL);
                k1 *= C1_128;
                k1  = Long.rotateLeft(k1,31);
                k1 *= C2_128; h1 ^= k1;
        }
        h1 ^= len; h2 ^= len;

        h1 += h2;
        h2 += h1;

        h1 = fmix64(h1);
        h2 = fmix64(h2);

        h1 += h2;
        h2 += h1;
        return new LongPair(h1,h2);
    }
    // --------------------------- 包装方法 ---------------------------
    /**
     * 字符串基于Murmur3_32的Hash求值函数
     * @param seed 种子（无符号数）
     * @param key 待求hash的key值
     * @param charset key的字符集
     * @return 32位Hash值
     */
    public static int murmurhash3_x86_32_Str(int seed, String key, Charset charset){
        byte[] data = key.getBytes(charset);
        return murmurhash3_x86_32(data,0,data.length,seed);
    }
    public static LongPair murmurhash3_x64_128_Str(int seed, String key, Charset charset){
        byte[] data = key.getBytes(charset);
        return murmurhash3_x64_128(data,0,data.length,seed);
    }
}
