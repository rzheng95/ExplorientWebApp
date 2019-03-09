package com.rzheng.login;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class NonceGenerator {
    private  SecureRandom random = new SecureRandom();

    public String nextNonce() {
        return new BigInteger(500, random).toString(32);
    }
    
}