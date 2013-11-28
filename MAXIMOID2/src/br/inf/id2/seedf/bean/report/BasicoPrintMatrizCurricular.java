/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.seedf.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class BasicoPrintMatrizCurricular extends DataBean {

    public BasicoPrintMatrizCurricular() {
    }

    public void Imprimir() throws MXException, RemoteException {


        Hashtable reportParams = new Hashtable();

        reportParams.put("SEEETAEDU", (String) parent.getMbo().getMboSet("SEERL01CURMOD").getMbo(0).getMboSet("SEERL01MODCURID").getMbo(0).getString("SEEETAEDU"));

        reportParams.put("SEEMODENS", (String) parent.getMbo().getMboSet("SEERL01CURMOD").
                getMbo(0).getMboSet("SEERL01MODCURID").
                getMbo(0).getString("SEEMODENS"));

        reportParams.put("SEEMETODO", (String) parent.getMbo().getMboSet("SEERL01CURMOD").
                getMbo(0).getMboSet("SEERL01METODO").
                getMbo(0).getString("SEE_SIGLA"));

        try {
            reportParams.put("SEETURNO", (String) parent.getMbo().getMboSet("SEERL01CURMOD").
                    getMbo(0).getMboSet("SEERL01TURNO").
                    getMbo(0).getString("SEE_SIGLA"));
        } catch (Exception e) {
            //Turno n�o � obrigat�rio
            System.out.println("#### N�o possui turno! N�o deve ser presencial! " + e.getMessage());
        }

        WebClientEvent event = sessionContext.getCurrentEvent();
        reportParams.put("relatorio", (String) event.getValue());
        app.put("relatorio", reportParams);
        //Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    }
}
