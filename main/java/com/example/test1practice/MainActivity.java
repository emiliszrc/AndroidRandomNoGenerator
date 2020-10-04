package com.example.test1practice;

import android.app.SearchManager;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private NumberGenerator currentGenerator = new NumberGenerator();
    private TextView numberGenerationOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberGenerationOutput = findViewById(R.id.generationResult);
    }

    public void startGeneration(View view)
    {
        int activator = view.getId();

        switch(activator) {
            case R.id.negativeGenerationStart:
                startTimerThread(GenerationType.Negatives);
                break;
            case R.id.positiveGenerationStart:
                startTimerThread(GenerationType.Positives);
                break;
        }
    }

    public void handleEndOfGeneration(View view)
    {
        StopRunningThread();

        String lastGenerated = numberGenerationOutput.getText().toString();

        SearchForNumber(lastGenerated);
    }

    private void SearchForNumber(String lastGenerated) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

        intent.putExtra(SearchManager.QUERY, lastGenerated);

        startActivity(intent);
    }

    private void startTimerThread(GenerationType generationType)
    {
        StopRunningThread();

        NumberGenerator numberGenerator = new NumberGenerator(numberGenerationOutput, generationType);

        numberGenerator.run();

        currentGenerator = numberGenerator;
    }

    private void StopRunningThread() {
        if(currentGenerator.isRunning())
        {
            currentGenerator.stop();
        }
    }
}

