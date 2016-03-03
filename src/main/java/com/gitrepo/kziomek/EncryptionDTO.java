package com.gitrepo.kziomek;

/**
 * @author Krzysztof Ziomek
 * @since 03/03/2016.
 */
public class EncryptionDTO {

    private String iv;
    private String ct;

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }
}
