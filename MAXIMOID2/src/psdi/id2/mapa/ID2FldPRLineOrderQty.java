package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.app.common.purchasing.FldPurOrderQty;

/**
 *
 * @author Ricardo  S Gomes
 * 
 */
public class ID2FldPRLineOrderQty extends FldPurOrderQty {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv 
     * @throws MXException
     */
    public ID2FldPRLineOrderQty(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     *
     * Deve limpar o valor do atributo ID2ESTOQUEESTRATEGICO
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {

        super.initValue();

        Executa.limparMboValues(new MboValue[]{getMboValue().getMbo().getMboValue("ID2ESTOQUEESTRATEGICO")});

    }
}
