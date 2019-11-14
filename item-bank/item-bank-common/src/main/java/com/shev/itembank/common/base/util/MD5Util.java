package com.shev.itembank.common.base.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util
{

    public static String getMD5String(String input) throws Exception
    {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bArr = input.getBytes("UTF-8");
        messageDigest.update(bArr);
        byte[] messageValue = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, messageValue);
        String str = bigInt.toString(16);
        while (str.length() < 32)
            str = "0" + str;
        return str;
    }

}
