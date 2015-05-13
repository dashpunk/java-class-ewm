package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Uteis;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldAlnNumerico extends MboValueAdapter {

    public ID2FldAlnNumerico(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("MMyyyy");
        String mesAnoAtual = formatador.format(data);
        int ano = Integer.valueOf(mesAnoAtual.substring(2, 6));

        String valor = new String();
        valor = getMboValue().getString();
        getMboValue().setValue(Uteis.getApenasNumeros(valor));
        if (getMboValue().getInt() < ano) {
            throw new MXApplicationException("company", "AnoInvalido");
        }
                
        super.validate();
    }
}
