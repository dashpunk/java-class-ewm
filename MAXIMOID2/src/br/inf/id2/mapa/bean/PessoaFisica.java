package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;

/**
 *
 * @author Dyogo Dantas
 *
 */
public class PessoaFisica extends StatefulAppBean {


    /**
     *
     */
    public PessoaFisica() {
    }
    
    @Override
    public int SAVE() throws MXException, RemoteException {
    	// Valida se o CPF informado está sem Máscara. Se estiver, validar com o código IBGE
    	
        boolean semMascara = getMbo().getBoolean("MASEMMASCARA");
        System.out.println("############### Sem máscara? = " + semMascara);

        // Se estiver sem máscara e COM código de CEP
        String cep = getMbo().getString("ID2CEPCODE");
        System.out.println("############ CEP = " + cep);
        if (semMascara && (cep != null && !cep.equals(""))) {
        	String personId = getMbo().getString("PERSONID");
        	int tamanhoPerson = personId.length();
        	if (tamanhoPerson != 11) {
        		throw new MXApplicationException("pessoa", "personidDiferenteDeOnze");
        	}
        	
        	String codigoIBGEInf = personId.substring(0, 7);
        	String codigoIBGERel = getMbo().getMboSet("ID2ADDVWPER01").getMbo(0).getString("ID2CODMUN");
        	
        	System.out.println("################ Código IBGE Informado = " + codigoIBGEInf + "| Código IBGE Relacionado = " + codigoIBGERel);
        	if (codigoIBGERel != null && !codigoIBGERel.equals("")) {
        		if (!codigoIBGERel.equals(codigoIBGEInf)) {
        			throw new MXApplicationException("pessoa", "CodigoIBGEDiferente");
        		}
        	} else {
        		System.out.println("############### Relacionamento IBGE vazio");
        	}
        }
    	
    	return super.SAVE();
    }

}
