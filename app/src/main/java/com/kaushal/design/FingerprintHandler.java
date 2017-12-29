package com.kaushal.design;

import android.annotation.SuppressLint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private TextView tv;

    public FingerprintHandler(TextView tv) {
        this.tv = tv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        tv.setText("Authentication Error!");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        tv.setText("Auth Successfully");
        tv.setTextColor(tv.getContext().getResources().getColor(android.R.color.holo_blue_bright));
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }

    public void doAuth(FingerprintManager manager, FingerprintManager.CryptoObject object) {
        CancellationSignal signal = new CancellationSignal();

        try {
            manager.authenticate(object, signal, 0, this, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}