package br.inf.id2.valec.bean;

import psdi.webclient.system.controller.*;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.*;
import psdi.mbo.*;

/**
 * @author Jess� Rovira
 */
public class DlgSelItensBase extends DataBean {

    public DlgSelItensBase() {
    }

    protected void initialize() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.initialize();
		// Filtra as compet�ncias pelo m�s e ano que est� escolhido na medi��o
		/*
		if(app.getDataBean("selitenscont_comp")!=null)
			app.getDataBean("selitenscont_comp").setAppWhere("dscomp = to_char(to_date('"+getParent().getMbo().getString("SCHEDSTART")+"','DD/MM/YY HH24:MI:SS'),'MM/YYYY')");
		if(app.getDataBean("selitenscontefet_comp")!=null)
			app.getDataBean("selitenscontefet_comp").setAppWhere("dscomp = to_char(to_date('"+getParent().getMbo().getString("SCHEDSTART")+"','DD/MM/YY HH24:MI:SS'),'MM/YYYY')");
		*/
			
	}

}
