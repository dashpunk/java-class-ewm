package br.inf.id2.common.ws;

import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import java.util.Map;
import psdi.iface.mic.MicSetIn;
import psdi.iface.mic.StructureData;
import psdi.iface.mos.MosDetailInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.txn.MXTransaction;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * Classe genérica para validação de chave
 * @author ricardo s gomes
 * @since 24/11/2011 17:03
 *
 */
public class ValidarChaveAcessoEntrada extends MicSetIn {

    public ValidarChaveAcessoEntrada() throws MXException, RemoteException {
        System.out.println("### ValidarChaveAcessoEntrada 16:16");
    }

    @Override
    public void checkAdditionalRules() throws MXException, RemoteException {
        System.out.println("### ENTRADA super.checkAdditionalRules()");
        super.checkAdditionalRules();
        //System.out.println("### ENTRADA " + mbo.getString("WSSTCHAVE"));
    }

    @Override
    public MboSetRemote processMboSet(boolean isPrimaryMbo, MboRemote parentMbo, MosDetailInfo mdi, String processTable) throws MXException, RemoteException {
        System.out.println("###> processMboSet(...)");
        //nullSystem.out.println("###> processMboSet(...) " + parentMbo.getName());
        return super.processMboSet(isPrimaryMbo, parentMbo, mdi, processTable);
    }

    @Override
    protected byte[] processResponse() throws MXException, RemoteException {
        System.out.println("###> processResponse()");
        return super.processResponse();
    }

    @Override
    public void processResponse(StructureData data, String ifaceName, Object target, int action, UserInfo info, MXTransaction mxTrans, Map<String, ?> md) throws MXException, RemoteException {
        System.out.println("###> processResponse(...)");
        System.out.println("###> processResponse(...) size " + md.size());
        super.processResponse(data, ifaceName, target, action, info, mxTrans, md);
    }

//    findTargetMbo(mdi)
    @Override
    public int checkBusinessRules() throws MXException, RemoteException {
        System.out.println("### ValidarChaveAcessoEntrada.checkBusinessRules()");

//        System.out.println("###ea "+micsetrel.size());
//        System.out.println("###eb "+messageType);
//        System.out.println("###ec "+hr.size());
//        System.out.println("###ed "+metaData.size());
//        System.out.println("###ee "+responseMbos.size());
//        System.out.println("###ef "+primaryMboSet.count());
//        System.out.println("### valor " + mbo.getString("BGSTCODESTCIV"));
//        System.out.println("### name "+mbo.getName());
//        System.out.println("### valor da chave " + mbo.getString("BGSTCHAVE"));
//        if (mbo.getString("BGSTCHAVE") == null) {
//            throw new MXApplicationException("id2message", "chaveNaoInformada");
//        }
//        System.out.println("### chave " + mbo.getString("BGSTCHAVE"));
//
//        if (Validar.validaChaveWsMIF(mbo.getString("BGSTCHAVE"))) {
//            throw new MXApplicationException("id2message", "chaveInvalida");
//        }
        return super.checkBusinessRules();

    }

    @Override
    public int checkBusinessRules(MboSetRemote mboSet, String tableName) throws MXException, RemoteException {
        System.out.println("###--- ValidarChaveAcessoEntrada.checkBusinessRules(...)");
        System.out.println("###--- tablename "+tableName);
        System.out.println("###--- mboSet "+mboSet.count());

        return super.checkBusinessRules(mboSet, tableName);
    }

}
