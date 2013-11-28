// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WOTasksBean.java

package psdi.webclient.beans.id2.me;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.server.MXServer;

public class ID2PagamentosBean extends AppBean
{

    public ID2PagamentosBean()
    {
    }

    /*
    public int addrow()
        throws MXException
    {
		MboRemote pagto;
		java.util.Date data;
		int ret = -1;
		try
		{
			data = MXServer.getMXServer().getDate();
			ret = super.addrow();
			getMbo().setValue("memes",data.getMonth()+1);
			getMbo().setValue("meano",data.getYear()+1900);
			getMbo().setValue("metbcontid",getParent().getMbo().getString("metbcntid"));
			getMbo().setValue("mevlr",getMbo().getMboSet("MERLFICHA01").getMbo(0).getMboSet("MERLCAT01").getMbo(0).getDouble("MEVLR"));
			getMbo().getThisMboSet().save();
			refreshTable();
			reloadTable();
		}
		catch (MXException mx)
		{
			mx.printStackTrace();
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
    }
     *
     */

    public void gerarPagamentos()
        throws MXException
    {

        int i = 0;
        java.util.Date data;
        
        try
        {
            data = MXServer.getMXServer().getDate();
            MboSet contasSet = (MboSet)MXServer.getMXServer().getMboSet("METBCNT", app.getDataBean().getMboSet().getUserInfo());
            contasSet.setWhere("mestcnt='1'");
            contasSet.reset();
            MboSet pagtoSet = (MboSet)app.getAppBean().getMboSet();
            Mbo pagto;
            Mbo conta;
            do
            {
                conta = (Mbo)contasSet.getMbo(i);
                if(conta == null) break;
                pagto = (Mbo)pagtoSet.add();
                pagto.setValue("memes",data.getMonth()+1);
                pagto.setValue("meano",data.getYear()+1900);
                pagto.setValue("mepagger",MXServer.getMXServer().getDate());
                pagto.setValue("metbcontid",conta.getString("metbcntid"));
                pagto.setValue("mevlr",conta.getMboSet("MERLFICHA01").getMbo(0).getMboSet("MERLCAT01").getMbo(0).getDouble("MEVLR"));
                pagtoSet.save();
                i++;
            } while(true);
            refreshTable();
            reloadTable();
        }
        catch(RemoteException re)
        {
            Object msg[] = {re.getMessage()};
            throw new MXApplicationException("MEPAGTO", "PAGAMENTO",msg);
        }

    }
}
