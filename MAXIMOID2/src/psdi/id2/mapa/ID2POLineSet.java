/*
 * ID2POLineSet.java
 *
 * Created on 24 de Novembro de 2010, 17:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author dyogo.dantas
 */
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class ID2POLineSet extends psdi.tloam.app.po.POLineSet
	implements ID2POLineSetRemote
{
    public ID2POLineSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException {
        return new ID2POLine(mboset);
    }
}
