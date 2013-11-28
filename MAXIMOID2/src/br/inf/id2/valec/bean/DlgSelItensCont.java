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
public class DlgSelItensCont extends DlgSelItensBase {

    public DlgSelItensCont() {
    }
    
	public void planejar() throws MXException, RemoteException {
		
		DataBean wpmatBeanSet = app.getDataBean("plans_plans_materials_materials_table");
		Iterator itSelec = getMboSet().getSelection().iterator();
		double indice = 1;
		//String indicesAuxentes = "";
		
		while (itSelec.hasNext()) {
			//System.out.println("############### Entrou = " + ++i);
			MboRemote item = (MboRemote) itSelec.next();
			//ADICIONA LINHA NA TABELA DE PLANEJAMENTO
			
			wpmatBeanSet.addrow();
			/*
			//APLICA��O DOS �NDICES
			item.getMboSet("MXRL01INDCOMP").setWhere("dscomp = to_char(to_date('"+getParent().getMbo().getString("SCHEDSTART")+"','DD/MM/YY HH24:MI:SS'),'MM/YYYY')");
			item.getMboSet("MXRL01INDCOMP").reset();

			if(item.getMboSet("MXRL01INDCOMP").count()>0)
			{
				indice = item.getMboSet("MXRL01INDCOMP").getMbo(0).getDouble("nuvlrest");
				wpmatBeanSet.getMbo().setValue("INDCOMPID", item.getMboSet("MXRL01INDCOMP").getMbo(0).getInt("tbindcomp01id"));
			} else {
				indicesAuxentes += item.getMboSet("ITEM").getMbo(0).getString("DESCRIPTION")+"\n";
            }
			wpmatBeanSet.getMbo().setValue("NUVLRIND", indice);
			//FIM DA APLICA��O DE �NDICE
			 */
			wpmatBeanSet.getMbo().setValue("ID2ITEMNUM", item.getString("ITEMNUM"));
			wpmatBeanSet.getMbo().setValue("ITEMNUM", item.getString("ITEMNUM"));
			wpmatBeanSet.getMbo().setValue("ORDERUNIT", item.getString("ORDERUNIT"));
			wpmatBeanSet.getMbo().setValue("UNITCOST", item.getDouble("AMCUSTMATE")*indice);
			wpmatBeanSet.getMbo().setValue("MXINITCUST", item.getDouble("AMCUSTMATE"));
			wpmatBeanSet.getMbo().setValue("AMCUSTMATE", item.getDouble("AMCUSTMATE")*indice);
			wpmatBeanSet.getMbo().setValue("ITEMQTY", 1);
			indice = 1;
		}
		/*
        if (!indicesAuxentes.equals("")) {
            throw new MXApplicationException("id2message", "valoresAusentes", new String [] {indicesAuxentes});
        }
        */
		Utility.sendEvent(new WebClientEvent("dialogcancel", app.getCurrentPageId(), null, sessionContext));
	}

}
