package psdi.webclient.beans.id2.mintur;

import java.rmi.RemoteException;

import psdi.id2.Executa;
import psdi.id2.mapa.Uteis;
import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class ID2MTProcom extends AppBean {

	/**
	 * @author Dyogo
	 */
    public ID2MTProcom() {
    }
   
    @Override
    public int SAVE() throws MXException, RemoteException {

        double valorTotal = 0;

        try {
            for (int i = 0; i < getMbo().getMboSet("MTRL01EMPCOM").count(); i++) {
                valorTotal += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01EMPCOM").getMbo(i).getMboSet("MTRL01PO"));
            }
        } catch (Exception e) {
            valorTotal = 0D;
            Uteis.espera("--------------------- c exception = " + e.getMessage());
        }

        getMbo().setValue("MTVALTOT", valorTotal, MboConstants.NOACTION);
        
        
        //Nova alteração realizada dia 17/01/2011
        //Se o campo Modalidade for Dispensa (01) ou Inexigibilidade (02) gerar um novo sequencial (xx/AAAA) para
        //o campo MTNUMMOD (Número da Modalidade)
        
        System.out.println("##############################################################");
        System.out.println("################# Valor do campo MTMODPROCOM (Modalidade): " + getMbo().getString("MTMODPROCOM"));
        System.out.println("################# Valor do campo MTNUMMOD (Número da Modalidade): " + getMbo().getString("MTNUMMOD"));
        System.out.println("##############################################################");

        
        

        super.validate();
        Uteis.espera("--------------------- FIM SAVE appBean");
        return super.SAVE();
    }
}
