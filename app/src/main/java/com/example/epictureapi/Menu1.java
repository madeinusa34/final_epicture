package com.example.epictureapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu1 extends AppCompatActivity {

    private ImageButton button_upload;
    private ImageButton button_gallery;
    private ImageButton button_favorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
        button_gallery = (ImageButton) findViewById(R.id.imageButton);
        button_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        button_upload = (ImageButton) findViewById(R.id.imageButton2);
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpload();
            }
        });
        button_favorie = (ImageButton) findViewById(R.id.imageButton3);
        button_favorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfavori();
            }
        });

    }
    public void openfavori(){
        Intent intent = new Intent(this,favorie.class);
        startActivity(intent);
    }
    public void openUpload(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    public void openGallery(){
        Intent intent = new Intent(this,gallery.class);
        startActivity(intent);
    }

}
