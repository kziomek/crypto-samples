package com.gitrepo.kziomek;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Krzysztof Ziomek
 * @since 03/03/2016.
 */
public class SymmetricCipherTest {


    private SymmetricCipher symmetricCipher;

    @Before
    public void init(){
        symmetricCipher = new SymmetricCipher();
    }

    @Test
    public void shouldCorrectlyEncryptAndDecrypt() {

        String plainText = "This is text to be encrypted.";

        //encrypt
        EncryptionDTO encryptionDTO = symmetricCipher.encrypt(plainText);
        //decrypt
        String decryptedText = symmetricCipher.decrypt(encryptionDTO);

        Assert.assertEquals(plainText, decryptedText);
        Assert.assertNotNull(encryptionDTO);
        Assert.assertNotNull(encryptionDTO.getIv());
        Assert.assertNotNull(encryptionDTO.getCt());
    }


}
