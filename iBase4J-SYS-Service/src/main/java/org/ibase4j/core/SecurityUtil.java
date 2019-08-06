package org.ibase4j.core;
import java.util.Map;

import top.ibase4j.core.support.security.BASE64Encoder;
import top.ibase4j.core.support.security.Hex;
import top.ibase4j.core.support.security.coder.DESCoder;
import top.ibase4j.core.support.security.coder.HmacCoder;
import top.ibase4j.core.support.security.coder.MDCoder;
import top.ibase4j.core.support.security.coder.RSACoder;
import top.ibase4j.core.support.security.coder.SHACoder;
public class SecurityUtil {
	 private SecurityUtil() {
	    }

	    /**
	     * 榛樿绠楁硶瀵嗛挜
	     */
	    private static final byte[] ENCRYPT_KEY = {-81, 0, 105, 7, -32, 26, -49, 88};

	    public static final String UTF8 = "UTF-8";

	    /**
	     * BASE64瑙ｇ爜
	     * @param str
	     * @return
	     */
	    public static final byte[] decryptBASE64(String str) {
	        try {
	            return new BASE64Encoder().decode(str);
	        } catch (Exception e) {
	            throw new RuntimeException("瑙ｅ瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * BASE64缂栫爜
	     * @param str
	     * @return
	     */
	    public static final String encryptBASE64(byte[] str) {
	        try {
	            return new BASE64Encoder().encode(str);
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * 鏁版嵁瑙ｅ瘑锛岀畻娉曪紙DES锛�
	     *
	     * @param cryptData
	     *            鍔犲瘑鏁版嵁
	     * @return 瑙ｅ瘑鍚庣殑鏁版嵁
	     */
	    public static final String decryptDes(String cryptData) {
	        return decryptDes(cryptData, ENCRYPT_KEY);
	    }

	    /**
	     * 鏁版嵁鍔犲瘑锛岀畻娉曪紙DES锛�
	     *
	     * @param data
	     *            瑕佽繘琛屽姞瀵嗙殑鏁版嵁
	     * @return 鍔犲瘑鍚庣殑鏁版嵁
	     */
	    public static final String encryptDes(String data) {
	        return encryptDes(data, ENCRYPT_KEY);
	    }

	    /**
	     * 鍩轰簬MD5绠楁硶鐨勫崟鍚戝姞瀵�
	     *
	     * @param strSrc
	     *            鏄庢枃
	     * @return 杩斿洖瀵嗘枃
	     */
	    public static final String encryptMd5(String strSrc) {
	        String outString = null;
	        try {
	            outString = encryptBASE64(MDCoder.encodeMD5(strSrc));
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	        return outString;
	    }

	    /**
	     * 鍩轰簬MD5绠楁硶鐨勫崟鍚戝姞瀵�
	     *
	     * @param strSrc
	     *            鏄庢枃
	     * @return 杩斿洖瀵嗘枃
	     */
	    public static final String md5(String strSrc) {
	        String outString = null;
	        try {
	            outString = Hex.encodeHexString(MDCoder.encodeMD5(strSrc));
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	        return outString;
	    }

	    /**
	     * SHA鍔犲瘑
	     *
	     * @param data
	     * @return
	     */
	    public static final String encryptSHA(String data) {
	        try {
	            return encryptBASE64(SHACoder.encodeSHA256(data));
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * 鏁版嵁瑙ｅ瘑锛岀畻娉曪紙DES锛�
	     *
	     * @param cryptData
	     *            鍔犲瘑鏁版嵁
	     * @param key
	     * @return 瑙ｅ瘑鍚庣殑鏁版嵁
	     */
	    public static final String decryptDes(String cryptData, byte[] key) {
	        String decryptedData = null;
	        try {
	            // 鎶婂瓧绗︿覆瑙ｇ爜涓哄瓧鑺傛暟缁勶紝骞惰В瀵�
	            decryptedData = new String(DESCoder.decrypt(decryptBASE64(cryptData), key));
	        } catch (Exception e) {
	            throw new RuntimeException("瑙ｅ瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	        return decryptedData;
	    }

	    /**
	     * 鏁版嵁鍔犲瘑锛岀畻娉曪紙DES锛�
	     *
	     * @param data
	     *            瑕佽繘琛屽姞瀵嗙殑鏁版嵁
	     * @param key
	     * @return 鍔犲瘑鍚庣殑鏁版嵁
	     */
	    public static final String encryptDes(String data, byte[] key) {
	        String encryptedData = null;
	        try {
	            // 鍔犲瘑锛屽苟鎶婂瓧鑺傛暟缁勭紪鐮佹垚瀛楃涓�
	            encryptedData = encryptBASE64(DESCoder.encrypt(data.getBytes(), key));
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	        return encryptedData;
	    }

	    /**
	     * RSA绛惧悕
	     *
	     * @param data
	     *            鍘熸暟鎹�
	     * @param privateKey
	     * @return
	     */
	    public static final String signRSA(String data, String privateKey) {
	        try {
	            return encryptBASE64(RSACoder.sign(data.getBytes(UTF8), decryptBASE64(privateKey)));
	        } catch (Exception e) {
	            throw new RuntimeException("绛惧悕閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * RSA楠岀
	     *
	     * @param data
	     *            鍘熸暟鎹�
	     * @param publicKey
	     * @param sign
	     * @return
	     */
	    public static final boolean verifyRSA(String data, String publicKey, String sign) {
	        try {
	            return RSACoder.verify(data.getBytes(UTF8), decryptBASE64(publicKey), decryptBASE64(sign));
	        } catch (Exception e) {
	            throw new RuntimeException("楠岀閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * 鏁版嵁鍔犲瘑锛岀畻娉曪紙RSA锛�
	     *
	     * @param data
	     *            鏁版嵁
	     * @param privateKey
	     * @return 鍔犲瘑鍚庣殑鏁版嵁
	     */
	    public static final String encryptRSAPrivate(String data, String privateKey) {
	        try {
	            return encryptBASE64(RSACoder.encryptByPrivateKey(data.getBytes(UTF8), decryptBASE64(privateKey)));
	        } catch (Exception e) {
	            throw new RuntimeException("瑙ｅ瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * 鏁版嵁瑙ｅ瘑锛岀畻娉曪紙RSA锛�
	     *
	     * @param cryptData
	     *            鍔犲瘑鏁版嵁
	     * @param publicKey
	     * @return 瑙ｅ瘑鍚庣殑鏁版嵁
	     */
	    public static final String decryptRSAPublic(String cryptData, String publicKey) {
	        try {
	            // 鎶婂瓧绗︿覆瑙ｇ爜涓哄瓧鑺傛暟缁勶紝骞惰В瀵�
	            return new String(RSACoder.decryptByPublicKey(decryptBASE64(cryptData), decryptBASE64(publicKey)));
	        } catch (Exception e) {
	            throw new RuntimeException("瑙ｅ瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    /**
	     * HMAC鍔犲瘑
	     * @param type HmacMD2/HmacMD4/HmacMD5/HmacSHA1/HmacSHA224/HmacSHA256/HmacSHA512
	     * @return
	     */
	    public static final String initHmacKey(String type) {
	        try {
	            return encryptBASE64(HmacCoder.initHmacKey(type));
	        } catch (Exception e) {
	            throw new RuntimeException("鑾峰彇HMAC瀵嗛挜澶辫触锛�", e);
	        }
	    }

	    /**
	     * HMAC鍔犲瘑
	     * @param type HmacMD2/HmacMD4/HmacMD5/HmacSHA1/HmacSHA224/HmacSHA256/HmacSHA512
	     * @param data
	     * @param key initHmacKey
	     * @return
	     */
	    public static final String encryptHMAC(String type, String data, String key) {
	        try {
	            return HmacCoder.encodeHmacHex(type, data.getBytes(UTF8), decryptBASE64(key));
	        } catch (Exception e) {
	            throw new RuntimeException("鍔犲瘑閿欒锛岄敊璇俊鎭細", e);
	        }
	    }

	    public static String encryptPassword(String password) {
	        return encryptMd5(SecurityUtil.encryptSHA(password));
	    }

	    public static void main(String[] args) throws Exception {
	        System.out.println(encryptDes("SHJR"));
	        System.out.println(decryptDes("INzvw/3Qc4q="));
	        System.out.println(encryptMd5("SHJR"));
	        System.out.println(encryptSHA("1"));
	        Map<String, Object> key = RSACoder.initKey();
	        String privateKey = encryptBASE64(RSACoder.getPrivateKey(key));
	        String publicKey = encryptBASE64(RSACoder.getPublicKey(key));
	        System.out.println(privateKey);
	        System.out.println(publicKey);
	        String sign = signRSA("132", privateKey);
	        System.out.println(sign);
	        String encrypt = encryptRSAPrivate("132", privateKey);
	        System.out.println(encrypt);
	        String org = decryptRSAPublic(encrypt, publicKey);
	        System.out.println(org);
	        System.out.println(verifyRSA(org, publicKey, sign));

	        // System.out.println("-------鍒楀嚭鍔犲瘑鏈嶅姟鎻愪緵鑰�-----");
	        // Provider[] pro = Security.getProviders();
	        // for (Provider p : pro) {
	        // System.out.println("Provider:" + p.getName() + " - version:" +
	        // p.getVersion());
	        // System.out.println(p.getInfo());
	        // }
	        // System.out.println("");
	        // System.out.println("-------鍒楀嚭绯荤粺鏀寔鐨勬秷鎭憳瑕佺畻娉曪細");
	        // for (String s : Security.getAlgorithms("MessageDigest")) {
	        // System.out.println(s);
	        // }
	        // System.out.println("-------鍒楀嚭绯荤粺鏀寔鐨勭敓鎴愬叕閽ュ拰绉侀挜瀵圭殑绠楁硶锛�");
	        // for (String s : Security.getAlgorithms("KeyPairGenerator")) {
	        // System.out.println(s);
	        // }
	    }
}
