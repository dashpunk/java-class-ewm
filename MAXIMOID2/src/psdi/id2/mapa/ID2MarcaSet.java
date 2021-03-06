/*
 * PoliticaSetRemote.java
 *
 * Created on 30 de Maio de 2006, 17:15
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
import psdi.security.UserInfo;
import psdi.util.*;

public class ID2MarcaSet extends MboSet
	implements ID2MarcaSetRemote
{
    public ID2MarcaSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new ID2Marca(mboset);
    }
}
