package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import br.inf.id2.common.util.Executa;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsNuNumQuantidadeDistribuicao extends MboValueAdapter{
	
	public MsNuNumQuantidadeDistribuicao(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
	
		Double valor = Executa.somaValor(getMboValue().getName(), getMboValue().getMbo().getMboSet("MSTBITENSDISTRIBUICAO"));

        valor += getMboValue().getDouble();

        if (valor > getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) {
            throw new MXApplicationException("generica", "QuantidadeExcedida");
        }
	}

}
