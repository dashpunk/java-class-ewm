package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.inf.id2.common.util.Data;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;

/**
 *
 * @author ricardo
 */
public class Snapfnrh01 extends psdi.webclient.beans.common.StatefulAppBean {

    public Snapfnrh01() {
        System.out.println("--- Snapfnrh01 10:47");
    }

    @Override
    public int addrow() throws MXException {
        System.out.println("---addrow a");
        return super.addrow();
    }

    @Override
    public int INIT() throws MXException, RemoteException {
        System.out.println("---INIT a");
        return super.INIT();
    }

    @Override
    public int INSERT() throws MXException, RemoteException {
        int retorno = super.INSERT();
        System.out.println("---INSERT a");
        try {
            if (getMbo() != null && getMbo().isNull("SNCODCNPJ")) {
                System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());

                System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
                String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
                System.out.println("--- cnpj " + cnpj);

                getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

                String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
                String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
                System.out.println("--- uf " + uf);
                System.out.println("--- localidade " + localidade);
                getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
                getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);

                System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));
                refreshTable();
                reloadTable();

            }
        } catch (Exception e) {
        }

        return retorno;
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
        System.out.println("---initialize a");
        try {
            if (getMbo() != null && getMbo().isNull("SNCODCNPJ")) {
                System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());

                System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
                String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
                System.out.println("--- cnpj " + cnpj);

                getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

                String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
                String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
                System.out.println("--- uf " + uf);
                System.out.println("--- localidade " + localidade);
                getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
                getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);
                System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        System.out.println("--- Snapfnrh01.SAVE()");

        System.out.println("*** ANTES dataHoraInicialMenorFinal A");
        if (Data.dataHoraInicialMenorFinal(app.getDataBean().getMboSet().getMbo().getDate("SNPREVSAI"), app.getDataBean().getMboSet().getMbo().getDate("SNPREVENT"))) {
            throw new MXApplicationException("fnrh", "prevCheckoutMenorCheckin");
        }

        System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());
        if (getMbo().getMboSet("MTRL01MAXUSER").count() == 0) {
            throw new MXApplicationException("fnrh", "semCnpjVinculado");
        }

        System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
        String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
        System.out.println("--- cnpj " + cnpj);

        getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);
        String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
        String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
        System.out.println("--- uf " + uf);
        System.out.println("--- localidade " + localidade);
        getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
        getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);
        System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));


        if (!getMbo().getString("SNSTATUS").equalsIgnoreCase("CHECKOUT")) {
            MboRemote mboEnderecoPrimario;

            for (int i = 0; ((mboEnderecoPrimario = getMbo().getMboSet("SNRLPEFI01").getMbo(0).getMboSet("BGRLENDE01").getMbo(i)) != null); i++) {
                if (mboEnderecoPrimario.getBoolean("ISPRIMARY")) {
                    break;
                }

            }


            System.out.println("--valores fnrh 17:04");
            try {
                if (!mboEnderecoPrimario.isNull("BGSTCODCEP2")) {
                    getMbo().setValue("SNSTCODCEP2", mboEnderecoPrimario.getString("BGSTCODCEP2"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCPAIS")) {
                    getMbo().setValue("SNSTDSCPAIS", mboEnderecoPrimario.getString("BGSTDSCPAIS"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {

                if (!mboEnderecoPrimario.isNull("BGSTDSCESTADO")) {
                    getMbo().setValue("SNSTDSCESTADO", mboEnderecoPrimario.getString("BGSTDSCESTADO"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCCIDADE")) {
                    getMbo().setValue("SNSTDSCCIDADE", mboEnderecoPrimario.getString("BGSTDSCCIDADE"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCCIDADE2")) {
                    getMbo().setValue("SNSTDSCCIDADE2", mboEnderecoPrimario.getString("BGSTDSCCIDADE2"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCLOGRADOURO")) {
                    getMbo().setValue("SNSTDSCLOGRADOURO", mboEnderecoPrimario.getString("BGSTDSCLOGRADOURO"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCCOMP")) {
                    getMbo().setValue("SNSTDSCCOMP", mboEnderecoPrimario.getString("BGSTDSCCOMP"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGNUNUMNUM")) {
                    getMbo().setValue("SNNUNUMNUM", mboEnderecoPrimario.getString("BGNUNUMNUM"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }
            try {
                if (!mboEnderecoPrimario.isNull("BGSTDSCBAIRRO")) {
                    getMbo().setValue("SNSTDSCBAIRRO", mboEnderecoPrimario.getString("BGSTDSCBAIRRO"), MboConstants.NOACCESSCHECK);
                }
            } catch (Exception e) {
            }

            System.out.println("--valores fnrh FIM");
        }
        return super.SAVE();

    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {


        if (getMbo() != null && getMbo().isNull("SNCODCNPJ")) {
            System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());

            System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
            String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
            System.out.println("--- cnpj " + cnpj);

            getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

            String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
            String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
            System.out.println("--- uf " + uf);
            System.out.println("--- localidade " + localidade);
            getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
            getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);

            System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));
        }
        return super.fetchRecordData();
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        try {
            System.out.println("---bindComponent");
            if (getMbo() != null) {
                System.out.println("---bindComponent cnpj " + getMbo().isNull("SNCODCNPJ"));
            }
            if (getMbo() != null && getMbo().isNull("SNCODCNPJ")) {
                System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());

                System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
                String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
                System.out.println("--- cnpj " + cnpj);

                getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

                String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
                String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
                System.out.println("--- uf " + uf);
                System.out.println("--- localidade " + localidade);
                getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
                getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);
                System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));
            }
        } catch (MXException ex) {
            Logger.getLogger(Snapfnrh01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Snapfnrh01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
