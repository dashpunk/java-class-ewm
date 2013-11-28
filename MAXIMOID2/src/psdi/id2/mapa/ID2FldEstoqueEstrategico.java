package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Validar;

/**
 *
 * @author Ricardo S Gomes
 * @since 09 de Junho de 2010
 * 
 */
public class ID2FldEstoqueEstrategico extends MboValueAdapter {

    /**
     * Método construtor de ID2FldUsaPostal
     * @param mbv 
     * @throws MXException
     */
    public ID2FldEstoqueEstrategico(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>validate</b>
     * <p>
     * verifica se PRLINE.ID2ESTOQUEESTRATEGICO <= PRLINE.ORDERQTY 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {

        super.validate();

        if (!Validar.menorIgualQue(getMboValue(), getMboValue().getMbo().getMboValue("ORDERQTY"))) {
            throw new MXApplicationException("company", "EstoqueEstrategicoInvalido");
        }

    }
}
