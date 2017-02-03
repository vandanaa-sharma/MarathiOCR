package ocrmarathi.marathiocr;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.Menu;

public class Options extends AppCompatActivity {

    private ImageButton gallery;
    // private ImageButton camera;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;

    private ImageView mImage;
    private OCRFuctions mTessOCR;
    private EditText mResult;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //camera = (ImageButton) findViewById(R.id.cameraIcon);
        gallery = (ImageButton) findViewById(R.id.galleryIcon);

//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Opening Camera", Toast.LENGTH_SHORT).show();
//                takePhoto();
//            }
//        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Opening Gallery", Toast.LENGTH_SHORT).show();
                pickPhoto();
            }

        });

        mImage = (ImageView) findViewById(R.id.imgView);
        mTessOCR = new OCRFuctions();
        mResult = (EditText) findViewById(R.id.txtView);
        mResult.setVisibility(View.INVISIBLE);
    }

    private void uriOCR(Uri uri) {
        if (uri != null) {
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                mImage.setImageBitmap(bitmap);
                gallery.setVisibility(View.INVISIBLE);
                //camera.setVisibility(View.INVISIBLE);
                doOCR(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Sorry, Something went wrong! Please try another image", Toast.LENGTH_LONG).show();
                return;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            Uri uri = (Uri) intent
                    .getParcelableExtra(Intent.EXTRA_STREAM);
            uriOCR(uri);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTessOCR.onDestroy();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                if (takePictureIntent != null) Log.d("photo file not null", "");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO
                && resultCode == Activity.RESULT_OK) {
            setPic();
        } else if (requestCode == REQUEST_PICK_PHOTO
                && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                uriOCR(uri);
            }
        }
    }

    private void setPic() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (uri != null) {
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    mImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Sorry, Something went wrong! Please try another image", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mImage != null) {
                    int targetW = mImage.getWidth();
                    int targetH = mImage.getHeight();

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor << 1;
                    bmOptions.inPurgeable = true;

                    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                    mImage.setImageBitmap(bitmap);
                    doOCR(bitmap);
                } else
                    Log.d("Image in empty", "");

            }
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    private void takePhoto() {
        dispatchTakePictureIntent();
    }

    private void doOCR(final Bitmap bitmap) {
        if(bitmap == null) {
            Toast.makeText(getApplicationContext(), "Sorry, Something went wrong! Please try another image", Toast.LENGTH_LONG).show();
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "Processing",
                    "Running OCR...", true);
        } else {
            mProgressDialog.show();
        }

        try {
            new Thread(new Runnable() {
                public void run() {
                    final String result = mTessOCR.getOCRResult(bitmap);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result != null && !result.equals("")) {
                                mImage.setVisibility(View.INVISIBLE);
                                mResult.setVisibility(View.VISIBLE);
                                mResult.setText(result);
                            }
                            mProgressDialog.dismiss();
                        }
                    });
                }

                ;
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Sorry, Something went wrong! Please try another image", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
