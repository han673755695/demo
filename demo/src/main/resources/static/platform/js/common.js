/*分页方法*/
function pageFunction(totalCount,pageSize) {
	layui.use('laypage', function() {
		var laypage = layui.laypage;

		//执行一个laypage实例
		laypage.render({
			elem: 'page', //注意，这里的 test1 是 ID，不用加 # 号
			count: totalCount, //数据总数，从服务端得到
			limit: pageSize,
			layout: ['prev', 'page', 'next', 'limit'],
			limits: [10, 15, 20, 30, 40, 50],
			jump: function(obj, first) {
				//obj包含了当前分页的所有参数，比如：
				console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
				console.log(obj.limit); //得到每页显示的条数
				//首次不执行
				if(!first) {
					var dataJson = getData();
					dataJson.currentPage = obj.curr;
					dataJson.pageSize = obj.limit;
					searchAjax(dataJson);
				}
			}
		});
	});
}


/* 日期格式化 	使用示列new Date().format("yyyy-MM-dd hh:mm:ss")*/
Date.prototype.format = function(format) {
	var args = {
		"M+": this.getMonth() + 1,
              "d+": this.getDate(),
              "h+": this.getHours(),
              "m+": this.getMinutes(),
              "s+": this.getSeconds(),
              "q+": Math.floor((this.getMonth() + 3) / 3),
              "S": this.getMilliseconds()
	};          
	if(/(y+)/.test(format)){
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var i in args) {
		var n = args[i];
		if(new RegExp("(" + i + ")").test(format)){
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
		}
	}   
	return format;
};
