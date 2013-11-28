// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FldPurItemNum.java

package psdi.app.common.purchasing;

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

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingLineMboRemote, PurchasingMbo, PurchasingLineMbo

public class FldPurItemNum extends FldItemnum
{

    public FldPurItemNum(MboValue mbv)
        throws MXException, RemoteException
    {
        super(mbv);
        i = 0;
        exceptionAlreadyThrown = false;
        prevValue = new String();
        MboRemote mboRemote = getMboValue().getMbo();
        if(mboRemote instanceof POLineRemote)
            setKeyMap("inventory", new String[] {
                "itemnum", "tositeid", "storeloc"
            }, new String[] {
                "itemnum", "siteid", "location"
            });
        else
            setKeyMap("inventory", new String[] {
                "itemnum", "siteid", "storeloc"
            }, new String[] {
                "itemnum", "siteid", "location"
            });
    }

    public void validate()
        throws MXException, RemoteException
    {
        if(getMboValue().isNull())
            return;
        MboRemote popr = getMboValue().getMbo();
        String maxLineType = getTranslator().toInternalString("LINETYPE", popr.getString("linetype"));
        MboSetRemote itemSetRemote = popr.getMboSet("ITEM");
        Object params[] = {
            getMboValue().getString()
        };
        if(maxLineType.equalsIgnoreCase("SPORDER"))
            if(!itemSetRemote.isEmpty())
                throw new MXApplicationException("po", "itemused", params);
            else
                return;
        super.validate();
        SqlFormat sqlf = null;
        boolean fromVendorItem = false;
        if(popr instanceof PRLineRemote)
            fromVendorItem = ((PRLine)popr).getVendorItem();
        if(popr instanceof POLineRemote)
            fromVendorItem = ((POLine)popr).getVendorItem();
        if(fromVendorItem)
        {
            sqlf = new SqlFormat(popr, "itemnum = :itemnum and itemsetid = :itemsetid");
            itemSetRemote = ((Mbo)popr).getMboSet("$itemtype", "ITEM", sqlf.format());
            MboRemote itemRemote = itemSetRemote.getMbo(0);
            String maxItemType = getTranslator().toInternalString("ITEMTYPE", itemRemote.getString("itemtype"));
            if(!maxLineType.equalsIgnoreCase(maxItemType))
            {
                String itemNum = popr.getString("itemnum");
                popr.setValue("linetype", getTranslator().toExternalDefaultValue("LINETYPE", maxItemType, popr), 2L);
                popr.setValue("itemnum", itemNum, 3L);
            }
        } else
        {
            sqlf = new SqlFormat(popr, "itemnum = :itemnum and itemsetid = :itemsetid and itemtype in (select value from synonymdomain where domainid='ITEMTYPE' and maxvalue=:1)");
            sqlf.setObject(1, "ITEM", "itemtype", maxLineType);
            itemSetRemote = ((Mbo)popr).getMboSet("$itemtype", "ITEM", sqlf.format());
        }
        if(itemSetRemote.isEmpty())
            throw new MXApplicationException("po", "itemtype", params);
        if(popr instanceof RFQLineRemote)
            return;
        if(popr.getString("contractrefnum").equals(""))
            return;
        MboRemote contractRemote = popr.getMboSet("CONTRACTREF").getMbo(0);
        if(((PurchasingLineMboRemote)popr).isAddLinesOnUse(contractRemote))
            return;
        MboRemote owner = popr.getOwner();
        MboRemote contractAuth = ((PurchasingMbo)owner).getContractAuth(contractRemote);
        if(contractAuth == null)
        {
            Object param[] = {
                popr.getString("contractrefnum")
            };
            throw new MXApplicationException("po", "contractisnotauthorized", param);
        }
        String status = getTranslator().toExternalList("CONTRACTSTATUS", "APPR", popr);
        sqlf = new SqlFormat(popr, "contractnum=:contractrefnum and revisionnum=:contractrefrev and linetype=:1 and itemnum =:2 and itemsetid=:3 and orgid=:4 and linestatus in (" + status + ")");
        sqlf.setObject(1, "CONTRACTLINE", "linetype", popr.getString("linetype"));
        sqlf.setObject(2, "CONTRACTLINE", "itemnum", popr.getString("itemnum"));
        sqlf.setObject(3, "CONTRACTLINE", "itemsetid", popr.getString("itemsetid"));
        sqlf.setObject(4, "CONTRACTLINE", "orgid", contractAuth.getString("orgid"));
        MboSetRemote contractLineSet = ((Mbo)popr).getMboSet("$contractline", "CONTRACTLINE", sqlf.format());
        if(contractLineSet.isEmpty())
            throw new MXApplicationException("po", "cannotaddmorelines");
        else
            return;
    }

    public void action()
        throws MXException, RemoteException
    {
        setValueForRelatedFields();
        MboRemote popr = getMboValue().getMbo();
        MboRemote owner = popr.getOwner();
        if(popr.getString("itemnum").equals(""))
            return;
        String maxLineType = getTranslator().toInternalString("LINETYPE", popr.getString("linetype"));
        if(popr.toBeAdded() && maxLineType.equalsIgnoreCase("SPORDER"))
            return;
        getMboValue("orderunit").setFlag(7L, !getMboValue().isNull());
        MboRemote invRemote = null;
        MboRemote item = getMboSet().getMbo(0);
        popr.setValue("commoditygroup", item.getString("commoditygroup"), 11L);
        popr.setValue("commodity", item.getString("commodity"), 11L);
        popr.setValue("prorateservice", item.getString("prorate"), 11L);
        if(owner.getBoolean("inspectionrequired"))
            popr.setValue("inspectionrequired", true, 11L);
        else
        if(item.getBoolean("inspectionrequired"))
        {
            popr.setValue("inspectionrequired", true, 11L);
            popr.setFieldFlag("inspectionrequired", 7L, true);
        } else
        {
            popr.setValue("inspectionrequired", false, 11L);
        }
        String storeLoc = popr.getString("storeloc");
        if(owner.getBoolean("internal"))
        {
            String poprStoreLoc = owner.getString("storeloc");
            String poprFromSite = owner.getString("storelocsiteid");
            String poLineToSite = popr.getString("siteid");
            if(owner.isBasedOn("PO"))
                poLineToSite = popr.getString("tositeid");
            if(poprFromSite.equalsIgnoreCase(poLineToSite))
            {
                popr.setValue("inspectionrequired", false, 11L);
                popr.setFieldFlag("inspectionrequired", 7L, true);
            } else
            {
                popr.setFieldFlag("inspectionrequired", 7L, false);
            }
            if(poprStoreLoc.equals(""))
                throw new MXApplicationException("po", "requiredstoreloc");
            invRemote = getInventory(poprStoreLoc, poprFromSite, true);
            if((owner instanceof PORemote) && invRemote == null)
            {
                SqlFormat sqlf = new SqlFormat(popr, "location = :1 and siteid = :2");
                sqlf.setObject(1, "LOCATIONS", "location", poprStoreLoc);
                sqlf.setObject(2, "LOCATIONS", "siteid", poprFromSite);
                MboSetRemote locationSet = getMboValue().getMbo().getMboSet("$locations", "locations", sqlf.format());
                MboRemote location = locationSet.getMbo(0);
                String ownersysid2 = location.getString("ownersysid");
                IntegrationServiceRemote intserv = (IntegrationServiceRemote)((AppService)getMboValue().getMbo().getMboServer()).getMXServer().lookup("INTEGRATION");
                boolean useIntegration = intserv.useIntegrationRules(owner.getString("ownersysid"), ownersysid2, "POINV", popr.getUserInfo());
                if(useIntegration)
                {
                    popr.setValue("description", item.getString("description"), 2L);
                    popr.setValue("description_longdescription", item.getString("description_longdescription"), 11L);
                    popr.setValue("inspectionrequired", item.getBoolean("inspectionrequired"), 2L);
                    ((PurchasingLineMboRemote)popr).getTaxCodes();
                    return;
                } else
                {
                    throw new MXApplicationException("po", "ItemStorelocCombinationNotFound");
                }
            }
            ((PurchasingLineMboRemote)popr).setOrderUnit(invRemote, "ISSUEUNIT");
            double unitCost = ((InventoryRemote)invRemote).getDefaultIssueCost(popr.getString("conditioncode"));
            popr.setValue("unitcost", unitCost, 2L);
        } else
        {
            if(storeLoc != null && !storeLoc.equals(""))
            {
                String siteID = null;
                if(popr instanceof POLineRemote)
                    siteID = popr.getString("tositeid");
                else
                    siteID = popr.getString("siteid");
                invRemote = getInventory(storeLoc, siteID, false);
            }
            boolean fromVendorItem = false;
            if(popr instanceof PRLineRemote)
                fromVendorItem = ((PRLine)popr).getVendorItem();
            if(popr instanceof POLineRemote)
                fromVendorItem = ((POLine)popr).getVendorItem();
            if(!fromVendorItem && !MXServer.getBulletinBoard().isPosted("pr.SKIPDFTPRICE", popr.getUserInfo()) && !MXServer.getBulletinBoard().isPosted("po.FROMCONTRACTITEMS", popr.getUserInfo()))
                ((PurchasingLineMbo)popr).setDefaultOrderPrice();
            ((PurchasingLineMbo)popr).setOrderUnit(invRemote, "ORDERUNIT");
        }
        getMboValue("orderunit").setFlag(7L, owner.getBoolean("internal"));
        getGLDebitAcct();
        popr.setValue("description", item.getString("description"), 2L);
        if(popr.getBoolean("issue"))
            popr.setFieldFlag("storeloc", 7L, true);
        else
            popr.setFieldFlag("storeloc", 7L, false);
        popr.setValue("description_longdescription", item.getString("description_longdescription"), 11L);
        popr.setFieldFlag("conditioncode", 7L, !((ItemRemote)item).isConditionEnabled());
        ((PurchasingLineMboRemote)popr).getTaxCodes();
        ((PurchasingLineMboRemote)popr).setPriceQtyFields();
        prevValue = "";
        if(storeLoc == null || storeLoc.equals(""))
            return;
        if(invRemote != null)
            popr.setValue("category", invRemote.getString("category"), 2L);
    }

    private MboRemote getInventory(String storeLoc, String siteID, boolean exceptionFlag)
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        String itemnum = getMboValue().getString();
        String itemsetid = popr.getString("itemsetid");
        SqlFormat sqf = new SqlFormat(popr, "itemnum = :1 and itemsetid = :2 and location = :3 and siteid = :4");
        sqf.setObject(1, "inventory", "itemnum", itemnum);
        sqf.setObject(2, "inventory", "itemsetid", itemsetid);
        sqf.setObject(3, "inventory", "location", storeLoc);
        sqf.setObject(4, "inventory", "siteid", siteID);
        MboSetRemote inventorySet = ((Mbo)popr).getMboSet("$inventory", "INVENTORY", sqf.format());
        if(inventorySet.isEmpty() && exceptionFlag)
        {
            Object params[] = {
                itemnum, storeLoc
            };
            throw new MXApplicationException("po", "itemNotInStore", params);
        } else
        {
            return inventorySet.getMbo(0);
        }
    }

    private void setValueForRelatedFields()
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        popr.setValue("inspectionrequired", false, 2L);
        popr.setFieldFlag("inspectionrequired", 7L, false);
        MboRemote owner = popr.getOwner();
        if(((owner instanceof PRRemote) || (owner instanceof PORemote)) && owner.getBoolean("inspectionrequired"))
        {
            popr.setValue("inspectionrequired", true, 2L);
            popr.setFieldFlag("inspectionrequired", 7L, true);
        }
        popr.setValue("unitcost", 0.0D, 11L);
        popr.setValue("linecost", 0.0D, 11L);
        popr.setValue("loadedcost", 0.0D, 11L);
        popr.setValue("tax1", 0.0D, 2L);
        popr.setValue("tax2", 0.0D, 2L);
        popr.setValue("tax3", 0.0D, 2L);
        popr.setValue("tax4", 0.0D, 2L);
        popr.setValue("tax5", 0.0D, 2L);
        popr.setValueNull("description", 2L);
        popr.setValueNull("category", 2L);
        popr.setValueNull("modelnum", 2L);
        popr.setValueNull("manufacturer", 2L);
        popr.setValueNull("commoditygroup", 2L);
        popr.setValueNull("commodity", 2L);
        popr.setValueNull("catalogcode", 2L);
        popr.setValueNull("orderunit", 2L);
        if(owner.getString("contractrefnum").equals(""))
        {
            popr.setValueNull("contractrefnum", 2L);
        } else
        {
            popr.setValue("contractrefnum", owner.getString("contractrefnum"), 11L);
            popr.setValue("contractrefrev", owner.getString("contractrefrev"), 11L);
            popr.setValue("contractrefid", owner.getString("contractrefid"), 11L);
        }
    }

    public void getGLDebitAcct()
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        if(!popr.isNull("storeloc"))
        {
            getGLWithItemStore();
            return;
        }
        if(popr.isNull("wonum") && popr.isNull("assetnum") && popr.isNull("location"))
        {
            if(popr.isNull("gldebitacct"))
            {
                return;
            } else
            {
                mergeGLWithItem();
                return;
            }
        } else
        {
            TransactionGLMerger glm = new TransactionGLMerger((Mbo)popr);
            glm.setWonum(popr.getString("wonum"));
            glm.setAssetnum(popr.getString("assetnum"));
            glm.setLocation(popr.getString("location"));
            glm.setChargeToStore(popr.getBoolean("chargestore"));
            glm.setItem(popr.getString("itemnum"));
            glm.setItemSetID(popr.getString("itemsetid"));
            popr.setValue("gldebitacct", glm.getMergedDebitGLAccount(), 2L);
            return;
        }
    }

    private void getGLWithItemStore()
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        MboRemote item = getMboSet().getMbo(0);
        String siteID = null;
        if(popr instanceof POLineRemote)
            siteID = popr.getString("tositeid");
        else
            siteID = popr.getString("siteid");
        Inventory inventory = (Inventory)getInventory(popr.getString("storeloc"), siteID, false);
        if(inventory == null)
        {
            SqlFormat sqlf = new SqlFormat(popr, "location = :1 and siteid = :2");
            sqlf.setObject(1, "LOCATIONS", "location", popr.getString("storeloc"));
            sqlf.setObject(2, "LOCATIONS", "siteid", siteID);
            MboRemote locMbo = getMboValue().getMbo().getMboSet("$getlocation", "locations", sqlf.format()).getMbo(0);
            if(locMbo == null)
                return;
            String maxLineType = getTranslator().toInternalString("LINETYPE", popr.getString("linetype"));
            if(maxLineType.equalsIgnoreCase("ITEM"))
                popr.setValue("gldebitacct", locMbo.getString("controlacc"), 2L);
            else
            if(maxLineType.equalsIgnoreCase("TOOL"))
                popr.setValue("gldebitacct", locMbo.getString("toolcontrolacc"), 2L);
            return;
        }
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

    private void mergeGLWithItem()
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        TransactionGLMerger glm = new TransactionGLMerger((Mbo)popr);
        glm.setItem(popr.getString("itemnum"));
        glm.setItemSetID(popr.getString("itemsetid"));
        String topGl = glm.getItemResourceAccount();
        String bottomGl = popr.getString("gldebitacct");
        String glAccount = glm.mergedGL(topGl, bottomGl);
        popr.setValue("gldebitacct", glAccount, 2L);
    }

    public MboSetRemote getList()
        throws MXException, RemoteException
    {
        MboSetRemote valueListSet = super.getList();
        MboRemote mboRemote = getMboValue().getMbo();
        String itemSetID = mboRemote.getString("itemsetid");
        SqlFormat sqlf = null;
        if(mboRemote.isZombie())
            sqlf = new SqlFormat(mboRemote, "itemsetid=:2 and commoditygroup=:5 and commodity=:6");
        else
            sqlf = new SqlFormat(mboRemote, "itemtype in (select value from synonymdomain where domainid='ITEMTYPE' and maxvalue=:1) and itemsetid=:2 and commoditygroup=:5 and commodity=:6");
        MboRemote owner = mboRemote.getOwner();
        if(!(mboRemote instanceof RFQLineRemote) && !mboRemote.isZombie() && owner != null && owner.getBoolean("internal"))
        {
            sqlf = new SqlFormat(mboRemote, " itemnum in (select itemnum from inventory where itemsetid = :2 and location = :3 and siteid = :4) and itemtype in (select value from synonymdomain where domainid='ITEMTYPE' and maxvalue=:1) and itemsetid=:2 and commoditygroup=:5 and commodity=:6");
            sqlf.setObject(3, "PO", "storeloc", owner.getString("storeloc"));
            sqlf.setObject(4, "PO", "storelocsiteid", owner.getString("storelocsiteid"));
            sqlf.setObject(5, "PO", "commoditygroup", owner.getString("commoditygroup"));
            sqlf.setObject(6, "PO", "commodity", owner.getString("commodity"));
        }
        if(itemSetID.equals(""))
            itemSetID = ((Mbo)mboRemote).getProfile().getInsertItemSet();
        String maxLineType = getTranslator().toInternalString("LINETYPE", mboRemote.getString("linetype"));
        if(!mboRemote.isZombie())
            sqlf.setObject(1, "ITEM", "itemtype", maxLineType);
        sqlf.setObject(2, "ITEM", "itemsetid", itemSetID);
        String relationWhere = valueListSet.getRelationship();
        if(relationWhere.equals(""))
            valueListSet.setRelationship(sqlf.format());
        else
            valueListSet.setRelationship(relationWhere + " and " + sqlf.format());
        if(mboRemote instanceof RFQLineRemote)
            return valueListSet;
        if(!mboRemote.getString("contractrefnum").equals(""))
        {
            MboRemote contractRemote = mboRemote.getMboSet("CONTRACTREF").getMbo(0);
            if(((PurchasingLineMboRemote)mboRemote).isAddLinesOnUse(contractRemote))
                return valueListSet;
            String status = getTranslator().toExternalList("CONTRACTSTATUS", "APPR", mboRemote);
            sqlf = new SqlFormat(mboRemote, " itemnum in (select itemnum from contractline where contractnum=:contractrefnum and linetype = :1 and linestatus in (" + status + "))");
            sqlf.setObject(1, "CONTRACTLINE", "linetype", mboRemote.getString("linetype"));
            valueListSet.setRelationship(sqlf.format());
        }
        return valueListSet;
    }

    public String[] getAppLink()
        throws MXException, RemoteException
    {
        MboRemote itemnum = getMboValue().getMbo();
        String maxLineType = new String();
        String lineType = itemnum.getString("linetype");
        if(!lineType.equals(""))
        {
            maxLineType = getTranslator().toInternalString("LINETYPE", lineType);
            if(maxLineType.equalsIgnoreCase("STDSERVICE"))
                return (new String[] {
                    "srvitem"
                });
            if(maxLineType.equalsIgnoreCase("ITEM"))
                return (new String[] {
                    "inventor", "item"
                });
            if(maxLineType.equalsIgnoreCase("TOOL"))
                return (new String[] {
                    "tool", "toolinv"
                });
            if(maxLineType.equalsIgnoreCase("SERVICE"))
                return (new String[] {
                    ""
                });
            if(maxLineType.equalsIgnoreCase("SPORDER"))
                return (new String[] {
                    ""
                });
            if(maxLineType.equalsIgnoreCase("MATERIAL"))
                return (new String[] {
                    ""
                });
            else
                return super.getAppLink();
        } else
        {
            return (new String[] {
                ""
            });
        }
    }

    public MboSetRemote smartFill(String value, boolean exact)
        throws MXException, RemoteException
    {
        MboRemote popr = getMboValue().getMbo();
        String maxLineType = getTranslator().toInternalString("LINETYPE", popr.getString("linetype"));
        if(maxLineType.equalsIgnoreCase("SPORDER"))
            return null;
        else
            return super.smartFill(value, exact);
    }

    private int i;
    private boolean exceptionAlreadyThrown;
    public String prevValue;
}
