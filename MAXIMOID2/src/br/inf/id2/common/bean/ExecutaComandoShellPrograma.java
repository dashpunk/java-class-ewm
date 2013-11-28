package br.inf.id2.common.bean;

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
public class ExecutaComandoShellPrograma extends DataBean {

	public ExecutaComandoShellPrograma() {
		super();
		System.out.println("#################### ExecutaComandoShellPrograma - Construtor");
	}

	public void executa() throws MXException, RemoteException {
		System.out.println("########################## ExecutaComandoShellPrograma - EXECUTA()");

		String parametro = app.getDataBean("MAINRECORD").getMbo().getString("ITPROID");
		System.out.println("############# PARAMETRO=" + parametro);
		Properties prop = MXServer.getMXServer().getConfig();
		String sComando = prop.getProperty("mxe.shell.command.prog", "C:\\relatorios\\comando.bat");
		sComando += " " + parametro;

		sessionContext.queueRefreshEvent();
		WebClientEvent event = sessionContext.getCurrentEvent();
		
		try {
			ThreadComandoShell thread = new ThreadComandoShell();
			thread.setComando(sComando);
			thread.start();
			Utility.showMessageBox(event, new MXApplicationException("process", "ProcessoProgramaExecutadoComSucesso", new String[]{parametro}));
		} catch (Exception e) {
			System.out.println("############### Erro ao iniciar Thread do Shell: " + e.getMessage());
			Utility.showMessageBox(event, new MXApplicationException("process", "ProcessoThreadComErro"));
			e.printStackTrace();
		}
		
		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));

	}
}
