package br.com.findposto.model;

import java.io.Serializable;

public class TipoServicoPosto implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public TipoServico idTipoServico;
    public Posto idPosto;
    public Login idLogin;

    @Override
    public String toString (){
        return "TipoServicoPosto {" +
                "tipoServico=" + idTipoServico +
                ", posto='" + idPosto + '\'' +
                ", login='" + idLogin + '\'' +
                '}';
    }
}
