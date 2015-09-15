#healthcare



word在线预览:

	1.jacob的dll放在system32;  jdk/bin; jdk/jre/bin;
	
	2.安装openoffice，利用url(startwordOnlinetools)开启服务
		url：startwordOnlinetools
		cd "c:\Program Files (x86)\OpenOffice 4\program"
		soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp; –nofirststartwizard
		
	3.安装swftools
	
解锁：
	conn sys/sys as sysdba; //以DBA的身份登录
    alter user system account unlock;// 然后解锁
