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

public class PreencherLaudoHistorico extends AppBean {
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
		if (boundComponent.getProperty("dataattribute").equalsIgnoreCase(
				"ID2RESLAUHST"))
			atualizaAtributos();
	}

	public synchronized int execute() throws MXException, RemoteException {
		int resultado = super.execute();
		atualizaAtributos();
		return resultado;
	}

	private void atualizaAtributos() {
		try {
			if (this.nome.equalsIgnoreCase("---")) {
				MboSet pessoa = (MboSet) MXServer.getMXServer().getMboSet("PERSON", this.sessionContext.getUserInfo());

				pessoa.setWhere("personid = '"+ this.sessionContext.getUserInfo().getUserName() + "'");
				pessoa.reset();
				System.out.println("after reset");

				this.nome = pessoa.getMbo(0).getString("DISPLAYNAME");
				this.cargo = pessoa.getMbo(0).getString("TITLE");
				System.out.println("nome " + this.nome);
				System.out.println("cargo " + this.cargo);
				Thread.sleep(0L);
			}

			getMbo().setFieldFlag(new String[] { "ID2RESLAUHST" }, 7L, false);
			System.out.println("flag");

			getMbo().setValue("ID2RESLAUHST", this.nome, 9L);

			System.out.println("setValue");
			Thread.sleep(0L);

			getMbo().setFieldFlag(new String[] { "ID2RESLAUHST" }, 7L, true);
			System.out.println("end");
			Thread.sleep(0L);
		} catch (InterruptedException ex) {
			Logger.getLogger(PreencherLaudoHistorico.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (MXException ex) {
			Logger.getLogger(PreencherLaudoHistorico.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (RemoteException ex) {
			Logger.getLogger(PreencherLaudoHistorico.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}
}