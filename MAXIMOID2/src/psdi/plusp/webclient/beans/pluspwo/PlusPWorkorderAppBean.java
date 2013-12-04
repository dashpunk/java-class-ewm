package psdi.plusp.webclient.beans.pluspwo;

import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.mbo.StatefulMboRemote;
import psdi.plusp.app.workorder.PlusPWORemote;
import psdi.plusp.webclient.beans.common.PlusPApplier;
import psdi.security.UserInfo;
import psdi.util.BidiUtils;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.WorkorderAppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.session.WebClientSession;

public class PlusPWorkorderAppBean extends WorkorderAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    if ((!this.clientSession.getCurrentEvent().getType().equalsIgnoreCase("save")) && (!this.clientSession.getCurrentEvent().getType().equalsIgnoreCase("dialogok")))
    {
      PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();

      if ((!localPlusPWORemote.isModified("pluspresponseplan")) && (!localPlusPWORemote.isNull("pluspbillbatch")) && (localPlusPWORemote.toBeSaved())) {
        throw new MXApplicationException("inventory", "savecontinue");
      }
    }
    int i = 1;
    try
    {
      i = super.SAVE();
    } catch (MXException localMXException) {
      if ((!localMXException.getErrorGroup().equals("pluspservprov")) || (!localMXException.getErrorKey().equals("recordNotSaved"))) {
        throw localMXException;
      }
    }
    return i;
  }

  public boolean saveYesNoCheck()
    throws MXException
  {
    try
    {
      PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
      if ((localPlusPWORemote == null) || (localPlusPWORemote.isNull("pluspbillbatch")))
        return super.saveYesNoCheck();
      try {
        boolean bool = super.saveYesNoCheck();
        localPlusPWORemote.setBillBatchWarned(false);
        return bool;
      } catch (MXApplicationException localMXApplicationException) {
        if ((localMXApplicationException.getErrorGroup().equalsIgnoreCase("jspmessages")) && (localMXApplicationException.getErrorKey().equalsIgnoreCase("savecontinue"))) {
          localPlusPWORemote.setBillBatchWarned(true);
          StatefulMboRemote localStatefulMboRemote1 = (StatefulMboRemote)localPlusPWORemote.getMboSet("PLUSPBILLBATCH").getMbo(0);
          StatefulMboRemote localStatefulMboRemote2 = (StatefulMboRemote)localPlusPWORemote.getMboSet("PLUSPBILLLINE").getMbo(0);
          String str1 = null;
          String str2 = null;
          if (localStatefulMboRemote1 != null)
            str1 = localStatefulMboRemote1.getString("status");
          if (localStatefulMboRemote2 != null)
            str2 = localStatefulMboRemote2.getString("status");
          String[] arrayOfString = { localPlusPWORemote.getString("pluspbillbatch"), str1, str2 };

          if (BidiUtils.isBidiEnabled()) {
            String str3 = localPlusPWORemote.getUserInfo().getLangCode();
            arrayOfString[0] = BidiUtils.buildAndPush(localPlusPWORemote.getName(), "pluspbillbatch", arrayOfString[0], str3);
            if (str1 != null)
              arrayOfString[1] = BidiUtils.buildAndPush(localStatefulMboRemote1.getName(), "status", arrayOfString[1], str3);
            if (str2 != null) {
              arrayOfString[2] = BidiUtils.buildAndPush(localStatefulMboRemote2.getName(), "status", arrayOfString[2], str3);
            }
          }

          throw new MXApplicationYesNoCancelException("pluspwoSaveContinue", "pluspservprov", "pluspwoSaveContinue", arrayOfString);
        }

        throw localMXApplicationException;
      }
    }
    catch (RemoteException localRemoteException) {
      handleRemoteException(localRemoteException);
    }
    return true;
  }

  public int APPLYPS()
    throws MXException, RemoteException
  {
    return PlusPApplier.apply(this.app.getAppBean(), this.clientSession, this.clientSession.getCurrentEvent(), "PLUSPPRICESCHED");
  }

  public int VIEWCOSTPR()
    throws RemoteException, MXException
  {
    SAVE();
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
    if (localPlusPWORemote != null)
    {
      localPlusPWORemote.calculateTotals();
      this.app.getAppBean().fireDataChangedEvent();
      return 2;
    }
    return 1;
  }

  public int UPDTQUOTE()
    throws RemoteException, MXException
  {
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();

    localPlusPWORemote.initQuote();
    this.app.getAppBean().fireDataChangedEvent();
    return 2;
  }

  public int copyQuoteFields()
    throws RemoteException, MXException
  {
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
    localPlusPWORemote.copyQuoteFields();
    return 1;
  }

  public int undoQuoteChanges()
    throws RemoteException, MXException
  {
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
    localPlusPWORemote.undoQuoteChanges();
    return 1;
  }

  public int MODIFYPS()
    throws RemoteException, MXException
  {
    SAVE();
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
    if (localPlusPWORemote != null) localPlusPWORemote.setModifyPSFlags();
    save();
    return 2;
  }

  public int setStreetAddress()
    throws MXException, RemoteException
  {
    PlusPWORemote localPlusPWORemote = (PlusPWORemote)getMbo();
    localPlusPWORemote.setStreetAddress();
    this.app.getAppBean().fireDataChangedEvent();
    return 1;
  }
}