package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldEmissaoTipoLocal extends MboValueAdapter {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv 
     * @throws MXException
     */
    public ID2FldEmissaoTipoLocal(MboValue mbv) throws MXException {
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
        //Uteis.espera("************* inicio initValue ID2FldEmissaoTipoLocal");
        super.initValue();

        String valor = getMboValue().getString();

        Uteis.espera("valor = " + valor);

        try {

            if (valor != null) {
                if (valor.equals("02")) { //abatedouro
                    limparAtributosProcedenciaAglomeracao();
                    limparAtributosProcedenciaExploracaoAgropecuaria();
                } else if (valor.equals("03")) { //aglomeracao
                    limparAtributosProcedenciaAbatedouro();
                    limparAtributosProcedenciaExploracaoAgropecuaria();
                } else if (valor.equals("04")) { //exploracao
                    limparAtributosProcedenciaAbatedouro();
                    limparAtributosProcedenciaAglomeracao();
                }
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void limparAtributosProcedenciaAbatedouro() throws MXException, RemoteException {
        //Uteis.espera("**************** limparAtributosProcedenciaAbatedouro");
        getMboValue().getMbo().setValueNull("ID2PROTIPEST");
        getMboValue().getMbo().setValueNull("ID2PRONUMCONT");
        getMboValue().getMbo().setValueNull("ID2PROABA");
    }

    private void limparAtributosProcedenciaAglomeracao() throws MXException, RemoteException {
        //Uteis.espera("***************** limparAtributosProcedenciaAglomeracao");
        getMboValue().getMbo().setValueNull("ID2PROAGLCNPJ");
        getMboValue().getMbo().setValueNull("ID2PROTIPEVE");
        getMboValue().getMbo().setValueNull("ID2PROAGLNOMEVE");
        getMboValue().getMbo().setValueNull("ID2PROAGL");
    }

    private void limparAtributosProcedenciaExploracaoAgropecuaria() throws MXException, RemoteException {
        //Uteis.espera("******************* limparAtributosProcedenciaExploracaoAgropecuaria");
        getMboValue().getMbo().setValueNull("ID2PROEXPPER");
        getMboValue().getMbo().setValueNull("ID2PROEXPPROP");
        getMboValue().getMbo().setValueNull("ID2PROEXP");
    }
}
