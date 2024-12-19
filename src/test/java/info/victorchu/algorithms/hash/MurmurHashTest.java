package info.victorchu.algorithms.hash;

import info.victorchu.algorithms.hash.hashfunction.MurmurHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class MurmurHashTest {

    @Test
    void murmurhash3_x86_32() {
        System.out.println("############### murmurhash3_x86_32 ###############");
        int hashResult1 = MurmurHash.murmurhash3_x86_32_Str(123434556,"victorchu", StandardCharsets.UTF_8);
        System.out.println("Test1>> key[victorchu]=> hash:"+hashResult1);
        int hashResult2 = MurmurHash.murmurhash3_x86_32_Str(123434556,"victorchu", StandardCharsets.UTF_8);
        System.out.println("Test2>> key[victorchu]=> hash:"+hashResult2);
        Assertions.assertTrue(hashResult1 == hashResult2);
        int hashResult3 = MurmurHash.murmurhash3_x86_32_Str(123434556,"victorch", StandardCharsets.UTF_8);
        System.out.println("Test3>> key[victorch]=> hash:"+hashResult3);
        Assertions.assertFalse(hashResult1 == hashResult3);
    }

    @Test
    void murmurhash3_x64_128() {
        System.out.println("############### murmurhash3_x64_128 ###############");
        MurmurHash.LongPair hashResult1 = MurmurHash.murmurhash3_x64_128_Str(123434556,"victorchu", StandardCharsets.UTF_8);
        System.out.println("Test1>> key[victorchu]=> hash:"+hashResult1);
        MurmurHash.LongPair hashResult2 = MurmurHash.murmurhash3_x64_128_Str(123434556,"victorchu", StandardCharsets.UTF_8);
        System.out.println("Test2>> key[victorchu]=> hash:"+hashResult2);
        Assertions.assertTrue(hashResult1.eq( hashResult2));
        MurmurHash.LongPair hashResult3 = MurmurHash.murmurhash3_x64_128_Str(123434556,"victorch", StandardCharsets.UTF_8);
        System.out.println("Test3>> key[victorch]=> hash:"+hashResult3);
        Assertions.assertFalse(hashResult1.eq(hashResult3));
    }
}