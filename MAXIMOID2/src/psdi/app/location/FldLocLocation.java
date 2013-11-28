package psdi.app.location;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldLocLocation extends MAXTableDomain
{
  public FldLocLocation(MboValue mbv)
    throws MXException
  {
    super(mbv);
  }

  public void validate() throws MXException, RemoteException
  {
    MboRemote location = getMboValue().getMbo();
    if (location.toBeAdded())
    {
      MboRemote locOper = location.getMboSet("LOCOPER").getMbo(0);
      if (locOper != null)
      {
        locOper.setValue("location", getMboValue().getString(), 2L);
      }

      if (locOper == null)
      {
        MboValue locLocation = getMboValue();
        Location locationMbo = (Location)locLocation.getMbo();
        if (locationMbo.isOperating())
        {
          MboSetRemote locOperSet = locationMbo.getMboSet("LOCOPER");
          locOper = locOperSet.add();
        }
      }
    }
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    LocationSet locSet = (LocationSet)getMboValue().getMbo().getThisMboSet();
    if (locSet.isStoreroom())
    {
      return locSet;
    }
    return null;
  }
}