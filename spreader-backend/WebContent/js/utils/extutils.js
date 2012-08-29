/**
 * 隐藏一个panel
 * 
 * @param divId
 *            例 : <a id="hideit" href="#">Toggle the west region</a> , 这里的id
 * @param panelId
 *            需要被隐藏的面板的id
 */
function hidePanelLink(divId, panelId) {
	Ext.get(divId).on('click', function() {
				var w = Ext.getCmp(panelId);
				w.collapsed ? w.expand() : w.collapse();
			});
}

function hidePanelDbClick(panelId) {
	var w = Ext.getCmp(panelId);
	w.collapsed ? w.expand() : w.collapse();
}

function addTabNew(url, name, menuid, pathCh, icon) {
	var id = "tab_id_" + menuid;
	if (url == '#' || url == '') {
		Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');
	} else {
		// var index = url.indexOf('.ered');
		// if(index != -1)
		// url = url + '&menuid4Log=' + menuid;
		var n = mainTabPanel.getComponent(id);
		if (!n) {
			// 如果对centerPanel进行遮罩,则可以出现阴影mainTabs
			Ext.getCmp('centerPanel').getEl().mask(
					'<span style=font-size:12>正在加载页面,请稍等...</span>',
					'x-mask-loading');
			// document.getElementById('endIeStatus').click();//解决Iframe
			// IE加载不完全的问题
			// 兼容IE和FF触发.click()函数
			// var endIeStatus = document.getElementById("endIeStatus");
			// if (document.createEvent) {
			// var ev = document.createEvent('HTMLEvents');
			// ev.initEvent('click', false, true);
			// endIeStatus.dispatchEvent(ev);
			// } else
			// endIeStatus.click();
			n = mainTabPanel.add({
				id : id,
				// title:"<img align='top' class='IEPNG'
				// src='./resource/image/ext/" + icon + "'/>" + name,
				title : name,
				closable : true,
				layout : 'fit',
				listeners : {
					activate : function() {
						Ext.getCmp('centerPanel').setTitle(pathCh);
					}
				},
				// html:'<iframe scrolling="auto" frameborder="0" width="100%"
				// height="100%" src='+url+'></iframe>'
				// 如果功能页面使用中心区域阴影加载模式则使用下面的代码unmaskCenterPanel();页面加载完毕后取消阴影
				html : '<iframe scrolling="auto" frameborder="0" onload="unmaskCenterPanel()" width="100%" height="100%" src='
						+ url + '></iframe>'
			}).show();
		}
		mainTabPanel.setActiveTab(n);
	}
}
function unmaskCenterPanel() {
	// 如果对centerPanel进行遮罩,则可以出现阴影
	Ext.getCmp('centerPanel').getEl().unmask();
}
/**
 * 鼠标悬浮显示细节函数
 * 
 * @param {}
 *            value
 * @param {}
 *            p
 * @param {}
 *            record
 * @return {}
 */
function renderBrief(value, p, record) {
	if (!Ext.isEmpty(value)) {
		var bk = '<div title="' + value + '">' + value.substr(0, 50);
		if (value.length > 50) {
			bk = bk + "...";
		}
		bk = bk + "</div>";
		return bk;
	} else {
		return "";
	}
}
/**
 * 渲染带有HTML的字符串
 * 
 * @param {}
 *            value
 * @param {}
 *            p
 * @param {}
 *            record
 * @return {}
 */
function renderHtmlBrief(value, p, record) {
	var shtml = renderHtmlEncode(value);
	var str = renderBrief(shtml);
	return str;
}
/**
 * 将将某些字符(&, <, >,和')转换成它们等价的HTML字符， 以便在web页面中显示这些字符的字面值。
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderHtmlEncode(value) {
	if (!Ext.isEmpty(value)) {
		value = Ext.util.Format.htmlEncode(value);
	}
	return value;
}
/**
 * 将等价的HTML字符转换成某些字符(&, <, >, 和 ')。
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderHtmlDecode(value) {
	if (!Ext.isEmpty(value)) {
		value = Ext.util.Format.htmlDecode(value);
	}
	return value;
}
/**
 * 截取最后一个分隔符
 * 
 * @param {}
 *            id
 * @return {}
 */
function subStrLastId(id) {
	if (id != null) {
		var str = '';
		str = id.substring(0, id.length - 1);
		return str;
	}
}
/**
 * 日期格式
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderDate(value) {
	return value != null && value != '' ? new Date(value).format('Y-m-d') : '';
}
/**
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderDateHis(value) {
	return value != null && value != ''
			? new Date(value).format('Y-m-d H:i:s')
			: '';
}
/**
 * 日期格式化汉字模式
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderDateNYRHis(value) {
	return value != null && value != '' ? new Date(value)
			.format('Y年m月d日 H时i分s秒') : '';
}

function string2Date(str) {
	var d = new Date(Date.parse(str.replace(/-/g, "/")));
	return d;
}
/**
 * 获取默认日期
 * 
 * @param {}
 *            value
 * @return {}
 */
function createInitDate() {
	var tdate = renderDate(new Date());
	tdate = renderDateHis(tdate);
	return tdate;
}

/**
 * 获取当天的后一天
 * 
 * @param {}
 *            value
 */
function createNextDate() {
	var sdate = new Date().add(Date.DAY, 1);
	var tdate = renderDate(sdate);
	tdate = renderDateHis(tdate);
	return tdate;
}
/**
 * 格式化字符为指定颜色
 * 
 * @param {}
 *            text
 * @param {}
 *            color
 */
function renderTextColor(text, color) {
	if (!Ext.isEmpty(color)) {
		return '<span style="color:' + color + ';">' + text + '</span>';
	} else {
		return '<span style="color:red;">' + text + '</span>';
	}
}
/**
 * 格式化任务执行结果状态
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderJobResultStatus(value) {
	if (value == 0) {
		return '<span style="color:green;">' + '执行中' + '</span>';
	} else if (value == 1) {
		return '<span style="color:blue;">' + '完成' + '</span>';
	} else if (value == 2) {
		return '<span style="color:red;">' + '异常中断' + '</span>';
	}
	return value;
}
/**
 * 格式化任务状态
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderTaskStatus(value) {
	if (value == 0) {
		return '<span style="color:#8600FF;">' + '已分配' + '</span>';
	} else if (value == 1) {
		return '<span style="color:green;">' + '成功' + '</span>';
	} else if (value == 2) {
		return '<span style="color:red;">' + '失败' + '</span>';
	} else if (value == 3) {
		return '<span style="color:bule;">' + '放弃' + '</span>';
	} else if (value == 4) {
		return '<span style="color:black;">' + '过期' + '</span>';
	} else if (value == 5) {
		return '<span style="color:black;">' + '系统异常' + '</span>';
	}
	return value;
}
/**
 * 渲染颜色
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderRed(value) {
	if (value != null) {
		return '<span style="color:red;">' + value + '</span>';
	}
	return value;
}
/**
 * 渲染新增状态
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderAddStatus(value) {
	if (value) {
		return renderRed('新增记录');
	} else {
		return renderGreen('原始记录');
	}
}
/**
 * 渲染更新状态
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderStatus(value) {
	if (value) {
		return '<span style="color:green;">可更新</span>';
	} else {
		return '<span style="color:red;">正在更新中</span>';
	}
}
/**
 * NULL值渲染为红色
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderNobind(value) {
	if (Ext.isEmpty(value)) {
		return '<span style="color:red;">未绑定分类</span>';
	}
	if (value == -1) {
		return '<span style="color:blue;">无需分类</span>';
	}
	return value;
}
/**
 * 渲染颜色
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderGreen(value) {
	if (value != null) {
		return '<span style="color:green;">' + value + '</span>';
	}
	return value;
}
/**
 * 性别中文渲染
 * 
 * @param {}
 *            value
 */
function renderGender(value) {
	if (value == '1') {
		return '男';
	} else {
		return '女';
	}
}
/**
 * 渲染是否
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendTrueFalse(value) {
	if (value) {
		return '是';
	} else {
		return '否';
	}
}
/**
 * 将URL渲染成图片
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderImage(value) {
	var url = '';
	if (value != null) {
		url = '<img src=\'' + value + '\' onload="AutoSize(this,32,32)"/>';
	}
	return url;
}
/**
 * 头像渲染，像素16*16
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderImage2(value) {
	var url = '';
	if (value != null) {
		url = '<img src=\'' + value + '\' onload="AutoSize(this,16,16)"/>';
	}
	return url;
}
/**
 * 渲染分组类型
 * 
 * @param {}
 *            value
 */
function renderGroupType(value) {
	if (value == 1) {
		return '简单分组';
	} else {
		return '复杂分组';
	}
}
/**
 * 用户分组类型渲染
 * 
 * @param {}
 *            value
 * @return {String}
 */
function renderUserGroupType(value) {
	if (value == 0) {
		return '静态分组';
	} else if (value == 1) {
		return '动态分组';
	} else {
		return '手动分组';
	}
}
/**
 * 网站类型渲染
 * 
 * @param {}
 *            value
 * @return {String}
 */
function renderWebsiteType(value) {
	if (value == 1) {
		return '新浪微博';
	}
	if (value == 2) {
		return '苹果';
	}
	return '其它';
}
/**
 * 渲染消息类型
 * 
 * @param {}
 *            value
 * @return {String}
 */
function renderNoticeType(value) {
	if (value == 1) {
		return '评论微博';
	}
	if (value == 2) {
		return '评论回复';
	}
	if (value == 3) {
		return '新增粉丝';
	}
	if (value == 4) {
		return '私信';
	}
	if (value == 5) {
		return '@到我的微博';
	}
	if (value == 6) {
		return '@到的评论';
	}
	if (value == 7) {
		return '群组消息';
	}
	if (value == 8) {
		return '相册消息';
	}
	return null;
}
/**
 * 自动缩放图片
 * 
 * @param {}
 *            ImgD
 * @param {}
 *            MaxWidth
 * @param {}
 *            MaxHeight
 */
function AutoSize(ImgD, MaxWidth, MaxHeight) {
	var image = new Image();
	image.src = ImgD.src;
	if (image.width > 0 && image.height > 0) {
		flag = true;
		if (image.width / image.height >= MaxWidth / MaxHeight) {
			if (image.width > MaxWidth) {
				ImgD.width = MaxWidth;
				ImgD.height = (image.height * MaxWidth) / image.width;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			// ImgD.alt="原始尺寸:宽" + image.width+",高"+image.height;
		} else {
			if (image.height > MaxHeight) {
				ImgD.height = MaxHeight;
				ImgD.width = (image.width * MaxHeight) / image.height;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			// ImgD.alt="原始尺寸:宽" + image.width+",高"+image.height;
		}
	}
}