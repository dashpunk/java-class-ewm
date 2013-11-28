// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java

package psdi.id2.mapa;

import psdi.app.pr.*;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;
import psdi.app.rfq.*;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService

public class FldCPF extends MboValueAdapter
{

	private final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	public FldCPF(MboValue mbv)
	{
		super(mbv);
	}

	public void validate()
		throws MXException, RemoteException
	{
		super.validate();
		String param[] = {new String()};
		if(getMboValue().getMbo().getString("APPTYPE").equals("PF"))
		{
			if(CPF(getMboValue().getString()))
				return;
			else
				throw new MXApplicationException("person", "CPFInvalido", param);
		}
		else
		{
			if(CNPJ(getMboValue().getString()))
				return;
			else
				throw new MXApplicationException("company", "CNPJInvalidoA2", param);
		}
	}
	
	private boolean CPF( String str_cpf )
	{
		int soma = 0;
		int result1, result2;
		if(str_cpf.length() != 11) {
		  return false;
		}
		else {
			if(str_cpf.equals("00000000000") || str_cpf.equals("11111111111")) {
				return false;
			}
			else {
				int j = 10;
				for (int i = 0; i <= 8; i++) {
				  soma = soma + Integer.parseInt(str_cpf.charAt(i) + "") * j;
				  j--;
				}
				soma = soma - (11 * (soma / 11));
				if(soma == 0 || soma == 1){
				  result1 = 0;
				}
				else {
				  result1 = 11 - soma;
				}
				if(result1 == Integer.parseInt(str_cpf.charAt(9) + "")) {
					j = 11;
					soma = 0;
					for(int i = 0; i<=9; i++) {
						soma = soma + Integer.parseInt(str_cpf.charAt(i) + "") * j;
						j--;
					}
					soma = soma - (11 * (soma / 11));
					if(soma == 0 || soma == 1) {
						result2 = 0;
					}
					else {
						result2 = 11 - soma;
					}
					if(result2 == Integer.parseInt(str_cpf.charAt(10) + "")){
						return true;
					}
					else {
						return false;
					}
				}  // fim do if(resltado1 == ...)
				else {
				  return false;
				}
			}
		}
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