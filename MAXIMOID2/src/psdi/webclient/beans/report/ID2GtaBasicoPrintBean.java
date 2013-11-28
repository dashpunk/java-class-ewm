/*
 * Esta classe � a bean que ap�ia a tela de passagem de par�metros para o relat�rio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas intera��es em tela devem ocorrer, como por exemplo passar o nome do relat�rio incluindo
 * a extens�o .jasper no atributo "Valor do Evento" no bot�o de OK da caixa de di�logo
 */
/**
 *
 * @author Jessé Rovira
 */
package psdi.webclient.beans.report;

import java.rmi.RemoteException;
import java.util.Hashtable;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;

import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ID2GtaBasicoPrintBean extends DataBean {

    public ID2GtaBasicoPrintBean() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            reportParams.put("ID2NUMGTA", (String) parent.getMbo().getString("ID2NUMGTA"));
            reportParams.put("ID2SERIE", (String) parent.getMbo().getString("ID2SERIE"));
            reportParams.put("ID2LOCUF", (String) parent.getMbo().getString("ID2LOCUF"));


            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            //Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

            //Popula um contador e exibe uma mensagem de a cordo com a qtd do mesmo
            System.out.println("--- contador a " + parent.getMbo().getInt("PRINTREPORT"));
            System.out.println("--- contador b " + app.getDataBean("MAINRECORD").getMbo().getInt("PRINTREPORT"));
            int contadorImpressao = parent.getMbo().getInt("PRINTREPORT");
            if (contadorImpressao > 2) {
                String yesNoId = getClass().getName();
                int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), parent.getMbo().getUserInfo());

                Utility.showMessageBox(event, new MXApplicationException("process", "contadorImpressaoExcedido"));
            }
            ++contadorImpressao;
            parent.getMbo().setValue("PRINTREPORT", contadorImpressao, MboConstants.NOACCESSCHECK);
//
//            parent.save();
            MboSet po;
            po = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PO", sessionContext.getUserInfo());

            System.out.println("--- poid " + parent.getMbo().getInt("POID"));

            po.setWhere("POID = '" + parent.getMbo().getInt("POID") + "'");
            po.reset();
            System.out.println("---po count "+po.count());
            if (po.count() > 0) {
                po.getMbo(0).setValue("PRINTREPORT", contadorImpressao, MboConstants.NOACCESSCHECK);
                System.out.println("---- saveb");
                po.save();
                System.out.println("---- savea");
            }

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
