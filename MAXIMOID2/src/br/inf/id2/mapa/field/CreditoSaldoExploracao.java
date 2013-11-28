package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 * Classe responsável por calcular o valor de um crédito somando ao valor corrente.
 */

public class CreditoSaldoExploracao extends MboValueAdapter {

	public CreditoSaldoExploracao(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		creditaValor();
	}
	
	private void creditaValor() throws MXException, RemoteException {
		String valorSaldo = getMboValue().getMbo().getString("CURBAL");
		String valorCredito = getMboValue().getMbo().getString("MACRE");
		long valorTotal = 0;
		if (valorSaldo != null && valorCredito != null) {
			try {
				valorTotal = Long.parseLong(valorSaldo.replace(".", "")) + Long.parseLong(valorCredito.replace(".", ""));
			} catch (Exception e) {
				System.out.println("############# Erro: " + e.getMessage());
				throw new MXApplicationException("inventory", "ValorCreditoNaoNumerico");
			}
		}
		getMboValue().getMbo().setValue("NEWCURBAL", valorTotal);
	}
}