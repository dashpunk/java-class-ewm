package br.inf.id2.mintur.field;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class MttbredreaValtran extends MboValueAdapter {

    public MttbredreaValtran(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.validate();
        double valor = getMboValue().getDouble();

        if (valor <= 0) {
            throw new MXApplicationException("id2message", "valorNegativo");
        }

        //existirá mais regras apartir daqui, por isso a classe não é common
        MboSet asset;
        asset = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ASSET", getMboValue().getMbo().getUserInfo());

        asset.setWhere("ASSETNUM= '" + getMboValue("ASSETNUM").getString() + "'");
        asset.reset();

    }
}
