function jsonToString(json) {
    var _array = [];
    for (var i in json) {
        json[i] = typeof(json[i]) == 'string' ? '"' + json[i] + '"' : (typeof (json[i]) == 'object' ? jsonToString(json[i]) : json[i]);
        _array.push(i + ":" + json[i]);
    }
    return "{" + _array.join(',') + "}";
}
window.onerror = function(message){
    alert("错误原因："+arguments[0]+"\n错误URL:"+arguments[1]+"\n错误行号："+arguments[2]);
    return true;//禁止浏览器显示标准出错信息
};