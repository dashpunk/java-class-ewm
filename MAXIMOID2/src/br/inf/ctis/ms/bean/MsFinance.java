package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsFinance extends AppBean {

	/**
	 * @author marcelosydney.lima
	 * psdi.webclient.system.beans.AppBean
	 */
	public MsFinance() {
	}
	
	@Override
	public void save() throws MXException {
		try {
				MboRemote mbo1;
				MboRemote mbo2;
				MboRemote mbo3;
				MboRemote mbo4;
				double valorglobal = 0d;
				double saldoinstrumento = 0d;
				double valortotalap = 0d;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("INVOICE").getMbo(i)) != null); i++) {					
					System.out.println("########## Count dos Objetos da Nota Fiscal (INVOICELINE) = " + mbo1.getMboSet("INVOICELINE").count());
					if (mbo1.getMboSet("INVOICELINE").count() > 0) {
						
						double valortotalnf = 0d;
						
						for (int j = 0; ((mbo2 = mbo1.getMboSet("INVOICELINE").getMbo(j)) != null); j++) {
							double valor = mbo2.getDouble("LINECOSTFORUI") - mbo2.getDouble("TAX1FORUI");
							mbo2.setValue("MSNUNUMTOTALCOMTRIBUTO", valor);
							System.out.println("########## Valor = " + valor);
							if (!mbo2.toBeDeleted()) {
								valortotalnf += valor;
							}
						}
						System.out.println("########## Valor Total = " + valortotalnf);
						mbo1.setValue("MSNUNUMVALORTOTAL", valortotalnf);
						
						valorglobal += mbo1.getDouble("MSNUNUMVALORTOTAL");
						System.out.println("########## Valor Global = " + valorglobal);
							
					} else {
						throw new MXApplicationException("invoiceline", "SemInsumos");
					}
				}
										
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					System.out.println("########## Count Notas Fiscais = " + mbo1.getMboSet("MSTBAPNOTAFISCAL").count());
					if (mbo1.getMboSet("MSTBAPNOTAFISCAL").count() > 0) {
										
						for (int j = 0; ((mbo2 = mbo1.getMboSet("MSTBAPNOTAFISCAL").getMbo(j)) != null); j++) {
							if (!mbo2.toBeDeleted()) {
								System.out.println("########## Valor total da NF = " + mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL"));
								valortotalap += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL");
								System.out.println("########## Valor Total da AP (PARCIAL) = " + valortotalap);
							}
						}
						
						System.out.println("########## Valor Total da AP = " + valortotalap);
						mbo1.setValue("MSNUNUMVALORTOTAL", valortotalap);
						
					} else {
						throw new MXApplicationException("autorizacaopagamento", "SemNotasFiscais");
					}
				}
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					System.out.println("########## Count Notas de Empenho = " + mbo1.getMboSet("MSTBAPNOTAEMPENHO").count());
					if (mbo1.getMboSet("MSTBAPNOTAEMPENHO").count() > 0) {
												
						double valortotalne = 0d;
						
						for (int j = 0; ((mbo3 = mbo1.getMboSet("MSTBAPNOTAEMPENHO").getMbo(j)) != null); j++) {
							if (!mbo3.toBeDeleted()) {
								System.out.println("########## Valor total da NE = " + mbo3.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO"));
								valortotalne += mbo3.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO");
								System.out.println("########## Valor Total das NEs (PARCIAL) = " + valortotalne);
							}
						}
						
						System.out.println("########## Valor Total das NEs = " + valortotalne);
						
						if (valortotalap > valortotalne){
							throw new MXApplicationException("autorizacaopagamento", "ValorAPsuperiorNE");
						}
						
						
					} else {
						throw new MXApplicationException("autorizacaopagamento", "SemNotasEmpenho");
					}
				}
	
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {		
					System.out.println("########## Count Ordens Bancarias = " + mbo1.getMboSet("MSTBORDEMBANCARIA").count());
					if (mbo1.getMboSet("MSTBORDEMBANCARIA").count() > 0) {
											
						double valortotalob = 0d;
						double saldoap = 0d;
						
						for (int j = 0; ((mbo4 = mbo1.getMboSet("MSTBORDEMBANCARIA").getMbo(j)) != null); j++) {
							if (!mbo4.toBeDeleted()) {
								System.out.println("########## Valor da OB = " + mbo4.getDouble("MSNUNUMVALOR"));
								valortotalob += mbo4.getDouble("MSNUNUMVALOR");
								System.out.println("########## Valor total das OBs (PARCIAL) = " + valortotalob);
							}
						}
						
						saldoap = mbo1.getDouble("MSNUNUMVALORTOTAL") - valortotalob;
						if(saldoap >= 0){
							System.out.println("########## Saldo da AP = " + saldoap);
							mbo1.setValue("MSNUNUMSALDO", saldoap);
						} else if (saldoap < 0){
							throw new MXApplicationException("ordensbancarias", "SaldoInsuficiente");
						}
					}
				}
			
				System.out.println("########## Valor Global Final = " + valorglobal);
				saldoinstrumento = getMbo().getDouble("VALOR_TOTAL_INSTRUMENTO") - valorglobal;
				System.out.println("########## Saldo do Instrumento = " + saldoinstrumento);
				if(saldoinstrumento >= 0){
					super.save();
				} else if (saldoinstrumento <0){
					throw new MXApplicationException("invoice", "SaldoInsuficiente");
				}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}