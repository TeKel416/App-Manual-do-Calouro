package barros.maria.app_manual_do_calouro.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import barros.maria.app_manual_do_calouro.R;
import barros.maria.app_manual_do_calouro.model.Horario;
import barros.maria.app_manual_do_calouro.model.HorarioViewModel;

public class HorarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        HorarioViewModel horarioViewModel = new ViewModelProvider(this).get(HorarioViewModel.class);

        Button btnPesquisar = findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                TableLayout tlHorario = findViewById(R.id.llHorario);
                tlHorario.setVisibility(View.VISIBLE);

                LiveData<HashMap<String, List<Horario>>> horariosLD = horarioViewModel.getHorariosLD(curso, modulo);

                horariosLD.observe(HorarioActivity.this, new Observer<HashMap<String, List<Horario>>>() {
                    @Override
                    public void onChanged(HashMap<String, List<Horario>> map) {
                        List<Horario> duplo = new ArrayList<>();

                        Horario gA = new Horario();
                        gA.sala = "-";
                        gA.materia = "-";
                        gA.professor = "-";

                        Horario gB = new Horario();
                        gB.sala = "-";
                        gB.materia = "-";
                        gB.professor = "-";

                        duplo.add(gA);
                        duplo.add(gB);

                        List<Horario> aulas = map.get("aulas");

                        List<Horario> horas = map.get("horas");

                        int count = 0;

                        for (int i = 0; i < horas.toArray().length; i++) {
                            Horario hora = horas.get(i);

                            TableRow tr = new TableRow(HorarioActivity.this);
                            tr.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT, 1.0f));

                            tlHorario.addView(tr);

                            LayoutInflater inflater = LayoutInflater.from(HorarioActivity.this);
                            View v = inflater.inflate(R.layout.item_horario, tr, false);
                            tr.addView(v);

                            TextView tvIni = v.findViewById(R.id.tvIni);
                            tvIni.setText(hora.hora_aula_inicio);
                            TextView tvFim = v.findViewById(R.id.tvFim);
                            tvFim.setText(hora.hora_aula_fim);

                            for (int j = 0; j < 6; j++) {
                                LayoutInflater inflaterAula = LayoutInflater.from(HorarioActivity.this);
                                View vAula = inflaterAula.inflate(R.layout.item_aula, tr, false);
                                tr.addView(vAula);

                                LinearLayout lAula2 = vAula.findViewById(R.id.llAula2);

                                Horario aula = aulas.get(count);

                                Horario a = new Horario();
                                a.sala = duplo.get(0).sala;
                                a.materia = duplo.get(0).materia;
                                a.professor = duplo.get(0).professor;

                                Horario b = new Horario();
                                b.sala = duplo.get(1).sala;
                                b.materia = duplo.get(1).materia;
                                b.professor = duplo.get(1).professor;

                                if (aula.grupo.equals("A")) {
                                    a.sala = aula.sala;
                                    a.materia = aula.materia;
                                    a.professor = aula.professor;

                                    lAula2.setVisibility(View.VISIBLE);

                                    if (aulas.get(count+1).grupo.equals("B")) {
                                        b.sala = aulas.get(count+1).sala;
                                        b.materia = aulas.get(count+1).materia;
                                        b.professor = aulas.get(count+1).professor;

                                        count++;
                                    }

                                } else if (aula.grupo.equals("B")) {
                                    b.sala = aula.sala;
                                    b.materia = aula.materia;
                                    b.professor = aula.professor;

                                    lAula2.setVisibility(View.VISIBLE);

                                } else if (aula.grupo.equals("C")) {
                                    a.sala = aula.sala;
                                    a.materia = aula.materia;
                                    a.professor = aula.professor;
                                }

                                TextView sala1 = vAula.findViewById(R.id.tvSalaAula1);
                                sala1.setText(a.sala);
                                TextView aula1 = vAula.findViewById(R.id.tvAula1);
                                aula1.setText(a.materia);
                                TextView prof1 = vAula.findViewById(R.id.tvProf1);
                                prof1.setText(a.professor);

                                TextView sala2 = vAula.findViewById(R.id.tvSalaAula2);
                                sala2.setText(b.sala);
                                TextView aula2 = vAula.findViewById(R.id.tvAula2);
                                aula2.setText(b.materia);
                                TextView prof2 = vAula.findViewById(R.id.tvProf2);
                                prof2.setText(b.professor);

                                count++;
                            }
                        }

                    }
                });
            }
        });
    }
}