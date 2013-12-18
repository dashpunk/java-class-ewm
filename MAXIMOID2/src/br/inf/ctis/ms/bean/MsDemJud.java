package br.inf.ctis.ms.bean;

import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 * 
 */
public class MsDemJud extends
		psdi.plusp.webclient.beans.pluspwo.PlusPWorkorderAppBean {

	public MsDemJud() {
		System.out.print("CTIS # --- Entrou na Classe AppBean MsDemJud");
	}
	
	
	@Override
	public synchronized void dataChangedEvent(DataBean speaker) {
		reloadTable();
		refreshTable();
		
		super.dataChangedEvent(speaker);
	}
}
