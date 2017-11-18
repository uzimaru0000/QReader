package uzimaru.qreader;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.reading_toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
