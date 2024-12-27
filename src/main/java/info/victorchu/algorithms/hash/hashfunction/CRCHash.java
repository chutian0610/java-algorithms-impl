package info.victorchu.algorithms.hash.hashfunction;

public class CRCHash
{

    public static final byte generator_8 = 0x1D;
    public static final byte[] crcTable_8 = calCrc8Table();
    public static final short generator_16 = 0x1021;
    public static final short[] crcTable_16 = calCrc16Table();

    public static byte crc8BitShift(byte[] byteVal)
    {
        byte crc = 0; /* init crc register */
        byte[] data = new byte[byteVal.length + 1];
        System.arraycopy(byteVal, 0, data, 0, byteVal.length);
        data[byteVal.length + 1] = 0x00;

        for (byte b : data) {
            for (int i = 7; i >= 0; i--) {
                /* 检查 MSB ==1 ? */
                if ((crc & 0x80) != 0) {
                    crc = (byte) (crc << 1);
                    /* 读入 1 位
                     * 如果是 1 , 设置 LSB 为 1.
                     * 如果是 0 , 设置 LSB 为 0.
                     */
                    crc = ((byte) (b & (1 << i)) != 0) ? (byte) (crc | 0x01) : (byte) (crc & 0xFE);
                    // 异或计算
                    crc = (byte) (crc ^ generator_8);
                }
                else {
                    // crc 左移 1 位
                    crc = (byte) (crc << 1);
                    crc = ((byte) (b & (1 << i)) != 0) ? (byte) (crc | 0x01) : (byte) (crc & 0xFE);
                }
            }
        }
        return crc;
    }

    public static byte crcByte(byte byteVal)
    {
        // 省去了一开始的 8 次 移位
        byte crc = byteVal;

        for (int i = 0; i < 8; i++) {
            if ((crc & 0x80) != 0) {
                // 每一次MSB都是会异或得0,其实是把信息的最高位和多项式的多高位一起去掉了。
                // 所以每一次信息都是先移位在异或。
                crc = (byte) ((crc << 1) ^ generator_8);
            }
            else {
                // 左移
                crc <<= 1;
            }
        }
        return crc;
    }

    public static byte crc8ByteShift(byte[] bytes)
    {
        byte crc = 0;

        for (byte currByte : bytes) {
            /* XOR-in the next input byte */
            crc ^= currByte;

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (byte) ((crc << 1) ^ generator_8);
                }
                else {
                    crc <<= 1;
                }
            }
        }
        return crc;
    }

    public static byte[] calCrc8Table()
    {
        byte[] crcTable = new byte[256];
        for (int dividend = 0; dividend < 256; dividend++) {
            byte currByte = (byte) dividend;
            for (byte bit = 0; bit < 8; bit++) {
                if ((currByte & 0x80) != 0) {
                    currByte <<= 1;
                    currByte ^= generator_8;
                }
                else {
                    currByte <<= 1;
                }
            }
            crcTable[dividend] = currByte;
        }
        return crcTable;
    }

    public static byte crc8ByteByTable(byte[] bytes)
    {
        byte crc = 0;
        for (byte b : bytes) {
            byte data = (byte) (b ^ crc);
            crc = (byte) (crcTable_8[data]);
        }
        return crc;
    }

    public static short crc16ByteShift(byte[] bytes)
    {
        short crc = 0;
        for (byte b : bytes) {
            crc ^= (short) (b << 8);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (short) ((crc << 1) ^ generator_16);
                }
                else {
                    crc <<= 1;
                }
            }
        }
        return crc;
    }

    public static short[] calCrc16Table()
    {
        short[] crcTable = new short[256];

        for (int dividend = 0; dividend < 256; dividend++) {
            short curByte = (short) (dividend << 8);

            for (byte bit = 0; bit < 8; bit++) {
                if ((curByte & 0x8000) != 0) {
                    curByte <<= 1;
                    curByte ^= generator_16;
                }
                else {
                    curByte <<= 1;
                }
            }
            crcTable[dividend] = curByte;
        }
        return crcTable;
    }

    public static short crc16ByteByTable(byte[] bytes)
    {
        short crc = 0;
        for (byte b : bytes) {
            /* equal: ((crc ^ (b << 8)) >> 8), MSB是查找表的索引*/
            byte pos = (byte) ((crc >> 8) ^ b);
            crc = (short) ((crc << 8) ^ (crcTable_16[pos]));
        }
        return crc;
    }
}
