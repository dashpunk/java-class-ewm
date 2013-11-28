package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2VwLoc04ID2CodProp extends MboValueAdapter {

    public ID2VwLoc04ID2CodProp(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("--------- validate ()");
        if (getMboValue().getMbo().isNew()) {
            String valor = getMboValue().getMbo().getString("LOCATION");

            getMboValue().getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, false);
            MboSet exploracoes;
            exploracoes = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC04", getMboValue().getMbo().getUserInfo());
            exploracoes.setWhere("id2codprop = '" + getMboValue().getMbo().getString("ID2CODPROP") + "'");
            exploracoes.reset();
            getMboValue().getMbo().setValue("ID2LOCATION", getMboValue().getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
            getMboValue().getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, true);
        }
    }
}
