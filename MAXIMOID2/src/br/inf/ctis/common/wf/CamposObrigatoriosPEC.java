/*
 * Verifica Validade dos Lotes com a Data de Entrega
 * se ultrapassa os 30%
 */
package br.inf.ctis.common.wf;

import java.util.Date;

import java.rmi.RemoteException;
import psdi.common.condition.CustomCondition;
import psdi.mbo.*;
import psdi.util.*;

/*
 * Criada por Willians.Andrade
 * Data 10/02/2014
 * Versao 1: Verifica Se Campos Obrigatorios do PEC foram Preenchidos - Somente Atributos da PO
 */

public class CamposObrigatoriosPEC implements CustomCondition  {
	
	String camposObrigatorios = "";

	public CamposObrigatoriosPEC() {
		super();
		System.out.println("* Verifica Campos Obrigatórios da PO *");
	}

	
	public boolean evaluateCondition(MboRemote mbo, Object arg1) throws MXException, RemoteException {

		System.out.println("applyCustomAction");

		MboRemote aMbo;

		
		int retorno = 0;
		
		// Se Existir Campos Obrigatórios
		if (mbo.getMboSet("MSTBPEC_CAMPOS").count() > 0) {
		
			for (int i = 0; (aMbo = mbo.getMboSet("MSTBPEC_CAMPOS").getMbo(i)) != null; i++) {
				
				System.out.println("Campo Obrigatório " + i + ":" + aMbo.getString("MSALNCAMPOS"));
				
				String Campo = aMbo.getString("MSALNCAMPOS");
				String DescCampo = aMbo.getString("DESCRIPTION");
				
				if (mbo.isNull(Campo)){
					setResultadoCampoObrigatorio(DescCampo);
					retorno++;
				}
			}
		}
		
		if (retorno > 0){
			
	        if (!camposObrigatorios.equals("")) {
 		            throw new MXApplicationException("ms_clpo01", "camposObrigatorios", new String[]{camposObrigatorios});
	        }
			
		} else {
			return true;
		}
		
		return false;
		
		    }
	
    private void setResultadoCampoObrigatorio(String string) {
        if (string.equals("")) {
            camposObrigatorios = "";
        } else {
            if (camposObrigatorios.indexOf(string) == -1) {
                camposObrigatorios += "\n" + string;
            }
        }
    }	
	
    public String toWhereClause(Object arg0, MboSetRemote arg1) throws MXException, RemoteException {
        return "";
    }	
}
