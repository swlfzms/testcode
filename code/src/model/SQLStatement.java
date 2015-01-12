package model;

public class SQLStatement {

	
	//TyphoonYear
	public static final String GETTYPHOONYEAR = "SELECT YEAR FROM typhoonyear;";
	
	//TyphoonData
	public static final String INSERTTYPHOONDATA = "LOAD DATA LOCAL INFILE FILENAME INTO TABLE dataTABLENAME FIELDS TERMINATED BY ','";
	public static final String INSERTTYPHOONSN = "LOAD DATA LOCAL INFILE FILENAME INTO TABLE snTABLENAME FIELDS TERMINATED BY ','";
	//public static final String QUERYTABLEISEXIST = "";
	public static final String CREATETYPHOONDATATABLE = "CREATE TABLE IF NOT EXISTS dataTABLENAME (serialNumber int(11) NOT NULL,time varchar(20) character set utf8 default NULL,lat double NOT NULL,lng double NOT NULL,pressure double default NULL,centerSpeed double default NULL,moveSpeed double default NULL,direction varchar(20) default NULL,sevenSolarHalo double default NULL,tenSolarHalo double default NULL,PRIMARY KEY(serialNumber,time)) ENGINE=MyISAM DEFAULT CHARSET=gb2312;";
	public static final String CREATETYPHOONSNTABLE = "CREATE TABLE IF NOT EXISTS snTABLENAME (serialNumber int(11) NOT NULL, name_zh varchar(12) default NULL, name_en varchar(25) default NULL, similar_sn1 int(11) default NULL COMMENT '最相似的',distance1 double default NULL COMMENT '最小结果',similar_sn2 int(11) default NULL,distance2 double default NULL,similar_sn3 int(11) default NULL,distance3 double default NULL,PRIMARY KEY  (serialNumber)) ENGINE=InnoDB DEFAULT CHARSET=gb2312;";
}

