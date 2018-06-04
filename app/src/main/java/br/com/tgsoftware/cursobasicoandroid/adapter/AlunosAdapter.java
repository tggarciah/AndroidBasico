package br.com.tgsoftware.cursobasicoandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.ListaAlunoActivity;
import br.com.tgsoftware.cursobasicoandroid.R;
import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class AlunosAdapter extends BaseAdapter {
    private final Context context;
    private final List<Aluno> alunos;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        //Aqui reaproveitamos a view caso ela esteja inflada.
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView campoNome = view.findViewById(R.id.item_nome);
        TextView campoTelefone = view.findViewById(R.id.item_telefone);
        ImageView campoFoto = view.findViewById(R.id.item_foto);
        TextView campoEndereco = view.findViewById(R.id.item_endereco);
        TextView campoSite = view.findViewById(R.id.item_site);
        String caminhoFoto = aluno.getCaminhoFoto();

        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if (campoEndereco != null) {
            campoEndereco.setText(aluno.getEndereco());
        }
        if (campoSite != null) {
            campoSite.setText(aluno.getSite());
        }

        return view;
    }
}