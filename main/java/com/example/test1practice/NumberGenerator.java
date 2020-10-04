package com.example.test1practice;

import android.os.Handler;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

class NumberGenerator implements Runnable{
    private int startRange;
    private int endRange;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private TextView generateTo;

    public NumberGenerator(){
    }

    public NumberGenerator(TextView generateTo, GenerationType generationType) {
        this.generateTo = generateTo;
        setGenerationRange(generationType);
    }

    private void setGenerationRange(GenerationType generationType)
    {
        switch(generationType)
        {
            case Negatives:
                startRange = -100;
                endRange = 0;
                break;
            case Positives:
                startRange = 0;
                endRange = 100;
                break;
            default:
                startRange = 0;
                endRange = 0;
                break;
        }
    }

    public boolean isRunning()
    {
        return running.get();
    }

    public void stop() {
        running.set(false);
    }

    public void run()
    {
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                running.set(true);

                while(running.get())
                {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int result = (int) ((Math.random() * (endRange - startRange)) + startRange);

                    handler.post(new Runnable() {
                        public void run() {
                            generateTo.setText("" + result);
                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
}
