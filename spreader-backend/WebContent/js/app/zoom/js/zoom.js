var zoomWin = new Ext.Window({
	id : 'zoomWin',
	title : '图片浏览',
	width : 500,
	height : 500,
	resizable : false,
	closeAction : 'hide',
	layout : 'fit',
	items : [{
		xtype : 'panel',
//		region : 'center',
		layout : 'fit',
		bodyStyle : 'background-color:#E5E3DF;',
		frame : false,
		border : false,
		html : '<div id="mapPic"><div class="nav">'
				+ '<div class="up" id="up"></div><div class="right" id="right"></div>'
				+ '<div class="down" id="down"></div><div class="left" id="left"></div>'
				+ '<div class="zoom" id="zoom"></div><div class="in" id="in"></div>'
				+ '<div class="out" id="out"></div></div>'
				+ '<img id="image" src="" border="0" width=200 height=200> </div>'
	}],
	// buttons : [{
	// text : '取消',
	// handler : function() {
	// zoomWin.hide();
	// }
	// }],
	listeners : {
		'show' : function(c) {
			pageInit();
		}
	}
});

/**
 * 初始化
 */
function pageInit() {
	var image = Ext.get('image');

	if (image != null) {
		Ext.get('image').on({
			'mousedown' : {
				fn : function() {
					this
							.setStyle('cursor',
									'url(../js/app/zoom/images/closedhand_8_8.cur),default;');
				},
				scope : image
			},
			'mouseup' : {
				fn : function() {
					this
							.setStyle('cursor',
									'url(../js/app/zoom/images/openhand_8_8.cur),move;');
				},
				scope : image
			},
			'dblclick' : {
				fn : function() {
					zoom(image, true, 1.2);
				}
			}
		});
		new Ext.dd.DD(image, 'pic');

		image.center();// 图片居中

		// 获得原始尺寸
		image.osize = {
			width : image.getWidth(),
			height : image.getHeight()
		};

		Ext.get('up').on('click', function() {
					imageMove('up', image);
				}); // 向上移动
		Ext.get('down').on('click', function() {
					imageMove('down', image);
				}); // 向下移动
		Ext.get('left').on('click', function() {
					imageMove('left', image);
				}); // 左移
		Ext.get('right').on('click', function() {
					imageMove('right', image);
				}); // 右移动
		Ext.get('in').on('click', function() {
					zoom(image, true, 1.5);
				}); // 放大
		Ext.get('out').on('click', function() {
					zoom(image, false, 1.5);
				}); // 缩小
		Ext.get('zoom').on('click', function() {
					restore(image);
				}); // 还原
	}
};

/**
 * 图片移动
 */
function imageMove(direction, el) {
	el.move(direction, 50, true);
}

/**
 * 
 * @param el
 *            图片对象
 * @param type
 *            true放大,false缩小
 * @param offset
 *            量
 */
function zoom(el, type, offset) {
	var width = el.getWidth();
	var height = el.getHeight();
	var nwidth = type ? (width * offset) : (width / offset);
	var nheight = type ? (height * offset) : (height / offset);
	var left = type ? -((nwidth - width) / 2) : ((width - nwidth) / 2);
	var top = type ? -((nheight - height) / 2) : ((height - nheight) / 2);
	el.animate({
				height : {
					to : nheight,
					from : height
				},
				width : {
					to : nwidth,
					from : width
				},
				left : {
					by : left
				},
				top : {
					by : top
				}
			}, null, null, 'backBoth', 'motion');
}

/**
 * 图片还原
 */
function restore(el) {
	var size = el.osize;

	// 自定义回调函数
	function center(el, callback) {
		el.center();
		callback(el);
	}
	el.fadeOut({
				callback : function() {
					el.setSize(size.width, size.height, {
								callback : function() {
									center(el, function(ee) {// 调用回调
												ee.fadeIn();
											});
								}
							});
				}
			});
}