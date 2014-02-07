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

public class SelfRegAppBean extends AppBean
{
  public int submit()
    throws MXException, RemoteException
  {
    MboRemote addUser = getMbo();
    save();
    this.sessionContext.queueEvent(new WebClientEvent("signout", this.app.getId(), null, this.sessionContext));
    Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "signature", "selfRegDone", null);
    return 1;
  }
}