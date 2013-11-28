package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Data;

/**
 *
 * @author Dyogo
 *
 */
public class SolicitacaoViagens extends psdi.webclient.system.beans.AppBean {

    public SolicitacaoViagens() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
    	super.SAVE();
    	
    	int iDet = getMbo().getMboSet("RL01DETSOL").count() -1;
    	
		//Antes de começar, retira os valores de Diária (tempo de permanencia e valor diaria) do último trecho
    	if (iDet > 0) {
    		getMbo().getMboSet("RL01DETSOL").getMbo(iDet).setValueNull("SITEMPER");
    		getMbo().getMboSet("RL01DETSOL").getMbo(iDet).setValueNull("DCVALDIA");
    	}
		
        System.out.println("############# Entrei no SAVE");
        
        //Não aceita origem e destino iguais
        int iTrechos = getMbo().getMboSet("RL01DETSOL").count();
        System.out.println("################### Quantidade de Detalhes de Viagem:" +  getMbo().getMboSet("RL01DETSOL").count());
        for (int i=0; i < iTrechos; i++) {
    		System.out.println("############ ENTREI NO FOR(" + i + ")");
    		String sOrigem = getMbo().getMboSet("RL01DETSOL").getMbo(i).getString("ALLOCORI");
    		String sDestino = getMbo().getMboSet("RL01DETSOL").getMbo(i).getString("ALLOCDES");
    		System.out.println("########## Origem:" + sOrigem);
    		System.out.println("########## Destino:" + sDestino);
    		
    		if (sOrigem.equalsIgnoreCase(sDestino)) {
    			throw new MXApplicationException("passagens", "CampoOrigemEDestinoNaoPodemSerIguais");
    		}
        }
        
        double dValorTotal = 0;
        double dValorTotalDiaria = 0;
        double dValorTotalLocomocao = 0;
        double iTotalDias = 0;
        MboSetRemote mboSetR = getMbo().getMboSet("RL01DETSOL");
        System.out.println("#Total de trechos: " + mboSetR.count());
        
        //Deve existir pelo menos um Trecho
        if (mboSetR.count() < 1) {
        	throw new MXApplicationException("passagens", "DeveExistirPeloMenosUmTrecho");
        }
        
        //Se tiver apenas um trecho, calcular os valores
        if (mboSetR.count() == 1) {
        	double sValorDia = getMbo().getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA");
        	//double sValorDia = getMbo().getMboSet("RL01PERSON").getMbo(0).getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA");
        	//UPNOMCAT
        	calculaDespesaLocomocao(getMbo(), 1, 0, sValorDia);
        	
        	//Define a meia diária
    		MboSetRemote mboSetRNew = getMbo().getMboSet("RL01DETSOL").getMbo(0).getMboSet("RL01LOC");
    		System.out.println("########### REL ANTES=" +mboSetRNew.count());
    		mboSetRNew.setWhere("tblocid =" + getMbo().getMboSet("RL01DETSOL").getMbo(0).getInt("ITLOCDES"));
    		mboSetRNew.reset();
    		System.out.println("########### REL DEPOIS=" +mboSetRNew.count());
    		double dAjusteDiaria = mboSetRNew.getMbo(0).getDouble("DCVALAJU");
    		System.out.println("############# SJUSTE=" + dAjusteDiaria);
	        double sValorDiaria = sValorDia * dAjusteDiaria;
	        System.out.println("########### VALOR DIARIA=" + sValorDiaria);

	        getMbo().setValue("DCMEIDIA", sValorDiaria/2);
	        
        }
        
        

        //Calcula o valor total - Valor total das passagens + Valor de Locomoção + Valor da Passagem
        for (int i = 0; i < mboSetR.count(); i++) {
        	double iDiasPerm = mboSetR.getMbo(i).getInt("SITEMPER");
        	double dValorDiaria = mboSetR.getMbo(i).getDouble("DCVALDIA");
        	//O Valor da diária já está totalizado
        	dValorTotalDiaria += dValorDiaria;
        	dValorTotal += dValorDiaria;
        	iTotalDias += iDiasPerm;
        	
        	//Soma também o valor de locomoção
        	//Regra ajustada a pedido do Junior no dia 7/12/2011 e executada por Dyogo
        	//Não haverão mais despesas com locomoção por trecho, O valor da locomoção agora é referente na diária do primeiro trecho * temporalidade
        	//double dValorLocomocao = mboSetR.getMbo(i).getDouble("DCDESPLOC");
        	//dValorTotal += dValorLocomocao;
        	//dValorTotalLocomocao += dValorLocomocao;
        	
        	//Somta também o valor da Passagem
        	//Retirado
        	//double dValorPassagem = mboSetR.getMbo(i).getDouble("DCVALPAS");
        	//dValorTotal += dValorPassagem;
        	
        	//Após isso, verifica se a Data está inválida
        	Date dataIni = mboSetR.getMbo(i).getDate("DTDTAINI");
        	
        	//Comentado no dia 04/05 por solicitação via e-mail do Junior
        	/* 
        	Date dataFim = mboSetR.getMbo(i).getDate("DTDATFIM");
    		int dias = Data.recuperaDiasEntreDatas(dataIni, dataFim);
    		if (dias < 0) {
    			throw new MXApplicationException("passagens", "DataInicialMaiorQueDataFinal");
    		}*/
    		
    		//Verifica se ele preencheu todas as datas
    		if (dataIni == null) {
    			throw new MXApplicationException("passagens", "PreenchaDataEmbarque");
    		}
    		if (Data.dataInicialMenorFinal(dataIni, new Date())) {
    			throw new MXApplicationException("passagens", "DataEmbarqueMenorDataAtual");
    		}
    		
    		
    		//Ainda verificar se a Data Final do Trecho anterior é maior que a Data Inicial do Atual
    		//Foi alterado dia 04/05 só existindo datas de embarque
    		double dias;
    		if (i > 0) {
    			Date dtInicialAnt = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getDate("DTDTAINI");
    			//Date dtFinalAnt = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getDate("DTDATFIM");
				Date dtInicial = getMbo().getMboSet("RL01DETSOL").getMbo(i).getDate("DTDTAINI");
				//Date dtFinal = getMbo().getMboSet("RL01DETSOL").getMbo(i).getDate("DTDATFIM");
				
				String hrAnterior = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getString("DSPERSAI");
				String hrAtual = getMbo().getMboSet("RL01DETSOL").getMbo(i).getString("DSPERSAI");
				
				//dias = Data.recuperaDiasEntreDatas(dtInicialAnt, dtInicial);
				if (Data.dataInicialMenorFinal(dtInicial, dtInicialAnt)) {
					throw new MXApplicationException("passagens", "DataInicialTrechoMaiorQueInicialAnt");
				}
				
/*				if (dias > 1) {
					throw new MXApplicationException("passagens", "DataIniMaiorQueUmDiaAnterior");
				}

				dias = Data.recuperaDiasEntreDatas(dtFinalAnt, dtFinal);
				if (dias > 0) {
					dias = Data.recuperaDiasEntreDatas(dtFinalAnt, dtInicial);
					if (dias == 0) {
						throw new MXApplicationException("passagens", "DataIniDeveSerMaiorQueFinalAnt");
					}
				}*/
				
				//Regra adicionada dia 04/05 a pedido do Junior
				dias = Data.recuperaDiasEntreDatas(dtInicialAnt, dtInicial);
				if (hrAnterior != null && hrAtual != null) {
					if (dias == 0 && hrAnterior.equals(hrAtual)) {
						throw new MXApplicationException("passagens", "DataEmbarqueEHoraNaoPodemSerIguais");
					}
				}
    		}
        }
        
        //Só é permitido até 20 dias de viagem por solicitação
        String sTotalDias = MXServer.getMXServer().getProperty("id2.dp.totalpermanencia");
        
        if (iTotalDias > Integer.parseInt(sTotalDias)) {
        	throw new MXApplicationException("passagens", "NumeroMaximoDeDiariasAtingido", new String[]{sTotalDias});
        }
        
        //Soma o Valor total a Meia diária calculada
        dValorTotal += getMbo().getDouble("DCMEIDIA");
        
        //Após acumular os valores dos trechos, somar mais uma diária da categoria
        //Regra suprimida
        //double dValorCategoria = getMbo().getMboSet("RL01PERSON").getMbo(0).getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA");
        //dValorTotal += dValorCategoria;
        
        //E ainda somar a porcentagem de permanência (Valor da Diária * Porcentagem)
        //Regra suprimida, foi colocada no listener, para cálculo da locomoção
		MboSetRemote mboSetRT = getMbo().getMboSet("RL01TEMP");
		System.out.println("### Total antes: " + mboSetRT.count() + "| Dias: " + iTotalDias);
		mboSetRT.setWhere("sitemp =" + iTotalDias);
		mboSetRT.reset();
		System.out.println("### Total depois: " + mboSetRT.count());
		double dPercent = mboSetRT.getMbo(0).getDouble("SIPERAJU");
		System.out.println("# Percentual:" + dPercent);
		//dValorTotal += ((dValorCategoria * dPercent)/100);
        
    	//Regra ajustada a pedido do Junior no dia 7/12/2011 e executada por Dyogo
    	//Não haverão mais despesas com locomoção por trecho, O valor da locomoção agora é referente na diária do primeiro trecho * temporalidade
        System.out.println("######## Permanencia primeirot recho = " + mboSetR.getMbo(0).getDouble("SITEMPER"));
        System.out.println("######## Diárias primeiro trecho = " + mboSetR.getMbo(0).getDouble("DCVALDIA"));
        dValorTotalLocomocao = ((mboSetR.getMbo(0).getDouble("DCVALDIA") / mboSetR.getMbo(0).getDouble("SITEMPER")) * dPercent) / 100;
        dValorTotal += dValorTotalLocomocao;
        
        System.out.println("############# Valor total de Locomoção = " + dValorTotalLocomocao);
        System.out.println("############# Valor total = " + dValorTotal);
        System.out.println("############# Valor diária primeiro trecho = " +mboSetR.getMbo(0).getDouble("DCVALDIA"));
        getMbo().setValue("DCTOTDESLOC", dValorTotalLocomocao);
        getMbo().setValue("MXTOTDIA", dValorTotalDiaria);
        getMbo().setValue("DCVALTOT", dValorTotal);
        
        //Cria um novo registro na Conta Corrente
        System.out.println("############ Atualizando Conta Corrente...");
        criarNovaContaCorrente(iTotalDias);


        return super.SAVE();
    }

	private void criarNovaContaCorrente(double iTotalDias) throws MXException, RemoteException {
		
    	MboSet setDiaVia = (MboSet) getMbo().getMboSet("RL01DIAVIA");
    	
    	if (setDiaVia == null || setDiaVia.count() == 0) {
    		throw new MXApplicationException("passagens", "PeriodoAtivoInexistente");
    	}
    	
    	MboSet setContaCorrente = (MboSet) setDiaVia.getMbo(0).getMboSet("RL01CONCOR");
    	
    	System.out.println("############ Tamanho Conta Corrente = "  + setContaCorrente.count());
		boolean bExiste = false;
		for (int i=0; i< setContaCorrente.count(); i++) {
    		Mbo mboConta = (Mbo) setContaCorrente.getMbo(i);
    		if (mboConta.getString("MXSTATUS").equals("PREVISAO") && mboConta.getString("TBSOLVIAID").equals(getMbo().getString("TBSOLVIAID"))) {
	    		mboConta.setValue("MXCREDIA", Double.parseDouble(iTotalDias+"") + 0.5);
	    		//setContaCorrente.save();
	    		bExiste = true;
    		}
		}
   		if (bExiste == false) {
   			criaRegistroContaCorrente(setContaCorrente, setDiaVia, iTotalDias);
    	}
   		atualizaDiarias(setContaCorrente, (Mbo)setDiaVia.getMbo(0));
    	
	}

	private void criaRegistroContaCorrente(MboSet setContaCorrente, MboSet setDiaVia, double iTotalDias) throws MXException, RemoteException {
		Mbo mboConta = (Mbo)setContaCorrente.add();
		mboConta.setValue("TBSOLVIAID", getMbo().getString("TBSOLVIAID"));
		mboConta.setValue("PERSONID", getMbo().getString("UPSOL"));
		mboConta.setValue("MXTBDIAVIAID", setDiaVia.getMbo(0).getString("MXTBDIAVIAID"));
		mboConta.setValue("MXSTATUS", "PREVISAO");
		mboConta.setValue("MXDATOPE", new Date());
		mboConta.setValue("MXTIPOPE", "DEBITO");
		mboConta.setValue("MXCREDIA", Double.parseDouble(iTotalDias+"") + 0.5);
		System.out.println("############### Salvando...");
    	//setContaCorrente.save();
	}


	public void atualizaDiarias(MboSet setContaCorrente, Mbo mboMembro) throws MXException, RemoteException {
		
		double iDiaria = mboMembro.getDouble("MXSALDOINI");
		double iDiariasCons = 0;
		System.out.println("############# Diaria = " + iDiaria);
		if (setContaCorrente != null) {
			for (int k=0; k < setContaCorrente.count(); k++ ) {
				Mbo mboConta = (Mbo) setContaCorrente.getMbo(k);
				System.out.println("############ Valor do Crédito: " + mboConta.getInt("MXCREDIA"));
				if (mboConta.getString("MXTIPOPE").equals("CREDITO")) {
					iDiaria += mboConta.getDouble("MXCREDIA");
				} else if (mboConta.getString("MXTIPOPE").equals("DEBITO") && !mboConta.getString("MXSTATUS").equals("CANCELADO")) {
					iDiaria -= mboConta.getDouble("MXCREDIA");
					iDiariasCons += mboConta.getDouble("MXCREDIA");
				}
			}
		}
		
		System.out.println("################ Valor da Diária=" + iDiaria + "|Saldo Atual=" + mboMembro.getDouble("MXSALDO")); 
		
		if (iDiaria < 0) {
			throw new MXApplicationException("passagens", "NumeroDiariasInferiorSaldo");
		}
		
		System.out.println("############### Setando o MXSaldo = " + iDiaria);
		mboMembro.setValue("MXSALDO", iDiaria);
		mboMembro.setValue("MXDIACON", iDiariasCons);
		
	}
	
	@Override
    public synchronized void listenerChangedEvent(DataBean speaker) {
    
    	try {
    		
    		recalculaValoresListener();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	// TODO Auto-generated method stub
    	super.listenerChangedEvent(speaker);
    }
	
	

	private void recalculaValoresListener() throws MXException, RemoteException {
		
		//Começar o valor de Local de Origem da linha de Detalhe de viagem com o Local de Destino anterior
		int iDet = getMbo().getMboSet("RL01DETSOL").count() -1;
		
		
		System.out.println("########### Quantidade dde Detalhe de viagem: " + iDet);
		if (iDet == 0) {
				//Tem apenas um trecho. Adicionar o primeiro trecho como meia permanência
				getMbo().getMboSet("RL01DETSOL").getMbo(0).setValue("SITEMPER", 0.5);
		}
		if (iDet > 0) {
			System.out.println("###### Entrei, é maior");
			String sLocalDestinoAnt = getMbo().getMboSet("RL01DETSOL").getMbo(iDet-1).getString("ALLOCDES");
			String sLocalOrigem = getMbo().getMboSet("RL01DETSOL").getMbo(iDet).getString("ALLOCORI");
			System.out.println("## Destino Ant=" + sLocalDestinoAnt + "| Origem atual=" + sLocalOrigem);
			if (sLocalDestinoAnt != null && !sLocalDestinoAnt.trim().equals("") && 
				(sLocalOrigem == null || sLocalOrigem.trim().equals(""))) {
				System.out.println("####### ENTREI, setando o valor do campo");
				getMbo().getMboSet("RL01DETSOL").getMbo(iDet).setValue("ALLOCORI", sLocalDestinoAnt);
			}
		}
		
		System.out.println("############################## POSICAO ATUAL=" + getMbo().getMboSet("RL01DETSOL").getCurrentPosition());
		
		double dias = 0;
		int posicaoAtual = getMbo().getMboSet("RL01DETSOL").getCurrentPosition();
		//Se a pessoa solicitar viagens com dois trechos iguais, datas iguais, só ganhará uma diária			
		//if (iDet > 0) {
		
		//Foi alterado para atualizar todos os registros, sempre que houver alteração
		for (int i=1; i <= iDet; i++) {
			System.out.println("############################## PASSEI AQUI (" + i  + ")");
		
			Date dtInicialAnt = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getDate("DTDTAINI");
			//Date dtFinalAnt = getMbo().getMboSet("RL01DETSOL").getMbo(iDet-1).getDate("DTDATFIM");
			Date dtInicial = getMbo().getMboSet("RL01DETSOL").getMbo(i).getDate("DTDTAINI");
			//Date dtFinal = getMbo().getMboSet("RL01DETSOL").getMbo(iDet).getDate("DTDATFIM");
/*
			if (Data.recuperaDiasEntreDatas(dtInicial, dtInicialAnt) == 0 &&
				Data.recuperaDiasEntreDatas(dtFinal, dtFinalAnt) == 0) {
				getMbo().getMboSet("RL01DETSOL").setValue("SITEMPER", 0);
			}*/
			
			// Calcular o Tempo de Permanência
			System.out.println("############# Verificando datas INI=" + dtInicialAnt + "| FIM=" + dtInicial);
			if (dtInicialAnt != null && dtInicial != null) {
				System.out.println("############ Datas não são nulas!");
				
				//Verifica se é só dia util
				String diaUtil = getMbo().getString("DSPERFIMSEM");
				System.out.println("############### DIA UTIL?? = " + diaUtil);
				if (diaUtil == null || diaUtil.trim().equals("") || diaUtil.trim().equals("02")) {
					dias = Data.recuperaDiasInteirosUteisEntreDatas(dtInicialAnt, dtInicial);
				} else {
					dias = Data.recuperaDiasInteirosEntreDatas(dtInicialAnt, dtInicial);
				}
				
				System.out.println("####### Dias = " + dias);
				//A Data inicial ser maior que a final será validada no Save
				//Se for 0, é o mesmo dia, tratar como 1 dia
				if (dias < 0) {
					dias = 0;
				}
				
				if (i == 1) { //Tem dois trechos
					getMbo().getMboSet("RL01DETSOL").getMbo(i-1).setValue("SITEMPER", dias + 0.5);
				} else {
					getMbo().getMboSet("RL01DETSOL").getMbo(i-1).setValue("SITEMPER", dias);
				}
				reloadTable();
			}
			
	        //Calcular o valor da diária
    		MboSetRemote mboSetR = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getMboSet("RL01LOC");
    		mboSetR.setWhere("tblocid =" + getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getInt("ITLOCDES"));
    		mboSetR.reset();
    		double dAjusteDiaria = mboSetR.getMbo(0).getDouble("DCVALAJU");
    		System.out.println("#D:" + dAjusteDiaria);
	        //double sValorDia = getMbo().getMboSet("RL01PERSON").getMbo(0).getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA");
    		double sValorDia = getMbo().getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA");
	        double sValorDiaria = sValorDia * dAjusteDiaria;
	        double sValorDiariaTotal = sValorDiaria * dias;
	        
	        getMbo().getMboSet("RL01DETSOL").getMbo(i-1).setValue("DCVALDIA", sValorDiariaTotal);
	        
	        //Metade do Valor da diária (sem ajuste) vai para o campo DCMEIDIA do primeiro trecho
	        //Dividido pelos dias pois a diária é a diária * dias de permanência 
	        double valorMeia = 0;
	        double diasLinha = 0;
	        if (getMbo().getMboSet("RL01DETSOL").count() > 0) {
	        	//valorMeia = getMbo().getMboSet("RL01DETSOL").getMbo(i-1).getDouble("DCVALDIA") / diasLinha;
	        	//Valor da Meia alterado em 07/12/2011 a pedido enviado por email pelo Junior, realizado por Dyogo
	        	//A regra agora é pegar o valor da diária do último trecho, dividido por 2 (meia)
	        	diasLinha = getMbo().getMboSet("RL01DETSOL").getMbo(iDet-1).getInt("SITEMPER");
	        	valorMeia = getMbo().getMboSet("RL01DETSOL").getMbo(iDet-1).getDouble("DCVALDIA") / diasLinha;
	        }
	        System.out.println("################ Valor da Meia diária: " + valorMeia);
	        if (valorMeia != 0 && !new Double(valorMeia).isNaN() && !new Double(valorMeia).isInfinite()) { 
	        	getMbo().setValue("DCMEIDIA", valorMeia/2);
	        }
	    	// Verificar o Meio de Transporte. Assim que for colocado, buscar se ele tem despesa com locomoção (RL01DETSOL.RL01METRANS.YNDESLOC)
	    	//e trazer o valor no campo "Despesa com Locomoção"
	        System.out.println("###################### Calculando despesas com locomoção:" + getMbo() + "|" + i + "|" + dias + "|" + sValorDiaria);
	        //calculaDespesaLocomocao(getMbo(), i, dias, sValorDiaria);

	        //Apaga o valor da diária se não tiver tempo de permanência, não válido para a primeira linha
	        System.out.println("############### Dias de Permanência = " + diasLinha);
	        if (diasLinha == 0 ) {
	        	getMbo().getMboSet("RL01DETSOL").getMbo(iDet-1).setValue("DCVALDIA", 0);
	        }
		}
	}

	private void calculaDespesaLocomocao(MboRemote mbo, int posicao, double dias, double sValorDiaria) throws MXException, RemoteException  {
		
		MboSetRemote mboSetR;
		String despDesloc = getMbo().getMboSet("RL01DETSOL").getMbo(posicao-1).getString("DSMETRANS");
		System.out.println("#################### Despesa Desloc: " + despDesloc);

		if (despDesloc != null && !despDesloc.equals("")) {
			
    		mboSetR = getMbo().getMboSet("RL01DETSOL").getMbo(posicao-1).getMboSet("RL01METRANS");
    		mboSetR.setWhere("DSNOM ='" + despDesloc + "'");
    		mboSetR.reset();
			boolean temDespesa = mboSetR.getMbo(0).getBoolean("YNDESLOC");
			
			//Tem Despesa com locomoção
			if (temDespesa) {
				//Recupera a Temporalidade
				MboSetRemote mboSetRT = getMbo().getMboSet("RL01DETSOL").getMbo(posicao-1).getMboSet("RL01TEMP");
				System.out.println("### Total antes: " + mboSetRT.count() + "| Dias: " + dias);
				mboSetRT.setWhere("sitemp =" + dias);
				mboSetRT.reset();
				System.out.println("### Total depois: " + mboSetRT.count());
				double dPercent = mboSetRT.getMbo(0).getDouble("SIPERAJU");
				System.out.println("# Percentual:" + dPercent);
				double dValorTotal = (sValorDiaria * dPercent)/100;
				System.out.println("## Valor total = " + dValorTotal);
		        
		        getMbo().getMboSet("RL01DETSOL").getMbo(posicao-1).setValue("DCDESPLOC", Double.parseDouble(new DecimalFormat("0.00").format(dValorTotal)));
		        reloadTable();
			} else {
				//Retira o valor
				getMbo().getMboSet("RL01DETSOL").getMbo(posicao-1).setValue("DCDESPLOC", "");
		        reloadTable();
			}
		}
		
	}
	
	@Override
	public synchronized void fireDataChangedEvent() {

		System.out.println("######################### FIREDATA CHANGE");
		try {
			System.out.println("################### " + getMbo().getMboSet("RL01CATCOL").getMbo(0).getDouble("DCVALDIA"));
		} catch (Exception e) {
			System.out.println("############## EXCEÇÃO: " + e.getMessage());
		}
		//Recalcular toda a tabela quando for modificado o final de semana
		listenerChangedEvent(getParent());
		super.fireDataChangedEvent();
	}
	
	
}
