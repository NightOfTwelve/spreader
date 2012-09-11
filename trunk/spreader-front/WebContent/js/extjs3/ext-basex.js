(function(){var b=Ext.lib.Ajax,h=function(i){return typeof i!="undefined"},d=Ext.emptyFn||function(){},a=Object.prototype;Ext.lib.Ajax.Queue=function(i){i=i?(i.name?i:{name:i}):{};Ext.apply(this,i,{name:"q-default",priority:5,FIFO:true,callback:null,scope:null,suspended:false,progressive:false});this.requests=new Array();this.pending=false;this.priority=this.priority>9?9:(this.priority<0?0:this.priority)};Ext.extend(Ext.lib.Ajax.Queue,Object,{add:function(i){var j=b.events?b.fireEvent("beforequeue",this,i):true;if(j!==false){this.requests.push(i);this.pending=true;b.pendingRequests++;this.manager&&this.manager.start()}},suspended:false,activeRequest:null,next:function(i){var j=i?this.requests[this.FIFO?"first":"last"]():this.requests[this.FIFO?"shift":"pop"]();if(this.requests.length==0){this.pending=false;Ext.isFunction(this.callback)&&this.callback.call(this.scope||null,this);b.events&&b.fireEvent("queueempty",this)}return j||null},clear:function(){this.suspend();b.pendingRequests-=this.requests.length;this.requests.length=0;this.pending=false;this.resume();this.next()},suspend:function(){this.suspended=true},resume:function(){this.suspended=false},requestNext:function(i){var j;this.activeRequest=null;if(!this.suspended&&(j=this.next(i))){if(j.active){this.activeRequest=b.request.apply(b,j);b.pendingRequests--}else{return this.requestNext(i)}}return this.activeRequest}});Ext.lib.Ajax.QueueManager=function(i){Ext.apply(this,i||{},{quantas:10,priorityQueues:new Array(new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array(),new Array()),queues:{}})};Ext.extend(Ext.lib.Ajax.QueueManager,Object,{quantas:10,getQueue:function(i){return this.queues[i]},createQueue:function(i){if(!i){return null}var j=new b.Queue(i);j.manager=this;this.queues[j.name]=j;var k=this.priorityQueues[j.priority];k&&k.indexOf(j.name)==-1&&k.push(j.name);return j},removeQueue:function(i){if(i&&(i=this.getQueue(i.name||i))){i.clear();this.priorityQueues[i.priority].remove(i.name);delete this.queues[i.name]}},start:function(){if(!this.started){this.started=true;this.dispatch()}return this},suspendAll:function(){forEach(this.queues,function(i){i.suspend()})},resumeAll:function(){forEach(this.queues,function(i){i.resume()});this.start()},progressive:false,stop:function(){this.started=false;return this},dispatch:function(){var l=this,k=l.queues;var i=(b.activeRequests>b.maxConcurrentRequests);while(b.pendingRequests&&!i){var j=function(o){var n=k[o],m;while(n&&!n.suspended&&n.pending&&n.requestNext()){i||(i=b.activeRequests>b.maxConcurrentRequests);if(i){break}if(n.progressive||l.progressive){break}}if(i){return false}};forEach(this.priorityQueues,function(m){!!m.length&&forEach(m,j,this);i||(i=b.activeRequests>b.maxConcurrentRequests);if(i){return false}},this)}if(b.pendingRequests||i){this.dispatch.defer(this.quantas,this)}else{this.stop()}}});Ext.apply(b,{headers:b.headers||{},defaultPostHeader:b.defaultPostHeader||"application/x-www-form-urlencoded; charset=UTF-8",defaultHeaders:b.defaultHeaders||{},useDefaultXhrHeader:!!b.useDefaultXhrHeader,defaultXhrHeader:"Ext.basex",poll:{},pollInterval:b.pollInterval||50,queueManager:new b.QueueManager(),queueAll:false,activeRequests:0,pendingRequests:0,maxConcurrentRequests:Ext.isIE?Ext.value(window.maxConnectionsPerServer,2):4,forceActiveX:false,async:true,createXhrObject:function(r,s){var o={status:{isError:false},tId:r},n=null;s||(s={});try{s.xdomain&&window.XDomainRequest&&(o.conn=new XDomainRequest());if(!h(o.conn)&&window.ActiveXObject&&!!Ext.value(s.forceActiveX,this.forceActiveX)){throw ("IE7forceActiveX")}o.conn||(o.conn=new XMLHttpRequest())}catch(k){var j=window.ActiveXObject?(s.multiPart?this.activeXMultipart:this.activeX):null;if(j){for(var p=0,m=j.length;p<m;p++){try{o.conn=new ActiveXObject(j[p]);break}catch(q){n=(k=="IE7forceActiveX"?q:k)}}}}finally{o.status.isError=!h(o.conn);o.status.error=n}return o},createExceptionObject:function(m,l,j,i,k){return{tId:m,status:j?-1:0,statusText:j?"transaction aborted":"communication failure",isAbort:j,isTimeout:i,argument:l}},encoder:encodeURIComponent,serializeForm:function(){var k=/select-(one|multiple)/i,i=/file|undefined|reset|button/i,j=/radio|checkbox/i;return function(m){var n=m.elements||(document.forms[m]||Ext.getDom(m)).elements,t=false,s=this.encoder,q,u,l,o,p="",r;forEach(n,function(v){l=v.name;r=v.type;if(!v.disabled&&l){if(k.test(r)){forEach(v.options,function(w){if(w.selected){p+=String.format("{0}={1}&",s(l),(w.hasAttribute?w.hasAttribute("value"):w.getAttribute("value")!==null)?w.value:w.text)}})}else{if(!i.test(r)){if(!(j.test(r)&&!v.checked)&&!(r=="submit"&&t)){p+=s(l)+"="+s(v.value)+"&";t=/submit/i.test(r)}}}}});return p.substr(0,p.length-1)}}(),getHttpStatus:function(i){var k={status:0,statusText:"",isError:false,isLocal:false,isOK:true,error:null,isAbort:false,isTimeout:false};try{if(!i||!("status" in i)){throw ("noobj")}k.status=i.status;k.readyState=i.readyState;k.isLocal=(!i.status&&location.protocol=="file:")||(Ext.isSafari&&!h(i.status));k.isOK=(k.isLocal||(k.status==304||k.status==1223||(k.status>199&&k.status<300)));k.statusText=i.statusText||""}catch(j){}return k},handleTransactionResponse:function(m,n,k,j){n=n||{};var l=null;m.isPart||b.activeRequests--;if(!m.status.isError){m.status=this.getHttpStatus(m.conn);l=this.createResponseObject(m,n.argument,k)}m.isPart||this.releaseObject(m);m.status.isError&&(l=Ext.apply({},l||{},this.createExceptionObject(m.tId,n.argument,(k?k:false),j,m.status.error)));l.options=m.options;l.fullStatus=m.status;if(!this.events||this.fireEvent("status:"+m.status.status,m.status.status,m,l,n,k)!==false){if(m.status.isOK&&!m.status.isError){if(!this.events||this.fireEvent("response",m,l,n,k,j)!==false){var i=m.isPart?"onpart":"success";Ext.isFunction(n[i])&&n[i].call(n.scope||null,l)}}else{if(!this.events||this.fireEvent("exception",m,l,n,k,j,l.fullStatus.error)!==false){Ext.isFunction(n.failure)&&n.failure.call(n.scope||null,l,l.fullStatus.error)}}}return l},releaseObject:function(i){if(i&&Ext.value(i.tId,-1)+1){if(this.poll[i.tId]){window.clearInterval(this.poll[i.tId]);delete this.poll[i.tId]}if(this.timeout[i.tId]){window.clearInterval(this.timeout[i.tId]);delete this.timeout[i.tId]}}i&&(i.conn=null)},decodeJSON:Ext.decode,reCtypeJSON:/(application|text)\/json/i,reCtypeXML:/(application|text)\/xml/i,createResponseObject:function(j,t,m,k){var q={responseXML:null,responseText:"",responseStream:null,responseJSON:null,contentType:null,getResponseHeader:d,getAllResponseHeaders:d};var A={},l="";if(m!==true){try{q.responseJSON=j.conn.responseJSON||null;q.responseStream=j.conn.responseStream||null;q.contentType=j.conn.contentType||null;q.responseText=j.conn.responseText}catch(u){j.status.isError=true;j.status.error=u}try{q.responseXML=j.conn.responseXML||null}catch(v){}try{l=("getAllResponseHeaders" in j.conn?j.conn.getAllResponseHeaders():null)||"";var z;l.split("\n").forEach(function(o){(z=o.split(":"))&&z.first()&&(A[z.first().trim().toLowerCase()]=(z.last()||"").trim())})}catch(i){j.status.isError=true;j.status.error=i}finally{q.contentType=q.contentType||A["content-type"]||""}if((j.status.isLocal||j.proxied)&&typeof q.responseText=="string"){j.status.isOK=!j.status.isError&&((j.status.status=(!!q.responseText.length)?200:404)==200);if(j.status.isOK&&((!q.responseXML&&this.reCtypeXML.test(q.contentType))||(q.responseXML&&q.responseXML.childNodes.length===0))){var r=null;try{if(window.ActiveXObject){r=new ActiveXObject("MSXML2.DOMDocument.3.0");r.async=false;r.loadXML(q.responseText)}else{var x=null;try{x=new DOMParser();r=x.parseFromString(q.responseText,"application/xml")}catch(w){}finally{x=null}}}catch(y){j.status.isError=true;j.status.error=y}q.responseXML=r}if(q.responseXML){var n=(q.responseXML.documentElement&&q.responseXML.documentElement.nodeName=="parsererror")||(q.responseXML.parseError||0)!==0||q.responseXML.childNodes.length===0;n||(q.contentType=A["content-type"]=q.responseXML.contentType||"text/xml")}}if(j.options.isJSON||(this.reCtypeJSON&&this.reCtypeJSON.test(A["content-type"]||""))){try{Ext.isObject(q.responseJSON)||(q.responseJSON=Ext.isFunction(this.decodeJSON)&&Ext.isString(q.responseText)?this.decodeJSON(q.responseText):null)}catch(p){j.status.isError=true;j.status.error=p}}}j.status.proxied=!!j.proxied;Ext.apply(q,{tId:j.tId,status:j.status.status,statusText:j.status.statusText,contentType:q.contentType||A["content-type"],getResponseHeader:function(o){return A[(o||"").trim().toLowerCase()]},getAllResponseHeaders:function(){return l},fullStatus:j.status,isPart:j.isPart||false});j.parts&&!j.isPart&&(q.parts=j.parts);h(t)&&(q.argument=t);return q},setDefaultPostHeader:function(i){this.defaultPostHeader=i||""},setDefaultXhrHeader:function(i){this.useDefaultXhrHeader=i||false},request:function(i,k,m,o,u){var r=u=Ext.apply({async:this.async||false,headers:false,userId:null,password:null,xmlData:null,jsonData:null,queue:null,proxied:false,multiPart:false,xdomain:false},u||{});if(!this.events||this.fireEvent("request",i,k,m,o,r)!==false){if(!r.queued&&(r.queue||(r.queue=this.queueAll||null))){r.queue===true&&(r.queue={name:"q-default"});var p=r.queue;var l=p.name||p,t=this.queueManager;var j=t.getQueue(l)||t.createQueue(p);r.queue=j;r.queued=true;var s=[i,k,m,o,r];s.active=true;j.add(s);return{tId:this.transactionId++,queued:true,request:s,options:r}}u.onpart&&(m.onpart||(m.onpart=Ext.isFunction(u.onpart)?u.onpart.createDelegate(u.scope):null));r.headers&&forEach(r.headers,function(v,q){this.initHeader(q,v,false)},this);var n;if(n=(this.headers?this.headers["Content-Type"]||null:null)){delete this.headers["Content-Type"]}if(r.xmlData){n||(n="text/xml");i="POST";o=r.xmlData}else{if(r.jsonData){n||(n="application/json; charset=utf-8");i="POST";o=Ext.isObject(r.jsonData)?Ext.encode(r.jsonData):r.jsonData}}if(o){n||(n=this.useDefaultHeader?this.defaultPostHeader:null);n&&this.initHeader("Content-Type",n,false)}return this.makeRequest(r.method||i,k,m,o,r)}return null},getConnectionObject:function(k,i){var m,l;var n=this.transactionId;i||(i={});try{if(l=i.proxied){m={tId:n,status:{isError:false},proxied:true,conn:{el:null,send:function(){var p=(l.target||window).document,o=p.getElementsByTagName("head")[0];if(o&&this.el){o.appendChild(this.el.dom)}},abort:function(){this.readyState=0},getAllResponseHeaders:d,getResponseHeader:d,onreadystatechange:null,onload:null,readyState:0,status:0,responseText:null,responseXML:null,responseJSON:null},debug:l.debug,params:i.params||{},cbName:l.callbackName||"basexCallback"+n,cbParam:l.callbackParam||null};window[m.cbName]=m.cb=function(q,p){q&&typeof(q)=="object"&&(this.responseJSON=q);this.responseText=q||null;this.readyState=4;this.status=!!q?200:404;Ext.isFunction(this.onreadystatechange)&&this.onreadystatechange();window[m.cbName]=undefined;try{delete window[m.cbName]}catch(o){}m.debug||this.el.remove();this.el=null;Ext.isFunction(this.onload)&&this.onload()}.createDelegate(m.conn,[m],true);m.conn.open=function(){if(m.cbParam){m.params[m.cbParam]=m.cbName}var o=Ext.urlEncode(m.params)||null;this.el=f(l.tag||"script",{type:"text/javascript",src:o?k+(k.indexOf("?")>-1?"&":"?")+o:k,charset:l.charset||i.charset||null},null,l.target,true);this.readyState=1;Ext.isFunction(this.onreadystatechange)&&this.onreadystatechange()};i.async=true}else{m=this.createXhrObject(n,i)}if(m){this.transactionId++}}catch(j){m&&(m.status.isError=!!(m.status.error=j))}finally{return m}},makeRequest:function(q,l,p,i,j){var n;if(n=this.getConnectionObject(l,j)){n.options=j;var k=n.conn;try{if(n.status.isError){throw n.status.error}b.activeRequests++;k.open(q.toUpperCase(),l,j.async,j.userId,j.password);("onreadystatechange" in k)&&(k.onreadystatechange=this.onStateChange.createDelegate(this,[n,p,"readystate"],0));("onload" in k)&&(k.onload=this.onStateChange.createDelegate(this,[n,p,"load",4],0));("onprogress" in k)&&(k.onprogress=this.onStateChange.createDelegate(this,[n,p,"progress"],0));if(p&&p.timeout&&j.async){("timeout" in k)&&(k.timeout=p.timeout);("ontimeout" in k)&&(k.ontimeout=this.abort.createDelegate(this,[n,p,true],0));("ontimeout" in k)||(j.async&&(this.timeout[n.tId]=window.setInterval(function(){b.abort(n,p,true)},p.timeout)))}if(this.useDefaultXhrHeader&&!j.xdomain){this.defaultHeaders["X-Requested-With"]||this.initHeader("X-Requested-With",this.defaultXhrHeader,true)}this.setHeaders(n);if(!this.events||this.fireEvent("beforesend",n,q,l,p,i,j)!==false){k.send(i||null)}}catch(m){n.status.isError=true;n.status.error=m}if(n.status.isError){return Ext.apply(n,this.handleTransactionResponse(n,p))}j.async||this.onStateChange(n,p,"load");return n}},abort:function(j,k,i){if(j&&j.queued&&j.request){j.request.active=j.queued=false;this.events&&this.fireEvent("abort",j,k);return true}else{if(j&&this.isCallInProgress(j)){if(!this.events||this.fireEvent(i?"timeout":"abort",j,k)!==false){Ext.isFunction(j.conn.abort)&&j.conn.abort();j.status.isAbort=!(j.status.isTimeout=i||false);this.handleTransactionResponse(j,k,j.status.isAbort,i)}return true}else{return false}}},isCallInProgress:function(i){if(i&&i.conn){if("readyState" in i.conn&&{0:true,4:true}[i.conn.readyState]){return false}return true}return false},clearAuthenticationCache:function(i){try{if(Ext.isIE){document.execCommand("ClearAuthenticationCache")}else{var j;if(j=new XMLHttpRequest()){j.open("GET",i||"/@@",true,"logout","logout");j.send("");j.abort.defer(100,j)}}}catch(k){}},initHeader:function(i,j){(this.headers=this.headers||{})[i]=j},onStateChange:function(m,w,u){if(!m.conn){return}var j=m.conn,s=("readyState" in j?j.readyState:0);if(u==="load"||s>2){var v;try{v=j.contentType||j.getResponseHeader("Content-Type")||""}catch(n){}if(v&&/multipart\//i.test(v)){var i=null,l=v.split('"')[1],t="--"+l;m.multiPart=true;try{i=j.responseText}catch(q){}var k=i?i.split(t):null;if(k){m.parts||(m.parts=[]);k.shift();k.pop();forEach(Array.slice(k,m.parts.length),function(o){var r=o.split("\n\n");var p=(r[0]?r[0]:"")+"\n";m.parts.push(this.handleTransactionResponse(Ext.apply(Ext.clone(m),{boundary:l,conn:{status:200,responseText:(r[1]||"").trim(),getAllResponseHeaders:function(){return p.split("\n").filter(function(x){return !!x}).join("\n")}},isPart:true}),w))},this)}}}(s===4||u==="load")&&b.handleTransactionResponse(m,w);this.events&&this.fireEvent.apply(this,["readystatechange"].concat(Array.slice(arguments,0)))},setHeaders:function(i){if(i.conn&&"setRequestHeader" in i.conn){this.defaultHeaders&&forEach(this.defaultHeaders,function(k,j){i.conn.setRequestHeader(j,k)});this.headers&&forEach(this.headers,function(k,j){i.conn.setRequestHeader(j,k)})}this.headers={};this.hasHeaders=false},resetDefaultHeaders:function(){delete this.defaultHeaders;this.defaultHeaders={};this.hasDefaultHeaders=false},activeXMultipart:["MSXML2.XMLHTTP.6.0","MSXML3.XMLHTTP"],activeX:["MSXML2.XMLHTTP.3.0","MSXML2.XMLHTTP","Microsoft.XMLHTTP"]});var f=function(r,l,p,i,q){l=Ext.apply({},l||{});i||(i=window);var j=null,o=i.document,n=o.getElementsByTagName("head")[0];if(o&&n&&(j=Ext.get(o.createElement(r)))){var m=Ext.getDom(j);m&&forEach(l,function(t,s){t&&(s in m)&&m.setAttribute(s,t)});if(p){var k=(p.success||p).createDelegate(p.scope||null,[p],true);Ext.capabilities.isEventSupported("load",r)?j.on("load",k):k.defer(50)}q||n.appendChild(m)}return j};if(Ext.util.Observable){Ext.apply(b,{events:{request:true,beforesend:true,response:true,exception:true,abort:true,timeout:true,readystatechange:true,beforequeue:true,queue:true,queueempty:true},onStatus:function(i,m,l,k){var j=Array.slice(arguments,1);i=new Array().concat(i||new Array());forEach(i,function(n){n=parseInt(n,10);if(!isNaN(n)){var o="status:"+n;this.events[o]||(this.events[o]=true);this.on.apply(this,[o].concat(j))}},this)},unStatus:function(i,m,l,k){var j=Array.slice(arguments,1);i=new Array().concat(i||new Array());forEach(i,function(n){n=parseInt(n,10);if(!isNaN(n)){var o="status:"+n;this.un.apply(this,[o].concat(j))}},this)}},new Ext.util.Observable());Ext.hasBasex=true}Ext.stopIteration={stopIter:true};Ext.applyIf(Array.prototype,{map:function(k,n){var j=this.length;if(typeof k!="function"){throw new TypeError()}var m=new Array(j);for(var l=0;l<j;l++){if(l in this){m[l]=k.call(n||this,this[l],l,this)}}return m},some:function(m){var n=Ext.isFunction(m)?m:function(){};var k=0,j=this.length,o=false;while(k<j&&!(o=!!n(this[k++]))){}return o},every:function(m){var n=Ext.isFunction(m)?m:function(){};var k=0,j=this.length,o=true;while(k<j&&(o=!!n(this[k++]))){}return o},include:function(l,j){if(!j&&typeof this.indexOf=="function"){return this.indexOf(l)!=-1}var k=false;try{this.forEach(function(n,m){if(k=(j?(n.include?n.include(l,j):(n===l)):n===l)){throw Ext.stopIteration}})}catch(i){if(i!=Ext.stopIteration){throw i}}return k},filter:function(k,j){var i=new Array();k||(k=function(l){return l});this.forEach(function(m,l){k.call(j,m,l)&&i.push(m)});return i},compact:function(j){var i=new Array();this.forEach(function(k){(k===null||k===undefined)||i.push(j&&Ext.isArray(k)?k.compact():k)},this);return i},flatten:function(){var i=new Array();this.forEach(function(j){Ext.isArray(j)?(i=i.concat(j)):i.push(j)},this);return i},indexOf:function(l){for(var k=0,j=this.length;k<j;k++){if(this[k]==l){return k}}return -1},lastIndexOf:function(k){var j=this.length-1;while(j>-1&&this[j]!=k){j--}return j},unique:function(j,k){var i=new Array();this.forEach(function(m,l){if(0==l||(j?i.last()!=m:!i.include(m,k))){i.push(m)}},this);return i},grep:function(m,l,k){var i=new Array();l||(l=function(n){return n});var j=k?l.createDelegate(k):l;if(typeof m=="string"){m=new RegExp(m)}m instanceof RegExp&&this.forEach(function(o,n){m.test(o)&&i.push(j(o,n))});return i},first:function(){return this[0]},last:function(){return this[this.length-1]},clear:function(){this.length=0},atRandom:function(j){var i=Math.floor(Math.random()*this.length);return this[i]||j},clone:function(i){if(!i){return this.concat()}var k=this.length||0,j=new Array(k);while(k--){j[k]=Ext.clone(this[k],true)}return j},forEach:function(j,i){Array.forEach(this,j,i)}});window.forEach=function(j,m,k,i){k=k||j;if(j){if(typeof m!="function"){throw new TypeError()}var l=Object;if(j instanceof Function){l=Function}else{if(j.forEach instanceof Function){return j.forEach(m,k)}else{if(typeof j=="string"){l=String}else{if(Ext.isNumber(j.length)){l=Array}}}}return l.forEach(j,m,k,i)}};Ext.clone=function(j,i){if(j===null||j===undefined){return j}if(Ext.isFunction(j.clone)){return j.clone(i)}else{if(Ext.isFunction(j.cloneNode)){return j.cloneNode(i)}}var k={};forEach(j,function(m,l,n){k[l]=(m===n?k:i?Ext.clone(m,true):m)},j,i);return k};var g=Array.prototype.slice;var e=Array.prototype.filter;Ext.applyIf(Array,{slice:function(i){return g.apply(i,g.call(arguments,1))},filter:function(k,j){var i=k&&typeof k=="string"?k.split(""):[];return e.call(i,j)},forEach:function(o,n,m){if(typeof n!="function"){throw new TypeError()}for(var k=0,j=o.length;k<j;k++){n.call(m,o[k],k,o)}}});Ext.applyIf(RegExp.prototype,{clone:function(){return new RegExp(this)}});Ext.applyIf(Date.prototype,{clone:function(i){return i?new Date(this.getTime()):this}});Ext.applyIf(Boolean.prototype,{clone:function(){return this==true}});Ext.applyIf(Number.prototype,{times:function(m,k){var l=parseInt(this,10)||0;for(var j=1;j<=l;){m.call(k,j++)}},forEach:function(){this.times.apply(this,arguments)},clone:function(){return(this)+0}});Ext.applyIf(String.prototype,{trim:function(){var i=/^\s+|\s+$/g;return function(){return this.replace(i,"")}}(),trimRight:function(){var i=/^|\s+$/g;return function(){return this.replace(i,"")}}(),trimLeft:function(){var i=/^\s+|$/g;return function(){return this.replace(i,"")}}(),clone:function(){return String(this)+""},forEach:function(j,i){String.forEach(this,j,i)}});var c=function(p,n){var o=typeof p=="function"?p:function(){};var m=o._ovl;if(!m){m={base:o};m[o.length||0]=o;o=function(){var l=arguments.callee._ovl;var i=l[arguments.length]||l.base;return i&&i!=arguments.callee?i.apply(this,arguments):undefined}}var q=[].concat(n);for(var k=0,j=q.length;k<j;k++){m[q[k].length]=q[k]}o._ovl=m;return o};Ext.applyIf(Ext,{overload:c(c,[function(i){return c(null,i)},function(k,j,i){return k[j]=c(k[j],i)}]),isIterable:function(i){if(i===null||i===undefined){return false}if(Ext.isArray(i)||!!i.callee||Ext.isNumber(i.length)){return true}return !!((/NodeList|HTMLCollection/i).test(a.toString.call(i))||i.nextNode||i.item||false)},isArray:function(i){return a.toString.apply(i)=="[object Array]"},isObject:function(i){return(i!==null)&&typeof i=="object"},isNumber:function(i){return typeof i=="number"&&isFinite(i)},isBoolean:function(i){return typeof i=="boolean"},isDocument:function(i){return a.toString.apply(i)=="[object HTMLDocument]"||(i&&i.nodeType===9)},isElement:function(i){return i&&Ext.type(i)=="element"},isEvent:function(i){return a.toString.apply(i)=="[object Event]"||(Ext.isObject(i)&&!Ext.type(i.constructor)&&(window.event&&i.clientX&&i.clientX===window.event.clientX))},isFunction:function(i){return a.toString.apply(i)=="[object Function]"},isString:function(i){return typeof i=="string"},isDefined:h});Ext.capabilities={hasActiveX:!!window.ActiveXObject,hasXDR:function(){return(Ext.isIE&&h(window.XDomainRequest))||Ext.isSafari4||(Ext.isGecko&&"withCredentials" in new XMLHttpRequest())}(),hasFlash:(function(){if(window.ActiveXObject){try{new ActiveXObject("ShockwaveFlash.ShockwaveFlash");return true}catch(l){}return false}else{if(navigator.plugins){for(var j=0,k=navigator.plugins.length;j<k;j++){if((/flash/gi).test(navigator.plugins[j].name)){return true}}return false}}return false})(),hasCookies:!!navigator.cookieEnabled,hasCanvas:!!document.createElement("canvas").getContext,hasSVG:!!(document.createElementNS&&document.createElementNS("http://www.w3.org/2000/svg","svg").width),hasXpath:!!document.evaluate,hasBasex:true,isEventSupported:function(l,m){var k={select:"input",change:"input",submit:"form",reset:"form",error:"img",load:"img",abort:"img"},i={},j=function(p,o){var n=Ext.getDom(o);return(n?(Ext.isElement(n)||Ext.isDocument(n)?n.nodeName.toLowerCase():o.self?"#window":o||"#object"):o||"div")+":"+p};return function(r,t){var s,q=false;var o="on"+r;var n=(t?t:k[r])||"div";var p=j(r,n);if(p in i){return i[p]}s=Ext.isString(n)?document.createElement(n):t;q=(!!s&&(o in s));q||(q=window.Event&&!!(String(r).toUpperCase() in window.Event));if(!q&&s){s.setAttribute&&s.setAttribute(o,"return;");q=Ext.isFunction(s[o])}i[p]=q;s=null;return q}}()}})();Ext.applyIf(Function.prototype,{forEach:function(a,e,d,c){if(a){var b;for(b in a){(!!c||a.hasOwnProperty(b))&&e.call(d||a,a[b],b,a)}}},clone:function(a){return this}});