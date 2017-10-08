var meetingroomPath = "http://localhost:8080/meetingroom"

/**
 * 新增会议室检查并提交
 */
$("#addMeetingroom").click(function () {
    var roomNameA = $("#roomNameA").val();
    var meetingroomCapacityA = $("#meetingroomCapacityA").val();
    var meetingroomStatusA = $("#meetingroomStatusA").val();
    var meetingroomRemarkA = $("#meetingroomRemarkA").val();
    if (roomNameA == '') {
        alert("会议室名不能为空");
        return;
    }
    if (meetingroomCapacityA == '') {
        alert("会议室可容纳人数不能为空");
        return;
    }

    meetingroomStatusA == '不可使用' ? "1" : "0";

    $.ajax({
        type: "POST",
        async: false,
        url: meetingroomPath + "/add",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            roomName: roomNameA,
            capacity: meetingroomCapacityA,
            status: meetingroomStatusA,
            remark: meetingroomRemarkA
        }),
        success: function (data, status) {
            alert(data.m);
        }
    });
    window.location.reload();
});

/**
 * 编辑会议室信息
 * @param obj
 */
function editMrInfo(obj) {
    var id = $(obj).attr("id");
    id = id.substring(6);

    var roomName = document.getElementById("roomName" + id).innerText;
    var capacity = document.getElementById("capacity" + id).innerText;
    var remark = document.getElementById("remark" + id).innerText;

    $('#idC').val(id);
    $('#roomNameC').val(roomName);
    $('#meetingroomCapacityC').val(capacity);
    $('#meetingroomRemarkC').val(remark);
};

/**
 * 更新会议室信息
 */
$('#updateMeetingroom').click(function () {
    var idC = $("#idC").val();
    var roomNameC = $("#roomNameC").val();
    var meetingroomCapacityC = $("#meetingroomCapacityC").val();
    var meetingroomRemarkC = $("#meetingroomRemarkC").val();
    var meetingroomStatusC = $("#meetingroomStatusC").val();

    if (roomNameC == '') {
        alert("会议室名不能为空");
        return;
    }
    if (meetingroomCapacityC == '') {
        alert("会议室可容纳人数不能为空");
        return;
    }

    meetingroomStatusC = (meetingroomStatusC == '暂不可使用' ? "1" : "0");

    $.ajax({
        type: "POST",
        async: false,
        url: meetingroomPath + "/update",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            id: idC,
            roomName: roomNameC,
            status: meetingroomStatusC,
            capacity: meetingroomCapacityC,
            remark: meetingroomRemarkC
        }),
        success: function (data, status) {
            alert(data.m);
        }
    });
    window.location.reload();
});

/**
 * 删除会议室
 * @param obj
 */
function cancelMrInfo(obj) {
    var id = $(obj).attr("id");
    id = id.substring(6);
    var roomName = document.getElementById("roomName" + id).innerText;

    if (confirm("确认要将" + roomName + "置为不可使用?\r" +
            "警告！已预定的相关会议将一同被取消！")) {

        var changeReason = prompt("请输入置位原因（不能为空）:", "");

        if (changeReason == null || changeReason.trim() == "") {
            alert("原因不能为空");
            return;
        } else {
            $.post(meetingroomPath + "/cancel", {
                id: id,
                changeReason: changeReason.trim()
            }, function (data, status) {
                alert(data.m);
            });
            window.location.reload();
        }
    }

};
