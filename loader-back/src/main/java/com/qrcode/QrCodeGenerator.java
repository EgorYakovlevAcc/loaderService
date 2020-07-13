package com.qrcode;

import com.model.user.Porter;

public interface QrCodeGenerator {
    void processGeneratingQrCode(Porter porter);
    byte[] getQrCode();
}
