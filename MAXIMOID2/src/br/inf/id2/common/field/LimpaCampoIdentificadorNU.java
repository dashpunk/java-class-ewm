package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Davi Dias
 *  
 */
public class LimpaCampoIdentificadorNU extends MboValueAdapter {

    public LimpaCampoIdentificadorNU(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        String campoAtual = getMboValue().getName();

        if (getMboValue().getMbo().getString(campoAtual).length() == 0) {
        	
        	String nomeCampo = campoAtual.substring(7, campoAtual.length());
        	String nomeProduto = campoAtual.substring(0, 2);
        	System.out.println("########### nomeCampo = " + nomeCampo + "| nomeProduto = " + nomeProduto);
        	
        	getMboValue().getMbo().setValueNull(nomeProduto + "NUCOD" + nomeCampo);
          }

    }
}
