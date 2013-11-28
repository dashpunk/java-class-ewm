package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.util.*;
import java.util.Date;
import psdi.mbo.MboSet;
import psdi.webclient.system.controller.*;
import psdi.id2.Validar;

public class ID2AglomeracaoAppBean extends psdi.webclient.beans.storeroom.StoreroomAppBean {

    /**
     * M?todo construtor de ID2AglomeracaoAppBean
     */
    public ID2AglomeracaoAppBean() {
    }

    /**
     *
     * Sobrescrita do método validate BMXZZ0002E
     *<p>
     * 
     * Caso o tipo de localização (ID2TIPLOC) seja igual a '01 limpar os campos (ID2LOCUF, ID2CODMUN, ID2AGLEND). Caso contrário limpar o ID2CODLOC
     *
     * Valida se a data final deve ser igual ou maior que a data inicial
     *
     * @since 14/04/2010
     */
    public int SAVE() throws MXException, RemoteException {

        //controle de tipo de propriedade
        if (getMbo().getString("ID2TIPLOC").equals("01")) {
            getMbo().setValueNull("ID2AGLEND");
        } else {
            getMbo().setValueNull("ID2CODLOC");
        }

        String param[] = {new String()};

        if (!Validar.preenchimentoMinimoObrigatorio((MboSet) getMbo().getMboSet("ID2VWLUCRESTEC"), 1)) {
            throw new MXApplicationException("company", "NumeroRelacionamentoInvalidoAglomeracao");
        }

        Date aDataPerIni = getMbo().getDate("ID2PERINI");
        Date aDataPerFim = getMbo().getDate("ID2PERFIN");

        if (aDataPerIni != null && aDataPerFim != null) {
            if ((aDataPerIni.after(aDataPerFim))) {
                throw new MXApplicationException("company", "DataPeriodoInvalida", param);
            } else {
                return super.SAVE();
            }
        } else {
            return super.SAVE();
        }
    }

    public void ADDROWONTABLE() {
        WebClientEvent event = sessionContext.getCurrentEvent();
        System.out.println("Teste 1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        System.out.println("Teste 2");
    }
}

