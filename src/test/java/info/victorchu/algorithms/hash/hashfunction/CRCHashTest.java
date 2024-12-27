package info.victorchu.algorithms.hash.hashfunction;

import info.victorchu.utils.ByteUtils;
import org.junit.jupiter.api.Test;

class CRCHashTest
{

    @Test
    void crc8ByteByTable()
    {
        byte crc1 = CRCHash.crc8ByteByTable(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.byteToHex(crc1));
        byte crc2 = CRCHash.crc8ByteByTable(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.byteToHex(crc2));
    }

    @Test
    void crc16ByteShift()
    {
        short crc1 = CRCHash.crc16ByteShift(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.bytesToHex(ByteUtils.unsignedShortToBytes(crc1)));
        short crc2 = CRCHash.crc16ByteByTable(ByteUtils.hexStringToByteArray("0102"));
        System.out.println(ByteUtils.bytesToHex(ByteUtils.unsignedShortToBytes(crc2)));
    }
}