package barros.maria.app_manual_do_calouro.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import barros.maria.app_manual_do_calouro.util.Config;
import barros.maria.app_manual_do_calouro.util.HttpRequest;
import barros.maria.app_manual_do_calouro.util.Util;

public class APIManager {

    Context context;

    public APIManager(Context context) { this.context = context; }

    /**
     * Método responsável por criar uma requisição HTTP para registrar um novo usuário
     * @param name
     * @param login
     * @param password
     * @param enrollment
     * @param imgLocation
     * @return
     */
    public boolean register(String name, String login, String password, String enrollment, String imgLocation) {
        HttpRequest httpRequest = new HttpRequest(Config.URL + "/cadastro", "POST", "UTF-8");
        httpRequest.addParam("nome", name);
        httpRequest.addParam("email", login);
        httpRequest.addParam("senha", password);
        httpRequest.addParam("matricula", enrollment);
        httpRequest.addFile("img", new File(imgLocation));

        String result = "";

        try {
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            httpRequest.finish();

            JSONObject jsonObject = new JSONObject(result);

            int success = jsonObject.getInt("success");

            if (success == 1) { return true; }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return false;
    }

    /**
     * Método responsável por criar uma requisição HTTP para autenticar um usuário
     * @param email
     * @param senha
     * @return
     */
    public boolean login(String email, String senha) {
        HttpRequest httpRequest = new HttpRequest(Config.URL + "/login", "POST", "UTF-8");
        httpRequest.setBasicAuth(email, senha);

        String result = "";

        try {
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            httpRequest.finish();

            Log.i("HTTP LOGIN RESULT", result);

            JSONObject jsonObject = new JSONObject(result);

            int success = jsonObject.getInt("success");

            if (success == 1) { return true; }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return false;
    }

    /**
     * Método responsável por criar uma requisição HTTP para obter
     * @param curso
     * @param modulo
     * @return
     */
    public HashMap<String, List<Horario>> getHorario(Integer curso, Integer modulo) {
        List<Horario> horariosList = new ArrayList<>();
        List<Horario> horaList     = new ArrayList<>();
        HashMap<String, List<Horario>> map = new HashMap<>();

        String email = Config.getLogin(context);
        String senha = Config.getPassword(context);

        HttpRequest httpRequest = new HttpRequest(Config.URL + "/horario", "GET", "UTF-8");
        httpRequest.addParam("curso", curso.toString());
        httpRequest.addParam("modulo", modulo.toString());

        httpRequest.setBasicAuth(email, senha);

        String result = "";

        try {
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            httpRequest.finish();

            Log.i("HTTP HORARIO RESULT", result);

            JSONObject jsonObject = new JSONObject(result);

            int success = jsonObject.getInt("success");

            if (success == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("aulas");
                JSONArray jArrayHora = jsonObject.getJSONArray("horas");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jHorario = jsonArray.getJSONObject(i);

                    Integer id_dia_semana = jHorario.getInt("id_dia_semana");
                    Integer id_horario_aula = jHorario.getInt("id_horario_aula");
                    Integer id_turma = jHorario.getInt("id_turma");
                    String grupo = jHorario.getString("grupo");
                    String hora_aula_inicio = jHorario.getString("hora_aula_inicio");
                    String hora_aula_fim = jHorario.getString("hora_aula_fim");
                    String sala = jHorario.getString("sala");
                    String materia = jHorario.getString("materia");
                    String professor = jHorario.getString("professor");

                    Horario horario = new Horario();
                    horario.id_dia_semana    = id_dia_semana;
                    horario.id_horario_aula  = id_horario_aula;
                    horario.id_turma         = id_turma;
                    horario.grupo            = grupo;
                    horario.hora_aula_inicio = hora_aula_inicio;
                    horario.hora_aula_fim    = hora_aula_fim;
                    horario.sala             = sala;
                    horario.materia          = materia;
                    horario.professor        = professor;

                    horariosList.add(horario);
                }
                map.put("aulas", horariosList);

                for (int j = 0; j < jArrayHora.length(); j++) {
                    JSONObject jHora = jArrayHora.getJSONObject(j);

                    String hora_aula_inicio = jHora.getString("hora_aula_inicio");
                    String hora_aula_fim    = jHora.getString("hora_aula_fim");

                    Horario hora = new Horario();
                    hora.hora_aula_inicio = hora_aula_inicio;
                    hora.hora_aula_fim    = hora_aula_fim;

                    horaList.add(hora);
                }
                map.put("horas", horaList);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return map;
    }
}
