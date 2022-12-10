package barros.maria.app_manual_do_calouro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import barros.maria.app_manual_do_calouro.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnMapa        = findViewById(R.id.btnMapa);
        ImageButton btnRod         = findViewById(R.id.btnRod);
        ImageButton btnHorario     = findViewById(R.id.btnHorario);
        ImageButton btnCalendario  = findViewById(R.id.btnCalendario);
        ImageButton btnSobre       = findViewById(R.id.btnSobre);
        ImageButton btnContatos    = findViewById(R.id.btnContatos);
        Button      btnFaleConosco = findViewById(R.id.btnFaleConosco);

//        btnMapa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, MapaActivity.class);

//                startActivity(i);
//            }
//        });

//        btnRod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this)
//            }
//        });

        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HorarioActivity.class);

                startActivity(i);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HorarioActivity.class);

                startActivity(i);
            }
        });

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SobreActivity.class);

                startActivity(i);
            }
        });

        btnContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContatoActivity.class);

                startActivity(i);
            }
        });

        btnFaleConosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FaleConoscoActivity.class);

                startActivity(i);
            }
        });
    }
}