# Excluindo Aplicativo #

  * delete from maxapps where app='APPNAME';
  * delete from maxpresentation where app='APPNAME';
  * delete from sigoption where app='APPNAME';
  * delete from applicationauth where app='APPNAME';
  * delete from maxlabels where app='APPNAME';
  * delete from maxmenu where moduleapp='APPNAME' and menutype!='Module';
  * delete from maxmenu where elementtype='APP' and keyvalue='APPNAME';
  * delete from appdoctype where app= 'APPNAME';

# Excluindo Opção de Assinatura e Toolbar #

  * delete from sigoptflag where app='APPNAME';
  * delete from wfapptoolbar where appname='APPNAME';

  * COMMIT;