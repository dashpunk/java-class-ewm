package br.inf.id2.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 *
 * 
 */
public class RegistroPreco extends MboValueAdapter {


    public RegistroPreco(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        //	Pegar o valor de PO.MS_REGPRECO. Se for SIM, colocar um texto no campo justificativa (PO.MS_JUSTIFICATIVA)
        String sRegistro = getMboValue().getString();
        System.out.println("############### Valor do Registro de Preço=" + sRegistro);
        if (sRegistro != null && sRegistro.equals("SIM")) {
        	getMboValue().getMbo().setValue("MS_JUSTIFICATIVA","Tendo em vista o Sistema Único de Saúde e " +
        													   "a possibilidade de outros entes federativos " +
        													   "necessitarem do insumo, opta-se pelo registro, "+
        													   "conforme art. 3º do Decreto 7.892, "+
        													   "de 23 de janeiro de 2013.");
        } else {
        	getMboValue().getMbo().setValueNull("MS_JUSTIFICATIVA");
        }

    }

}
