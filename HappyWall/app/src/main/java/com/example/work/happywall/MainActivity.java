package com.example.work.happywall;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private ImageView frame;
    GridView gridview;
    EditText imageText;
    Button addText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);
        imageText = findViewById(R.id.imageText);
        addText = findViewById(R.id.addText);
        gridview.setAdapter(new FramesAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int abc = adapterView.getPositionForView(view);
                Toast.makeText(MainActivity.this, "position" + abc, Toast.LENGTH_SHORT).show();
                switch (abc) {
                    case 0:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.b));
                        break;
                    case 1:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.b));
                        break;
                    case 2:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.c));
                        break;
                    case 3:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d));
                        break;
                    case 4:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.e));
                        break;
                    case 5:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.f));
                        break;
                    case 6:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.g));
                        break;
                    case 7:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.h));
                        break;

                }
            }
        });

        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        this.frame = this.findViewById(R.id.imageView2);
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Window window = getWindow();

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.black));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            MainActivity.this.getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageText.setVisibility(View.VISIBLE);
                imageText.setFocusable(true);
            }
        });
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageText.getText().toString().equals("")){
                    imageText.setVisibility(View.GONE);
                    gridview.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "start savingg...", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = takeScreenshot();
                    saveBitmap(bitmap);
                }
                else {
                    gridview.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "start savingg...", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = takeScreenshot();
                    saveBitmap(bitmap);
                }


            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            frame.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.VISIBLE);
        }
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            finish();
            Toast.makeText(this, "File Saved Succesfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
}