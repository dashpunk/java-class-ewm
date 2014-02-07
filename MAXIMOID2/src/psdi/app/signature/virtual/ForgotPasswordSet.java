/*
 * Willians Andrade
 */

package psdi.app.signature.virtual;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.NonPersistentMboSet;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.server.MXServer;
import psdi.server.MXServerInfo;
import psdi.util.MXAccessException;
import psdi.util.MXException;

public class ForgotPasswordSet extends NonPersistentMboSet
  implements NonPersistentMboSetRemote
{
  static final long serialVersionUID = -5609735698248055467L;

  public ForgotPasswordSet(MboServerInterface ms)
    throws MXException, RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException
  {
    return new ForgotPassword(ms);
  }

  public void canAdd()
    throws MXException
  {
    String app = null;
    try
    {
      app = getApp();
    }
    catch (Exception e)
    {
    }
    try {
      if ((app != null) && (MXServer.getMXServer().getMXServerInfo().useAppServerSecurity()))
        throw new MXAccessException("access", "mxauthenabled");
    }
    catch (Exception e)
    {
      throw new MXAccessException("access", "mxauthenabled");
    }
  }

  public void execute()
    throws MXException, RemoteException
  {
    if (!isEmpty())
    {
      ((ForgotPassword)getMbo(0)).requestPassword();
    }
  }
}