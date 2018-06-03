package br.com.tgsoftware.cursobasicoandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.adapter.AlunosAdapter;
import br.com.tgsoftware.cursobasicoandroid.dao.AlunoDao;
import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class ListaAlunoActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        //String[] alunos = {"Daniel","Ronaldo", "Jeferson", "Felipe", "Daniel 2","Ronaldo 2", "Jeferson 2", "Felipe 2", "Daniel 3","Ronaldo 3", "Jeferson 3", "Felipe 3"};
        listaAlunos = (ListView) findViewById(R.id.listview_lista_aluno);
        carregarListaAluno();

        Button btnNovoAluno = findViewById(R.id.btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                //Toast.makeText(ListaAlunoActivity.this, "Aluno " + aluno.getNome() + " clicado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });
        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarListaAluno();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        MenuItem itemSite = menu.add("Visitar Site");
        MenuItem itemSMS = menu.add("Enviar SMS");
        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        MenuItem itemDeletar = menu.add("Deletar");

        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDao alunoDao = new AlunoDao(ListaAlunoActivity.this);
                alunoDao.deleta(aluno);
                alunoDao.close();

                carregarListaAluno();
                return false;
            }
        });

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);
    }

    private void carregarListaAluno() {
        AlunoDao alunoDao = new AlunoDao(this);
        List<Aluno> alunos = alunoDao.buscarAlunos();
        alunoDao.close();

        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }
}