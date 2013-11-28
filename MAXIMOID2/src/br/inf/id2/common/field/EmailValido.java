package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo Dantas
 *
 */

public class EmailValido extends MboValueAdapter {

    public EmailValido(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = getMboValue().getString();
        super.validate();
        
        if (valor == null || valor.length() == 0) {
        	return;
        }
        
        String param[] = {valor};
        
        String urlRegex = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(urlRegex); 
        try {
        	System.out.println("########### Validando Email: " + valor);
        	Matcher matcher = pattern.matcher(valor);
        	if (!matcher.matches()) {
        		throw new MXApplicationException("company", "EmailInvalido", param);
        	}
        } catch (Exception e) {
        	throw new MXApplicationException("company", "EmailInvalido", param);
        }
    }

}