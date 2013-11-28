// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2InvVendorTableBean.java

package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import psdi.app.inventory.MatRecTransRemote;
import psdi.app.inventory.MatRecTransSetRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ID2TransEleBean extends DataBean
{
    private MboSetRemote mbosetremote;

    public ID2TransEleBean()
    {
    	System.out.println("*** ID2TransEleBean ***");
    }

	public void initialize()
        throws MXException, RemoteException
    {
        super.initialize();
        System.out.println("*** ID2TransEleBean - initialize ***");
		try
		{
			DataBean gtaBean = app.getDataBean("MAINRECORD");
			if(gtaBean != null)
			{
				System.out.println("*** if gtaBean != null");
				setAppWhere("maponum = '"+gtaBean.getMbo().getString("PONUM")+"'");
				setUserWhere("maponum = '"+gtaBean.getMbo().getString("PONUM")+"'");
			}
		}
		catch (MXException mx)
		{
			System.out.println("*** catch 1"+mx.getMessage());
		}
		catch (RemoteException re)
		{
			System.out.println("*** catch 2"+re.getMessage());
		}
	}

	public void realizar()
           throws MXException, RemoteException
	{
        Properties prop;
        prop = MXServer.getMXServer().getConfig();
        String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
        String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
        String username = prop.getProperty("mxe.db.user", "dbmaximo");
        String password = prop.getProperty("mxe.db.password", "id2maximo");

		try
		{
	        Class.forName(driver).newInstance();
	        java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));

	        System.out.println("*** ID2TransEleBean - realizar ***");
            int i = 0;

            System.out.println("*** antes do save");
            long uniqueId = getUniqueIdValue();
            getMboSet().save();
            System.out.println("*** depois do save");

            MboSetRemote elementos = getMboForUniqueId(uniqueId).getMboSet("MARLTRANSELELINE");
            MboRemote elemento;
            System.out.println("*** elementos.count() "+elementos.count());
            System.out.println("*** antes do while");

            while (elementos.getMbo(i) != null) {
                elemento = elementos.getMbo(i);
                if(elemento != null)
                {

                	PreparedStatement ps2 = conexao.prepareStatement("{ call movimentacao(?,?,?,?,?,?) }");
                    ps2.setString(1, elemento.getMboSet("MARLINVBALANCES").getMbo(0).getString("location"));
                    ps2.setString(2, elemento.getMboSet("MARLINVBALANCES").getMbo(0).getString("binnum"));
                    ps2.setString(3, elemento.getMboSet("MARLINVBALANCES").getMbo(0).getString("conditioncode"));
                    ps2.setString(4, elemento.getString("ASSETNUM"));
                    ps2.setString(5, elemento.getMboSet("MARLLOCDEST01").getMbo(0).getString("LOCATION"));
                    ps2.setString(6, elemento.getThisMboSet().getUserInfo().getPersonId());

                    ps2.executeUpdate();
                    
                    conexao.commit();

                }
                i++;
            }
            
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
            System.out.println("*** 3 pessoa depois de ninguem! Acabou...");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
