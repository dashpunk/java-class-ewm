package br.inf.ctis.ms.field;

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

public class PrevisaoCoberturaNovaAquisicao extends MboValueAdapter{
	
	public PrevisaoCoberturaNovaAquisicao(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		if (getMboValue().getName().equals("ID2PRIENT")) {
			String valor;
			valor = getMboValue().getString();
	        String novoValor;
	        String mascara="";
	        
	        if (valor != null) {
	        	try {
	        		String valorSemBarra = valor.replaceAll("/", "");
	        		long lValor = Long.parseLong(valorSemBarra);
	        	} catch (Exception e) {
	        		throw new MXApplicationException("data", "CampoMesAnoInvalido");
	        	}
	        }
	        
	        if(valor != null && valor.length() > 4){
	        	
	        	valor = valor.replaceAll("/", "");
	        	
	        	for (int i = 0; i < valor.length() - 4; i++){
	        		mascara = mascara + 9;
	        	}
	        	
	        	mascara = mascara + "/9999"; //numero + ano.
	        	
	        	novoValor = Uteis.getValorMascarado(mascara, valor, false);
	        	System.out.println("NumeroMascaraAno.validate = " + novoValor);
	        	
	        	if(valor.indexOf("/") == -1)
	        		getMboValue().setValue(novoValor);
	        	
	        	if (novoValor.length() != 7) {
	        		throw new MXApplicationException("data", "CampoMesAnoInvalido");
	        	}
	        	
	        	String[] datas = novoValor.split("/");
	        	if (Integer.parseInt(datas[0]) > 12) {
	        		throw new MXApplicationException("data", "CampoMesAnoInvalido");
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
	        		System.out.println("############# MES - Atual: " + cAtual.getTime().getMonth() + " | Campo: " + cValor.getTime().getMonth());
	        		if (cValor.getTime().getMonth() < cAtual.getTime().getMonth()) {
	        			throw new MXApplicationException("data", "DataMenorQueAtual");
	        		}
	        	}
	        
	        }
		}
		
		try {
			if (!getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").isNull()) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				Date data = sdf.parse("01/" + getMboValue("ID2PRIENT").getString());
				System.out.println("########## Data = " + data);
				
				Calendar c = Calendar.getInstance();
				c.setTime(data);
								
				System.out.println("########## meses = " + (getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble()));
				System.out.println("########## meses = " + (int) Math.round(getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble()));
				int meses = (int) Math.round(getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble());
				
				
				System.out.println("########## Data antes de somar meses = " + c.getTime() + " -> " + sdf.format(c.getTime()).substring(3));
				c.add(Calendar.MONTH, meses);
				System.out.println("########## Data apos somar meses = " + c.getTime() + " -> " + sdf.format(c.getTime()).substring(3));
				
				String valor = sdf.format(c.getTime()).substring(3);
				
				if (valor.length() == 6) {
					valor = "0" + valor;
			    }
								
				getMboValue("MSALDTAPREVISAOCOBERTURA").setValue(valor);
				
				
			}
					
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



