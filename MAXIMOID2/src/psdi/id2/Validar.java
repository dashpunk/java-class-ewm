package psdi.id2;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueData;
import psdi.util.MXException;

/**
 * Título: Validar
 * Descrição: Classe que agrupa as decorator's de validação.
 * Empresa:   ID² Tecnologia
 *
 * @author Ricardo S Gomes
 * @since 28 de Maio de 2010
 * @version 1.0
 */
public class Validar {

    /**

     *
     * @param aMboValue MboValue a ser analisado
     * @param tamanho Quantidade de caracteres que o aMboValue deve conter
     * @return Verdadeiro caso tenha o mesmo tamanho e negativo caso contrário
     * @throws MXException
     */
    public static boolean tamnhoIgual(MboValue aMboValue, int tamanho) throws MXException {
        if (aMboValue.getString().length() == tamanho) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param aMboValueYorN List de MboValue's a serem analisados
     * @param preenchimentoMinimo Quantidade mínima de objetos que devem conter valores
     * @return Verdadeiro caso o número de objetos com valores seja igual ou maior que o valor contido em preenchimentoMinimo
     * @throws MXException
     * @since 02/06/2010
     */
    public static boolean preencimentoMinimoObrigatorio(MboValueData[] aMboValueYorN, int preenchimentoMinimo) throws MXException {

        int contador = 0;
        //System.out.print("************ total de mbo's " + aMboValue.length);
        for (MboValueData obj : aMboValueYorN) {
            if (obj != null) {

                //System.out.print("************ valor do mbo " + obj.getData());
                if (!obj.getData().equals("N")) {
                    contador++;
                }
            }
        }
        //System.out.print("************ total não nullo " + contador);

        if (contador < preenchimentoMinimo) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean naoVasio(MboValueData[] aMboValue) throws MXException {

        int contador = 0;
        //System.out.print("************ total de mbo's " + aMboValue.length);
        for (MboValueData obj : aMboValue) {
            if (obj.isNull()) {
                contador++;
            }
        }
        //System.out.print("************ total não nullo " + contador);

        if (contador > 0) {
            return false;
        } else {
            //System.out.print("************ retornou true em naoVasio()");
            return true;
        }

    }

    /**
     *
     * @param aMboSet List a ser analisado
     * @param atributoYorN Nome do atributo YorN a ser analisado
     * @param preenchimentoMaximo Quantidade máxima de objetos com o valor do atributo <> "N"
     * @return Verdadeiro caso o número de objetos com valores seja igual ou menor que o valor contido em preenchimentoMaximo
     * @throws MXException
     * @throws RemoteException
     * @since 07/06/2010
     */
    public static boolean preencimentoMaximoObrigatorio(MboSet aMboSet, String atributoYorN, int preenchimentoMaximo) throws MXException, RemoteException {

        int contador = 0;
        //System.out.print("***************Antes de chamar...");

        Mbo linha;
        //System.out.print("***************Após de chamar...");

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if (!linha.getString(atributoYorN).equals("N")) {
                    contador++;
                    //System.out.print("***************Contador = " + contador);
                }
            }

        }

        //System.out.print("***************Total Contador = " + contador);
        if (contador > preenchimentoMaximo) {
            return false;
        } else {
            return true;
        }

    }

    /**
     *
     * @param aMboSet List a ser analisado
     * @param preenchimentoMinimo Quantidade mínima de registros no relacionamento
     * @return Verdadeiro caso o número de objetos com valores seja igual ou maior que o valor contido em preenchimentoMinimo
     * @throws MXException
     * @throws RemoteException
     * @since 06/07/2010
     */
    public static boolean preenchimentoMinimoObrigatorio(MboSet aMboSet, int preenchimentoMinimo) throws MXException, RemoteException {

        int contador = 0;
        //System.out.print("***************Antes de chamar...");

        Mbo linha;
        //System.out.print("***************Após de chamar...");

        boolean resultado = false;
        if (aMboSet != null) {
            if (aMboSet.count() >= preenchimentoMinimo) {

                resultado = true;

            }
        }

        return resultado;

    }

    public static boolean maiorQue(MboValue aMboValueValorMenor, MboValue aMboValueValorMaior) throws MXException {

        return (aMboValueValorMaior.getDouble() > aMboValueValorMenor.getDouble());

    }

    public static boolean menorIgualQue(MboValue valor1, MboValue valor2) throws MXException {
        double v1 = valor1.getDouble();
        double v2 = valor2.getDouble();
        //System.out.print("**************** menor v1 = " + v1 + " v2 = " + v2);
        return (v1 <= v2);

    }

    /**
     *
     * @param aMboSet List a ser analisado
     * @param preenchimentoMinimo Quantidade mínima de registros no relacionamento
     * @return Verdadeiro caso o número de objetos com valores seja igual ou maior que o valor contido em preenchimentoMinimo
     * @throws MXException
     * @throws RemoteException
     * @since 06/07/2010
     */
    public static boolean maiorQue(MboSet aMboSetPai, MboSet aMboSetFilho, String atributoPai, String atributoFilho) throws MXException, RemoteException {

        boolean resultado = false;

        if ((aMboSetPai != null) && (aMboSetFilho != null)) {
            //System.out.print("*********************** aMboSetPai = " + aMboSetPai.count());
            //System.out.print("*********************** aMboSetFilho = " + aMboSetFilho.count());
            if ((aMboSetPai.count() > 0) && (aMboSetFilho.count() > 0)) {
                //System.out.print("*********************** aMboSetFilho ENTROU ");
                for (int pai = 0; pai < aMboSetPai.count(); pai++) {

                    for (int filho = 0; filho < aMboSetPai.count(); filho++) {
                        if (!(aMboSetFilho.getMbo(filho).getDouble(atributoFilho) > aMboSetPai.getMbo(pai).getDouble(atributoPai))) {
                            return false;
                        }
                    }

                }
                resultado = true;
            }
        }
        return resultado;

    }

    public static boolean filhoMaiorQuePai(MboSet aMboSetPai, String aMboSetFilho, String atributoPai, String atributoFilho) throws MXException, RemoteException {

        boolean resultado = false;

        if (aMboSetPai != null) {
            //System.out.print("*********************** aMboSetPai = " + aMboSetPai.count());
            //System.out.print("*********************** aMboSetFilho = " + aMboSetFilho);
            long contadorParcial = 0;
            if (aMboSetPai.count() > 0) {
                //System.out.print("*********************** aMboSetFilho ENTROU ");
                for (int pai = 0; pai < aMboSetPai.count(); pai++) {
                    contadorParcial = 0;
                    if (aMboSetPai.getMbo(pai).getMboSet(aMboSetFilho) != null) {
                        for (int filho = 0; filho < aMboSetPai.getMbo(pai).getMboSet(aMboSetFilho).count(); filho++) {
                            if (!aMboSetPai.getMbo(pai).getMboSet(aMboSetFilho).getMbo(filho).toBeDeleted()) {
                                //System.out.print("*********************** Pai " + pai + " Filho " + filho + " Valor do filho " + aMboSetPai.getMbo(pai).getMboSet(aMboSetFilho).getMbo(filho).getLong(atributoFilho));
                                contadorParcial += aMboSetPai.getMbo(pai).getMboSet(aMboSetFilho).getMbo(filho).getLong(atributoFilho);
                            }
                        }
                        //System.out.print("*********************** aMboSetFilho contadorParcial " + contadorParcial + " Valor do pai " + aMboSetPai.getMbo(pai).getDouble(atributoPai));
                        if ((contadorParcial > aMboSetPai.getMbo(pai).getDouble(atributoPai))) {
                            //System.out.print("*********************** FALSE");
                            return true;
                        }
                    }

                }
                //System.out.print("*********************** TRUE");
                resultado = false;
            }
        }
        return resultado;

    }

    /**
     *
     * @param aMboSet List a ser analisado
     * @param atributoYorN Nome do atributo YorN a ser analisado
     * @param preenchimentoMaximo Quantidade máxima de objetos com o valor do atributo <> "N"
     * @return Verdadeiro caso o número de objetos com valores seja igual ou menor que o valor contido em preenchimentoMaximo
     * @throws MXException
     * @throws RemoteException
     * @since 07/06/2010
     */
    public static boolean marcado(MboSet aMboSet, String atributoYorN, String atributoChave, String valorChave) throws MXException, RemoteException {

        int contador = 0;
        ////System.out.print("***************Antes de chamar...");

        Mbo linha;
        ////System.out.print("***************Após de chamar...");

        boolean resultado = false;

        if (aMboSet != null) {
            ////System.out.print("***************mboSet <> null");
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                ////System.out.print("***************após linha = " + linha.getString(atributoChave));

                ////System.out.print("Valores-------: ");
                ////System.out.print("Nm atributochave-------: " + atributoChave);
                ////System.out.print("Vl atributochave-------: " + linha.getString(atributoChave));
                ////System.out.print("Vl ESPERADO atributochave-------: " + valorChave);


                ////System.out.print("Nm atributoYorN-------: " + atributoYorN);
                ////System.out.print("Vl atributoYorN-------: " + linha.getString(atributoYorN));
                if (atributoYorN != null) {
                    if ((linha.getString(atributoChave).equals(valorChave)) && (!linha.getString(atributoYorN).equals("N"))) {
                        ////System.out.print("***************após linha = " + linha.getString(atributoChave) + " VERDADEIRO");
                        return true;
                    }
                } else {
                    if ((linha.getString(atributoChave).equals(valorChave)) && (linha.isSelected())) {
                        ////System.out.print("***************após linha = " + linha.getString(atributoChave) + " VERDADEIRO");
                        return true;
                    }
                }
            }

        }

        ////System.out.print("***************após linha = FALSO");

        return resultado;
    }

    public static boolean tamnhoIgual(String aMboValue, int tamanho) throws MXException {
        if (aMboValue.length() == tamanho) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean CPF(String str_cpf) {
        int soma = 0;
        int result1, result2;
        if (str_cpf.length() != 11) {
            return false;
        } else {
            if (str_cpf.equals("00000000000") || str_cpf.equals("11111111111")) {
                return false;
            } else {
                int j = 10;
                for (int i = 0; i <= 8; i++) {
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
                    for (int i = 0; i <= 9; i++) {
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

    public static boolean CNPJ(String str_cnpj) {
        //System.out.print("FLDCNPJ");
        int soma = 0, aux, dig;

        if (str_cnpj.length() != 14) {
            return false;
        }

        String cnpj_calc = str_cnpj.substring(0, 12);

        char[] chr_cnpj = str_cnpj.toCharArray();

        // Primeira parte
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        // Segunda parte
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        return str_cnpj.equals(cnpj_calc);
    }

    public static boolean preencimentoMaximoObrigatorio(MboSet aMboSet, String atributo, String valor, int preenchimentoMaximo) throws MXException, RemoteException {

        int contador = 0;
        //System.out.print("***************Antes de chamar...");

        Mbo linha;
        //System.out.print("***************Após de chamar...");

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if (linha.getString(atributo).equals(valor)) {
                    contador++;
                    //System.out.print("***************Contador = " + contador);
                }
            }

        }

        //System.out.print("***************Total Contador = " + contador);
        if (contador > preenchimentoMaximo) {
            return false;
        } else {
            return true;
        }

    }
}
