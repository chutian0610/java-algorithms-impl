package info.victorchu.algorithms.hash;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class MurmurHashTest {

    @Test
    void murmurhash3_x86_32() {
        System.out.println(MurmurHash.murmurhash3_x86_32_Str(123434556,"victorchu", StandardCharsets.UTF_8));
        System.out.println(MurmurHash.murmurhash3_x86_32_Str(123434556,"victorch", StandardCharsets.UTF_8));
    }

    @Test
    void murmurhash3_x64_128() {
        System.out.println(MurmurHash.murmurhash3_x64_128_Str(123434556,"victorchu", StandardCharsets.UTF_8));
        System.out.println(MurmurHash.murmurhash3_x64_128_Str(123434556,"victorch", StandardCharsets.UTF_8));
    }
}