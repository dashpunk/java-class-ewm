/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.seedf.mbo;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class ID2FechamentoPeriodo extends psdi.mbo.custapp.CustomMbo
        implements ID2FechamentoPeriodoRemote {

    //private MboRemote owner;
    @Override
    public void init()
            throws MXException {
        super.init();
    }

    public ID2FechamentoPeriodo(MboSet mboset) throws MXException, RemoteException {
        super(mboset);

    }

    @Override
    public boolean toBeDeleted() {
        ////System.out.println("------- toBeDeleted()");
        return super.toBeDeleted();

    }

    @Override
    public void delete() throws MXException, RemoteException {
        ////System.out.println("------- delete()");
        super.delete();
    }

    @Override
    public void delete(long accessModifier) throws MXException, RemoteException {
        ////System.out.println("------- toBeDeleted(long)");
        super.delete(accessModifier);
    }

    @Override
    public void canDelete() throws MXException, RemoteException {
        super.canDelete();
        ////System.out.println("------- canDelete()");

    }

    @Override
    public void setFlags(long flags) {
        super.setFlags(flags);
        ////System.out.println("------- setFlags " + flags);
    }

    @Override
    public void setDeleted(boolean deleted) {
        super.setDeleted(deleted);
        ////System.out.println("------- setDeleted");
    }

    @Override
    public void undelete() throws MXException, RemoteException {
        super.undelete();
        ////System.out.println("------- undelete");
    }
}
