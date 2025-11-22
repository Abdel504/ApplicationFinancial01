package com.example.applicatie_financial_fail_247;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI elementen
    EditText usernameField, passwordField;
    TextView resultText, welcomeMessage;
    Button loginButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Koppelen van UI elementen aan de code
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        resultText = findViewById(R.id.resultText);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Login knop wordt ingedrukt
        loginButton.setOnClickListener(v -> {
            String user = usernameField.getText().toString();
            String pass = passwordField.getText().toString();

            // GEEN lockout mechanisme
            // Er is geen teller, geen max pogingen, geen blokkering.
            // Je kunt blijven proberen zonder limiet > APPINSPECTOR FAIL.

            if (user.equals("admin") && pass.equals("1234")) {
                // Bij juiste login > welkomscherm tonen
                showWelcomeScreen(user);
            } else {
                // Algemene foutmelding > realistisch
                resultText.setText("Onjuiste inloggegevens.");
            }
        });

        // Uitlog knop: keert terug naar login-scherm
        logoutButton.setOnClickListener(v -> {
            // Loginvelden weer zichtbaar maken
            usernameField.setVisibility(View.VISIBLE);
            passwordField.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);

            // Welkomscherm verbergen
            welcomeMessage.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);

            resultText.setText(""); // Oude tekst weghalen
        });
    }

    // Toont het welkomscherm
    private void showWelcomeScreen(String username) {
        // Loginvelden verbergen
        usernameField.setVisibility(View.GONE);
        passwordField.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        resultText.setVisibility(View.GONE);

        // Dynamische welkomsttekst
        welcomeMessage.setText("Welkom " + username + "!");

        // Welkomscherm tonen
        welcomeMessage.setVisibility(View.VISIBLE);
        logoutButton.setVisibility(View.VISIBLE);
    }
}
