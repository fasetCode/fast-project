package com.fastproject.utils.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class SM2KeyGenerator {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /** 生成 SM2 公私钥 HEX */
    public static String[] generateKeyPairHex() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("sm2p256v1"), new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 私钥 HEX
        BigInteger d = ((org.bouncycastle.jce.interfaces.ECPrivateKey) keyPair.getPrivate()).getD();
        String privateHex = d.toString(16);

        // 公钥 HEX（未压缩 04 + X + Y）
        org.bouncycastle.jce.interfaces.ECPublicKey ecPub = (org.bouncycastle.jce.interfaces.ECPublicKey) keyPair.getPublic();
        BigInteger x = ecPub.getQ().getAffineXCoord().toBigInteger();
        BigInteger y = ecPub.getQ().getAffineYCoord().toBigInteger();
        String publicHex = "04" + x.toString(16) + y.toString(16);

        return new String[]{publicHex, privateHex};
    }


}