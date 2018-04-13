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
                url: "${ctx!}/admin/note/delete/" + id,
                success: function (res) {
                    layer.msg(res.message, {time: 2000}, function () {
                        location.reload();
                    });
                }
            });
        });
    }

    function checkText() {
        var text = $("#text").val();
        if (text === '' || text === null) {
            layer.msg("请输入关键词",{time:2000},function () {
            });
            return false;
        }else{
            return true;
        }
    }

</script>
</#assign>
<@layout title="笔记管理" active="note">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        笔记列表
        <small>一切从这里开始</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-database"></i>内容管理</a></li>
        <li><a href="#"><i class="fa fa-list-ul"></i> 笔记管理</a></li>
        <li class="active"><i class="fa fa-table"></i> 笔记列表</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <!-- Default box -->
    <div class="box box-primary">
        <div class="box-header">
            <@shiro.hasPermission name="system:note:find">
                <form id="searchForm" class="form-horizontal" action="${ctx!}/admin/note/find" method="get" onsubmit="return checkText()">
                    <div class="form-group">
                        <div class="col-sm-8 col-xs-12 ">
                            <input type="text" name="text" class="form-control" id="text" placeholder="请输入查找内容">
                        </div>
                        <button type="submit" class="btn btn-success col-sm-1 col-xs-3">查找</button>
                    </div>
                </form>
            </@shiro.hasPermission>
        </div>
        <div class="box-body">
            <table class="table table-striped">
                <tr>
                    <th>序号</th>
                    <th>笔记标题</th>
                    <th>笔记本</th>
                    <th>标签</th>
                    <th>作者</th>
                    <th>类型</th>
                    <th>阅读量</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                <#list pageInfo.content as noteInfo>
                    <tr>
                        <td>${(pageInfo.number)*10+(noteInfo_index + 1)}</td>
                        <td>${noteInfo.cn_note_title}</td>
                        <td>${noteInfo.cn_notebook_name}</td>
                        <td>${noteInfo.cn_notelabel_name}</td>
                        <td>${noteInfo.cn_user_email}</td>
                        <td>${noteInfo.cn_noteType_name}</td>
                        <td>${noteInfo.cn_note_read}次</td>
                        <td>${noteInfo.cn_note_creatTime?date}</td>
                        <td>
                            <@shiro.hasPermission name="system:note:see">
                                <a class="btn btn-sm btn-primary" href="${ctx!}/admin/note/see/${noteInfo.cn_note_id}">查看笔记</a>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="system:note:deleteBatch">
                                <button class="btn btn-sm btn-danger" onclick="del(${noteInfo.cn_note_id})">删除</button>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <@macro.page pageInfo=pageInfo url="${ctx!}/admin/note/index?" />
        </div>
    </div>
    <!-- /.box -->

</section>
<!-- /.content -->
</@layout>