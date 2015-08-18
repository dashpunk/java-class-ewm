package br.inf.ctis.ms.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		Double qtdDeEmbalagens = (double) Math.ceil(qtdTotal / qtdPorEmbalagem);
		System.out.println("########## qtdDeEmbalagens: " + qtdDeEmbalagens);
		
		getMboValue("MSNUNUMQTDEMBALAGENS").setValue(qtdDeEmbalagens);
						
		atualizaPrevisaoCobertura();

		if(!getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").isEmpty()) {
			getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBPREVISAODISTRIBUICAO").save();
		}
		
		if(!getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").isEmpty()) {
			getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").save();
		}
		
    }
    
    
    private void atualizaPrevisaoCobertura() throws MXException, RemoteException {
    	
		try {
			if (!getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").isNull() && !getMboValue("ID2PRIENT").isNull()) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				Date data = sdf.parse("01/" + getMboValue("ID2PRIENT").getString());
				System.out.println("########## Data = " + data);
				
				Calendar c = Calendar.getInstance();
				c.setTime(data);
								
				System.out.println("########## meses = " + (getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble()));
				System.out.println("########## meses = " + (int) Math.round(getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble()));
				int meses = (int) Math.round(getMboValue("ID2QNTPEDIDO").getDouble() / getMboValue("MSNUNUMCONSUMOMEDIOMENSAL").getDouble());
				
				
				System.out.println("########## Data antes de somar meses = " + c.getTime() + " -> " + sdf.format(c.getTime()).substring(3));
				c.add(Calendar.MONTH, meses);
				System.out.println("########## Data apos somar meses = " + c.getTime() + " -> " + sdf.format(c.getTime()).substring(3));
				
				String valor = sdf.format(c.getTime()).substring(3);
				
				if (valor.length() == 6) {
					valor = "0" + valor;
			    }
								
				getMboValue("MSALDTAPREVISAOCOBERTURA").setValue(valor);
				
				
			}
					
		}  catch (RemoteException e) {
			e.printStackTrace();
        } catch (ParseException e) {		
			e.printStackTrace();
		}
    }

}
