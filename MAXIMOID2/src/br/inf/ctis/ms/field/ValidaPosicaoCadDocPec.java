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
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;

public class ValidaPosicaoCadDocPec extends MAXTableDomain {

	public ValidaPosicaoCadDocPec(MboValue mbv) {
		super(mbv);
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe ValidaPosicaoCadDocPec versao 03 ");
	}
	
	public void validate() throws MXException, RemoteException {
		
		if (getMboValue().isNull()) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> is null");
            return;
        }
		
		//Mascara de validacao
		//String valor = new String();
	    // valor = Uteis.getApenasNumeros(getMboValue().getString());
		String valor = getMboValue().getMbo().getString("MSPOSICAO");
	    System.out.println(">>>>>>>>>>>>>>>>>>>>valor = " + getMboValue().getMbo().getString("MSPOSICAO"));
	  	    
	    if ((valor.length() > 10)) {
	    	throw new MXApplicationException("tamposicao", "TamanhoPosicaoInvalido");
	    }
	    
	    System.out.println(">>>>>>>>>>>>>>>>>>>>validando mascara do ponto");
	    if ((!getMboValue().getString().substring(1, 1).equalsIgnoreCase(".")||(!getMboValue().getString().substring(3, 3).equalsIgnoreCase(".")))) {
	    	
	    	System.out.println(">>>>>>>>>>>>>>>>>>>>getMboValue().getString().substring(0, 1) = "+getMboValue().getString().substring(0, 1));
	    	throw new MXApplicationException("pontoposicao", "PontosInvalida");
	    }
		
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do validate");

		MboSet mbosetPosicao = (MboSet) getMboValue().getMbo().getMboSet("MSVWCLAUSULAPEC");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na MSVWCLAUSULAPEC: "+mbosetPosicao.count());
        
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
