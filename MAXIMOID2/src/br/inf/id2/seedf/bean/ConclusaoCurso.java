package br.inf.id2.seedf.bean;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class ConclusaoCurso extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public ConclusaoCurso() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        //System.out.println("---- INICIO DO LOOP");
        //System.out.println("-------------- total de i " + getMbo().getMboSet("SEERL01MTCOMCUR").count());
        for (int i = 0; i < getMbo().getMboSet("SEERL01MTCOMCUR").count(); i++) {
        //for (int i = 0; i < 1; i++) {
            double cht = 0d;
            double flt = 0d;
            double mf = 0d;

            boolean chtE = false;
            boolean fltE = false;
            boolean mfE = false;

            if (!getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).toBeDeleted()) {

                int mensuradores = getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").count();
                //System.out.println("------------ mensuradores total "+mensuradores);
                for (int j = 0; j < mensuradores; j++) {
                    //System.out.println("--------------= j " + j);
                    //System.out.println("--------------= mens " + getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEMENS"));
                    //System.out.println("--- Inicio loop J");
                    if (getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEMENS").equals("CHT")) {
                        chtE = true;
                        //System.out.println("-------- chtE");
                        if (!getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEUNIMED").equals("HRA")) {
                            throw new MXApplicationException("company", "unidadeMediaMensuradorInvalido", new String[]{"Carga Horaria Total"});
                        }
                        cht = getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getDouble("SEEVALOR");
                        //System.out.println("00000000000 apos cht");
                    }
                    if (getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEMENS").equals("FLT")) {
                        fltE = true;
                        //System.out.println("-------- fltE");
                        if (!getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEUNIMED").equals("HRU")) {
                            throw new MXApplicationException("company", "unidadeMediaMensuradorInvalido", new String[]{"Faltas"});
                        }
                        flt = getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getDouble("SEEVALOR");
                        //System.out.println("00000000000 apos flt");
                    }
                    if (getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEMENS").equals("MF")) {
                        mfE = true;
                        //System.out.println("-------- mfE");
                        if (!getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getString("SEEUNIMED").equals("NT")) {
                            throw new MXApplicationException("company", "unidadeMediaMensuradorInvalido", new String[]{"Media Final"});
                        }
                        mf = getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getMboSet("SEERL01CCMENS").getMbo(j).getDouble("SEEVALOR");
                        //System.out.println("00000000000 apos mfe");
                    }
                }
                //System.out.println("--------------))) contador i = " + i);
                //System.out.println("--------------))) ID = " + getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).getString("SEECOMCURMOD"));
                //System.out.println("--------------))) chtE = " + chtE);
                //System.out.println("--------------))) " + fltE);
                //System.out.println("--------------))) " + mfE);

                if ((!chtE) || (!fltE) || (!mfE)) {
                    throw new MXApplicationException("company", "mensuradorAusente");
                }

                //System.out.println("------ antes deseecperpre");

                double percentual = (flt / cht) * 100;

                //System.out.println("------ percentual "+percentual);
                
                DecimalFormat decimalFormat = new DecimalFormat("0.0");

                getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).setValue("SEECPERPRE", decimalFormat.format(percentual));

                //System.out.println("------ apos deseecperpre");

                String mensao = "";

                if ((mf >= 90) && (mf <= 100)) {
                    mensao = "SS";
                }
                if ((mf >= 70) && (mf < 90)) {
                    mensao = "MS";
                }
                if ((mf >= 50) && (mf < 70)) {
                    mensao = "MM";
                }
                if ((mf >= 30) && (mf < 50)) {
                    mensao = "MI";
                }
                if ((mf >= 01) && (mf < 30)) {
                    mensao = "II";
                }
                if (mf < 1) {
                    mensao = "SS";
                }

                getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(i).setValue("SEECMENCAO", mensao);
            }
        }

        return super.SAVE();
    }
}
