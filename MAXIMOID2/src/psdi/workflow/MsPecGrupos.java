package psdi.workflow;

import java.rmi.RemoteException;
import psdi.common.role.CustomRoleAdapter;
import psdi.common.role.CustomRoleInterface;
import psdi.common.role.MaxRole;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsPecGrupos extends CustomRoleAdapter
  implements CustomRoleInterface
{
  public MboRemote evaluateCustomRole(MaxRole roleMbo, MboRemote currentMbo)
    throws MXException, RemoteException
  {
    MboRemote owner = roleMbo.getOwner();
    while (true)
    {
      if (owner == null)
      {
        throw new MXApplicationException("workflow", "OriginWorkflowNotFound");
      }

      if ((owner instanceof WFInstance))
      {
        return owner.getMboSet("ORIGINATOR").getMbo(0);
      }

      owner = owner.getOwner();
    }
  }
}