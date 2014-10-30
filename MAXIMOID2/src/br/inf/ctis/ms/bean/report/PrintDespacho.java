package br.inf.ctis.ms.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;
import java.util.*;
import java.rmi.*;


public class PrintDespacho extends DataBean {

    public PrintDespacho() {
    	System.out.println("########### PrintDespacho - Entrou");
    }

    public void Imprimir() {
    	 try {
    		 System.out.println("########## PrintDespacho - Imprimir");
             Hashtable reportParams = new Hashtable();
             java.lang.String[] attrs = getAttributes();
             for (int i = 0; i < attrs.length; i++) {
             		System.out.println("########## Atributo (" + i + ")=" + attrs[i]);
                 reportParams.put(attrs[i], (String) getMbo().getString(attrs[i]));
             }
             System.out.println("########## MSTBOCID" + (String) parent.getMbo().getString("MSTBOCID"));
             reportParams.put("MSTBOCID", (String) parent.getMbo().getString("MSTBOCID"));
             reportParams.put("REPORTTYPE", "PDF");
             reportParams.put("DISPLAYNAME", getMbo().getUserInfo().getUserName());
             
             WebClientEvent event = sessionContext.getCurrentEvent();
             reportParams.put("relatorio", (String) event.getValue());
             app.put("relatorio", reportParams);
             Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
             System.out.println("########## Enviado ao Maximo");

         } catch (MXException mx) {
             mx.printStackTrace();
         } catch (RemoteException re) {
             re.printStackTrace();
         }
    }
}
