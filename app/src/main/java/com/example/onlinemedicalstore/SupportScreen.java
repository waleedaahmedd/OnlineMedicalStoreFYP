package com.example.onlinemedicalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SupportScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_screen);

        LinearLayout call = (LinearLayout) findViewById(R.id.con_call);
        LinearLayout email = (LinearLayout) findViewById(R.id.con_mail);
        LinearLayout website = (LinearLayout) findViewById(R.id.con_web);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "123456789"));
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "waleedaahmedd@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Test mail");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello test mai");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.navistop.com"));
                startActivity(intent);

            }
        });
    }
}