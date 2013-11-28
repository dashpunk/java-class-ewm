package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.app.inventory.InvBalancesSetRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class Id2stoxxCurbaladj extends psdi.webclient.beans.inventory.AdjustCurBalanceBean
 {

    
    public Id2stoxxCurbaladj()
    {
      
    }
    
    @Override
    public int execute() throws MXException, RemoteException {
    	String motivo = getMbo().getString("MA_MOTAJU");
    	String grupoEspecie = getMbo().getString("ITEMNUM");
    	String location = app.getDataBean().getMbo().getString("LOCATION");
    	double valorDebito =  getMbo().getDouble("MADEB");
    	int retorno =  super.execute();
    	if (motivo.equalsIgnoreCase("DEBITO POR EVOLUÇÃO")) {
    		
    		String[] detalhamento = grupoEspecie.split("\\.");
    		int item = Integer.valueOf(detalhamento[2]);
    		//não pode evoluir
    		if (item ==4 || item == 8) {
    			
    		}
    		//macho ou femea
    		else if (item < 4 || (item >4 && item <8)) {
    			
    			int itemNum = item+1;
    			String itemnum = detalhamento[0]+"."+detalhamento[1]+"."+itemNum;
    			
    			 MboSetRemote mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("invbalances", sessionContext.getUserInfo());

    			 mboSet.setWhere("LOCATION = '" + location + "'"
    		                + "AND ITEMNUM = '" + itemnum+"'");
    		    mboSet.reset();
    		    System.out.println(mboSet.count());
    		    MboRemote mbo = mboSet.getMbo(0);
//    			MboRemote mbo = getMbo().getThisMboSet().add();
    			
//    		    mbo.setValue("LOCATION", location, MboConstants.NOACCESSCHECK);
    			
//    			mbo.setValue("ITEMNUM", itemnum, MboConstants.NOACCESSCHECK);
    			mbo.setValue("CURBAL", mbo.getDouble("CURBAL")+valorDebito, MboConstants.NOACCESSCHECK);
//    			mbo.setValue("MA_MOTAJU", "CREDITO POR EVOLUÇÃO", MboConstants.NOACCESSCHECK);
    			
    			System.out.println(mbo.getString("LOCATION"));
    			System.out.println(mbo.getString("ITEMNUM"));
    			System.out.println(mbo.getString("CURBAL"));
    			System.out.println(mbo.getString("MA_MOTAJU"));
    			
    			mboSet.save();
    			
    		}
    	}
    	return retorno;
    }

}