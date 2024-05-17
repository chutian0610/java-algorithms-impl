package info.victorchu.algorithms.text.diff;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MyersDiffTest {

    @Test
    void getEditScript01() throws IOException {
        Text textA = new Text("hello\nworld\njava\n");
        Text textB = new Text("hello\nvictor\njava\n");
        MyersDiff myersDiff = new MyersDiff(textA, textB);
        EditScript editScript = myersDiff.getEditScript();
        System.out.println(editScript.printDiff());
    }
    @Test
    void getEditScript02() throws IOException {
        Text textA = new Text("A\nB\nC\nA\nB\nB\nA");
        Text textB = new Text("C\nB\nA\nB\nA\nC");
        MyersDiff myersDiff = new MyersDiff(textA, textB);
        EditScript editScript = myersDiff.getEditScript();
        System.out.println(editScript.printDiff());
    }
    @Test
    void getEditScript03() throws IOException {
        Text textA = new Text("D\nA\nB\nC\nA\nB\nB\nA");
        Text textB = new Text("D\nC\nB\nA\nB\nA\nC");
        MyersDiff myersDiff = new MyersDiff(textA, textB);
        EditScript editScript = myersDiff.getEditScript();
        System.out.println(editScript.printDiff());
    }
    @Test
    void getEditScript04() throws IOException {
        Text textA = new Text("hello\nworld\njava\n");
        Text textB = new Text("hel\nvictor\njava\n");
        MyersDiff myersDiff = new MyersDiff(textA, textB);
        EditScript editScript = myersDiff.getEditScript();
        System.out.println(editScript.printDiff());
    }
    @Test
    void getEditScript05() throws IOException {
        String a= "void Chunk_copy(Chunk *src, size_t src_start, Chunk *dst, size_t dst_start, size_t n)\n" +
                "{\n" +
                "    if (!Chunk_bounds_check(src, src_start, n)) return;\n" +
                "    if (!Chunk_bounds_check(dst, dst_start, n)) return;\n" +
                "\n" +
                "    memcpy(dst->data + dst_start, src->data + src_start, n);\n" +
                "}\n" +
                "\n" +
                "int Chunk_bounds_check(Chunk *chunk, size_t start, size_t n)\n" +
                "{\n" +
                "    if (chunk == NULL) return 0;\n" +
                "\n" +
                "    return start <= chunk->length && n <= chunk->length - start;\n" +
                "}";
        String b = "int Chunk_bounds_check(Chunk *chunk, size_t start, size_t n)\n" +
                "{\n" +
                "    if (chunk == NULL) return 0;\n" +
                "\n" +
                "    return start <= chunk->length && n <= chunk->length - start;\n" +
                "}\n" +
                "\n" +
                "void Chunk_copy(Chunk *src, size_t src_start, Chunk *dst, size_t dst_start, size_t n)\n" +
                "{\n" +
                "    if (!Chunk_bounds_check(src, src_start, n)) return;\n" +
                "    if (!Chunk_bounds_check(dst, dst_start, n)) return;\n" +
                "\n" +
                "    memcpy(dst->data + dst_start, src->data + src_start, n);\n" +
                "}\n";
        Text textA = new Text(a);
        Text textB = new Text(b);
        MyersDiff myersDiff = new MyersDiff(textA, textB);
        EditScript editScript = myersDiff.getEditScript();
        System.out.println(editScript.printDiff());
    }
}