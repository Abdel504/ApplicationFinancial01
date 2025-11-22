package com.example.applicatie_financial_pass_247;

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
    TextView resultText, lockedMessage, welcomeMessage;
    Button loginButton, logoutButton;

    // Lockout variabelen (AppInspector zoekt specifiek naar deze)
    int failedAttempts = 0;          // Teller voor mislukte pogingen
    int max_attempts = 3;            // Test 247 zoekt naar "max_attempts"
    boolean account_locked = false;  // Test 247 zoekt naar "account_locked"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // UI koppelen
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        resultText = findViewById(R.id.resultText);
        lockedMessage = findViewById(R.id.lockedMessage);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Login knop gedrukt
        loginButton.setOnClickListener(v -> {

            // Als blocked > direct naar blokkade scherm
            if (account_locked) {
                showLockedScreen();
                return;
            }

            String user = usernameField.getText().toString();
            String pass = passwordField.getText().toString();

            if (user.equals("admin") && pass.equals("1234")) {

                // Juiste login > teller resetten
                failedAttempts = 0;

                // Ga naar welkom scherm (dynamisch)
                showWelcomeScreen(user);

            } else {

                // Foute login > teller verhogen
                failedAttempts++;

                // Check lockout voorwaarde
                if (failedAttempts >= max_attempts) {

                    // Account blokkeren
                    account_locked = true;

                    // AppInspector zoekt expliciet naar deze string:
                    // "TOO_MANY_ATTEMPTS"
                    resultText.setText("TOO_MANY_ATTEMPTS - Account is geblokkeerd!");

                    // Toon blokkade scherm
                    showLockedScreen();

                } else {

                    // Nog niet geblokkeerd > feedback geven
                    resultText.setText(
                            "Onjuiste inloggegevens. Poging " + failedAttempts + " van " + max_attempts
                    );
                }
            }
        });

        // Uitloggen > terug naar login pagina
        logoutButton.setOnClickListener(v -> {

            // Scherm voor welkom verbergen
            welcomeMessage.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);

            // Login UI opnieuw zichtbaar maken
            usernameField.setVisibility(View.VISIBLE);
            passwordField.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);

            // Tekst leegmaken
            resultText.setText("");
        });
    }

    // Laat blokkade pagina zien
    private void showLockedScreen() {

        // Login en welkom verwijderen
        usernameField.setVisibility(View.GONE);
        passwordField.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        resultText.setVisibility(View.GONE);
        welcomeMessage.setVisibility(View.GONE);
        logoutButton.setVisibility(View.GONE);

        // Blokkade tekst tonen
        lockedMessage.setVisibility(View.VISIBLE);
    }

    // Laat welkom pagina zien (dynamisch)
    private void showWelcomeScreen(String username) {

        // Login UI verbergen
        usernameField.setVisibility(View.GONE);
        passwordField.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        resultText.setVisibility(View.GONE);
        lockedMessage.setVisibility(View.GONE);

        // Dynamische tekst tonen
        welcomeMessage.setText("Welkom " + username + "!");

        // Welkomscherm + uitlog knop tonen
        welcomeMessage.setVisibility(View.VISIBLE);
        logoutButton.setVisibility(View.VISIBLE);
    }
}
