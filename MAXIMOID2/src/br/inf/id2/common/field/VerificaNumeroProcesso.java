package br.inf.id2.common.field;

import java.rmi.RemoteException;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * ID2 Tecnologia S.A.
 * 
 * <h4>Descrição da Classe</h4>
 * - Classe criada com o intuito de, ao se preencher o campo, verificar se já existe um valor igual salvo no banco de dados. 
 * 
 * <h4>Notas</h4>
 * 
 * <h4>Referências</h4>
 * - Aplicada em qualquer campo/tela
 * 
 * <h4>Revisões</h4>
 * - Revisão 1.0 - @author Patrick Silva: Classe inicialmente criada.
 * 
 * @since 23/02/2012 16:00 (Data da criação da classe)
 * @version 1.0 (Revisão atual)
 * 
 */
public class VerificaNumeroProcesso extends MboValueAdapter {

	public VerificaNumeroProcesso(MboValue mbv) {
		super(mbv);
		System.out.println("*** VerificaNumeroProcesso ***");
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		String digitado = getMboValue().getString();
		String tabela = mboValue.getMbo().getName();
		String coluna = mboValue.getName();

//		System.out.println("*** VALOR DIGITADO "+digitado);
//		System.out.println("*** NOME TABELA mboValue.getMbo.getName "+tabela);
//		System.out.println("*** NOME COLUNA mboValue.getName "+coluna);
		
        MboSet mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet(tabela, getMboValue().getMbo().getUserInfo());
        mboSet.setWhere(coluna+" = '"+digitado+"'");
        mboSet.reset();
		
        if(mboSet.count() > 0){
        	throw new MXApplicationException("company", "NumeroProcessoExistente");
        }
	}
}
