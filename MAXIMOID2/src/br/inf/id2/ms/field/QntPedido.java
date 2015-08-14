package br.inf.id2.ms.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author ID2
 *
 * Antes extendia de: psdi.app.common.purchasing.FldPurOrderQty
 * 
 */
public class QntPedido extends MboValueAdapter {

    /**
     * Método construtor de ID2FldOrderQty
     * @param mbv 
     * @throws MXException
     */
    public QntPedido(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        getMboValue().getMbo().setValue("ORDERQTY",getMboValue().getDouble());
        
        System.out.println("########## getMboValue(MSNUNUMQTDPOREMBALAGEM).getDouble(): " + getMboValue("MSNUNUMQTDPOREMBALAGEM").getDouble());
		Double qtdPorEmbalagem = (getMboValue("MSNUNUMQTDPOREMBALAGEM").getDouble() != 0) ? getMboValue("MSNUNUMQTDPOREMBALAGEM").getDouble() : 1;
		System.out.println("########## qtdPorEmbalagem: " + qtdPorEmbalagem);
		
		System.out.println("########## getMboValue(ID2QNTPEDIDO).getDouble(): " + getMboValue().getDouble());
		Double qtdTotal = getMboValue().getDouble();
		System.out.println("########## qtdTotal: " + qtdTotal);
		
		System.out.println("########## Divisao: " + (qtdTotal / qtdPorEmbalagem));
		Double qtdDeEmbalagens = qtdTotal / qtdPorEmbalagem;
		System.out.println("########## qtdDeEmbalagens: " + qtdDeEmbalagens);
		
		getMboValue("MSNUNUMQTDEMBALAGENS").setValue(qtdDeEmbalagens);
		
		if(!getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").isEmpty()) {
			getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").save();
		}
		
		if(!getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").isEmpty()) {
			getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").save();
		}

    }

}
