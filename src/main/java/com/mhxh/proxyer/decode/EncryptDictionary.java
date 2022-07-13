package com.mhxh.proxyer.decode;

import io.netty.buffer.ByteBufUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptDictionary {


    public static Map<String, String> encodeMap = new ConcurrentHashMap<>();
    /**
     * 原始的字符
     */
    public static Map<String, String> originalEncodeMap = new ConcurrentHashMap<>();


    public static Map<String, String> decodeMap = new ConcurrentHashMap<>();


    private static final String[] encode = new String[]{
            "VP,", "Zu,", "Iv,", "yP,", "BZ,", "wd,", "5R,", "8v,", "Wa,", "q6,"
            , "0W,", "Cx,", "ET,", "fN,", "nB,", "xi,", "Aa,", "hY,", "m9,", "Sw,"
            , "Lq,", "jO,", "mP,", "aW,", "JA,", "Uc,", "ab,", "Yx,", "P8,"
            , "CO,", "S9,", "wu,", "My,", "vt,", "Hu,", "DG,"
            , "vd,", "j3,", "cj,", "gZ,", "de,", "Qi,", "u2,", "Au,", "A2,", "is,"
            , "f4,", "9W,", "wO,", "pF,", "Ve,", "Dl,", "DT,", "XC,", "PW,"
            , "Pf,", "NR,", "cK,", "es,", "6b,", "2l,", "qL,",


    };

    private static final String[] decode = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l",
            "z", "x", "c", "v", "b", "n", "m",
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
            "Z", "X", "C", "V", "B", "N", "M",
    };


    static {
        for (int i = 0; i < encode.length; i++) {
            encodeMap.put(ByteBufUtil.hexDump(encode[i].getBytes(Charset.forName("GBK"))), ByteBufUtil.hexDump(decode[i].getBytes(Charset.forName("GBK"))));
            decodeMap.put(ByteBufUtil.hexDump(decode[i].getBytes(Charset.forName("GBK"))), ByteBufUtil.hexDump(encode[i].getBytes(Charset.forName("GBK"))));
            originalEncodeMap.put(encode[i], decode[i]);
        }
    }


    public static String DecodeEncryptString(String hexCode) {
        for (String key : encodeMap.keySet()) {
            hexCode = hexCode.replaceAll(key, encodeMap.get(key));
        }
        return hexCode;
    }

    public static String decodeEncryptFromOriginalString(String gbkCode) {
        for (String key : originalEncodeMap.keySet()) {
            gbkCode = gbkCode.replaceAll(key, originalEncodeMap.get(key));
        }
        return gbkCode;
    }
}
