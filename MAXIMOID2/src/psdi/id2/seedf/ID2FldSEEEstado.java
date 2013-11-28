package psdi.id2.seedf;

import psdi.id2.*;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldSEEEstado extends MboValueAdapter {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv 
     * @throws MXException
     */
    public ID2FldSEEEstado(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     *
     * Deve limpar os valores da seção caso o tipo procedencia ou destino seja alterado
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.initValue();

        String valor = getMboValue().getString();
        //Uteis.espera("------------------------------ valor " + valor);
        try {

            if (valor != null) {
                getMboValue().getMbo().setValueNull("SEECIDADE");
            }

        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }
}
