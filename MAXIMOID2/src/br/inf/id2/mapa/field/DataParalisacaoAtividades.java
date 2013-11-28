package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 * 
 */
public class DataParalisacaoAtividades extends MboValueAdapter {

    public DataParalisacaoAtividades(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        System.out.println("---- inicio DataParalisacaoAtividades");
        Date iD2DatPar = getMboValue().getMbo().getDate("ID2DATPAR");
        Date iD2PrevRet = getMboValue().getMbo().getDate("ID2PREVRET");
        Date iD2DatRetEfe = getMboValue().getMbo().getDate("ID2DATRETEFE");

        System.out.println("--- iD2DatPar " + iD2DatPar);
        System.out.println("--- iD2PrevRet " + iD2PrevRet);

        if (!getMboValue().getMbo().isNull("ID2DATRETEFE")) {
            if (Data.dataInicialMenorFinal(iD2DatRetEfe, iD2DatPar)) {
                throw new MXApplicationException("matbparati", "DataSolRetAtividadesInvalida");
            }
        }

        if (!getMboValue().getMbo().isNull("ID2DATPAR")) {

            if (Data.dataInicialMenorFinal(iD2DatPar, new Date())) {
                throw new MXApplicationException("matbparati", "DataID2DatParInvalida");
            }

            if (!getMboValue().getMbo().isNull("ID2PREVRET")) {

                if (Data.dataInicialMenorFinal(iD2PrevRet, iD2DatPar)) {
                    throw new MXApplicationException("matbparati", "DataParalisacaoInvalida");
                }

                if (Data.dataInicialMenorFinal(iD2PrevRet, new Date())) {
                    throw new MXApplicationException("matbparati", "DataParalisacaoInvalida");
                }

            }
        }



        System.out.println("--- final de DataParalisacaoAtividades");
        super.validate();

    }
}
