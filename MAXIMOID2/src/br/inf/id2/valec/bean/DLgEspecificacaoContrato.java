package br.inf.id2.valec.bean;

import psdi.webclient.system.controller.*;
import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Patrick
 */
public class DLgEspecificacaoContrato extends DataBean {

    public DLgEspecificacaoContrato() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {

        super.initialize();
        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("setAutoKey", "createcont", event.getValue(), sessionContext));
    }

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		String contractnum = getMbo().getString("CONTRACTNUM");
		String aditivo = getMbo().getString("MTNUMADI");
		int i = super.execute();
		parent.getMbo().getMboSet("MXRL01FILHOS").setWhere("CONTRACTNUM='"+contractnum+"'");
		parent.getMbo().getMboSet("MXRL01FILHOS").reset();
		if(parent.getMbo().getMboSet("MXRL01FILHOS").getMbo(0)!=null)
		{
			parent.getMbo().getMboSet("MXRL01FILHOS").getMbo(0).setValue("MTNUMADI", aditivo);
			parent.getMbo().getMboSet("MXRL01FILHOS").save();
		}
		return i;
	}
    
    
}
