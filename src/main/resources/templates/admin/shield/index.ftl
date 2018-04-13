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
                url: "${ctx!}/admin/shield/delete/" + id,
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
<@layout title="屏蔽词管理" active="shield">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        屏蔽词列表
        <small>一切从这里开始</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i> 内容</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 屏蔽词管理</a></li>
        <li class="active"><i class="fa fa-table"></i> 屏蔽词列表</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <!-- Default box -->
    <div class="box box-primary">
        <div class="box-header">
            <@shiro.hasPermission name="system:shield:add">
                <a class="btn btn-sm btn-success" href="${ctx!}/admin/shield/add">新增</a>
            </@shiro.hasPermission>
        </div>
        <div class="box-body">
            <table class="table table-striped">
                <tr>
                    <th>序号</th>
                    <th>内容</th>
                    <th>创建者</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                <#list pageInfo.content as shieldInfo>
                    <tr>
                        <td>${(pageInfo.number)*10+(shieldInfo_index+1)}</td>
                        <td>${shieldInfo.cn_shield_content}</td>
                        <td>${shieldInfo.cn_user_name}</td>
                        <td>${shieldInfo.cn_shield_createTime?date}</td>
                        <td>

                            <@shiro.hasPermission name="system:shield:edit">
                                <a class="btn btn-sm btn-primary"
                                   href="${ctx!}/admin/shield/edit/${shieldInfo.cn_shield_id}">编辑</a>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="system:shield:deleteBatch">
                                <button class="btn btn-sm btn-danger" onclick="del(${shieldInfo.cn_shield_id})">删除
                                </button>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <@macro.page pageInfo=pageInfo url="${ctx!}/admin/shield/index?" />
        </div>
    </div>
    <!-- /.box -->

</section>
<!-- /.content -->
</@layout>