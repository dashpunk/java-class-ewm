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
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe ValidaPosicaoCadDocPec versao 01");
	}
	
	public void validate() throws MXException, RemoteException {
		
				
		//Mascara de validacao
		String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println(">>>>>>>>>>>>>>>>>>>> valor antes do IF de posicao= " + getMboValue().getMbo().getString("MSPOSICAO"));
	  	System.out.println("########## valor antes do if: " + valor);
	        
	    System.out.println(">>>>>>>>>>>>>>>>>>>> validando mascara do ponto");
	    if (!getMboValue().getMbo().isNull("MSPOSICAO")) {	 
	    	
	    	System.out.println(">>>>>>>>>>>>>>>>>>>> tamanho do length: "+valor.length());
	    	if((valor.length() < 2)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do if de valor < 3");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.", valor, false));
	    	} else if((valor.length() < 3)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 5");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.#.", valor, false));
	    	//else if((valor.length() < 7 && valor.length() >= 5)){
	    	} else if((valor.length() < 4)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 7");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.#.#.", valor, false));
	    	} else if((valor.length() < 5)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 9");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.#.#.#.", valor, false));
	    	} else if((valor.length() < 6)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 11");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.#.#.#.#.", valor, false));
	    	} else{
	    		throw new MXApplicationException("pontoposicao", "MascaraPosicaoInvalida");
	    	}
	    	
	    	
	    }
		
		//MboSet mbosetPosicao = (MboSet) getMboValue().getMbo().getMboSet("MSVWCLAUSULAPEC");
        
        //System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na MSVWCLAUSULAPEC: "+mbosetPosicao.count());
        
        /*for (int i = 0; i < mbosetPosicao.count(); i++) {
           
                                          
	            if (getMboValue().getString().equals(mbosetPosicao.getMbo(i).getString("MSPOSICAO"))) {
	                
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do IF para validar a existencia da Posicao");
	            	                
	                
	                throw new MXApplicationException("genericaDoc", "PosicaoDocPecExiste");
	            }
	            else{
	            	getMboValue().getMbo().setValue("MSPOSICAO", mbosetPosicao.getMbo(i).getString("MSPOSICAO"));
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do else ja persistindo a Posicao");
	            }
        }*/
        
       

	}

 }
