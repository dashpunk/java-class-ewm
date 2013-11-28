package br.inf.id2.me.bean;


import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Patrick
 */
public class UnidadeOrganizacionalEOTabBean extends DataBean {

	public UnidadeOrganizacionalEOTabBean() {
		super();
	}

    @Override
    public int selectrecord() throws MXException, RemoteException {
        System.out.println("----- selectedrow()");
        return super.selectrecord();

    }



}
