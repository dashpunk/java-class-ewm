package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Dyogo Dantas
 *  
 */
public class NumeroMascaraAnoMaiorAtual extends MboValueAdapter {

    public NumeroMascaraAnoMaiorAtual(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        String valor = getMboValue().getString();
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
        	
        	//Ano atual
        	Calendar cAtual = Calendar.getInstance();
        	
        	//Ano do Campo
        	Calendar cValor = Calendar.getInstance();
        	cValor.set(Calendar.MONTH, Integer.parseInt(datas[0]));
        	cValor.set(Calendar.YEAR, Integer.parseInt(datas[1]));
        	System.out.println("############# Datas - Atual: " + cAtual + " | Campo: " + cValor.toString());
        	
        	if (Data.dataInicialMenorFinal(cValor.getTime(), cAtual.getTime())) {
        		throw new MXApplicationException("data", "DataMenorQueAtual");
        	}
        }
    }
}
