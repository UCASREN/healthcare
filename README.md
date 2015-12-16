#healthcare


数据库密码修改位置：
	healthcare.properties
	hibernate.cfg.xml
	root-context.xml
	
	
word在线预览:

	1.jacob的dll放在system32;  jdk/bin; jdk/jre/bin;
	
	2.安装openoffice，利用url(starttool)开启服务
		starttool
		cd "c:\Program Files (x86)\OpenOffice 4\program"
		soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp; –nofirststartwizard
		
		启动了soffice.exe的进程（两个），以管理员方式启动tomcat，才能成功开启服务
		
	3.安装swftools

	
解锁：

	conn sys/sys as sysdba; //以DBA的身份登录
    alter user system account unlock;// 然后解锁
    
    
虚拟计算环境：
   
   	手动安装mvn本地库，oncecloud
   	
数据库：
	增大游标数量（过多执行的sql导致createStatement和prepareStatement被重复执行，直到cursor被耗尽）：
		 输入以下命令, 修改 oracle 最大游标数为 1000
		alter system set open_cursors=1000 scope=both;
	查看最大游标数是否已修改成功
		show parameter open_cursors;
