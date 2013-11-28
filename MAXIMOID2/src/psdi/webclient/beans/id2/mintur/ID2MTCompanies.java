package psdi.webclient.beans.id2.mintur;

import java.rmi.RemoteException;

import psdi.id2.Uteis;
import psdi.mbo.MboConstants;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author ID2
 */
public class ID2MTCompanies extends AppBean {


    public ID2MTCompanies() {
        super();
    }

    private String getCPF(String aCpf) throws MXApplicationException {
        if (!CPF(aCpf)) {
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
        if (!CPF(aCnpj)) {
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
    	System.out.println("############################## ENTREI no SAVE!");
        String param[] = {new String()};
        String valor;

        Uteis.espera("*************** VALOR = " + getMbo().getString("COMPANY"));
        if (getMbo().getString("COMPANY").length() == 14) {

            Uteis.espera("*************** CPF ");

            valor = getCPF(getMbo().getString("COMPANY"));


            if (!CPF(valor)) {
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
            if (!CNPJ(getMbo().getString("COMPANY"))) {
                throw new MXApplicationException("company", "CNPJInvalido", param);

            } else {
            	System.out.println("################ Passou na validacao do CNPJ");
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
//                	System.out.println(e.getMessage());
//                	e.printStackTrace();
//                    throw new MXApplicationException("company", "CNPJInvalidoID2MTCompanies", param);
//
//
//                }
//            }
        }
        return super.SAVE();


    }

    private boolean CPF(String str_cpf) {
        int soma = 0;


        int result1, result2;
        str_cpf = str_cpf.replaceAll("\\.", "").replaceAll("-", "");


        if (str_cpf.length() != 11) {
            return false;


        } else {
            str_cpf = str_cpf.replaceAll("\\.", "").replaceAll("-", "");


            if (str_cpf.equals("00000000000")
                    || str_cpf.equals("11111111111")
                    || str_cpf.equals("22222222222")
                    || str_cpf.equals("33333333333")
                    || str_cpf.equals("44444444444")
                    || str_cpf.equals("55555555555")
                    || str_cpf.equals("66666666666")
                    || str_cpf.equals("77777777777")
                    || str_cpf.equals("88888888888")
                    || str_cpf.equals("99999999999")) {
                return false;


            } else {
                int j = 10;


                for (int i = 0; i
                        <= 8; i++) {
                    soma = soma + Integer.parseInt(str_cpf.charAt(i) + "") * j;
                    j--;

                }


                soma = soma - (11 * (soma / 11));


                if (soma == 0 || soma == 1) {
                    result1 = 0;


                } else {
                    result1 = 11 - soma;


                }
                if (result1 == Integer.parseInt(str_cpf.charAt(9) + "")) {
                    j = 11;
                    soma = 0;


                    for (int i = 0; i
                            <= 9; i++) {
                        soma = soma + Integer.parseInt(str_cpf.charAt(i) + "") * j;
                        j--;

                    }


                    soma = soma - (11 * (soma / 11));


                    if (soma == 0 || soma == 1) {
                        result2 = 0;


                    } else {
                        result2 = 11 - soma;


                    }
                    if (result2 == Integer.parseInt(str_cpf.charAt(10) + "")) {
                        return true;


                    } else {
                        return false;


                    }
                } // fim do if(resltado1 == ...)
                else {
                    return false;


                }
            }
        }
    }

    private boolean CNPJ(String str_cnpj) {
        int soma = 0, aux, dig;

        str_cnpj = str_cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");


        if (str_cnpj.length() != 14) {
            return false;


        }

        String cnpj_calc = str_cnpj.substring(0, 12);



        char[] chr_cnpj = str_cnpj.toCharArray();

        // Primeira parte


        for (int i = 0; i
                < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));


            }
        }
        for (int i = 0; i
                < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));


            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        // Segunda parte
        soma = 0;


        for (int i = 0; i
                < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));


            }
        }
        for (int i = 0; i
                < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));


            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        return str_cnpj.equals(cnpj_calc);

    }
}

