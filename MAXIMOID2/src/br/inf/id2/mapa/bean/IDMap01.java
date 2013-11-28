package br.inf.id2.mapa.bean;



import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Ricardo S Gomes
 */
public class IDMap01 extends AppBean { //psdi.webclient.beans.storeroom.StoreroomAppBean {

    private int numeroDelecao = 0;

    private boolean atualizado;
    private String id="";
    private String numRegistro = "";
    private String especie  = "";
    private Date data =  new Date();
    
    public IDMap01() {
        super();
    }

    @Override
    	public  void save() throws MXException {
    	try {
			validarSalvar();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    		super.save();
    	}
    @SuppressWarnings("deprecation")
    @Override
    public int SAVE() throws MXException, RemoteException {
//    	validarSalvar();
        int ret = super.SAVE();
        atualizaSaldo();
        ret = super.SAVE();
 
        return ret;
    }
    
    private void validarSalvar() throws MXException, RemoteException {
        MboRemote mboa;
        MboSet mboSetMatblotaba;
        mboSetMatblotaba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MATBLOTABA", sessionContext.getUserInfo());
        Set<String> setExiste = new TreeSet<String>();

        MboSet mboSetMadataba;
        mboSetMadataba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC06", sessionContext.getUserInfo());

        mboSetMadataba.reset();

        Integer locationID = getMbo().getInt("LOCATIONSID");
        
        getMbo().setValue("ID2DATCHANGE", new Date());


        if (getMbo().getDate("MADATABA") != null) {
            System.out.println("___antes " + locationID);
            mboSetMadataba.setWhere("LOCATIONSID <> " + locationID + " AND COMMODITY = \'" + getMbo().getString("COMMODITY") + "\'" + " AND appname = \'" + appName.toUpperCase() + "\' AND MANUMREG = \'"+getMbo().getString("MANUMREG")+"\'");
            mboSetMadataba.reset();
            MboRemote mbo;
            for (int n = 0; ((mbo = mboSetMadataba.getMbo(n)) != null); n++) {
                System.out.println("___n " + n);
                System.out.println("___n d1 " + mbo.getDate("MADATABA"));
                  System.out.print("     == " + getMbo().getDate("MADATABA"));

                if (getMbo().getDate("MADATABA").equals(mbo.getDate("MADATABA"))) {
                    System.out.println("___throw exitemRegistroEmDulicidade");
                    throw new MXApplicationException("madataba", "existemRegistrosEmDuplicidade");
                }
            }
        }
        


        for (int i = 0; ((mboa = getMbo().getMboSet("MATBLOTABA").getMbo(i)) != null); i++) {
        	if (!mboa.toBeDeleted()) {
            //Incluido por Leysson e solicitado pelo Willians dia 20/10/2011
            if (setExiste.contains(mboa.getString("MANUMLOT") + "#" + mboa.getString("MATIPLOT"))) {
                throw new MXApplicationException("mtlotaba", "ManumlotJaExiste");
            } else {
                setExiste.add(mboa.getString("MANUMLOT") + "#" + mboa.getString("MATIPLOT"));
            }
            //######################################################################


            if ((!mboa.isNull("MAQTDMAC") && mboa.getInt("MAQTDMAC") != 0) && mboa.isNull("MAQTDMACMOR")) {
                throw new MXApplicationException("mtlotaba", "qtdMacMorIsNull");
            }
            if ((!mboa.isNull("MAQTDFEM") && mboa.getInt("MAQTDFEM") != 0) && mboa.isNull("MAQTDFEMMOR")) {
                throw new MXApplicationException("mtlotaba", "qtdFemMorIsNull");
            }

              int qtdMachos = mboa.getInt("MAQTDMAC");
              int qtdFemeas = mboa.getInt("MAQTDFEM");

              mboa.setValue("MAQTDTOTAL", qtdMachos + qtdFemeas);
        	}

        }
        

//        for (int i = 0; ((mboa = getMbo().getMboSet("MATBLOTABA").getMbo(i)) != null); i++) {
//            mboa.setFieldFlag(new String[]{"PERSONID", "MACODPROP", "MAQTDMAC", "MAQTDFEM", "MAQTDTOTMOR", "MAQTDFEMMOR", "MAQTDMACMOR"}, MboConstants.READONLY, true);
//        }
    }
    
    public int mySAVE() throws MXException, RemoteException {
    	return super.SAVE();
    }


    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {

            if (speaker.getUniqueIdName().equalsIgnoreCase("LOCATIONSID")) {
                refreshTable();
                reloadTable();
                //SAVE();
                refreshTable();
                reloadTable();
            }
            if (speaker.getUniqueIdName().equalsIgnoreCase("MATBLOTABAID")) {
            	MboRemote mboV;
            	boolean rodar = true;
            	for (int i = 0; ((mboV = getMbo().getMboSet("MATBLOTABA").getMbo(i)) != null); i++) {
            		 if (mboV.isNull("PERSONID") || mboV.isNull("MACODPROP") || (mboV.isNull("MAQTDMAC") && mboV.isNull("MAQTDFEM"))) {
            			 rodar = false;
            			 break;
            		 }
            	}            	
            	if (rodar) {
                atualizaSaldo();
                String desc = getMbo().getString("DESCRIPTION");
                getMbo().setValue("DESCRIPTION", "...", MboConstants.NOACCESSCHECK);
                getMbo().setValue("DESCRIPTION", desc, MboConstants.NOACCESSCHECK);
            	}
            		
            }

        } catch (MXException ex) {
            Logger.getLogger(IDMap01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(IDMap01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private boolean atualizaSaldo(String person, String propriedade, int quantidade) throws RemoteException, MXException {
    	 MboSet mboDestino = (MboSet) getMbo().getMboSet("MATBSALABA");
    	 MboRemote mboSalaba;
         boolean encontrado;
    	for (int i = 0; ((mboSalaba = mboDestino.getMbo(i)) != null) ; i++) {
			if (mboSalaba.getString("PERSONID").equalsIgnoreCase(person) && mboSalaba.getString("ID2CODPROP").equalsIgnoreCase(propriedade)) {
				encontrado = true;
				break;
			}
		}
    	return true;
    }
    
    public void atualizaSaldo() throws RemoteException, MXException {
    	if (toBeSaved()) {
    	MboRemote mboa;
    	
    	
    	getMbo().setValue("APPNAME", appName.toUpperCase(), MboConstants.NOACCESSCHECK);
        atualizado = true;
        id = getMbo().getString("LOCATION");
        numRegistro = getMbo().getString("MANUMREG");
        data = getMbo().getDate("MADATABA");
        especie = getMbo().getString("COMMODITY");
        int aNumeroDelecao = 0;
        
        MboRemote mbob = null;
        MboRemote mboc;
        MboSetRemote origem;
        origem = psdi.server.MXServer.getMXServer().getMboSet("MAVWMAP02", getMbo().getUserInfo());
        int qtdMacho = 0;
        int qtdFemea = 0;
        
        if ( !getMbo().isNull("COMMODITY") && !getMbo().isNull("MANUMREG") && !getMbo().isNull("MADATABA")) {
               
        origem.setWhere("local_destino in (select location from id2vwloc02 where id2numcont = \'" + getMbo().getString("MANUMREG") + "\' and especie = \'" + getMbo().getString("COMMODITY") + "\' and qtd_total > 0)");
        origem.reset();
           
        MboSet mboDestino = (MboSet) getMbo().getMboSet("MATBSALABA");
        System.out.println("Mbo Destino: " + mboDestino.count());
       
//        mboDestino.deleteAll();
//        mboDestino.save();

      
//        134 32
//        6166 0
//        12 0
        System.out.println("Total MboDestino: " + mboDestino.count());
         
        
        //povoa ou atualiza salaba
        MboRemote mboSalaba;
        boolean encontrado;
		for (int i = 0; ((mboSalaba = mboDestino.getMbo(i)) != null) ; i++) {
			mboDestino.setValue("MAQTDMACHO", 0, MboConstants.NOACCESSCHECK);
			mboDestino.setValue("MAQTDFEMEA", 0, MboConstants.NOACCESSCHECK);
			mboDestino.setValue("MAQTDTOTAL", 0, MboConstants.NOACCESSCHECK);
		}
        for (int j = 0; ((mbob = origem.getMbo(j)) != null); j++) {
        	if (mbob.getString("PRODUTOR") != null) {
        		encontrado = false;
        		for (int i = 0; ((mboSalaba = mboDestino.getMbo(i)) != null) ; i++) {
					if (mboSalaba.getString("PERSONID").equalsIgnoreCase(mbob.getString("PRODUTOR")) && mboSalaba.getString("ID2CODPROP").equalsIgnoreCase(mbob.getString("COD_PROPRIEDADE"))) {
						encontrado = true;
						break;
					}
				}
	        	
        		if (!encontrado) {
        			mboSalaba = mboDestino.add();
        		}
            
        		mboSalaba.setValue("MAQTDMACHO", origem.getMbo(j).getInt("QTD_MACHO"), MboConstants.NOACCESSCHECK);
        		mboSalaba.setValue("MAQTDFEMEA", origem.getMbo(j).getInt("QTD_FEMEA"), MboConstants.NOACCESSCHECK);
        		mboSalaba.setValue("MAQTDTOTAL", origem.getMbo(j).getInt("QTD_TOTAL"), MboConstants.NOACCESSCHECK);
        		mboSalaba.setValue("ID2CODPROP", origem.getMbo(j).getString("COD_PROPRIEDADE"), MboConstants.NOACCESSCHECK);
        		mboSalaba.setValue("PERSONID", origem.getMbo(j).getString("PRODUTOR"), MboConstants.NOACCESSCHECK);
	        	
        		mboSalaba.setValue("COMMODITY", origem.getMbo(j).getString("ESPECIE"), MboConstants.NOACCESSCHECK);
        		mboSalaba.setValue("LOCATION", getMbo().getString("LOCATION"), MboConstants.NOACCESSCHECK);
        	}
         }
       

        
        for (int i = 0; ((mboa = getMbo().getMboSet("MATBLOTABA").getMbo(i)) != null); i++) {
        	if (!mboa.toBeDeleted()) {
        	
        	System.out.println("___ i "+i+" "+mboa.getBoolean("MANOVO")+" / "+getMbo().getMboSet("MATBLOTABA").count());

//            if (!mboa.toBeDeleted() && mboa.getBoolean("MANOVO")) {
                for (int j = 0; ((mbob = getMbo().getMboSet("MATBSALABA").getMbo(j)) != null); j++) {
                	System.out.println("___ j "+j +" / "+getMbo().getMboSet("MATBSALABA").count());
                if (mbob.getString("PERSONID").equalsIgnoreCase(mboa.getString("PERSONID")) && mbob.getString("ID2CODPROP").equalsIgnoreCase(mboa.getString("MACODPROP"))) {
                	BigDecimal machoQtdBanco = (BigDecimal) mboa.getDatabaseValue("MAQTDMAC");
                    	if (machoQtdBanco == null) {
                    		machoQtdBanco = new BigDecimal(0);
                    	}
                    	Long machoQtdTela = mboa.getLong("MAQTDMAC");
                    	Long machoQtd = machoQtdTela - machoQtdBanco.longValue();
                    	System.out.println(machoQtd);
                        if (machoQtd > mbob.getInt("MAQTDMACHO")) {
                            mboa.setValueNull("MAQTDMAC", MboConstants.NOACCESSCHECK);
                            mbob.setValue("MAQTDMACHO", mbob.getInt("MAQTDMACHO") + machoQtdBanco.longValue());
                        } else {
                            mbob.setValue("MAQTDMACHO", mbob.getInt("MAQTDMACHO") - machoQtd);
                        }
                        if (mboa.getInt("MAQTDFEM") > mbob.getInt("MAQTDFEMEA")) {
                            mboa.setValueNull("MAQTDFEM", MboConstants.NOACCESSCHECK);

                        } else {
                            mbob.setValue("MAQTDFEMEA", mbob.getInt("MAQTDFEMEA") - mboa.getInt("MAQTDFEM"));
                        }

                        mbob.setValue("MAQTDTOTAL", mbob.getInt("MAQTDFEMEA") + mbob.getInt("MAQTDMACHO"), MboConstants.NOACCESSCHECK);

                    }

                }
//                if (i < getMbo().getMboSet("MATBLOTABA").count() - 1) {
//                    mboa.setFieldFlag(new String[]{"PERSONID", "MACODPROP", "MAQTDMAC", "MAQTDFEM"}, MboConstants.READONLY, true);
//                }
//            } else {
//                aNumeroDelecao++;
//            }

                refreshTable();
                reloadTable();
                
            double pmfem = mboa.getDouble("MAQTDMACMOR");
            double pmmc = mboa.getDouble("MAQTDFEMMOR");

//            mboa.setFieldFlag(new String[]{"MAQTDMACMOR"}, MboConstants.READONLY, (mboa.isNull("MAQTDMAC") || mboa.getInt("MAQTDMAC") <= 0));
//            mboa.setFieldFlag(new String[]{"MAQTDFEMMOR"}, MboConstants.READONLY, (mboa.isNull("MAQTDFEM") || mboa.getInt("MAQTDFEM") <= 0));
//
//            mboa.setFieldFlag(new String[]{"MAQTDTOTMOR"}, MboConstants.READONLY, true);
            mboa.setValue("MAQTDTOTMOR", pmfem + pmmc, MboConstants.NOACCESSCHECK);


            String cpfProdutor = mboa.getString("PERSONID");
            if (cpfProdutor != null && !cpfProdutor.equals("")) {
                for (int k = 0; ((mboc = getMbo().getMboSet("MATBSALABA").getMbo(k)) != null); k++) {
                    if (mboc.getString("PERSONID").equalsIgnoreCase(cpfProdutor)) {
                        qtdFemea = mboc.getInt("MAQTDFEMEA");
                        qtdMacho = mboc.getInt("MAQTDMACHO");
//                        if (qtdFemea > 0) {
//                            mboa.setFieldFlag("MAQTDFEM", MboConstants.READONLY, false);
//                        }
//                        if (qtdMacho > 0) {
//                            mboa.setFieldFlag("MAQTDMAC", MboConstants.READONLY, false);
//                        }
                    }
                }
            }
            
            mboa.setValue("MANOVO", false);
        }

        }       
//        if (aNumeroDelecao != numeroDelecao) {
//            reloadTable();
//            numeroDelecao = aNumeroDelecao;
//        }
        }
    	}
    	
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
    	System.out.println("___ id "+id);
    	System.out.println("___ commodity "+especie);
    	System.out.println("___ data "+data);
    	System.out.println("___ numRegistro "+numRegistro);
    	if (getMbo() != null) {
    		System.out.println(getMbo().getString("LOCATION"));
    		System.out.println(getMbo().getString("COMMODITY"));
    		System.out.println(getMbo().getString("MADATABA"));
    		System.out.println(getMbo().getString("MANUMREG"));
    	
    	}
    	if (getMbo() != null && 
    			( !getMbo().isNull("COMMODITY") && !getMbo().isNull("MANUMREG") && !getMbo().isNull("MADATABA")) 
    			&& ((!atualizado || ( id != null && !id.equals(getMbo().getString("LOCATION"))))
    			|| (numRegistro != null && !numRegistro.equalsIgnoreCase(getMbo().getString("MANUMREG")))
    			|| (especie != null && !especie.equalsIgnoreCase(getMbo().getString("COMMODITY")))
    			|| (data != null && !data.equals(getMbo().getDate("MADATABA"))))
    			) {
	        atualizaSaldo();	        

    	}
        return super.fetchRecordData();
    }
    
    public int inserirLote() throws MXException, RemoteException {
    	getMbo().getMboSet("MATBLOTABA").add();
    	refreshTable();
    	reloadTable();
    	return EVENT_HANDLED;
    }
    
    public int inserirLoteIndividual() throws MXException, RemoteException {
    	getMbo().getMboSet("MATBLOTABA").add();
    	String personId = getMbo().getMboSet("MATBSALABA").getMbo(getMbo().getMboSet("MATBSALABA").getCurrentPosition()).getString("PERSONID");
    	String prorprietario = getMbo().getMboSet("MATBSALABA").getMbo(getMbo().getMboSet("MATBSALABA").getCurrentPosition()).getString("ID2CODPROP");
    	getMbo().getMboSet("MATBLOTABA").getMbo(getMbo().getMboSet("MATBLOTABA").getCurrentPosition()).setValue("MACODPROP", prorprietario);
    	getMbo().getMboSet("MATBLOTABA").getMbo(getMbo().getMboSet("MATBLOTABA").getCurrentPosition()).setValue("PERSONID", personId);
    	refreshTable();
    	reloadTable();
    	return EVENT_HANDLED;
    }

}