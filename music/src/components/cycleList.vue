<template>
    <div>
        <div style="margin-top: 20px">
            <el-button @click="add()">添加</el-button>
        </div>
        <el-table
                :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%">
            <el-table-column
                    label="轮播图编号"
                    prop="cpid">
            </el-table-column>

            <el-table-column
                    label="轮播图片"
                    prop="cpurl">
                <template slot-scope="scope">
                    <img class="cpImg" :src="scope.row.cpurl" />
                </template>
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="update(scope.row)">修改</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="del(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="轮播图" :visible.sync="dialogFormVisible">
            <el-form>
                <el-form-item label="">
                    <el-upload
                            class="avatar-uploader"
                            :action="uploadUrl"
                            :show-file-list="false"
                            multiple
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                    >
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="initdate">取 消</el-button>
                <el-button type="primary" @click="change()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "cycleList",
        data(){
            return{
                tableData:[],
                search: '',
                cpurl:'',
                cpid:'',

                dialogFormVisible: false,
                formLabelWidth: '120px',

                uploadUrl:'http://localhost:8082/muser/upload',
                imageUrl: '',
                file:''
            }
        },
        created(){
            this.$http.get("http://localhost:8082/cyclephoto/findAll").then(req=>{
                this.tableData=req.data;
            })
        },
        methods:{
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                this.file=file;
                this.cpurl="img/"+file.name;
                return true;
            },
            upload(){
                this.uploadUrl = 'http://localhost:8082/muser/upload'
                console.log("开始上传，上传的文件为："+this.file)
                let formData = new FormData();
                formData.append("multipartFiles", this.file);
                this.$http({
                    method: 'post',
                    url: 'http://localhost:8082/muser/upload',
                    data: formData,
                    headers: {'Content-Type': 'multipart/form-data' }
                }).then((res) => {
                    console.log("文件上传返回："+res)
                }).catch(error => {
                    console.log("文件上传异常:"+error)
                });
            },
            initdate(){
                this.dialogFormVisible=false;
                this.imageUrl='';
                this.file='';
            },
            reload(){
                this.$http.get("http://localhost:8082/cyclephoto/findAll").then(req=>{
                    this.tableData=req.data;
                })
            },
            del(row){
                this.$confirm('是否永久删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.get(`http://localhost:8082/cyclephoto/delete?cpid=${row.cpid}`).then(req=>{
                        this.$message({
                            type: 'success',
                            message: req.data
                        });
                        this.reload();
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
                console.log(row);
            },

            update(row){
                this.dialogFormVisible = true;
                this.cpid=row.cpid;
            },
            add(){
                this.dialogFormVisible = true;
                this.cpid='';
            },
            change(){
                if(this.cpurl==''){
                    alert("不能为空");
                }else{
                    this.$http.get(`http://localhost:8082/cyclephoto/update?cpid=${this.cpid}&cpurl=${this.cpurl}`).then(req=>{
                        console.log(req.data);
                        this.upload();
                        this.initdate();
                        this.reload();
                    })
                }
            }
        }
    }
</script>

<style scoped>
    .cpImg{
        width: 324px;
        height: 126px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>