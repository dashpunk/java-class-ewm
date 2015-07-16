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
		
		String valor = "10/2016";
        String novoValor;
        String mascara="";
		
		valor = valor.replaceAll("/", "");
    	
    	
		for (int i = 0; i < valor.length() - 4; i++){
    		mascara = mascara + 9;
    	}
    	
    	mascara = mascara + "/9999"; //numero + ano.
    	
    	novoValor = Uteis.getValorMascarado(mascara, valor, false);
    	System.out.println("NumeroMascaraAno.validate = " + novoValor);
    	
   
    	
    	String[] datas = novoValor.split("/");
    	if (Integer.parseInt(datas[0]) > 12) {
    		System.out.println("dataCampoMesAnoInvalido");
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	Date data = null;
		try {
			data = sdf.parse("01/" + novoValor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("########## Data = " + data);
		
		Calendar cAtual = Calendar.getInstance();
		Calendar cValor = Calendar.getInstance();
		cValor.setTime(data);
    	
    	System.out.println("############# Datas - Atual: " + cAtual.getTime() + " | Campo: " + cValor.getTime());
    		        	
    	if (Data.dataInicialMenorFinal(cValor.getTime(), cAtual.getTime())) {
    		System.out.println(cValor.getTime().getMonth() +" e "+ cAtual.getTime().getMonth());
    		if (cValor.getTime().getMonth() < cAtual.getTime().getMonth()) {
    			System.out.println("menor");
    		}
    		
    	}
    	try {
			
				
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			
			Date data1 = sdf2.parse("01/" + "10/2016");
			System.out.println("########## Data = " + data1);
			
			Calendar c = Calendar.getInstance();
			c.setTime(data1);
							
			System.out.println("########## meses = " + (973836 / 81153));
			System.out.println("########## meses = " + (int) Math.round(973836 / 81153));
			int meses = (int) Math.round(973836 / 81153);
			
			
			System.out.println("########## Data antes de somar meses = " + c.getTime() + " -> " + sdf2.format(c.getTime()).substring(3));
			c.add(Calendar.MONTH, meses);
			System.out.println("########## Data apos somar meses = " + c.getTime() + " -> " + sdf2.format(c.getTime()).substring(3));
			
			String valor1 = sdf2.format(c.getTime()).substring(3);
			
			if (valor1.length() == 6) {
				valor1 = "0" + valor1;
		    }
							
			System.out.println(valor1);;
			
				
				
				
			
					
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
