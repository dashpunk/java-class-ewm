package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Dyogo
 * Classe responsável por calcular o valor de um débito diminuindo ao valor corrente.
 */

public class DebitoSaldoExploracao extends MboValueAdapter {

	public DebitoSaldoExploracao(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		debitaValor();
	}
	
	private void debitaValor() throws MXException, RemoteException {
		String valorSaldo = getMboValue().getMbo().getString("CURBAL");
		String valorDebito = getMboValue().getMbo().getString("MADEB");
		long valorTotal = 0;
		if (valorSaldo != null && valorDebito != null) {
			try {
				valorTotal = Long.parseLong(valorSaldo.replace(".", "")) - Long.parseLong(valorDebito.replace(".", ""));
			} catch (Exception e) {
				System.out.println("############# Erro: " + e.getMessage());
				throw new MXApplicationException("inventory", "ValorDebitoNaoNumerico");
			}
		}
		if (valorTotal < 0) {
			throw new MXApplicationException("inventory", "ValorMenorQueZero");
		} else {
			getMboValue().getMbo().setValue("NEWCURBAL", valorTotal);
		}
	}
}