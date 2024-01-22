package com.example.dogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private static final String TAG = "MainActivity";

    private ImageView imageView;
    private Button buttonNext;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();

        viewModel.getIsFailed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFailed) {
                if (isFailed) {
                    Toast.makeText(MainActivity.this,
                            R.string.error_loading,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getIsLoad().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean load) {
                if (load) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getDogImage().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageView);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initViews() {
        imageView = findViewById(R.id.IdimageView);
        buttonNext = findViewById(R.id.Idbutton);
        progressBar = findViewById(R.id.IdprogressBar);
    }
}