package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;


/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class MatranseleMaprotocolo extends MboValueAdapter
{

	public MatranseleMaprotocolo(MboValue mbv) {
		super(mbv);
		System.out.println("----- MatranseleMaprotocolo");
	}
	
	@Override
	public void initValue() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.initValue();
		String valor = "TAI."+ Uteis.adicionaValorEsquerda(
				String.valueOf(getMboValue().getMbo().getInt("MATRANSELEID")),
				"0", 10);
		
		if (!getMboValue().getString().equalsIgnoreCase(valor)) {
			getMboValue().setValue(valor, MboConstants.NOACCESSCHECK);
		}
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		
		String valor = "TAI."+ Uteis.adicionaValorEsquerda(
				String.valueOf(getMboValue().getMbo().getInt("MATRANSELEID")),
				"0", 10);
		
		if (!getMboValue().getString().equalsIgnoreCase(valor)) {
			getMboValue().setValue(valor, MboConstants.NOACCESSCHECK);
		}
	}
	
}