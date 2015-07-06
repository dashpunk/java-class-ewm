package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class AlertaPendenciasEnvioPR implements ActionCustomClass {

    public AlertaPendenciasEnvioPR() {
        super();
        System.out.println("*** AlertaPendenciasEnvioPR ***");
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	int qtdCatmatSemEntregas = 0;
    	int qtdCatmatFaltaEntregas = 0;
    	String catmatSemEntregas = "";
    	String catmatFaltaEntregas = "";
    	int qtdCatmatFaltaDistribuicoes = 0;
    	String catmatFaltaDistribuicoes = "";
    	
    	try {
    				    		   		
    		if(mbo.getString("MSALCODSITEID").equalsIgnoreCase("CGGPL")) {
				//-------------------------------------------------------------------PREVISAO DE ENTREGA
				
				//String catmatSemEntregas = "";
				//int qtdCatmatSemEntregas = 0;
				//String catmatFaltaEntregas = "";
				//int qtdCatmatFaltaEntregas = 0;
				MboRemote itemEntrega;
				    		
				for (int i = 0; ((itemEntrega = mbo.getMboSet("PRLINEENTREGA").getMbo(i)) != null); i++) {
					
					if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
						
						int countEntregas = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").count();
						System.out.println("########## countEntregas: " + countEntregas);
						
						if (countEntregas < 1) {
							qtdCatmatSemEntregas++;
							catmatSemEntregas += " \n" + itemEntrega.getString("ID2ITEMNUM");
							//throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega");
						} else {
						
							MboRemote mbo1;
							int contador = 0;
							
							for (int j = 0; ((mbo1 = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
								if (!mbo1.toBeDeleted()) {
									contador++;
								}
							}
							System.out.println("########## contador: " + contador);
							
							if (contador < 1) {
								qtdCatmatSemEntregas++;
								catmatSemEntregas += " \n" + itemEntrega.getString("ID2ITEMNUM");
								//throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega");
							}
							
						}
					} else if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("NAO")) {
		    					
						Double valor = 0d;
						MboRemote mbo1;
						
						for (int j = 0; ((mbo1 = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
							
							System.out.println("########## Data: " + mbo1.getString("MSALDTAENTREGA") + " ########## Quantidade: " + mbo1.getDouble("MSNUNUMQUANTIDADE"));
							valor += mbo1.getDouble("MSNUNUMQUANTIDADE");
							
							System.out.println("########## valor: " + valor);
						}
									        
				        if (valor != itemEntrega.getDouble("ORDERQTY")) {
				        	qtdCatmatFaltaEntregas++;
				        	catmatFaltaEntregas += " \n" + itemEntrega.getString("ID2ITEMNUM");
				        	//throw new MXApplicationException("entrega", "FaltaEntregas");
				        }
		
					} 
				}
				
				//if (qtdCatmatSemEntregas > 0) {
					//getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOACCESSCHECK);
					//throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega", new String[]{catmatSemEntregas});
				//}
				
				//if (qtdCatmatFaltaEntregas > 0) {
					//getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOACCESSCHECK);
					//throw new MXApplicationException("entrega", "FaltaEntregas", new String[]{catmatFaltaEntregas});
				//}
				//-------------------------------------------------------------------PREVISAO DE ENTREGA
				
				//-------------------------------------------------------------------PREVISAO DE DISTRIBUICAO
				
				//String catmatFaltaDistribuicoes = "";
				//int qtdCatmatFaltaDistribuicoes = 0;
				MboRemote itemDistribuicao;
				    		
				for (int i = 0; ((itemDistribuicao = mbo.getMboSet("PRLINEDISTRIBUICAO").getMbo(i)) != null); i++) {
					
					//if (itemDistribuicao.getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
						//if (itemDistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").count() < 1) {
							//throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaDistribuicao");
						//}
					//} else
					if (itemDistribuicao.getString("ID2DISTDIRETA").equalsIgnoreCase("SIM")) {
		    					
						Double valor = 0d;
						MboRemote mbo1;
						
						for (int j = 0; ((mbo1 = itemDistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(j)) != null); j++) {
							
							System.out.println("########## Data: " + mbo1.getString("MSALDTADISTRIBUICAO") + " ########## Quantidade: " + mbo1.getDouble("MSNUNUMQUANTIDADE"));
							valor += mbo1.getDouble("MSNUNUMQUANTIDADE");
							
							System.out.println("########## valor: " + valor);
						}
				        
				        if (valor != itemDistribuicao.getDouble("ORDERQTY")) {
				        	qtdCatmatFaltaDistribuicoes++;
				        	catmatFaltaDistribuicoes += " \n" + itemDistribuicao.getString("ID2ITEMNUM");
				        	//throw new MXApplicationException("distribuicao", "FaltaDistribuicoes");
				        }
		
					} 
				}
				
				//if (qtdCatmatFaltaDistribuicoes > 0) {
					//throw new MXApplicationException("distribuicao", "FaltaDistribuicoes", new String[]{catmatFaltaDistribuicoes});
				//}
				//-------------------------------------------------------------------PREVISAO DE DISTRIBUICAO
			}
			
			if (qtdCatmatSemEntregas > 0) {
				throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega", new String[]{catmatSemEntregas});
			}
			
			if (qtdCatmatFaltaEntregas > 0) {
				throw new MXApplicationException("entrega", "FaltaEntregas", new String[]{catmatFaltaEntregas});
			}
			
			if (qtdCatmatFaltaDistribuicoes > 0) {
				throw new MXApplicationException("distribuicao", "FaltaDistribuicoes", new String[]{catmatFaltaDistribuicoes});
			}
    	} catch (RemoteException e) {
			e.printStackTrace();	
    	}
    	
    }


}
