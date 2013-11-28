package psdi.id2.mintur;

import java.rmi.RemoteException;

import psdi.mbo.MboSet;
import psdi.util.MXException;

public class ID2PO extends psdi.tloam.app.po.PO
        implements ID2PORemote {

	private static final long serialVersionUID = 1L;

	//private MboRemote owner;
    @Override
    public void init()
            throws MXException {
    	//System.out.println("##################### Entrei no INIT");
        super.init();
    }

    public ID2PO(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
        //System.out.println("#################### Construtor");
    }

    @Override
    public void add() throws MXException, RemoteException {
    	// TODO Auto-generated method stub
    	super.add();
    	System.out.println("#################### Entrei no ADD");
    	//Não é possível definir um valor inicial para o campo READONLY
    	//setValue("STORELOC", "01");
    }
    
}
