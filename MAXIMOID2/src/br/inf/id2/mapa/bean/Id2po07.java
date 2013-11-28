package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
 * @author Ricardo S Gomes 53269192930020120121100002808
 */
public class Id2po07 extends AppBean {

	public Id2po07() {
		super();
	}

	@Override
//	public synchronized void reset() throws MXException {
	public void reset() throws MXException {
		try {

			super.reset();
			System.out.println("___ reset()");

			int registros = getMboSet().count();

			System.out.println("___ count " + getMboSet().count());

			System.out.println("___b1");
			try {
				System.out.println("___ valor pesquisado "
						+ getMboSet().getMbo().getString("ID2CODBARRA"));
			} catch (Exception e) {
				e.getStackTrace();
			}

			System.out.println("___b2");

			// WebClientEvent newEvent = new WebClientEvent("changeapp",
			// this.app.getId(),"receipts",null,null,null,selectedMbo.getUniqueIDValue(),this.clientSession);

			String aplicacao = "";
			long tabelaId = 0;
			if (registros == 0) {
				System.out.println("___if1");

				System.out.println("*** !encontrado ");
				System.out.println("*** RELPRG");

                

                System.out.println("*** antes");

                this.clientSession.loadDialog("naoencontrado");

                WebClientEvent event = sessionContext.getCurrentEvent();

                Utility.sendEvent(new WebClientEvent("naoencontrado", event.getTargetId(), event.getValue(), sessionContext));

                System.out.println("*** depois");

			} else if (registros > 1) {

				System.out.println("___if2");
				throw new MXApplicationException("recebimento", "resultadoMaiorQueZero");
			} else {
				System.out.println("___if3");
				aplicacao = getMboSet().getMbo(0).getString("MACODAPP");

				tabelaId = getMboSet().getMbo(0).getInt("GTAID");
			}
			System.out.println("___...");

			if (!aplicacao.equalsIgnoreCase("")) {
				WebClientEvent newEvent = new WebClientEvent("changeapp",
						this.app.getId(), aplicacao, null, null, null,
						tabelaId, this.clientSession);

				newEvent.setSourceControl(this.app);

				this.clientSession.queueEvent(newEvent);
			}

		} catch (RemoteException ex) {
			ex.getStackTrace();
		}

	}

	public int linkApp() throws MXException, RemoteException {

		String aplicacao = getMboSet().getMbo(0).getString("MACODAPP");

		int tabelaId = getMboSet().getMbo(getMboSet().getCurrentPosition())
				.getInt("GTAID");
		WebClientEvent newEvent = new WebClientEvent("changeapp",
				this.app.getId(), aplicacao, null, null, null, tabelaId,
				this.clientSession);

		newEvent.setSourceControl(this.app);

		this.clientSession.queueEvent(newEvent);

		return EVENT_HANDLED;
	}
}
