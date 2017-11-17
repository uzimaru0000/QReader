package uzimaru.qreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class ReadingActivity extends AppCompatActivity {

    private CompoundBarcodeView compoundBarcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        compoundBarcodeView = (CompoundBarcodeView) findViewById(R.id.qr_reader);
        compoundBarcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Intent intent = new Intent(getApplication(), ResultActivity.class);
                intent.putExtra("result", result.getText());
                startActivity(intent);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        compoundBarcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compoundBarcodeView.pause();
    }
}
