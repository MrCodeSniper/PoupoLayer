;
(function(context) {

 if (context.__HRZCJSBridgeObject !== undefined) {
 return;
 }


 const HRZCJSBridgeObject = {};
 context.__HRZCJSBridgeObject = HRZCJSBridgeObject;


 const MESSAGE_SEPARATOR = '__HRZ_MESSAGE_SEPARATOR_V1';
 const QUEUE_HAS_MESSAGE = '__HRZ_QUEUE_HAS_MESSAGE_V1';


 let GlobalMessageObjectQueue = [];
 let GlobalCallBackUniqueID = 1;
 let isReadyToSendMessage = false;

 const GlobalCallBackQueue = {};
 const GlobalEventTriggers = {};


 const HRZSystemConfig = {
 currentWebViewID : '',
 currentPlatform : '',
 };

 document.addEventListener('readystatechange', function() {
                           if (document.readyState === 'interactive' || document.readyState === 'complete') {

                           isReadyToSendMessage = true;
                           if (GlobalMessageObjectQueue.length !== 0) {
                           HRZCJSBridgeObject.sendMessage(QUEUE_HAS_MESSAGE);
                           }
                           }
                           }, false);



 HRZCJSBridgeObject.drainMessageQueue = function() {

 const messages = GlobalMessageObjectQueue.join(MESSAGE_SEPARATOR);
 GlobalMessageObjectQueue = [];

  if (HRZSystemConfig.currentPlatform !== 'iPhone') {
       androidobj.obtainCallBackMsg(messages);
  }

 return messages;
 };

 HRZCJSBridgeObject.onInvokeError = function(callbackID, params) {
 if (typeof params === 'string') {
 params = JSON.parse(params);
 }

 if (typeof params !== 'object') {
 throw new Error('Callback error, params format error');
 }

 const callbackObject = GlobalCallBackQueue[callbackID];
 if (callbackObject === undefined) {
 return;
 }

 if (callbackObject.onError !== undefined && params !== undefined) {
 callbackObject.onError(params);
 }


 delete GlobalCallBackQueue[callbackID];
 };

 HRZCJSBridgeObject.onInvokeCompleted = function(callbackID) {

 delete GlobalCallBackQueue[callbackID];
 };

 HRZCJSBridgeObject.onCallback = function(callbackID, params) {
 if (typeof params === 'string') {
 try {
 params = JSON.parse(params);
 console.log(params);
 } catch (error) {
 HRZCJSBridgeObject.onInvokeError(callbackID, '{"errorCode" : "-1"}');
 throw new Error('Parse param to json failed');
 }
 }

 if (typeof params !== 'object') {
 delete GlobalCallBackQueue[callbackID];
 throw new Error('Invalid call back param format');
 }

 const callbackObject = GlobalCallBackQueue[callbackID];
 if (callbackObject === undefined) {
 return;
 }
 if (callbackObject.callback !== undefined && params !== undefined) {
 callbackObject.callback(params);
 }


 delete GlobalCallBackQueue[callbackID];
 };

 HRZCJSBridgeObject.sendMessage = function(message) {


 if (!isReadyToSendMessage) {

 }
 HRZCJSBridgeObject._doSend(message);
 };

 HRZCJSBridgeObject._doSend = function(message) {

 const messageRequest = new XMLHttpRequest();
 const messageId = HRZCJSBridgeObject.generateMessageID();

 messageRequest.open("POST", (location.host.length > 0 ? '' : location.origin + '/') + "hrz_client_msg_" + message + "?" + 'msgId=' + messageId + '&wvid=' +  HRZSystemConfig.currentWebViewID, true);
 messageRequest.setRequestHeader('Cache-Control', 'no-cache');
 messageRequest.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
 messageRequest.send();

 };

 HRZCJSBridgeObject.generateMessageID = function() {
 const dateValue = new Date().getTime();
 const randomValue = Math.floor(Math.random() * (1000 - 100)) + 100;

 return dateValue.toString() + randomValue.toString();
 };


 HRZCJSBridgeObject.registerTriggerEvent = function(triggerObject) {
 if (typeof triggerObject.methodName !== 'string' || triggerObject.methodName.length === 0) {
 throw  new Error('Registering a trigger event with empty method name');
 }

 if (typeof triggerObject.methodTrigger !== 'function') {
 throw new Error('Registering a trigger without trigger function body');
 }

 GlobalEventTriggers[triggerObject.methodName] = triggerObject.methodTrigger;
 };

 HRZCJSBridgeObject.onTriggerEvent = function(triggerParam) {
 if (typeof triggerParam !== 'object' || triggerParam.length === 0) {
 return;
 }

 let methodName =  triggerParam['methodName'];
 if (typeof methodName !== 'string' || methodName.length === 0) {
 return;
 }


 let methodParams = triggerParam['methodParams'];

 let trigger = GlobalEventTriggers[methodName];
 if (typeof trigger !== 'function') {

 return;
 }

 let result = trigger(methodParams);
 let triggerCallbackId =  methodParams['callbackId'];


 if (triggerCallbackId !== undefined) {
 const callbackObject = {
 methodName : methodName,
 triggerResult : result,
 callbackId : triggerCallbackId,
 };

 context.hrzapi.invoke("hrz.onTriggerEventCallback", callbackObject);
 }
 };


 HRZCJSBridgeObject.onConfigSystemInfo = ({
                                          methodName : 'hrz.configSystemInfo',
                                          methodTrigger : function(configs) {
                                          if (typeof  configs !== 'object' || configs.length === 0) {
                                          return;
                                          }

                                          const attachWebViewID = configs['HRZWebViewID'];
                                          if (attachWebViewID !== undefined) {
                                          HRZSystemConfig.currentWebViewID = attachWebViewID;
                                          }

                                          const currentPlatform = configs['HRZPlatform'];
                                          if (currentPlatform !== undefined) {
                                          HRZSystemConfig.currentPlatform = currentPlatform;
                                          }


                                          },
                                          });


 HRZCJSBridgeObject.registerTriggerEvent(HRZCJSBridgeObject.onConfigSystemInfo);



 context.hrzapi = context.hrzapi || {};

 context.hrzapi.invoke = function(methodName, params) {
 if (typeof methodName !== 'string' || methodName.length === 0) {
 return;
 }

 const invokeId = methodName + '_' + (GlobalCallBackUniqueID++) + '_' + new Date().getTime();
 let callbackObject = {};
 let hasCallback = false;

 if (params.success !== undefined && (typeof params.success === 'function')) {
 callbackObject.callback = params.success;
 delete params.success;
 hasCallback = true;
 }

 if (params.fail !== undefined && (typeof params.fail === 'function')) {
 callbackObject.onError = params.fail;
 delete params.fail;
 hasCallback = true;
 }

 const paramsPackage = {
 'invokeId': invokeId,
 'methodName': methodName,
 'methodParams': params ? params : {}
 };

 let paramString = JSON.stringify(paramsPackage);
 if (typeof paramString !== 'string' || paramString.length === 0) {
 return;
 }

 if (hasCallback) {
 GlobalCallBackQueue[invokeId] = callbackObject;
 }

 GlobalMessageObjectQueue.push(paramString);
 HRZCJSBridgeObject.sendMessage(QUEUE_HAS_MESSAGE);
 };

 context.hrzapi.registerTriggerEvent = function(methodName, methodImpl) {
 if (typeof methodName !== 'string' || methodName.length === 0) {
 throw new Error('Registering a biz trigger with empty method name');
 }


 let nameComponents = methodName.split(".");
 if (nameComponents.length !== 2) {
 throw new Error('Trigger name should be like "domain.method"');
 }


 if (nameComponents[0] === 'hrz') {
 throw new Error('"hrz" method domain is reserved for internal use, please select other name');
 }


 if (typeof methodImpl !== 'function') {
 throw new Error('Registering a trigger without providing its implementation');
 }


 if (methodImpl.length > 1) {
 throw new Error('Trigger handler can have only one or no parameters');
 }

 const tempTriggerObject = {
 methodName : methodName,
 methodTrigger : methodImpl,
 };

 HRZCJSBridgeObject.registerTriggerEvent(tempTriggerObject);
 };


 context.hrzapi.triggerEvent = function(triggerName, triggerParams) {

 function _doSendErrorTriggerCallbackWithParams(result, params) {

 if (params !== undefined && params.callbackId !== undefined) {
 const callbackObject = {
 methodName : triggerName,
 triggerResult : result,
 callbackId : params.callbackId,
 };

 context.hrzapi.invoke("hrz.onTriggerEventCallback", callbackObject);
 }
 }


 if (typeof triggerName !== 'string' || triggerName.length === 0) {
 _doSendErrorTriggerCallbackWithParams('Send trigger event without valid trigger event name', triggerParams);
 return;
 }


 let trigger =  GlobalEventTriggers[triggerName];
 if (typeof trigger !== 'function' || trigger.length > 1) {
 _doSendErrorTriggerCallbackWithParams('Send trigger event without preparing it in the JS context at first ', triggerParams);
 return;
 }


 setTimeout(HRZCJSBridgeObject.onTriggerEvent({
                                              methodName : triggerName,
                                              methodParams : triggerParams
                                              }), 0);
 };

 })(window);