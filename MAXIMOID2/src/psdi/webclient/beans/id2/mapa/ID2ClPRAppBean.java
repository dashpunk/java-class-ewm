package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.pr.PRAppBean;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Executa;
import br.inf.id2.common.util.Uteis;

public class ID2ClPRAppBean extends PRAppBean {

    /**
     * @author Ricardo S Gomes
     */
    public ID2ClPRAppBean() {
    }

    
    @Override
    public synchronized void fireDataChangedEvent(DataBean speaker) {
        super.fireDataChangedEvent(speaker);
        somaStArmazenagem();
        somaStDistribuicao();
    }

    private void somaStArmazenagem() {
        double retorno = 0;
        try {

            for (int i = 0; i < getMbo().getMboSet("PRLINE").count(); i++) {
                //System.out.println("--- prlineid " + getMbo().getMboSet("PRLINE").getMbo(i).getString("PRLINEID"));
                //System.out.println("--- ID2DISTDIRETA " + getMbo().getMboSet("PRLINE").getMbo(i).getString("ID2DISTDIRETA"));

                if ((!getMbo().getMboSet("PRLINE").getMbo(i).isNull("ID2DISTDIRETA")) && (!getMbo().getMboSet("PRLINE").getMbo(i).getString("ID2DISTDIRETA").equalsIgnoreCase("SIM"))) {

                    //System.out.println("--- count do relacionamento " + getMbo().getMboSet("PRLINE").getMbo(i).getMboSet("ID2RLITEREC").count());
                    retorno += Executa.somaValor("MS_QTDENT", getMbo().getMboSet("PRLINE").getMbo(i).getMboSet("ID2RLITEREC"));
                    //System.out.println("--- retorno = " + retorno);
                    if (!getMbo().getMboSet("PRLINE").getMbo(i).isNull("ORDERQTY")) {
                        //System.out.println("--- orderqty " + getMbo().getMboSet("PRLINE").getMbo(i).getDouble("ORDERQTY"));
                        retorno = getMbo().getMboSet("PRLINE").getMbo(i).getDouble("ORDERQTY") - retorno;
                        //System.out.println("--- retorno = " + retorno);
                    }
                } else {
                    //System.out.println("--- SEM SOMA");
                }

                getMbo().getMboSet("PRLINE").getMbo(i).setValue("ST_ARMAZENAGEM", retorno);

                retorno = 0;
            }

        } catch (MXException ex) {
            Logger.getLogger(ID2ClPRAppBean.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("--- a ex " + ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(ID2ClPRAppBean.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("--- b ex " + ex.getMessage());
        }

    }

    private void somaStDistribuicao() {
        double retorno = 0;
        try {
            for (int i = 0; i < getMbo().getMboSet("PRLINE").count(); i++) {
                //System.out.println("+++ prlineid " + getMbo().getMboSet("PRLINE").getMbo(i).getString("PRLINEID"));
                //System.out.println("+++ count do 1o relacionamento " + getMbo().getMboSet("PRLINE").getMbo(i).getMboSet("ID2RLITEDIS").count());
                retorno += Executa.somaValor("MS_QTDDIS", getMbo().getMboSet("PRLINE").getMbo(i).getMboSet("ID2RLITEDIS"));
                //System.out.println("+++ retorno = " + retorno);
                if (!getMbo().getMboSet("PRLINE").getMbo(i).isNull("ORDERQTY")) {
                    //System.out.println("+++ orderqty " + getMbo().getMboSet("PRLINE").getMbo(i).getDouble("ORDERQTY"));
                    retorno = getMbo().getMboSet("PRLINE").getMbo(i).getDouble("ORDERQTY") - retorno;
                    //System.out.println("+++ retorno = " + retorno);
                }
                getMbo().getMboSet("PRLINE").getMbo(i).setValue("ST_DISTRIBUICAO", retorno);
                retorno = 0;
            }
        } catch (MXException ex) {
            Logger.getLogger(ID2ClPRAppBean.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("+++ a ex " + ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(ID2ClPRAppBean.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("+++ b ex " + ex.getMessage());
        }

    }

    /**
     *
     * Sobrescrita do metodo validate BMXZZ0002E
     *<p>
     * 
     * Caso o tipo de localização (ID2TIPLOC) seja igual a '01 limpar os campos (ID2LOCUF, ID2CODMUN, ID2AGLEND). Caso contrário limpar o ID2CODLOC
     *
     * Valida se a data final deve ser igual ou maior que a data inicial
     *
     * @since 19/06/2010
     */
    public int SAVE() throws MXException, RemoteException {
        somaStArmazenagem();
        somaStDistribuicao();
        Uteis.espera("*************** SAVE()");

        if (getMbo().getString("ID2CODCOO") == null || getMbo().getString("ID2CODCOO").equals("")) {
            throw new MXApplicationException("demanda", "ID2CDCOOEstaNuloOuVazio");
        }
        if (getMbo().getString("ID2SEC") == null || getMbo().getString("ID2SEC").equals("")) {
            throw new MXApplicationException("demanda", "ID2SECEstaNuloOuVazio");
        }


        /*

        if (Validar.filhoMaiorQuePai((MboSet) getMbo().getMboSet("PRLINE"), "ID2RELPRLINE", "ORDERQTY", "MS_QTDENT")) {
        throw new MXApplicationException("company", "ID2QNTENTREGAInvalida1");
        }

        if (Validar.filhoMaiorQuePai((MboSet) getMbo().getMboSet("PRLINE.ID2RELPRLINE"), "ID2RELDETREC", "MS_QTDENT", "MS_QTDDIS")) {
        throw new MXApplicationException("company", "ID2QNTENTREGAInvalida2");
        }
         */
        Executa.atualizaAtributo((MboSet) getMbo().getMboSet("MS_RL01LOCREC"), "ID2FLAG", true);
        Executa.atualizaAtributo((MboSet) getMbo().getMboSet("MS_RL01LOCDIS"), "ID2FLAG", true);



        /* //System.out.println("------------ valor de PRLINE.PRNUM " + getMbo().getMboSet("PRLINE").getMbo(0).getString("PRNUM"));
        //System.out.println("---erro aqui");
        //System.out.println("------------ valor de PRLINE.ID2ITEMNUM " + getMbo().getMboSet("PRLINE").getMbo(0).getString("ID2ITEMNUM"));
        //System.out.println("---erro aqui NÃO");


        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC quantos " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").count());
        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC quantos " + getMbo().getMboSet("PRLINE").getMbo().getMboSet("ID2RELPRLINE").count());
        
        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC.PRNUM " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getString("PRNUM"));
        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC.ID2ITEMNUM " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getString("ID2ITEMNUM"));
        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC.LOCRECID " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getString("LOCRECID"));

        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC.MS_TBLOCDIS.ID2ITEMNUM " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getMboSet("ID2RELDETREC").getMbo(0).getString("ID2ITEMNUM"));
        //System.out.println("------------ valor de PRLINE.MS_TBLOCREC.MS_TBLOCDIS.LOCRECID " + getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getMboSet("ID2RELDETREC").getMbo(0).getString("LOCRECID"));

        //System.out.println("----- Antes do save 0");
        getMbo().getMboSet("PRLINE").save();
        //System.out.println("----- Após do save 0");
        //System.out.println("----- Antes do save 1");
        getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").save();
        //System.out.println("----- Após do save 1");
        //System.out.println("----- Antes do save 2");
        //getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("ID2RELPRLINE").getMbo(0).getMboSet("ID2RELDETREC").save();
        //System.out.println("----- Após do save 2");
         */


        /*
        if (Validar.maiorQue((MboSet) getMbo().getMboSet("PRLINE"), (MboSet) getMbo().getMboSet("PRLINE.ID2RELPRLINE"), "ORDERQTY", "MS_QTDENT")) {
        throw new MXApplicationException("company", "ID2QNTENTREGAInvalida1");
        }

        if (Validar.maiorQue((MboSet) getMbo().getMboSet("PRLINE.ID2RELPRLINE"), (MboSet) getMbo().getMboSet("PRLINE.ID2RELPRLINE.ID2RELDETREC"), "MS_QTDENT", "MS_QTDDIS")) {
        throw new MXApplicationException("company", "ID2QNTENTREGAInvalida2");
        }*/

        /*if ((getMbo().getMboSet("MS_RL01PER") != null) && (getMbo().isNull("ID2SECRETARIA"))) {
        Uteis.espera("*************** SAVE() Entrou total de linhas do relacionamento = " + getMbo().getMboSet("MS_RL01PER").count());
        if (getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_SIGSEC") != null) {
        getMbo().setValue("ID2SECRETARIA", getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_SIGSEC"));
        Uteis.espera("*************** SAVE() Entrou 1 " + getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_SIGSEC"));
        }
        if (getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_AREDEM") != null) {
        getMbo().setValue("ID2COORDENACAO", getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_AREDEM"));
        Uteis.espera("*************** SAVE() Entrou 2 " + getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("MS_AREDEM"));
        }

        }*/
        return super.SAVE();

    }
}
