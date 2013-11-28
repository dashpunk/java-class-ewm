package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 *
 */

public class DataRecebimentoAnimais extends MboValueAdapter
{

	public DataRecebimentoAnimais(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	// Valida se a data de Recebimento (MADATARECEBE) é maior ou igual a data de emissão da GTA (ID2EMISSAO.CHANGEDATE)
	//e se é menor ou igual a data de hoje.
	private void define() throws MXException, RemoteException {
 
		Date dataReceb = getMboValue().getMbo().getDate("MADATARECEBE");
		System.out.println("####################Data de Recebimento: " + dataReceb);

		//Alterado dia 23/02/2011 por solicitação do Bruno Freitas
		//Date dataEmiss = getMboValue().getMbo().getDate("ORDERDATE");
		Date dataEmiss = getMboValue().getMbo().getMboSet("ID2EMISSAO").getMbo(0).getDate("CHANGEDATE");
		System.out.println("####################Data de Emissão: " + dataEmiss);
		Date dataAtual = new Date();
		System.out.println("####################Data Atual: " + dataAtual);
		
		
		if (Data.dataInicialMenorFinal(dataReceb, dataEmiss)) {
			throw new MXApplicationException("gta", "DataRecebimentoMenorDataEmissao");
		}
		
		if (Data.dataInicialMenorFinal(dataAtual, dataReceb)) {
			throw new MXApplicationException("gta", "DataRecebimentoMaiorDataAtual");
		}
		
	}
	
}