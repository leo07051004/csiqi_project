//****************针对第一种方式的具体js实现部分******************//
//****************所使用的数据是city.js******************//

var prov= document.getElementById("prov");
var city = document.getElementById('city');
var country = document.getElementById('country');
showCity(city);

/*用于保存当前所选的省市区*/
var current = {
    prov: '11',
    city: '',
    country: ''
};

/*自动加载省份列表*/
function showProv() {
    var len = provice.length;
    for (var i = 0; i < len; i++) {
        var provOpt = document.createElement('option');
        provOpt.innerText = provice[i]['name'];
        provOpt.value = i;
        prov.appendChild(provOpt);
    }
};

/*根据所选的省份来显示城市列表*/
function showCity(obj) {
    //var val = obj.options[obj.selectedIndex].value;
	var val = 11;
    if (val != null) {
        var cityLen = provice[val]["city"].length;
        for (var j = 0; j < cityLen; j++) {
            var cityOpt = document.createElement('option');
            cityOpt.innerText = provice[val]["city"][j].name;
            cityOpt.value = j;
            city.appendChild(cityOpt);
        }
    }
}

/*根据所选的城市来显示县区列表*/
function showCountry(obj) {
    var val = obj.options[obj.selectedIndex].value;
    current.city = val;
    if (val != null&&provice[current.prov]["city"][val]) {
        var countryLen = provice[current.prov]["city"][val].districtAndCounty.length;
        if(countryLen == 0){
            return;
        }
        country.innerHTML='<option value="">请选择</option>';
        for (var n = 0; n < countryLen; n++) {
            var countryOpt = document.createElement('option');
            countryOpt.innerText = provice[current.prov]["city"][val].districtAndCounty[n];
            countryOpt.value = n;
            country.appendChild(countryOpt);
        }
    }
    country.value="";
}

/*选择县区之后的处理函数*/
function selecCountry(obj) {
    current.country = obj.options[obj.selectedIndex].value;
    if ((current.city != null) && (current.country != null)) {
    }
}