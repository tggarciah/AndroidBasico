package br.com.tgsoftware.cursobasicoandroid.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.tgsoftware.cursobasicoandroid.FormularioActivity;
import br.com.tgsoftware.cursobasicoandroid.R;
import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_aluno_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_aluno_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_aluno_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_aluno_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_aluno_rating_bar);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_aluno_foto);
        aluno = new Aluno();
    }

    public Aluno pegarAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        this.aluno = aluno;
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());
    }

    public void carregaFoto(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
