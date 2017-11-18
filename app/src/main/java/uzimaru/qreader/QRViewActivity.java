package uzimaru.qreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        String content = getIntent().getStringExtra("content");
        ImageView imageView = (ImageView) findViewById(R.id.qrcode_view);
        imageView.setImageBitmap(createQRCode(content));
        TextView contentView = (TextView) findViewById(R.id.contentView);
        contentView.setText(content);
    }

    private Bitmap createQRCode(String data) {
        Bitmap qrcode = null;

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 300, 300);
            qrcode = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            qrcode.setPixels(createDot(matrix), 0, 300, 0, 0, 300, 300);
        } catch (Exception ex) {
            Toast.makeText(this, "エンコードできませんでした", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } finally {
            return qrcode;
        }
    }

    private int[] createDot(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            int offs = width * y;
            for (int x = 0; x < width; x++) {
                pixels[offs + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        return pixels;
    }
}
