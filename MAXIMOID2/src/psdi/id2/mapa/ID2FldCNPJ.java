// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java

package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.app.company.FldCompanyCompany;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService

public class ID2FldCNPJ extends FldCompanyCompany
{

	private final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	public ID2FldCNPJ(MboValue mbv)
        throws MXException
	{
		super(mbv);
	}

	public void validate()
		throws MXException, RemoteException
	{
		super.validate();
		String param[] = {new String()};
		if(CNPJ(getMboValue().getString()))
			return;
		else
			throw new MXApplicationException("company", "CNPJInvalidoA3", param);
	}
	
	private boolean CNPJ( String str_cnpj )
	{
		int soma = 0, aux, dig;
  
		if ( str_cnpj.length() != 14 )
			return false;
		
		String cnpj_calc = str_cnpj.substring(0,12);

		char[] chr_cnpj = str_cnpj.toCharArray();
  
		 // Primeira parte
		for( int i = 0; i < 4; i++ )
			if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1)) ;
		for( int i = 0; i < 8; i++ )
			if ( chr_cnpj[i+4]-48 >=0 && chr_cnpj[i+4]-48 <=9 )
				soma += (chr_cnpj[i+4] - 48) * (10 - (i + 1)) ;
		
		dig = 11 - (soma % 11);
		cnpj_calc += ( dig == 10 || dig == 11 ) ? "0" : Integer.toString(dig);
  
		// Segunda parte
		soma = 0;
		for ( int i = 0; i < 5; i++ )
			if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1)) ;
		for ( int i = 0; i < 8; i++ )
			if ( chr_cnpj[i+5]-48 >=0 && chr_cnpj[i+5]-48 <=9 )
				soma += (chr_cnpj[i+5] - 48) * (10 - (i + 1)) ;
		
		dig = 11 - (soma % 11);
		cnpj_calc += ( dig == 10 || dig == 11 ) ? "0" : Integer.toString(dig);
  
		return str_cnpj.equals(cnpj_calc);
	}
}