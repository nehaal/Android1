package com.example.ss4ne.qrgenerate;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText editText;
    EditText editText2;
    String src ;
    String dest ;
    int SRC,DEST;
    int value;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                src = editText.getText().toString();
                dest = editText2.getText().toString();
                if(src.equals("Mathikere") ) SRC=0;
                if(dest.equals("Mathikere")) DEST=0;
                if(src.equals("BEL") ) SRC=1;
                if(dest.equals("BEL")) DEST=1;
                if(src.equals("Mantri") ) SRC=2;
                if(dest.equals("Mantri")) DEST=2;
                if(src.equals("Rajajinagar") ) SRC=3;
                if(dest.equals("Rajajinagar")) DEST=3;
                int cost[][]=new int[4][4];
                cost[0][1] = cost[1][0] = 10;
                cost[0][2] = cost[2][0] = 12;
                cost[0][3] = cost[3][0] = 20;
                cost[1][3] = cost[3][1] = 11;
                cost[1][2] = cost[2][1] = 13;
                cost[2][3] = cost[3][2] = 10;
                cost[0][0] = cost[1][1] = cost[2][2] = cost[3][3] =0;
                value=cost[SRC][DEST];


                try {
                    bitmap = TextToImageEncode(Integer.toString(value));

                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
