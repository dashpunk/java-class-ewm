package br.inf.id2.common.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Properties;

import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Dyogo
 */
public class ThreadComandoShell extends Thread {

	private String comando;
	
	public ThreadComandoShell() {
		super();
		System.out.println("#################### ThreadComandoShell - construtor");
	}

	public void run() {
		
		System.out.println("########################## ThreadComandoShell - inicio da Thread de Convênios");
		System.out.println("################### Comando=" + comando);
		Runtime runtime = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = runtime.exec(comando);
			//pr.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line=buf.readLine())!=null) {
				System.out.println(line);
			}
			System.out.println("############# RESULTADO DA EXECUÇÃO: " + buf.toString());
			
		} catch (Exception e) {
			System.out.println("############### Erro ao EXECUTAR o SHELL: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void setComando(String sComando) {
		this.comando = sComando;
	}
}
