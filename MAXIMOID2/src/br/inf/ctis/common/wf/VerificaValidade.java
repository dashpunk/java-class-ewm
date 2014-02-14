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
 * Solicitado por Glaucio
 */

public class VerificaValidade implements CustomCondition  {

	public VerificaValidade() {
		super();
		System.out.println("* Verifica Lotes de Entrega *");
	}

	
	public boolean evaluateCondition(MboRemote mbo, Object arg1) throws MXException, RemoteException {

		System.out.println("applyCustomAction");

		MboRemote aMbo;

		int retorno = 0;
		
		if (mbo.getMboSet("MSLOTEAGEND").count() <= 0) {
			throw new MXApplicationException("msagend", "InformeLotesEntrega");
		} else {
			
			for (int i = 0; (aMbo = mbo.getMboSet("MSLOTEAGEND").getMbo(i)) != null; i++) {
				
				// valores
				
				Date DataEntrega = mbo.getDate("MSDATAENTREGA");
				Date DataValidade = aMbo.getDate("MSVALIDADE");
				Date DataFabricacao = aMbo.getDate("MSDATAFAB");

				System.out.println ("DataEntrega , DataValidade, DataFabricacao: " + DataEntrega + DataValidade + DataFabricacao);

				if (DataFabricacao.getTime() > DataValidade.getTime()){
					throw new MXApplicationException("msagend", "DataFabricaoMaiorDataValidade");
				}

				// Calculos
				
				int Dias1 = (int) ((DataEntrega.getTime() - DataValidade.getTime()) / (1000 * 60 * 60 * 24));
				int Dias2 = (int) ((DataValidade.getTime() - DataFabricacao.getTime()) / (1000 * 60 * 60 * 24));
				
				System.out.println("Diferença de Dias: Data de Entrega  -  Data de Validade = " + Dias1);
				System.out.println("Diferença de Dias: Data de Validade  -  Data de Fabricação = " + Dias2);
				
				int PercentDias = (100 * Dias1) / Dias2;
				
				System.out.println("Porcentagem: " + PercentDias);
				
				if (PercentDias > 30){
					System.out.println("Retorno: FALSE");
					retorno = retorno + 1;
				} else {
					System.out.println("Retorno: TRUE");
					retorno = retorno + 0;
				}
			}
		}

		System.out.println ("Retorno = " + retorno);
		
		if (retorno > 0){
			return false;
		} else {
			return true;
		}
		
		    }
	
    public String toWhereClause(Object arg0, MboSetRemote arg1) throws MXException, RemoteException {
        return "";
    }	
}
