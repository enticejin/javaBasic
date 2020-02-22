/**
 * 显示加载层
 * 
 * @returns
 */
function showLoading() {
	window.parent.showLoading();
}

/**
 * 隐藏加载层
 * 
 * @returns
 */
function hideLoading() {
	window.parent.hideLoading();
}

/**
 * AJAX异常提示信息
 * 
 * @returns
 */
function getError() {
	return "与服务器通信故障，或系统异常，请联系管理员!";
}

/**
 * 判断是否浮点数
 * @param value
 * @returns
 */
function checkFloat(value) {
	var re = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;　　//正浮点数 
	return re.test(value) ;
}

/**
 * 判断是否为数字
 * @param value
 * @returns
 */
function checkNumber(value){
	var re=/^[+-]?\d*\.?\d*$/;
	return re.test(value);
}