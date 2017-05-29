package br.com.findposto.model;

import java.io.Serializable;

public class Bandeira implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public int id;
    public String nome;
    public Login idLogin;

    @Override
    public String toString(){
        return "Bandeira {" +
                "id '"    + id + '\''+
                "nome '" + nome + '\'' +
                "login '" + idLogin + '\'' +
                "}";
    }
}
