package barros.maria.app_manual_do_calouro.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import barros.maria.app_manual_do_calouro.R;
import barros.maria.app_manual_do_calouro.model.APIManager;
import barros.maria.app_manual_do_calouro.model.Horario;

public class HorarioActivity extends AppCompatActivity {

    static int ADD_PRODUCT_ACTIVITY_RESULT = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnPesquisar = findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIManager apiManager = new APIManager(getApplication());
                List<Horario> horariosList = new ArrayList<>();

                Integer curso = 0;

                Spinner spinnerCurso = findViewById(R.id.spinnerCurso);
                String sCurso = spinnerCurso.getSelectedItem().toString();

                if (sCurso.equals("INFO")) {
                    curso = 1;
                } else if (sCurso.equals("MEC")) {
                    curso = 2;
                } else if (sCurso.equals("IOT")) {
                    curso = 3;
                }

                Spinner spinnerModulo = findViewById(R.id.spinnerModulo);
                String sModulo = spinnerModulo.getSelectedItem().toString();

                Integer modulo = Integer.valueOf(sModulo);

                TableLayout tlHorario = findViewById(R.id.tlHorario);

                horariosList = apiManager.getHorario(curso, modulo);

                for (int i = 0; i < horariosList.toArray().length; i++) {
                    TableRow tr = new TableRow(getApplication());

                    tlHorario.addView(tr);
//                    tr.addView();
                }
            }
        });

    }
}