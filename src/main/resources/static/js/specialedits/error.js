function jsonToString(json) {
    var _array = [];
    for (var i in json) {
        json[i] = typeof(json[i]) == 'string' ? '"' + json[i] + '"' : (typeof (json[i]) == 'object' ? jsonToString(json[i]) : json[i]);
        _array.push(i + ":" + json[i]);
    }
    return "{" + _array.join(',') + "}";
}
window.onerror = function(message){
    alert("����ԭ��"+arguments[0]+"\n����URL:"+arguments[1]+"\n�����кţ�"+arguments[2]);
    return true;//��ֹ�������ʾ��׼������Ϣ
};