package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class Id2po02RecebeGta extends psdi.webclient.beans.po.CompleteReceiptsBean
 {

    
    public Id2po02RecebeGta()
    {
      
    }
    
     @Override
    protected void initialize() throws MXException, RemoteException {
    	// TODO Auto-generated method stub
//    	 getMboSet().selectAll();
    	 
    	super.initialize();
   	 //System.out.println("initialize");

	 if (getMboSet() != null) {
		 MboRemote mbo;
		 for (int i = 0; ((mbo = getMboSet().getMbo(i)) != null) ; i++) {
			 System.out.println(mbo.getDouble("ORDERQTY"));
			 if (mbo.isNull("RECEIVEDQTY")) {
				 System.out.println(mbo.getDouble("RECEIVEDQTY"));
				 mbo.setValue("RECEIVEDQTY", mbo.getDouble("ORDERQTY"), MboConstants.NOACCESSCHECK);
				 System.out.println(mbo.getDouble("RECEIVEDQTY"));
				 mbo.setFieldFlag("RECEIVEDQTY", MboConstants.READONLY, false);
			 }
			 
			 
		}
		 
		 
		 
//		 reloadTable();
//		 refreshTable();
	 }
    }
     
     
     
    @Override
    public int execute() throws MXException, RemoteException {
    	getMboSet().selectAll();
    	int retorno =  super.execute();
    	app.getDataBean().getMbo().setValue("ID2PERRECEBE", sessionContext.getUserInfo().getUserName(), MboConstants.NOACCESSCHECK);
    	app.getDataBean().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, false);
    	app.getDataBean().getMbo().setValue("STATUS", "BAIXADA", MboConstants.NOVALIDATION_AND_NOACTION);
    	app.getDataBean().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, true);
    	return retorno;
    }

}