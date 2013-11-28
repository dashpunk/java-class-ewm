package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.session.WebClientSession;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;


/**
 *
 * @author Willians Andrade
 *
 */
public class ID2OS01 extends psdi.webclient.beans.servicedesk.TicketAppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    
    public ID2OS01() {
    }
    
    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        MboSetRemote mboSR;
        int iTamanho = 0;
        
        camposObrigatorios = "";
        relacionamentosObrigatorios = "";

        mboSR = getMbo().getMboSet("MARL05MEMOS");
        iTamanho = getTotalRegistros(mboSR);
        
        if (iTamanho <= 0) {
        	setResultadoRelacionamentoObrigatorio("Equipe");
//            throw new MXApplicationException("agendarvistoriaterreno", "RelacionamentoMARL05MEMOSSemRegistros");
        }
        if (!preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL05MEMOS"), "ID2LID", 1)) {
        	setResultadoRelacionamentoObrigatorio("Deve selecionar um LÃ­der");
//            throw new MXApplicationException("agendarvistoriaterreno", "RelacionamentoMARL05MEMOSLider");
        }
        MboRemote mbo;
        
        for (int i = 0; ((mbo = getMbo().getMboSet("MARL01COOEXT").getMbo(i)) != null); i++) {
            //Latitude
            if (mbo.isNull("ID2ORILAT")) {
                setResultadoCampoObrigatorio("Latitude");
                //throw new MXApplicationException("vistoria", "ID2ORILATIsNull");
            }
            //Graus
            if (mbo.isNull("ID2GRALAT")) {
                setResultadoCampoObrigatorio("Graus");
                //throw new MXApplicationException("vistoria", "ID2GRALATIsNull");
            }
            //Minutos
            if (mbo.isNull("ID2MINLAT")) {
                setResultadoCampoObrigatorio("Minutos");
                //throw new MXApplicationException("vistoria", "ID2MINLATIsNull");
            }
            //Segundos
            if (mbo.isNull("ID2SEGLAT")) {
                setResultadoCampoObrigatorio("Segundos");
                //throw new MXApplicationException("vistoria", "ID2SEGLATIsNull");
            }
            //Utm Latitude
            if (mbo.isNull("ID2UTMLAT")) {
                setResultadoCampoObrigatorio("Utm Latitude");
                //throw new MXApplicationException("vistoria", "ID2UTMLATIsNull");
            }
            //Longitude
            if (mbo.isNull("ID2ORILON")) {
                setResultadoCampoObrigatorio("Longitude");
                //throw new MXApplicationException("vistoria", "ID2ORILONIsNull");
            }
            //Graus
            if (mbo.isNull("ID2GRALON")) {
                setResultadoCampoObrigatorio("Graus");
                //throw new MXApplicationException("vistoria", "ID2GRALONIsNull");
            }
            //Minutos
            if (mbo.isNull("ID2MINLON")) {
                setResultadoCampoObrigatorio("Minutos");
                //throw new MXApplicationException("vistoria", "ID2MINLONIsNull");
            }
            //Segundos
            if (mbo.isNull("ID2SEGLON")) {
                setResultadoCampoObrigatorio("Segundos");
                //throw new MXApplicationException("vistoria", "ID2SEGLONIsNull");
            }
            //Utm Longitude
            if (mbo.isNull("ID2UTMLON")) {
                setResultadoCampoObrigatorio("Utm Longitude");
                //throw new MXApplicationException("vistoria", "ID2UTMLONIsNull");
            }
            //Local da coordenada
            if (mbo.isNull("DESCRIPTION")) {
                setResultadoCampoObrigatorio("Local da coordenada");
                //throw new MXApplicationException("vistoria", "DESCRIPTIONIsNull");
            }
        }

        if (getMbo().getMboSet("MARL01COOEXT").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Coordenadas Geográficas");
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
        }
        
        if (!Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL01COOEXT"), "MAIN", 1)) {
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
            throw new MXApplicationException("vistoria", "MAINDifUm");

        }
        
        if (!camposObrigatorios.equals("")) {
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});

        }
        if (!relacionamentosObrigatorios.equals("")) {
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});

        }
        
        return super.ROUTEWF();
    }

    public void adicionaTodos() {
        //try {
        //System.out.println("Adicionou");

        try {
            CustomMboSet memoset;

            MboRemote memosetWO = getMbo();
            MboRemote memosetLoc = getMbo();
            boolean existe;
            for (int i = 0; i < getMbo().getMboSet("PERSON2WO").count(); i++) {
                try {
                    existe = false;
                    for (int j = 0; j < getMbo().getMboSet("MARL05MEMOS").count(); j++) {

                        if (getMbo().getMboSet("MARL05MEMOS").getMbo(j).getString("ID2CPF").equalsIgnoreCase(getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"))) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        MboRemote memo = (MboRemote) getMbo().getMboSet("MARL05MEMOS").add();

                        memo.setValue("ID2CPF", getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"));

                        memo.setValue("WONUM", memosetWO.getString("WONUM"));

                        memo.setValue("LOCATION", memosetLoc.getString("LOCATION"));

                        getMbo().getMboSet("MARL05MEMOS").save();
                    }
                } catch (Exception e) {
                }
            }
            memoset = (CustomMboSet) getMbo().getMboSet("MARL05MEMOS");
        } catch (MXException ex) {
            Logger.getLogger(AgendarVistoriaTerreno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AgendarVistoriaTerreno.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshTable();
        reloadTable();
        refreshTable();
        reloadTable();
    }
    public static int getTotalRegistros(MboSetRemote mboSet) throws MXException, RemoteException {
        int resultado = 0;
        MboRemote mbo;
        for (int i = 0; ((mbo = mboSet.getMbo(i))) != null; i++) {
            if (!mbo.toBeDeleted()) {
                resultado++;
            }
        }
        return resultado;
    }
    
    public static boolean preencimentoObrigatorio(MboSet aMboSet, String atributoYorN, int preenchimentoTamanho) throws MXException, RemoteException {

        int contador = 0;

        Mbo linha;

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if ((!linha.getString(atributoYorN).equals("N")) && (!linha.toBeDeleted())) {
                    contador++;
                }
            }

        }

        if (contador == preenchimentoTamanho) {
            return true;
        } else {
            return false;
        }

    }
    
    private void setResultadoCampoObrigatorio(String string) {
        if (string.equals("")) {
            camposObrigatorios = "";
        } else {
            if (camposObrigatorios.indexOf(string) == -1) {
                camposObrigatorios += "\n" + string;
            }
        }
    }

    private void setResultadoRelacionamentoObrigatorio(String string) {
        if (string.equals("")) {
            relacionamentosObrigatorios = "";
        } else {
            if (relacionamentosObrigatorios.indexOf(string) == -1) {
                relacionamentosObrigatorios += "\n" + string;
            }
        }
    }
   
    
}
