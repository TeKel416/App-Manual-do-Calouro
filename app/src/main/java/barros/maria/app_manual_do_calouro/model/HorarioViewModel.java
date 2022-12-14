package barros.maria.app_manual_do_calouro.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HorarioViewModel extends AndroidViewModel {

    public HorarioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<HashMap<String, List<Horario>>> getHorariosLD(Integer curso, Integer modulo) {

        MutableLiveData<HashMap<String, List<Horario>>> result = new MutableLiveData<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                APIManager apiManager = new APIManager(getApplication());

                HashMap<String, List<Horario>> map = apiManager.getHorario(curso, modulo);

                result.postValue(map);
            }
        });

        return result;
    }
}
