/*
 * Willians Andrade
 */

package psdi.webclient.beans.signature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.HashMap;
import psdi.util.MXException;
import psdi.webclient.beans.common.TreeControlBean;
import psdi.webclient.system.beans.DataBean;

public class SecurityProfileTreeBean extends TreeControlBean
{
  private HashMap treeControlProps;

  protected void initialize()
    throws MXException, RemoteException
  {
    super.initialize();
  }

  public void setcurrentnode(String newobjectname, String newuniqueidname, String newuniqueidvalue)
    throws MXException, RemoteException
  {
  }

  public int selectnode()
    throws MXException, RemoteException
  {
    return 1;
  }

  private void loadTreeControlProps() throws MXException, RemoteException
  {
    if (this.mboSetRemote != null)
    {
      try
      {
        Method m = this.mboSetRemote.getClass().getMethod("getTreeControlProps", new Class[0]);
        this.treeControlProps = ((HashMap)m.invoke(this.mboSetRemote, new Object[0]));
      }
      catch (SecurityException e)
      {
        e.printStackTrace();
      }
      catch (NoSuchMethodException e)
      {
      }
      catch (IllegalArgumentException e1)
      {
        e1.printStackTrace();
      }
      catch (IllegalAccessException e1)
      {
        e1.printStackTrace();
      }
      catch (InvocationTargetException e1)
      {
        e1.printStackTrace();
      }
    }
  }

  public synchronized void structureChangedEvent(DataBean speaker)
  {
    super.structureChangedEvent(speaker);
    setRefreshTree(true);
  }
}