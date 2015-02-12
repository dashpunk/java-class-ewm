package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class BaixaEstoqueGuiaRemessa implements ActionCustomClass {

	public BaixaEstoqueGuiaRemessa() {
		super();
		System.out.println("########## Baixa Estoque via Guia de Remessa ");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		MboRemote mbo1;
		
		for (int i = 0; ((mbo1 = mbo.getMboSet("MSTBMEDGUIA").getMbo(i)) != null); i++){
			
			if(mbo1.getString("MSALNTIPOATENDIMENTO").length() > 0) {
				
				System.out.println("########## Entrou Medicamento da Guia de ID / CATMAT: " + mbo1.getInt("MSTBMEDGUIAID") + " / " + mbo1.getString("CATMAT"));
				
				if(mbo1.getString("MSALNTIPOATENDIMENTO").equalsIgnoreCase("ESTOQUE")) {
					
					System.out.println("########## Medicamento da Guia com TIPOATENDIMENTO: ESTOQUE ! " + mbo1.getString("MSALNTIPOATENDIMENTO"));
					
					double valor = 0d;
					double valortotal = 0d;
					System.out.println("########## mbo1.getMboSet(MSTBMEDALMOX).getMbo(0).getDouble(MSNUMQNTRESERV) - mbo1.getDouble(MSQTD) = " + (mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).getDouble("MSNUMQNTRESERV") - mbo1.getDouble("MSQTD")));
					System.out.println("########## mbo1.getMboSet(MSTBMEDALMOX).getMbo(0).getDouble(MSNUMQNTTOTAL) - mbo1.getDouble(MSQTD) = " + (mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).getDouble("MSNUMQNTTOTAL") - mbo1.getDouble("MSQTD")));
					valor = mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).getDouble("MSNUMQNTRESERV") - mbo1.getDouble("MSQTD");
					valortotal = mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).getDouble("MSNUMQNTTOTAL") - mbo1.getDouble("MSQTD");
					System.out.println("########## valor = " + valor);
					System.out.println("########## valortotal = " + valortotal);
					
					if (valor >= 0) {
						mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).setValue("MSNUMQNTRESERV", valor);
						mbo1.getMboSet("MSTBMEDALMOX").getMbo(0).setValue("MSNUMQNTTOTAL", valortotal);
					} else {
						throw new MXApplicationException("guiaremessa", "QuantidadeReservadaInsuficiente");
					}

					
				} else {
					System.out.println("########## Medicamento da Guia com TIPOATENDIMENTO: NAO ESTOQUE ! " + mbo1.getString("MSALNTIPOATENDIMENTO"));
				}
				
			} else {
				System.out.println("########## Entrou Medicamento da Guia com TIPOATENDIMENTO nulo ou vazio");
			}
						
		}
		
		mbo.getMboSet("MSTBMEDGUIA").save(MboConstants.NOACCESSCHECK);
		mbo.getMboSet("MSTBMEDALMOX").save(MboConstants.NOACCESSCHECK);
		
	}

}
