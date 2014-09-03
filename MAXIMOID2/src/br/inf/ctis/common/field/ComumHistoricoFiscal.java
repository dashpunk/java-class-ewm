package br.inf.ctis.common.field;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class ComumHistoricoFiscal {
	public HashMap<?, ?> initializeUseCase(MboRemote mbo) throws RemoteException, MXException {
		Map<String, List> map = new HashMap<String, List>();
		
		List<String> listNumPortaria = new ArrayList<String>();
		listNumPortaria.add(mbo.getString("MSALNUMPORTARIAFISCAL") == null ? "" : mbo.getString("MSALNUMPORTARIAFISCAL"));
		
		List<String> listNomFiscal = new ArrayList<String>();
		listNumPortaria.add(mbo.getString("MSALNOMFISCALCONTRATO") == null ? "" : mbo.getString("MSALNOMFISCALCONTRATO"));
		
		List<String> listNomFiscalSubstituto = new ArrayList<String>();
		listNumPortaria.add(mbo.getString("MSALNOMFISCALCONTRATOSUB") == null ? "" : mbo.getString("MSALNOMFISCALCONTRATOSUB"));
		
		map.put("MSALNUMPORTARIAFISCAL", listNumPortaria);
		map.put("MSALNOMFISCALCONTRATO", listNomFiscal);
		map.put("MSALNOMFISCALCONTRATOSUB", listNomFiscalSubstituto);
		
		return (HashMap<?, ?>) map;
	}
}
