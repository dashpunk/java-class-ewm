package br.inf.id2.common.util;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.security.UserInfo;
import psdi.util.MXException;

/**
 *
 * @author Dyogo Dantas
 */
public class Data {
	
	public static int getAno(Date data){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		return cal.get(Calendar.YEAR);
	}

    /**
     * Valida as duas datas passadas
     * @param dtInicial
     * @param dtFinal
     * @return Retorna true se a data inicial for menor que a data final
     */
    public static boolean dataHoraInicialMenorFinal(Date dtInicial, Date dtFinal) {

    	System.out.println("*** dataHoraInicialMenorFinal");
        Calendar cInicial = Calendar.getInstance();
        Calendar cFinal = Calendar.getInstance();
        cInicial.setTime(dtInicial);
        cFinal.setTime(dtFinal);

        if (cInicial.get(Calendar.YEAR) < cFinal.get(Calendar.YEAR)) {
        	System.out.println("*** 1");
            return true;
        }
        if (cInicial.get(Calendar.YEAR) > cFinal.get(Calendar.YEAR)) {
        	System.out.println("*** 2");
        	return false;
        }
        if(cInicial.get(Calendar.DAY_OF_YEAR) < cFinal.get(Calendar.DAY_OF_YEAR)){
        	System.out.println("*** 3");
        	return true;
        }
        if(cInicial.get(Calendar.DAY_OF_YEAR) > cFinal.get(Calendar.DAY_OF_YEAR)){
        	System.out.println("*** 4");
        	return false;
        }

        if(cInicial.get(Calendar.HOUR_OF_DAY) < cFinal.get(Calendar.HOUR_OF_DAY)){
        	System.out.println("*** 5");
        	return true;
        }
        if(cInicial.get(Calendar.HOUR_OF_DAY) > cFinal.get(Calendar.HOUR_OF_DAY)){
        	System.out.println("*** 6");
        	return false;
        }
        
        System.out.println("*** cInicial HORA "+cInicial.get(Calendar.HOUR_OF_DAY));
        System.out.println("*** cFinal HORA "+cFinal.get(Calendar.HOUR_OF_DAY));
        System.out.println("*** cInicial MINUTE "+cInicial.get(Calendar.MINUTE));
        System.out.println("*** cFinal MINUTE "+cFinal.get(Calendar.MINUTE));
        
        return cInicial.get(Calendar.MINUTE) < cFinal.get(Calendar.MINUTE);
    }
	
    /**
     * Valida as duas datas passadas
     * @param dtInicial
     * @param dtFinal
     * @return Retorna true se a data inicial for menor que a data final
     */
    public static boolean dataInicialMenorFinal(Date dtInicial, Date dtFinal) {

        Calendar cInicial = Calendar.getInstance();
        Calendar cFinal = Calendar.getInstance();
        cInicial.setTime(dtInicial);
        cFinal.setTime(dtFinal);

        if (cInicial.get(Calendar.YEAR) < cFinal.get(Calendar.YEAR)) {
            return true;
        }
        if (cInicial.get(Calendar.YEAR) > cFinal.get(Calendar.YEAR)) {
            return false;
        }

        //Chegou aqui pq o Ano he igual
        return cInicial.get(Calendar.DAY_OF_YEAR) < cFinal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Valida as duas datas passadas
     * @param dtInicial
     * @param dtFinal
     * @return Retorna true se a data inicial for menor que a data final
     */
    public static boolean dataEntreInicialFinal(Date dtReferencia, Date dtInicial, Date dtFinal) {

        Calendar cInicial = Calendar.getInstance();
        Calendar cFinal = Calendar.getInstance();
        Calendar cReferencia = Calendar.getInstance();


        cInicial.setTime(dtInicial);
        cFinal.setTime(dtFinal);
        cReferencia.setTime(dtReferencia);

        return ((cReferencia.getTimeInMillis() >= cInicial.getTimeInMillis()) && (cReferencia.getTimeInMillis() <= cFinal.getTimeInMillis()));
    }

    /**
     * Quantidade de meses 
     * @param dataReferencia
     * @return Retorna a quantidade de meses entre a dataReferemcia e a presente data
     */
    public static int getMeses(Date dataReferencia) {

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        d1.setTime(dataReferencia);
        d2.setTime(new Date());

        int anos = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
        int meses = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        int resultado = anos * 12 + meses;

        d1.set(Calendar.YEAR, d2.get(Calendar.YEAR));
        d1.set(Calendar.MONTH, d2.get(Calendar.MONTH));

        if (d1.after(d2)) {
            return resultado - 1;
        } else {
            return resultado;
        }
    }

    /**
     * Retorna a quantidade de Dias entre duas datas
     * @param dataIni Data Inicial
     * @param dataIni Data Fim
     * @return int Retorna a quantidade de Dias entre duas datas
     */
    public static int recuperaDiasEntreDatas(Date dataIni, Date dataFim) {

        if (dataIni == null || dataFim == null) {
            return 0;
        }

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        d1.setTime(dataIni);
        d2.setTime(dataFim);

        /*
        // System.out.println(d2.get(Calendar.DAY_OF_YEAR));
        // System.out.println(d1.get(Calendar.DAY_OF_YEAR));
        int dias = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        return dias;
         *
         */
        long diferenca = d2.getTimeInMillis()
                - d1.getTimeInMillis();
        int tempoDia = 1000 * 60 * 60 * 24;

        long diasDiferenca = diferenca / tempoDia;

        return (int) diasDiferenca;

    }

    /**
     * Retorna a quantidade de Dias UTEIS entre duas datas
     * @param dataIni Data Inicial
     * @param dataIni Data Fim
     * @return int Retorna a quantidade de Dias UTEIS entre duas datas
     */
    public static int recuperaDiasUteisEntreDatas(Date dataIni, Date dataFim) {

        if (dataIni == null || dataFim == null) {
            return 0;
        }
        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        d1.setTime(dataIni);
        d2.setTime(dataFim);

        int dias = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);

        //Pega os dias e acrescenta na primeira data para verificar se � dia util ou n�o
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dataIni);
        int diasUteis = 0;
        int i = 0;
        while (i < dias) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            int ds = cal.get(Calendar.DAY_OF_WEEK);
            if (ds != Calendar.SATURDAY && ds != Calendar.SUNDAY) {
                diasUteis++;
            }
            i++;
        }

        return diasUteis;
    }

    public static Date getDataAcrescimo(Date data, int dias) {

        Calendar aData = Calendar.getInstance();

        aData.setTime(data);

        aData.add(Calendar.DAY_OF_MONTH, dias);

        return aData.getTime();


    }

    public static Date getDiferencaHoras(Date dataInicial, Date dataFinal) {

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        Calendar d3 = Calendar.getInstance();

        d2.setTime(dataInicial);

        d3.setTime(dataFinal);

        d1.setTimeInMillis(d3.getTimeInMillis() - d2.getTimeInMillis());

        d1.setTimeInMillis(d1.getTimeInMillis() + 10800000);

        return d1.getTime();


    }

    public static String getDiferencaHorasString(Date dataInicial, Date dataFinal) {

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        d1.setTime(dataInicial);

        d2.setTime(dataFinal);

        long segundos = (d2.getTimeInMillis() - d1.getTimeInMillis()) / 1000;
        long minutos = segundos / 60;
        long hora = minutos / 60;
        long resto = minutos % 60;

        String sHora = Uteis.adicionaValorEsquerda("" + hora, "0", 2)
                + ":"
                + Uteis.adicionaValorEsquerda("" + resto, "0", 2);

        return sHora;

    }

    public static String getDiferencaDiasString(Date dataInicial, Date dataFinal) {

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        d1.setTime(dataInicial);

        d2.setTime(dataFinal);

        long segundos = (d2.getTimeInMillis() - d1.getTimeInMillis()) / 1000;
        long minutos = segundos / 60;
        long hora = minutos / 60;
        long resto = minutos % 60;

        String sHora = Uteis.adicionaValorEsquerda("" + hora, "0", 2)
                + ":"
                + Uteis.adicionaValorEsquerda("" + resto, "0", 2);

        return sHora;

    }

    public static Date getAcrescimoHoras(Date dataReferencia, int horas, int minutos, int segundos) {

        Calendar d1 = Calendar.getInstance();

        Calendar d2 = Calendar.getInstance();

        Calendar d3 = Calendar.getInstance();

        d2.setTime(dataReferencia);


        // System.out.println("- d2 " + d2.getTime());
        // System.out.println("- d3 " + d3.getTime());
        d2.add(Calendar.HOUR_OF_DAY, horas);
        d2.add(Calendar.MINUTE, minutos);
        d2.add(Calendar.SECOND, segundos);
        // System.out.println("- d2 " + d2.getTime());


        return d2.getTime();

    }

    /**
     * Esse método retorna o total de horas úteis baseado na entidade WORKPERIOD
     * @param dataInicial data inicial
     * @param dataFinal data inicial
     * @return
     */
    public static Date getHorasUteis(Date dataInicial, Date dataFinal, UserInfo user) {
        long retorno = 0;
        try {
            MboSet diasUteis;
            diasUteis = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WORKPERIOD", user);
            //diasUteis.setWhere("workdate between '" + mValue.getMbo().getUserInfo()+ "'");
            diasUteis.reset();

            MboRemote mbo;
            // System.out.println("v4------------- diasUteis " + diasUteis.count());
            // System.out.println("---d1 " + dataInicial);
            // System.out.println("---d2 " + dataFinal);
            boolean primeiro = true;
            for (int i = 0; ((mbo = diasUteis.getMbo(i)) != null); i++) {

                //System.out.println("------ i " + i + " dia " + mbo.getString("workdate") + " horas " + retorno);
                Date dataCorrente = new Date(mbo.getDate("workdate").getTime() + (3600000 * 20));
                Date dataCorrente2 = new Date(mbo.getDate("workdate").getTime());
                //System.out.println("--- dataCorrente "+dataCorrente);
                if (dataEntreInicialFinalB(dataCorrente, dataInicial, dataFinal) || dataEntreInicialFinal(dataCorrente2, dataInicial, dataFinal)) {
                    // System.out.println("--- dentro da faixa");
                    // System.out.println("- d1 " + dataInicial);
                    // System.out.println("- d2 " + dataFinal);
                    // System.out.println("- wd " + mbo.getDate("workdate"));
                    //primeiro dia

                    if ((Data.recuperaDiasEntreDatas(mbo.getDate("workdate"), dataInicial) == 0) && (primeiro)) {

                        // System.out.println("---- primeiro dia");
                        if (dataInicial.getTime() > (mbo.getDate("endtime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime())) {
                        } else if (Data.recuperaDiasEntreDatas(dataInicial, dataFinal) == 0) {
                            Long horas = 0L;
                            if (dataFinal.getTime() > (mbo.getDate("endtime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime())) {
                                horas = mbo.getDate("endtime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime() - dataInicial.getTime();
                            } else {
                                retorno += dataFinal.getTime() - dataInicial.getTime();
                            }

                            if (horas > 0) {
                                retorno += horas;
                            }
                        } else if (dataInicialMenorFinal(dataInicial, mbo.getDate("starttime"))) {
                            // System.out.println("---- foi antes do horário inicial");
                            retorno += mbo.getDate("endtime").getTime() - mbo.getDate("starttime").getTime();
                            // System.out.println("--retorno " + retorno);
                        } else {
                            // System.out.println("---- NAO foi antes do horário inicial");
                            Long horas = mbo.getDate("endtime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime() - dataInicial.getTime();
                            // System.out.println("horas = " + horas);

                            if (horas > 0) {
                                retorno += horas;
                            }
                        }
                        primeiro = false;
                    } //último dia
                    else if (Data.recuperaDiasEntreDatas(mbo.getDate("workdate"), dataFinal) == 0) {
                        // System.out.println("--- Ultimo dia");
                        // System.out.println("a "+mbo.getDate("endtime").getTime());
                        // System.out.println("b "+mbo.getDate("workdate").getTime());
                        Date ref = new Date(mbo.getDate("endtime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime());
                        Date ref2 = new Date(mbo.getDate("starttime").getTime() + 2208978000000L + mbo.getDate("workdate").getTime());
                        // System.out.println("c "+ref);
                        // System.out.println("c2 "+ref2);
                        // System.out.println("d "+dataFinal);


                        if (ref.getTime() < dataFinal.getTime()) {
                            // System.out.println("---- foi depois do horário final");
                            retorno += mbo.getDate("endtime").getTime() - mbo.getDate("starttime").getTime();
                            // System.out.println("--retorno " + retorno);
                        } else {
                            // System.out.println("---- NAO foi depois do horário final");
                            Long horas = dataFinal.getTime() - ref2.getTime();
                            // System.out.println("horas = " + horas);

                            if (horas > 0) {
                                retorno += horas;
                            }
                        }
                    } else {
                        long x = mbo.getDate("endtime").getTime() - mbo.getDate("starttime").getTime();
                        // System.out.println("--- dia dentro da faixa <> de inicial e final " + x / 3600000);
                        // System.out.println("da " + mbo.getDate("endtime").getTime());
                        // System.out.println("db " + mbo.getDate("starttime").getTime());
                        retorno += mbo.getDate("endtime").getTime() - mbo.getDate("starttime").getTime();
                        // System.out.println("--- retorno " + retorno);
                    }
                }
            }
        } catch (MXException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            // System.out.println("--- ex " + ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            // System.out.println("--- ex " + ex.getMessage());
        }
        // System.out.println("----- FIM retorno = " + retorno);

        return new Date(retorno);
    }

    public static Date getDiaUtilWorkPeriod(Date dataInicial, int diasUteis, UserInfo user) throws MXException, RemoteException {
        long retorno = 0;



        GregorianCalendar dataRef = new GregorianCalendar();
        GregorianCalendar dataRefR = new GregorianCalendar();

        dataRefR.setTime(dataInicial);
        dataRef.set(GregorianCalendar.YEAR, dataRefR.get(GregorianCalendar.YEAR));
        dataRef.set(GregorianCalendar.MONTH, dataRefR.get(GregorianCalendar.MONTH));
        dataRef.set(GregorianCalendar.DAY_OF_MONTH, dataRefR.get(GregorianCalendar.DAY_OF_MONTH));
        dataRef.set(GregorianCalendar.HOUR_OF_DAY, 0);
        dataRef.set(GregorianCalendar.MINUTE, 0);
        dataRef.set(GregorianCalendar.SECOND, 0);
        dataRef.set(GregorianCalendar.MILLISECOND, 0);

        MboSet diasUteisMboSet;
        diasUteisMboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WORKPERIOD", user);
        diasUteisMboSet.reset();

        MboRemote mbo;
        boolean contadorAtivado = false;
        int contador = 0;
        for (int i = 0; ((mbo = diasUteisMboSet.getMbo(i)) != null); i++) {

            System.out.println("---vals-----------------------------");
            System.out.println("dr: " + dataRef.getTime() + " - " + dataRef.getTime().getTime());
            System.out.println("dc: " + mbo.getDate("workdate") + " - " + mbo.getDate("workdate").getTime());
            System.out.println("---vals-----------------------------");

            if (contadorAtivado) {
                contador++;
            }

            if ((contador == diasUteis) && (contadorAtivado)) {
                return mbo.getDate("workdate");
            }

            if ((mbo.getDate("workdate").getTime() >= dataRef.getTime().getTime()) && (!contadorAtivado)) {
                contadorAtivado = true;
            }
        }

        // System.out.println("----- FIM retorno = " + retorno);

        return new Date(retorno);
    }

    private static boolean dataEntreInicialFinalB(Date dataCorrente, Date dataInicial, Date dataFinal) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(dataCorrente);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(dataInicial);
        Calendar d3 = Calendar.getInstance();
        d3.setTime(dataFinal);

        /*// System.out.println(d1.get(Calendar.YEAR));
        // System.out.println(d1.get(Calendar.MONTH + 1));
        // System.out.println(d1.get(Calendar.DAY_OF_MONTH));
        // System.out.println(d2.get(Calendar.YEAR));
        // System.out.println(d2.get(Calendar.MONTH + 1));
        // System.out.println(d2.get(Calendar.DAY_OF_MONTH));
        // System.out.println(d3.get(Calendar.YEAR));
        // System.out.println(d3.get(Calendar.MONTH + 1));
        // System.out.println(d3.get(Calendar.DAY_OF_MONTH));
         *
         */

        if (d1.get(Calendar.YEAR) >= d2.get(Calendar.YEAR) && d1.get(Calendar.YEAR) <= d3.get(Calendar.YEAR)) {
            if (d1.get(Calendar.MONTH) >= d2.get(Calendar.MONTH) && d1.get(Calendar.MONTH) <= d3.get(Calendar.MONTH)) {
                if (d1.get(Calendar.DAY_OF_MONTH) >= d2.get(Calendar.DAY_OF_MONTH) && d1.get(Calendar.DAY_OF_MONTH) <= d3.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }

        return false;

    }

    public static Date adicionaDiasData(Date dataIni, int dias) {

        Calendar cIni = Calendar.getInstance();
        cIni.setTime(dataIni);
        cIni.add(Calendar.DAY_OF_MONTH, dias);
        return cIni.getTime();
    }
    
    public static Date adicionaMesesData(Date dataIni, int meses) {

        Calendar cIni = Calendar.getInstance();
        cIni.setTime(dataIni);
        cIni.add(Calendar.MONTH, meses);
        return cIni.getTime();
    }

    /**
     * Retorna a quantidade de Dias entre duas datas, desconsiderando as horas
     * @param dataIni Data Inicial
     * @param dataIni Data Fim
     * @return int Retorna a quantidade de Dias entre duas datas
     */
    public static int recuperaDiasInteirosEntreDatas(Date dataIni, Date dataFim) {

        if (dataIni == null || dataFim == null) {
            return 0;
        }

        Calendar dIni = Calendar.getInstance();
        Calendar dFim = Calendar.getInstance();

        dIni.setTime(dataIni);
        dFim.setTime(dataFim);

        dIni.set(Calendar.HOUR, 0);
        dIni.set(Calendar.MINUTE, 0);
        dIni.set(Calendar.SECOND, 0);
        dIni.set(Calendar.MILLISECOND, 0);
        dIni.set(Calendar.HOUR_OF_DAY, 0);

        dFim.set(Calendar.HOUR, 0);
        dFim.set(Calendar.MINUTE, 0);
        dFim.set(Calendar.SECOND, 0);
        dFim.set(Calendar.MILLISECOND, 0);
        dFim.set(Calendar.HOUR_OF_DAY, 0);

        System.out.println(dIni.getTime() + "|" + dFim.getTime());

        return recuperaDiasEntreDatas(dIni.getTime(), dFim.getTime());
    }
    
    /**
     * Retorna a quantidade de Dias entre duas datas, desconsiderando as horas
     * @param dataIni Data Inicial
     * @param dataIni Data Fim
     * @return int Retorna a quantidade de Dias entre duas datas
     */
    public static int recuperaDiasInteirosUteisEntreDatas(Date dataIni, Date dataFim) {

        if (dataIni == null || dataFim == null) {
            return 0;
        }

        Calendar dIni = Calendar.getInstance();
        Calendar dFim = Calendar.getInstance();

        dIni.setTime(dataIni);
        dFim.setTime(dataFim);

        dIni.set(Calendar.HOUR, 0);
        dIni.set(Calendar.MINUTE, 0);
        dIni.set(Calendar.SECOND, 0);
        dIni.set(Calendar.MILLISECOND, 0);
        dIni.set(Calendar.HOUR_OF_DAY, 0);

        dFim.set(Calendar.HOUR, 0);
        dFim.set(Calendar.MINUTE, 0);
        dFim.set(Calendar.SECOND, 0);
        dFim.set(Calendar.MILLISECOND, 0);
        dFim.set(Calendar.HOUR_OF_DAY, 0);

        System.out.println(dIni.getTime() + "|" + dFim.getTime());

        return recuperaDiasUteisEntreDatas(dIni.getTime(), dFim.getTime());
    }
    
    public static void main (String args[]) {
//    	System.out.println(new Data().recuperaDiasEntreDatas(new Date(), new Date(112, 3, 11)));
//    	System.out.println(new Data().recuperaDiasInteirosEntreDatas(new Date(), new Date(112, 3, 11)));
    	
    	System.out.println("*** "+new Data().adicionaMesesData(new Date(), 4));
    }
}