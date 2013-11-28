package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldEmissaoTipoLocalDestino extends MboValueAdapter {

    public ID2FldEmissaoTipoLocalDestino(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     *
     * Deve limpar os valores da seção caso o tipo destino seja alterado
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, RemoteException {
        //Uteis.espera("*************** inicio initValue ID2FldEmissaoTipoLocalDestino()");
        super.validate();

        String valor = getMboValue().getString();

        //Uteis.espera("************* valor = " + valor);
        try {

            if (valor != null) {
                if (valor.equals("02")) {
                    limparAtributosDestinoAglomeracao();
                    limparAtributosDestinoExploracaoAgropecuaria();
                } else if (valor.equals("03")) {
                    limparAtributosDestinoAbatedouro();
                    limparAtributosDestinoExploracaoAgropecuaria();
                } else if (valor.equals("04")) {
                    limparAtributosDestinoAbatedouro();
                    limparAtributosDestinoAglomeracao();
                }
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void limparAtributosDestinoAbatedouro() throws MXException, RemoteException {
        //Uteis.espera("*****************************limparAtributosDestinoAbatedouro");
        getMboValue().getMbo().setValueNull("ID2DESTIPEST");
        getMboValue().getMbo().setValueNull("ID2DESNUMCONT");
        getMboValue().getMbo().setValueNull("ID2DESABA");
    }

    private void limparAtributosDestinoAglomeracao() throws MXException, RemoteException {
        //Uteis.espera("**********************limparAtributosDestinoAglomeracao");
        getMboValue().getMbo().setValueNull("ID2DESAGLCNPJ");
        getMboValue().getMbo().setValueNull("ID2DESTIPEVE");
        getMboValue().getMbo().setValueNull("ID2DESAGLNOMEVE");
        getMboValue().getMbo().setValueNull("ID2DESAGL");
    }

    private void limparAtributosDestinoExploracaoAgropecuaria() throws MXException, RemoteException {
        //Uteis.espera("********************limparAtributosDestinoExploracaoAgropecuaria");
        getMboValue().getMbo().setValueNull("ID2DESEXPPER");
        getMboValue().getMbo().setValueNull("ID2DESEXPPROP");
        getMboValue().getMbo().setValueNull("ID2DESEXP");
    }
}
