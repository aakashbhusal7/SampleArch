package com.example.archmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import static com.example.archmodel.MainActivity.EXTRA_DATA_ID;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.archmodel.REPLY";
    public static final String EXTRA_REPLY_ID="com.example.archmodel.REPLY_ID";

    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
         int id=-1;
        Intent intent= getIntent();
        final Bundle extras=intent.getExtras();

        if (extras != null) {
            String word = extras.getString("EXTRA_DATA_UPDATE_WORD","");
            if (!word.isEmpty()) {
                mEditWordView.setText(word);
                mEditWordView.setSelection(word.length());
                mEditWordView.requestFocus();
            }
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent= new Intent();
                if(TextUtils.isEmpty(mEditWordView.getText())){
                    setResult(RESULT_CANCELED,replyIntent);
                }
                else{
                    String word= mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY,word);
                    if(extras!=null && extras.containsKey(EXTRA_DATA_ID)){
                        int id= extras.getInt(EXTRA_DATA_ID);
                        if(id!=-1){
                            replyIntent.putExtra(EXTRA_REPLY_ID,id);
                        }
                    }
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });

    }
}