package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;


/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class UnidadeOrganizacional extends psdi.webclient.system.beans.AppBean  {

    String[][] pessoas;
    int id;
    private final int DEL = 1;
    private final int NEW = 2;
    private final int OLD = 3;

    public UnidadeOrganizacional() {
    	System.out.println("#################### UNIDADE ORGANIZACIONAL 10:27 - 10/02");
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        try {
            if (pessoas == null || id != getMbo().getInt(("MXTBUAID"))) {

            	boolean flag;
                int contador = getMbo().getMboSet("MXRLUOVAGA01").count();
                pessoas = new String[contador][2];
                System.out.println("---------- contador " + contador);
                for (int i = 0; i < contador; i++) {
                	flag = true;
                    System.out.println("------------- i " + i);
                    System.out.println("--valor nullo " + getMbo().getMboSet("MXRLUOVAGA01").getString("MXSERVIDID"));
                    for(int j = 0; j < pessoas.length; j++){
                    	if(pessoas[j][0] == getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getInitialValue("MXSERVIDID").asString()){
                    		flag = false;
                    		break;
                    	}
                    }
                    if(flag){
                    	pessoas[i][0] = getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getInitialValue("MXSERVIDID").asString();
                    	pessoas[i][1] = getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getInitialValue("MXTIPCAR").asString();
                    	System.out.println("----- pessoa atribuida: " + pessoas[i][0]);
                    	System.out.println("----- pessoa atribuida: " + pessoas[i][1]);
                    }
                }
                id = getMbo().getInt("MXTBUAID");
                System.out.println("------ ID " + id);
            }
        } catch (MXException ex) {
            Logger.getLogger(UnidadeOrganizacional.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(UnidadeOrganizacional.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dataChangedEvent(speaker);
    }

    @Override
    public int SAVE() throws RemoteException, MXException {

        if (pessoas != null) {
            insereMxUOLot();
        }
        
        int retorno = super.SAVE();

        pessoas = null;
        return retorno;

    }

    private void insereMxUOLot() throws RemoteException, MXException {

        System.out.println("----------------------------------");
        System.out.println("Valores pessoas[][]");
        System.out.println("----------------------------------");
        for (int i = 0; i < pessoas.length; i++) {
            System.out.println(i + " - " + pessoas[i][0] + " - " + pessoas[i][1]);
        }
        System.out.println("----------------------------------");
        System.out.println("Valores mboSet");
        System.out.println("----------------------------------");
        for (int i = 0; i < getMbo().getMboSet("MXRLUOVAGA01").count(); i++) {
            System.out.println(i + " - " + getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXSERVIDID") + " - " + getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXTIPCAR"));
        }
        System.out.println("----------------------------------");

        System.out.println("---- insereMxUOLot()");
        int contadorPessoas = pessoas.length;
        int contadorMboSet = getMbo().getMboSet("MXRLUOVAGA01").count();
        System.out.println("--- contadorPessoas " + contadorPessoas);
        System.out.println("--- contadorMboSet " + contadorMboSet);
        for (int i = 0; i < contadorMboSet; i++) {
            if (getMbo().getMboSet("MXRLUOVAGA01") != null && getMbo().getMboSet("MXRLUOVAGA01").getMbo(i) != null) {

                getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).setValue("MXSALVO", true);
                System.out.println("--- insereMxUOLot i = " + i);
                if (getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).toBeDeleted()) {
                    System.out.println("--- insereMxUOLot deleted");
                    muda(getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXSERVIDID"), (i < contadorPessoas ? pessoas[i][0] : null), id, DEL, i);
                } else if (getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).isNew()) {
                    System.out.println("--- insereMxUOLot isNew");
                    muda(getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXSERVIDID"), (i < contadorPessoas ? pessoas[i][0] : null), id, NEW, i);
                } else if (i < contadorPessoas && !getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXSERVIDID").equals(pessoas[i][0])) {
                    System.out.println("--- insereMxUOLot isOld");
                    muda(getMbo().getMboSet("MXRLUOVAGA01").getMbo(i).getString("MXSERVIDID"), pessoas[i][0], id, OLD, i);
                }
            }
        }
    }

    /*private enum Acao {

        DEL, NEW, OLD;
    }*/

    private void muda(String pessoa, String pessoaOld, int mxTbUaId, int acao, int posicao) throws MXException, RemoteException {
        System.out.println("--- muda");
        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBRHHULE", sessionContext.getUserInfo());
        MboSet mboSet2;
        mboSet2 = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBRHCASE", sessionContext.getUserInfo());
        System.out.println("--- muda antes do switch");
        switch (acao) {
            case DEL:
                System.out.println("--- muda DEL");
                if (pessoaOld != null) {
                    System.out.println("--- muda DEL pessoaOld != null");
                    mboSet.setWhere("MXTIPO = \'LOT\' AND MXTBUAID = \'" + mxTbUaId + "\' AND PERSONID = \'" + pessoaOld + "\' AND MXDATSAI is NULL");
                    mboSet.setOrderBy("MXDATENT DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda DEL pessoaOld > 0");
                        System.out.println("############## Aqui a data deveria ser alterada pela regra antiga (DEL)");
                        //Comentado no dia 09/02/2012 a pedido do Felipe Marinho. Segundo ele não pode atualizar a data de Exoneração
                        //mboSet.getMbo(0).setValue("MXDATSAI", new Date());
                    }
                    System.out.println("--- muda DEL save 1");
                    mboSet.save();
                    System.out.println("--- muda DEL save 1 Fim");
                }

                
               
                if (getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").count() != 0) {
                    getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").getMbo(0).setValueNull("MXUOLOT");
                    getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").getMbo(0).setValueNull("MXUOLOTID");
                }
                System.out.println("--- muda DEL save 2");
                getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").save();
                System.out.println("--- muda DEL save 2 Fim");
                break;
            case NEW:
                System.out.println("--- muda NEW");
                if (pessoa != null) {
                    System.out.println("--- muda NEW pessoaNew != null");
                    mboSet.setWhere("MXTIPO = \'LOT\' AND MXTBUAID = \'" + mxTbUaId + "\' AND PERSONID = \'" + pessoa + "\' AND MXDATSAI is NULL");
                    mboSet.setOrderBy("MXDATENT DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda NEW > 0");
                        System.out.println("############## Aqui a data deveria ser alterada pela regra antiga (NEW)");
                        //Comentado no dia 09/02/2012 a pedido do Felipe Marinho. Segundo ele não pode atualizar a data de Exoneração
                        //mboSet.getMbo(0).setValue("MXDATSAI", new Date());
                    }
                    System.out.println("----- lot add");
                    MboRemote mbo = mboSet.add();
                    System.out.println("----- lot vals");
                    mbo.setValue("PERSONID", pessoa);
                    mbo.setValue("MXTBUAID", mxTbUaId);
                    mbo.setValue("MXTIPO", "LOT");
                    mbo.setValue("MXDATENT", new Date());
                    System.out.println("----- lot save");
                    mboSet.save();
                    System.out.println("--- muda NEW v1");
                    System.out.println("-----vals ");
                    System.out.println(posicao);
                    System.out.println(getMbo().getString("MXSGUA"));
                    System.out.println(getMbo().getInt("MXTBUAID"));
                    System.out.println("################### COUNT MBO: " + getMbo().getMboSet("MXRLUOVAGA01").count());
                    System.out.println("################### COUNT MXRLRHCASE01: " + getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").count());
                    MboRemote mboRH;
                    if (getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").count() == 0) {
                        //mboRH = getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").add();
                        //throw new MXApplicationException("uo", "RelacionamentoRHRLCASE01Vazio");
                    } else {
                        mboRH = getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").getMbo(0);
	                    mboRH.setValue("MXUOLOT", getMbo().getString("MXSGUA"));
	                    System.out.println("--- muda NEW v2");
	                    mboRH.setValue("MXUOLOTID", getMbo().getInt("MXTBUAID"));
	                    System.out.println("--- muda NEW save");
	                    getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").save();
	                    System.out.println("--- muda NEW save FIM");
                    }

                }
                break;
            case OLD:
                System.out.println("--- muda OLD");
                if (pessoaOld != null) {
                    System.out.println("--- muda OLD pessoaOld != null");
                    mboSet.setWhere("MXTIPO = \'LOT\' AND MXTBUAID = \'" + mxTbUaId + "\' AND PERSONID = \'" + pessoaOld + "\' AND MXDATSAI is NULL");
                    mboSet.setOrderBy("MXDATENT DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda OLD > 0");
                        System.out.println("############## Aqui a data deveria ser alterada pela regra antiga (OLD pessoaOld)");
                        //Comentado no dia 09/02/2012 a pedido do Felipe Marinho. Segundo ele não pode atualizar a data de Exoneração
                        //mboSet.getMbo(0).setValue("MXDATSAI", new Date());
                    }
                    System.out.println("--- muda OLD save1");
                    mboSet.save();
                    System.out.println("--- muda OLD save1 Fim");
                    mboSet2.setWhere("PERSONID = \'" + pessoaOld + "\' ");
                    mboSet2.reset();
                    if (mboSet2.count() > 0) {
                        System.out.println("--- muda OLD v1");
                        mboSet2.getMbo(0).setValueNull("MXUOLOT");
                        System.out.println("--- muda OLD v2");
                        mboSet2.getMbo(0).setValueNull("MXUOLOTID");
                        System.out.println("--- muda OLD save2");
                        mboSet2.save();
                        System.out.println("--- muda OLD save2 FIM");
                    }
                }

                if (pessoa.length() > 0) {
                    System.out.println("--- muda OLD pessoa != null");
                    mboSet.setWhere("MXTIPO = \'LOT\' AND MXTBUAID = \'" + mxTbUaId + "\' AND PERSONID = \'" + pessoa + "\' AND MXDATSAI is NULL");
                    mboSet.setOrderBy("MXDATENT DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda OLD pessoa > 0");
                        System.out.println("############## Aqui a data deveria ser alterada pela regra antiga (OLD pessoa)");
                        //Comentado no dia 09/02/2012 a pedido do Felipe Marinho. Segundo ele não pode atualizar a data de Exoneração
                        //mboSet.getMbo(0).setValue("MXDATSAI", new Date());
                    }

                    System.out.println("--- muda OLD mboSet save");
                    mboSet.save();
                    System.out.println("--- muda OLD mboSet save FIM");
                    System.out.println("----- lot add");
                    MboRemote mbo = mboSet.add();
                    System.out.println("----- lot vals");
                    mbo.setValue("PERSONID", pessoa);
                    mbo.setValue("MXTBUAID", mxTbUaId);
                    mbo.setValue("MXTIPO", "LOT");
                    mbo.setValue("MXDATENT", new Date());
                    System.out.println("----- lot save");
                    mboSet.save();
                    System.out.println("----- lot save FIM");
                    System.out.println("-----vals ");
                    System.out.println(posicao);
                    System.out.println(getMbo().getString("MXSGUA"));
                    System.out.println(getMbo().getInt("MXTBUAID"));
                    System.out.println("################### COUNT MBO: " + getMbo().getMboSet("MXRLUOVAGA01").count());
                    System.out.println("################### COUNT MXRLRHCASE01: " + getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").count());
                    MboRemote mboRH;
                    if (getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").count() == 0) {
                        //mboRH = getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").add();
                    	//throw new MXApplicationException("uo", "RelacionamentoRHRLCASE01Vazio");
                    } else {
                        mboRH = getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").getMbo(0);
	                    mboRH.setValue("MXUOLOT", getMbo().getString("MXSGUA"));
	                    System.out.println("----- lot save v1");
	                    mboRH.setValue("MXUOLOTID", getMbo().getInt("MXTBUAID"));
	                    System.out.println("----- lot save save rel");
	                    getMbo().getMboSet("MXRLUOVAGA01").getMbo(posicao).getMboSet("MXRLRHCASE01").save();
	                    System.out.println("----- lot save save rel fim");
                    }
                }


                break;
        }
    }
}
