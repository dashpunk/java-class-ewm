/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
/**
 *
 * @author jesse
 */
public class ID2SelElementosAppBean extends MultiselectDataBean {

    public ID2SelElementosAppBean()
    {
        super();
        System.out.println("###################### CONSTRUTOR");
    }

    public void selElementos()
    {

    	System.out.println("######################## ENTREI NO ELEMENTOS()");
        int i = 0;

        try
        {
            while (getMboSet().getMbo(i) != null) {
                System.out.println("entrou 1");
                System.out.println("entrou 2");
                //System.out.println(i);
                if (getMboSet().getMbo(i).isSelected())
                {
                    System.out.println("entrou 3");
                    app.getDataBean("id2transeleline_table").addrow();
                    System.out.println("entrou 4");
                    app.getDataBean("id2transeleline_table").getMbo().setValue("ASSETNUM",getMboSet().getMbo(i).getString("ASSETNUM"));
                    System.out.println("entrou 5");
                    app.getDataBean("id2transeleline_table").refreshTable();
                }
                System.out.println("entrou 7");
                i++;
            }
            System.out.println("entrou 8");
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        }
        catch(MXException mx)
        {
            mx.printStackTrace();
        }
        catch(RemoteException re)
        {
            re.printStackTrace();
        }
    }

}
