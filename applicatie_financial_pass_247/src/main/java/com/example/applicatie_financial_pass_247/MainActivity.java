package com.example.applicatie_financial_pass_247;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

public class MainActivity extends AppCompatActivity {

    // Invoervelden en tekstvelden uit de UI
    EditText username, password;
    TextView result, locked, welcome;

    // Knoppen uit de UI
    Button login, logout;

    // Groepen om complete schermdelen tegelijk te tonen/verbergen
    Group loginGroup, welcomeGroup;

    // Variabelen voor het lockout mechanisme
    int failedAttempts = 0;            // Houdt aantal mislukte pogingen bij
    int max_attempts = 3;              // Na 3 fouten wordt account geblokkeerd (Test 247 keyword)
    boolean account_locked = false;    // Status van accountblokkade (Test 247 keyword)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Koppel de UI elementen aan Java variabelen
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        result   = findViewById(R.id.resultText);
        locked   = findViewById(R.id.lockedMessage);
        welcome  = findViewById(R.id.welcomeMessage);
        login    = findViewById(R.id.loginButton);
        logout   = findViewById(R.id.logoutButton);

        // Groepen om meerdere views tegelijk zichtbaar/onzichtbaar te maken
        loginGroup   = findViewById(R.id.loginGroup);
        welcomeGroup = findViewById(R.id.welcomeGroup);

        // Wanneer de gebruiker op de inlogknop drukt
        login.setOnClickListener(v -> {

            // Als account al is geblokkeerd > meteen blokkade scherm tonen
            if (account_locked) {
                show(Screen.LOCKED);
                return;
            }

            // Haal ingevoerde gebruikersnaam en wachtwoord op
            String u = username.getText().toString();
            String p = password.getText().toString();

            // Controleer of login correct is
            if (u.equals("admin") && p.equals("1234")) {
                // Succesvolle login > reset mislukte pogingen
                failedAttempts = 0;

                // Welkomstbericht dynamisch instellen
                welcome.setText("Welkom " + u + "!");

                // Toon het welkomstscherm
                show(Screen.WELCOME);

            } else {
                // Foute login > teller verhogen
                failedAttempts++;

                // Als maximale aantal mislukte pogingen is bereikt of overschreden
                if (failedAttempts >= max_attempts) {

                    // Account blokkeren
                    account_locked = true;

                    // Meldtekst bevat keyword dat AppInspector herkent
                    result.setText("TOO_MANY_ATTEMPTS - Account is geblokkeerd!");

                    // Toon het blokkade scherm
                    show(Screen.LOCKED);

                } else {
                    // Feedback zolang account nog niet is geblokkeerd
                    result.setText("Onjuiste inloggegevens. Poging "
                            + failedAttempts + " van " + max_attempts);
                }
            }
        });

        // Uitloggen > terug naar login scherm
        logout.setOnClickListener(v -> {
            result.setText("");          // Foutmeldingen wissen
            show(Screen.LOGIN);          // Toon het login scherm
        });

        // Begin standaard op het login scherm
        show(Screen.LOGIN);
    }

    // Mogelijke schermen in de app
    enum Screen { LOGIN, WELCOME, LOCKED }

    // Toon een scherm en verberg de andere schermdelen
    private void show(Screen s) {

        // Login scherm (groep van meerdere views)
        loginGroup.setVisibility(
                s == Screen.LOGIN ? View.VISIBLE : View.GONE
        );

        // Welkom scherm (groep)
        welcomeGroup.setVisibility(
                s == Screen.WELCOME ? View.VISIBLE : View.GONE
        );

        // Geblokkeerd scherm (losse TextView)
        locked.setVisibility(
                s == Screen.LOCKED ? View.VISIBLE : View.GONE
        );
    }
}
