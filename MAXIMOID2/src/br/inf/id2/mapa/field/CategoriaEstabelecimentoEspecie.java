package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 */

public class CategoriaEstabelecimentoEspecie extends MboValueAdapter {

	public CategoriaEstabelecimentoEspecie(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		verificaEspecie();
	}
	
	private void verificaEspecie() throws MXException, RemoteException {
		System.out.println("#################### ENTREI NA ACTION");
    	String sEspecie = getMboValue().getMbo().getString("ID2ESP");
		System.out.println("####################### Especie = " + sEspecie);
		
		MboSetRemote mboSR = getMboValue().getMbo().getThisMboSet();
        //Quantidade só pode ser 1, pois encontrou uma vez o registro
        int qtdEspecie = 0;
        if (sEspecie != null && !sEspecie.equals("")) {
	        for (int i=0; i < mboSR.count(); i++) {
        		String sEspecieNew = mboSR.getMbo(i).getString("ID2ESP");
        		System.out.println("################### ESPECIE = " + sEspecieNew);
        		if (sEspecie.equals(sEspecieNew)) {
        			qtdEspecie++;
        		}
	        }
	        System.out.println("################ QUANTIDADE ESP =" + qtdEspecie);
	        if (qtdEspecie > 1) {
	        	throw new MXApplicationException("vistoria", "EspecieNaoPodeSerIgual");
	        }
        }		
	}
}