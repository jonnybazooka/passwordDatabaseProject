package org.sda.authentication.hashFunction;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA256 implements HashFunction {

    @Override
    public String hash(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
