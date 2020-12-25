
/**
 * 日期格式化工具；用法：date.format('yyyy-MM-dd hh:mm:ss')
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

/**
 * long类型转string
 * @param date
 * @returns
 */
function getFormatDate(mill, pattern, isBr) {
	
	var r = "";
	if(mill){
		var date = new Date(mill);
		
		if (pattern == undefined) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}
		r = date.format(pattern);
		
		if(isBr==true){
			r = r.substring(11,19)+"</br>"+r.substring(0,11)
		}
	}
	
	return r;
	
}


//获取前一个月的日期
//入参格式：YYYY-MM-DD
function getPreMonthDay(date) {
  var arr = date.split('-');
  var year = arr[0];     //当前年
  var month = arr[1];      //当前月
  var day = arr[2];        //当前日
  //验证日期格式为YYYY-MM-DD
  var reg = date.match(/^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$/);
  if ((!reg) || (month > 12) || (day > 31)) {
      console.log('日期或格式有误！请输入正确的日期格式（年-月-日）');
      return;
  }

  var pre_year = year;     //前一个月的年
  var pre_month = parseInt(month) - 1;      //前一个月的月，以下几行是上月数值特殊处理
  if (pre_month === 0) {
      pre_year = parseInt(pre_year) - 1;
      pre_month = 12;
  }
  var pre_day = parseInt(day);       //前一个月的日，以下几行是特殊处理前一个月总天数
  var pre_month_alldays = (new Date(pre_year, pre_month, 0)).getDate();    //巧妙处理，返回前一个月的总天数
  if (pre_day > pre_month_alldays) {
      pre_day = pre_month_alldays;
  }
  if (pre_month < 10) {   //补0
      pre_month = '0' + pre_month;
  }
  else if (pre_day < 10) {   //补0
      pre_day = '0' + pre_day;
  }

  var pre_month_day = pre_year + '-' + pre_month + '-' + pre_day;
  return pre_month_day;
}

