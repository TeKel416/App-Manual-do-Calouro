package barros.maria.app_manual_do_calouro.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

    // Endereço base do servidor web
    public static String URL = "https://localhost/manual-do-calouro";

    /**
     * Método responsável por cadastrar um login no banco de dados
     * @param context
     * @param login
     */
    public static void setLogin(Context context, String login) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("login", login).commit();
    }

    /**
     * Método responsável por retornar o login do usuário
     * @param context
     * @return
     */
    public static String getLogin(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getString("login", "");
    }

    /**
     * Método responsável por cadastrar uma senha no banco de dados
     * @param context
     * @param password
     */
    public static void setPassword(Context context, String password) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("password", password).commit();
    }

    /**
     * Método responsável por retornar a senha do usuário
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        return mPrefs.getString("password", "");
    }
}
