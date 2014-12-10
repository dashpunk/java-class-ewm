/*     */ package psdi.common.expbuilder;
/*     */ 
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import psdi.mbo.MaximoDD;
/*     */ import psdi.mbo.Mbo;
/*     */ import psdi.mbo.MboServerInterface;
/*     */ import psdi.mbo.MboSetInfo;
/*     */ import psdi.mbo.MboSetRemote;
/*     */ import psdi.mbo.SqlFormat;
/*     */ import psdi.security.ConnectionKey;
/*     */ import psdi.security.UserInfo;
/*     */ import psdi.server.MXServer;
/*     */ import psdi.util.MXException;
/*     */ import psdi.util.MXSystemException;
/*     */ 
/*     */ public class ExpressionBuilderFormat
/*     */ {
/*     */   private String objectName;
/*     */   private String entityName;
/*     */   private Mbo currentMbo;
/*  46 */   private Locale locale = Locale.getDefault();
/*  47 */   private TimeZone timeZone = TimeZone.getDefault();
/*     */   public static final int VALIDATE_THROW_SQLEXCEPTION = 1;
/*     */   public static final int VALIDATE_THROW_MXEXCEPTION = 2;
/*     */ 
/*     */   public ExpressionBuilderFormat(Mbo currentMbo, String objectName)
/*     */   {
/*     */     try
/*     */     {
/*  56 */       this.objectName = objectName.toLowerCase();
/*  57 */       MboSetInfo msi = MXServer.getMXServer().getMaximoDD().getMboSetInfo(objectName);
/*  58 */       this.entityName = msi.getEntityName().toLowerCase();
/*  59 */       this.currentMbo = currentMbo;
/*     */     }
/*     */     catch (RemoteException e)
/*     */     {
/*  64 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private MboSetRemote getDiscardableMboSet(String objName, Mbo mr)
/*     */     throws RemoteException, MXException
/*     */   {
/*  71 */     MboSetRemote mboset = mr.getMboServer().getMboSet(objName, mr.getUserInfo());
/*  72 */     mboset.setFlag(39L, true);
/*  73 */     return mboset;
/*     */   }
/*     */ 
/*     */   private String getWhere(String expression)
/*     */     throws RemoteException, MXException
/*     */   {
/*  79 */     if ((this instanceof ExpressionBuilderSetRemote))
/*     */     {
/*  81 */       if ((!((ExpressionBuilderSetRemote)this.currentMbo.getThisMboSet()).isDotNotationOn()) && (expression.indexOf(":") != -1))
/*     */       {
/*  84 */         throw new MXSystemException("expbuilder", "nocolonallowef");
/*     */       }
/*     */     }
/*     */ 
/*  88 */     MboSetRemote msr = getDiscardableMboSet(this.objectName, this.currentMbo);
/*  89 */     SqlFormat sqlFormat = new SqlFormat(this.locale, this.timeZone, expression);
/*  90 */     String formattedExp = sqlFormat.formatRaw();
/*  91 */     msr.setWhere(formattedExp);
/*  92 */     msr.reset();
/*  93 */     String mboWhere = this.entityName + " where " + msr.getWhere();
/*     */ 
/*  95 */     String where = "Select * from " + mboWhere;
/*  96 */     return where;
/*     */   }
/*     */ 
/*     */   public void validate(String expression, int flag) throws RemoteException, MXException, SQLException
/*     */   {
/* 101 */     String where = getWhere(expression);
/* 102 */     ConnectionKey ck = this.currentMbo.getUserInfo().getConnectionKey();
/* 103 */     Connection conn = this.currentMbo.getMboServer().getDBConnection(ck);
/* 104 */     Statement stmt = conn.createStatement();
/*     */     try
/*     */     {
/* 107 */       stmt.execute(where);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 111 */       if (flag == 2)
/*     */       {
/* 113 */         Object[] params = { e.getMessage().trim() };
/* 114 */         throw new MXSystemException("expbuilder", "validationfailure", params, e);
/*     */       }
/*     */ 
/* 117 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 122 */       this.currentMbo.getMboServer().freeDBConnection(ck);
/* 123 */       stmt.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validate(String expression)
/*     */     throws RemoteException, MXException, SQLException
/*     */   {
/* 131 */     validate(expression, 2);
/*     */   }
/*     */ }

/* Location:           D:\Trabalho\Eclipse\workspace_andre\MAXIMOID2\src\psdi\expbuilder\
 * Qualified Name:     psdi.common.expbuilder.ExpressionBuilderFormat
 * JD-Core Version:    0.6.0
 */