<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>

</#assign>
<#assign js>
<script>
    $(".btn-submit").click(function () {
        $.ajax({
            type: "POST",
            url: "${ctx!}/admin/user/edit",
            data: $(".form-edit").serialize(),
            dataType: "JSON",
            success: function(res){
                layer.msg(res.message, {time: 2000
                }, function(){
                    location.reload();
                });
            }
        });
    });
</script>
</#assign>
<@layout title="笔记查看" active="note">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        用户笔记
        <small>查看用户笔记信息</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 笔记管理</a></li>
        <li class="active"><i class="fa fa-edit"></i> 笔记内容</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-10">
            <!-- Default box -->
            <div class="box  box-primary">
                <form class="form-horizontal form-edit" method="post" action="${ctx!}/admin/user/edit">
                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_user_description" name="cn_user_description" class="form-control" rows="20">${note.cn_note_content}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                    </div>
                </form>
            </div>
            <!-- /.box -->
        </div>
    </div>
</section>
<!-- /.content -->
</@layout>
