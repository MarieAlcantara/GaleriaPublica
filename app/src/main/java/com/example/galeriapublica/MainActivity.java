package com.example.galeriapublica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //definicao do botao como atributo da classe MainActivity
    BottomNavigationView bottomNavigationView;

    static int RESULT_REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //referencia ao MainViewModel
        final MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);

        //referencia ao BottonNavigationView
        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            //o metodo sera chamado ao usuario clicar no botao
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //guardamos  dentro do ViewModelModel a opcao que foi escolhida pelo usuario
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    //define as opcoes que serao realizadas em cada acao
                    case R.id.gridViewOp:
                        GridViewFragment gridViewFragment = GridViewFragment.newInstance();
                        setFragment(gridViewFragment);
                        break;
                    case R.id.listViewOp:
                        ListViewFragment listViewFragment = ListViewFragment.newInstance();
                        setFragment(listViewFragment);
                        break;
                }
                return true;
            }

        });
    }

    //o metodo setFragment recebe como parametro um fragment
    void setFragment(Fragment fragment) {
        //e iniciada uma transacao do gerenciador de fraqmentos

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //o fragment sera setado no espaco definido pelo elemento de UIfragConteiner

        fragmentTransaction.replace(R.id.fragConteiner, fragment);
        //indicamos que esse fragmento agora a faz parte da pilha de tela do botao voltar do Android

        fragmentTransaction.addToBackStack(null);

        //e realizado o commmit da transacao
        fragmentTransaction.commit();
    }

    //usado para pedir acesso as imagens do usuario
    @Override
    protected void onResume() {
        MainActivity.super.onResume();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);
    }

    //metodo que aceita como entrada uma lista de permissoes
    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        //cada permissao e verificada
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
            else {
                MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
                int navigationOpSelected = vm.getNavigationOpSelected();
                bottomNavigationView.setSelectedItemId(navigationOpSelected);
            }
        }
    }

    //verifica se uma determinada permissao ja foi concedida ou nao
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    //metodo chamado apos o usuario conceder ou nao as permissoes
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if (requestCode == RESULT_REQUEST_PERMISSION) {
            for (String permission : permissions) {
                if (!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        //se alguma permissao nao foi concedida, e informado ao usuario que ele precisa de permitir para usar a app
        if (permissionsRejected.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar essa app é preciso conceder essas permissões").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }
                    }).create().show();
                }
            }
        }
    }

}