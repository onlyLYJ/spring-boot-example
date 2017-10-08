//密码复杂要求：要求包含字母、数字。至少6位，至多20位
function checkValidPass(s) {
    //返回'OK'表示合格
    if (s.length < 6)
        return '密码至少为6位！';
    if (s.length > 20)
        return '您的密码长度击败了超过全国99.99%的人，请设置小于20位~';
    if (!s.match(/[0-9]/))
        return '密码中必须包含数字';
    if (!s.match(/[a-zA-Z]/))
        return '密码中必须包含字母';
    return 'OK';
}

function isValidEnglishname(name) {
    var regex = /^[0-9A-Za-z_]{5,15}$/;
    return regex.exec(name)
}

$("#addEmployee").click(function () {
    var regRealName = $("#regRealName").val();
    var regEnglishName = $("#regEnglishName").val();
    var regPassword = $("#regPassword").val();
    if (regRealName == '') {
        alert("请先填写您的真实姓名");
        return;
    }
    if (!isValidEnglishname(regEnglishName)) {
        alert("账号请输入5-15位字母数字组合");
        return;
    }

    var passInfo = checkValidPass(regPassword);
    if (passInfo != 'OK') {
        alert(passInfo);
        return;
    }
    var regDepartment = $("#regDepartment").val();
    console.log(regDepartment);
    $.ajax({
        type: "POST",
        async: false,
        url: employeeBasePath + "/add",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            department: regDepartment,
            realName: regRealName,
            englishName: regEnglishName,
            password: regPassword
        }),
        success: function (data, status) {
            alert(data.m);
        }
    });
    window.location.reload();
});
