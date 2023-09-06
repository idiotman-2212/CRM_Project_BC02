$(document).ready(function() {

    // Sự kiện click cho nút Xóa
    $(".btn-xoa").click(function() {
        var id = $(this).attr('id-task');
        var This = $(this);
        $.ajax({
            method: "GET",
            url: `http://localhost:8080/crm_project_02/api/task/delete?id=${id}`,
        })
        .done(function(result) {
            if (result.data == true) {
                This.closest('tr').remove();
            }
        })
        .fail(function() {
            console.log("Lỗi khi gửi request");
        });
    });

    // Sự kiện click cho nút Sửa
    $(".btn-sua").click(function() {
        var id = $(this).attr("id-task");
        $.ajax({
            method: "GET",
            url: `http://localhost:8080/crm_project_02/api/task/update?id=${id}`,
        })
        .done(function(response) {
            if (response.statusCode === 200) {
                var task = response.data;
                window.location.href = `sua-task.jsp?id=${task.id}&name=${task.name}&projectName=${task.groupWork.name}&fullName=${task.users.fullName}&startDate=${task.startDate}&endDate=${task.endDate}&status_name=${task.status.name}`;
            } else {
                console.log("Lỗi khi lấy thông tin công việc");
            }
        })
        .fail(function() {
            console.log("Lỗi khi gửi request");
        });
    });
});
