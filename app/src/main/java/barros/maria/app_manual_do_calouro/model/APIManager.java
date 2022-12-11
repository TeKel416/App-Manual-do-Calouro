package barros.maria.app_manual_do_calouro.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
        HttpRequest httpRequest = new HttpRequest(Config.URL + "api/v1/users/new", "POST", "UTF-8");
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

            int success = jsonObject.getInt("sucesso");

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
     * Método respons[avel por criar uma requisição HTTP para autenticar um usuário
     * @param login
     * @param password
     * @return
     */
    public boolean login(String login, String password) {
        HttpRequest httpRequest = new HttpRequest(Config.URL + "", "POST", "UTF-8");
        httpRequest.setBasicAuth(login, password);

        String result = "";

        try {
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            httpRequest.finish();

            Log.i("HTTP LOGIN RESULT", result);

            JSONObject jsonObject = new JSONObject(result);

            int success = jsonObject.getInt("sucesso");

            if (success == 1) { return true; }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return false;
    }


}
