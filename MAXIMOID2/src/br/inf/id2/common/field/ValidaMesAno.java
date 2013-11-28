package br.inf.id2.common.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Leysson Barbosa Moreira
 * 
 */
public class ValidaMesAno extends MboValueAdapter {

    public ValidaMesAno(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("---- ValidaMesAno validate()");
        super.validate();
        
        String campo = getMboValue().getString();
        
        String[] valores = campo.split("/");
        
        if(campo.indexOf("/") ==  -1) {
        	throw new MXApplicationException("ID2", "MesAnoInvalido");
        }
        
        
        if(valores.length != 2) {
        	throw new MXApplicationException("ID2", "MesAnoInvalido");
        }
        
        if(Integer.valueOf(valores[0]) < 1 || Integer.valueOf(valores[0]) > 12) {
        	throw new MXApplicationException("ID2", "MesAnoInvalido");
        }
        
        if(valores[0].length() != 2 || valores[1].length() != 4) {
        	throw new MXApplicationException("ID2", "MesAnoInvalido");
        }

    }
    
    
}
