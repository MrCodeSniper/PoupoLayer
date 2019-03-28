# PopLayer
一个通用的Android端弹窗管理框架,支持带网络请求的业务流程管理,内部维护弹窗优先级队列 具备弹窗管理扩展功能 整合Dialog,PoupoWindow,悬浮Widget,透明Webview,Toast,SnackBar,无需再为繁琐的业务弹窗逻辑所困扰

 <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/PopLayer-v3.0.0-blue.svg?style=flat-square" alt="弹窗管理" />
  </a>
 
## 如何添加依赖

只需要两行代码轻松接入

```groovy
//add this to your repositories
 maven { url 'https://www.jitpack.io' }

//add this to your dependencies
implementation 'com.github.MrCodeSniper:PopLayer:3.0.0'
```

## 具体如何使用


### 1.根据策略创建对应的弹窗view

```java
//Dialog形式
PopLayerView  mLayerView = new PopLayerView(this,R.layout.common_dialog_upgrade_app);
//透明Webview形式
PopLayerView mLayerView = new PopLayerView(this,LayerConfig.redPocketScheme);
//取得对应的弹窗实体
Dialog mView=mLayerView.getiPop().getView(Dialog.class);
WebView mView=mLayerView.getiPop().getView(WebView.class);
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
                .setLayerView(mLayerView)//弹窗View
                .build();
```

### 3.纳入弹窗管理并显示

```java
//纳入弹窗管理
PopManager.getInstance().pushToQueue(mUpgradePopi);
//开始显示弹窗
PopManager.getInstance().showNextPopi();
```

### 4.加入涉及网络回调的任务管理

```java

//添加任务 
Task taskUpdate=new Task();
taskUpdate.setmPriority(1);
taskUpdate.setmTaskId(1);

//任务管理器添加任务及对应的弹窗
TaskManager.getInstance(this)
           .pushToQueue(taskUpdate,mUpgradePopi)
           .pushToQueue(taskDownload,downloadPop)
           .pushToQueue(taskNotice,noticePopi);
           
//显示逻辑
TaskManager.getInstance(this).onTaskGoOn(taskUpdate);//回调成功
TaskManager.getInstance(this).onTaskInterupt(taskUpdate);//回调失败
//或
//如果您使用的是Rxjava实现回调您可以继承框架中自带回调逻辑的PopRxSubscriber
public class MySubscriber extends PopRxSubscriber {
    public MySubscriber(Context mContext, Task task) {
        super(mContext, task);
    }
}
```

### 效果预览

![avatar](https://user-gold-cdn.xitu.io/2019/1/31/1689fff4be066237?imageslim)

### 未来的计划

逐步统一 其他类型的弹窗 希望能提供给大家一个较为全面的应对业务需求的弹窗管理框架

### 版本记录


#### V1方案

版本号|LOG|进度更新
--|:--:|--:
V1.0.0|项目开源,完成弹窗管理与Dialog形式扩展|Dialog策略扩展完成
V1.0.1|修复Dialog策略无法获取dialog实体bug|Dialog策略优化
V1.0.2|修复activity摧毁造成的弹窗异常 bug|Dialog策略优化
V1.0.3|优化了弹窗的使用更加方便快捷|框架使用优化

#### V2方案

版本号|LOG|进度更新
--|:--:|--:
V2.0.0|正式加入透明Webview弹窗策略扩展|透明Webview策略扩展完成

#### V3方案

版本号|LOG|进度更新
--|:--:|--:
V3.0.0|引入流程任务管理模块|解决涉及网络的业务逻辑弹窗

### 关于项目

如果您对本项目感兴趣您可以去掘金观看我的文章 给予一点小小的支持

[Android通用业务弹窗管理方案PopLayerV1](https://juejin.im/post/5c51da126fb9a04a006f6da0)

[Android通用业务弹窗管理方案PopLayerV2](https://juejin.im/post/5c56acb851882562eb50d564)

[Android通用业务弹窗管理方案PopLayerV3- 业务流程控制](https://juejin.im/post/5c961f585188252da05f4b08)
### 作者介绍

Hello 我叫lalala,如果您喜欢这个项目 请给个star 能follow我那真是太好了！！
