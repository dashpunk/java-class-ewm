package br.inf.id2.ms.bean;

import br.inf.id2.common.util.Executa;
import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.po.POAppBean;
import psdi.webclient.system.controller.SessionContext;

public class MSCLPO01 extends POAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    Executa.atualizaAtributo((MboSet)getMbo().getMboSet("POLINE"), "GLDEBITACCT", "0.0.0.0");

    System.out.println("---- prline count() " + getMbo().getMboSet("POLINE").count());
    MboRemote mbo;
    for (int i = 0; (mbo = getMbo().getMboSet("POLINE").getMbo(i)) != null; i++)
    {
      MboRemote mbo;
      System.out.println("---- i " + i);
      if (mbo.toBeDeleted()) {
        System.out.println("---- i " + i + " del");

        MboSet polineSet = (MboSet)MXServer.getMXServer().getMboSet("POLINE", this.sessionContext.getUserInfo());

        polineSet.setWhere("polineid <> " + mbo.getInt("POLINEID") + " AND prlineid = " + mbo.getInt("PRLINEID"));
        polineSet.reset();

        System.out.println("---- polinecrit count " + polineSet.count());

        if (polineSet.count() != 0)
          continue;
        MboSet prlineSet = (MboSet)MXServer.getMXServer().getMboSet("PRLINE", this.sessionContext.getUserInfo());

        prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
        prlineSet.reset();
        System.out.println("---- prlineSetCrit count " + prlineSet.count());
        MboRemote mbob;
        if ((mbob = prlineSet.getMbo(0)) != null) {
          System.out.println("---- prlineSetCrit b");
          mbob.setValue("ID2STATUS", "ENVIADO", 2L);
          System.out.println("---- prlineSetCrit a");
          prlineSet.save();
          System.out.println("---- prlineSetCrit a save");
        }

      }
      else
      {
        System.out.println("---- não marcado para deletar");
        MboSet prlineSet = (MboSet)MXServer.getMXServer().getMboSet("PRLINE", this.sessionContext.getUserInfo());

        prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
        prlineSet.reset();
        System.out.println("---- prlineSetCrit count " + prlineSet.count());
        MboRemote mbob;
        if ((mbob = prlineSet.getMbo(0)) != null) {
          System.out.println("---- prlineSetCrit b");
          mbob.setValue("ID2STATUS", "TR", 2L);
          System.out.println("---- prlineSetCrit a");
          prlineSet.save();
          System.out.println("---- prlineSetCrit a save");
        }
      }

    }

    System.out.println("*************** Adicionando valores aos campos...");
    System.out.println("########### MS_RL04PER = " + getMbo().getMboSet("MS_RL04PER"));
    System.out.println("########### ID2CODCOO = " + getMbo().isNull("ID2CODCOO"));
    if ((getMbo().getMboSet("MS_RL04PER") != null) && (getMbo().isNull("ID2CODCOO"))) {
      System.out.println("###################### ID2Lotacao = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
      if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
        getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
        getMbo().setValue("ID2SEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
        System.out.println("################# ID2DIR = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
        getMbo().setValue("ID2DIR", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
      } else {
        System.out.println("##################### Excecao");
        throw new MXApplicationException("pr", "SemLotacao");
      }
    }
    System.out.println("####################### Campos adicionados!");

    return super.SAVE();
  }
}