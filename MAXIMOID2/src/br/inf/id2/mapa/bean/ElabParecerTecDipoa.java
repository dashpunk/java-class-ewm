package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

public class ElabParecerTecDipoa extends AppBean {
	String nome = "---";
	String cargo = "---";

	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		atualizaAtributos();
	}

	public int SAVE() throws MXException, RemoteException {
		atualizaAtributos();
		return super.SAVE();
	}

	public int INSERT() throws MXException, RemoteException {
		int retorno = super.INSERT();
		atualizaAtributos();
		return retorno;
	}

	public synchronized void dataChangedEvent(DataBean speaker) {
		super.dataChangedEvent(speaker);
		atualizaAtributos();
	}

	public synchronized void listenerChangedEvent(DataBean speaker) {
		super.listenerChangedEvent(speaker);
		atualizaAtributos();
	}

	public void bindComponent(BoundComponentInstance boundComponent) {
		super.bindComponent(boundComponent);

		if ((boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESPARTECDIPRPOJ"))
				|| (boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESPARLEI"))
				|| (boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESPARMEL"))
				|| (boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESPARPES"))
				|| (boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESPAROVO"))) {
			atualizaAtributos();
		}
	}

	public synchronized int execute() throws MXException, RemoteException {
		int resultado = super.execute();
		atualizaAtributos();
		return resultado;
	}

	private void atualizaAtributos() {
		try {
			if (this.nome.equalsIgnoreCase("---")) {
				MboSet pessoa = (MboSet) MXServer.getMXServer().getMboSet(
						"PERSON", this.sessionContext.getUserInfo());

				pessoa.setWhere("personid = '"+ this.sessionContext.getUserInfo().getUserName() + "'");
				pessoa.reset();
				System.out.println("after reset");

				this.nome = pessoa.getMbo(0).getString("DISPLAYNAME");
				this.cargo = pessoa.getMbo(0).getString("TITLE");
				System.out.println("nome " + this.nome);
				System.out.println("cargo " + this.cargo);
				Thread.sleep(0L);
			}

			// Solicitação do André referente a alteração do nome da DISPLAYNAME da PERSON afeta o bloco condicional abaixo. alterado em 10/07/2012
			if(this.nome.equalsIgnoreCase("DILEI - Fiscal")) {
				getMbo().setFieldFlag(new String[] {"ID2RESPARLEI"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESPARLEI", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESPARLEI"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DICS - fiscal")) {
				getMbo().setFieldFlag(new String[] {"ID2RESPARMEL"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESPARMEL", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESPARMEL"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DIPES - Fiscal")) {
				getMbo().setFieldFlag(new String[] {"ID2RESPARPES"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESPARPES", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESPARPES"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DICAO - Fiscal")){
				getMbo().setFieldFlag(new String[] {"ID2RESPAROVO"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESPAROVO", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESPAROVO"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DICAR - Fiscal")) {
				getMbo().setFieldFlag(new String[] {"ID2RESPARTECDIPRPOJ"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESPARTECDIPRPOJ", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESPARTECDIPRPOJ"},7L, true);
				
			}
			
		} catch (InterruptedException ex) {
			Logger.getLogger(ElabParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MXException ex) {
			Logger.getLogger(ElabParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
		} catch (RemoteException ex) {
			Logger.getLogger(ElabParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}