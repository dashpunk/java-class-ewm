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
import psdi.app.inventory.MatRecTransSet;
import psdi.mbo.*;
import psdi.util.*;

public class ID2MatRecTransSet extends MatRecTransSet
        implements ID2MatRecTransSetRemote {

    public ID2MatRecTransSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    @Override
    public void save() throws MXException, RemoteException {

        Uteis.espera("*********************** save");

        super.save();
    }
}
