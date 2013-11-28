package br.inf.id2.common.ws;

import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import java.util.Map;
import psdi.iface.mic.MicSetOut;
import psdi.iface.mos.MosDetailInfo;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * Classe genérica para validação de chave
 * @author ricardo s gomes
 * @since 24/11/2011 15:16
 *
 */
public class ValidarChaveAcessoSaida extends MicSetOut {

    public ValidarChaveAcessoSaida() throws MXException, RemoteException {
        System.out.println("### ValidarChaveAcessoSaida 14:14");
    }


    @Override
    public int checkBusinessRules(MboRemote mbo, MosDetailInfo mosDetInfo, Map<String, Object> ovrdColValueMap) throws MXException, RemoteException {


        try {
            System.out.println("###sa " + micsetrel.size());
        } catch (Exception e) {
            System.out.println("###--- e1 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###sb " + mboIdsForUpdSenderSysId.size());
        } catch (Exception e) {
            System.out.println("###--- e2 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###sc " + hr.size());
        } catch (Exception e) {
            System.out.println("###--- e3 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###sd " + defnMetaData.size());
        } catch (Exception e) {
            System.out.println("###--- e4 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###se " + ovrdColValueMap.size());
        } catch (Exception e) {
            System.out.println("###--- e5 " + e.getMessage());
            e.getStackTrace();
        }
//        try {
//            System.out.println("###sf " + mdi.getResponseKeys().length);
//        } catch (Exception e) {
//            System.out.println("###--- e6 " + e.getMessage());
//            e.getStackTrace();
//        }
        try {
            System.out.println("###sg " + mbo.getThisMboSet().count());
        } catch (Exception e) {
            System.out.println("###--- e7 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###sh1 " + mosDetInfo.getAltKeys().length);
        } catch (Exception e) {
            System.out.println("###--- e81 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("###sh2 " + mosDetInfo.getAutokeyCols().size());
        } catch (Exception e) {
            System.out.println("###--- e82 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("###sh3 " + mosDetInfo.getChildren().size());
        } catch (Exception e) {
            System.out.println("###--- e83 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("###sh4 " + mosDetInfo.getColumns().size());
            System.out.println("#########sh4valor personidexiste " + mosDetInfo.getColumns().containsKey("SNPERSONID"));
            
            
            for (int i = 0; i < mosDetInfo.getColumns().size(); i++) {
            }
            System.out.println("###sh4 " + mosDetInfo.getColumns().get(mbo));
        } catch (Exception e) {
            System.out.println("###--- e84 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("###sh5 " + mosDetInfo.getIfaceTbExclusiveCols().size());
        } catch (Exception e) {
            System.out.println("###--- e85 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("###sh6 " + mosDetInfo.getKeyInfoMap().size());
        } catch (Exception e) {
            System.out.println("###--- e86 " + e.getMessage());
            e.getStackTrace();
        }
        try {
            System.out.println("###sh7 " + mosDetInfo.getKeys().length);
        } catch (Exception e) {
            System.out.println("###--- e87 " + e.getMessage());
            e.getStackTrace();
        }
//        try {
//            System.out.println("###sh8 " + mosDetInfo.getResponseKeys().length);
//        } catch (Exception e) {
//            System.out.println("###--- e88 " + e.getMessage());
//            e.getStackTrace();
//        }

        try {
            System.out.println("###si1 " + ovrdColValueMap.size());
        } catch (Exception e) {
            System.out.println("###--- e9 " + e.getMessage());
            e.getStackTrace();
        }

        try {
            System.out.println("### valor " + mbo.getString("BGSTCODESTCIV"));
        } catch (Exception e) {
            System.out.println("###--- e10 " + e.getMessage());
            e.getStackTrace();
        }
        if (mbo.getString("BGSTCHAVE") == null) {
            throw new MXApplicationException("id2message", "chaveNaoInformada");
        }
        if (Validar.validaChaveWsMIF(mbo.getString("BGSTCHAVE"))) {
            throw new MXApplicationException("id2message", "chaveInvalida");
        }
        return super.checkBusinessRules(mbo, mosDetInfo, ovrdColValueMap);
    }
}
