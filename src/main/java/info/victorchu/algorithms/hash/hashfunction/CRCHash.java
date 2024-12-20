package info.victorchu.algorithms.hash.hashfunction;

public class CRCHash
{
    public static byte crc8BitShift(byte[] byteVal)
    {
        final byte generator = 0x1D;
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
                    crc = (byte) (crc ^ generator);
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
        byte generator = 0x1D;
        // 省去了一开始的 8 次 移位
        byte crc = byteVal;

        for (int i = 0; i < 8; i++) {
            if ((crc & 0x80) != 0) {
                // 每一次MSB都是会异或得0,其实是把信息的最高位和多项式的多高位一起去掉了。
                // 所以每一次信息都是先移位在异或。
                crc = (byte) ((crc << 1) ^ generator);
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
        byte generator = 0x1D;
        byte crc = 0;

        for (byte currByte : bytes) {
            /* XOR-in the next input byte */
            crc ^= currByte;

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (byte) ((crc << 1) ^ generator);
                }
                else {
                    crc <<= 1;
                }
            }
        }
        return crc;
    }
}
