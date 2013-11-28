package br.inf.id2.mapa.field;

import br.inf.id2.common.field.*;
import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MatbcomId2proId2tipdes extends MboValueAdapter {

    public MatbcomId2proId2tipdes(MboValue mbv) {
        super(mbv);
        System.out.println("--- MatbcomId2proId2tipdes");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("--- MatbcomId2proId2tipdes validate");
        MboRemote mbo;
        int registroCorrente = getMboValue().getMbo().getThisMboSet().getCurrentPosition();
        System.out.println("--- MatbcomId2proId2tipdes v1 " + registroCorrente);
        System.out.println("--- MatbcomId2proId2tipdes v2 " + getMboValue().getMbo().getThisMboSet().count());
        for (int i = 0; ((mbo = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
            System.out.println("--- i " + i);
            System.out.println("--- v1 " + getMboValue("ID2PRO").getString());
            System.out.println("--- v1 " + getMboValue("ID2TIPDES").getString());
            System.out.println("--- v2 " + mbo.getString("ID2PRO"));
            System.out.println("--- v2 " + mbo.getString("ID2TIPDES"));
            if (i != registroCorrente && mbo.getString("ID2TIPDES").equalsIgnoreCase(getMboValue("ID2TIPDES").getString()) && mbo.getString("ID2PRO").equalsIgnoreCase(getMboValue("ID2PRO").getString())) {
                throw new MXApplicationException("matbcom", "valorID2PROID2TIPDESDuplicado");
            }

        }
        
        if (getMboValue().getName().equalsIgnoreCase("ID2TIPDES")) {
            System.out.println("--- valor " + getMboValue().getString());
            getMboValue("ID2UF").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("UF"));
            getMboValue("ID2PAIS").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("País"));
            getMboValue("ID2NUMSIF").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("País"));
        }
        if (!getMboValue().getString().equalsIgnoreCase("UF")) {
            getMboValue("ID2UF").setValueNull(MboConstants.NOACCESSCHECK);
        }
        if (!getMboValue().getString().equalsIgnoreCase("País")) {
            getMboValue("ID2PAIS").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2NUMSIF").setValueNull(MboConstants.NOACCESSCHECK);
        }

        super.validate();
    }
    
    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        if (getMboValue().getName().equalsIgnoreCase("ID2TIPDES")) {
            System.out.println("--- valor " + getMboValue().getString());
            getMboValue("ID2UF").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("UF"));
            getMboValue("ID2PAIS").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("País"));
            getMboValue("ID2NUMSIF").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("País"));
            
            if (!getMboValue().getString().equalsIgnoreCase("UF")) {
                getMboValue("ID2UF").setValueNull(MboConstants.NOACCESSCHECK);
            }
            if (!getMboValue().getString().equalsIgnoreCase("País")) {
                getMboValue("ID2PAIS").setValueNull(MboConstants.NOACCESSCHECK);
                getMboValue("ID2NUMSIF").setValueNull(MboConstants.NOACCESSCHECK);
            }
        }
    }
    
}
