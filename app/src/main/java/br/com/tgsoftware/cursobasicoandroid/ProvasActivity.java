package br.com.tgsoftware.cursobasicoandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.tgsoftware.cursobasicoandroid.fragments.DetalhesProvaFragment;
import br.com.tgsoftware.cursobasicoandroid.fragments.ListaProvasFragment;
import br.com.tgsoftware.cursobasicoandroid.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_principal, new ListaProvasFragment());
        if (estaNoModoPaisagem()) {
            transaction.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }
        transaction.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if (!estaNoModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();
            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prova", prova);
            detalhesFragment.setArguments(bundle);
            tx.replace(R.id.frame_principal, detalhesFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            DetalhesProvaFragment fragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            fragment.populaCamposCom(prova);
        }
    }
}
