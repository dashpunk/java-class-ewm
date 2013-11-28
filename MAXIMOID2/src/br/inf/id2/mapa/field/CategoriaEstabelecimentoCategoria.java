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

public class CategoriaEstabelecimentoCategoria extends MboValueAdapter {

	public CategoriaEstabelecimentoCategoria(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		verificaCategoria();
	}
	
	private void verificaCategoria() throws MXException, RemoteException {
		System.out.println("#################### ENTREI NA ACTION");
    	String sEspecie = getMboValue().getMbo().getString("ID2ESP");
    	String sCategoria = getMboValue().getMbo().getString("ID2CAT");
		System.out.println("####################### Categoria = " + sCategoria);
		
		MboSetRemote mboSR = getMboValue().getMbo().getThisMboSet();
        //Quantidade só pode ser 1, pois encontrou uma vez o registro
        int qtdCategoria = 0;
        if (sCategoria != null && !sCategoria.equals("")) {
	        for (int i=0; i < mboSR.count(); i++) {
        		String sCategoriaNew = mboSR.getMbo(i).getString("ID2CAT");
        		String sEspecieNew = mboSR.getMbo(i).getString("ID2ESP");
        		System.out.println("################# ESPECIE = " + sEspecieNew + " | CATEGORIA = " + sCategoriaNew);
        		if (sCategoria.equals(sCategoriaNew) && sEspecie.equals(sEspecieNew)) {
        			qtdCategoria++;
        		}
	        }
	        System.out.println("################ QUANTIDADE CAT =" + qtdCategoria);
	        if (qtdCategoria > 1) {
	        	throw new MXApplicationException("vistoria", "CategoriaNaoPodeSerIgual");
	        }
        }		
	}
}