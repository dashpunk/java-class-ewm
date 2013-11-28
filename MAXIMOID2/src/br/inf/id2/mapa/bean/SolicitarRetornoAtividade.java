package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;

/**
* ID2 Tecnologia S.A.
*
* <h4>Descrição Classe</h4>
*    - Classe criada para atender a demanda de solicitar retorno de atividades.
*    
* 
* <h4>Notas</h4>
*    - 
*    
* 
* <h4>Referências</h4>
*    - Classe aplicada na tela MA_RET01
* 
* <h4>Revisões</h4>
*    - Revisão 1.0 - @author Leysson Moreira: Classe inicialmente criada.
* 
* 
* @since 20/03/2012 09:40
* @version 1.0
* 
* 
*/

public class SolicitarRetornoAtividade extends psdi.webclient.system.beans.AppBean{

	public SolicitarRetornoAtividade() {
		super();
	}
	
	  //Método utilizado para setar o usuário da sessão no atributo ID2SOLREG ao entrar na tela.
	  @Override
	  public synchronized boolean fetchRecordData() throws MXException,
	    		RemoteException {
	    	getMbo().setValue("id2solret", sessionContext.getUserInfo().getLoginUserName());
	    	return super.fetchRecordData();
	    }

}
