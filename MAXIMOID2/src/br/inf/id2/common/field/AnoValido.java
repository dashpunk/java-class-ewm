package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Dyogo
 */
public class AnoValido extends MboValueAdapter {

	public AnoValido(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void initValue() throws MXException, RemoteException {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		getMboValue().setValue(c.get(Calendar.YEAR));
		System.out.println("########### Definindo valor inicial: " + c.get(Calendar.YEAR));
		super.initValue();
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		String ano = getMboValue().getString();
		System.out.println("############## Verificando se o ano he valido: " + ano);
		if (ano != null && !ano.equals("")) {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				System.out.println("################ Ano hoje: " + calendar.toString());
				int iAno = Integer.parseInt(ano);
				System.out.println("############### Ano Campo: " + iAno);
				calendar.set(Calendar.YEAR, iAno);
				System.out.println("############### Data validada: " + calendar.toString());
			} catch (Exception e) {
				throw new MXApplicationException("data", "AnoInvalido");
			}
		} else {
			initValue();
			throw new MXApplicationException("data", "AnoDeveSerInformado");
		}
	}
}
