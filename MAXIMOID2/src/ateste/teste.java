package ateste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.id2.Uteis;
import psdi.util.MXApplicationException;
import br.inf.ctis.common.util.Extenso;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date data;
		try {
			data = sdf.parse("01/" + "01/2016");
		
		System.out.println("########## Data = " + data);
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		
		System.out.println("########## meses = " + (1000 / 500));
		System.out.println("########## meses = " + (int) Math.round(1000 / 500));
		int meses = (int) Math.round(1000 / 500);
		
		System.out.println(c.getTime());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + meses);
		System.out.println("########## Data apos somar meses = " + c.getTime() + " -> " + sdf.format(c.getTime()).substring(3));
		
		String valor = sdf.format(c.getTime()).substring(4);
		
		if (valor.length() == 6) {
			valor = "0" + valor;
	    }
		
		System.out.println(valor);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
