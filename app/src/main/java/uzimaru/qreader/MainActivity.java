package uzimaru.qreader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.start_toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);

        // ボタンのイベント設定
        Button readBtn = (Button) findViewById(R.id.reading_button);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RuntimePermissionUnits.hasSelfPermissions(MainActivity.this, Manifest.permission.CAMERA)) {
                    readingActivity();
                } else {
                    requestPermissions(new String[] { Manifest.permission.CAMERA }, REQUEST_CODE);
                }
            }
        });

        Button createBtn = (Button) findViewById(R.id.create_button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), CreateActivity.class);
                startActivity(intent);
            }
        });

    }

    private void readingActivity() {
        Intent intent = new Intent(this, ReadingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);

        if (requestCode == requestCode && grantResult.length > 0) {
            if (!RuntimePermissionUnits.checkGrantResults(grantResult)) {
                if (RuntimePermissionUnits.shouldShowRequestPermissionRational(MainActivity.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(MainActivity.this, "権限がないです", Toast.LENGTH_SHORT).show();
                } else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            RuntimePermissionUnits.showAlertDialog(getSupportFragmentManager(), "カメラ");
                        }
                    });
                }
            } else {
                readingActivity();
            }
        }
    }

}
