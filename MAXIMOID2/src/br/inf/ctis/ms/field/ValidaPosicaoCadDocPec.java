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
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println(">>>>>>>>>>>>>>>>>>>> valor antes do IF de posicao= " + getMboValue().getMbo().getString("MSPOSICAO"));
	  	    
	        
	    System.out.println(">>>>>>>>>>>>>>>>>>>> validando mascara do ponto");
	    if (!getMboValue().getMbo().isNull("MSPOSICAO")) {	 
	    	
	    	if((valor.length() < 3)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 3");
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor mascarado"+Uteis.getValorMascarado("#.", valor, false));
	    	}
	    	if((valor.length() < 5)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 5");
	    	}
	    	if((valor.length() < 7)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 7");
	    	}
	    	if((valor.length() < 9)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 9");
	    	}
	    	if((valor.length() < 11)){
	    		getMboValue().setValue(Uteis.getValorMascarado("#.#.#.#.#.", valor, false));
	    		System.out.println(">>>>>>>>>>>>>>>>>>>> valor < 11");
	    	}
	    	else{
	    	
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
