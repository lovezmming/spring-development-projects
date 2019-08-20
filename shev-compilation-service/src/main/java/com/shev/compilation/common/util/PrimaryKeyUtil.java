package com.shev.compilation.common.util;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

public class PrimaryKeyUtil
{
    private static HashMap<String, RSAPrivateKey> privateKeyCache = new HashMap<String, RSAPrivateKey>();
    private static HashMap<String, RSAPublicKey> publicKeyCache = new HashMap<String, RSAPublicKey>();

    public static BigInteger publicExponent = new BigInteger("65537");

    public static BigInteger modulus = new BigInteger(
            "99736681816311187623570006105030202198556124169653863528661311976380311405920083544097958744849238808109320962512812894993758788723305805166666806732044986781553960108157157483258736413580904744644142258203460668183421412683299618701387483736867697544893567040138049779390625438084768774494174952229359789233");

    public static BigInteger privateExponent = new BigInteger(
            "45776880068276554064375631836051520242497645834005038603264297484619676931963259120900660680914065388222353396591015943381788369391291005377318729061444872369158314062718842220558398995903326551783539721144492551976040943397960567057393356112776175477458912025700132804999659644290836590601129024975197660673");

    public static String nextId(String isPublic, String tenantId, String serviceId)
    {
        Random random = new Random();
        String newid = isPublic
                + TextUtil.expandNum(Integer.valueOf(tenantId), "0", 6)
                + TextUtil.expandNum(Integer.valueOf(serviceId), "0", 6)
                + TextUtil.expandNum(random.nextInt(999999), "0", 6)
                + System.currentTimeMillis();
        return newid;
    }

    public static String password(String password) throws Exception
    {
        return publicKey(password, modulus, publicExponent);
    }

    public static String privateKey(String value, BigInteger modulus, BigInteger privateExponent) throws Exception
    {
        String key = modulus + "_" + privateExponent;
        RSAPrivateKey privateKey = privateKeyCache.get(key);
        if (privateKey == null)
        {
            synchronized (privateKeyCache)
            {
                privateKey = privateKeyCache.get(key);
                if (privateKey == null)
                {
                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, privateExponent);
                    privateKey = (RSAPrivateKey) factory.generatePrivate(keySpec);
                    privateKeyCache.put(key, privateKey);
                }
            }
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int keyLen = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = value.getBytes();
        byte[] bcd = TextUtil.ascii2bcd(bytes, bytes.length);
        Collection<byte[]> arrays = TextUtil.split(bcd, keyLen);
        StringBuffer buffer = new StringBuffer();
        for (byte[] arr : arrays)
            buffer.append(new String(cipher.doFinal(arr)));
        return buffer.toString();
    }

    public static String publicKey(String value, BigInteger modulus, BigInteger publicExponent) throws Exception
    {
        String key = modulus + "_" + publicExponent;
        RSAPublicKey publicKey = publicKeyCache.get(key);
        if (publicKey == null)
        {
            synchronized (publicKeyCache)
            {
                publicKey = publicKeyCache.get(key);
                if (publicKey == null)
                {
                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
                    publicKey = (RSAPublicKey) factory.generatePublic(keySpec);
                    publicKeyCache.put(key, publicKey);
                }
            }
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int keyLen = publicKey.getModulus().bitLength() / 8;
        List<String> datas = TextUtil.split(value, keyLen - 11);
        StringBuffer buffer = new StringBuffer();
        for (String data : datas)
            buffer.append(TextUtil.bcd2Str(cipher.doFinal(data.getBytes())));
        return buffer.toString();
    }

    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args)
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            System.out.println(publicKey.getModulus() + "#" + publicKey.getPublicExponent());
            System.out.println(privateKey.getModulus() + "#" + privateKey.getPrivateExponent());

            String tenantId = "123456";
//            String token = publicKey(tenantId, publicKey.getModulus(), publicKey.getPublicExponent());
//            System.out.println("encode public: " + token);
//            String privates = privateKey(token, privateKey.getModulus(), privateKey.getPrivateExponent());
//            System.out.println("decode private: " + privates);

            String s = publicKey(tenantId, modulus, publicExponent);
            String ss = privateKey(s, modulus, privateExponent);
            System.out.println("encode : " + s);
            System.out.println("encode s : " + ss);


            System.out.println("accessKey: " + randomUUID());
            System.out.println("secretKey: " + randomUUID());


            /*System.out.println("new id: " + PrimaryKeyUtil.nextId(
                    ServiceIdEnum.USER.getIsPublic(),
                    tenantId,
                    ServiceIdEnum.USER.getServiceId()));*/

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
