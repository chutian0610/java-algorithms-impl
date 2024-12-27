package info.victorchu.utils;

public class ByteUtils {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static String byteToHex(byte bytes) {
        char[] hexChars = new char[2];
        int v = bytes & 0xFF;
        hexChars[0] = HEX_ARRAY[v >>> 4];
        hexChars[ + 1] = HEX_ARRAY[v & 0x0F];
        return new String(hexChars);
    }

    public static byte[] unsignedShortToBytes(short value)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((value >> 8) & 0xFF); // Higher-order byte
        bytes[1] = (byte) (value & 0xFF);       // Lower-order byte
        return bytes;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
