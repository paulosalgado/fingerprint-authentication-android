package br.com.testes.fingerprint_authentication_tests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.USE_FINGERPRINT;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    private Button btnLerDigital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        btnLerDigital = (Button) findViewById(R.id.btn_ler_digital);
        btnLerDigital.setOnClickListener(btnLerDigital_OnClickListener());
    }

    private OnClickListener btnLerDigital_OnClickListener() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(
                        MainActivity.this, USE_FINGERPRINT) != PERMISSION_GRANTED) {

                    return;
                }

                FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);

                if (!fingerprintManager.isHardwareDetected()
                        || !fingerprintManager.hasEnrolledFingerprints()) {

                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_fingerprint_unavailable),
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setView(R.layout.fingerprint_dialog)
                        .setNegativeButton(R.string.dialog_fingerprint_cancel,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }

                        })
                        .create();

                alertDialog.show();
            }

        };
    }

}
