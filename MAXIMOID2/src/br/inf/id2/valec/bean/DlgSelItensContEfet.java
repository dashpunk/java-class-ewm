package br.inf.id2.valec.bean;

import psdi.webclient.system.controller.*;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.mbo.*;
import psdi.util.MXApplicationException;

/**
 * @author Jesse Rovira
 */
public class DlgSelItensContEfet extends DlgSelItensBase {

    public DlgSelItensContEfet() {
    }

    public void efetivar() throws MXException, RemoteException {

        DataBean actualsBeanSet = app.getDataBean("actuals_actuals_acmaterials_acmaterials_table");
        Iterator itSelec = getMboSet().getSelection().iterator();
        double indice = 1;
        //String indicesAuxentes = "";
        while (itSelec.hasNext()) {
            //System.out.println("############### Entrou = " + ++i);
            MboRemote item = (MboRemote) itSelec.next();
            //ADICIONA LINHA NA TABELA DE MEDIÇÃO
            actualsBeanSet.addrow();
            /*
            //item.getMboSet("MXRL01INDCOMP").setWhere("dscomp = to_char(to_date('" + getParent().getMbo().getString("SCHEDSTART") + "','DD/MM/YY HH24:MI:SS'),'MM/YYYY')");
            //item.getMboSet("MXRL01INDCOMP").reset();
            System.out.println("--------------------------- count " + item.getMboSet("MXRL01INDCOMP").count());
            if (item.getMboSet("MXRL01INDCOMP").count() > 0) {
                indice = item.getMboSet("MXRL01INDCOMP").getMbo(0).getDouble("nuvlrest");
                actualsBeanSet.getMbo().setValue("INDCOMPID", item.getMboSet("MXRL01INDCOMP").getMbo(0).getInt("tbindcomp01id"));
            } else {
                indicesAuxentes += item.getMboSet("ITEM").getMbo(0).getString("DESCRIPTION")+"\n";
            }
            actualsBeanSet.getMbo().setValue("NUVLRIND", indice);
            */
            //FIM DA APLICAï¿½ï¿½O DE ï¿½NDICES
            actualsBeanSet.getMbo().setValue("LINETYPE", "MATERIAL");
            actualsBeanSet.getMbo().setValue("ID2ITEMNUMOB", item.getString("ITEMNUM"));
            actualsBeanSet.getMbo().setValue("DESCRIPTION", item.getString("ITEM.DESCRIPTION"));
            actualsBeanSet.getMbo().setValue("UNITCOST", item.getDouble("AMCUSTMATE") * indice);
            actualsBeanSet.getMbo().setValue("MXINITCUST", item.getDouble("AMCUSTMATE"));
            actualsBeanSet.getMbo().setValue("POSITIVEQUANTITY", 1);
            indice = 1;
        }
        /*
        if (!indicesAuxentes.equals("")) {
            throw new MXApplicationException("id2message", "valoresAusentes", new String[]{indicesAuxentes});
        }
        */
        Utility.sendEvent(new WebClientEvent("dialogcancel", app.getCurrentPageId(), null, sessionContext));
    }
}
