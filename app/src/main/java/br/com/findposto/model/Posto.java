package br.com.findposto.model;

import java.io.Serializable;

public class Posto implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public int id;
    public Login idLogin;
    public Bandeira idBandeira;
    public String ativo;
    public String nome;
    public String latitude;
    public String longitude;
    public String cidade;
    public String rua;
    public String numero;


    @Override
    public String toString() {
        return "Posto{" +
                "id=" + id +
                ", bandeira='" + idBandeira + '\'' +
                ", nome='" + nome + '\'' +
                ", cidade='" + cidade + '\'' +
                ", login='" + idLogin + '\'' +
                ", ativo='" + ativo + '\'' +
                ", rua='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
