package br.inf.id2.me.field;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Dyogo Dantas
 * 
 */
public class DataDevolucaoBiblioteca extends MboValueAdapter {

    public DataDevolucaoBiblioteca(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date dataDevolucao = getMboValue().getDate();
        Date dataEmprestimo = getMboValue().getMbo().getDate("MXDTEMP");
        super.validate();
        Date dataAtual = new Date();

        if (Data.dataInicialMenorFinal(dataAtual,dataDevolucao)) {
            throw new MXApplicationException("system", "DataMaiorQueAtual");
        }
        
        if (Data.dataInicialMenorFinal(dataDevolucao, dataEmprestimo)) {
        	throw new MXApplicationException("system", "DataDevolucaoMenorDataEmprestimo");
        }
        
        
    }
}
