# PopLayer
一个通用的Android端弹窗管理框架,内部维护弹窗优先级队列 具备弹窗管理扩展功能 整合Dialog,PoupoWindow,悬浮Widget,透明Webview,Toast,SnackBar,无需再为繁琐的业务弹窗逻辑所困扰

 <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/PopLayer-v1.0-blue.svg?style=flat-square" alt="弹窗管理" />
  </a>
 
## 如何添加依赖

只需要两行代码轻松接入

```groovy
//add this to your repositories
 maven { url 'https://www.jitpack.io' }

//add this to your dependencies
implementation 'com.github.MrCodeSniper:PopLayer:1.0'
```

## 具体如何使用


### 1.根据策略创建对应的弹窗view

```java
PopLayerView mLayerView=new PopLayerView(this);
//第一个参数为dialog的布局 第二个参数为dialog的主题
mLayerView.setiLayerStrategy(new DialogLayerStrategyImpl(R.layout.common_dialog_upgrade_app,R.style.FullTransDialog));
```

### 2.开始装配弹窗配置

```java
Popi mUpgradePopi1 = new Popi.Builder()
                .setmPopId(4)//弹窗的唯一标识 当id发生改变 视为新的弹窗
                .setmPriority(2)//优先级这里不具体划分对应的范围 值越小优先级越高
                .setmCancelType(TRIGGER_CANCEL)//弹窗消失的类型分为 TRIGGER_CANCEL(触摸消失) COUNTDOWN_CANCEL (延时消失)
                .setMaxShowTimeLength(5)//最长显示时间(S)
                .setMaxShowCount(5)//最大显示次数
                .setmBeginDate(1548858028)//开始时间 2019-01-30 22:20:28
                .setmEndDate(1548944428)//结束时间 2019-01-31 22:20:28
                .setmPopLayerView(mLayerView1)//弹窗View
                .build();
```

### 3.纳入弹窗管理

```java
//纳入弹窗管理
PopManager.getInstance().pushToQueue(mUpgradePopi);
//开始显示弹窗
PopManager.getInstance().showNextPopi();
```
### 效果预览

[预览图](https://user-gold-cdn.xitu.io/2019/1/31/1689fff4be066237?imageslim)

### 框架目前的缺陷

目前还没有好的方法来监听原生的控件消失的回调

类似dialog 点击圈外时 是不走我们的Poplayer消失逻辑的

需要加上监听 但这使得Poplayer内部的逻辑有些紊乱

目前的解决办法是 统一弹窗的 触摸机制 分为实体和外围区域 将其纳入我们的管理范围内

### 未来的计划

很抱歉的是 目前的框架只涉及到了Dialog相关的业务逻辑

类似手淘在其云溪社区提供的方案基于透明的Webview也在筹备之中,会从全方面统筹webview

其他类型的弹窗也会陆续更新 希望能提供给大家一个较为全面的应对业务需求的弹窗管理框架

### 作者介绍

Hello 我叫lalala,如果您喜欢这个项目 请给个star 能follow我那真是太好了！！
