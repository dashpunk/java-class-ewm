package br.inf.id2.common.util;

public class TesteMain {

	public static void main(String args[]){
		
		String valMasc = Serial.getValorMascarado("###/###/#######-ANO", "0010021234567","2011", true);//Uteis.getValorMascarado("PROC9999/9999", "00012011", true);
		System.out.println("---> "+valMasc);
		
		String seq = Serial.mascaraSeq(123, 3);
		System.out.println("---> "+ seq);
	}
}
