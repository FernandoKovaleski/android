package br.com.findposto.model;

import java.io.Serializable;

/**
 * Created by Fernando on 14/05/2017.
 */
public class TipoServico implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public int id;
    public Login idLogin;
    public String nome;
    @Override
    public String toString (){
        return "TipoServico{" +
                "id=" + id +
                ", login='" + idLogin + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}