package br.com.findposto.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.findposto.R;
import br.com.findposto.fragment.PostosFragment;

/**
 * Adaptador para o ViewPager.
 *
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;

    public TabsAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    /*
        Constrói cada ViewPager. Nesta construção associa um controlador para a View.
        ATENÇÃO: Este método é chamado baseado no valor definido no getCount().
     */
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        Fragment f = null;

        switch (position){
            case 0:
                f = new PostosFragment(); //controlador para a aba de índice zero
                args.putString("cidade", context.getString(R.string.tabs_todas));
                break;
            case 1:
                f = new PostosFragment(); //controlador para a aba de índice um
                args.putString("cidade", context.getString(R.string.tabs_cachoeirinha));
                break;
            case 2:
                f = new PostosFragment(); //controlador para a aba de índice dois
                args.putString("cidade", context.getString(R.string.tabs_gravatai));
                break;

        }

        f.setArguments(args);

        return f;
    }

    /*
        Define a quantidade de objetos ViewPager. Na visão do usuário, define a quantidade de abas que aparecerão na Toolbar.
     */
    @Override
    public int getCount() {
        return 3;
    }


    /*
        Insere os títulos nas abas da Toolbar.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tabs_todas);
            case 1:
                return context.getString(R.string.tabs_cachoeirinha);
            case 2:
                return context.getString(R.string.tabs_gravatai);

        }
        return null;
    }
}