package info.victorchu.algorithms.hash;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Integer.reverseBytes;


public class MD5 {

    private int[] state;
    private int[] x;
    private int bufOfs;
    private byte[] buffer;
    static final byte[] padding;
    static{
        padding = new byte[136];
        padding[0] = (byte)0x80;
    }


    private static final int blockSize = 64;

    // 固定偏移量
    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;
    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;
    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;
    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;

    private static final int[] K = new int[] {
            0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
            0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
            0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
            0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
            0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
            0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
            0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
            0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
            0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
            0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
            0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
            0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
            0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
            0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
            0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
            0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391

    };

    private static int FF(int a, int b, int c, int d, int x, int s, int ac) {
        a += ((b & c) | ((~b) & d)) + x + ac;
        return ((a << s) | (a >>> (32 - s))) + b;
    }

    private static int GG(int a, int b, int c, int d, int x, int s, int ac) {
        a += ((b & d) | (c & (~d))) + x + ac;
        return ((a << s) | (a >>> (32 - s))) + b;
    }

    private static int HH(int a, int b, int c, int d, int x, int s, int ac) {
        a += ((b ^ c) ^ d) + x + ac;
        return ((a << s) | (a >>> (32 - s))) + b;
    }

    private static int II(int a, int b, int c, int d, int x, int s, int ac) {
        a += (c ^ (b | (~d))) + x + ac;
        return ((a << s) | (a >>> (32 - s))) + b;
    }

    static void b2iLittle(byte[] in, int inOfs, int[] out, int outOfs, int len) {
        if ((inOfs < 0) || ((in.length - inOfs) < len) ||
                (outOfs < 0) || ((out.length - outOfs) < len/4)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        len += inOfs;
        while (inOfs < len) {
            out[outOfs++] = ((in[inOfs    ] & 0xff))
                    | ((in[inOfs + 1] & 0xff) <<  8)
                    | ((in[inOfs + 2] & 0xff) << 16)
                    | ((in[inOfs + 3]       ) << 24);
            inOfs += 4;
        }
    }

    static void i2bLittle4(int val, byte[] out, int outOfs) {
        if ((outOfs < 0) || ((out.length - outOfs) < 4)) {
            throw new ArrayIndexOutOfBoundsException();
        }

            out[outOfs    ] = (byte)(val      );
            out[outOfs + 1] = (byte)(val >>  8);
            out[outOfs + 2] = (byte)(val >> 16);
            out[outOfs + 3] = (byte)(val >> 24);

    }

    static void i2bLittle(int[] in, int inOfs, byte[] out, int outOfs, int len) {
        if ((inOfs < 0) || ((in.length - inOfs) < len/4) ||
                (outOfs < 0) || ((out.length - outOfs) < len)) {
            throw new ArrayIndexOutOfBoundsException();
        }
            len += outOfs;
            while (outOfs < len) {
                int i = in[inOfs++];
                out[outOfs++] = (byte)(i      );
                out[outOfs++] = (byte)(i >>  8);
                out[outOfs++] = (byte)(i >> 16);
                out[outOfs++] = (byte)(i >> 24);
            }
    }

    public MD5() {
        state = new int[4];
        x = new int[16];
        buffer = new byte[blockSize];
        reset();
    }

    private void reset() {
        state[0] = 0x67452301;
        state[1] = 0xefcdab89;
        state[2] = 0x98badcfe;
        state[3] = 0x10325476;
        Arrays.fill(x, 0);
        Arrays.fill(buffer, (byte) 0x00);
        bufOfs = 0;
    }

    /* 448 bit = 56 byte
    *  512 bit = 64 byte
    *  */
    public byte[] md5(byte[] input) {
        updateMessage(input, 0, input.length);

        byte[] out = new byte[16];

        long bitsProcessed = (long) input.length << 3;
        // 计算padding
        int index = (int) input.length  & 0x3f;
        int padLen = (index < 56) ? (56 - index) : (120 - index);
        updateMessage(padding, 0, padLen);

        // 计算小端 低64位
        i2bLittle4((int)bitsProcessed, buffer, 56);
        i2bLittle4((int)(bitsProcessed >>> 32), buffer, 60);

        // 计算缓冲
        implCompress(buffer, 0);

        // 返回结果
        i2bLittle(state, 0, out, 0, 16);

        return out;
    }

    protected final void updateMessage(byte[] b, int ofs, int len) {
        if (len == 0) {
            return;
        }
        if ((ofs < 0) || (len < 0) || (ofs > b.length - len)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // if buffer is not empty, we need to fill it before proceeding
        if (bufOfs != 0) {
            int n = Math.min(len, blockSize - bufOfs);
            System.arraycopy(b, ofs, buffer, bufOfs, n);
            bufOfs += n;
            ofs += n;
            len -= n;
            if (bufOfs >= blockSize) {
                // compress completed block now
                implCompress(buffer, 0);
                bufOfs = 0;
            }
        }
        // compress complete blocks
        if (len >= blockSize) {
            int limit = ofs + len;
            ofs = implCompressMultiBlock(b, ofs, limit - blockSize);
            len = limit - ofs;
        }
        // copy remainder to buffer
        if (len > 0) {
            System.arraycopy(b, ofs, buffer, 0, len);
            bufOfs = len;
        }
    }
    private void implCompressMultiBlockCheck(byte[] b, int ofs, int limit) {
        if (limit < 0) {
            return;
        }
        Objects.requireNonNull(b);
        if (ofs < 0 || ofs >= b.length) {
            throw new ArrayIndexOutOfBoundsException(ofs);
        }
        int endIndex = (limit / blockSize) * blockSize  + blockSize - 1;
        if (endIndex >= b.length) {
            throw new ArrayIndexOutOfBoundsException(endIndex);
        }
    }

    private int implCompressMultiBlock(byte[] b, int ofs, int limit) {
        implCompressMultiBlockCheck(b, ofs, limit);
        for (; ofs <= limit; ofs += blockSize) {
            implCompress(b, ofs);
        }
        return ofs;
    }

    void implCompress(byte[] buf, int ofs) {
        // 读16个小端32位数
        b2iLittle(buf, ofs, x, 0, blockSize);

        int a = state[0];
        int b = state[1];
        int c = state[2];
        int d = state[3];

        /* Round 1 */
        a = FF ( a, b, c, d, x[ 0], S11, K[0]); 
        d = FF ( d, a, b, c, x[ 1], S12, K[1]); 
        c = FF ( c, d, a, b, x[ 2], S13, K[2]); 
        b = FF ( b, c, d, a, x[ 3], S14, K[3]); 
        a = FF ( a, b, c, d, x[ 4], S11, K[4]); 
        d = FF ( d, a, b, c, x[ 5], S12, K[5]);
        c = FF ( c, d, a, b, x[ 6], S13, K[6]);
        b = FF ( b, c, d, a, x[ 7], S14, K[7]);
        a = FF ( a, b, c, d, x[ 8], S11, K[8]);
        d = FF ( d, a, b, c, x[ 9], S12, K[9]);
        c = FF ( c, d, a, b, x[10], S13, K[10]);
        b = FF ( b, c, d, a, x[11], S14, K[11]);
        a = FF ( a, b, c, d, x[12], S11, K[12]);
        d = FF ( d, a, b, c, x[13], S12, K[13]);
        c = FF ( c, d, a, b, x[14], S13, K[14]);
        b = FF ( b, c, d, a, x[15], S14, K[15]);

        /* Round 2 */
        a = GG ( a, b, c, d, x[ 1], S21, K[16]);
        d = GG ( d, a, b, c, x[ 6], S22, K[17]);
        c = GG ( c, d, a, b, x[11], S23, K[18]);
        b = GG ( b, c, d, a, x[ 0], S24, K[19]);
        a = GG ( a, b, c, d, x[ 5], S21, K[20]);
        d = GG ( d, a, b, c, x[10], S22,  K[21]);
        c = GG ( c, d, a, b, x[15], S23, K[22]);
        b = GG ( b, c, d, a, x[ 4], S24, K[23]);
        a = GG ( a, b, c, d, x[ 9], S21, K[24]);
        d = GG ( d, a, b, c, x[14], S22, K[25]);
        c = GG ( c, d, a, b, x[ 3], S23, K[26]);
        b = GG ( b, c, d, a, x[ 8], S24, K[27]);
        a = GG ( a, b, c, d, x[13], S21, K[28]);
        d = GG ( d, a, b, c, x[ 2], S22, K[29]);
        c = GG ( c, d, a, b, x[ 7], S23, K[30]);
        b = GG ( b, c, d, a, x[12], S24, K[31]);

        /* Round 3 */
        a = HH ( a, b, c, d, x[ 5], S31, K[32]);
        d = HH ( d, a, b, c, x[ 8], S32, K[33]);
        c = HH ( c, d, a, b, x[11], S33, K[34]);
        b = HH ( b, c, d, a, x[14], S34, K[35]);
        a = HH ( a, b, c, d, x[ 1], S31, K[36]);
        d = HH ( d, a, b, c, x[ 4], S32, K[37]);
        c = HH ( c, d, a, b, x[ 7], S33, K[38]);
        b = HH ( b, c, d, a, x[10], S34, K[39]);
        a = HH ( a, b, c, d, x[13], S31, K[40]);
        d = HH ( d, a, b, c, x[ 0], S32, K[41]);
        c = HH ( c, d, a, b, x[ 3], S33, K[42]);
        b = HH ( b, c, d, a, x[ 6], S34,  K[43]);
        a = HH ( a, b, c, d, x[ 9], S31, K[44]);
        d = HH ( d, a, b, c, x[12], S32, K[45]);
        c = HH ( c, d, a, b, x[15], S33, K[46]);
        b = HH ( b, c, d, a, x[ 2], S34, K[47]);

        /* Round 4 */
        a = II ( a, b, c, d, x[ 0], S41, K[48]);
        d = II ( d, a, b, c, x[ 7], S42, K[49]);
        c = II ( c, d, a, b, x[14], S43, K[50]);
        b = II ( b, c, d, a, x[ 5], S44, K[51]);
        a = II ( a, b, c, d, x[12], S41, K[52]);
        d = II ( d, a, b, c, x[ 3], S42, K[53]);
        c = II ( c, d, a, b, x[10], S43, K[54]);
        b = II ( b, c, d, a, x[ 1], S44, K[55]);
        a = II ( a, b, c, d, x[ 8], S41, K[56]);
        d = II ( d, a, b, c, x[15], S42, K[57]);
        c = II ( c, d, a, b, x[ 6], S43, K[58]);
        b = II ( b, c, d, a, x[13], S44, K[59]);
        a = II ( a, b, c, d, x[ 4], S41, K[60]);
        d = II ( d, a, b, c, x[11], S42, K[61]);
        c = II ( c, d, a, b, x[ 2], S43, K[62]);
        b = II ( b, c, d, a, x[ 9], S44, K[63]);

        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;
    }
}
