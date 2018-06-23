package br.com.tgsoftware.cursobasicoandroid.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.dao.AlunoDao;
import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap googleMapa;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMapa = googleMap;
        LatLng posicao;

        //TODO: melhorar esse endereço para que seja pego diretamente pelo celular
        String endereco = "Avenida Loreto 85, Jardim Santo André, Santo André";
        posicao = getCoordenadaDoEndereco(endereco);
        if (posicao != null) {
            centralizaEm(posicao);
        }

        AlunoDao alunoDao = new AlunoDao(getContext());
        for (Aluno aluno : alunoDao.buscarAlunos()) {
            posicao = getCoordenadaDoEndereco(aluno.getEndereco());
            if (posicao != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(posicao);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDao.close();
    }

    public LatLng getCoordenadaDoEndereco(String endereco) {
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);
            if (!enderecos.isEmpty()) {
                LatLng posicao = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void centralizaEm(LatLng coordenada) {
        if (googleMapa != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            googleMapa.moveCamera(cameraUpdate);
        }
    }
}
