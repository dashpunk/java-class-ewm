// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FldPurItemNum.java
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.common.TransactionGLMerger;
import psdi.app.integration.IntegrationServiceRemote;
import psdi.app.inventory.Inventory;
import psdi.app.inventory.InventoryRemote;
import psdi.app.item.FldItemnum;
import psdi.app.item.ItemRemote;
import psdi.app.po.*;
import psdi.app.pr.*;
import psdi.app.rfq.RFQLineRemote;
import psdi.mbo.*;
import psdi.security.ProfileRemote;
import psdi.server.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.app.common.purchasing.*;

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingLineMboRemote, PurchasingMbo, PurchasingLineMbo
public class ID2FldPurItemNum extends FldPurItemNum {

    public ID2FldPurItemNum(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    public MboSetRemote getList()
            throws MXException, RemoteException {
        MboSetRemote valueListSet = super.getList();
        MboRemote mboRemote = getMboValue().getMbo();
        String itemSetID = mboRemote.getString("itemsetid");
        SqlFormat sqlf = null;
        if (mboRemote.isZombie()) {
            sqlf = new SqlFormat(mboRemote, "itemsetid=:2 and commoditygroup=:5 and commodity=:6");
        } else {
            sqlf = new SqlFormat(mboRemote, "itemtype in (select value from synonymdomain where domainid='ITEMTYPE' and maxvalue=:1) and itemsetid=:2 and commoditygroup=:5 and commodity=:6");
        }
        MboRemote owner = mboRemote.getOwner();
        if (!(mboRemote instanceof RFQLineRemote) && !mboRemote.isZombie() && owner != null && owner.getBoolean("internal")) {
            sqlf = new SqlFormat(mboRemote, " itemnum in (select itemnum from inventory where itemsetid = :2 and location = :3 and siteid = :4) and itemtype in (select value from synonymdomain where domainid='ITEMTYPE' and maxvalue=:1) and itemsetid=:2 and commoditygroup=:5 and commodity=:6");
            sqlf.setObject(3, "PO", "storeloc", owner.getString("storeloc"));
            sqlf.setObject(4, "PO", "storelocsiteid", owner.getString("storelocsiteid"));
            sqlf.setObject(5, "PO", "commoditygroup", owner.getString("commoditygroup"));
            sqlf.setObject(6, "PO", "commodity", owner.getString("commodity"));
        }
        if (itemSetID.equals("")) {
            itemSetID = ((Mbo) mboRemote).getProfile().getInsertItemSet();
        }
        String maxLineType = getTranslator().toInternalString("LINETYPE", mboRemote.getString("linetype"));
        if (!mboRemote.isZombie()) {
            sqlf.setObject(1, "ITEM", "itemtype", maxLineType);
        }
        sqlf.setObject(2, "ITEM", "itemsetid", itemSetID);
        String relationWhere = valueListSet.getRelationship();
        if (relationWhere.equals("")) {
            valueListSet.setRelationship(sqlf.format());
        } else {
            valueListSet.setRelationship(relationWhere + " and " + sqlf.format());
        }
        if (mboRemote instanceof RFQLineRemote) {
            return valueListSet;
        }
        if (!mboRemote.getString("contractrefnum").equals("")) {
            MboRemote contractRemote = mboRemote.getMboSet("CONTRACTREF").getMbo(0);
            if (((PurchasingLineMboRemote) mboRemote).isAddLinesOnUse(contractRemote)) {
                return valueListSet;
            }
            String status = getTranslator().toExternalList("CONTRACTSTATUS", "APPR", mboRemote);
            sqlf = new SqlFormat(mboRemote, " itemnum in (select itemnum from contractline where contractnum=:contractrefnum and linetype = :1 and linestatus in (" + status + "))");
            sqlf.setObject(1, "CONTRACTLINE", "linetype", mboRemote.getString("linetype"));
            valueListSet.setRelationship(sqlf.format());
        }
        return valueListSet;
    }

    public String[] getAppLink()
            throws MXException, RemoteException {
        MboRemote itemnum = getMboValue().getMbo();
        String maxLineType = new String();
        String lineType = itemnum.getString("linetype");
        if (!lineType.equals("")) {
            maxLineType = getTranslator().toInternalString("LINETYPE", lineType);
            if (maxLineType.equalsIgnoreCase("STDSERVICE")) {
                return (new String[]{
                            "srvitem"
                        });
            }
            if (maxLineType.equalsIgnoreCase("ITEM")) {
                return (new String[]{
                            "inventor", "id2ite01"
                        });
            }
            if (maxLineType.equalsIgnoreCase("TOOL")) {
                return (new String[]{
                            "tool", "toolinv"
                        });
            }
            if (maxLineType.equalsIgnoreCase("SERVICE")) {
                return (new String[]{
                            ""
                        });
            }
            if (maxLineType.equalsIgnoreCase("SPORDER")) {
                return (new String[]{
                            ""
                        });
            }
            if (maxLineType.equalsIgnoreCase("MATERIAL")) {
                return (new String[]{
                            ""
                        });
            } else {
                return super.getAppLink();
            }
        } else {
            return (new String[]{
                        ""
                    });
        }
    }
}
