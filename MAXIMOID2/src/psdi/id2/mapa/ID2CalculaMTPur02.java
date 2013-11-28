package psdi.id2.mapa;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2CalculaMTPur02 extends MboValueAdapter {

    /**
     * Método construtor
     * @param mbv
     * @throws MXException
     */
    public ID2CalculaMTPur02(MboValue mbv) throws MXException {

        super(mbv);

    }

    /**
     *
     * Método que calculará o valor de "Valor a ser Apresentado" (ID2VALPREST) e "Valor Atual do Contrato" (MTATCONT)
     * 
     * <p>
     * "Valor a ser Apresentado" (ID2VALPREST) = "Valor do Contrato" (ID2CPTOTALVAL) * ("Percentual do valor do contrato" (ID2CFXDESEM) / 100)
     * <p>
     * "Valor Atual do Contrato" (MTATCONT) = "Valor do Contrato" (ID2CPTOTALVAL) + soma(MTTERADI.MTVALTOT)
     *
     *
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	
    		
	        String atual = getMboValue().getAttributeName();
	        double valorContrato = getMboValue().getMbo().getDouble("ID2CPTOTALVAL");
	        double aliquota = getMboValue().getMbo().getDouble("ID2CFXDESEM") / 100;
	
	        getMboValue().getMbo().getMboValue("ID2VALPREST").setValue(valorContrato * aliquota, MboConstants.NOACTION);
	
	        double valorAtualContrato;
	        if (getMboValue().getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
	            valorAtualContrato = getMboValue().getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALGLO");
	        } else {
	            valorAtualContrato = getMboValue().getMbo().getDouble("ID2CPTOTALVAL");
	        }
	        getMboValue().getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);

	        if(getMboValue().getName().equals("ID2PCPVAL")){
		        System.out.println("*** IF");
		        System.out.println("*** ID2PCPVAL "+getMboValue().getDouble());
		        System.out.println("*** ID2CPCPVALBS "+getMboValue().getMbo().getDouble("ID2CPCPVALBS"));
		        getMboValue().getMbo().setValue("ID2CTPVAL", getMboValue().getDouble() + getMboValue().getMbo().getDouble("ID2CPCPVALBS"));
	    	
	    	}else if(getMboValue().getName().equals("ID2CPCPVALBS")){
	    		System.out.println("*** ELSE");
		        System.out.println("*** ID2CPCPVALBS "+getMboValue().getDouble());
		        System.out.println("*** ID2PCPVAL "+getMboValue().getMbo().getDouble("ID2PCPVAL"));
	        	getMboValue().getMbo().setValue("ID2CTPVAL", getMboValue().getMbo().getDouble("ID2PCPVAL") + getMboValue().getDouble());
	    	}
        
        super.validate();
        
        
    }
}
