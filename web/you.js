/**
 * Created by admin@earthvip.xyz on 2017/1/29.
 */

/**
 * sql语句拼装
 * @param sql
 * @param arr
 * @returns {string}
 */
function you_sql(sql, arr) {
    var a = sql.split("?");
    var result = "";
    for (var temp in a) {
        if (arr[temp] == null) {
            result = result + a[temp];
        } else {
            //判断是否为字符串，加双引号
            if (isNaN(arr[temp])) {
                arr[temp] = '"' + arr[temp] + '"';
            }
            result = result + a[temp] + arr[temp];
        }
    }
    console.info("you_sql:" + result);
    return result;
}

/**
 * 暴露的ajax请求
 * @param 回调函数
 * @param sql
 * @param.. 多个参数
 */
function you_ajax() {
    var fn = arguments[0];
    var sql = arguments[1];
    var arr = new Array();
    for (var temp in arguments) {
        if (temp > 1) {
            arr.push(arguments[temp]);
        }
    }
    var ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4) {
            if (ajax.status = 200) {
                fn(JSON.parse(ajax.responseText))
            }
        }
    };
    ajax.open("GET", "you.php?sql=" + you_sql(sql, arr), true);
    ajax.send();
}


/**
 * 解析for
 * row为for,参数,模板,当前元素
 * @returns {Array}
 */
function you_for() {
    var result = new Array();
    $.each($("*[for*='you']"), function (i, n) {
        var arr = new Array();
        var a = $(n).attr("for");
        var o = n.outerHTML;
        var match = o.match(eval("/\{\{[a-zA-Z_0-9]+\}\}/g"));
        for (var temp in match) {
            var temp1 = match[temp];
            arr.push(temp1.substr(2, temp1.length - 4))
        }
        o = o.replace(eval("/ for=\"[a-zA-Z_0-9]+\"/g"), "").replace(eval("/\{\{[a-zA-Z_0-9]+\}\}/g"), "you?");
        var row = new Array(a, arr, o, n);
        result.push(row);
    });
    return result;
}
/**
 * 工具类，数组删除指定下标元素
 * @param arr
 * @param n
 * @returns {Array}
 */
function you_remove(arr, n) {
    var result = new Array();
    for (var temp in arr) {
        if (temp != n) {
            result.push(arr[temp]);
        }
    }
    return result;
}
/**
 * @param 最开始的网页标签
 */
var you_binded = new Array();
/**
 * 页面for绑定替换
 * @param 被替换for
 * @param 替换的数据
 */
function you_bind(temp1, temp2) {
    var f = you_for();
    for (var temp in f) {
        var ff = f[temp];
        //找到匹配sql结果的页面元素
        if (ff[0] == temp1) {
            var result = "<div id='" + temp1 + "'>";
            for (var i in temp2) {
                //拼装模板
                var split1 = ff[2].split("you?");
                for (var s2 in split1) {
                    if (s2 == split1.length - 1) {
                        result = result + split1[s2];
                    } else {
                        var aaa = ff[1][s2];
                        result = result + split1[s2] + eval("temp2[i]." + aaa);
                    }
                }
            }
            result = result + "</div>";
            //替换
            $(result).replaceAll(ff[3]);
            you_binded.push(new Array(ff));
            return;
        }
    }
    for (var temped in you_binded) {
        var ff = you_binded[temped][0];
        if (ff[0] == temp1) {
            var result = "";
            for (var i in temp2) {
                //拼装模板
                var split1 = ff[2].split("you?");
                for (var s2 in split1) {
                    if (s2 == split1.length - 1) {
                        result = result + split1[s2];
                    } else {
                        var aaa = ff[1][s2];
                        result = result + split1[s2] + eval("temp2[i]." + aaa);
                    }
                }
            }
            //替换
            $("#" + temp1).html(result);
            return;
        }
    }
    //未找到提示
    console.info("error:bind fall");
    console.info(temp1);
    console.info(f);
}
