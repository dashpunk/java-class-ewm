package br.inf.ctis.ms.bean;

import psdi.util.MXException;

public class MsDemJud extends psdi.plusp.webclient.beans.pluspwo.PlusPWorkorderAppBean {

	public MsDemJud(){
		
	}

	@Override
	public void save() throws MXException {
		reloadTable();
		refreshTable();
	}
	
}
