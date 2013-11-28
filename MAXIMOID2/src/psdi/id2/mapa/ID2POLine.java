/*
 * ID2POLine.java
 *
 * Created on 24 de Novembro de 2010, 17:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author dyogo.dantas
 */
package psdi.id2.mapa;

import psdi.tloam.app.po.PO;
import psdi.tloam.app.po.PORemote;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.BidiUtils;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class ID2POLine extends psdi.tloam.app.po.POLine
        implements ID2POLineRemote {

    //private MboRemote owner;

    @Override
    public void init()
            throws MXException {
        super.init();
    }

    public ID2POLine(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    /*
    @Override
    public void appValidate() throws MXException, RemoteException {
        //super.appValidate();

        super.appValidate();
        if (getVendorItem()) {
            setDefaultOrderPrice();
            setVendorItem(false);
        }
        String params[] = {
            getString("polinenum")
        };
        if (isNull("itemnum") && isNull("description")) {
            throw new MXApplicationException("po", "ItemOrDescIsRequired", params);
        }
        String maxLineType = getInternalLineType();
        if ((maxLineType.equalsIgnoreCase("ITEM") || maxLineType.equalsIgnoreCase("STDSERVICE") || maxLineType.equalsIgnoreCase("TOOL") || maxLineType.equalsIgnoreCase("SPORDER")) && isNull("itemnum")) {
            throw new MXApplicationException("po", "NoItemForLineType");
        }
        if (!isServiceType() && getDouble("orderqty") == 0.0D) //throw new MXApplicationException("po", "NoQtyForLineType");
        {
            owner = getOwner();
        }
        PO relatedPO = null;
        if (owner instanceof PORemote) {
            relatedPO = (PO) owner;
        }
        if (relatedPO == null) {
            relatedPO = (PO) getMboSet("PO").getMbo(0);
        }
        if ((relatedPO instanceof PORemote) && relatedPO.getBoolean("internal") && getString("storeloc").equals("")) {
            throw new MXApplicationException("po", "StoreLocIsNull", params);
        }
        if (getBoolean("chargestore") && getString("assetnum").equals("")) {
            throw new MXApplicationException("po", "AssetNumNull", params);
        }
        checkPOCostValidity();
        if (!maxLineType.equalsIgnoreCase("SPORDER") && getString("storeloc").equals("") && !getString("itemnum").equals("")) {
            MboSetRemote itemOrgSet = getMboSet("ITEMORGINFOSTATUS");
            if (itemOrgSet.isEmpty()) {
                String param[] = {
                    getString("itemnum"), "Item/Organization"
                };
                if (BidiUtils.isBidiEnabled()) {
                    param[0] = BidiUtils.buildAndPush(getName(), "itemnum", param[0], getUserInfo().getLangCode());
                }
                throw new MXApplicationException("item", "ActionNotAllowedInvalidStatus", param);
            }
        }


    }*/
}
