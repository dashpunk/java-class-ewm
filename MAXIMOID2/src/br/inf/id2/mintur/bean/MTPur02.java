package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;

/**
 *
 * @author ricardo
 * 
 */
public class MTPur02 extends ContPurchAppBean {

    public MTPur02() {
        System.out.println("---mtpur02 17:11");
    }

    /**
     * <h4>Relacionamentos</h4> Apostilamento: CTRLAPOST01 <h4>Regras:</h4> Não
     * permitir mais do que 25% de aditivos Apostilamento, caso esteja marcado
     * que altera valor o campo novo valor global tem que ser maior que zero
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("---save 17:18");
        Date dataAssinatura;
        double pagamentos;
        double totaisEmpenho = 0;
        double totalValorPrevisto;
        Date vencimento;
        Double valor;

        double valorGlobalApostilamento = 0;
        double valorMensalApostilamento = 0;


        boolean mudarValorAtualContrato = false;
        boolean mudarVecimentoGarantia = false;
        boolean mudarValorMensalAtual = false;
        boolean mudarValorAtualGarantia = false;
//        double valorAtualContrato;
        double novoValorMensal;
//        double valorAtualGarantia;
//        Date vencimentoGarantia;
        Date data;
        MboRemote mbo;

//        if (getMbo().isNew()) {
//            MboSet autenticacaoSet;
//            autenticacaoSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("CONTRACTAUTH", sessionContext.getUserInfo());
//            MboRemote mboAutenticacao = autenticacaoSet.add();
//
//            mboAutenticacao.setValue("CONTRACTNUM", getMbo().getString("CONTRACTNUM"));
//            mboAutenticacao.setValue("CVENDORONTRACTNUM", getMbo().getString("MTVENDOR"));
//            mboAutenticacao.setValue("REVISIONNUM", getMbo().getString("REVISIONNUM"));
//            mboAutenticacao.setValue("CONTRACTID", getMbo().getString("CONTRACTID"));
//            mboAutenticacao.setValue("AUTHORGID", "MTUR");
//            mboAutenticacao.setValue("AUTHSITEID", "ST.01");
//            mboAutenticacao.setValue("ORGID", "MTUR");
//            mboAutenticacao.setValue("ISDEFAULT", true);
//            
//            autenticacaoSet.save();
//
////            CONTRACTNUM = CONTRACTNUM
////            VENDOR = MTVENDOR
////            REVISIONNUM = REVISIONNUM
////CONTRACTID = CONTRACTID
////AUTHORGID = 'MTUR'
////            AUTHSITEID = 'ST.01'
////ORGID = 'MTUR'
////ISDEFAULT = '1'
//
//        }

        refreshTable();
        reloadTable();

//        getMbo().getMboSet("MTRL01MTTBTERADI").reset();
        getMbo().getMboSet("MTRL01MTTBTERADI").setOrderBy("MTDATASS desc");

        System.out.println("--- antes do loop final");
        double valorAtualContrato;
        double valorMensalAtual;
        double valorAtualGarantia;
        Date vencimentoGarantia;

        dataAssinatura = getMbo().getDate("MTDATASS");
        Date dataInicio = getMbo().getDate("MTDATINI");
        Date dataFim = getMbo().getDate("MTDATVEN");

        vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
        valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
        valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
        valorMensalAtual = getMbo().getDouble("MTVALMEN");


        boolean valorAlterado = false;
        Date valorAlteradoData = null;
        boolean dataAlterada = false;


        System.out.println("--- inicio loop final " + getMbo().getMboSet("MTRL01MTTBTERADI").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(i)) != null); i++) {
            System.out.println("--- loop final " + i + " " + mbo.getString("MTCONTTIPO"));
            //apenas valor
            System.out.println("valoralterado? " + valorAlterado);
            System.out.println("valorAtualContrato " + valorAtualContrato);
            if (mbo.getString("MTCONTTIPO").equals("1")) {
                if (!valorAlterado) {
                    valorAlteradoData = mbo.getDate("MTDATASS");
                    System.out.println("---ENTROU EM VALOR");
                    if (!mbo.isNull("MTNOVVALGLO")) {
                        valorAtualContrato = mbo.getDouble("MTNOVVALGLO");
                    }
                    if (!mbo.isNull("MTNOVVALMEN")) {
                        valorMensalAtual = mbo.getDouble("MTNOVVALMEN");
                    } else {
                        valorMensalAtual = 0D;
                    }
                    if (!mbo.isNull("MTVALPRES")) {
                        valorAtualGarantia = mbo.getDouble("MTVALPRES");
                    } else {
                        valorAtualGarantia = 0D;
                    }
                }
                valorAlterado = true;
            }
            //apenas prazo
            if (mbo.getString("MTCONTTIPO").equals("2")) {
                if (!dataAlterada) {
                    System.out.println("---ENTROU EM DATA");
                    if (!mbo.isNull("MTDATVENFIA")) {
                        vencimentoGarantia = mbo.getDate("MTDATVENFIA");
                    } else {
                        vencimentoGarantia = null;
                    }
                    if (!mbo.isNull("MTDATINIVIG")) {
                        dataInicio = mbo.getDate("MTDATINIVIG");
                    } else {
                        dataInicio = null;
                    }
                    if (!mbo.isNull("MTDATVEN")) {
                        dataFim = mbo.getDate("MTDATVEN");
                    } else {
                        dataFim = null;
                    }
                    if (!mbo.isNull("MTDATASS")) {
                        dataAssinatura = mbo.getDate("MTDATASS");
                    } else {
                        dataAssinatura = null;
                    }
                }
                dataAlterada = true;
            }

            //ambos
            if (mbo.getString("MTCONTTIPO").equals("3")) {
                if (!valorAlterado) {
                    valorAlteradoData = mbo.getDate("MTDATASS");
                    System.out.println("---ENTROU EM VALOR");
                    if (!mbo.isNull("MTNOVVALGLO")) {
                        valorAtualContrato = mbo.getDouble("MTNOVVALGLO");
                    }
                    if (!mbo.isNull("MTNOVVALMEN")) {
                        valorMensalAtual = mbo.getDouble("MTNOVVALMEN");
                    } else {
                        valorMensalAtual = 0D;
                    }
                    if (!mbo.isNull("MTVALPRES")) {
                        valorAtualGarantia = mbo.getDouble("MTVALPRES");
                    } else {
                        valorAtualGarantia = 0D;
                    }
                }
                valorAlterado = true;
                if (!dataAlterada) {
                    System.out.println("---ENTROU EM DATA");
                    if (!mbo.isNull("MTDATVENFIA")) {
                        vencimentoGarantia = mbo.getDate("MTDATVENFIA");
                    } else {
                        vencimentoGarantia = null;
                    }
                    if (!mbo.isNull("MTDATINIVIG")) {
                        dataInicio = mbo.getDate("MTDATINIVIG");
                    } else {
                        dataInicio = null;
                    }
                    if (!mbo.isNull("MTDATVEN")) {
                        dataFim = mbo.getDate("MTDATVEN");
                    } else {
                        dataFim = null;
                    }
                    if (!mbo.isNull("MTDATASS")) {
                        dataAssinatura = mbo.getDate("MTDATASS");
                    } else {
                        dataAssinatura = null;
                    }
                }
                dataAlterada = true;
            }

        }

        System.out.println("---apostilamento");
        for (int i = 0; ((mbo = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(i)) != null); i++) {
            mbo.setValue("FKCTNUNUMCONTRACTID", getMbo().getInt("CONTRACTID"), MboConstants.NOACCESSCHECK);
        }
        getMbo().getMboSet("CTRLAPOSTILAMENTO01").setOrderBy("CTDTDTAASSINATURA desc");

        System.out.println("---apostilamento count() " + getMbo().getMboSet("CTRLAPOSTILAMENTO01").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(i)) != null); i++) {

            System.out.println("---apostilamento I " + i);
            if (mbo.getBoolean("CTNUFLGALTVLRCONTRATO")) {
                if (valorAlteradoData == null) {
                    valorAtualContrato = mbo.getDouble("CTNUVLRNOVOGLOBAL");
                    break;
                }
                if (Data.dataInicialMenorFinal(valorAlteradoData, mbo.getDate("CTDTDTAASSINATURA"))) {
                    valorAtualContrato = mbo.getDouble("CTNUVLRNOVOGLOBAL");
                    break;
                }
            }
        }
        System.out.println("---apostilamento fim");


        getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACCESSCHECK);
        getMbo().setValue("MTVALMESAT", valorMensalAtual, MboConstants.NOACCESSCHECK);
        getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACCESSCHECK);
        if (vencimentoGarantia == null) {
            getMbo().setValueNull("MTVENGA");
        } else {
            getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACCESSCHECK);
        }
        if (dataInicio == null) {
//            getMbo().setValueNull("MTDATINI");
        } else {
            getMbo().setValue("MTATDATINI", dataInicio, MboConstants.NOACCESSCHECK);
        }
        if (dataFim == null) {
//            getMbo().setValueNull("MTATDATVEN");
        } else {
            getMbo().setValue("MTATDATVEN", dataFim, MboConstants.NOACCESSCHECK);
        }
        if (dataAssinatura == null) {
//            getMbo().setValueNull("MTATDATASS");
        } else {
            getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACCESSCHECK);
        }

        //validar se excede 25% de aditivo
//        double aliqAditivoTotal = 0;
//        for (int i = 0; ((mbo = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(i)) != null); i++) {
//            aliqAditivoTotal += mbo.getDouble("MTPERALTVAL");
//
//        }
//
//        if (aliqAditivoTotal > 25) {
//            throw new MXApplicationException("purchview", "percentualAlteracaoSuperior25");
//        }

//        //data de referencia para os aditivos
//        Date dataUltimoApostilamento = null;
//        System.out.println("-- CTRLAPOSTILAMENTO01 count " + getMbo().getMboSet("CTRLAPOSTILAMENTO01").count());
//        if (getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() > 0) {
//            System.out.println("--- bol " + getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getBoolean("CTNUFLGALTVLRCONTRATO"));
//            if (getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getBoolean("CTNUFLGALTVLRCONTRATO")) {
//                System.out.println("-- data apostilamento " + getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getDate("CTDTDTAASSINATURA"));
//                dataUltimoApostilamento = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getDate("CTDTDTAASSINATURA");
//
//                valorGlobalApostilamento = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getDouble("CTNUVLRNOVOGLOBAL");
//                valorMensalApostilamento = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1).getDouble("CTNUVLRNOVOMENSAL");
//                System.out.println("--- valorGlobalApostilamento " + valorGlobalApostilamento);
//                System.out.println("--- valorMensalApostilamento " + valorMensalApostilamento);
//            }
//        }

        //percorre aditivos
        Double[] valoresUltimoAditivo = new Double[2];
        boolean primeiroAditivoValor = true;

        //        getMbo().getMboSet("MTRL01MTTBTERADI").setOrderBy("MTDATASS");
//        getMbo().getMboSet("MTRL01MTTBTERADI").reset();


        //a ser sugerido ao cliente
//        System.out.println("-- percorrer INICIO");
//        for (int i = 0; ((mbo = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(i)) != null); i++) {
//            System.out.println("-- percorrer I " + i);

//            System.out.println("---tipo aditivo " + mbo.getString("MTCONTTIPO"));
//            if (mbo.getString("MTCONTTIPO").equals("1") || mbo.getString("MTCONTTIPO").equals("3")) {
//
//                Double[] apostilamento = apostilamento(mbo.getDate("MTDATASS"));
//                System.out.println("---valoresUltimoAditivo[0] " + valoresUltimoAditivo[0]);
//
//                //não existe apostilamento
//                if (apostilamento[0] == -1) {
//                    //é o primeiro aditivo
//                    if (primeiroAditivoValor) {
//                        System.out.println("primeiroAditivoValor e sem apostilamento");
//                        apostilamento[0] = getMbo().getDouble("ID2CPTOTALVAL");
//                        apostilamento[1] = getMbo().getDouble("MTVALMEN");
//                    } else {
//                        System.out.println("!primeiroAditivoValor e sem apostilamento");
//                        apostilamento[0] = valoresUltimoAditivo[0];
//                        apostilamento[1] = valoresUltimoAditivo[1];
//                    }
//                }
//
//                double percentualAditivo = (mbo.getDouble("MTPERALTVAL") / 100) + 1;
//
//                System.out.println("---percentualAditivo " + percentualAditivo);
//
//                mbo.setValue("MTNOVVALGLO", apostilamento[0] * percentualAditivo, MboConstants.NOACCESSCHECK);
//                System.out.println("---apostilamento[0] " + apostilamento[0]);
//                valoresUltimoAditivo[0] = mbo.getDouble("MTNOVVALGLO");
//                valoresUltimoAditivo[1] = apostilamento[1];
//                System.out.println("---valoresUltimoAditivo[0]a" + valoresUltimoAditivo[0]);
//
//                //mbo.setValue("MTNOVVALMEN", apostilamento[1]);
//
//                primeiroAditivoValor = false;
//
//            }
//
//        }
//
//        System.out.println("---percorrer FIM");


        //Valor a ser Prestado
        double valorContrato = getMbo().getDouble("ID2CPTOTALVAL");
        double aliquota = getMbo().getDouble("ID2CFXDESEM") / 100;
        getMbo().setValue("ID2VALPREST", valorContrato * aliquota, MboConstants.NOACTION);

        System.out.println("--- Valor a ser Prestado");
        //Data Limite de Vigência
        int dias = getMbo().getInt("MTPRAMAXVIG");
        Date dataLimiteVigencia = null;
        //dataInicio = getMbo().getDate("MTDATINI");
        if (dataInicio != null) {
            dataLimiteVigencia = Data.adicionaDiasData(dataInicio, dias-1);
        }
        getMbo().setValue("DATLIMVIG", dataLimiteVigencia, MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("--- Data Limite de Vigência");

        int relAditivo = getMbo().getMboSet("MTRL01MTTBTERADI").count();
        if (relAditivo > 0) {
//            valorAtualContrato = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDouble("MTNOVVALGLO");
            novoValorMensal = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDouble("MTNOVVALMEN");
//            dataAssinatura = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDate("MTDATASS");
//            valorAtualGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDouble("MTVALPRES");
//            vencimentoGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDate("MTDATVENFIA");
            vencimento = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDate("MTDATVEN");


//            data = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(relAditivo - 1).getDate("MTDATVEN");
        } else {
            System.out.println("--- valores sem aditivo");
            mudarValorAtualContrato = true;
            mudarVecimentoGarantia = true;
            mudarValorMensalAtual = true;
            mudarValorAtualGarantia = true;
            novoValorMensal = getMbo().getDouble("MTVALMEN");
//            dataAssinatura = getMbo().getDate("MTDATASS");
//            valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
//            valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
//            vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            vencimento = getMbo().getDate("ID2DATAVENCFIANCA");

            valor = getMbo().getDouble("ID2CPTOTALVAL");
            data = getMbo().getDate("MTDATVEN");
            System.out.println("--- valores sem aditivo FIM");

        }



        pagamentos = Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));

        System.out.println("--------------------- a regs = " + getMbo().getMboSet("MTRL01CONEMP").count());

        try {
//            System.out.println("--------------------- b regs = " + getMbo().getMboSet("MTRL01CONEMP").getMbo().getMboSet("MTRL01PO").count());
            //totaisEmpenho = Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo().getMboSet("MTRL01PO"));

        	System.out.println("*** ANTES MTRL01CONEMP.count() "+getMbo().getMboSet("MTRL01CONEMP").count());
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
            	System.out.println("*** MTRLNOTAEMP01.count() "+getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRLNOTAEMP01").count());
//                totaisEmpenho += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL01PO"));
            	totaisEmpenho += Executa.somaValor("MTVALOR", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRLNOTAEMP01"));
            }


        } catch (Exception e) {
            totaisEmpenho = 0D;
            System.out.println("--------------------- c exception = " + e.getMessage());
        }


        totalValorPrevisto = Executa.somaValor("MTVALPRE", getMbo().getMboSet("MTRL01PROPAG"));

        int result = super.SAVE();
        System.out.println("################ PRIMEIRO RESULTADO=" + result);

        //a pedido do Bruno do MinTur getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);


        //Pagamentos
        getMbo().setValue("MTPAG", pagamentos, MboConstants.NOACTION);

        //Saldo Atual
        getMbo().setValue("MTSALATU", getMbo().getDouble("MTVALVIG") - pagamentos, MboConstants.NOACTION);

        //Valor total
        getMbo().setValue("MTEMPVALTOT", totaisEmpenho, MboConstants.NOACTION);

        //Pagamentos Vinculados
        getMbo().setValue("MTEMPPAGVIN", pagamentos, MboConstants.NOACTION);

        //Total Previsto
        getMbo().setValue("MTPROPAGTOTPRE", totalValorPrevisto, MboConstants.NOACTION);

        //Total pago
        getMbo().setValue("MTPROPAGTOTPAG", pagamentos, MboConstants.NOACTION);

//a pedido do Bruno getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);

        //Saldo Atual
        getMbo().setValue("MTEMPSALATU", totaisEmpenho - pagamentos, MboConstants.NOACTION);

        //Data de Vencimento
//        getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);

        System.out.println("-- fim de valores");

        //a pedido do Nelson Silva, entretanto, o Bruno do Min Turismo, informou que a regra é diferente
//        for (int i = getMbo().getMboSet("MTRL01MTTBTERADI").count() - 1; ((mbo = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(i)) != null); i--) {
//            if (mbo.getString("MTCONTTIPO").equalsIgnoreCase("APOSTILADO")) {
//                valor = mbo.getDouble("MTNOVVALGLO");
//                data = mbo.getDate("MTDATVEN");
//                break;
//            }
//        }

        //antigo

//        System.out.println("---x1 " + mudarValorAtualContrato);
//        System.out.println("---x2 " + mudarVecimentoGarantia);
//        System.out.println("---x3 " + mudarValorAtualGarantia);
//        System.out.println("---x4 " + mudarValorMensalAtual);
//        System.out.println();
//
//        if (mudarVecimentoGarantia) {
//            getMbo().setValue("MTVENGA", data, MboConstants.NOACCESSCHECK);
//        }
//        if (mudarValorAtualContrato) {
//            System.out.println("--- valor Atual Contrato " + valor);
//            boolean readOnly = getMbo().getMboValueData("MTATCONT").isReadOnly();
//            if (readOnly) {
//                System.out.println("###IS READONLY");
//                getMbo().setFieldFlag("MTATCONT", MboConstants.READONLY, false);
//            }
//            getMbo().setValue("MTATCONT", valor, MboConstants.NOVALIDATION_AND_NOACTION);
//            System.out.println("--- valor Atual Contrato " + getMbo().getDouble("MTATCONT"));
//            getMbo().setFieldFlag("MTATCONT", MboConstants.READONLY, readOnly);
//        }
//        if (mudarValorAtualGarantia) {
//            getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
//        }
//        if (mudarValorMensalAtual) {
//            getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
//        }


        //MTATCONT valor atual contrato
        //MTVALMESAT valor mensal atual
        //MTVALATGA valor atual garantia
        //MTVENGA vencimento da garantia


        super.validate();
        System.out.println("--------------------- FIM SAVE appBean");

        int meses = getMbo().getInt("MTPRAMAXVIG");
        dataInicio = getMbo().getDate("MTDATINI");
        dataLimiteVigencia = adicionaMesesData(dataInicio, meses);
        System.out.println("########### Data antes = " + dataLimiteVigencia);
        long umDia = 24 * 60 * 60 * 1000;
        dataLimiteVigencia.setTime(dataLimiteVigencia.getTime() - umDia);
        System.out.println("########### Data depois = " + dataLimiteVigencia);
        getMbo().setValue("DATLIMVIG", dataLimiteVigencia, MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("-------------apos calcular meses");
        result = super.SAVE();
        System.out.println("################ SEGUNDO RESULTADO=" + result);
        return result;
    }

    private Double[] apostilamento(Date data) throws RemoteException, MXException {
        System.out.println("---apostilamento");
        MboRemote mbo;

        Double[] retorno = new Double[2];
        retorno[0] = -1D;
        retorno[1] = -1D;

        if (getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() == 0) {
//            retorno[0] = getMbo().getDouble("ID2CPTOTALVAL");
//            retorno[1] = getMbo().getDouble("MTVALMEN");
            System.out.println("--- retorna 0");
            return retorno;
        }

        for (int i = getMbo().getMboSet("CTRLAPOSTILAMENTO01").count() - 1; ((mbo = getMbo().getMboSet("CTRLAPOSTILAMENTO01").getMbo(i)) != null); i--) {

            System.out.println("--- apostilamento i " + i);
            System.out.println("--- apostilamento v1 " + data);
            System.out.println("--- apostilamento v2 " + mbo.getDate("CTDTDTAASSINATURA"));
            System.out.println("--- apostilamento v3 " + mbo.getBoolean("CTNUFLGALTVLRCONTRATO"));
            if (Data.dataInicialMenorFinal(data, mbo.getDate("CTDTDTAASSINATURA")) && mbo.getBoolean("CTNUFLGALTVLRCONTRATO")) {
                retorno[0] = mbo.getDouble("CTNUVLRNOVOGLOBAL");
                retorno[1] = mbo.getDouble("CTNUVLRNOVOMENSAL");
            }

        }
        return retorno;
    }

    public Date adicionaMesesData(Date dataIni, int meses) {

        Calendar cIni = Calendar.getInstance();
        cIni.setTime(dataIni);
        cIni.add(Calendar.MONTH, meses);
        return cIni.getTime();
    }
}
