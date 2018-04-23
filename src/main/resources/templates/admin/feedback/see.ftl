<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>

</#assign>
<#assign js>
<script>
    $(".btn-submit").click(function () {
        $.ajax({
            type: "POST",
            url: "${ctx!}/admin/feedback/edit",
            data: $(".form-edit").serialize(),
            dataType: "JSON",
            success: function (res) {
                layer.msg(res.message, {
                    time: 2000
                }, function () {
//                    location.reload();
                    window.location.href = "${ctx!}/admin/feedback/index";
                });
            }
        });
    });
</script>
</#assign>
<@layout title="查看反馈" active="feedback">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        反馈信息
        <small>查看反馈详细信息</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 用户反馈管理</a></li>
        <li class="active"><i class="fa fa-edit"></i> 信息查看</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-10">
            <!-- Default box -->
            <div class="box  box-primary">
                <form class="form-horizontal form-edit" method="post" action="${ctx!}/admin/feedback/return">
                    <div class="box-body">
                        <input type="hidden" id="cn_user_id" name="cn_user_id" value="${feedback.cn_feedback_id}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">反馈者：</label>
                            <div class="col-sm-10">
                                <input id="cn_feedback_email" name="cn_feedback_email" class="form-control" type="text"
                                       value="${feedback.cn_user_email}" <#if feedback?exists>
                                       readonly="readonly"</#if>>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">反馈类型：</label>
                            <div class="col-sm-10">
                                <input id="cn_feedback_type" name="cn_feedback_type" class="form-control" type="text"
                                       value="${feedback.cn_feedback_type}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">反馈时间：</label>
                            <div class="col-sm-10">
                                <input id="cn_feedback_createTime" name="cn_feedback_createTime" class="form-control"
                                       type="url" value="${feedback.cn_feedback_createTime}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">反馈内容：</label>
                            <div class="col-sm-10">
                                <textarea id="cn_feedback_content" name="cn_feedback_content"
                                          class="form-control"
                                          rows="6">${feedback.cn_feedback_content}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">已回复：</label>
                            <div class="col-sm-10">
                                <#if feedback.cn_feedback_isReturn == 0>
                                    <input id="cn_feedback_isReturn" name="cn_feedback_isReturn"
                                           class="form-control"
                                           type="text" value="是">
                                <#elseif feedback.cn_feedback_isReturn == 1>
                                    <input id="cn_feedback_isReturn" name="cn_feedback_isReturn"
                                           class="form-control"
                                           type="text" value="否">
                                </#if>
                            </div>

                        </div>

                        <#if feedback.cn_feedback_isReturn == 0>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">回复人：</label>
                                <div class="col-sm-10">
                                    <input id="cn_userReturn_name " name="cn_userReturn_name" class="form-control"
                                           value="${feedback.cn_userReturn_name}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">回复内容：</label>
                                <div class="col-sm-10">
                                    <textarea id="cn_feedback_returnContent" name="cn_feedback_returnContent"
                                              class="form-control"
                                              rows="6">${feedback.cn_feedback_returnContent}</textarea>
                                </div>
                            </div>
                        </#if>


                    </div>
                    <div class="box-footer">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                    <#--<button type="button" class="btn btn-info pull-right btn-submit">提交</button>-->
                    </div>
                </form>
            </div>
            <!-- /.box -->
        </div>
    </div>
</section>
<!-- /.content -->
</@layout>
