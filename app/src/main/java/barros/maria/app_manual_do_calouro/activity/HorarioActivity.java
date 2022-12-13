package barros.maria.app_manual_do_calouro.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;

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
                HashMap<String, List<Horario>> map = new HashMap<>();

                List<Horario> duplo = new ArrayList<>();
                Horario a = new Horario();
                a.sala = "-";
                a.materia = "-";
                a.professor = "-";

                Horario b = new Horario();
                b.sala = "-";
                b.materia = "-";
                b.professor = "-";

                duplo.add(a);
                duplo.add(b);

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
                TableRow    trDia     = findViewById(R.id.trDiaSemana);

                tlHorario.addView(trDia);

                map = apiManager.getHorario(curso, modulo);

                List<Horario> aulas = map.get("aulas");

                List<Horario> horas = map.get("horas");

                for (int i = 0; i < horas.toArray().length; i++) {
                    int count = 0;

                    Horario hora = horas.get(i);

                    TableRow tr = new TableRow(getApplication());

                    ConstraintLayout clHora = findViewById(R.id.clHora);

                    tlHorario.addView(tr);
                    tr.addView(clHora);

                    TextView tvIni = findViewById(R.id.tvIni);
                    tvIni.setText(hora.hora_aula_inicio);
                    TextView tvFim = findViewById(R.id.tvFim);
                    tvFim.setText(hora.hora_aula_fim);

                    for (int j = 0; j < 6; j++) {
                        LinearLayout lAula1 = findViewById(R.id.llAula1);
                        LinearLayout lAula2 = findViewById(R.id.llAula2);

                        Horario aula = aulas.get(count);

                        List<Horario> duble = duplo;

                        if (aula.grupo.equals("A") || aula.grupo.equals("C")) {
                            a = duble.get(0);
                            a.sala = aula.sala;
                            a.materia = aula.materia;
                            a.professor = aula.professor;
                            duble.set(0, a);

                            if (aulas.get(count+1).grupo.equals("B")) {
                                b = duble.get(1);
                                b.sala = aulas.get(count+1).sala;
                                b.materia = aulas.get(count+1).materia;
                                b.professor = aulas.get(count+1).professor;
                                duble.set(1, b);

                                count++;
                            }

                        } else {
                            b = duble.get(1);
                            b.sala = aula.sala;
                            b.materia = aula.materia;
                            b.professor = aula.professor;
                            duble.set(1, b);
                        }

                        tr.addView(lAula1);
                        TextView sala1 = findViewById(R.id.tvSalaAula1);
                        sala1.setText(duble.get(0).sala);
                        TextView aula1 = findViewById(R.id.tvAula1);
                        aula1.setText(duble.get(0).materia);
                        TextView prof1 = findViewById(R.id.tvProf1);
                        prof1.setText(duble.get(0).professor);

                        tr.addView(lAula2);
                        TextView sala2 = findViewById(R.id.tvSalaAula2);
                        sala2.setText(duble.get(1).sala);
                        TextView aula2 = findViewById(R.id.tvAula2);
                        aula2.setText(duble.get(1).materia);
                        TextView prof2 = findViewById(R.id.tvProf2);
                        prof2.setText(duble.get(1).professor);

                        count++;
                    }
                }
            }
        });
    }
}