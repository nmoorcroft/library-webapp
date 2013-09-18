package com.zuhlke.library.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static final String HASH_ALGORITHM = "SHA-512";
    private static final int ITERATIONS = 10;
    private static final String MASTER_SALT = "uUDDkQ5n9KrBiIOt+t8uCz4ms0g=";
    private static final String ENCODING = "UTF-8";
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    
    private SecureRandom secureRandom = null;
    private MessageDigest digest = null;

    @PostConstruct
    public void init() {
        try {
            secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
            digest =  MessageDigest.getInstance(HASH_ALGORITHM);
            
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    public String hash(final String plaintext, final String salt) {
        return hash(plaintext, salt, ITERATIONS);
    }

    private String hash(final String plaintext, final String salt, final int iterations) {
        byte[] bytes = null;

        try {
            digest.reset();
            digest.update(MASTER_SALT.getBytes("UTF-8"));
            digest.update(salt.getBytes(ENCODING));
            digest.update(plaintext.getBytes(ENCODING));

            bytes = digest.digest();
            for (int i = 0; i < iterations; ++i) {
                digest.reset();
                bytes = digest.digest(bytes);
            }

            return Base64.encodeBase64String(bytes);
            
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    public String generateRandomString(final int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, secureRandom);
    }


}
