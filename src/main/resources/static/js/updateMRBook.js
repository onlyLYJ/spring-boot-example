/**
 * 更新预定检查后提交
 * @returns {boolean}
 */
function checkValidBook(form) {
    if (updateBookCheck()) {
        $("#updateMrBook-form").submit();
    }
}

function updateBookCheck() {
    var bookReason = $("#bookReason").val().trim();
    var meetingBeginTime = $("#meetingBeginTime").val();
    var meetingEndTime = $("#meetingEndTime").val();
    var attendNum = $("#attendNum").val();
    if (bookReason == '') {
        alert("预定原因不能为空");
        return false;
    }
    if (meetingBeginTime == '') {
        alert("会议开始时间不能为空");
        return false;
    }

    if (meetingEndTime == '') {
        alert("会议结束时间不能为空");
        return false;
    }

    if (meetingBeginTime >= meetingEndTime) {
        alert("开始和结束时间得大于结束时间呀！");
        return false;
    }

    if (attendNum == '' || isNaN(attendNum)) {
        alert("与会人数错误");
        return false;
    }

    return true;
}

/**
 * POST提交更新预定表单
 */
$("#updateMrBook-form").submit(function () {
    $.ajax({
        type: 'POST',
        dataType: "json",
        data: $(this).serialize(),
        url: meetingroomPath + "/book/update",
        success: function (data, status) {
            alert(data.m);
            history.back(-1);
        }
    });
    return false;
});
