package br.inf.id2.ms.bean;

import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.controller.Utility;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.app.po.PORemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class PRLineBean extends psdi.webclient.beans.po.PRLineBean {

    public PRLineBean() {
        super();
    }

    public int execute() throws MXException, RemoteException {

        PORemote po = (PORemote) parent.getMbo();
        MboRemote mboDestino;
        MboRemote mboOrigem;


        System.out.println("-------- PRLineBean " + getMboSet().count());
        for (int i = 0; ((mboOrigem = getMboSet().getMbo(i)) != null); i++) {
            System.out.println("-------- i " + i);
            System.out.println("--- select " + mboOrigem.isSelected());
            System.out.println("--- select " + getMboSet().getMbo(i).isSelected());
            if (mboOrigem.isSelected()) {
                System.out.println("-------- selecionado " + i);
                mboDestino = po.getMboSet("POLINE").add();
                mboDestino.setValue("linetype", "MATERIAL", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("ponum", po.getString("ponum"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("orderqty", mboOrigem.getDouble("orderqty"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("orderunit", mboOrigem.getString("orderunit"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("unitcost", mboOrigem.getDouble("unitcost"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("receivedunitcost", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("receivedtotalcost", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("rejectedqty", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("enterdate", new Date(), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("enterby", po.getUserInfo().getUserName(), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("description", mboOrigem.getString("description"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("requestedby", mboOrigem.getString("requestedby"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("issue", 1, MboConstants.NOACCESSCHECK);
                //mboDestino.setValue("polinenum", "sequencial para aquela po", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("taxed", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("chargestore", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("gldebitacct", "0.0.0.0", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("linecost", mboOrigem.getDouble("linecost"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tax1", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tax2", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tax3", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("receiptreqd", 1, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tax4", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tax5", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("remark", mboOrigem.getString("remark"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("loadedcost", mboOrigem.getDouble("loadedcost"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("prorateservice", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("receiptscomplete", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("inspectionrequired", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("proratecost", 0, MboConstants.NOACCESSCHECK);
                //mboDestino.setValue("polineid", "sequencial", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("linecost2", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("siteid", "CGGPL", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("orgid", "MS", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("isdistributed", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("enteredastask", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("tositeid", "CGGPL", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("langcode", "PT", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("conversion", 1, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("hasld", 0, MboConstants.NOACCESSCHECK);
                mboDestino.setValue("mktplcitem", 0, MboConstants.NOACCESSCHECK);
                //mboDestino.setValue("rowstamp", 123456789", MboConstants.NOACCESSCHECK);
                mboDestino.setValue("prlineid", mboOrigem.getDouble("prlineid"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("id2prnum", mboOrigem.getString("prnum"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("id2prlinenum", mboOrigem.getDouble("prlinenum"), MboConstants.NOACCESSCHECK);
                mboDestino.setValue("id2itemnum", mboOrigem.getString("id2itemnum"), MboConstants.NOACCESSCHECK);
                System.out.println("----- antes do SAVE()");
                //po.getMboSet("POLINE").save();
                System.out.println("----- após o SAVE()");
                
            }
        }
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        System.out.println("-------- PRLineBean FIM");

        return 2;

    }
}
