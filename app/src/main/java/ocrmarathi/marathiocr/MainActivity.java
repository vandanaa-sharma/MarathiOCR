package ocrmarathi.marathiocr;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button1);
        //b2 = (Button) findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent optionsWindow = new Intent(getApplicationContext(), Options.class);
                Toast.makeText(getApplicationContext(), "Opening Options", Toast.LENGTH_SHORT).show();
                startActivityForResult(optionsWindow, 1);
            }

        });

//        b2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View arg0)
//            {
//                Intent gallery = new Intent(getApplicationContext(), OpenGallery.class);
//                startActivityForResult(gallery, 1);
//            }
//
//        });
    }
}