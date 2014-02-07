/*
 * Willians Andrade
 */

package psdi.app.signature.virtual;

import java.rmi.RemoteException;
import psdi.app.signature.SignatureService;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.NonPersistentMbo;
import psdi.mbo.NonPersistentMboRemote;
import psdi.mbo.SqlFormat;
import psdi.mbo.Translate;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class ForgotPassword extends NonPersistentMbo
  implements NonPersistentMboRemote
{
  static final long serialVersionUID = -4032521219379575962L;

  public ForgotPassword(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void requestPassword()
    throws MXException, RemoteException
  {
    MboSetRemote emailSet = getMboSet("$checkPerson", "EMAIL", "emailaddress = :primaryemail");
    if ((emailSet.isEmpty()) || (emailSet.count() > 1))
    {
      throw new MXApplicationException("signature", "ForgotPswdBadEntry");
    }

    MboRemote emailMbo = emailSet.getMbo(0);
    if (!emailMbo.getBoolean("isprimary"))
    {
      throw new MXApplicationException("signature", "ForgotPswdBadEntry");
    }

    String[] status = { "ACTIVE" };
    String statusList = getTranslator().toExternalList("MAXUSERSTATUS", status);
    SqlFormat sqf = new SqlFormat(getUserInfo(), "personid = :1 and status in (" + statusList + ")");
    sqf.setObject(1, "MAXUSER", "PERSONID", emailMbo.getString("personid"));
    MboSetRemote userSet = getMboSet("checkUser", "MAXUSER", sqf.format());
    if (userSet.isEmpty())
    {
      throw new MXApplicationException("signature", "ForgotPswdInactiveUser");
    }
    if (userSet.count() > 1)
    {
      throw new MXApplicationException("signature", "ForgotPswdBadEntry");
    }

    MboRemote userMbo = userSet.getMbo(0);

    /*
     * Implementação solicitada por MARLI para atender o cliente final
     * para alteração do senha dos usuarios.
     * 
     * 07/02/2014 por WILLIANS ANDRADE
     */
    
    /*
    if (!userMbo.getString("pwhintquestion").equals(getString("pwhintquestion")))
    {
      throw new MXApplicationException("signature", "ForgotPswdBadEntry");
    }

    if (!userMbo.getString("pwhintanswer").equals(getString("pwhintanswer")))
    {
      throw new MXApplicationException("signature", "ForgotPswdBadEntry");
    }
	*/
    String newPassword = ((SignatureService)getMboServer()).generatePassword(userMbo.getString("userid"), MXServer.getMXServer().getSystemUserInfo());

    userMbo.setValue("passwordinput", newPassword, 11L);
    userMbo.setValue("passwordcheck", newPassword, 11L);
    userMbo.setValue("password", newPassword, 11L);
    userMbo.setValue("forceexpiration", true, 11L);
    userMbo.setValue("emailpswd", true, 11L);

    userSet.save(2L);
  }
}