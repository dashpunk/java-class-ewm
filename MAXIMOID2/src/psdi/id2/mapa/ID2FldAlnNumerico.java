package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Uteis;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldAlnNumerico extends MboValueAdapter {

    public ID2FldAlnNumerico(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("MMyyyy");
        String mesAnoAtual = formatador.format(data);
        int ano = Integer.valueOf(mesAnoAtual.substring(2, 6));

        String valor = new String();
        valor = getMboValue().getString();
        getMboValue().setValue(Uteis.getApenasNumeros(valor));
        if (getMboValue().getInt() < ano) {
            throw new MXApplicationException("company", "AnoInvalido");
        }
        
        int anoDemanda = getMboValue().getInt();
        
        MboSet setArmazenagem = (MboSet) getMboValue().getMbo().getMboSet("MS_RL01LOCREC");
        MboSet setDistribuicao = (MboSet) getMboValue().getMbo().getMboSet("MS_RL01LOCDIS");
        
        System.out.println("################ Set Armaz:" + setArmazenagem);
        System.out.println("################ Set Dist:" + setDistribuicao);
        
        if (setArmazenagem != null) {
        	for (int i=0; i < setArmazenagem.count(); i++) {
        		Mbo mboArmaz = (Mbo) setArmazenagem.getMbo(i);
        		String dataArmaz = mboArmaz.getString("MS_DATREC");
        		if (dataArmaz != null && dataArmaz.length() >=7) {
        			int anoArmaz = Integer.valueOf(dataArmaz.substring(3, 7));
        			System.out.println("############ Ano Armazenagem:" + anoArmaz);
        			System.out.println("############ Ano Demanda:" + anoDemanda);
        			if (anoDemanda > anoArmaz) {
        				throw new MXApplicationException("demandas", "ArmazenagemComDataAnterior");
        			}
        		}
        	}
        }
        
        if (setDistribuicao != null) {
        	for (int i=0; i < setDistribuicao.count(); i++) {
        		Mbo mboDist = (Mbo) setDistribuicao.getMbo(i);
        		String dataDist = mboDist.getString("MS_DATDIS");
        		if (dataDist != null && dataDist.length() >=7) {
        			int anoDist = Integer.valueOf(dataDist.substring(3, 7));
        			System.out.println("############ Ano Distribuicao:" + anoDist);
        			System.out.println("############ Ano Demanda:" + anoDemanda);
        			if (anoDemanda > anoDist) {
        				throw new MXApplicationException("demandas", "DistribuicaoComDataAnterior");
        			}
        		}
        	}
        }
        
        super.validate();
    }
}
