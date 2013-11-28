/**
 *
 * @author Dyogo Dantas
 */
package br.inf.id2.mapa.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class GestaoRegistroEstabelecimento extends DataBean {

    public GestaoRegistroEstabelecimento() {
        System.out.println("################## GestaoRegistroEstabelecimento()");
    }

    public void Imprimir() {
        try {
            System.out.println("############### Entrou no imprimir - GestaoRegistroEstabelecimento");
            MboSet mboSetOcorrencia = (MboSet) app.getDataBean().getMbo(0).getMboSet("MARL15MATBOCO");
            Hashtable<String, Object> reportParams = new Hashtable<String, Object>();
            boolean itemMarcado = false;
            WebClientEvent event = sessionContext.getCurrentEvent();
            
            for (int i=0; i< mboSetOcorrencia.count(); i++) {
            	Mbo mboOcor = (Mbo) mboSetOcorrencia.getMbo(i);
            	System.out.println("############### SELECIONADO: " + mboOcor.getBoolean("PRINT"));
            	if (mboOcor.getBoolean("PRINT")) {
            		if (itemMarcado) {
                    	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
                		sessionContext.queueRefreshEvent();
                    	Utility.showMessageBox(event, new MXApplicationException("impressao", "SelecionadoMaisDeUm"));
                    	return;
            		}
            		System.out.println("############### Achei o selecionado: " + mboOcor.getInt("MATBOCOID") + "|" + 
            							mboOcor.getString("DESCRIPTION") + "|" + getMbo().getUserInfo().getUserName());
            		reportParams.put("MATBOCOID", mboOcor.getInt("MATBOCOID"));
            		reportParams.put("DESCRIPTION", mboOcor.getString("DESCRIPTION"));
            		reportParams.put("USERID", getMbo().getUserInfo().getUserName());
            		itemMarcado = true;
            	}
            }
            if (!itemMarcado) {
            	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        		sessionContext.queueRefreshEvent();
            	Utility.showMessageBox(event, new MXApplicationException("impressao", "SelecioneAoMenosUm"));
            	return;
            }
            

            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            System.out.println("############### Enviando parametros... saindo da classe");
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }

    }
}
