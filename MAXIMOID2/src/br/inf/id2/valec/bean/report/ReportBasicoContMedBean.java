/**
 *
 * @author Jesse Rovira
 */
package br.inf.id2.valec.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.inf.id2.common.bean.report.ReportBasicoBean;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ReportBasicoContMedBean extends ReportBasicoBean {

    public ReportBasicoContMedBean() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            System.out.println("############## "+app.getDataBean("MAINRECORD").getMbo().getMboSet("rl01wo").getMbo().getString("WONUM"));
            reportParams.put("WONUM2", app.getDataBean("MAINRECORD").getMbo().getMboSet("rl01wo").getMbo().getString("WONUM"));
            super.Imprimir();

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
