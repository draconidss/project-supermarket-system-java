drop database if exists Supermarket;
create database Supermarket;
use Supermarket;

drop table if exists User;
create table User(
id int auto_increment primary key,
name varchar(15) unique,/*仓库管理员或收银员名称,一般为工号*/
rname varchar(15),
password varchar(20) not null,
phone varchar(11),
sSuper int not null,/*0为超级管理员,1为仓库管理员,2收银员*/
img varchar(50),
delmark int DEFAULT 1
);

insert into User values(null,"z001","陈龙","0.00.0",'666666',"0","static\\userimg\\u1.png","1");
insert into User values("2","a001",'吴鸿发',"0.00.0",'666666',"1","static\\userimg\\u2.png","1");
insert into User values("3","a002",'刘锦生',"0.00.0",'666666',"1","static\\userimg\\u3.png","1");
insert into User values("4","b001",'陆伟逊',"0.00.0",'666666',"2","static\\userimg\\u4.png","1");
insert into User values("5","b002",'殷瑞',"0.00.0",'666666',"2","static\\userimg\\u5.png","1");

/*供应商信息*/
create table SupplierInf(
id int auto_increment primary key,
name varchar(30) unique,
address varchar(50),
contact varchar(11),
email varchar(50),
delmark int DEFAULT 1
);

insert into supplierInf values('1','安徽大兴批发部','安徽省芜湖市','113831280','1138312802@qq.com','1');
insert into supplierInf values('2','湖北良品铺子批发零售转存中心','湖北省武汉市','15354253','153542534@qq.com','1');
insert into supplierInf values('3','深圳百果园实业发展仓库','广东省深圳市','2858710006','2858710006@qq.com','1');
insert into supplierInf values('4','蒙牛乳业股份有限公司','内蒙古自治区呼和浩特市','1138312802','1138312802@qq.com','1');
insert into supplierInf values('5','内蒙古伊利实业集团股份有限公司','内蒙古自治区呼和浩特市','2858710006','2858710006@qq.com','1');
insert into supplierInf values('6','美的集团广东分区批发部','广东省顺德市','153542534','153542534@qq.com','1');
insert into supplierInf values('7','康师傅集团广东分区','广东省珠海市','1138312802','1138312802@qq.com','1');
insert into supplierInf values('8','宝洁公司','美国俄亥俄州辛辛那堤市','2858710006','2858710006@qq.com','1');


/*目录关系,id1为目录第一级,在系统中简化为3个类别食品,电器,生活用品*/
create table ProdCatalog(
id varchar(20) primary key,
name varchar(20)
);

insert into prodCatalog values('01','食品');
insert into prodCatalog values('02','电器');
insert into prodCatalog values('03','乳制品');
insert into prodCatalog values('04','生活用品');



/*商品目录*/
drop table if exists production;
create table Production(
id varchar(20) primary key,
name varchar(20) unique,
inPrice float,/*进货单价*/
OutPrice float,/*购买单价*/
life int,/*月份为单位*/
sum int,
supplyId int,
id2 varchar(20),
name2 varchar(20),
price float,/*方便吧记录查到InBuffer表中*/
delmark int DEFAULT 1,
foreign key (id2) references prodCatalog(id)
);

insert into production values('01001','猪肉脯','10','15','12','200','1','01','食品','0','1');
insert into production values('01002','芒果干','6','12','12','200','1','01','食品','0','1');
insert into production values('01003','碧根果','13','25','8','200','2','01','食品','0','1');
insert into production values('01004','葡萄','20','35','0.25','50','3','01','食品','0','1');
insert into production values('02001','蒙牛纯甄','40','65','18','300','4','03','乳制品','0','1');
insert into production values('02002','伊利安慕希','45','65','24','350','5','03','乳制品','0','1');
insert into production values('03001','美的变频空调','2400','3500',null,'50','6','02','电器','0','1');
insert into production values('03002','格力节能冰箱','3500','4300',null,'50','7','02','电器','0','1');
insert into production values('04001','海飞丝去屑洗发水','28','35','18','180','8','04','生活用品','0','1');
insert into production values('04002','汰渍洗衣液','24','32','18','180','8','04','生活用品','0','1');















/*超市进货订单信息*/
create table InOrder(
iNumber varchar(30) primary key,/*用java根据时间年月日时分秒和柜台号和一个随机数确定*/
allInPrice float,/*这个订单的总价*/
inDate Timestamp,
principal varchar(15),
status int DEFAULT 2,/*1为已入库,2为待入库,3为取消订单*/
delmark int DEFAULT 1
);

insert into inOrder values('a0011201907272322391','6.5','2019-07-27 23:22:39','a001','1','1');
insert into inOrder values('a0014201907272327143','13100','2019-07-27 23:27:14','a001','1','1');
insert into inOrder values('a0021201907272332006','235','2019-07-27 23:32:00','a002','1','1');

/*进货订单信息*/
create table InRecord(
iNumber varchar(30),
id varchar(20),
sum int,
Price float,/*单个物品进货总价*/
foreign key (iNumber) references InOrder(iNumber)
);

insert into inRecord values('a0011201907272322391','01001','5','2.5');
insert into inRecord values('a0011201907272322391','01002','5','4');
insert into inRecord values('a0014201907272327143','02001','5','5000');
insert into inRecord values('a0014201907272327143','02002','5','600');
insert into inRecord values('a0014201907272327143','02003','5','7500');
insert into inRecord values('a0021201907272332006','03001','5','115');
insert into inRecord values('a0021201907272332006','03002','5','120');


/*进货时缓冲区*/
drop table if exists InBuffer;
create table InBuffer(
id varchar(20) primary key,
name varchar(20) unique,
inPrice float,/*进货单价*/
OutPrice float,/*购买单价*/
life int,/*月份为单位*/
sum int,
supplyId int,
id2 varchar(20),
name2 varchar(20),
price float,
delmark int DEFAULT 0,
foreign key (supplyId) references supplierInf(id),
foreign key (id2) references prodCatalog(id)
);







/*顾客购买订单信息*/
create table OutOrder(
oNumber varchar(30) primary key,/*用java根据时间年月日时分秒和柜台号和一个随机数确定*/
allOutPrice float,/*这个订单的总价*/
oDate Timestamp,
principal varchar(15),
delmark int DEFAULT 1
);
insert into outOrder values('b0010201907272344271','68','2019-07-27 23:44:27','b001','1');

/*出货记录，即买单记录,订单信息*/
drop table if exists OutRecord;
create table OutRecord(
oNumber varchar(30),
id varchar(20),
sum int,
Price float,/*单个物品出售总价*/
foreign key (oNumber) references OutOrder(oNumber)
);

insert into outRecord values('b0010201907272344271','01001','2','2');
insert into outRecord values('b0010201907272344271','03001','2','66');



/*收银出货时缓冲区*/
drop table if exists OutBuffer;
create table OutBuffer(
id varchar(20) primary key,
name varchar(20) unique,
inPrice float,/*进货单价*/
OutPrice float,/*购买单价*/
life int,/*月份为单位*/
sum int,
supplyId int,
id2 varchar(20),
name2 varchar(20),
price float,
delmark int DEFAULT 0,
foreign key (supplyId) references supplierInf(id),
foreign key (id2) references prodCatalog(id)
);

/*库存*/
/*create table storage(
id varchar(20) primary key,
sum int
);

insert into storage values('01001','8');
insert into storage values('01002','10');
insert into storage values('02001','10');
insert into storage values('02002','10');
insert into storage values('02003','10');
insert into storage values('03001','8');
insert into storage values('03002','10');*/

/*库存日志,在进货和顾客购买时进行增加或删除操作*/




create table storageRecord(
theNumber varchar(30),
cDate Timestamp,
id varchar(20),
execute enum("+","-"),
num int
);

insert into storageRecord values('a0011201907272322391',"2019-07-27 23:22:39","01001","+","5");
insert into storageRecord values('a0011201907272322391',"2019-07-27 23:22:39","01002","+","5");
insert into storageRecord values('a0014201907272327143',"2019-07-27 23:27:14","02001","+","5");
insert into storageRecord values('a0014201907272327143',"2019-07-27 23:27:14","02002","+","5");
insert into storageRecord values('a0014201907272327143',"2019-07-27 23:27:14","02003","+","5");
insert into storageRecord values('a0021201907272332006',"2019-07-27 23:32:00","03001","+","5");
insert into storageRecord values('a0021201907272332006',"2019-07-27 23:32:00","03002","+","5");
insert into storageRecord values('b0010201907272344271',"2019-07-27 23:44:27","01001","-","2");
insert into storageRecord values('b0010201907272344271',"2019-07-27 23:44:27","03001","-","2");


/*-----------收银模块----------*/
/*插入缓冲区并修改sum*/
drop procedure if exists InsertOutBuffer;
DELIMITER $$
create procedure InsertOutBuffer(In prodid varchar(10),In addsum int)
	begin
	insert into OutBuffer select * from Production where id = prodid;
	update OutBuffer set sum=addsum,price=addsum*(select outPrice from Production where id = prodid) where id = prodid;
	END $$
DELIMITER ;

/*结账时插入库存日志，商品表中sum值，出货订单记录表，出货订单详细表并清空购物缓冲表记录共五个表*/
drop procedure if exists Account;
DELIMITER $$
create procedure Account(In nNumber varchar(30),In tTime Timestamp,In prodid varchar(20),In addsum int ,In pPrice float)
	
	begin
	declare allsum int;
	select sum into allsum from production where id = prodid;
	update production set sum=allsum-addsum where id = prodid;
	insert into outRecord values(nNumber,prodid,addsum,pPrice);
	insert into storageRecord values(nNumber,tTime,prodid,'-',addsum);
	delete from OutBuffer;
	END $$
DELIMITER ;






/*-----------进货模块----------*/
/*drop procedure if exists InsertInBuffer;
DELIMITER $$
create procedure InsertInBuffer(In prodid varchar(10),In addsum int)
	begin
	insert into InBuffer select * from Production where id = prodid;
	update InBuffer set sum=addsum,price=addsum*(select inPrice from Production where id = prodid) where id = prodid;
	END $$
DELIMITER ;*/

/*将购物车vector数组插入订单表inOrder和订单详细表inRecord*/
/*drop procedure if exists Stock;
DELIMITER $$+
create procedure Stock(In nNumber varchar(30),In tTime Timestamp,In prodid varchar(20),In addsum int ,In pPrice float)
	
	begin
	declare allsum int;
	select sum into allsum from production where id = prodid;
	insert into inRecord values(nNumber,prodid,addsum,pPrice);
	delete from InBuffer;
	END $$
DELIMITER ;*/