package br.inf.id2.mapa.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Validar;
import java.util.Date;

/**
 *
 * @author Willians Andrade
 *
 */
public class ID2PRE01 extends psdi.webclient.system.beans.AppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    public ID2PRE01() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        int retorno;

        camposObrigatorios = "";
        relacionamentosObrigatorios = "";

        if (getMbo().getMboSet("MARL03COOEXT").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Coordenagas Geográficas");
        }

        MboRemote mbo;

        for (int i = 0; ((mbo = getMbo().getMboSet("MARL03COOEXT").getMbo(i)) != null); i++) {
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
        
        if (!camposObrigatorios.equals("")) {

            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});

        }
        if (!relacionamentosObrigatorios.equals("")) {

            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});

        }

        //Coordenada Principal
        if (!Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL03COOEXT"), "MAIN", 1)) {
            throw new MXApplicationException("vistoria", "MAINDifUm");
        }
        
        return super.SAVE();
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {

        if (getMbo().getString("ID2NUMPROCESSO").equals("")){
	    	int retorno;
	
	        int ano = Data.getAno(new Date());
	
	        MboSet mboSet;
	        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC05", getMbo().getUserInfo());
	        int TicketId = getMbo().getInt("LOCATION");
	        
	        String valor = "PRE." + Uteis.adicionaValorEsquerda(String.valueOf(TicketId), "0", 9) + "." + Data.getAno(new Date());
	        getMbo().setValue("ID2NUMPROCESSO", valor, MboConstants.NOACCESSCHECK);
	        reloadTable();
	        refreshTable();
        }
        
        return super.fetchRecordData();
    }

    private void validaReadOnly(String mboSet, String campo) throws MXException, RemoteException {
        MboSetRemote mboSR = getMbo().getMboSet(mboSet);
        int iTamanho = mboSR.count();
        System.err.println("############ " + mboSet + ": " + iTamanho);
        if (iTamanho > 0) {
            getMbo().setFieldFlag(campo, MboConstants.READONLY, true);
        } else {
            getMbo().setFieldFlag(campo, MboConstants.READONLY, false);
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
