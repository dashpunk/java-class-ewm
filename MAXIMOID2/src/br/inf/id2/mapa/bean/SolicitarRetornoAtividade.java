package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;

/**
* ID2 Tecnologia S.A.
*
* <h4>Descri��o Classe</h4>
*    - Classe criada para atender a demanda de solicitar retorno de atividades.
*    
* 
* <h4>Notas</h4>
*    - 
*    
* 
* <h4>Refer�ncias</h4>
*    - Classe aplicada na tela MA_RET01
* 
* <h4>Revis�es</h4>
*    - Revis�o 1.0 - @author Leysson Moreira: Classe inicialmente criada.
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
	
	  //M�todo utilizado para setar o usu�rio da sess�o no atributo ID2SOLREG ao entrar na tela.
	  @Override
	  public synchronized boolean fetchRecordData() throws MXException,
	    		RemoteException {
	    	getMbo().setValue("id2solret", sessionContext.getUserInfo().getLoginUserName());
	    	return super.fetchRecordData();
	    }

}
