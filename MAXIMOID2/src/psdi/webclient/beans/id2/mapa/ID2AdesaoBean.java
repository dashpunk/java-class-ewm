package psdi.webclient.beans.id2.mapa;

import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.id2.mapa.Uteis;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

public class ID2AdesaoBean extends DataBean
{

    public ID2AdesaoBean()
    {
    }

    public void aderir()
        throws MXException, RemoteException
    {
        try
        {
            DataBean exploracao = app.getDataBean("MAINRECORD");
            if(exploracao != null)
            {
                System.out.println("entrou 1");
                System.out.println((new StringBuilder()).append("Valor MAADESAO ").append(exploracao.getMbo().getBoolean("MAADESAO")).toString());
                System.out.println((new StringBuilder()).append("Valor ID2ADESAO 2 ").append(exploracao.getMbo().getBoolean("ID2ADESAO")).toString());
                exploracao.getMbo().setValue("MAADESAO", exploracao.getMbo().getBoolean("ID2ADESAO"));
                System.out.println((new StringBuilder()).append("Valor MAADESAO 2 ").append(exploracao.getMbo().getBoolean("MAADESAO")).toString());
            }
        }
        catch(MXException mx)
        {
            Uteis.espera(mx.getMessage());
        }
        catch(RemoteException re)
        {
            Uteis.espera(re.getMessage());
        }
        catch(Throwable throwable)
        {
            Uteis.espera(throwable.getMessage());
        }
        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("dialogok", event.getTargetId(), event.getValue(), sessionContext));
    }
}