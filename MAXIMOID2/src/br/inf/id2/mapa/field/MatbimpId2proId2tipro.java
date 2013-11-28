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
public class MatbimpId2proId2tipro extends MboValueAdapter {

    public MatbimpId2proId2tipro(MboValue mbv) {
        super(mbv);
        System.out.println("--- MatbimpId2proId2tipro");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("--- MatbimpId2proId2tipro validate");
        MboRemote mbo;
        int registroCorrente = getMboValue().getMbo().getThisMboSet().getCurrentPosition();
        System.out.println("--- MatbimpId2proId2tipro v1 " + registroCorrente);
        System.out.println("--- MatbimpId2proId2tipro v2 " + getMboValue().getMbo().getThisMboSet().count());
        for (int i = 0; ((mbo = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
            System.out.println("--- i " + i);
            System.out.println("--- v1 " + getMboValue("ID2PRO").getString());
            System.out.println("--- v1 " + getMboValue("ID2TIPRO").getString());
            System.out.println("--- v2 " + mbo.getString("ID2PRO"));
            System.out.println("--- v2 " + mbo.getString("ID2TIPRO"));
            if (i != registroCorrente && mbo.getString("ID2TIPRO").equalsIgnoreCase(getMboValue("ID2TIPRO").getString()) && mbo.getString("ID2PRO").equalsIgnoreCase(getMboValue("ID2PRO").getString())) {
                throw new MXApplicationException("matbcom", "valorID2PROID2TIPRODuplicado");
            }

        }
        
        if (getMboValue().getName().equalsIgnoreCase("ID2TIPRO")) {
            System.out.println("--- valor " + getMboValue().getString());
            getMboValue("ID2NUMCON").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2ESTEST").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2ESTADO").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2MUN").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            
            getMboValue("DESCRIPTION").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Outros"));
            getMboValue("ID2PAIS").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Outros"));
        }
        if (!getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro")) {
            getMboValue("ID2NUMCON").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2ESTEST").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2ESTADO").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2MUN").setValueNull(MboConstants.NOACCESSCHECK);
        }
        if (!getMboValue().getString().equalsIgnoreCase("Outros")) {
            getMboValue("DESCRIPTION").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2PAIS").setValueNull(MboConstants.NOACCESSCHECK);
        }
        
        super.validate();
    }
    
    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        if (getMboValue().getName().equalsIgnoreCase("ID2TIPRO")) {
            System.out.println("--- valor " + getMboValue().getString());
            getMboValue("ID2NUMCON").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2ESTEST").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2ESTADO").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            getMboValue("ID2MUN").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro"));
            
            getMboValue("DESCRIPTION").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Outros"));
            getMboValue("ID2PAIS").setFlag(MboConstants.READONLY, !getMboValue().getString().equalsIgnoreCase("Outros"));
            
            if (!getMboValue().getString().equalsIgnoreCase("Estabelecimento Estrangeiro")) {
                getMboValue("ID2NUMCON").setValueNull(MboConstants.NOACCESSCHECK);
                getMboValue("ID2ESTEST").setValueNull(MboConstants.NOACCESSCHECK);
                getMboValue("ID2ESTADO").setValueNull(MboConstants.NOACCESSCHECK);
                getMboValue("ID2MUN").setValueNull(MboConstants.NOACCESSCHECK);
            }
            if (!getMboValue().getString().equalsIgnoreCase("Outros")) {
                getMboValue("DESCRIPTION").setValueNull(MboConstants.NOACCESSCHECK);
                getMboValue("ID2PAIS").setValueNull(MboConstants.NOACCESSCHECK);
            }
        }
    }
}
