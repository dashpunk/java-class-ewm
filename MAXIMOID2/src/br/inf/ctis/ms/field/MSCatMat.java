package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Willians L Andrade
 *
 */
public class MSCatMat extends MboValueAdapter {

    public MSCatMat(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("#### CTIS - Entrou na MSCatMat");
        super.validate();
    
        MboRemote WORKORDER;
        WORKORDER = getMboValue().getMbo().getMboSet("WORKORDER").getMbo(0);
           
        System.out.println("#### CTIS - MSCatMat / Status:" + WORKORDER.getString("STATUS"));
        
        if (WORKORDER.getString("STATUS").equals("ESTADO/MUNICIPIO")) {
        	System.out.println("#### CTIS - MSCatMat / ESTADO/MUNICIPIO");
            novaLinha("ESTADO/MUNICIPIO");
        } else if (WORKORDER.getString("STATUS").equals("VER. PROGRAMA")){
        	System.out.println("#### CTIS - MSCatMat / VER. PROGRAMA");
        	novaLinha("VER. PROGRAMA");
        }
    }

    /**
	 * Adiciona nova linha
	 */
	private void novaLinha(String status) throws RemoteException, MXException {
		// TODO Auto-generated method stub
		
		MboRemote mbo = getMboValue().getMbo();
            
        if (status == "ESTADO/MUNICIPIO"){  	
        	System.out.println("#### CTIS - MSCatMat / Nova Linha: ESTADO/MUNICIPIO");
        	mbo.setValue("MSALNTIPOATENDIMENTO", status, MboConstants.NOACCESSCHECK);
        } else if (status == "VER. PROGRAMA"){
        	System.out.println("#### CTIS - MSCatMat / Nova Linha: VER. PROGRAMA");
    		MboSet mboSetMedicamentosVW;
    		mboSetMedicamentosVW = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("VW_MEDICAMENTO_ESTOQUE", getMboValue().getMbo().getUserInfo());
    		mboSetMedicamentosVW.setWhere("CO_CATMAT = '" + getMboValue().getMbo().getString("CATMAT") + "' and QT_SALDO_ATUAL > 0");        	
    		mboSetMedicamentosVW.reset();     
    		
    		if (mboSetMedicamentosVW.count() > 0 ){
    			System.out.println("#### CTIS - MSCatMat / Nova Linha: > 0");
    			mbo.setValue("MSALNTIPOATENDIMENTO", "PROGRAMA", MboConstants.NOACCESSCHECK);
    		} else {
    			System.out.println("#### CTIS - MSCatMat / Nova Linha: <= 0");
    			mbo.setValue("MSALNTIPOATENDIMENTO", "COMPRA", MboConstants.NOACCESSCHECK);
    		}
        }
        mbo.setFieldFlag("MSALNTIPOATENDIMENTO",MboConstants.READONLY, true);	
	}
}
