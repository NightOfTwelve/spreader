Ext.BLANK_IMAGE_URL = $CONFIG.BLANK_IMAGE_URL;
Ext.chart.Chart.CHART_URL = $CONFIG.CHART_URL;
Ext.QuickTips.init();
var host = 'http://' + window.location.host;
Ext.util.CSS.swapStyleSheet("theme", "../js/extjs3/resources/css/xtheme-gray.css");
/**
 * 捕获ajax异常做重定向处理 登录超时
 */
Ext.Ajax.on('requestexception', function(conn, response, options) {
			if (response.status == '401') {
				setTimeout(function() {
							// 延时避免被failure回调函数中的aler覆盖
							top.Ext.MessageBox.alert('提示', '会话链接超时，请重新登录',
									function() {
										parent.location.href = host
												+ '/spreader-backend/account/init';
									});
						}, 200);
			}
		});
