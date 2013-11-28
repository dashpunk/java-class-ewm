package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.Uteis;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.MXApplicationException;

public class ID2AnimalTableBean extends psdi.webclient.beans.po.POLineTableBean {

    public ID2AnimalTableBean() {
    }

    /*@Override
    public void validate() throws MXException, RemoteException {
        //super.validate();
    }*/

    @Override
    protected void validateRow() throws MXException, RemoteException {
    //super.validateRow();
    }

    
    public int carregarItens()
            throws MXException {
        int ret = -1;
        boolean adicionou = false;

        try {
            int onde = 0;
            //Uteis.espera("Antes--------");
            ////System.out.println("---------------------------------- Inicio");

            MboSet linhas = (MboSet) parent.getMbo().getMboSet("POLINE");
            //linhas.clear();
            //linhas.deleteAll();
            linhas.deleteAndRemoveAll();

            //Uteis.espera("Depois-------");
            Mbo item;
            //System.out.println("---------------------------------- apos Mbos e MboSets");
            //Uteis.espera("Depois 1-------");
            parent.getMbo().setFieldFlag("STORELOC", psdi.mbo.MboConstants.READONLY, false);
            parent.getMbo().setFieldFlag("ID2PROABA", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2PROAGL", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2PROEXP", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESABA", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESAGL", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESEXP", psdi.mbo.MboConstants.READONLY, true);
            //Uteis.espera("Depois 2-------");
            //System.out.println("Tipo do local Proced?ncia " + parent.getMbo().getString("id2tipolocal"));
            //System.out.println("Tipo do local Destino " + parent.getMbo().getString("id2tipolocaldest"));


            if (parent.getMbo().getString("ID2TIPOLOCAL") == null) {
                throw new MXApplicationException("company", "ID2TIPOLOCALInvalido");
            }
            if (parent.getMbo().getString("ID2TIPOLOCALDEST") == null) {
                throw new MXApplicationException("company", "ID2TIPOLOCALDESTnvalido");
            }
            //Uteis.espera("*********** ID2TIPOLOCAL = " + parent.getMbo().getString("ID2TIPOLOCAL"));

            //Uteis.espera("*********** ID2TIPOLOCALDEST = " + parent.getMbo().getString("ID2TIPOLOCALDEST"));


            if (parent.getMbo().getString("ID2TIPOLOCAL").equals("02")) {
                //System.out.println("local Proced?ncia " + parent.getMbo().getString("ID2PROABA"));
                parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2PROABA"));  //TODO ver novo valor
            } else if (parent.getMbo().getString("ID2TIPOLOCAL").equals("03")) {
                //System.out.println("local Procedência " + parent.getMbo().getString("ID2PROAGL"));
                //parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2PROAGL")); //TODO ver novo valor
                parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2VWLOC03PRO.LOCATION")); //TODO ver novo valor
            } else if (parent.getMbo().getString("ID2TIPOLOCAL").equals("04")) {
                parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2PROEXP"));
            }

            //Uteis.espera("*********** após storeloc = " + parent.getMbo().getString("STORELOC"));

            if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("02")) {
                parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2DESABA"));  //TODO ver novo valor
            } else if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("03")) {
                parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2VWLOC03DES.LOCATION")); //TODO ver novo valor
            } else if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("04")) {
                parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2DESEXP"));
            }

            //Uteis.espera("*********** após storeloc destino = " + parent.getMbo().getString("ID2DESAGL"));
            //Uteis.espera("*********** storeloc destino relacionamento = " + parent.getMbo().getString("ID2VWLOC03DES.LOCATION"));
            //Uteis.espera("*********** ID2STORELOCDEST = " + parent.getMbo().getString("ID2STORELOCDEST"));

            MboSet itens = (MboSet) parent.getMbo().getMboSet("ID2PROEXP_SALDO");

            if (itens != null) {
                //Uteis.espera("Total de itens ------------- " + itens.count());

                for (int i = 0; i < itens.count(); i++) {

                    item = (Mbo) itens.getMbo(i);

                    if (item.getString("ITEMNUM").substring(0, 3).equals(parent.getMbo().getString("COMMODITY"))) {


                        ret = super.addrow();

                        adicionou = true;

                        MboRemote linhaAnimal = getMbo();
                        linhaAnimal.setValue("ORDERQTY", 0);

                        linhaAnimal.setValue("ITEMNUM", item.getString("ITEMNUM"));

                        linhaAnimal.setValue("POLINENUM", i);
                        linhaAnimal.setValue("VENDORWAREHOUSE", ".");

                        linhaAnimal.setValue("STORELOC", parent.getMbo().getString("ID2STORELOCDEST"));
                        linhaAnimal.setValue("FROMSTORELOC", parent.getMbo().getString("STORELOC"));
                        

                        if (linhaAnimal.getMboValueData("ORDERUNIT").isReadOnly()) {
                        } else {
                            linhaAnimal.setValue("ORDERUNIT", "CADA");
                        }



                    }
                }
                }



        } catch (Exception e) {
            String params[] = {new String()};

            //System.out.println(e.getMessage());

            throw new MXApplicationException("PO", "AnimalInvalido", params);
        }

        if (!adicionou) {
            throw new MXApplicationException("company", "ProcedenciaSemItens");
        }
        return ret;
    }

    public void delete()
            throws MXException, java.rmi.RemoteException {
        super.delete();

        try {
            if (count() == 0) {
                parent.getMbo().setFieldFlag("ID2PROABA", psdi.mbo.MboConstants.READONLY, false);
                parent.getMbo().setFieldFlag("ID2PROAGL", psdi.mbo.MboConstants.READONLY, false);
                parent.getMbo().setFieldFlag("ID2PROEXP", psdi.mbo.MboConstants.READONLY, false);
                parent.getMbo().setFieldFlag("ID2DESABA", psdi.mbo.MboConstants.READONLY, false);
                parent.getMbo().setFieldFlag("ID2DESAGL", psdi.mbo.MboConstants.READONLY, false);
                parent.getMbo().setFieldFlag("ID2DESEXP", psdi.mbo.MboConstants.READONLY, false);
            }
        } catch (Exception e) {
            String params[] = {new String()};
            //System.out.println(e.getMessage());
        }
    }
}
