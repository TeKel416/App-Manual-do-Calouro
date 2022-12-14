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

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<HashMap<String, List<Horario>>> result = new MutableLiveData<>();

        // Cria uma nova linha de execução (thread). O android obriga que chamadas de rede sejam feitas
        // em uma linha de execução separada da principal.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Executa a nova linha de execução. Dentro dessa linha, iremos realizar as requisições ao
        // servidor web.
        executorService.execute(new Runnable() {
            /**
             * Tudo o que colocármos dentro da função run abaixo será executada dentro da nova linha
             * de execução.
             */
            @Override
            public void run() {

                // Criamos uma instância de ProductsRepository. É dentro dessa classe que estão os
                // métodos que se comunicam com o servidor web.
                APIManager productsRepository = new APIManager(getApplication());

                // O método addProduct envia os dados de um novo produto ao servidor. Ele retorna
                // um booleano indicando true caso o produto tenha sido cadastrado e false
                // em caso contrário
                HashMap<String, List<Horario>> map = productsRepository.getHorario(curso, modulo);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(map);
            }
        });

        return result;
    }
}
