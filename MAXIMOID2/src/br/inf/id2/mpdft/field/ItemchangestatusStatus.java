package br.inf.id2.mpdft.field;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ItemchangestatusStatus extends SynonymDomain {

    public ItemchangestatusStatus(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
        setDomainId(((StatefulMboRemote) mbv.getMbo().getOwner()).getStatusListName());
        System.out.println("--- ItemchangestatusStatus 1702");
        System.out.println("--- ItemchangestatusStatus getStatusListName() = " + ((StatefulMboRemote) mbv.getMbo().getOwner()).getStatusListName());
    }

    public String getSiteOrgWhere() {
        return "";
    }

    public MboSetRemote getList()
            throws MXException, RemoteException {
        System.out.println("--- getList()");
        StatefulMbo statefulOwner = (StatefulMbo) getMboValue().getMbo().getOwner();
        MboSetRemote statusList = null;
        if (statefulOwner.isZombie()) {
            System.out.println("--- zombie");
            statusList = statefulOwner.getStatusList();
        } else {
            System.out.println("--- !zombie");
//            statusList = statefulOwner.getValidStatusList();
            statusList = statefulOwner.getStatusList();
        }
        int numStatus;

//        System.out.println("--- a");
//        MboRemote mboObsoleto = statusList.add();
//
//        mboObsoleto.setValue("VALUEID", "ITEMSTATUS|OBSOLETA");
//        mboObsoleto.setValue("synonymdomainid", 3041);
//
//        System.out.println("--- b");

        for (numStatus = 0; statusList.getMbo(numStatus) != null; numStatus++);
        System.out.println("--- numStatus " + numStatus);


        for (int i = numStatus - 1; i >= 0; i--) {
            System.out.println("--- i " + i);
            MboRemote statum = statusList.getMbo(i);
            try {
                System.out.println("--- value " + statum.getString("value"));
                if (!statum.getString("value").equalsIgnoreCase("OBSOLETA")) {
                    System.out.println("--- canChange");
                    statefulOwner.canChangeStatus(statum.getString("value"));
                }

            } catch (MXException me) {
                System.out.println("--- me " + me.getMessage());
                statusList.remove(statum);
            }
        }

        return statusList;
    }
}
