<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>

</#assign>
<#assign js>
<script>
    $(".btn-submit").click(function () {
        $.ajax({
            type: "POST",
            url: "${ctx!}/admin/shield/edit",
            data: $(".form-edit").serialize(),
            dataType: "JSON",
            success: function (res) {
                layer.msg(res.message, {
                    time: 2000
                }, function () {
//                    location.reload();
                    window.location.href = "${ctx!}/admin/shield/index";
                });
            }
        });
    });
</script>
</#assign>
<@layout title="屏蔽词编辑" active="shield">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        屏蔽词管理
        <small>添加屏蔽词</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 屏蔽词管理</a></li>
        <li class="active"><i class="fa fa-edit"></i> 屏蔽词编辑</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-10">
            <!-- Default box -->
            <div class="box  box-primary">
                <form class="form-horizontal form-edit" method="post" action="${ctx!}/admin/shield/edit">
                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_shield_content" name="cn_shield_content" class="form-control"
                                          rows="6">${shield.cn_shield_content}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                        <button type="button" class="btn btn-info pull-right btn-submit">提交</button>
                    </div>
                </form>
            </div>
            <!-- /.box -->
        </div>
    </div>
</section>
<!-- /.content -->
</@layout>
