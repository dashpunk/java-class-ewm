package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.app.common.purchasing.FldPurOrderQty;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 * Antes extendia de: psdi.app.common.purchasing.FldPurOrderQty
 * 
 */
public class ID2FldOrderQty extends FldPurOrderQty {

    /**
     * Método construtor de ID2FldOrderQty
     * @param mbv 
     * @throws MXException
     */
    public ID2FldOrderQty(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        //super.validate();

        if (getMboValue("RECEIVEDQTY").getDouble() > getMboValue("ORDERQTY").getDouble()) {
            throw new MXApplicationException("poline", "recebimentoMaioEnvio");
        }
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        //super.initValue();
    }

    



    public void action() throws MXException, RemoteException {
        boolean alterarValor;


        alterarValor = (getMboValue().getDouble() == 0);

        super.action();

        if (alterarValor) {
            Uteis.espera("+++++++++++++++++ Alterar valor entrou era = "+getMboValue().getDouble());
            getMboValue().setValue(0D,MboConstants.NOACTION);
            Uteis.espera("+++++++++++++++++ Alterar valor após voltar para 0 = "+getMboValue().getDouble());
        }

    }
}
