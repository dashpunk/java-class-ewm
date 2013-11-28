package br.inf.id2.common.wf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import psdi.mbo.MboRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXSystemException;
import psdi.common.action.*;
import java.lang.String;

/**
 * 
 * @author Jessé Rovira
 */
public class SQLAction implements ActionCustomClass
{
	public SQLAction(){
		super();
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
		throws MXException, java.rmi.RemoteException
	{
		//implementar c�digo de execu��o do SQL aqui
		try
		{

			Connection connection = MXServer.getMXServer().getDBManager().getConnection(mbo.getUserInfo().getConnectionKey());
			Statement statement = connection.createStatement();
			SqlFormat sqlformat = new SqlFormat(mbo, (String)params[0]);
			System.out.println(sqlformat.format());
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			Object []  aobj= new Object[]{new Integer(sqlexception.getErrorCode())};
			MXSystemException mxexception=new MXSystemException("system","sql",aobj,sqlexception);
			throw mxexception;
		}
		catch(Throwable throwable)
		{
			throwable.printStackTrace();
		}
		finally
		{
			MXServer.getMXServer().getDBManager().freeConnection(mbo.getUserInfo().getConnectionKey());
		}

	}
};