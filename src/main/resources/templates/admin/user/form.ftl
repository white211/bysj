<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>
<link rel="stylesheet" type="text/css" href="/assets/plugins/cityPicker/css/index.css">
</#assign>
<#assign js>
<#--<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>-->
<script src="/assets/plugins/cityPicker/js/citydata.min.js"></script>
<script src="/assets/plugins/cityPicker/js/cityPicker-2.0.2.js"></script>
<script src="/assets/plugins/laydate/laydate.js"></script>
<script>
    laydate.render({
        elem: '#cn_user_birthday' //指定元素
        , type: 'date'
    });

    $(".btn-submit").click(function () {
        var username = $("#cn_user_name").val();
        if (username == null || username == "") {
            layer.msg("用户名不能为空", {time: 2000}, function () {
                return;
            });
        } else {
            $.ajax({
                type: "POST",
                url: "${ctx!}/admin/user/edit",
                data: $(".form-edit").serialize(),
                dataType: "JSON",
                success: function (res) {
                    layer.msg(res.message, {
                        time: 2000
                    }, function () {
                        window.location.href = "${ctx!}/admin/user/index";
                    });
                }
            });
        }
    });

    var selector2 = $('#city-picker-selector').cityPicker({
        dataJson: cityData,
        renderMode: false,
        search: false,
        autoSelected: true,
        code: 'cityCode',
        level: 3,
        onChoiceEnd: function () {
            var address = this.values[0].name + this.values[1].name + this.values[2].name;
            $("#cn_user_address").val(address);
            console.log(address);
        }
    });

    // 设置城市
    //    selector2.setCityVal('广东省, 广州市, 天河区');

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
                                       value="${user.cn_user_name}"
                                    <#if user?exists>
                                       readonly="readonly"
                                    </#if>
                                >
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
                                    <input id="cn_user_birthday" name="cn_user_birthday"
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
                                <#if user.cn_user_address == null>
                                    <input id="cn_user_address" name="cn_user_address" class="form-control"
                                           value="${user.cn_user_address}" type="hidden">
                                    <div class="city-picker-selector" id="city-picker-selector">
                                        <div class="selector-item storey province">
                                            <a href="javascript:;" class="selector-name reveal">北京市</a>
                                            <input type="hidden" name="userProvinceId" class="input-price val-error"
                                                   value="110000" data-required="userProvinceId">
                                            <div class="selector-list listing hide">
                                                <ul>
                                                    <li>北京市</li>
                                                    <li>天津市</li>
                                                    <li>河北省</li>
                                                    <li>山西省</li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="selector-item storey city">
                                            <a href="javascript:;" class="selector-name reveal">北京市</a>
                                            <input type="hidden" name="userCityId" class="input-price val-error"
                                                   value="110100" data-required="userCityId">
                                            <div class="selector-list listing hide">
                                                <ul>
                                                    <li>北京市</li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="selector-item storey district">
                                            <a href="javascript:;" class="selector-name reveal">海淀区</a>
                                            <input type="hidden" name="userDistrictId" class="input-price val-error"
                                                   value="110108" data-required="userDistrictId">
                                            <div class="selector-list listing hide">
                                                <ul>
                                                    <li>东城区</li>
                                                    <li>西城区</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                <#else>
                                    <input id="cn_user_address" name="cn_user_address" class="form-control"
                                           value="${user.cn_user_address}" type="text" readonly="readonly"
                                    >
                                </#if>
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
