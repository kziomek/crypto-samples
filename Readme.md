#Crypto Samples

This project exemplify encryption sample with Symmetric Cipher `AES/CBC/ISO10126Padding`.
Cipher uses secret key generated with 4 parameters: `password`, `salt`, `number of iterations` and `key length`.

# Prerequisites:

+ Java 8
+ JCE
+ Maven

# Run Test

    mvn -Dtest=SymmetricCipherTest#shouldCorrectlyEncryptAndDecrypt test
    
**Notice:**
Key generator uses 256 key length. 
If you get exception while test running you can change key length to 128 value which is supported by standard java installation.
If you want to run example with 256 value you have to install Java Cryptography Extension (JCE) into your $JAVA_HOME/jre/lib/security/ folder.

