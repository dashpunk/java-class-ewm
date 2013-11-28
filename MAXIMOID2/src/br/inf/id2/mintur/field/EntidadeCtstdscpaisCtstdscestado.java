package br.inf.id2.mintur.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick Silva
 */
public class EntidadeCtstdscpaisCtstdscestado extends MboValueAdapter {

    public EntidadeCtstdscpaisCtstdscestado(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** EntidadeCtstdscpaisCtstdscestado ***");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

//        System.out.println("*** validate");
        
        String campoAtual = getMboValue().getName();
//        System.out.println("*** campoAtual "+campoAtual);
        
        if(campoAtual.equals("CTSTDSCPAIS")){
//        	System.out.println("*** CTSTDSCPAIS - limpa estado e cidade");
        	getMboValue("CTSTDSCESTADO").setValueNull();
        	getMboValue("CTSTDSCCIDADE").setValueNull();
        }else if(campoAtual.equals("CTSTDSCESTADO")){
//        	System.out.println("*** CTSTDSCESTADO - limpa cidade");
        	getMboValue("CTSTDSCCIDADE").setValueNull();
        }
    }
}
