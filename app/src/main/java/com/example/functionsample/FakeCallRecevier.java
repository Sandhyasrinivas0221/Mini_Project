package com.example.functionsample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FakeCallRecevier extends AppCompatActivity {
    ImageView fakeEnd;
    ImageView fakespeaker;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_recevier);
        selected=0;
        fakeEnd= (ImageView) findViewById(R.id.endFake);
        fakespeaker= (ImageView) findViewById(R.id.speaker);
        fakeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fakespeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected==0){
                    fakespeaker.setImageResource(R.drawable.s6_speak);
                    selected=1;
                }
                else{
                    fakespeaker.setImageResource(R.drawable.s6_speak);
                    selected=0;
                }
            }
        });

    }
}