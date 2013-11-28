package br.inf.ctis.ms.bean;

import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 * 
 */
public class MsTerAjuste extends psdi.webclient.system.beans.AppBean {

	public MsTerAjuste() {
		System.out.print("CTIS # --- MsTerAjuste");
	}

	@Override
	public synchronized void dataChangedEvent(DataBean speaker) {

		System.out.println("CTIS # --- MsTerAjuste dataChangedEvent()");
		System.out.println("CTIS # --- MsTerAjuste reloadTable()");
		reloadTable();
		System.out.println("CTIS # --- MsTerAjuste refreshTable()");
		refreshTable();

		super.dataChangedEvent(speaker);
		System.out.println("CTIS # --- MsTerAjuste Super()");
	}

} 