package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Validar;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class ID2FldCoordenadas extends MboValueAdapter {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv
     * @throws MXException
     *
     */
    public ID2FldCoordenadas(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     *
     * Quando o "PR.STATUS" (status) mudar para "ENVIADO" e o atributo "PR.ID2DATAPLANO" (data do plano) estiver null executar update em "PR.ID2DATAPLANO" com o valor de "PR.STATUSDATE"
     * e
     * Alterar "PRLINE.ID2STATUS" para "ENVIADO" do relacionamento "PRLINE"
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {

        //Uteis.espera("************************** getMboValue().getAttributeName() = "+getMboValue().getAttributeName());

        super.validate();

        if ("ID2ORILATID2GRALATID2MINLATID2SEGLAT".contains(getMboValue().getAttributeName())) {
            //Uteis.espera("****************Entrou em latitude dms ");
            if (Validar.naoVasio(new MboValueData[]{
                        getMboValue().getMbo().getMboValueData("ID2ORILAT"),
                        getMboValue().getMbo().getMboValueData("ID2GRALAT"),
                        getMboValue().getMbo().getMboValueData("ID2MINLAT"),
                        getMboValue().getMbo().getMboValueData("ID2SEGLAT")
                    })) {

                String valor = new String();
                if (getMboValue().getMbo().getString("ID2ORILAT").equals("S")) {
                    valor = "-";
                } else {
                    valor = "";
                }


                valor = valor.concat(getMboValue().getMbo().getString("ID2GRALAT"));

                valor = valor.concat(".");

                if (getMboValue().getMbo().getString("ID2MINLAT").length() == 1) {
                    valor = valor.concat("0");
                }
                valor = valor.concat(getMboValue().getMbo().getString("ID2MINLAT"));
                if (getMboValue().getMbo().getString("ID2SEGLAT").length() == 1) {

                    valor = valor.concat("0");

                }
                valor = valor.concat(getMboValue().getMbo().getString("ID2SEGLAT").replace(",", ""));

                getMboValue().getMbo().setValue("ID2UTMLAT", Uteis.converteDMS2UTM(Double.valueOf(valor)), MboConstants.NOVALIDATION_AND_NOACTION);

            }
        }

        if ("ID2ORILONID2GRALONID2MINLONID2SEGLON".contains(getMboValue().getAttributeName())) {
            //Uteis.espera("****************Entrou em longitude dms ");

            if (Validar.naoVasio(new MboValueData[]{
                        getMboValue().getMbo().getMboValueData("ID2ORILON"),
                        getMboValue().getMbo().getMboValueData("ID2GRALON"),
                        getMboValue().getMbo().getMboValueData("ID2MINLON"),
                        getMboValue().getMbo().getMboValueData("ID2SEGLON")
                    })) {


                String valor = "";
                if (getMboValue().getMbo().getString("ID2ORILON").equals("O")) {
                    valor = "-";
                }

                valor = valor.concat(getMboValue().getMbo().getString("ID2GRALON"));

                valor = valor.concat(".");

                if (getMboValue().getMbo().getString("ID2MINLON").length() == 1) {
                    valor = valor.concat("0");
                }
                valor = valor.concat(getMboValue().getMbo().getString("ID2MINLON"));

                if (getMboValue().getMbo().getString("ID2SEGLON").length() == 1) {
                    valor = valor.concat("0");
                }
                valor = valor.concat(getMboValue().getMbo().getString("ID2SEGLON").replace(",", ""));

                getMboValue().getMbo().setValue("ID2UTMLON", Uteis.converteDMS2UTM(Double.valueOf(valor)), MboConstants.NOVALIDATION_AND_NOACTION);

            }
        }

        if ((getMboValue().getAttributeName().equals("ID2UTMLAT")) && (!getMboValue().isNull())) {

            //Uteis.espera("****************Entrou em latitude utm");

            try {
                String[] valores = MboStrategy.converteUTM2DMS(getMboValue());

                getMboValue().getMbo().setValue("ID2ORILAT", valores[0], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2GRALAT", valores[1], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2MINLAT", valores[2], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2SEGLAT", valores[3], MboConstants.NOVALIDATION_AND_NOACTION);
            } catch (Exception e) {
                throw new MXApplicationException("company", "ConvercaoCoordenadasUTMLAT");
            }

        }

        if ((getMboValue().getAttributeName().equals("ID2UTMLON")) && (!getMboValue().isNull())) {

            //Uteis.espera("****************Entrou em longitude utm");
            try {
                String[] valores = MboStrategy.converteUTM2DMS(getMboValue());

                getMboValue().getMbo().setValue("ID2ORILON", valores[0], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2GRALON", valores[1], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2MINLON", valores[2], MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setValue("ID2SEGLON", valores[3], MboConstants.NOVALIDATION_AND_NOACTION);
            } catch (Exception e) {

                throw new MXApplicationException("company", "ConvercaoCoordenadasUTMLON");

            }

        }
    }
}
