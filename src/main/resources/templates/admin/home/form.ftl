<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>

</#assign>
<#assign js>
<script>
    $(".btn-submit").click(function () {
        $.ajax({
            type: "POST",
            url: "${ctx!}/admin/home/edit",
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
<@layout title="首页编辑" active="home">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        首页编辑
        <small>首页详细信息</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 首页管理</a></li>
        <li class="active"><i class="fa fa-edit"></i> 首页编辑</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-10">
            <!-- Default box -->
            <div class="box  box-primary">
                <form class="form-horizontal form-edit" method="post" action="${ctx!}/admin/home/edit">
                    <div class="box-body">
                        <input type="hidden" id="cn_home_id" name="cn_home_id" value="${home.cn_home_id}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">一大图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_first_bpic" name="cn_first_bpic" class="form-control" type="file" value="${home.cn_first_bpic}" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">一小图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_first_spic" name="cn_first_spic" class="form-control" type="file" value="${home.cn_first_spic}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">一标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_first_title" name="cn_first_title" class="form-control" type="url" value="${home.cn_first_title}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">一描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_first_desc" name="cn_first_desc" class="form-control" rows="6">${home.cn_first_desc}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">二大图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_bpic" name="cn_second_bpic" class="form-control" type="file" value="${home.cn_second_bpic}" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">二小图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_spic" name="cn_second_spic" class="form-control" type="file" value="${home.cn_second_spic}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">二标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_title" name="cn_second_title" class="form-control" type="url" value="${home.cn_second_title}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">二描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_second_desc" name="cn_second_desc" class="form-control" rows="6">${home.cn_second_desc}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">三大图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_bpic" name="cn_second_bpic" class="form-control" type="file" value="${home.cn_second_bpic}" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">三小图url：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_spic" name="cn_second_spic" class="form-control" type="file" value="${home.cn_second_spic}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">三标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_title" name="cn_second_title" class="form-control" type="url" value="${home.cn_second_title}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">三描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_second_desc" name="cn_second_desc" class="form-control" rows="6">${home.cn_second_desc}</textarea>
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
