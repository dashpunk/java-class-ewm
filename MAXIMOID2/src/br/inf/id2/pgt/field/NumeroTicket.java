package br.inf.id2.pgt.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.app.ticket.FldTkTicketId;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Dyogo
 *
 */

public class NumeroTicket extends FldTkTicketId
{

	
	public NumeroTicket(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	private void define() throws MXException {
        try {
        	System.out.println("################# Valor do campo antes..." + getMboValue().getMbo().getString("TICKETID"));
        	String ticket = getMboValue().getMbo().getString("TICKETID");
        	if (ticket.indexOf("/") == -1) {
	        	String valor = new GregorianCalendar().get(GregorianCalendar.YEAR) +
	        				   "/" +
	        				   Uteis.adicionaValorEsquerda(ticket, "0", 6);
	        	System.out.println("############### Valor=" + valor);
	        	getMboValue().getMbo().setValue("TICKETID",valor);
        	} else {
        		String ano = ticket.substring(0, ticket.indexOf("/"));
        		int numeroSeq = Integer.parseInt(ticket.substring(ticket.indexOf("/")+1, ticket.length()));
        		System.out.println("########## Ano = " + ano + "|Numero =" + numeroSeq);
        		String valor = ano + "/" + Uteis.adicionaValorEsquerda(numeroSeq+"", "0", 6);
        		System.out.println("############# Valor:" + valor);
        		getMboValue().getMbo().setValue("TICKETID",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de TICKETID: " + re.getMessage());
        }
	}
	
}