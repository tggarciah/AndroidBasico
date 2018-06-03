package br.com.tgsoftware.cursobasicoandroid.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.connection.WebClient;
import br.com.tgsoftware.cursobasicoandroid.converter.AlunoConverter;
import br.com.tgsoftware.cursobasicoandroid.dao.AlunoDao;
import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class EnviaAlunoTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviado alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient client = new WebClient();
        AlunoConverter conversor = new AlunoConverter();
        AlunoDao alunoDao = new AlunoDao(context);

        List<Aluno> alunos = alunoDao.buscarAlunos();
        alunoDao.close();

        String json = conversor.converteParaJSON(alunos);

        String resultado = client.post(json);

        return resultado;
    }

    @Override
    protected void onPostExecute(String json) {
        dialog.dismiss();
        Toast.makeText(context, json, Toast.LENGTH_LONG).show();
    }
}
