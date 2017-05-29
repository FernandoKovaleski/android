package br.com.findposto.model;

import java.io.Serializable;

public class Preco implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public int id;
    public String combustivel;
    public double valor;
    public String data;
    public Login idLogin;

    @Override
    public String toString (){
        return "Preco{" +
                "id=" + id +
                ", combustivel='" + combustivel + '\'' +
                ", valor='" + valor + '\'' +
                ", data='" + data + '\'' +
                ", login='" + idLogin + '\'' +
                '}';
    }
}