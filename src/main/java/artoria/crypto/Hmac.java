package artoria.crypto;

import artoria.util.Assert;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import static artoria.common.Constants.DEFAULT_CHARSET_NAME;

/**
 * Hmac tools.
 * In JDK, support list:
 *  HMAC MD5
 *  HMAC SHA1
 *  HMAC SHA256
 *  HMAC SHA384
 *  HMAC SHA512
 *
 * @author Kahle
 */
public class Hmac {
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String HMAC_SHA384 = "HmacSHA384";
    public static final String HMAC_SHA512 = "HmacSHA512";

    private String charset = DEFAULT_CHARSET_NAME;
    private String algorithm;
    private byte[] key;

    public Hmac(String algorithm) {
        this.setAlgorithm(algorithm);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        Assert.notBlank(charset, "Parameter \"charset\" must not blank. ");
        this.charset = charset;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        Assert.notBlank(algorithm, "Parameter \"algorithm\" must not blank. ");
        this.algorithm = algorithm;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        Assert.notNull(key, "Parameter \"key\" must not null. ");
        this.key = key;
    }

    public void setKey(String key) {
        Assert.notNull(key, "Parameter \"key\" must not null. ");
        Charset charset = Charset.forName(this.charset);
        this.key = key.getBytes(charset);
    }

    public byte[] calc(String data) throws GeneralSecurityException {
        Assert.notNull(data, "Parameter \"data\" must not null. ");
        Charset charset = Charset.forName(this.charset);
        byte[] bytes = data.getBytes(charset);
        return this.calc(bytes);
    }

    public byte[] calc(byte[] data) throws GeneralSecurityException {
        Assert.notNull(data, "Parameter \"data\" must not null. ");
        Assert.notNull(key, "Parameter \"key\" must not null. ");
        SecretKey secretKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKey);
        return mac.doFinal(data);
    }

}