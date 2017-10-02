/**
 * 新增预定前检查
 * @returns {boolean}
 */
function addBookCheck() {
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

    if (meetingBeginTime > meetingEndTime) {
        alert("开始和结束时间是不是写反啦！");
        return false;
    }

    if (attendNum == '' || isNaN(attendNum)) {
        alert("与会人数错误");
        return false;
    }

    return true;
};

/**
 * 检查新增预定信息后提交
 */

function checkValidBook() {
    if (addBookCheck()) {
        $("#newBook-form").submit();
    }
};

/**
 * 提交新增预定
 */
$("#newBook-form").submit(function () {
    $.ajax({
        type: 'POST',
        dataType: "json",
        data: $(this).serialize(),
        url: mbrNewBookPath + "/apply",
        success: function (data, status) {
            alert(data.m);
        }
    });
    return false;
});

/**
 * 根据周期任务勾选框，显示日期时间选择框
 */
function showWeeklyBookEndDate() {
    var isChecked = $('#weeklyBook').is(":checked");
    if (isChecked != null && isChecked == true) {
        document.getElementById("weeklyBookEndTime").style.display = "";//显
    } else {
        document.getElementById("weeklyBookEndTime").style.display = "none";//隐
    }
}
