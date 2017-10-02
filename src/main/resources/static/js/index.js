var mrbPath = "http://localhost:8080/book"

function isLogin() {
    var username = $("#username").val()
    if (username == null) {
        alert("请先登陆！")
        return false;
    }
    return true;
}

$("#speedMeetingroomBook").click(function () {

    if (!isLogin()) {
        window.location.href = mrbPath
        return;
    }

    var minutesAfterNow = $("#minutesAfterNow").val();

    if (minutesAfterNow > 1440) {
        alert("一天后的预定也需要极速预定嘛？请使用新增预定功能吧~")
        return;
    }

    var duration = $("#duration").val();

    if (duration > 60) {
        alert("极速预定使用时间不能超过1个小时~")
        return;
    }
    var deptId = $("#deptId").val();

    $.post(mrbPath + "/speedMeetingroomBook",
        {
            deptId: deptId,
            minutesAfterNow: minutesAfterNow,
            duration: duration
        }, function (data, status) {
            alert(data.m);
        });
    window.location.reload();
});


/**
 * 根据id获取需审核的预定信息
 * @param obj
 */
function auditMeetingroomBook(obj) {

    if (!isLogin()) {
        window.location.href = mrbPath
        return;
    }

    var username = $("#username").val()
    if (username == null) {
        alert("请先登陆！")
        return;
    }

    var id = $(obj).attr("id");
    id = id.substring(5);
    $.ajax({
        url: mrbPath + "/audit?id=" + id,
        type: "get",
        dataType: "json",
        success: function (resData) {
            $('#idD').val(resData.id);
            $('#deptNameD').val(resData.deptName);
            $('#roomNameD').val(resData.roomName);
            $('#bookReasonD').val(resData.bookReason);
            $('#startTimeD').val(resData.meetingBeginTime);
            $('#endTimeD').val(resData.meetingEndTime);
            $('#remarkD').val(resData.remark);
            $('#auditStatusD').val('');
        }
    });
}

/**
 * 审核预定检查并提交
 */
$("#editAuditMrBook").click(function () {

    if (!isLogin()) {
        window.location.href = mrbPath
        return;
    }
    var auditStatusD = $("#auditStatusD").val();
    var idD = $("#idD").val();
    if (auditStatusD == '') {
        alert("审核意见不能为空");
        return;
    }
    if (auditStatusD == '通过') {
        auditStatusD = '1';
    }
    if (auditStatusD == '不通过') {
        auditStatusD = '2';
    }
    if (auditStatusD != '0' && auditStatusD != '1' && auditStatusD != '2') {
        alert("请输入审核意见：0待审核，1审核通过，2审核不通过");
        return;
    }
    $.post(mrbPath + "/editAudit",
        {
            id: idD,
            auditStatus: auditStatusD
        }, function (data, status) {
            if (data.m == null) {
                alert("请先登陆")
                return;
            }
            alert(data.m);
        });
    window.location.reload();
});

/**
 * 取消预定
 * @param obj
 */
function cancelMeetingroomBook(obj) {
    if (!isLogin()) {
        window.location.href = mrbPath
        return;
    }

    var id = $(obj).attr("id");
    id = id.substring(6);
    var changeReason = prompt("请输入取消预订的原因:", "");

    if (changeReason == null) {
        return;
    }

    changeReason = changeReason.trim();
    if (changeReason == "") {
        alert("取消原因不能为空");
        return;
    }

    $.post(mrbPath + "/cancel",
        {
            id: id,
            changeReason: changeReason
        }, function (data, status) {

            if (data.m == null) {
                alert("请先登陆")
                return;
            }
            alert(data.m);
        });
    window.location.reload()
}
