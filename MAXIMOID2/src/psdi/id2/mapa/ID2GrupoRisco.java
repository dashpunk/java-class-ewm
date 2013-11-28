/*
 * ID2GrupoRisco.java
 *
 * Created on 30 de Maio de 2006, 17:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author jesse.rovira
 */
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.*;
import psdi.app.asset.Asset;

// Referenced classes of package psdi.app.asset:
//            FinControlRemote

public class ID2GrupoRisco extends Mbo 
	implements ID2GrupoRiscoRemote
{
    
    /** Creates a new instance of ID2GrupoRisco */
    public ID2GrupoRisco(MboSet mboset) throws MXException, RemoteException{
        super(mboset);
    }
    
    public void init()
        throws MXException
    {
        super.init();
    }

    public void add()
        throws MXException, RemoteException
    {
        super.add();
        Mbo mboPai = (Mbo)getOwner();
		//11L significa, das constantes do Maximo, que o campo náo terá validação. A classe est?tica que mant?m essa
        setValue("hazardid", mboPai.getString("hazardid"));
	}
}
