package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.Executa;
import psdi.id2.mapa.Uteis;
import psdi.mbo.MboConstants;
import psdi.util.*;
import psdi.webclient.beans.workorder.WorkorderAppBean;

public class ID2MTPRCCompAppBean extends WorkorderAppBean {

    /**
     * Método construtor de ID2ExploracaoPecuariaAppBean
     */
    public ID2MTPRCCompAppBean() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        Uteis.espera("--------------------- SAVE appBean do dia 08/11/2010");

        double valorTotal = 0;

        try {
            for (int i = 0; i < getMbo().getMboSet("MTRL02TBEMPCOM").count(); i++) {
                valorTotal += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL02TBEMPCOM").getMbo(i).getMboSet("MTRL01PO"));
            }
        } catch (Exception e) {
            valorTotal = 0D;
            Uteis.espera("--------------------- c exception = " + e.getMessage());
        }

        getMbo().setValue("MTVALORTOTAL", valorTotal, MboConstants.NOACTION);

        super.validate();
        Uteis.espera("--------------------- FIM SAVE appBean");
        return super.SAVE();
    }
}
