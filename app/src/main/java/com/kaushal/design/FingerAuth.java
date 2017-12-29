package com.kaushal.design;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerAuth extends AppCompatActivity {

    private TextView textView;
    private static final String KEY_NAME = "SwA";

    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;

    private FingerprintManager fingerprintmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_auth);

        textView = findViewById(R.id.FingerStatus);
        Button button = findViewById(R.id.btnauth);

        final FingerprintHandler fph = new FingerprintHandler(textView);

        if (!checkFinger()) {
            button.setEnabled(false);
        } else {
            try {
                generateKey();
                Cipher cipher = generateCipher();
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
            } catch (FingerprintException e) {
                button.setEnabled(false);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                textView.setText("Swipe Your Finger");
                fph.doAuth(fingerprintmanager, cryptoObject);
            }
        });
    }//End of onCreateMethod

    @SuppressLint("SetTextI18n")
    private boolean checkFinger() {
        // Keyguard Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        // Fingerprint Manager
        fingerprintmanager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        try {
            // Check if the fingerprint sensor is present
            assert fingerprintmanager != null;
            if (!fingerprintmanager.isHardwareDetected()) {
                textView.setText("Fingerprint authentication not supported");
                return false;
            }
            if (!fingerprintmanager.hasEnrolledFingerprints()) {
                textView.setText("No fingerprint configured.");
                return false;
            }
            assert keyguardManager != null;
            if (!keyguardManager.isKeyguardSecure()) {
                textView.setText("Secure lock screen not enabled");
                return false;
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        }
        return true;
    }

    private void generateKey() throws FingerprintException {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CertificateException | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    private Cipher generateCipher() throws FingerprintException {
        try {
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnrecoverableKeyException | KeyStoreException exc) {
            throw new FingerprintException(exc);
        }
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }
}//end of FingerAuth