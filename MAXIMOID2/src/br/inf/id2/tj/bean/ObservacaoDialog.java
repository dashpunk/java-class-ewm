package br.inf.id2.tj.bean;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

<<<<<<< .mine
public class ObservacaoDialog extends DataBean {
=======
public class ObservacaoDialog extends DataBean { 
>>>>>>> .r896


<<<<<<< .mine
	public ObservacaoDialog() {
		super();
	}
=======
    public ObservacaoDialog() {
        super();
    }
>>>>>>> .r896

<<<<<<< .mine
	public void initialize()
=======
    public void initialize()
>>>>>>> .r896
    throws MXException, RemoteException
<<<<<<< .mine
	{
	    super.initialize();
		try
		{
			DataBean wo = app.getDataBean("MAINRECORD");
			if(wo != null)
			{
				setAppWhere("wonum = '"+wo.getMbo().getString("wonum")+"'");
				setUserWhere("wonum = '"+wo.getMbo().getString("wonum")+"'");
			}
		}
		catch (MXException mx)
		{
			System.out.println(mx.getMessage());
		}
		catch (RemoteException re)
		{
			System.out.println(re.getMessage());
		}
	}
}
=======
    {
        super.initialize();
        try
        {
            DataBean wo = app.getDataBean("MAINRECORD");
            if(wo != null)
            {
                setAppWhere("wonum = '"+wo.getMbo().getString("wonum")+"'");
                setUserWhere("wonum = '"+wo.getMbo().getString("wonum")+"'");
            }
        }
        catch (MXException mx)
        {
            System.out.println("EX1: "+mx.getMessage());
        }
        catch (RemoteException re)
        {
            System.out.println("EX1: "+re.getMessage());
        }
    }
}
>>>>>>> .r896
