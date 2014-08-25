package ateste;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.inf.ctis.common.util.Extenso;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Double a = 1284281.50;
		Extenso extenso = new Extenso();
		extenso.setNumber(a);
		System.out.println(extenso.toString());

	}

}
