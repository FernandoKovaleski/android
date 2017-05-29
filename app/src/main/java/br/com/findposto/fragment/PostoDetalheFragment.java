package br.com.findposto.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.findposto.R;
import br.com.findposto.activity.PostoActivity;
import br.com.findposto.model.Posto;
import br.com.findposto.model.Preco;
import br.com.findposto.model.TipoServicoPosto;


public class PostoDetalheFragment extends BaseFragment implements OnMapReadyCallback {

    private Posto posto;
    private Preco preco;
    private TipoServicoPosto tsp;

    private RadioButton rbCachoeirinha, rbGravatai;
    private TextView tvNome, tvRua, tvCidade,tvLatitude,tvLongitude, tvComum, tvAditivada,tvPremium,
    tvEtanol,tvDiesel;
    private CheckBox cbLavagem, cb24h, cbOleo,cbConveniencial;
    private ImageView imageView; //container para icone do posto
    private ProgressBar progressBarCard0; //progressBar do Card0, do container icone do posto
    private ProgressBar progressBarCard4;


    //utilizado pela Activity para repassar o objeto posto para este fragmento
    public void setPosto(Posto posto) {
        this.posto = posto;
    }

    public void setPreco (Preco preco){
        this.preco = preco;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //informa ao sistema que o fragment irá adicionar botões na ActionBar

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //infla o layout
        View view = inflater.inflate(R.layout.fragment_detalheposto, container, false);

        ((PostoActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_detalheposto); //um título para a janela

        //um log para depurar
        Log.d(TAG, "Dados do registro = " + posto);

        //carrega a imagem e controla o progressbar
        Log.d(TAG, "URL foto = " + posto.idBandeira); //um log para depurar
        imageView = (ImageView) view.findViewById(R.id.imageView);
        if(posto.idBandeira != null){
        progressBarCard0 = (ProgressBar) view.findViewById(R.id.pb_card0_frdetalheposto);
            Picasso.with(getContext()).load(String.valueOf(posto.idBandeira)).fit().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    progressBarCard0.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressBarCard0.setVisibility(View.GONE);
                }
            });
        }else{
            imageView.setImageResource(R.drawable.car_background);
        }


        //carrega o tipo nos RadioButtons


        //carrega o checkBox
        Log.d(TAG, "cidade = " + posto.cidade); //um log para depurar
        rbCachoeirinha = (RadioButton) view.findViewById(R.id.rb_cachoeirinha_card1_frndetalheposto);
        rbGravatai = (RadioButton) view.findViewById(R.id.rb_gravatai_card1_frdetalheposto);
        if (posto.cidade.equals(getContext().getResources().getString(R.string.cidade_cachoeirinha))) {
            rbCachoeirinha.setChecked(true);
        } else if (posto.cidade.equals(getContext().getResources().getString(R.string.cidade_gravatai))) {

            rbGravatai.setChecked(true);
        }


        //carrega o nome e rua e cidade
        Log.d(TAG, "Nome = " + posto.nome + "\n Rua = " + posto.rua + "\n Cidade = " + posto.cidade); //um log para depurar
        tvNome = (TextView) view.findViewById(R.id.tvNome_card0_frdetalheposto);
        tvRua = (TextView) view.findViewById(R.id.tvRua_card0_frdetalheposto);
        tvCidade = (TextView) view.findViewById(R.id.tvCidade_card0_frdetalheposto);
        tvNome.setText(posto.nome);
        tvCidade.setText(posto.cidade);
        tvRua.setText(posto.rua);
/**
        //carrega o valores dos combustiveis
        Log.d(TAG, "Comum = " + preco.comun + "\nAditivadad = " + preco.aditivada + "\nPremium = " + preco.premium
                + "Etanol = " + preco.etanol + "Diesel = " + preco.diesel);
        tvComum= (TextView) view.findViewById(R.id.tvComum_card3_frdetalheposto);
        tvAditivada = (TextView) view.findViewById(R.id.tvAditivada_card3_frdetalheposto);
        tvPremium= (TextView) view.findViewById(R.id.tvPremium_card3_frdetalheposto);
        tvEtanol = (TextView) view.findViewById(R.id.tvEtanol_card3_frdetalheposto);
        tvDiesel= (TextView) view.findViewById(R.id.tvDiesel_card3_frdetalheposto);
        tvComum.setText(preco.comum);
        tvAditivada.setText(posto.aditivada);
        tvPremium.setText(preco.premium);
        tvEtanol.setText(posto.etanol);
        tvDiesel.setText(preco.diesel);

**/
        //carrega a latitude e a longitude
        tvLatitude = (TextView) view.findViewById(R.id.tvLatitude_card4_frdetalheposto);
        tvLongitude = (TextView) view.findViewById(R.id.tvLongitude_card4_frdetalheposto);
        tvLatitude.setText(posto.latitude);
        tvLongitude.setText(posto.longitude);
        Log.d(TAG, "Latitude = " + posto.latitude + "\nlongitude = " + posto.longitude); //um log para depurar
        // Mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // Inicia o Google Maps dentro do fragment
        mapFragment.getMapAsync(this);

        //isso deve ser ajustado na próxima versão
        progressBarCard4.setVisibility(View.INVISIBLE);
        return view;
    }

    /*
        Infla o menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_detalheposto, menu);
    }

    /*
        Trata eventos dos itens de menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_editar: {
                //Substitui o Fragmento no container R.id.fragment_container, componente do layout content_main.xml
                PostoEdicaoFragment edicaopostoEdicaoFragment = new PostoEdicaoFragment();
                edicaopostoEdicaoFragment.setPosto(this.posto);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, edicaopostoEdicaoFragment).commit();
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return false;
    }

    /*
        Manipula o fragmento do Maps.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (posto != null && googleMap != null) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            googleMap.setMyLocationEnabled(true); //habilita o botão minha localização no mapa


            // parse das coordenadas de String para Double
            double lat = Double.parseDouble(posto.latitude);
            double lng = Double.parseDouble(posto.longitude);

            if (lat != 0 && lng != 0) {
                LatLng location = new LatLng(lat, lng); // Cria o objeto LatLng com a coordenada da fábrica

                Log.d(TAG, "Lat = " + lat + " Long = " + lng);

                // Posiciona o mapa na coordenada da fábrica (zoom = 13)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

                // Adiciona um marcador para o objeto location
                googleMap.addMarker(new MarkerOptions()
                        .title(posto.nome)
                        .snippet("Local de uma dos postos")
                        .position(location));

                // Objeto para controlar o zoom da câmera
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
                googleMap.moveCamera(update);

                googleMap.animateCamera(update, 2000, null); //anima o zoom

                // Marcador no local da fábrica
                googleMap.addMarker(new MarkerOptions()
                        .title(posto.nome)
                        .snippet(posto.rua)
                        .position(location));
            }

            // Tipo do mapa: MAP_TYPE_NORMAL,
            // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }
    }

}
