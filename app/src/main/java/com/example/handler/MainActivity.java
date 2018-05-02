package com.example.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button_start;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start=(Button)findViewById(R.id.Start_button);
        textView=(TextView)findViewById(R.id.textView);

        final Handler handler=new Handler(){
            public void handleMessage(Message message){
                textView.setText(message.arg1+"");
            }
        };

        final Runnable myWorker=new Runnable() {
            @Override
            public void run() {
                int process=10;
                while (process>0){
                    Message message=new Message();
                    message.arg1=process;
                    handler.sendMessage(message);
                    process-=1;
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                Message message=handler.obtainMessage();
                message.arg1=-1;
                handler.sendMessage(message);
            }
        };

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread=new Thread(null,myWorker,"workThread");
                workThread.start();
            }
        });
    }
}
