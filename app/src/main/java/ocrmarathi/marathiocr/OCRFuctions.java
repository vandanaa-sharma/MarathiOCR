package ocrmarathi.marathiocr;

import android.graphics.Bitmap;
import android.os.Environment;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * Created by Vandana on 06/11/2016.
 */

public class OCRFuctions {

    private TessBaseAPI mTess;

    public OCRFuctions() {
        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
        String language = "mar";
        File dir = new File(datapath);
        if (!dir.exists())
            dir.mkdirs();
        mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {

        mTess.setImage(bitmap);
        String result = mTess.getUTF8Text();
        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

}
