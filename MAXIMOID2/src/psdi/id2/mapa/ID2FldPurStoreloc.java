// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2FldPurStoreloc.java

package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.inventory.Inventory;
import psdi.app.inventory.InventorySetRemote;
import psdi.app.location.FldCrossSiteLocation;
import psdi.app.po.POLineRemote;
import psdi.app.pr.PRLineRemote;
import psdi.app.rfq.RFQLineRemote;
import psdi.mbo.*;
import psdi.security.ProfileRemote;
import psdi.security.UserInfo;
import psdi.server.BulletinBoardServiceRemote;
import psdi.server.MXServer;
import psdi.util.*;
import psdi.app.common.purchasing.*;

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingLineMboRemote, PurchasingLineMbo

public class ID2FldPurStoreloc extends FldCrossSiteLocation
{

    public ID2FldPurStoreloc(MboValue mbv)
        throws MXException, RemoteException
    {
        super(mbv, "CROSSSITESTOREROOM");
        poprlRemote = null;
        rfqlRemote = null;
        sqlWhere = getListCriteria();
        MboRemote mboRemote = getMboValue().getMbo();
        if(mboRemote instanceof POLineRemote)
            setKeyMap("locations", new String[] {
                "tositeid", "storeloc"
            }, new String[] {
                "siteid", "location"
            });
        else
            setKeyMap("locations", new String[] {
                "storeloc"
            }, new String[] {
                "location"
            });
    }

    public void setValueFromLookup(MboRemote sourceMbo)
        throws MXException, RemoteException
    {
        MboRemote mboRemote = getMboValue().getMbo();
        if(mboRemote instanceof POLineRemote)
            setLookupKeyMapInOrder(new String[] {
                "tositeid", "storeloc"
            }, new String[] {
                "siteid", "location"
            });
        super.setValueFromLookup(sourceMbo);
    }

    public void validate()
        throws MXException, RemoteException
    {
        MboRemote lineMboRemote = getMboValue().getMbo();
        if(MXServer.getBulletinBoard().isPosted("pr.SKIPSTORELOCVALIDATION", lineMboRemote.getUserInfo()))
            return;
        String maxLineType = getTranslator().toInternalString("LINETYPE", lineMboRemote.getString("linetype"));
        if(maxLineType.equalsIgnoreCase("SERVICE") || maxLineType.equalsIgnoreCase("STDSERVICE") || maxLineType.equalsIgnoreCase("MATERIAL"))
            throw new MXApplicationException("po", "storeroomnotallowed");
        if(!lineMboRemote.isNull("itemnum"))
        {
            InventorySetRemote invSet = (InventorySetRemote)lineMboRemote.getMboSet("INVENTORY");
            if(!invSet.isEmpty())
            {
                String maxStatus = getTranslator().toInternalString("ITEMSTATUS", invSet.getMbo(0).getString("status"));
                if(maxStatus.equals("OBSOLETE") || maxStatus.equals("PENDING"))
                {
                    String param[] = {
                        getMboValue().getMbo().getString("itemnum"), "Inventory"
                    };
                    if(BidiUtils.isBidiEnabled())
                        param[0] = BidiUtils.buildAndPush(getMboValue().getMbo().getName(), "itemnum", param[0], getMboValue().getMbo().getUserInfo().getLangCode());
                    throw new MXApplicationException("item", "ActionNotAllowedInvalidStatus", param);
                }
            }
        }
    }

    public void action()
        throws MXException, RemoteException
    {
        MboRemote lineMboRemote = getMboValue().getMbo();
        if(MXServer.getBulletinBoard().isPosted("pr.SKIPSTORELOCVALIDATION", lineMboRemote.getUserInfo()))
            return;
        if(getMboValue().getMbo() instanceof RFQLineRemote)
        {
            rfqlRemote = (RFQLineRemote)getMboValue().getMbo();
            lineMboRemote = rfqlRemote;
        } else
        {
            poprlRemote = (PurchasingLineMboRemote)getMboValue().getMbo();
            lineMboRemote = poprlRemote;
            lineMboRemote.setFieldFlag("ORDERUNIT", 128L, !getMboValue().isNull());
        }
        MboRemote owner = lineMboRemote.getOwner();
        boolean internal = !(lineMboRemote instanceof RFQLineRemote) && owner.getBoolean("internal");
        if(getMboValue().isNull())
        {
            lineMboRemote.setValueNull("category", 2L);
            lineMboRemote.setValueNull("conversion", 11L);
            if(!(lineMboRemote instanceof RFQLineRemote) && !lineMboRemote.getOwner().getBoolean("internal"))
                lineMboRemote.setFieldFlag("issue", 7L, false);
            if(!internal)
                setOrderUnit(null, "ORDERUNIT");
            return;
        }
        String storeLoc = getMboValue().getString();
        lineMboRemote.setValue("issue", false, 11L);
        lineMboRemote.setFieldFlag("issue", 7L, true);
        SqlFormat sqlLoc = new SqlFormat(lineMboRemote, "location=:1 and siteid=:2");
        sqlLoc.setObject(1, "locations", "location", storeLoc);
        if(lineMboRemote instanceof POLineRemote)
            sqlLoc.setObject(2, "locations", "siteid", lineMboRemote.getString("tositeid"));
        else
            sqlLoc.setObject(2, "locations", "siteid", lineMboRemote.getString("siteid"));
        MboRemote locMbo = ((Mbo)lineMboRemote).getMboSet("$getLocation", "LOCATIONS", sqlLoc.format()).getMbo(0);
        Object params[] = {
            getMboValue().getString(), ""
        };
        if(locMbo == null)
            throw new MXApplicationException("locations", "NotValidStoreLoc", params);
        if((lineMboRemote instanceof PRLineRemote) && !lineMboRemote.getString("siteid").equalsIgnoreCase(locMbo.getString("siteid")))
            throw new MXApplicationException("locations", "NotValidStoreLoc", params);
        String maxLineType = getTranslator().toInternalString("LINETYPE", lineMboRemote.getString("linetype"));
        if(maxLineType.equalsIgnoreCase("ITEM") || maxLineType.equalsIgnoreCase("SPORDER"))
            lineMboRemote.setValue("gldebitacct", locMbo.getString("controlacc"), 2L);
        else
        if(maxLineType.equalsIgnoreCase("TOOL"))
            lineMboRemote.setValue("gldebitacct", locMbo.getString("toolcontrolacc"), 2L);
        String siteID = null;
        if(lineMboRemote instanceof POLineRemote)
        {
            siteID = lineMboRemote.getString("tositeid");
            if(!locMbo.getString("shiptoaddresscode").equals(""))
                lineMboRemote.setValue("shipto", locMbo.getString("shiptoaddresscode"), 2L);
            if(!locMbo.getString("shiptolaborcode").equals(""))
                lineMboRemote.setValue("shiptoattn", locMbo.getString("shiptolaborcode"), 2L);
        } else
        {
            siteID = lineMboRemote.getString("siteid");
        }
        if(internal && owner.getString("storeloc").equals(storeLoc) && owner.getString("storelocsiteid").equals(siteID))
            throw new MXApplicationException("po", "cannotbesamestoreloc");
        String itemnum = lineMboRemote.getString("itemnum");
        if(itemnum == null || itemnum.equals(""))
            return;
        MboRemote inventoryRec = getInventory(storeLoc, siteID);
        if(inventoryRec == null)
        {
            if(!internal)
            {
                setOrderUnit(null, "ORDERUNIT");
                return;
            }
        } else
        {
            getGLWithItemStore();
            lineMboRemote.setValue("category", inventoryRec.getString("category"), 2L);
            if(!internal)
            {
                setOrderUnit(inventoryRec, "ORDERUNIT");
                if(lineMboRemote instanceof RFQLineRemote)
                    return;
                if(getMboValue("conversion").isNull())
                    ((PurchasingLineMbo)lineMboRemote).setConversionFactor();
                if(lineMboRemote.getDouble("unitcost") == 0.0D)
                {
                    MboRemote invCost = ((Inventory)inventoryRec).getInvCostRecord(lineMboRemote.getString("conditioncode"));
                    ((PurchasingLineMbo)lineMboRemote).setUnitCost(true, invCost.getDouble("lastcost"));
                }
                return;
            }
        }
        MboRemote headerInvRemote = getInventory(owner.getString("storeloc"), owner.getString("storelocsiteid"));
        setOrderUnit(headerInvRemote, "ISSUEUNIT");
        if(getMboValue("conversion").isNull())
            ((PurchasingLineMbo)lineMboRemote).setConversionFactor();
        SqlFormat sql = new SqlFormat(lineMboRemote, "itemnum = :1 and itemsetid = :2");
        sql.setObject(1, "item", "itemnum", itemnum);
        sql.setObject(2, "item", "itemsetid", lineMboRemote.getString("itemsetid"));
        MboRemote itemRemote = ((Mbo)lineMboRemote).getMboSet("$getitem", "ITEM", sql.format()).getMbo(0);
        if(itemRemote.getBoolean("rotating") && inventoryRec != null && !inventoryRec.isNull("issueunit") && !lineMboRemote.isNull("orderunit") && !lineMboRemote.getString("orderunit").equals(inventoryRec.getString("issueunit")))
            throw new MXApplicationException("po", "internalrotating");
        else
            return;
    }

    private MboRemote getInventory(String storeLoc, String siteID)
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        SqlFormat sqf = new SqlFormat(popr, "itemnum = :1 and itemsetid = :2 and location = :3 and siteid = :4");
        sqf.setObject(1, "inventory", "itemnum", popr.getString("itemnum"));
        sqf.setObject(2, "inventory", "itemsetid", popr.getString("itemsetid"));
        sqf.setObject(3, "inventory", "location", storeLoc);
        sqf.setObject(4, "inventory", "siteid", siteID);
        MboSetRemote inventorySet = ((Mbo)popr).getMboSet("$inventory", "INVENTORY", sqf.format());
        return inventorySet.getMbo(0);
    }

    private void setOrderUnit(MboRemote inventory, String orderOrIssueUnitAttr)
        throws MXException, RemoteException
    {
        if(rfqlRemote != null)
            rfqlRemote.setOrderUnit(inventory, orderOrIssueUnitAttr);
        else
            poprlRemote.setOrderUnit(inventory, orderOrIssueUnitAttr);
    }

    private void getGLWithItemStore()
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        MboRemote item = popr.getMboSet("ITEM").getMbo(0);
        String siteID = null;
        if(popr instanceof POLineRemote)
            siteID = popr.getString("tositeid");
        else
            siteID = popr.getString("siteid");
        Inventory inventory = (Inventory)getInventory(popr.getString("storeloc"), siteID);
        if(item.getBoolean("conditionenabled"))
        {
            if(!popr.getString("conditioncode").equals(""))
            {
                MboRemote invCost = inventory.getInvCostRecord(popr.getString("conditioncode"));
                if(invCost != null)
                {
                    popr.setValue("gldebitacct", invCost.getString("controlacc"), 2L);
                    return;
                }
            }
            popr.setValue("gldebitacct", inventory.getString("controlacc"), 2L);
        } else
        {
            MboRemote invCost = inventory.getInvCostRecord(null);
            if(invCost != null)
                popr.setValue("gldebitacct", invCost.getString("controlacc"), 2L);
        }
    }

    public MboSetRemote getList()
        throws MXException, RemoteException
    {
        MboRemote mboRemote = getMboValue().getMbo();
		MboRemote owner = mboRemote.getOwner();
        if(MXServer.getBulletinBoard().isPosted("pr.SKIPSTORELOCVALIDATION", mboRemote.getUserInfo()))
            return null;
        String siteID = null;
        siteID = ((Mbo)mboRemote).getProfile().getDefaultSite();
        SqlFormat sqlf = new SqlFormat(mboRemote, (new StringBuilder()).append(sqlWhere).append(" and siteid = :1 and location in (select location from id2locationusercust where personid=:2 and id2tipolocal=:3)").toString());
        sqlf.setObject(1, "LOCATIONS", "SITEID", siteID);
        sqlf.setObject(2, "PO", "ID2PESSOADEST", owner.getString("ID2PESSOADEST"));
        sqlf.setObject(3, "PO", "ID2TIPOLOCALDEST", owner.getString("ID2TIPOLOCALDEST"));
        setListCriteria(sqlf.format());
        String sql = "";
        SqlFormat sqf = null;
        if(!mboRemote.isNull("itemnum"))
        {
            String statusList[] = {
                "ACTIVE", "PLANNING", "PENDOBS"
            };
            String validStatus = getTranslator().toExternalList("ITEMSTATUS", statusList);
            sql = "  and ( ( not exists ( select 1 from inventory where itemnum=:1 and location=locations.location and siteid=:2 and itemsetid = :3 ))";
            sql = (new StringBuilder()).append(sql).append(" or location in ( select location from inventory where  itemnum = :1 and location = locations.location and siteid =:2 and itemsetid =:3 and status in (").append(validStatus).append(")) )").toString();
            sqf = new SqlFormat(mboRemote, sql);
            sqf.setObject(1, "ITEM", "ITEMNUM", mboRemote.getString("itemnum"));
            sqf.setObject(2, "SITE", "siteid", mboRemote.getString("siteid"));
            sqf.setObject(3, "ITEM", "ITEMSETID", mboRemote.getString("itemsetid"));
            setListCriteria((new StringBuilder()).append(sqlf.format()).append(sqf.format()).toString());
        }
        return super.getList();
    }

    private PurchasingLineMboRemote poprlRemote;
    private RFQLineRemote rfqlRemote;
    String sqlWhere;
}
