/*
 * This file is part of Explorient Web App
 * Copyright (C) 2016-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/ExplorientWebApp
 * 
 * All Right Reserved.
 */

package com.login;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class NonceGenerator {
    private  SecureRandom random = new SecureRandom();

    public String nextNonce() {
        return new BigInteger(500, random).toString(32);
    }
    
}