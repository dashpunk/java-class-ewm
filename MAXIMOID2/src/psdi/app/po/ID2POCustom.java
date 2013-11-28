// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package psdi.app.po;

import java.rmi.RemoteException;
import psdi.app.po.PO;
import psdi.id2.mapa.ID2POCustomRemote;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            Mbo, ID2POCustomRemote
public class ID2POCustom extends PO
        implements ID2POCustomRemote {

    public ID2POCustom(MboSet mboset)
            throws MXException, RemoteException {
        super(mboset);
    }

    public void save()
            throws MXException, java.rmi.RemoteException {
        MboSet linhas = (MboSet) getMboSet("POLINE");
        MboSet itens = (MboSet) getMboSet("ITENSGTA");
        Mbo linha;

        linha = (Mbo) linhas.add();
        linha.setValue("LINETYPE", "ITEM", 11L);
        if (itens.getInt("[CAMPO DA QUANTIDADE MACHO]") > 0) {
            itens.setWhere("itemnum = '[Código do item para macho]'");
            itens.reset();
            linha.setValue("ITEMNUM", itens.getString("itemnum"));
        } else {
            itens.setWhere("itemnum = '[c?digo do item para f?mea]'");
            itens.reset();
            linha.setValue("ITEMNUM", itens.getString("itemnum"));
        }
        linha.setValue("TAX1", 0);
        linha.setValue("UNITCOST", 0);
        linha.setValue("ENTEREDASTASK", 0, 11L);
        linha.setValue("CONVERSION", 1, 11L);
        linha.setValue("chargestore", 0, 11L);
        linha.setValue("inspectionrequired", 0, 11L);
        linha.setValue("issue", 1, 2L);
        linha.setValue("orderqty", itens.getInt("[CAMPO DA QUANTIDADE MACHO]"), 11L);
        linha.setValue("proratecost", 0.0D, 11L);
        linha.setValue("prorateservice", 0, 11L);
        linha.setValue("receiptreqd", 0, 11L);
        linha.setValue("receiptscomplete", false, 11L);
        linha.setValueNull("receivedqty", 11L);
        linha.setValue("receivedtotalcost", 0.0D, 11L);
        linha.setValue("receivedunitcost", 0.0D, 11L);
        linha.setValue("rejectedqty", 0.0D, 11L);

        super.save();
        return;
    }
}
