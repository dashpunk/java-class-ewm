/**
 * @author Ricardo S Gomes
 */
package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ExecutaProcedureBOH extends DataBean {

    private static final String PROCEDURE = "FNRH_GERA_BOH_10";

    public ExecutaProcedureBOH() {
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        try {
            getMbo().setValue("SNCODCNPJ", app.getDataBean().getMboSet().getMbo().getString("CO_CNPJ"), MboConstants.NOACCESSCHECK);
        } catch (Exception ex) {
            Logger.getLogger(ExecutaProcedureBOH.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Imprimir() throws MXException, RemoteException {
        System.out.println("---imprimir");
        executaProcedure();
        System.out.println("--- apos executeproc");
        try {
            System.out.println("################  - Imprimir");
            Hashtable reportParams = new Hashtable();
            java.lang.String[] attrs = getAttributes();
            for (int i = 0; i < attrs.length; i++) {
                System.out.println("################ Atributo (" + i + ")=" + attrs[i]);
                reportParams.put(attrs[i], (String) getMbo().getString(attrs[i]));
            }
            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
            System.out.println("############ Enviado ao Maximo");

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    public void executaProcedure() throws MXException, RemoteException {

        Properties prop;

        System.out.println("---vals ");
        System.out.println("---vals " + getMbo().getString("SNCODCNPJ"));
        System.out.println("---vals " + getMbo().getString("SNMES"));
        System.out.println("---vals " + getMbo().getString("SNANO"));

        if (getMbo().isNull("SNCODCNPJ") || getMbo().isNull("SNMES") || getMbo().isNull("SNANO")) {
            throw new MXApplicationException("boh", "atributoCnpjOuMesOuAnoFaltando");
        }

        Date dataAtual = new Date();

        System.out.println("---data atual " + dataAtual);

        Calendar dataRef = Calendar.getInstance();

        dataRef.setTime(dataAtual);

        int anoAtual = dataRef.get(Calendar.YEAR);
        int mesAtual = dataRef.get(Calendar.MONTH) + 1;
        int mes = getMbo().getInt("SNMES");
        int ano = getMbo().getInt("SNANO");

        System.out.println("anoAtual " + anoAtual);
        System.out.println("ano " + ano);
        System.out.println("mesAtual " + mesAtual);
        System.out.println("mes " + mes);

        if ((ano > dataRef.get(Calendar.YEAR))
                || ((ano == anoAtual) && (mes >= mesAtual))) {
            throw new MXApplicationException("boh", "dataInvalida");
        }


    }
}
