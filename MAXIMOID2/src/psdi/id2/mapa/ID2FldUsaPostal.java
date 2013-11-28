package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * @since 08 de Junho de 2010
 * 
 */
public class ID2FldUsaPostal extends MboValueAdapter {

    /**
     * Método construtor de ID2FldUsaPostal
     * @param mbv 
     * @throws MXException
     */
    public ID2FldUsaPostal(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     * No momento em que o usuário desmarcar o campo ID2USAPOSTAL(YORN), os
     * campos listados anteriormente devem ser limpados.
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        //Uteis.espera("************* initValue()");

        super.validate();

        //Uteis.espera("************* após super.initValue()");
        if (getMboValue().getString().equals("N")) {
            //Uteis.espera("************* = \"N\"");
            Executa.limparMboValues(new MboValue[]{
                        getMboValue().getMbo().getMboValue("ID2CEPCODE2"),
                        getMboValue().getMbo().getMboValue("ID2ADDNUM2"),
                        getMboValue().getMbo().getMboValue("ID2ADDCOM2")
                    });
            //Uteis.espera("************* FIM...");
        }

    }

    public void action()
            throws MXException, RemoteException {
        if (getMboValue().getBoolean() == false) {
            Executa.limparMboValues(new MboValue[]{
                        getMboValue().getMbo().getMboValue("ID2CEPCODE2"),
                        getMboValue().getMbo().getMboValue("ID2ADDNUM2"),
                        getMboValue().getMbo().getMboValue("ID2ADDCOM2")
                    });
        }
        super.action();
    }
}
