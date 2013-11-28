package psdi.id2.mapa;

import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.pr.*;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;
import psdi.app.rfq.*;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService
/**
 *
 * @author ID2
 */
public class ID2FldPersonid extends MboValueAdapter {

    private final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     *
     * @param mbv
     */
    public ID2FldPersonid(MboValue mbv) {
        super(mbv);
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
                throw new MXApplicationException("person", "CNPJInvalidoFldPersonid", param);
            }
        }
    }

    /**
     *
     * @throws MXException
     * @throws RemoteException
     * @since 25/05/2010
     */
    @Override
    public void validate() throws MXException, RemoteException {
        String param[] = {new String()};
        String valor;

        Uteis.espera("*************** VALOR = " + getMboValue().getString());
        
        //Acrescentado para verificar se está marcado para não validar e mascarar
        boolean semMascara = getMboValue().getMbo().getBoolean("MASEMMASCARA");
        System.out.println("############### Sem máscara? = " + semMascara);
        
        if (semMascara) {
        	super.validate();
        	return;
        }
        
        //mudou devido novas regras
        //if (getMboValue().getMbo().getString("ID2PERTYPE").equals("01")) {
        if (getMboValue().getString().length() == 14) {

            Uteis.espera("*************** CPF ");

            valor = getCPF(getMboValue().getString());


            if (!CPF(valor)) {
                Uteis.espera("*************** !CPF ");
                throw new MXApplicationException("person", "CPFInvalido", param);


            } else {
                try {
                    if (!valor.equals(getMboValue().getString())) {

                        getMboValue().setValue(valor, MboConstants.NOVALIDATION);


                    }
                } catch (Exception e) {
                    throw new MXApplicationException("person", "CPFInvalido", param);


                }
            }
        } else {
            if (!CNPJ(getMboValue().getString())) {
                throw new MXApplicationException("company", "CNPJInvalido", param);


            } else {
                try {
                    valor = getMboValue().getString().replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
                    javax.swing.text.MaskFormatter formatCNPJ = new javax.swing.text.MaskFormatter("**.***.***/****-**");
                    formatCNPJ.setValueContainsLiteralCharacters(false);
                    getMboValue().setValue(formatCNPJ.valueToString(valor), MboConstants.NOVALIDATION);


                } catch (Exception e) {
                    // Uteis.espera("-------------" + e.getMessage());
                    throw new MXApplicationException("company", "CNPJInvalidoID2FldPersonId", param);


                }
            }
        }
        super.validate();


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

