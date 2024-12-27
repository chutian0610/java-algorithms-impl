package info.victorchu.algorithms.hash.hashfunction;

import info.victorchu.utils.ByteUtils;
import org.junit.jupiter.api.Test;

class CRCHashTest
{

    @Test
    void crc8ByteByTable()
    {
        byte crc = CRCHash.crc8ByteByTable(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.byteToHex(crc));
    }

    @Test
    void crc16ByteShift()
    {
        short crc = CRCHash.crc16ByteShift(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.bytesToHex(ByteUtils.unsignedShortToBytes(crc)));
    }
}