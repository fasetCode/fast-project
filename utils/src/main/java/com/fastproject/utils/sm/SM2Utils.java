package com.fastproject.utils.sm;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class SM2Utils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // ===== 生成 SM2 公私钥 =====
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("sm2p256v1"), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    // ===== 公钥 HEX =====
    public static String getPublicKeyHex(PublicKey publicKey) {
        org.bouncycastle.jce.interfaces.ECPublicKey ecPub = (org.bouncycastle.jce.interfaces.ECPublicKey) publicKey;
        BigInteger x = ecPub.getQ().getAffineXCoord().toBigInteger();
        BigInteger y = ecPub.getQ().getAffineYCoord().toBigInteger();
        return "04" + toFixedHex(x, 64) + toFixedHex(y, 64); // 未压缩前缀 04
    }

    // ===== 私钥 HEX =====
    public static String getPrivateKeyHex(PrivateKey privateKey) {
        org.bouncycastle.jce.interfaces.ECPrivateKey ecPri = (org.bouncycastle.jce.interfaces.ECPrivateKey) privateKey;
        return toFixedHex(ecPri.getD(), 64);
    }

    // ===== HEX 补全到固定长度 =====
    private static String toFixedHex(BigInteger value, int length) {
        String hex = value.toString(16);
        while (hex.length() < length) {
            hex = "0" + hex;
        }
        return hex;
    }

    // ===== HEX 转公钥对象 =====
    public static ECPublicKeyParameters publicKeyFromHex(String hex) {
        if (!hex.startsWith("04") || hex.length() != 130) {
            throw new IllegalArgumentException("公钥HEX格式不正确");
        }
        byte[] pubBytes = Hex.decode(hex);
        X9ECParameters sm2Params = GMNamedCurves.getByName("sm2p256v1");
        ECPoint point = sm2Params.getCurve().decodePoint(pubBytes);
        return new ECPublicKeyParameters(point, new ECDomainParameters(
                sm2Params.getCurve(), sm2Params.getG(), sm2Params.getN()));
    }

    // ===== HEX 转私钥对象 =====
    public static ECPrivateKeyParameters privateKeyFromHex(String hex) {
        if (hex.length() != 64) {
            throw new IllegalArgumentException("私钥HEX格式不正确");
        }
        BigInteger d = new BigInteger(hex, 16);
        X9ECParameters sm2Params = GMNamedCurves.getByName("sm2p256v1");
        return new ECPrivateKeyParameters(d, new ECDomainParameters(
                sm2Params.getCurve(), sm2Params.getG(), sm2Params.getN()));
    }

    // ===== 加密 =====
    public static String encrypt(String data, String pubHex) throws Exception {
        byte[] plain = data.getBytes();
        SM2Engine engine = new SM2Engine();
        ECPublicKeyParameters pubKey = publicKeyFromHex(pubHex);
        engine.init(true, new ParametersWithRandom(pubKey, new SecureRandom()));
        byte[] cipher = engine.processBlock(plain, 0, plain.length);
        return Hex.toHexString(cipher);
    }

    // ===== 解密 =====
    public static String decrypt(String cipherHex, String priHex) throws Exception {
        byte[] cipher = Hex.decode(cipherHex);
        SM2Engine engine = new SM2Engine();
        ECPrivateKeyParameters priKey = privateKeyFromHex(priHex);
        engine.init(false, priKey);
        byte[] plain = engine.processBlock(cipher, 0, cipher.length);
        return new String(plain);
    }

}