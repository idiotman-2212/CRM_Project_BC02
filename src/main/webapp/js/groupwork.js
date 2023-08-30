$(document).ready(function(){
    $(".btn-xoa").click(function(){
        var id = $(this).attr('id-groupwork');
        var This = $(this);

        $.ajax({
            method: "GET",
            url: `http://localhost:8080/crm_project_02/api/groupwork/delete?id=${id}`,
        })
        .done(function(result){
            if(result.data == true){
                This.closest('tr').remove();
            }
            console.log(result.data);
        });
    });
});
