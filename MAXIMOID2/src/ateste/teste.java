package ateste;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String valor = new String();
	    valor = Uteis.getApenasNumeros("12/2016");
	    
	    System.out.println("###############valor = " + valor);
	
		if ((valor.length() < 5) || (valor.length() > 6)) {
			System.out.println("genericaMascaraInvalida");
		}
		
		if (valor.length() == 5) {
			valor = "0" + valor;
	    }
		
		System.out.println("########## Mes: " + Integer.valueOf(valor.substring(0, 2)).intValue());
		if (Integer.valueOf(valor.substring(0, 2)).intValue() > 12 || Integer.valueOf(valor.substring(0, 2)).intValue() < 1) {
			System.out.println("genericaMesInvalido");
		}
	    
	    Calendar cAtual = Calendar.getInstance();        
	
	    System.out.println(Integer.valueOf(cAtual.get(Calendar.YEAR)) + "  1111111 " + Integer.valueOf(valor.substring(2, 6)).intValue());
		if (Integer.valueOf(cAtual.get(Calendar.YEAR)) < Integer.valueOf(valor.substring(2, 6)).intValue()) {
			
			System.out.println(Integer.valueOf(cAtual.get(Calendar.MONTH)+1) + "  22222222 " + Integer.valueOf(valor.substring(0, 2)).intValue());
			if(Integer.valueOf(cAtual.get(Calendar.MONTH)+1) <= Integer.valueOf(valor.substring(0, 2)).intValue()) {
				
				System.out.println(Uteis.getValorMascarado("##/####", valor, false));
			} else {
				System.out.println("genericaMesInvalido");
			}
			System.out.println(Integer.valueOf(cAtual.get(Calendar.YEAR))+ "  3333333 " + Integer.valueOf(valor.substring(2, 6)).intValue());
		} else if (Integer.valueOf(cAtual.get(Calendar.YEAR)) == Integer.valueOf(valor.substring(2, 6)).intValue()) {
			
			System.out.println(Integer.valueOf(cAtual.get(Calendar.MONTH)+1) + "  44444444 " + Integer.valueOf(valor.substring(0, 2)).intValue());
			if(Integer.valueOf(cAtual.get(Calendar.MONTH)+1) <= Integer.valueOf(valor.substring(0, 2)).intValue()) {
				
				System.out.println(Uteis.getValorMascarado("##/####", valor, false));
			} else {
				System.out.println("genericaMesInvalido");
			}
		} else {
			System.out.println("genericaAnoInvalido");
	    }
		
	}
}
