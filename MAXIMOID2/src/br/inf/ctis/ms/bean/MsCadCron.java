/**
 * Aplicação de Cadastro de Cronogramas da SVS
 * Solicitado por Glaucio Fernandes
 * 
 * Ao Entrar no aplicativo caso não tenha responsável habilitar com o personid logado.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * @author willians.andrade
 * 
 */
public class MsCadCron extends AppBean {
	
	public MsCadCron() {
		System.out.print("CTIS # --- Entrou na Classe MsCadCron");
	}
	
	@Override
    public int SAVE() throws MXException, RemoteException {
		
		String PersonID = sessionContext.getUserInfo().getPersonId();
		System.out.print("CTIS # --- Capturado PERSONID " + PersonID);
		
		if (getMbo().isNull("MSRESPCRON")){
			System.out.print("CTIS # --- Setado valor do PERSONID");
			getMbo().setValue("MSRESPCRON", PersonID, MboConstants.NOACCESSCHECK);
		}
        return super.SAVE();

	}

	
}
