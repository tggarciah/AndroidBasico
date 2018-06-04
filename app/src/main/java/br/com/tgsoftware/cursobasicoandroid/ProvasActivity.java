package br.com.tgsoftware.cursobasicoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto Direto", "Alfabeto");
        Prova provaPortugues = new Prova("Português", "05/06/2018", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Equações de Segundo Grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matemática", "10/06/2018", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provas);

        ListView listaProvas = findViewById(R.id.listview_provas);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvasActivity.this, "Clicou na prova " + prova, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                intent.putExtra("prova", prova);
                startActivity(intent);
            }
        });
    }
}
