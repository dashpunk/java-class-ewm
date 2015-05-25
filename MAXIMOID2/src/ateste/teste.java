package ateste;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import psdi.id2.Uteis;
import psdi.util.MXApplicationException;
import br.inf.ctis.common.util.Extenso;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String valor = "42016";
		
	    System.out.println("###############valor = " + valor);
	
		if ((valor.length() < 5) || (valor.length() > 6)) {
            System.out.println("1");
		}
		
		if (valor.length() == 5) {
			valor = "0" + valor;
			System.out.println(valor);
	    }
		Calendar cAtual = Calendar.getInstance();        
		
		if (Integer.valueOf(cAtual.get(Calendar.YEAR)) < Integer.valueOf(valor.substring(2, 6)).intValue()) {
			System.out.println(Integer.valueOf(valor.substring(0, 2)).intValue());
		}
		

	}

}
