/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.mintur.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class BasicoPrintBohE extends DataBean {

    public BasicoPrintBohE() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
    }

    public void Imprimir() {

        try {
            System.out.println("---imprimir C");
//            executaProcedure();
            System.out.println("--- apos executeproc");
            System.out.println("################  - Imprimir");
            Hashtable reportParams = new Hashtable();
            java.lang.String[] attrs = getAttributes();
            for (int i = 0; i < attrs.length; i++) {
                System.out.println("################ Atributo (" + i + ")=" + attrs[i]);
                reportParams.put(attrs[i], (String) getMbo().getString(attrs[i]));
            }

            reportParams.put("SNCODCNPJ", app.getDataBean().getMbo().getString("CO_CNPJ"));
            System.out.println("--- currentposition ---> " + app.getDataBean().getMbo().getMboSet("SNRLBOH").getCurrentPosition());
            System.out.println("--- currentposition mes ---> " + app.getDataBean().getMbo().getMboSet("SNRLBOH").getMbo(app.getDataBean().getMbo().getMboSet("SNRLBOH").getCurrentPosition()).getString("MES"));
            System.out.println("--- currentposition ano ---> " + app.getDataBean().getMbo().getMboSet("SNRLBOH").getMbo(app.getDataBean().getMbo().getMboSet("SNRLBOH").getCurrentPosition()).getInt("ANO"));
            reportParams.put("SNMES", app.getDataBean().getMbo().getMboSet("SNRLBOH").getMbo(app.getDataBean().getMbo().getMboSet("SNRLBOH").getCurrentPosition()).getString("MES"));
            reportParams.put("SNANO", app.getDataBean().getMbo().getMboSet("SNRLBOH").getMbo(app.getDataBean().getMbo().getMboSet("SNRLBOH").getCurrentPosition()).getInt("ANO"));

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
//
//    private void executaProcedure() throws MXException, RemoteException {
//
//        Properties prop;
//
//        System.out.println("---vals ");
//        System.out.println("---vals " + getMbo().getString("SNCODCNPJ"));
//        System.out.println("---vals " + getMbo().getString("SNMES"));
//        System.out.println("---vals " + getMbo().getString("SNANO"));
//
//        if (getMbo().isNull("SNCODCNPJ") || getMbo().isNull("SNMES") || getMbo().isNull("SNANO")) {
//            throw new MXApplicationException("boh", "atributoCnpjOuMesOuAnoFaltando");
//        }
//
//        Date dataAtual = new Date();
//
//        System.out.println("---data atual " + dataAtual);
//
//        Calendar dataRef = Calendar.getInstance();
//
//        dataRef.setTime(dataAtual);
//
//        int anoAtual = dataRef.get(Calendar.YEAR);
//        int mesAtual = dataRef.get(Calendar.MONTH) + 1;
//        int mes = getMbo().getInt("SNMES");
//        int ano = getMbo().getInt("SNANO");
//
//        System.out.println("anoAtual " + anoAtual);
//        System.out.println("ano " + ano);
//        System.out.println("mesAtual " + mesAtual);
//        System.out.println("mes " + mes);
//
//        if ((ano > dataRef.get(Calendar.YEAR))
//                || ((ano == anoAtual) && (mes >= mesAtual))) {
//            throw new MXApplicationException("boh", "dataInvalida");
//        }
//
//
//    }
}
