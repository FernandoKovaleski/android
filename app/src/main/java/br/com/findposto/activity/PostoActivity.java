package br.com.findposto.activity;

import android.os.Bundle;
import android.util.Log;

import br.com.findposto.R;
import br.com.findposto.fragment.PostoDetalheFragment;
import br.com.findposto.model.Posto;

;

/**
 * Esta classe é um container para os fragmentos CarroNovoFragment, CarroDetalheFragment
 * e CarroEdicaoFragment.
 *
 */
public class PostoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //associa um layout a esta Activity
        setContentView(R.layout.activity_posto);

        //obtém do extras da intent recebida o fragmento que ela deve abrir
        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");

        if(msg.equals("PostoDetalheFragment")){
            //constrói uma instância do Fragment PostoDetalheFragment
            PostoDetalheFragment postoDetalheFragment = new PostoDetalheFragment();
            //insere o fragmento como conteúdo de content_main.xml
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postoDetalheFragment).commit();
            //obtém o posto que foi repassado pela PostosActivity ao chamar esta Activity
            Posto posto = (Posto) getIntent().getSerializableExtra("posto");
            Log.d(TAG, "Objeto posto recebido em PostoActivity: " + posto.toString()); //um log para o LogCat
            //repassa o objeto carro para o fragmento
            postoDetalheFragment.setPosto(posto);
        }
    }
}
