package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;

/**
 * 
 * @author Dyogo Dantas
 *
 */

public class Convenios extends ContPurchAppBean
{

	public Convenios() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int SAVE() throws MXException, RemoteException {
	
		System.out.println("############## Entrou no SAVE do ConveniosDCTEC!");
		double valorTotal = 0;
		
		double valorCapital = getMbo().getDouble("ID2CREPVAL");
		double valorConsumo = getMbo().getDouble("MEVALCONS");
		double valorFinanceiro = getMbo().getDouble("ID2PCPVAL");
		double valorBensServicos = getMbo().getDouble("ID2CPCPVALBS");
		System.out.println("############### Valores: " +valorCapital + "|" + valorConsumo + "|" + valorFinanceiro + "|" + valorBensServicos);
		
		valorTotal = valorCapital + valorConsumo + valorFinanceiro + valorBensServicos;
		System.out.println("############## Valor total Geral:" + valorTotal);
		
		getMbo().setValue("ID2CPTOTALVAL", valorTotal);
		return super.SAVE();
	
	}	
	
}