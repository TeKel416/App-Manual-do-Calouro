package barros.maria.app_manual_do_calouro.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import barros.maria.app_manual_do_calouro.R;
import barros.maria.app_manual_do_calouro.model.CadastroViewModel;
import barros.maria.app_manual_do_calouro.util.Util;

public class CadastroActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;
    CadastroViewModel cadastroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cadastroViewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

        String currentPhotoPath = cadastroViewModel.getCurrentPhotoPath();
        if (!currentPhotoPath.isEmpty()) {
            ImageView imvPhoto = findViewById(R.id.imvPhoto);
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
            imvPhoto.setImageBitmap(bitmap);
        }

        Button btnRegister = findViewById(R.id.btnCadastrar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = findViewById(R.id.etCadNome);
                final String name = etName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de nome n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etLogin = findViewById(R.id.etCadEmail);
                String login = etLogin.getText().toString();
                if (login.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de email n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etPassword = findViewById(R.id.etCadSenha);
                final String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de senha n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etPasswordCheck = findViewById(R.id.etCadSenhaCheck);
                String passwordCheck = etPasswordCheck.getText().toString();
                if (passwordCheck.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de checagem de senha n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(passwordCheck)) {
                    Toast.makeText(CadastroActivity.this, "Senhas n??o id??nticas", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etEnrollment = findViewById(R.id.etMatricula);
                String enrollment = etEnrollment.getText().toString();
                if (enrollment.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de matr??cula n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                String currentPhotoPath = cadastroViewModel.getCurrentPhotoPath();
                if (currentPhotoPath.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de foto de perfil n??o preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    int h = (int) getResources().getDimension(R.dimen.imv_height);
                    Util.scaleImage(currentPhotoPath, -1, 2*h);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                LiveData<Boolean> resultLD = cadastroViewModel.register(name, login, password, enrollment, currentPhotoPath);
                
                resultLD.observe(CadastroActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CadastroActivity.this, "Erro ao registrar novo usu??rio", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        ImageView imvPhoto = findViewById(R.id.imvPhoto);
        imvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dispatchGalleryOrCameraIntent(); }
        });
    }

    public void dispatchGalleryOrCameraIntent() {
        File f = null;

        try {
            f = createImageFile();

        } catch (IOException e) {
            Toast.makeText(CadastroActivity.this, "N??o foi poss??vel o arqivo", Toast.LENGTH_LONG).show();
            return;
        }

        if (f != null) {
            cadastroViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(CadastroActivity.this, "barros.maria.app_manual_do_calouro.fileprovider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);

        } else {
            Toast.makeText(CadastroActivity.this, "N??o foi poss??vel criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {
            String currentPhotoPath = cadastroViewModel.getCurrentPhotoPath();

            if (resultCode == Activity.RESULT_OK) {
                ImageView imvPhoto = findViewById(R.id.imvPhoto);

                Uri selectedPhoto = data.getData();

                if (selectedPhoto != null) {
                    try {
                        Bitmap bitmap = Util.getBitmap(this, selectedPhoto);
                        Util.saveImage(bitmap, currentPhotoPath);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
                imvPhoto.setImageBitmap(bitmap);

            } else {
                File f = new File(currentPhotoPath);
                f.delete();
                cadastroViewModel.setCurrentPhotoPath("");
            }
        }
    }
}