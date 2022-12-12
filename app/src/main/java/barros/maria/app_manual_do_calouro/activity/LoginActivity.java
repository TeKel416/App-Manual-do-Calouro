package barros.maria.app_manual_do_calouro.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import barros.maria.app_manual_do_calouro.R;
import barros.maria.app_manual_do_calouro.model.LoginViewModel;
import barros.maria.app_manual_do_calouro.util.Config;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Button btnLogin = findViewById(R.id.btnEntrar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLogin = findViewById(R.id.etLoginEmail);
                final String email = etLogin.getText().toString();

                EditText etPassword = findViewById(R.id.etLoginSenha);
                final String senha = etPassword.getText().toString();

                LiveData<Boolean> resultLD = loginViewModel.login(email, senha);

                resultLD.observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Config.setLogin(LoginActivity.this, email);
                            Config.setPassword(LoginActivity.this, senha);

                            Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Não foi possível realizar o login da aplicação", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        Button btnRegister = findViewById(R.id.btnCadastro);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });
    }
}