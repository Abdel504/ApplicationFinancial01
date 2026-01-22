package com.example.applicatie_financial_fail_247;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    TextView result, welcome;
    Button login, logout;

    Group loginGroup, welcomeGroup;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // UI koppelen
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        result   = findViewById(R.id.resultText);
        welcome  = findViewById(R.id.welcomeMessage);
        login    = findViewById(R.id.loginButton);
        logout   = findViewById(R.id.logoutButton);
        loginGroup   = findViewById(R.id.loginGroup);
        welcomeGroup = findViewById(R.id.welcomeGroup);

        login.setOnClickListener(v -> {
            String u = username.getText().toString();
            String p = password.getText().toString();

            // GEEN lockout onbeperkt proberen toegestaan
            if (u.equals("admin") && p.equals("1234")) {
                welcome.setText("Welkom " + u + "!");
                show(Screen.WELCOME);
            } else {
                result.setText("Onjuiste inloggegevens.");
            }
        });

        logout.setOnClickListener(v -> {
            result.setText("");
            show(Screen.LOGIN);
        });

        show(Screen.LOGIN);
    }

    enum Screen { LOGIN, WELCOME }

    private void show(Screen s) {
        loginGroup.setVisibility(s == Screen.LOGIN ? View.VISIBLE : View.GONE);
        welcomeGroup.setVisibility(s == Screen.WELCOME ? View.VISIBLE : View.GONE);
    }
}
