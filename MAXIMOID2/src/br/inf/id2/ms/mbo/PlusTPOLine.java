package br.inf.id2.ms.mbo;

import br.inf.id2.mintur.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class PlusTPOLine extends psdi.plust.app.po.PlusTPOLine
        implements PlusTPOLineRemote {

    public PlusTPOLine(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

}
