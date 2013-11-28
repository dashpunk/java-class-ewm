package br.inf.id2.me.mbo;

import psdi.tamit.app.asset.*;
import java.rmi.RemoteException;

import br.inf.id2.common.util.Uteis;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2AssetSet extends TLOAMAssetSet
        implements TLOAMAssetSetRemote {

    public ID2AssetSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    @Override
    public void save() throws MXException, RemoteException {
        // TODO Auto-generated method stub
        MboRemote mbo = getMbo();
        if (mbo != null) {
            if (mbo.getMboValueData("ASSETTAG").isNull() && mbo.getString("ID2TIPASS").equals("01")) {
                if (mbo.getMboSet("CLASSSTRUCTURE").count() > 0) {
                    if (mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").count() > 0) {
                        String prefixo = mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getMboValueData("CODPREFIXO").isNull() ? "" : mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getString("CODPREFIXO");
                        int faixaAtual = mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getMboValueData("NUFAIXAAT").isNull() ? 1 : mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getInt("NUFAIXAAT") + 1;
                        int faixaInicial = mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getMboValueData("NUFAIXAINI").isNull() ? 1 : mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).getInt("NUFAIXAINI");
                        mbo.setValue("ASSETTAG", prefixo + Uteis.adicionaValorEsquerda(Integer.toString(faixaInicial + faixaAtual), "0", 6));
                        mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").getMbo(0).setValue("NUFAIXAAT", faixaInicial + faixaAtual);
                        mbo.getMboSet("CLASSSTRUCTURE").getMbo(0).getMboSet("MERL01SEQPAT").save();
                    }
                }
            }
        }
        super.save();
    }
}
