package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.Validar;
import psdi.mbo.MboSet;
import psdi.util.*;
import psdi.webclient.system.controller.*;


public class ID2PropriedadeRuralAppBean extends psdi.webclient.beans.storeroom.StoreroomAppBean {

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     */
    public ID2PropriedadeRuralAppBean() {
    }

    /**
     *
     * Sobrescrita do método SAVE
     *<p>
     * Valida se o código da Unidade da Federação faz parte da formação do código da
     * priedade. Abaixo exemplo de um código de propriedade valido:
     * <p>
     * Código da UF = <b>123</b>
     *<p>
     * Código da Propriedade = <b>123</b>00009871
     * @since 10/03/2010
     * @since 24/03/2010
     */
    public int SAVE() throws MXException, RemoteException {


        String param[] = {new String()};
        //TODO validar método (getMboValue() ou getMboValueData() de chamada e parâmetro
        String aCodigoUF = getMbo().getString("ID2LOCUF");
        //TODO validar método (getMboValue() ou getMboValueData() de chamada e parâmetro
        String aCodigoPropriedadeRural = getMbo().getString("LOCATION");

        if (!Validar.preenchimentoMinimoObrigatorio((MboSet) getMbo().getMboSet("ID2LUCTYPE14"), 1)) {
            throw new MXApplicationException("company", "NumeroRelacionamentoInvalidoPropriedade");
        }

        if (!Validar.preencimentoMaximoObrigatorio((MboSet) getMbo().getMboSet("ID2COOEXT"), "MAIN", 1)) {
            throw new MXApplicationException("company", "CorrdenadaPrincipal", param);
        }


        //TODO validar se existe validador para o Código do muniCópio, uma vez que o mesmo serve como parâmetro para o tamanho da validação
        int ret = -1;
        if (aCodigoPropriedadeRural.length() > 0) {
            if (aCodigoPropriedadeRural.substring(0, 2).equals(aCodigoUF)) {
                ret = super.SAVE();
            } else {
                //TODO validar parâmetros
                throw new MXApplicationException("company", "CodigoPropriedadeInvalido", param);
            }
        }
        return ret;
    }

    public void ADDROWONTABLE() {
        WebClientEvent event = sessionContext.getCurrentEvent();
        System.out.println("Teste 1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        System.out.println("Teste 2");
    }
}
