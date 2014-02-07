/*
 * Willians Andrade
 */

package psdi.webclient.beans.signature;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.session.WebClientSession;

public class ChangePswdBean extends AppBean
{
  public void initialize()
    throws MXException, RemoteException
  {
    super.initialize();
    try
    {
      MXSession s = getMXSession();
      if (s == null) {
        return;
      }
      SqlFormat sqf = new SqlFormat("loginid = :1");
      boolean convertToUpper = MXServer.getMXServer().getProperty("mxe.convertloginid").equals("1");
      String loginID = convertToUpper ? s.getUserName().toUpperCase() : s.getUserName();
      sqf.setObject(1, "MAXUSER", "LOGINID", loginID);
      String where = sqf.format();

      MboSetRemote set = getMboSet();
      if (set != null)
      {
        setAppWhere(where);
        reset();
        if (set.getName().equals("MAXUSER"))
        {
          MboRemote user = set.getMbo(0);
          if (user != null)
          {
            setCurrentRow(0);
            MboSetRemote chgPswdSet = user.getMboSet("CHANGEPASSWORDS");
            if (chgPswdSet.isEmpty())
              chgPswdSet.add();
          }
        }
      }
    }
    catch (MXException e)
    {
      this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), e);
    }
  }

  public int submit()
    throws MXException, RemoteException
  {
    MboRemote user = getMbo();

    MboSetRemote chgPswdSet = user.getMboSet("CHANGEPASSWORDS");
    if (!chgPswdSet.isEmpty())
    {
      user.setValue("forceexpiration", false);
      ((NonPersistentMboSetRemote)chgPswdSet).execute();
    }

    try
    {
      this.sessionContext.queueEvent(this.sessionContext.getLoginEvent());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "signature", "changepswdDone", null);

    return 1;
  }

  public boolean canExit()
    throws MXException, RemoteException
  {
    return true;
  }
}