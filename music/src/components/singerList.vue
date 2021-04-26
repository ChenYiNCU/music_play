<template>
    <div>
        <el-button
                size="mini"
                type="success"
                @click="openAdd()" >添加</el-button>
        <el-table
                :data="tableData"
                style="width: 100%"><!--            data	显示的数据-->
            <el-table-column
                    label="编号"
                    prop="u_id">
            </el-table-column>
            <el-table-column
                    label="头像"
                    prop="uphoto">
                <template slot-scope="scope">
                    <el-image
                            style="width: 100px; height: 100px;"
                            :src="scope.row.uphoto"
                            :fit="fit"></el-image>
                </template>
            </el-table-column>
            <el-table-column
                    label="昵称"
                    prop="uname">
            </el-table-column>
            <el-table-column
                    label="密码"
                    prop="upwd">
            </el-table-column>
            <el-table-column
                    label="年龄"
                    prop="age">
            </el-table-column>
            <el-table-column
                    label="性别"
                    prop="sex">
            </el-table-column>
            <el-table-column
                    label="电话号码"
                    prop="tel">
            </el-table-column>
            <el-table-column
                    label="邮箱"
                    prop="email">
            </el-table-column>
            <!--        太长了不想显示，但或许可以编辑-->
            <!--        <el-table-column-->
            <!--                label="个人简介"-->
            <!--                prop="des">-->
            <!--        </el-table-column>-->
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            type="text"
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"
                            @keyup.enter.native="searchUser(scope)"
                    />
                    <!--<el-button
                            size="mini"
                            type="primary"
                            @click="searchUser(scope)" >搜索</el-button>-->
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="primary"
                            @click="findById(scope.row)" >修改</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--修改会员信息 对话框-->
        <el-dialog title="歌手信息" :visible.sync="dialogUpdateVisible" v-if="dialogUpdateVisible">
            <el-form :model="updateForm" status-icon  :rules="rules" ref="updateForm">
                <el-form-item label="网名" :label-width="formLabelWidth" prop="uname">
                    <el-input v-model="updateForm.uname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="头像" :label-width="formLabelWidth" prop="uphoto">
                    <el-upload
                            class="avatar-uploader"
                            :action="uploadUrl"
                            :show-file-list="false"
                            multiple
                            :on-success="handleAvatarSuccess1"
                            :before-upload="beforeAvatarUpload1"
                    >
                        <img v-if="imageUrl1" :src="imageUrl1" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>

                </el-form-item>
                <el-form-item label="手机号" :label-width="formLabelWidth" prop="tel">
                    <el-input v-model="updateForm.tel" autocomplete="off" @change="checkTel()"></el-input>
                </el-form-item>
                <el-form-item label="密码" :label-width="formLabelWidth" prop="upwd">
                    <el-input v-model="updateForm.upwd" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                    <el-input v-model="updateForm.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄" :label-width="formLabelWidth" prop="age">
                    <el-input v-model="updateForm.age" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
                    <el-select v-model="updateForm.sex" placeholder="">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="个人简介" :label-width="formLabelWidth" prop="des">
                    <el-input type="textarea" v-model="updateForm.des" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogUpdateVisible = false">取 消</el-button>
                <el-button type="primary" @click="update('updateForm')">确 定</el-button>
            </div>
        </el-dialog>

        <!--添加会员信息 对话框-->
        <el-dialog title="歌手信息" :visible.sync="dialogAddVisible" v-if="dialogAddVisible">
            <el-form :model="addForm" status-icon  :rules="rules" ref="addForm">
                <el-form-item label="网名" :label-width="formLabelWidth" prop="uname">
                    <el-input v-model="addForm.uname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="头像" :label-width="formLabelWidth" prop="uphoto">
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
                <el-form-item label="手机号" :label-width="formLabelWidth" prop="tel">
                    <el-input v-model="addForm.tel" autocomplete="off" @change="checkTel1()"></el-input>
                </el-form-item>
                <el-form-item label="密码" :label-width="formLabelWidth" prop="upwd">
                    <el-input v-model="addForm.upwd" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                    <el-input v-model="addForm.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄" :label-width="formLabelWidth" prop="age">
                    <el-input v-model="addForm.age" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
                    <el-select v-model="addForm.sex" placeholder="">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="个人简介" :label-width="formLabelWidth" prop="des">
                    <el-input type="textarea" v-model="addForm.des" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogUpdateVisible=false">取 消</el-button>
                <el-button type="primary" @click="add('addForm')">确 定</el-button>
            </div>
        </el-dialog>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="index"
                       :page-sizes="[5, 10, 20, 40]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible1">
        </el-pagination>

        <el-pagination @size-change="handleSizeChange1" @current-change="handleCurrentChange1" :current-page="index1"
                       :page-sizes="[5, 10, 20, 40]" :page-size="size1" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible2">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        inject:['reload'],
        name: "singerList",
        data() {
            var checkAge = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('年龄不能为空'));
                }
                setTimeout(() => {
                    if (value <1 || value >120) {
                        callback(new Error('请输入正确的年龄'));0
                    } else {
                        callback();
                    }
                }, 1000);
            };
            return {
                tableData: [],
                updateForm:{
                    u_id:'',
                    uname:'',
                    uphoto:'',
                    tel:'',
                    upwd:'',
                    email:'',
                    age:'',
                    sex:'',
                    des:''
                },
                addForm:{
                    uname:'',
                    uphoto:'',
                    tel:'',
                    upwd:'',
                    email:'',
                    age:'',
                    sex:'',
                    des:''
                },
                oldTel:'',
                flag:2,   //歌手flag
                search: '',
                index:1,
                size:5,
                index1:1,
                size1:5,
                count:'',
                pageVisible1: true,
                pageVisible2: false,
                dialogUpdateVisible: false,
                dialogAddVisible: false,
                formLabelWidth: '120px',

                uploadUrl:'http://localhost:8082/muser/upload',
                imageUrl: '',
                file:'',
                imageUrl1: '',
                file1:'',

                rules:{
                    uname:[
                        {required: true, message: '请输入网名', trigger: 'blur'},
                        {min:1, max:20, message: '网名至多20个字符', trigger: 'blur'}
                    ],
                    upwd:[
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    tel:[
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        {min:11, max:11, message: '请输入正确的手机号', trigger: 'blur'}
                    ],
                    email:[
                        {required: true, message: '请输入邮箱', trigger: 'blur'},
                        {   type: 'email',
                            message: '请输入正确的邮箱地址',
                            trigger: ['blur', 'change'],}
                    ],
                    age: [
                        {required: true, validator: checkAge, trigger: 'blur' }
                    ],
                    sex:[
                        {required: true, message: '请选择性别', trigger: 'change'}
                    ],
                }
            }
        },
        created(){
            this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                this.tableData=req.data.list;
                this.index=req.data.index;
                this.count=req.data.count;
            })
        },
        methods: {
            //修改的
            checkTel:function(){
                this.$http.get("http://localhost:8082/muser/findTel?tel="+this.updateForm.tel).then(req=>{
                    if(req.data!=null){  //说明该电话号码已经存在
                        // alert(req.data);
                        //alert("原本电话号码："+this.oldTel);
                        if(req.data !='a') {  //说明当前输入手机号用户存在
                            if (req.data != this.oldTel) {  //当前登陆用户的新手机号
                                this.$message({
                                    showClose: true,
                                    message: '该手机号已注册为会员，请输入新的手机号！',
                                    type: 'warning'
                                });
                            }
                        }
                    }
                })
            },
            //添加的
            checkTel1:function(){
                this.$http.get("http://localhost:8082/muser/findTel?tel="+this.addForm.tel).then(req=>{
                    if(req.data!=null){  //说明该电话号码已经存在
                        // alert(req.data);
                        if(req.data !='a'){  //说明用户存在
                            this.$message({
                                showClose: true,
                                message: '该手机号已注册为会员，请输入新的手机号！',
                                type: 'warning'
                            });
                        }
                    }
                })
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                this.file=file;
                this.addForm.uphoto="img/"+file.name;
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
            handleAvatarSuccess1(res, file) {
                this.imageUrl1 = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload1(file) {
                this.file1=file;
                this.updateForm.uphoto="img/"+file.name;
                return true;
            },
            upload1(){
                this.uploadUrl = 'http://localhost:8082/muser/upload'
                console.log("开始上传，上传的文件为："+this.file1)
                let formData = new FormData();
                formData.append("multipartFiles", this.file1);
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
            searchUser:function(scope){
                console.log(scope);
                //alert(this.search);
                if(this.search=='' || this.search==null){
                    this.pageVisible1=true;
                    this.pageVisible2=false;
                }else{
                    this.pageVisible2=true;
                    this.pageVisible1=false;
                }
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
            },
            findById:function (row) {
                console.log(row);
                this.updateForm.u_id=row.u_id;
                this.updateForm.uname=row.uname;
                this.updateForm.uphoto=row.uphoto;
                this.imageUrl1=row.uphoto;
                this.updateForm.tel=row.tel;
                this.oldTel=row.tel;
                this.updateForm.upwd=row.upwd;
                this.updateForm.email=row.email;
                this.updateForm.age=row.age;
                this.updateForm.sex=row.sex;
                this.updateForm.des=row.des;

                this.dialogUpdateVisible = true;
            },
            update:function(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //alert(this.updateForm.u_id);
                        //alert(this.form.des);
                        this.$http.get("http://localhost:8082/muser/update?u_id="+this.updateForm.u_id+"&tel="+this.updateForm.tel+"&upwd="+this.updateForm.upwd+"&uname="+this.updateForm.uname+"&email="+this.updateForm.email+"&age="+this.updateForm.age+"&sex="+this.updateForm.sex+"&des="+this.updateForm.des+"&uphoto="+this.updateForm.uphoto).then(req=>{
                            if(req.data!=""){
                                this.upload1();
                                /* alert(req.data);*/
                                this.$message({
                                    showClose: true,
                                    message: '歌手'+this.updateForm.u_id+'信息修改成功！',
                                    type: 'success'
                                });
                                this.dialogUpdateVisible= false;
                                this.reload();
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            add:function(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //alert(this.addForm.tel);
                        //alert(this.flag);
                        this.$http.get("http://localhost:8082/muser/add?tel="+this.addForm.tel+"&upwd="+this.addForm.upwd+"&uname="+this.addForm.uname+"&email="+this.addForm.email+"&age="+this.addForm.age+"&sex="+this.addForm.sex+"&des="+this.addForm.des+"&flag="+this.flag+"&uphoto="+this.addForm.uphoto).then(req=>{
                            if(req.data!=""){
                                this.upload();
                                /* alert(req.data);*/
                                this.$message({
                                    showClose: true,
                                    message: '歌手账号添加成功！',
                                    type: 'success'
                                });
                                this.dialogAddVisible=false;
                                this.addForm.tel='';
                                this.addForm.uname='';
                                this.addForm.upwd='';
                                this.addForm.email='';
                                this.addForm.age='';
                                this.addForm.sex='';
                                this.addForm.des='';
                                this.addForm.uphoto='';
                                this.reload();
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            openAdd:function(){
                this.dialogAddVisible=true;
                this.addForm.tel='';
                this.addForm.uname='';
                this.addForm.upwd='';
                this.addForm.email='';
                this.addForm.age='';
                this.addForm.sex='';
                this.addForm.des='';
            },
            handleDelete(index, row) {
                console.log(index, row);
                //alert(row.u_id);
                this.$http.get("http://localhost:8082/muser/del?u_id="+row.u_id).then(req=>{
                    if(req!=null){
                        /*alert(req.data);*/
                        this.$message({
                            showClose: true,
                            message: '歌手'+row.u_id+'账号删除成功！',
                            type: 'success'
                        });
                        this.reload();
                    }
                })
            },
            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange(size){
                this.size = size;        //将每页显示的条数 = 改变的值
                this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                    this.tableData=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.size) //每页下拉显示数据
            },
            handleCurrentChange(index) {
                this.index = index;        //然后将当前页 = 改变的值
                this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                    this.tableData=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index)        //点击第几页
            },
            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange1(size){
                this.size1 = size;        //将每页显示的条数 = 改变的值
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.size1) //每页下拉显示数据
            },
            handleCurrentChange1(index) {
                this.index1 = index;        //然后将当前页 = 改变的值
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index1)        //点击第几页
            }
        }
    }
</script>

<style scoped>
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
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }
    .avatar {
        width :100px;
        height: 100px;
        display: block;
    }
    .el-pagination{
        text-align: center;
    }
</style>