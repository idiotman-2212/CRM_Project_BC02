//Khi nào file html được load thì thực hiện gì đó
$(document).ready(function(){
	
	//Đăng ký sự kiện click : $("tên_thẻ || tên_class || id").click()
	//class => .
	//id => #
	//Đăng ký sự kiện click cho toàn bộ class có tên là btn-xoa
	$(".btn-xoa").click(function(){
		//Lấy giá trị của thuộc tính (attr) bên thẻ có class là .btn-xoa
		//$(this) : đại diện cho function đang đang thực thi
		var id = $(this).attr('id-role')
		var This = $(this)
		//RestTemplate, HttpClient
		$.ajax({
		  method: "GET",
		  url: `http://localhost:8080/crm_project_02/api/role-table/delete?id=${id}`, //string literals
		  //data: { name: "John", location: "Boston" } //Tham số data chỉ giành cho phương thức POST
		})
		.done(function( result ) {
			//result đại diện cho kết quả từ link url trả về
			//Lấy giá trị kiểu Object trong JS thì tênbiên.key
			if(result.data == true){
				//.parent => đi ra một thẻ cha
				//.closest => đi ra thằng cha được chỉ định
				//.remove => Xóa thẻ
				//This.parent().parent().remove()
				This.closest('tr').remove()
			}
			console.log(result.data)
		});
		
	})
	
})