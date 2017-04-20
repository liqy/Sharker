package com.sharker.network.volley;

import java.io.InputStream;


public interface CertificateEncryption {
    public InputStream decode(InputStream in);
}
