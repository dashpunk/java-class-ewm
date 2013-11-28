package br.inf.id2.common.util;

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
        ////System.out.println("************* limparMboValues()");
        for (MboValue objeto : aMboValue) {
            ////System.out.println("************* objeto = " + objeto.getAttributeName());
            objeto.setValueNull();
        }
    }

    public static void setReadOnly(MboSet aMboSet, String[] atributos, boolean readOnly) throws MXException, RemoteException {
        MboRemote linha;

        if (aMboSet != null) {
            //System.out.println("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                //System.out.println("************** loop " + i);
                linha = aMboSet.getMbo(i);
                //System.out.println("************** loop b " + i);
                linha.setFieldFlag(atributos, MboConstants.READONLY, readOnly);
                //System.out.println("************** loop c " + i);
            }
        }
    }

    public static void atualizaAtributo(MboSet aMboSet, String atributo, String valor) throws MXException, RemoteException {

        ////System.out.println("************** atualizaAtributo()");

        Mbo linha;

        if (aMboSet != null) {
            ////System.out.println("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                ////System.out.println("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                ////System.out.println("************** loop b " + i);
                linha.setValue(atributo, valor, MboConstants.NOACCESSCHECK);
                ////System.out.println("************** loop c " + i);
            }
        }

    }

    public static void atualizaAtributo(MboSet aMboSet, String atributo, boolean valor) throws MXException, RemoteException {

        ////System.out.println("************** atualizaAtributo()");

        Mbo linha;

        if (aMboSet != null) {
            ////System.out.println("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                ////System.out.println("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                ////System.out.println("************** loop b " + i);
                linha.setValue(atributo, valor, MboConstants.NOACCESSCHECK);
                ////System.out.println("************** loop c " + i);
            }
        }

    }

    public static void incrementaAtributo(MboSet aMboSet, String atributo, int valor) throws MXException, RemoteException {

        ////System.out.println("************** atualizaAtributo()");

        Mbo linha;

        if (aMboSet != null) {
            ////System.out.println("************** atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSet.count(); i++) {
                ////System.out.println("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                ////System.out.println("************** loop b " + i);
                linha.setValue(atributo, linha.getInt(atributo) + valor, MboConstants.NOACCESSCHECK);
                ////System.out.println("************** loop c " + i);
            }
        }

    }

    public static void adiciona(MboSet aMboSetOrigem, MboSet aMboSetDestino, MboSet aMboSetDestinoFilho, Object[] atributosOrigem, String[] atributosDestino) throws MXException, RemoteException, Exception {

        System.out.println("************** atualizaAtributo()");

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;
        Mbo linhaDestinoFilho;

        if (aMboSetOrigem != null) {
            System.out.println("################ atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                System.out.println("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                System.out.println("****************################ loop ANTES SEM VALIDACAO ");

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("****************################ loop SEM VALIDACAO ");

                for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                    System.out.println("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                    if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                        if (atributosOrigem[contadorAtributo] instanceof String) {

                            if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                System.out.println("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                            } else {
                                System.out.println("################ atributo String = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                            }
                        } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                            System.out.println("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                        } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                            System.out.println("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                        }

                    } else {
                        System.out.println("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                    }

                }

                System.out.println("********************-------*************** antes do owner " + linhaDestino.getOwner().getName());
                System.out.println("********************-------*************** antes do owner " + linhaDestino.getName());

                aMboSetDestinoFilho.setOwner(linhaDestino);

                System.out.println("********************-------*************** depois do owner");

                linhaDestinoFilho = (Mbo) aMboSetDestinoFilho.add();

                System.out.println("********************-------*************** depois do add invbalance");


            }
        }

    }

    public static void adiciona(MboSet aMboSetOrigem, MboSet aMboSetDestino, Object[] atributosOrigem, String[] atributosDestino) throws MXException, RemoteException, Exception {

        System.out.println("************** adiciona()");

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;
        Mbo linhaDestinoFilho;

        if (aMboSetOrigem != null) {
            System.out.println("################ atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                System.out.println("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                //System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                //System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                //System.out.println("****************################ loop ANTES SEM VALIDACAO ");

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("****************################ loop SEM VALIDACAO ");
                //System.out.println("****************################ loop SEM VALIDACAO ");
                //System.out.println("****************################ loop SEM VALIDACAO ");
                //System.out.println("****************################ loop SEM VALIDACAO ");
                //System.out.println("****************################ loop SEM VALIDACAO ");

                for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                    System.out.println("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                    if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                        if (atributosOrigem[contadorAtributo] instanceof String) {

                            if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                System.out.println("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                            } else {
                                System.out.println("################ atributo String = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                            }
                        } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                            System.out.println("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                        } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                            System.out.println("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                        }

                    } else {
                        System.out.println("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                    }

                }

                System.out.println("################ adiciona() FIM");


                //aMboSetDestinoFilho.setOwner(linhaDestino);


                //linhaDestinoFilho = (Mbo) aMboSetDestinoFilho.add();


            }
        }

    }

    public static void adiciona(MboSet aMboSetOrigem, MboSet aMboSetDestino, Object[] atributosOrigem, String[] atributosDestino, String[] nomeCampoAdicional, Object[] valorcampoAdicional) throws MXException, RemoteException, Exception {

        ////System.out.println("************** atualizaAtributo()");

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;

        if (aMboSetOrigem != null) {
            ////System.out.println("################ atualizaAtributo() ENTROU na condicao");
            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                ////System.out.println("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                ////System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                ////System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                ////System.out.println("****************################ loop ANTES SEM VALIDACAO ");
                ////System.out.println("****************################ loop ANTES SEM VALIDACAO ");

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                ////System.out.println("****************################ loop SEM VALIDACAO ");
                ////System.out.println("****************################ loop SEM VALIDACAO ");
                ////System.out.println("****************################ loop SEM VALIDACAO ");
                ////System.out.println("****************################ loop SEM VALIDACAO ");
                ////System.out.println("****************################ loop SEM VALIDACAO ");
                for (int j = 0; j < nomeCampoAdicional.length; j++) {
                    linhaDestino.setValue(nomeCampoAdicional[j], (String) valorcampoAdicional[j]);
                }

                for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                    ////System.out.println("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                    if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                        if (atributosOrigem[contadorAtributo] instanceof String) {

                            if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                ////System.out.println("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                            } else {
                                ////System.out.println("################ atributo String = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                            }
                        } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                            ////System.out.println("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                        } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                            ////System.out.println("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                            linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                        }

                    } else {
                        ////System.out.println("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                    }

                }



            }
        }

    }

    public static void duplicaMBO(MboSetRemote aMboSetOrigem, MboSetRemote aMboSetDestino, String[] atributos, String[] attrsAuxiliares, String[] valoresAuxiliares) throws MXException, RemoteException {

        Mbo linhaOrigem;
        Mbo linhaDestino;

        //System.out.println("############### Entrou no DuplicaMBO()");
        if (aMboSetOrigem != null && aMboSetDestino != null) {
            //System.out.println("################### DuplicaMBO - MboSet Origem Nao he nulo");

            for (int i = 0; i < aMboSetOrigem.count(); i++) {
                //System.out.println("****************################ loop i " + i);
                linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);

                //System.out.println("################### Tamanho do MBODestino=" + aMboSetDestino.count());

                linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);

                for (int j = 0; j < atributos.length; j++) {
                    //System.out.println("################### Setando (" + linhaOrigem.getString(atributos[j]) + ") no campo " + atributos[j]);
                    linhaDestino.setValue(atributos[j], linhaOrigem.getString(atributos[j]));
                }

                if (attrsAuxiliares != null) {
                    copiaValorMBOSet(linhaDestino, attrsAuxiliares, valoresAuxiliares);
                }

            }
        }

    }

    public static void copiaValorMBOSet(MboRemote aMboDestino, String[] atributos, String[] valores) throws MXException, RemoteException {


        if (aMboDestino != null) {
            //System.out.println("################### DuplicaMBO - MboSet Origem N�o � nulo");


            for (int j = 0; j < atributos.length; j++) {
                //System.out.println("################### Setando (" + aMboDestino.getString(atributos[j]) + ") no campo " + atributos[j]);
                aMboDestino.setValue(atributos[j], valores[j]);
            }
        }

    }

    public static Long somaValor(MboSetRemote aMboSet, String atributo) throws MXException, RemoteException {
        //System.out.println("************** somaValor()");

        Mbo linha;

        long retorno = 0;

        if (aMboSet != null) {
            //System.out.println("************** atualizaAtributo() ENTROU na condicao total de linhas = " + aMboSet.count());
            for (int i = 0; i < aMboSet.count(); i++) {
                //System.out.println("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                //System.out.println("************** loop b " + i);

                if (!linha.toBeDeleted()) {
                    retorno += linha.getLong(atributo);
                }
                //System.out.println("************** loop c " + retorno);
            }
        }

        //System.out.println("************** RETORNO = " + retorno);
        return retorno;
    }

    public static double somaValor(String atributo, MboSetRemote aMboSet) throws MXException, RemoteException {

        Mbo linha;

        double retorno = 0;

        if (aMboSet != null) {
            //System.out.println("------)) total de linhas " + aMboSet.count());
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if (!linha.toBeDeleted()) {
                    //System.out.println("-------) linha " + i);
                    //System.out.println("-------) valor " + linha.getDouble(atributo));
                    retorno += linha.getDouble(atributo);
                }
            }
        }
        return retorno;
    }

    public static Double somaValor(MboSetRemote aMboSet, String atributo, String idEntidade, String valorIdEntidade) throws MXException, RemoteException {
        //System.out.println("************** somaValor()");

        Mbo linha;

        Double retorno = 0D;

        if (aMboSet != null) {
            //System.out.println("************** atualizaAtributo() ENTROU na condicao total de linhas = " + aMboSet.count());
            for (int i = 0; i < aMboSet.count(); i++) {
                //System.out.println("************** loop " + i);
                linha = (Mbo) aMboSet.getMbo(i);
                //System.out.println("************** loop b " + i);
                //System.out.println("************** IdEntidade: " + linha.getInt(idEntidade) + " Valor passado: " + valorIdEntidade);
                if ((!linha.toBeDeleted()) && (!linha.getString(idEntidade).equals(valorIdEntidade))) {
                    //System.out.println("************** IdEntidade Entrou");
                    retorno += linha.getDouble(atributo);
                }
                //System.out.println("************** loop c " + retorno);
            }
        }

        //System.out.println("************** RETORNO = " + retorno);
        return retorno;
    }

    public static void naoPermiteDeletar(MboSetRemote aMboSet) throws MXException, RemoteException {
        //System.out.println("---- deletembo int");
        for (int i = 0; i < aMboSet.count(); i++) {
            //System.out.println("---- deletembo i " + i);
            if (aMboSet.getMbo(i).toBeDeleted()) {
                aMboSet.getMbo(i).setDeleted(false);
            }

        }
    }

    public static int contaRegistros(MboSetRemote mboSet, String atributo, boolean valor) throws MXException, RemoteException {
        int contador = 0;

        for (int i = 0; i < mboSet.count(); i++) {
            if (mboSet.getMbo(i).getBoolean(atributo) == valor) {
                contador++;
            }

        }

        return contador;
    }

    public static void adicionaInventory(MboSet aMboSetOrigem, MboSet aMboSetDestino, MboSet aMboSetDestinoFilho, Object[] atributosOrigem, String[] atributosDestino, Object[] atributosOrigemFilho, String[] atributosDestinoFilho) throws MXException, RemoteException, Exception {


        System.out.println("************** atualizaAtributo()");

        if (atributosOrigem.length != atributosDestino.length) {

            throw new Exception("Número de atributos inválido!");

        }

        Mbo linhaOrigem;
        Mbo linhaDestino;
        Mbo linhaDestinoFilho;

        if (aMboSetDestino.count() == 0) {
             System.out.println("----- inserindo Inventory");
            if (aMboSetOrigem != null) {
                System.out.println("################ atualizaAtributo() ENTROU na condicao");
                for (int i = 0; i < aMboSetOrigem.count(); i++) {
                    System.out.println("****************################ loop i " + i);
                    linhaOrigem = (Mbo) aMboSetOrigem.getMbo(i);
                    System.out.println("****************################ loop ANTES SEM VALIDACAO ");

                    linhaDestino = (Mbo) aMboSetDestino.add(MboConstants.NOVALIDATION_AND_NOACTION);
                    System.out.println("****************################ loop SEM VALIDACAO ");

                    for (int contadorAtributo = 0; contadorAtributo < atributosOrigem.length; contadorAtributo++) {
                        System.out.println("################ loop contador " + contadorAtributo + " de " + atributosOrigem.length);
                        if (!linhaDestino.getMboValue(atributosDestino[contadorAtributo]).isFlagSet(MboConstants.READONLY)) {

                            if (atributosOrigem[contadorAtributo] instanceof String) {

                                if (((String) atributosOrigem[contadorAtributo]).contains("#")) {

                                    System.out.println("################ atributo String = " + atributosDestino[contadorAtributo] + " USANDO = " + ((String) atributosOrigem[contadorAtributo]).substring(1));
                                    linhaDestino.setValue(atributosDestino[contadorAtributo], ((String) atributosOrigem[contadorAtributo]).substring(1));

                                } else {
                                    System.out.println("################ atributo String = " + atributosDestino[contadorAtributo]);
                                    linhaDestino.setValue(atributosDestino[contadorAtributo], linhaOrigem.getString((String) atributosOrigem[contadorAtributo]));
                                }
                            } else if ((atributosOrigem[contadorAtributo] instanceof Integer)) {
                                System.out.println("################ atributo Integer = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], (Integer) atributosOrigem[contadorAtributo]);
                            } else if (atributosOrigem[contadorAtributo] instanceof MboValue) {
                                System.out.println("################ atributo MboValue = " + atributosDestino[contadorAtributo]);
                                linhaDestino.setValue(atributosDestino[contadorAtributo], ((MboValue) atributosOrigem[contadorAtributo]).getString());
                            }

                        } else {
                            System.out.println("################ atributo readonly = " + atributosDestino[contadorAtributo]);
                        }

                    }

                    System.out.println("********************-------*************** antes do owner " + linhaDestino.getOwner().getName());
                    System.out.println("********************-------*************** antes do owner " + linhaDestino.getName());

                    aMboSetDestinoFilho.setOwner(linhaDestino);

                    System.out.println("********************-------*************** depois do owner");

                    linhaDestinoFilho = (Mbo) aMboSetDestinoFilho.add();
                    System.out.println("------ mboSetFilho " + atributosDestinoFilho.length);
                    for (int j = 0; j < atributosDestinoFilho.length; j++) {
                        System.out.println("------ j " + j);
                        System.out.println("------ vals " + atributosDestinoFilho[j]);
                        System.out.println("------ vals " + (String) atributosOrigemFilho[j]);
                        linhaDestinoFilho.setValue(atributosDestinoFilho[j], (String) atributosOrigemFilho[j], MboConstants.NOVALIDATION_AND_NOACTION);

                    }

                    System.out.println("********************-------*************** depois do add invbalance");


                }
            }

        } else {
            System.out.println("----- apenas InvBalances");
            aMboSetDestinoFilho.setOwner(aMboSetDestino.getMbo(0));

            System.out.println("********************-------*************** depois do owner");

            linhaDestinoFilho = (Mbo) aMboSetDestinoFilho.add();
            System.out.println("------ mboSetFilho " + atributosDestinoFilho.length);
            for (int j = 0; j < atributosDestinoFilho.length; j++) {
                System.out.println("------ j " + j);
                System.out.println("------ vals " + atributosDestinoFilho[j]);
                System.out.println("------ vals " + (String) atributosOrigemFilho[j]);
                linhaDestinoFilho.setValue(atributosDestinoFilho[j], (String) atributosOrigemFilho[j], MboConstants.NOVALIDATION_AND_NOACTION);

            }

            System.out.println("********************-------*************** depois do add invbalance");

        }
    }

    /**
     * Permite como marcado apenas o mbo corrente
     * 
     * @param mboSet MboSet a ser pecorrido
     * @param atributoYorN atributo YorN a ser verificado
     * @throws MXException
     * @throws RemoteException
     */
    public static void setSelecionaAtualDeselecionarDemais(MboSetRemote mboSet, String atributoYorN) throws MXException, RemoteException {
        MboRemote mbo;

        int linhaAtual =  mboSet.getCurrentPosition();
//        System.out.println("---setSelecionaAtualDeselecionarDemais YorN "+atributoYorN);
//        System.out.println("---setSelecionaAtualDeselecionarDemais linhaAtual "+linhaAtual);
//        System.out.println("---setSelecionaAtualDeselecionarDemais count "+mboSet.count());
        for (int i = 0; ((mbo = mboSet.getMbo(i)) != null); i++) {
//            System.out.println("---setSelecionaAtualDeselecionarDemais i "+i);
//            System.out.println("---setSelecionaAtualDeselecionarDemais YorN "+mbo.getBoolean(atributoYorN));
            if ((i != linhaAtual) && (mbo.getBoolean(atributoYorN))) {
//                System.out.println("---setSelecionaAtualDeselecionarDemais desmarcar ");
                mbo.setValue(atributoYorN, false, MboConstants.NOACCESSCHECK);
            }

        }

    }
}
