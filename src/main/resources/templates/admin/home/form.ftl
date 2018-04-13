<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>

<#assign css>
<style type="text/css">
    .url {

    }

    .url:hover {
        cursor: pointer;
    }

</style>

</#assign>


<#assign js>
<script>

    $(".btn-submit").click(function () {
        $.ajax({
            type: "POST",
            url: "${ctx!}/admin/home/edit",
            data: $(".form-edit").serialize(),
            dataType: "JSON",
            success: function (res) {
                layer.msg(res.message, {
                    time: 2000
                }, function () {
                    location.reload();
                });
            }
        });
    });

    function upload(event) {
        var type = event.target.dataset.flag;
        var formData = new FormData();
        var text;
        var file;
        var pic;
        if (type == 11) {
            file = $("#cn_first_bpic");
            text = $('.url').eq(0);
            pic = $('.pic').eq(0);
        } else if (type == 12) {
            file = $("#cn_first_spic");
            text = $('.url').eq(1);
            pic = $('.pic').eq(1);
        } else if (type == 21) {
            file = $("#cn_second_bpic");
            text = $('.url').eq(2);
            pic = $('.pic').eq(2);
        } else if (type == 22) {
            file = $("#cn_second_spic");
            text = $('.url').eq(3);
            pic = $('.pic').eq(3);
        } else if (type == 31) {
            file = $("#cn_third_bpic");
            text = $('.url').eq(4);
            pic = $('.pic').eq(4);
        } else if (type == 32) {
            file = $("#cn_third_spic");
            text = $('.url').eq(5);
            pic = $('.pic').eq(5);
        }
        console.log(file);
        file = file.prop('files')[0];
        console.log(file);
        formData.append('image', file);
        $.ajax({
            type: "POST",
            dataType: "json",
            data: formData,
            processData: false,
            contentType: false,
            url: "${ctx!}/note/uploadFile.do",
            success: function (res) {
                if (res.status == 0) {
                    var url = res.data;
                    text.attr("src", url);
                    pic.val(url);
                }
            }
        });

    };

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
                <form class="form-horizontal form-edit" method="post" action="${ctx!}/admin/home/edit"
                      onsubmit="return false">
                    <div class="box-body">

                        <input type="hidden" id="cn_home_id" name="cn_home_id" value="${home.cn_home_id}">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">第一张标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_first_title" name="cn_first_title" class="form-control" type="url"
                                       value="${home.cn_first_title}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">第一张描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_first_desc" name="cn_first_desc" class="form-control"
                                          rows="6">${home.cn_first_desc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上传图片：</label>
                            <div class="col-sm-10" style="display: none">
                                <input id="cn_first_bpic" class="form-control" type="file"
                                         data-flag="11" onchange="upload(event)">
                            </div>
                            <label for="cn_first_bpic"
                                   style="width: 200px;height: 100px;display: inline-block;margin-left: 15px">
                                <input type="hidden" value="${home.cn_first_bpic}" name="cn_first_bpic" class="pic">
                                <div style="width: 200px;height: 100px;" title="点击更换图片">
                                    <img src="${home.cn_first_bpic}" class="url"
                                         style="height: 100px;width: 200px;">
                                </div>
                            </label>

                            <div class="col-sm-10" style="display: none">
                                <input class="form-control" type="file"
                                        id="cn_first_spic" value="${home.cn_first_spic}" data-flag="12" onchange="upload(event)">
                            </div>
                            <label for="cn_first_spic"
                                   style="width: 100px;height: 100px;display: inline-block;margin-left: 10px;">
                                <input type="hidden" value="${home.cn_first_spic}" name="cn_first_spic" class="pic">
                                <div style="width: 100px;height: 100px;" title="点击更换图片">
                                    <img src="${home.cn_first_spic}" class="url"
                                         style="width: 100px;height: 100px;">
                                </div>
                            </label>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">第一张标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_second_title" name="cn_second_title" class="form-control" type="url"
                                       value="${home.cn_second_title}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">第二张描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_second_desc" name="cn_second_desc" class="form-control"
                                          rows="6">${home.cn_second_desc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <#--第一张图片-->
                            <label class="col-sm-2 control-label">上传图片：</label>
                            <div class="col-sm-10" style="display: none">
                                <input id="cn_second_bpic" class="form-control" type="file"
                                       data-flag="21" onchange="upload(event)">
                            </div>
                            <label for="cn_second_bpic"
                                   style="width: 200px;height: 100px;display: inline-block;margin-left: 15px">
                                <input type="hidden" value="${home.cn_second_bpic}"  name="cn_second_bpic" class="pic">
                                <div style="width: 200px;height: 100px;" title="点击更换图片">
                                    <img src="${home.cn_second_bpic}" class="url"
                                         style="height: 100px;width: 200px;">
                                </div>
                            </label>
                            <#--第二张-->
                            <div class="col-sm-10" style="display: none">

                                <input class="form-control" type="file"
                                        id="cn_second_spic" data-flag="22"
                                        onchange="upload(event)">
                            </div>

                            <label for="cn_second_spic"
                                   style="width: 100px;height: 100px;display: inline-block;margin-left: 10px;">
                                <input type="hidden" value="${home.cn_second_spic}" name="cn_second_spic" class="pic">

                                <div style="width: 100px;height: 100px;" title="点击更换图片">
                                    <img src="${home.cn_second_spic}" class="url"
                                         style="width: 100px;height: 100px;">
                                </div>
                            </label>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">第三张标题：</label>
                            <div class="col-sm-10">
                                <input id="cn_third_title" name="cn_third_title" class="form-control" type="url"
                                       value="${home.cn_third_title}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">第三张描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_third_desc" name="cn_third_desc" class="form-control"
                                          rows="6">${home.cn_third_desc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上传图片：</label>
                            <#--第一张图片-->
                            <div class="col-sm-10" style="display: none">
                                <input id="cn_third_bpic" class="form-control" type="file"
                                       value="${home.cn_third_bpic}" data-flag="31" onchange="upload(event)">
                            </div>
                            <label for="cn_third_bpic"
                                   style="width: 200px;height: 100px;display: inline-block;margin-left: 15px">
                                <input type="hidden" value="${home.cn_third_bpic}"  name="cn_third_bpic" class="pic">
                                <div style="width: 200px;height: 100px;" title="点击更换图片">
                                    <img src="${home.cn_third_bpic}" class="url img-rounded"
                                         style="height: 100px;width: 200px;">
                                </div>
                            </label>
                            <#--第二张图片-->
                            <div class="col-sm-10" style="display: none">
                                <input class="form-control" type="file"
                                          id="cn_third_spic" data-flag="32" onchange="upload(event)">
                            </div>
                            <label for="cn_third_spic"
                                   style="width: 100px;height: 100px;display: inline-block;margin-left: 10px;">
                                <div style="width: 100px;height: 100px;" title="点击更换图片">
                                    <input type="hidden" value="${home.cn_third_spic}"  name="cn_third_spic" class="pic">
                                    <img src="${home.cn_third_spic}" class="url"
                                         style="width: 100px;height: 100px;">
                                </div>
                            </label>
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
