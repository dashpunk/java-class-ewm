package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import br.inf.id2.common.util.Uteis;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Dyogo
 */
public class GeraDigitoVerificador extends MboValueAdapter {

	public GeraDigitoVerificador(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
//	public static void main(String srg[]) {
		
		
		String numalfa;
		int soma;
		int mult;
		String numeroCampo = getMboValue().getString();
		System.out.println("########### Numero Campo = " + numeroCampo);
		
		if (numeroCampo == null) {
			return;
		}
		
		numeroCampo = Uteis.retiraCaracteresEspeciais(numeroCampo);
		
		
		if (numeroCampo.length() < 15) {
			throw new MXApplicationException("company", "CampoNumeroVerificadorMenor15");
		}
		
	    if (numeroCampo.substring(11, 13).equals("19")) {
	        numalfa = numeroCampo.substring(0, 11) + numeroCampo.substring(13, 15);
	    }
	    else {
	        numalfa = numeroCampo.substring(0, 15);
	    }

	    System.out.println("########### Numero Alfa = " + numalfa);
	    for (int i=1;i<3;i++) {

	        soma = 0; 
	        mult = numalfa.length();

	        for (int j=1; j<(mult+1); j++) {
	            soma = soma + Integer.parseInt((numalfa.substring(j-1, j))) * (mult - j + 2);
	        }

	        soma = 11 - (soma % 11);

	        if (soma > 9) {
	            soma = soma - 10;
	        }

	        numalfa = numalfa + soma;
	    }
	    System.out.println("####### Numero Alfa depois = " + numalfa);

	    if (numeroCampo.substring(11, 13).equals("19")) {
	        numalfa = numalfa.substring(0, 11) + "19" + numalfa.substring(11, 15);
	    }
	    String numFormatado = Uteis.getValorMascarado("#####.######/####-##", numalfa, true);
	    System.out.println(numFormatado);
	    
	    getMboValue().setValue(numFormatado);

	}
}
