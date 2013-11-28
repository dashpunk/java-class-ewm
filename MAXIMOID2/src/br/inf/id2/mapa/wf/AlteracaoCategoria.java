package br.inf.id2.mapa.wf;

import java.rmi.RemoteException;

import psdi.common.condition.CustomCondition;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class AlteracaoCategoria implements CustomCondition {

	public boolean evaluateCondition(MboRemote mbo, Object arg1)
			throws MXException, RemoteException {

		System.out.println("*** AlteracaoCategoria ***");

		MboSetRemote revAtual = mbo.getMboSet("MARL01CLAEST");
		MboSetRemote revAntiga = mbo.getMboSet("MARL02CLAEST");

		System.out.println("*** ID2REV.count() " + revAtual.count());
		System.out.println("*** ID2REV " + revAtual.getMbo(0).getInt("ID2REV"));
		int id2Rev = revAtual.getMbo(0).getInt("ID2REV");
		System.out.println("*** revAtual " + id2Rev);

		//if para viabilizar o teste do Seronni
		if(revAntiga.count()>0){
			if (id2Rev > 1) {
				System.out.println("*** id2Rev > 1");
				revAntiga.setWhere("ID2REV = " + (id2Rev - 1));
				revAntiga.reset();
				System.out.println("*** revAntiga " + revAntiga.count());
	
				System.out.println("*** point 1");
				String atualId2Cat = revAtual.getMbo(0).getString("ID2CAT");
				String atualId2CatId = revAtual.getMbo(0).getString("ID2CATID");
				String atualId2Esp = revAtual.getMbo(0).getString("ID2ESP");
	
				System.out.println("*** point 2");
				String antigoId2Cat = revAntiga.getMbo(0).getString("ID2CAT");
				System.out.println("*** point 2.1");
				String antigoId2CatId = revAntiga.getMbo(0).getString("ID2CATID");
				System.out.println("*** point 2.2");
				String antigoId2Esp = revAntiga.getMbo(0).getString("ID2ESP");
				System.out.println("*** point 3");
	
				if (atualId2Cat.equals(antigoId2Cat) && atualId2CatId.equals(antigoId2CatId) && atualId2Esp.equals(antigoId2Esp)) {
					System.out.println("*** return FALSE");
					return false;
				} else {
					System.out.println("*** return TRUE");
					return true;
				}
			} else {
				System.out.println("*** return FALSE 2");
				return false;
			}
		}else{
			System.out.println("*** return TRUE revAntiga < 1");
			return true;
		}
		
	}

	public String toWhereClause(Object arg0, MboSetRemote arg1)
			throws MXException, RemoteException {
		System.out.println("*** toWhereClause");
		return "";
	}
}