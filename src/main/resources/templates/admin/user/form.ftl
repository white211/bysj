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
            success: function (res) {
                layer.msg(res.message, {
                    time: 2000
                }, function () {
//                    location.reload();
                    window.location.href = "${ctx!}/admin/user/index";
                });
            }
        });
    });
</script>
</#assign>
<@layout title="用户编辑" active="user">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        用户编辑
        <small>编辑用户详细信息</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-cog"></i> 系统</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 用户管理</a></li>
        <li class="active"><i class="fa fa-edit"></i> 用户编辑</li>
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
                        <input type="hidden" id="cn_user_id" name="cn_user_id" value="${user.cn_user_id}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">账户名：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_name" name="cn_user_name" class="form-control" type="text"
                                       value="${user.cn_user_name}" required="required" <#if user?exists> readonly="readonly"</#if>>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">昵称：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_nickname" name="cn_user_nickname" class="form-control" type="text"
                                       value="${user.cn_user_nickname}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">头像URL：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_avatar" name="cn_user_avatar" class="form-control" type="url"
                                       value="${user.cn_user_avatar}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别：</label>
                            <div class="col-sm-10">
                                <select name="cn_user_sex" class="form-control">
                                    <option value="0" <#if user.cn_user_sex == 0>selected="selected"</#if>>女</option>
                                    <option value="1" <#if user.cn_user_sex == 1>selected="selected"</#if>>男</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">出生日期：</label>
                            <div class="col-sm-10">
                                <#if user.cn_user_birthday == null>
                                    <input id="cn_user_birthday" name="cn_user_birthday"
                                           class="laydate-icon form-control layer-date"
                                           value="${user.cn_user_birthday}">
                                <#else>
                                    <input id="cn_user_birthday" name="cn_user_birthday" readonly="readonly"
                                           class="laydate-icon form-control layer-date"
                                           value="${user.cn_user_birthday?date}">
                                </#if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">电话：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_telephone" name="cn_user_telephone" class="form-control"
                                       value="${user.cn_user_telephone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">E-mail：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_email" name="cn_user_email" class="form-control"
                                       value="${user.cn_user_email}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">地址：</label>
                            <div class="col-sm-10">
                                <input id="cn_user_address" name="cn_user_address" class="form-control"
                                       value="${user.cn_user_address}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">状态：</label>
                            <div class="col-sm-10">
                                <select name="cn_user_locked" class="form-control">
                                    <option value="0" <#if user.cn_user_locked == 0>selected="selected"</#if>>未锁定
                                    </option>
                                    <option value="1" <#if user.cn_user_locked == 1>selected="selected"</#if>>锁定
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">描述：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_user_description" name="cn_user_description" class="form-control"
                                          rows="6">${user.cn_user_description}</textarea>
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
