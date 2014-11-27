package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;

public class ValidaPosicaoCadDocPec extends MboValueAdapter {

	public ValidaPosicaoCadDocPec(MboValue mbv) throws MXException {
		super(mbv);
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe ValidaPosicaoCadDocPec versao 00");
	}
	
	public void validate() throws MXException, RemoteException {
		
				
		//Mascara de validacao
		String valor = new String();
		String valor2 = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println(">>>>>>>>>>>>>>>>>>>> valor antes do IF de posicao= " + getMboValue().getMbo().getString("MSPOSICAO"));
	  	System.out.println("########## valor antes do if: " + valor);
	        
	    System.out.println(">>>>>>>>>>>>>>>>>>>> validando mascara do ponto");
	    if (!getMboValue().getMbo().isNull("MSPOSICAO")) {	
	    	
	    	MboSet mbosetPosicao = (MboSet) getMboValue().getMbo().getMboSet("MSVWCLAUSULAPEC");
	    	
	    	System.out.println(">>>>>>>>>>>>>>>>>>>> tamanho do length: "+valor.length());
	    	if((valor.length() < 2)){
	    		
	    		 for (int i = 0; i < mbosetPosicao.count(); i++) {
	    		 valor2 = Uteis.getApenasNumeros(mbosetPosicao.getMbo(i).getString("MSPOSICAO")); 
	    			  
	                 if (valor.equals(valor2)) {
	 	                
	 	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Posicao Existente, valor: "+valor2);
	 	            	                
	 	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
	 	            }
	    		 }
	    		getMboValue().setValue(Uteis.getValorMascarado("#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do if de valor < 2");
	    		
	    	} else if((valor.length() < 3)){
	    		
	    		for (int i = 0; i < mbosetPosicao.count(); i++) {
		    		 valor2 = Uteis.getApenasNumeros(mbosetPosicao.getMbo(i).getString("MSPOSICAO")); 
		    			  
		                 if (valor.equals(valor2)) {
			 	                
			 	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Posicao Existente, valor: "+valor2);
			 	            	                
			 	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
			 	            }
		                 
		    		 }
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 3");
	    		
	    	} else if((valor.length() < 4)){
	    		
	    		for (int i = 0; i < mbosetPosicao.count(); i++) {
		    		 valor2 = Uteis.getApenasNumeros(mbosetPosicao.getMbo(i).getString("MSPOSICAO")); 
		    			  
		                 if (valor.equals(valor2)) {
			 	                
			 	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Posicao Existente, valor: "+valor2);
			 	            	                
			 	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
			 	         }
		    	 }
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 4");
	    		
	    	} else if((valor.length() < 5)){
	    		
	    		for (int i = 0; i < mbosetPosicao.count(); i++) {
		    		 valor2 = Uteis.getApenasNumeros(mbosetPosicao.getMbo(i).getString("MSPOSICAO")); 
		    			 
		                 if (valor.equals(valor2)) {
			 	                
			 	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Posicao Existente, valor: "+valor2);
			 	            	                
			 	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
			 	            }
		    	 }
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 5");
	    		
	    	} else if((valor.length() < 6)){
	    		
	    		for (int i = 0; i < mbosetPosicao.count(); i++) {
		    		 valor2 = Uteis.getApenasNumeros(mbosetPosicao.getMbo(i).getString("MSPOSICAO")); 
		    			
		                 if (valor.equals(valor2)) {
			 	                
			 	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Posicao Existente, valor: "+valor2);
			 	            	                
			 	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
			 	            }
		    	 }
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 6");
	    		
	    	} else{
	    		throw new MXApplicationException("pontoposicao", "MascaraPosicaoInvalida");
	    	}
	    	
	    	
	    }
		
       

	}

 }
