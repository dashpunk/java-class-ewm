package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.beans.pr.PRLinesTableBean;

public class ID2PRLineTableBean extends PRLinesTableBean {

    public ID2PRLineTableBean() {
    }

    @Override
    public synchronized void save() throws MXException {
        super.save();
        try {
            if (parent.getMbo().getString("STATUS").equals("ENVIADO")) {
                getMbo().setValue("ID2STATUS", "ALTERADO", MboConstants.NOACCESSCHECK);
            }
        } catch (Exception ex) {
            System.out.println("************************* ERRO = " + ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }
    
    public void duplicarLinha()
    throws MXException, RemoteException
	{
		long uid = getMbo().getUniqueIDValue();
		String prnum=getMbo().getString("PRNUM");
		String prlinenum=getMbo().getString("PRLINENUM");
		
		try
		{
			parent.save();
		}
		finally
		{
			try
			{
		    	MboSetRemote linhasPR = MXServer.getMXServer().getMboSet("PRLINE", parent.getMbo().getUserInfo());
		    	linhasPR.setWhere("PRNUM='"+prnum+"' and prlinenum='"+prlinenum+"'");
		    	linhasPR.reset();
		    	linhasPR.getMbo(0).setValue("PRLINENUM", parent.getMbo().getMboSet("PRLINE").count()+1);
		    	linhasPR.copy(parent.getMbo().getMboSet("PRLINE"));
		    	linhasPR.getMbo(0).setValue("PRLINENUM", prlinenum);
			}
			catch(Exception e)
			{
				System.out.println("rolou um erro");
				System.out.println("------------------------------------------"+e.getMessage());
				e.printStackTrace();
			}
		}    	
	}
}
