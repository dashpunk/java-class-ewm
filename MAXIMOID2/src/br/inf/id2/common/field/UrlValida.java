package br.inf.id2.common.field;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import psdi.util.MXException;

/**
 * 
 * @author Dyogo Dantas
 *
 */

public class UrlValida extends MboValueAdapter {

    public UrlValida(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = getMboValue().getString();
        super.validate();
        String param[] = {valor};
        if (valor == null || valor.length() == 0) {
        	return;
        }
        
        String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(urlRegex); 
        try {
        	System.out.println("########### Validando URL: " + valor);
        	Matcher matcher = pattern.matcher(valor);
        	if (!matcher.matches()) {
        		throw new MXApplicationException("company", "UrlInvalida", param);
        	}
        } catch (Exception e) {
        	throw new MXApplicationException("company", "UrlInvalida", param);
        }
        
    }

}