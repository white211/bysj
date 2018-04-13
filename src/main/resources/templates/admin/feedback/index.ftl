<#include "/admin/layout/layout.ftl">
<#import "/admin/layout/macro.ftl" as macro>
<#assign css>
<style>
</style>
</#assign>
<#assign js>
<script>
    function del(id) {
        layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx!}/admin/feedback/delete/" + id,
                success: function (res) {
                    layer.msg(res.message, {time: 2000}, function () {
                        location.reload();
                    });
                }
            });
        });
    }
</script>
</#assign>
<@layout title="反馈信息管理" active="feedback">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        用户反馈列表
        <small>一切从这里开始</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容管理</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 用户反馈管理</a></li>
        <li class="active"><i class="fa fa-table"></i> 反馈信息列表</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <!-- Default box -->
    <div class="box box-primary">

        <div class="box-body">
            <table class="table table-striped">
                <tr>
                    <th>序号</th>
                    <th>反馈者</th>
                    <th>反馈类型</th>
                    <th>反馈时间</th>
                    <th>回复状态</th>
                    <th>操作</th>
                </tr>
                <#list pageInfo.content as feedbackInfo>
                    <tr>
                        <td>${feedbackInfo_index+1}</td>
                        <td>${feedbackInfo.cn_user_email}</td>
                        <td>${feedbackInfo.cn_feedback_type}</td>
                        <td>${feedbackInfo.cn_feedback_createTime?date}</td>
                        <td>
                            <#if feedbackInfo.cn_feedback_isReturn == 0>
                                <span class="label label-info">已回复</span>
                            <#elseif feedbackInfo.cn_feedback_isReturn == 1>
                                <span class="label label-danger">未回复</span>
                            </#if>
                        </td>
                        <td>
                            <@shiro.hasPermission name="system:feedback:see">
                                <a class="btn btn-sm btn-primary"
                                   href="${ctx!}/admin/feedback/see/${feedbackInfo.cn_feedback_id}">查看</a>
                            </@shiro.hasPermission>
                            <#if feedbackInfo.cn_feedback_isReturn == 1>
                                <@shiro.hasPermission name="system:feedback:return">
                                    <a class="btn btn-sm btn-warning"
                                       href="${ctx!}/admin/feedback/return/${feedbackInfo.cn_feedback_id}">回复</a>
                                </@shiro.hasPermission>
                            </#if>
                            <@shiro.hasPermission name="system:feedback:deleteBatch">
                                <button class="btn btn-sm btn-danger" onclick="del(${feedbackInfo.cn_feedback_id})">删除
                                </button>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <@macro.page pageInfo=pageInfo url="${ctx!}/admin/feedback/index?" />
        </div>
    </div>
    <!-- /.box -->

</section>
<!-- /.content -->
</@layout>