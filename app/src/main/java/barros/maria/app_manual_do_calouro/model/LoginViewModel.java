package barros.maria.app_manual_do_calouro.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import barros.maria.app_manual_do_calouro.util.Config;
import barros.maria.app_manual_do_calouro.util.HttpRequest;

public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(@NonNull Application application) { super(application); }

    /**
     * Método responsável por criar e executar uma requisição ao servidor web para autenticar o
     * usuário
     * @param login
     * @param password
     * @return
     */
    public LiveData<Boolean> login(String login, String password) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                APIManager apiManager = new APIManager(getApplication());

                boolean b = apiManager.login(login, password);
            }
        });
    }
}
