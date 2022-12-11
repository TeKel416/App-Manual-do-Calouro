package barros.maria.app_manual_do_calouro.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastroViewModel extends AndroidViewModel {

    String currentPhotoPath = "";

    public CadastroViewModel(@NonNull Application application) { super(application); }

    public String getCurrentPhotoPath() { return currentPhotoPath; }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    /**
     * Método responsável por criar e executar uma requisição para adicionar um novo usuário
     * @param login
     * @param senha
     * @param enrollment
     * @param imgLocation
     * @return
     */
    public LiveData<Boolean> register(String login, String senha, String enrollment, String imgLocation) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                APIManager apiManager = new APIManager(getApplication());

                boolean b = apiManager.register(login, senha, enrollment, imgLocation);

                result.postValue(b);
            }
        });

        return result;
    }
}
