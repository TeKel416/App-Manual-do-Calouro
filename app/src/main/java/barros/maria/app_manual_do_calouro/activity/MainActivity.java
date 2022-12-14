package barros.maria.app_manual_do_calouro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import barros.maria.app_manual_do_calouro.R;
import barros.maria.app_manual_do_calouro.model.Horario;
import barros.maria.app_manual_do_calouro.util.Config;

public class MainActivity extends AppCompatActivity {

    static int RESULT_REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        checkForPermissions(permissions);

        Button      btnLogar       = findViewById(R.id.btnLogar);
        ImageButton btnMapa        = findViewById(R.id.btnMapa);
        ImageButton btnRod         = findViewById(R.id.btnRod);
        ImageButton btnHorario     = findViewById(R.id.btnHorario);
        ImageButton btnCalendario  = findViewById(R.id.btnCalendario);
        ImageButton btnSobre       = findViewById(R.id.btnSobre);
        ImageButton btnContatos    = findViewById(R.id.btnContatos);
        Button      btnFaleConosco = findViewById(R.id.btnFaleConosco);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(i);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2/manual-do-calouro/map"));

                startActivity(i);
            }
        });

        btnRod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2/manual-do-calouro/rod"));

                startActivity(i);
            }
        });

        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HorarioActivity.class);

                startActivity(i);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2/manual-do-calouro/calendar"));

                startActivity(i);
            }
        });

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SobreActivity.class);

                startActivity(i);
            }
        });

        btnContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContatoActivity.class);

                startActivity(i);
            }
        });

        btnFaleConosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FaleConoscoActivity.class);

                startActivity(i);
            }
        });
    }

    /**
     * Verifica se as permiss??es necess??rias j?? foram concedidas. Caso contr??rio, o usu??rio recebe
     * uma janela pedindo para conceder as permiss??es
     * @param permissions lista de permiss??es que se quer verificar
     */
    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions) {
            if( !hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }

    /**
     * Verifica se uma permiss??o j?? foi concedida
     * @param permission
     * @return true caso sim, false caso n??o.
     */
    private boolean hasPermission(String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * M??todo chamado depois que o usu??rio j?? escolheu as permiss??es que quer conceder. Esse m??todo
     * indica o resultado das escolhas do usu??rio.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {

            for(String permission : permissions) {
                if(!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this).
                            setMessage("Para usar essa app ?? preciso conceder essas permiss??es").
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                }
                            }).create().show();
                }
            }
        }
    }
}