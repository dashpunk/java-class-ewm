package br.inf.id2.valec.bean;

import psdi.webclient.system.controller.*;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.*;
import psdi.mbo.*;

/**
 * @author Jessé Rovira
 */
public class MedicaoContrato extends AppBean {

    public MedicaoContrato() {
    }
    
    public int finalizamedicao() 
    	throws MXException, RemoteException
    {
    	
    	System.out.println("#### debug 1");
    	
    	MboRemote mbo = app.getDataBean().getMbo();
    	System.out.println("#### debug 2");
    	MboSetRemote itensSet = mbo.getMboSet("GORLMEDCONTITEM01");
    	System.out.println("#### debug 3");
    	MboSetRemote woSet;
    	System.out.println("#### debug 4");
    	MboRemote wo;
    	System.out.println("#### debug 5");
    	MboRemote wotask;
    	System.out.println("#### debug 6");
    	MboRemote matrec;
    	System.out.println("#### debug 7");
    	
    	long wotaskID;
    	System.out.println("#### debug 8");
    	
    	for (int i=0; i<=itensSet.count(); i++)
    	{
        	System.out.println("#### debug 9");
    		if(itensSet.getMbo(i).getDouble("GONUQTDITEM")>0)
    		{
    	    	System.out.println("#### debug 10");
        		woSet = itensSet.getMbo(i).getMboSet("GORLWORKORDER01");
            	System.out.println("#### debug 11");
        		if(woSet.getMbo()!=null)
        	    	System.out.println("#### debug 12");
        		{
        	    	System.out.println("#### debug 13");
        			wo = woSet.getMbo();
        	    	System.out.println("#### debug 14");
        			wo.getMboSet("CHILDTASK").setWhere("taskid = '"+itensSet.getMbo(i).getString("FKGONUNUMMEDICAO")+"'");
        	    	System.out.println("#### debug 15");
        			wo.getMboSet("CHILDTASK").reset();
        	    	System.out.println("#### debug 16");
        			if(wo.getMboSet("CHILDTASK").getMbo()!=null)
        			{
        		    	System.out.println("#### debug 17");
        				wotask = wo.getMboSet("CHILDTASK").getMbo();
        		    	System.out.println("#### debug 18");
        			}
        			else
        			{
        		    	System.out.println("#### debug 19");
        				wotask = wo.getMboSet("CHILDTASK").add();
        		    	System.out.println("#### debug 20");
        				wotask.setValue("ID2CONTRACTNUM", itensSet.getMbo(i).getString("FKGOCONTRACTNUM"));
        		    	System.out.println("#### debug 21");
        				wotask.setValue("ID2CONTRACTREV", "0");
        		    	System.out.println("#### debug 22");
        				wotask.setValue("ASSETNUM", itensSet.getMbo(i).getString("FKGOASSETNUM"));
        		    	System.out.println("#### debug 23");
        				wotask.setValue("CTNUMOS", mbo.getString("FKGOSTNUMOS"));
        		    	System.out.println("#### debug 24");
        				wotask.setValue("SCHEDSTART", mbo.getDate("GODTDTAINIMED"));
        		    	System.out.println("#### debug 25");
        				wotask.setValue("SCHEDFINISH", mbo.getDate("GODTDTAFIMMED"));
        		    	System.out.println("#### debug 26");
        				wotask.setValue("ACTSTART", mbo.getDate("GODTDTAINIMED"));
        		    	System.out.println("#### debug 27");
        				wotask.setValue("ACTFINISH", mbo.getDate("GODTDTAFIMMED"));
        		    	System.out.println("#### debug 28");
        				wotaskID = wotask.getUniqueIDValue(); 
        		    	System.out.println("#### debug 29");
        				wo.getMboSet("CHILDTASK").save();
        		    	System.out.println("#### debug 30");
        				wotask = wo.getMboSet("CHILDTASK").getMboForUniqueId(wotaskID);
        		    	System.out.println("#### debug 31");
        			}
        			
        	    	System.out.println("#### debug 32");
        			matrec = wotask.getMboSet("SHOWACTUALMATERIAL").add();                
        	    	System.out.println("#### debug 33");
                    matrec.setValue("LINETYPE", "MATERIAL");
                	System.out.println("#### debug 34");
        			matrec.setValue("ID2ITEMNUMOB", itensSet.getMbo(i).getString("FKGOITEMNUM"));
        	    	System.out.println("#### debug 35");
        			matrec.setValue("DESCRIPTION", itensSet.getMbo(i).getMboSet("GORLCONTRACTLINE01").getMbo().getMboSet("ITEM").getMbo().getString("DESCRIPTION"));
        	    	System.out.println("#### debug 36");
        			matrec.setValue("UNITCOST", itensSet.getMbo(i).getMboSet("GORLCONTRACTLINE01").getMbo().getDouble("unitcost"));
        	    	System.out.println("#### debug 37");
        			matrec.setValue("MXINITCUST", itensSet.getMbo(i).getMboSet("GORLCONTRACTLINE01").getMbo().getDouble("unitcost"));
        	    	System.out.println("#### debug 38");
        			matrec.setValue("POSITIVEQUANTITY", itensSet.getMbo(i).getDouble("GONUQTDITEM"));
        	    	System.out.println("#### debug 39");
        			wotask.getMboSet("SHOWACTUALMATERIAL").save();
        	    	System.out.println("#### debug 40");
        		}
    			
    		}
    	}
    	
    	System.out.println("#### debug 41");
    	mbo.setValue("GOSTCODSTATUS", "FINALIZADA");
    	System.out.println("#### debug 42");
        WebClientEvent event = clientSession.getCurrentEvent();
    	System.out.println("#### debug 43");
        clientSession.showMessageBox(event, "id2message", "medreal", null);
        
        return 0;
    
    }
    
    public int limparmedicao() 
		throws MXException, RemoteException
	{
    	MboRemote mbo = app.getDataBean().getMbo();
    	MboSetRemote itensSet = mbo.getMboSet("GORLMEDCONTITEM01");
    	for(int i=0;i<=itensSet.count();i++)
    		itensSet.getMbo(i).delete();
		app.getDataBean().save();
		
		return 0;

    }
}

