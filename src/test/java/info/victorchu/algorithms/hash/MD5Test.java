package info.victorchu.algorithms.hash;

import info.victorchu.utils.ByteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class MD5Test {

    @Test
    void test() throws NoSuchAlgorithmException {
        Assertions.assertEquals(md5(),md5Jdk());
    }

    String md5() {
        String password = "ILoveJava";
        MD5 md5 = new MD5();
        byte[] digest= md5.md5(password.getBytes());
        String myHash = ByteUtils.bytesToHex(digest).toUpperCase();
        System.out.println("md5:"+myHash);
        return myHash;
    }

    String md5Jdk() throws NoSuchAlgorithmException {
        String password = "ILoveJava";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = ByteUtils.bytesToHex(digest).toUpperCase();
        System.out.println("md5Jdk:"+myHash);
        return myHash;
    }
}