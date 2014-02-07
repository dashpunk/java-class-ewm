package psdi.app.person;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.security.UserInfo;
import psdi.util.MXException;
import psdi.util.MaxType;

public class FldPacienteMedicamentos extends MboValueAdapter
{
  Person mbo;

  public FldPacienteMedicamentos(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);
    this.mbo = ((Person)getMboValue().getMbo());
  }

  public void initValue()
    throws MXException, RemoteException
  {
    super.initValue();
    MboRemote person = getMboValue().getMbo();
    if (!person.isNew())
    {
      MboSetRemote smsSet = this.mbo.getMboSet("SMS");
      int i = 0;
      for (MboRemote sms = smsSet.getMbo(i); sms != null; sms = smsSet.getMbo(i))
      {
        if (sms.getBoolean("isprimary"))
        {
          getMboValue().setValue(sms.getString("address"), 11L);

          UserInfo userInfo = person.getUserInfo();
          if (userInfo.isInteractive())
          {
            person.setModified(false);
          }
        }
        i++;
      }

      if (i > 1)
      {
        getMboValue().setFlag(7L, true);
      }
    }
  }

  public void action()
    throws MXException, RemoteException
  {
    MboSetRemote smsSet = this.mbo.getMboSet("SMS");

    if (getMboValue().isNull())
    {
      if (!smsSet.isEmpty())
      {
        int i = 0;
        for (MboRemote sms = smsSet.getMbo(i); sms != null; sms = smsSet.getMbo(i))
        {
          if (sms.getBoolean("isprimary"))
          {
            sms.delete();
            break;
          }
          i++;
        }

      }

    }
    else if (getMboValue().getPreviousValue().isNull())
    {
      MboRemote sms = smsSet.add();
      sms.setValue("personid", getMboValue("personid").getString(), 2L);
      sms.setValue("address", getMboValue().getString(), 2L);
      sms.setValue("isprimary", true, 2L);
    }
    else
    {
      smsSet = this.mbo.getMboSet("SMS");
      int i = 0;
      for (MboRemote sms = smsSet.getMbo(i); sms != null; sms = smsSet.getMbo(i))
      {
        if (sms.getBoolean("isprimary"))
        {
          sms.setValue("address", getMboValue().getString(), 2L);
          break;
        }
        i++;
      }
    }
  }
}