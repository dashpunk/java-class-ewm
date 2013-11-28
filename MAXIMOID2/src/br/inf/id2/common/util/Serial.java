package br.inf.id2.common.util;

import java.rmi.RemoteException;

import psdi.mbo.MboValueAdapter;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.security.UserInfo;
import psdi.util.MXException;

public class Serial extends MboValueAdapter{
	
	public static String getSerialMascarado(MboValue mboValue, UserInfo userInfo) throws RemoteException, MXException{

		System.out.println("*** Serial - getSerialMascarado!!!!!!!! ***");
		System.out.println("*** NOME COLUNA mboValue.getName "+mboValue.getName());
		System.out.println("*** NOME TABELA mboValue.getMbo.getName "+mboValue.getMbo().getName());
		
        MboSet mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBSERIAL", userInfo);
        mboSet.setWhere("STNOMTABELA = '"+mboValue.getMbo().getName()+"' AND STNOMCOLUNA = '"+mboValue.getName()+"'");
        mboSet.reset();

        System.out.println("*** stmascara "+mboSet.getMbo(0).getString("STMASCARA"));
        System.out.println("*** stmascara "+mboSet.getMbo(0).getString("NUNUMSEQ"));
        System.out.println("*** stmascara "+mboSet.getMbo(0).getString("NUNUMANO"));
		String mascara = mboSet.getMbo(0).getString("STMASCARA");
		int numSeqAtual = mboSet.getMbo(0).getInt("NUNUMSEQ");
		int numAno = mboSet.getMbo(0).getInt("NUNUMANO");
		
		int numPasso = mboSet.getMbo(0).getInt("NUNUMPASSO");
		int numTam = mboSet.getMbo(0).getInt("NUNUMTAM");
		
		System.out.println("*** setMascaraSeq "+mascaraSeq(numSeqAtual, numTam));
//		String resultMasc = getValorMascarado(mascara, Integer.toString(numSeqAtual), Integer.toString(numAno),true);
		String resultMasc = getValorMascarado(mascara, mascaraSeq(numSeqAtual, numTam), Integer.toString(numAno),true);
		
		System.out.println("*** numSeq antes "+numSeqAtual);
		mboSet.getMbo(0).setValue("nunumseq", numSeqAtual+numPasso);
		mboSet.save();
		System.out.println("*** numSeq depois do save "+numSeqAtual+1);
		
		return resultMasc;
	}
	
	public static String mascaraSeq(int seq, int tam){
		char[] aux = Integer.toString(seq).toCharArray();
		int qtdZeros = tam - aux.length;
		String result = "";
		for(int i=0; i<qtdZeros; i++){
			result += "0";
		}
		return result += seq;
	}

	public static String getValorMascarado(String pMask, String pSeq, String pAno, boolean pReturnValueEmpty) {

        if (pReturnValueEmpty == true && (pSeq == null || pSeq.trim().equals(""))) {
            return "";
        }
        
        pMask = pMask.toUpperCase().replaceAll("X", "#");

        for (int i = 0; i < pSeq.length(); i++) {
            pMask = pMask.replaceFirst("#", pSeq.substring(i, i + 1));
        }

        pMask = pMask.replaceAll("ANO", pAno);
        return pMask.replaceAll("#", "");
    }
}
