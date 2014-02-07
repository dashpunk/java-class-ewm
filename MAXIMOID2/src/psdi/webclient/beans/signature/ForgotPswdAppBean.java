/*
 * Willians Andrade
 */

package psdi.webclient.beans.signature;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ForgotPswdAppBean extends AppBean
{
  public int submit()
    throws MXException, RemoteException
  {
    MboRemote forgotPassword = getMbo();
    save();

    Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "signature", "pwResetDone", null);

    this.sessionContext.queueEvent(new WebClientEvent("signout", this.app.getId(), null, this.sessionContext));
    return 1;
  }
}