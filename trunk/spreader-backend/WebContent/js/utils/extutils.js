/**
 * 创建新的tab
 * 实现 -> 防止重复创建 -> 已经创建过的,再点击打开时需要定位到改选项卡上
 * @param rec               树型结构中的数据
 * @param tabPanelId        tabPanel的id
 * @param array             已创建过的所有选项卡的id集合
 * @param indexText         需要自行创建一个带'_'的id的前缀,  如 'xxxx_'
 * @param redirectUrl       跳转的url
 */
function createTab( rec , tabPanelId , array , indexText , redirectUrl) {
    var leaf = rec.get('leaf') ;//判断是否是父节点
    if (leaf) {
        var nodeId = indexText + rec.get('id');
        var tabText = rec.get('text');
        var tabIconCls = rec.get('iconCls');
        if (array.length > 0) {
            var hasExit = false;
            var exitPanelId = '';
            for (var i = 0; i < array.length; i ++) {
                if (array[i].indexOf(nodeId) != -1) {
                    hasExit = true;
                    exitPanelId = nodeId;
                    break;
                }
            }
            if (hasExit) {
                Ext.getCmp(exitPanelId).show();
            }
            if (!hasExit) {
                addTab(true, tabPanelId , array , nodeId, tabText, tabIconCls);
                array.push(nodeId);
                Ext.getCmp(nodeId).getEl().dom.innerHTML = '<iframe width="100%" height="100%" align="center" src="' + redirectUrl + '"/>';
            }
        } else {
            addTab(true, tabPanelId , array , nodeId, tabText, tabIconCls);
            array.push(nodeId);
            Ext.getCmp(nodeId).getEl().dom.innerHTML = '<iframe width="100%" height="100%" align="center" src="' + redirectUrl + '"/>';
        }
    }
}
/**
 * 创建一个选项卡
 * @param closable      是否要创建一个tab, true创建
 * @param array         临时变量
 * @param id            创建一个唯一的id
 * @param tabText       选项卡的内容
 * @param tabIconCls    选项卡上的图片
 */
function addTab(closable , tabPanelId , array ,id ,  tabText , tabIconCls) {
    Ext.getCmp(tabPanelId).add({
        id : id,
        title: '<div align="center">' + tabText + '<div>',
        iconCls: tabIconCls,
        html: '页面内容' + tabText,
        closable: !!closable,
        listeners : {
            "destroy" : function(){
                array.remove(id) ;//从临时变量中移除
                if ( array.length > 0 ){
                    Ext.getCmp(array[array.length - 1]).show();//显示最后一个
                }
            }
        }
    }).show();
}

var xx = new Ext.menu.Menu({
    id:'rightClickCont',
    items: [
        {
            //id: 'rMenu1',
            //handler: rMenu1Fn,
            text: '右键菜单1'
        },
        {
            //id: 'rMenu2',
            //handler: rMenu2Fn,
            text: '右键菜单2'
        }
    ]
});

/**
 * 隐藏一个panel
 * @param divId     例 : <a id="hideit" href="#">Toggle the west region</a> , 这里的id
 * @param panelId   需要被隐藏的面板的id
 */
function hidePanelLink ( divId , panelId ){
    Ext.get(divId).on('click', function() {
        var w = Ext.getCmp(panelId);
        w.collapsed ? w.expand() : w.collapse();
    });
}

function hidePanelDbClick ( panelId ){
    var w = Ext.getCmp(panelId);
    w.collapsed ? w.expand() : w.collapse();
}

function addTabNew(url,name,menuid,pathCh,icon){
  var id = "tab_id_" + menuid;
  if(url == '#' || url == ''){
    Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');
  }else{
//  var index = url.indexOf('.ered');
//  if(index != -1)
//    url = url + '&menuid4Log=' + menuid;
  var n = mainTabPanel.getComponent(id);
  if (!n) {
     // 如果对centerPanel进行遮罩,则可以出现阴影mainTabs
     //Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>正在加载页面,请稍等...</span>', 'x-mask-loading');
     // document.getElementById('endIeStatus').click();//解决Iframe IE加载不完全的问题
     // 兼容IE和FF触发.click()函数
//     var endIeStatus = document.getElementById("endIeStatus");
//     if(document.createEvent){
//         var ev = document.createEvent('HTMLEvents');
//         ev.initEvent('click', false, true);
//         endIeStatus.dispatchEvent(ev);
//     }
//     else endIeStatus.click();
     n = mainTabPanel.add({
       id:id,
//       title:"<img align='top' class='IEPNG' src='./resource/image/ext/" + icon + "'/>" + name,
       title: name,
       closable:true,
       layout:'fit',
       listeners: {activate: function(){Ext.getCmp('centerPanel').setTitle(pathCh)}},
       html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src='+url+'></iframe>'
	   //如果功能页面使用中心区域阴影加载模式则使用下面的代码unmaskCenterPanel();页面加载完毕后取消阴影
	   //html:'<iframe scrolling="auto" frameborder="0" onload="unmaskCenterPanel()" width="100%" height="100%" src='+url+'></iframe>'
         });
       }
  mainTabPanel.setActiveTab(n);
 }
  }