package br.inf.id2.tj.bean;

import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 */
public class Companies extends AppBean {


    public Companies() {
        super();
    }

    private String getCPF(String aCpf) throws MXApplicationException {
        if (!Validar.CPF(aCpf)) {
            return aCpf;
        } else {
            try {
                String valor;
                valor = aCpf.replaceAll("\\.", "").replaceAll("-", "");
                javax.swing.text.MaskFormatter formatCPF = new javax.swing.text.MaskFormatter("***.***.***-**");
                formatCPF.setValueContainsLiteralCharacters(false);
                return formatCPF.valueToString(valor);
            } catch (Exception e) {
                String param[] = {new String()};
                throw new MXApplicationException("person", "CPFInvalido", param);
            }
        }
    }

    private String getCNPJ(String aCnpj) throws MXApplicationException {
        if (!Validar.CPF(aCnpj)) {
            return aCnpj;
        } else {
            try {
                String valor;
                valor = aCnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
                javax.swing.text.MaskFormatter formatCNPJ = new javax.swing.text.MaskFormatter("**.***.***/****-**");
                formatCNPJ.setValueContainsLiteralCharacters(false);
                return formatCNPJ.valueToString(valor);
            } catch (Exception e) {
                String param[] = {new String()};
                throw new MXApplicationException("person", "CNPJInvalido", param);
            }
        }
    }
   
    @Override
    public int SAVE() throws MXException, RemoteException {
    	//System.out.println("############################## ENTREI no SAVE!");
        String param[] = {new String()};
        String valor;

        Uteis.espera("*************** VALOR = " + getMbo().getString("COMPANY"));
        if (getMbo().getString("COMPANY").length() == 14) {

            Uteis.espera("*************** CPF ");

            valor = getCPF(getMbo().getString("COMPANY"));


            if (!Validar.CPF(valor)) {
                Uteis.espera("*************** !CPF ");
                throw new MXApplicationException("company", "CPFInvalido", param);


            } else {
                try {
                    if (!valor.equals(getMbo().getString("COMPANY"))) {

                    	Uteis.espera("******************* Deu FALSE na validacao do CPF");
                        getMbo().setValue(valor, MboConstants.NOVALIDATION);

                    }
                } catch (Exception e) {
                    throw new MXApplicationException("company", "CPFInvalido", param);


                }
            }
        } else {
            if (!Validar.CNPJ(getMbo().getString("COMPANY"))) {
                throw new MXApplicationException("company", "CNPJInvalido", param);

            } else {
            	//System.out.println("################ Passou na validacao do CNPJ");
            }
//            } else {
//                try {
//                    valor = getMbo().getString("COMPANY").replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
//                    javax.swing.text.MaskFormatter formatCNPJ = new javax.swing.text.MaskFormatter("**.***.***/****-**");
//                    formatCNPJ.setValueContainsLiteralCharacters(false);
//                    
//                    Uteis.espera("******************* Deu FALSE na validacao do CNPJ");
//                    getMbo().setValue(formatCNPJ.valueToString(valor), MboConstants.NOVALIDATION);
//
//
//                } catch (Exception e) {
//                    // Uteis.espera("-------------" + e.getMessage());
//                	//System.out.println(e.getMessage());
//                	e.printStackTrace();
//                    throw new MXApplicationException("company", "CNPJInvalidoID2MTCompanies", param);
//
//
//                }
//            }
        }
        return super.SAVE();


    }

}

