<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/logo.png"/></div>

## 写在前面

初次引入可能会报错，所以食用前请重新引入好三个jar包，都在lib目录下

![](https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/build.jpg)

其它修改

> 1. 修改config/jdbc.properties里面连接mysql数据库的属性值为自己的。
>
> 2. 进货功能的邮件发送方面，调用的类为**com.lingnan.supermarket.util/SendQQMailUtil.java**，在**com.lingnan.supermarket.view/InView.java**下调用，如图，照着SendQQMailUtil.java的属性修改即可，前提要开通QQEmail的SMTP服务(自行百度),调用位置如图
>
>    ![](https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/sendEmail.jpg)



整个项目的运行入口在**com.lingnan.supermarket.view/LoginView.java**下即main函数放的地方

## 首页home

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/首页.jpg" /></div>

## 人员管理manage

权限0为超管，1为收货员，2为进货员

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/人员管理.jpg"/></div>

## 进货系统import

这里在添加要进货物时偷了懒只有选择编号加入。右上角添加按钮即可添加要进的货，右下角第一个按钮就是确认进货，点击后会发送邮件给相应的仓库，公司等，这里只是模拟。

`进货流程就是`(理想情况，实际还需看需求)

> 1. 进货员添加进货商品
> 2. 确认进货订单后会发送邮件
> 3. 供货方收到邮件后准备进货
> 4. 商场收到货，进货员线下清点好后更改订单状态为已入库
> 5. 系统库存自动增加



### 添加进货

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货添加进货.jpg"/></div>

### 更改进货数量

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货修改数量.jpg"/></div>

### 确认进货订单&发送进货邮件

<div align=center>
  <table>
    <tr>
  <img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货确认订单.jpg"/>
	<img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货发送邮件.jpg"/>
    </tr></table>
</div>



### 供货方查看邮件内容

没有用到表格或html，只是简单的字符串排版

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货邮件内容.jpg"/></div>

### 更改进货订单状态

模拟当进货成功后，==修改订单状态==为已入库或者已取消，如果已入库后库存数量会自动增加

<div align=center>
  <table>
    <tr>
  <img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货更改订单状态.jpg"/>
	<img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货库存更新成功.jpg"/>
    </tr></table>
</div>



## 收银系统cashier

也是没扫描机器，所以只能模拟。

`收银流程如下`

> 1. 添加商品，如果库存不够会提示
> 2. (手动)确认结账
> 3. 支付成功后库存减少



### 添加商品

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银添加商品.jpg"/></div>

### 库存不够提示

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银库存不够.jpg"/></div>

### 收银结账&支付成功

<div align=center>
  <table>
    <tr>
  <img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银结账.jpg"/>
	<img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银支付成功.jpg"/>
    </tr></table>
</div>



### 首页更新

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银成功首页.jpg"/></div>

## 商品库存storage

### 总览

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/商品库存.jpg"/></div>

### 库存日志

#### 进货库存日志

<div align=center>
  <table>
    <tr>
  <img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/库存进货记录.jpg"/>
	<img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/进货订单详情.jpg"/>
    </tr></table>
</div>



#### 收银库存日志

<div align=center>
  <table>
    <tr>
  <img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/库存收银记录.jpg"/>
	<img width="50%" src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/收银订单详情.jpg"/>
    </tr></table>
</div>



## 供应商supplier

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/供应商.jpg"/></div>

## 商品目录catalog

<div align=center><img src="https://cdn.jsdelivr.net/gh/lifealsoisgg/SupermarketSystem-Homework/images/商品目录.jpg"/></div>

## 联系contact

email：1138312802@qq.com

wechat：a1138312802



## 写在最后last

  这个项目是几个人一组做的。前端用的是java本身的窗口那些类JModel,JTable,JPanel等，首先难看，其次也不好写。当时想着山不在高，有仙则灵，先把核心功能实现再说，然后到后面时间有限和肝不动了遂只能这样。结束后想优化前端和后台逻辑的时候却发现里面很乱遂不了了之。每次看到这个作品都在警示着我要加强大局观和撸代码的素养(又或许是开发经验不足吧)。

  话说要是能折腾下这套系统所对应的线下的设备，比如扫货机，收银台啥的等，我还是很愿意再折腾的，虽然只是超市系统而非高大上创新的项目。但在这之前，这个作品起码是我第二个比较完整地Java应用，算是交代了我大二的学习成果吧，反正水没水学业拉出来溜溜就知道了。

