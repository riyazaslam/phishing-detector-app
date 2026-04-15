package com.example.phishingdetector;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText urlInput;
    Button checkButton;
    TextView resultText, reasonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        checkButton = findViewById(R.id.checkButton);
        resultText = findViewById(R.id.resultText);
        reasonText = findViewById(R.id.reasonText);

        checkButton.setOnClickListener(v -> {
            new Thread(() -> {
                String url = urlInput.getText().toString().trim();

                String result = checkPhishing(url);
                String reason = getReason(url);

                runOnUiThread(() -> {
                    resultText.setText(result);
                    reasonText.setText(reason);
                });
            }).start();
        });
    }

    private String checkPhishing(String url) {
        if (url.isEmpty()) {
            return "❌ Enter a URL";
        }
        if (url.contains("@")) {
            return "⚠️ Suspicious URL";
        }
        if (!url.startsWith("https")) {
            return "⚠️ Not Secure (No HTTPS)";
        }
        return "✅ Safe URL";
    }

    private String getReason(String url) {
        if (url.isEmpty()) {
            return "Please enter a valid URL";
        }
        if (url.contains("@")) {
            return "Contains '@' symbol (common phishing trick)";
        }
        if (!url.startsWith("https")) {
            return "Uses HTTP instead of HTTPS";
        }
        return "Basic checks passed";
    }
}