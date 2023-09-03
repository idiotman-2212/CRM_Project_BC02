//Khi nào file html được load thì thực hiện gì đó
$(document).ready(function() {

	//Đăng ký sự kiện click : $("tên_thẻ || tên_class || id").click()
	//class => .
	//id => #
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function() {
		//Lấy giá trị của thuộc tính (attr) bên thẻ có class là .btn-xoa
		//$(this) : đại diện cho function đang đang thực thi
		var id = $(this).attr('id-user')
		var This = $(this)
		//RestTemplate, HttpClient
		$.ajax({
			method: "GET",
			url: `http://localhost:8080/crm_project_02/api/user/delete?id=${id}`, //string literals
			//data: { name: "John", location: "Boston" } //Tham số data chỉ giành cho phương thức POST
		})
			.done(function(result) {
				//result đại diện cho kết quả từ link url trả về
				//Lấy giá trị kiểu Object trong JS thì tênbiên.key
				if (result.data == true) {
					//.parent => đi ra một thẻ cha
					//.closest => đi ra thằng cha được chỉ định
					//.remove => Xóa thẻ
					//This.parent().parent().remove()
					This.closest('tr').remove()
				}
				console.log(result.data)
			});

	})

	 $(document).ready(function() {
        // Bắt sự kiện khi nút "Sửa" được nhấn
        $(".btn-sua").click(function() {
            var id = $(this).attr("id-user");

            // Thực hiện AJAX request để lấy thông tin người dùng cần sửa
            $.ajax({
                method: "GET",
                url: `http://localhost:8080/crm_project_02/api/user/update?id=${id}`,
                // Có thể thêm tham số data nếu cần truyền dữ liệu lên server
            })
            .done(function(response) {
                // Xử lý dữ liệu trả về từ server (response) để điều hướng đến trang cập nhật
                if (response.statusCode === 200) {
                    var user = response.data;
                    // Điều hướng đến trang cập nhật người dùng với thông tin user
                    window.location.href = `user-table.jsp?id=${user.id}&firstName=${user.firstName}&lastName=${user.lastName}&email=${user.email}&password=${user.password}&phone=${user.phone}&role=${user.roleId}`;
                } else {
                    // Xử lý trường hợp có lỗi từ server
                    console.log("Lỗi khi lấy thông tin người dùng");
                }
            })
            .fail(function() {
                // Xử lý trường hợp thất bại khi gửi request
                console.log("Lỗi khi gửi request");
            });
        });
    });



})