<template>
    <div>
        <el-table
                :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%">
            <el-table-column
                    label="类别编号"
                    prop="t_id">
            </el-table-column>
            <el-table-column
                    label="类别名称"
                    prop="tname">
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="findById(scope.row)">修改</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
            <el-button @click="add()">添加</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "typeList",
        data() {
            return {
                tableData: [],
                search: ''
            }
        },
        created(){
            this.$http.get("http://localhost:8082/mtype/findAll").then(req=>{
                this.tableData=req.data;
            })
        },
        methods: {
            reload(){
                this.$http.get("http://localhost:8082/mtype/findAll").then(req=>{
                    this.tableData=req.data;
                })
            },
            add(){
                this.$prompt('请输入类别名',{
                    confirmButtonText:'添加',
                    cancelButtonText:'取消',
                }).then(({ value })=>{
                    this.$http.get(`http://localhost:8082/mtype/findByName?tname=${value}`).then(req=>{
                        if(req.data){
                            this.$message({
                                type: 'info',
                                message: '类型重复！'
                            });
                        }else{
                            this.$http.get(`http://localhost:8082/mtype/add?tname=${value}`).then(req=>{
                                this.$message({
                                    type: 'info',
                                    message: req.data
                                });
                                this.reload();
                            })
                        }
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            findById(row) {
                this.$prompt('请输入类别名', '提示', {
                    confirmButtonText: '确认修改',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    this.$http.get(`http://localhost:8082/mtype/findByName?tname=${value}`).then(req=>{
                        if(req.data){
                            this.$message({
                                type: 'info',
                                message: '类型重复！'
                            });
                        }else{
                            this.$http.get(`http://localhost:8082/mtype/update?t_id=${row.t_id}&tname=${value}`).then(req=>{
                                this.$message({
                                    type: 'success',
                                    message: req.data
                                });
                                this.reload();
                            })
                        }
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            }
        },
    }
</script>

<style scoped>

</style>