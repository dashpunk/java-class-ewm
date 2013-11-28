package br.inf.id2.valec.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;


/**
* ID2 Tecnologia S.A.
*
* <h4>Descrição da Classe</h4>
*    - Classe criada para atender a demanda de Controle de Contas do módulo Orçamentário. Essa classe é 
*    responsável, inicialmente por filtrar os registros da "Grid Contas Correntes" baseada na combo "Ano Base".
* 
* <h4>Notas</h4>
*    - Optamos por utilizar a Bean ao invés do Field por ter a necessidade de atualizar 
*    a tela após filtrar (reloadTable() e refreshTable()).
* 
* <h4>Referências</h4>
*    - Classe aplicada na tela MT_CCONT00
* 
* <h4>Revisões</h4>
*    - Revisão 1.0 - @author Dyogo Dantas: Classe inicialmente criada.
* 
* 
* @since 24/01/2012 15:40
* @version 1.0
* 
* 
*/

public class ControleContasContabeis extends AppBean
{

	/**
	 * Construtor
	 */
    public ControleContasContabeis()
    {
        System.out.println("############ Construtor - ControleContasContabeis");
    }

    /**
     * Efetua a atualização da Grid "Formato de Conta Corrente" após ser invocada. É chamada através do botão "Atualizar"
    **/
    public void atualizaContaCorrente()
        throws MXException, RemoteException {
    	atualizarAbas(getMbo());
    }

    /**
     * Executa a ação de atualização das 5 abas
     * 
     * @param mbo Mbo atual da tela 
    **/
    private void atualizarAbas(MboRemote mbo) throws MXException, RemoteException {
        
        System.out.println(" ######################## atualizaContaCorrente... ");
        String ano = getDefaultValue("MT_ANOBASE");
        System.out.println("################ Ano base = " + ano);

    	MboSetRemote mboSetContaCorrente = mbo.getMboSet("MT_RL00FMT1");
        MboSetRemote mboSetContaCorrente2 = mbo.getMboSet("MT_RL00FMT2");
        MboSetRemote mboSetContaCorrente3 = mbo.getMboSet("MT_RL00FMT3");
        MboSetRemote mboSetContaCorrente4 = mbo.getMboSet("MT_RL00FMT4");
        MboSetRemote mboSetContaCorrente5 = mbo.getMboSet("MT_RL00FMT5");
        if(ano != null) {
	    	System.out.println("############## Atualizada a tabela...");
	        //Realiza o filtro para as 5 abas ao mesmo tempo.
	        mboSetContaCorrente.setWhere("MT_ANOBASE = '" + ano + "'");
	        mboSetContaCorrente2.setWhere("MT_ANOBASE = '" + ano + "'");
	        mboSetContaCorrente3.setWhere("MT_ANOBASE = '" + ano + "'");
	        mboSetContaCorrente4.setWhere("MT_ANOBASE = '" + ano + "'");
	        mboSetContaCorrente5.setWhere("MT_ANOBASE = '" + ano + "'");
        } else {
        	mboSetContaCorrente.setWhere("MT_ANOBASE is null");
	        mboSetContaCorrente2.setWhere("MT_ANOBASE is null");
	        mboSetContaCorrente3.setWhere("MT_ANOBASE is null");
	        mboSetContaCorrente4.setWhere("MT_ANOBASE is null");
	        mboSetContaCorrente5.setWhere("MT_ANOBASE is null");
        }
        mboSetContaCorrente.reset();
        mboSetContaCorrente2.reset();
        mboSetContaCorrente3.reset();
        mboSetContaCorrente4.reset();
        mboSetContaCorrente5.reset();
        
        //Atualiza as tabelas
        reloadTable();
        refreshTable();

	}

	@Override
	/**
	 * Feito com o intuito de, ao mudar de registro, também atualizar os registros
	 */
    public synchronized boolean fetchRecordData() throws MXException,
    		RemoteException {
    	System.out.println("################# fetchRecordData... atualizando registros");
    	atualizarAbas(getMbo());
    	return super.fetchRecordData();
    }
    
}
