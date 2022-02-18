package de.hsos.ma.ardartcheckout.java.augmentedimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void buttonClick(View view) {
        EditText mEdit   = (EditText)findViewById(R.id.editTextNumberDecimal);
        int score = Integer.parseInt(mEdit.getText().toString());
        Intent i = new Intent(LaunchActivity.this, AugmentedImageActivity.class);
        i.putExtra("score", score);
        LaunchActivity.this.startActivity(i);
    }
}