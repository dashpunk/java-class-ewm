package psdi.id2;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * Título: Executa
 * Descrição: Classe que agrupa as decorator's de execução.
 * Empresa:   ID² Tecnologia
 *
 * @author Ricardo S Gomes
 * @since 10 de Junho de 2010
 * @version 1.0
 */
public class Executa {

    /**
     * Este método atribui o valor <b>null</b> para os MboValueAdapter's da lista
     * @param aMboValueAdapter Lista de MboValueAdapter's que recebrão <b>null</b>
     * @throws MXException
     */
    public static void limparMboValues(MboValue[] aMboValue) throws MXException {
        //Uteis.espera("************* limparMboValues()");
        for (MboValue objeto : aMboValue) {
            //Uteis.espera("************* objeto = " + objeto.getAttributeName());
            objeto.setValueNull();
        }
    }

    public static void setReadOnly(MboSet aMboSet, String[] atributos, boolean readOnly) throws MXException, RemoteException {
        MboRemote linha;

        if (aMboSet != null) {
            Uteis.espera("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                Uteis.espera("************** loop " + i);
                linha = aMboSet.getMbo(i);
                Uteis.espera("************** loop b " + i);
                linha.setFieldFlag(atributos, MboConstants.READONLY, readOnly);
                Uteis.espera("************** loop c " + i);
            }
        }
    }

    public static void atualizaAtributo(MboSet aMboSet, String atributo, String valor) throws MXException, RemoteException {

        Mbo linha;

        if (aMboSet != null) {
            //Uteis.espera("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                //Uteis.espera("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                //Uteis.espera("************** loop b " + i);
                linha.setValue(atributo, valor, MboConstants.NOACCESSCHECK);
                //Uteis.espera("************** loop c " + i);
            }
        }

    }

    public static void adiciona(MboSet aMboSetOrigem, MboSet aMboSetDestino, MboSet aMboSetDestinoFilho, Object[] atributosOrigem, String[] atributosDestino) throws MXException, RemoteException, Exception {

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;
        Mbo linhaDestinoFilho;

        if (aMboSetOrigem != null) {
            //Uteis.espera("################ atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                //Uteis.espera("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                //Uteis.espera("****************################ loop ANTES SEM VALIDACAO ");

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                //Uteis.espera("****************################ loop SEM VALIDACAO ");

                for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                    //Uteis.espera("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                    if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                        if (atributosOrigem[contadorAtributo] instanceof String) {

                            if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                //Uteis.espera("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                            } else {
                                //Uteis.espera("################ atributo String = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                            }
                        } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                            //Uteis.espera("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                        } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                            //Uteis.espera("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                        }

                    } else {
                        //Uteis.espera("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                    }

                }

                //Uteis.espera("********************-------*************** antes do owner " + linhaDestino.getOwner().getName());
                //Uteis.espera("********************-------*************** antes do owner " + linhaDestino.getName());
                //Uteis.espera("********************-------*************** antes do owner");

                aMboSetDestinoFilho.setOwner(linhaDestino);

                //Uteis.espera("********************-------*************** depois do owner");

                linhaDestinoFilho = (Mbo) aMboSetDestinoFilho.add();

                //Uteis.espera("********************-------*************** depois do add invbalance");


            }
        }

    }

    public static void adiciona(MboSet aMboSetOrigem, MboSet aMboSetDestino, Object[] atributosOrigem, String[] atributosDestino, String[] nomeCampoAdicional, Object[] valorcampoAdicional) throws MXException, RemoteException, Exception {

        //Uteis.espera("************** atualizaAtributo()");

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;

        if (aMboSetOrigem != null) {
            //Uteis.espera("################ atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                //Uteis.espera("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                //Uteis.espera("****************################ loop ANTES SEM VALIDACAO ");

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                //Uteis.espera("****************################ loop SEM VALIDACAO ");
                for (int j = 0; j < nomeCampoAdicional.length; j++) {
                    linhaDestino.setValue(nomeCampoAdicional[j], (String) valorcampoAdicional[j]);
                }

                for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                    //Uteis.espera("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                    if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                        if (atributosOrigem[contadorAtributo] instanceof String) {

                            if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                //Uteis.espera("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                            } else {
                                //Uteis.espera("################ atributo String = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                            }
                        } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                            //Uteis.espera("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                        } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                            //Uteis.espera("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                        }

                    } else {
                        //Uteis.espera("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                    }

                }



            }
        }

    }

    public static Long somaValor(MboSetRemote aMboSet, String atributo) throws MXException, RemoteException {
        Uteis.espera("************** somaValor()");

        Mbo linha;

        long retorno = 0;

        if (aMboSet != null) {
            Uteis.espera("************** atualizaAtributo() ENTROU na condicao total de linhas = " + aMboSet.count());
            for (int i = 0; i < aMboSet.count(); i++) {
                Uteis.espera("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                Uteis.espera("************** loop b " + i);

                if (!linha.toBeDeleted()) {
                    retorno += linha.getLong(atributo);
                }
                Uteis.espera("************** loop c " + retorno);
            }
        }

        Uteis.espera("************** RETORNO = " + retorno);
        return retorno;
    }

    public static double somaValor(String atributo, MboSetRemote aMboSet) throws MXException, RemoteException {

        Mbo linha;

        double retorno = 0;

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if (!linha.toBeDeleted()) {
                    retorno += linha.getDouble(atributo);
                }
            }
        }
        return retorno;
    }
}
