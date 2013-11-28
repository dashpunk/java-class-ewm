package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;

/**
* ID2 Tecnologia S.A.
*
* <h4>Descri��o Classe</h4>
*    - Classe criada para atender a demanda de solicita��o de registro de estabelecimento.
*    
* 
* <h4>Notas</h4>
*    - 
*    
* 
* <h4>Refer�ncias</h4>
*    - Classe aplicada na tela MA_REG01
* 
* <h4>Revis�es</h4>
*    - Revis�o 1.0 - @author Leysson Moreira: Classe inicialmente criada.
* 
* 
* @since 19/03/2012 12:08
* @version 1.0
* 
* 
*/

public class SolicitarRegistroEstabelecimento extends psdi.webclient.system.beans.AppBean{

	public SolicitarRegistroEstabelecimento() {
		super();
	}
	
	  //M�todo utilizado para setar o usu�rio da sess�o no atributo ID2SOLREG ao entrar na tela.
	  @Override
	  public synchronized boolean fetchRecordData() throws MXException,
	    		RemoteException {
	    	getMbo().setValue("ID2SOLREG", sessionContext.getUserInfo().getLoginUserName());
	    	return super.fetchRecordData();
	    }

}
