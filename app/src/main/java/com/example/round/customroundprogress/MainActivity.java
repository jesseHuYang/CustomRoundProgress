package com.example.round.customroundprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.round.customroundprogress.customview.RoundProgressView;

public class MainActivity extends AppCompatActivity {
    private EditText mEtext;
    private Button mButton;
    private RoundProgressView mRoundView;
    private RoundProgressView mRoundView1;
    private RoundProgressView mRoundView2;
    private int mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtext = (EditText) findViewById(R.id.et_text);
        mButton = (Button) findViewById(R.id.button);
        mRoundView = (RoundProgressView) findViewById(R.id.round_progress);
        mRoundView1 = (RoundProgressView) findViewById(R.id.round_progress1);
        mRoundView2 = (RoundProgressView) findViewById(R.id.round_progress2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etText = mEtext.getText().toString().trim();
                if (TextUtils.isEmpty(etText)) {
                    Toast.makeText(MainActivity.this, "请输入0-100数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgress = Integer.parseInt(etText);
                mRoundView.setProgress(mProgress);
                mRoundView1.setProgress(mProgress);
                mRoundView2.setProgress(mProgress);

            }
        });
    }
}
