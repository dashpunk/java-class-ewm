package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldDtDist extends MboValueAdapter {

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     * @param mbv 
     * @throws MXException
     */
    public ID2FldDtDist(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do método validate  
     *<p>
     * Valida se o <b>data dist</b> maior que a data atual
     *@throws MXException 
     * @throws RemoteException
     * @since 19/06/2010
     */
    @Override
    public void validate() throws MXException, RemoteException {
        //Uteis.espera("****************** Entrou em validate de ID2FlsDtDist");
        Date valor = new Date();
        valor = getMboValue().getDate();
        //Uteis.espera("****************** antes de super");
        super.validate();
        //Uteis.espera("****************** após de super");
        if (valor.after(new Date())) {
            //Uteis.espera("****************** certo");
            return;
        } else {
            //Uteis.espera("****************** errado");
            throw new MXApplicationException("company", "DataInvalida");
        }
    }
}
