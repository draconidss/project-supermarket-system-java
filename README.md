<div align=center><img src=images/logo.png/></div>
## 首页

<div align=center><img src=images/首页.jpg/></div>
## 人员管理

权限0为超管，1为收货员，2为进货员

<div align=center><img src=images/人员管理.jpg/></div>
## 进货系统

这里在添加要进货物时偷了懒只有选择编号加入。右上角添加按钮即可添加要进的货，右下角第一个按钮就是确认进货，点击后会发送邮件给相应的仓库，公司等，这里只是模拟。

`进货流程就是`(理想情况，实际还需看需求)

> 1. 进货员添加进货商品
> 2. 确认进货订单后会发送邮件
> 3. 供货方收到邮件后准备进货
> 4. 商场收到货，进货员线下清点好后更改订单状态为已入库
> 5. 系统库存自动增加

### 添加进货

<div align=center><img src=images/进货添加进货.jpg/></div>

### 更改进货数量

<div align=center><img src=images/进货修改数量.jpg/></div>

### 确认进货订单&发送进货邮件

<div align=center>
  <table>
    <tr>
  <img width="50%" src=images/进货确认订单.jpg/>
	<img width="50%" src=images/进货发送邮件.jpg/>
    </tr></table>
</div>

### 供货方查看邮件内容

没有用到表格或html，只是字符串排版

<div align=center><img src=images/进货邮件内容.jpg/></div>

### 更改进货订单状态

模拟当进货成功后，==修改订单状态==为已入库或者已取消，如果已入库后库存数量会自动增加

<div align=center>
  <table>
    <tr>
  <img width="50%" src=images/进货更改订单状态.jpg/>
	<img width="50%" src=images/进货库存更新成功.jpg/>
    </tr></table>
</div>



## 收银系统

也是没扫描机器，所以只能模拟。

`收银流程如下`

> 1. 添加商品，如果库存不够会提示
> 2. (手动)确认结账
> 3. 支付成功后库存减少

### 添加商品

<div align=center><img src=images/收银添加商品.jpg/></div>

### 库存不够提示

<div align=center><img src=images/收银库存不够.jpg/></div>

### 收银结账&支付成功

<div align=center>
  <table>
    <tr>
  <img width="50%" src=images/收银结账.jpg/>
	<img width="50%" src=images/收银支付成功.jpg/>
    </tr></table>
</div>

### 首页更新

<div align=center><img src=images/收银成功首页.jpg/></div>



## 商品库存

### 总览

<div align=center><img src=images/商品库存.jpg/></div>

### 库存日志

#### 进货库存日志

<div align=center>
  <table>
    <tr>
  <img width="50%" src=images/库存进货记录.jpg/>
	<img width="50%" src=images/进货订单详情.jpg/>
    </tr></table>
</div>

#### 收银库存日志

<div align=center>
  <table>
    <tr>
  <img width="50%" src=images/库存收银记录.jpg/>
	<img width="50%" src=images/收银订单详情.jpg/>
    </tr></table>
</div>

## 供应商

<div align=center><img src=images/供应商.jpg/></div>



## 商品目录

<div align=center><img src=images/商品目录.jpg/></div>