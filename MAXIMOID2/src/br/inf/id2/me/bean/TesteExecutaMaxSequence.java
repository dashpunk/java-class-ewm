package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.mbo.MaxSequence;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author Jessé Rovira
 *
 */

public class TesteExecutaMaxSequence extends AppBean
{

	public TesteExecutaMaxSequence() {
		// TODO Auto-generated constructor stub
	}
	
	public void teste() throws RemoteException, MXException {
		
		java.sql.Connection conexao = MXServer.getMXServer().getDBManager().getConnection(MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
		long seq = MaxSequence.generateKey(conexao, "PO", "POID");
		System.out.println("############## Sequence... = " + seq);
		throw new MXApplicationException("teste", "sequence", new String[]{seq + ""});
	
	}	
	
}