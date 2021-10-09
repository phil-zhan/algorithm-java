package org.study.class34;

import org.study.class33.Hash;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @author phil
 * @date 2021/10/8 10:38
 */
public class Code01_Diff {

    private MessageDigest hash;

    public Code01_Diff(String algorithm) {
        try {
            hash = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String hashCode(String input) {
        return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
    }



    public static void main(String[] args) {


        String algorithm = "MD5";
        Code01_Diff hash = new Code01_Diff(algorithm);
        String[] str=new String[1000];
        for (int i = 0; i < 1000; i++) {
            str[i] = "zuochengyunzuochengyun"+i;
        }

        for (int i = 0; i < str.length; i++) {
            String s = hash.hashCode(str[i]);
            System.out.println(s);
        }



    }
}
