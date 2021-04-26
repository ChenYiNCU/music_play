<template>
    <div id="main">
        <div class="right">
            <div class="form">
                <el-form label-width="80px" status-icon :model="ruleForm" :rules="rules" ref="ruleForm">
                    <el-form-item label="网名" prop="uname">
                        <el-input v-model="ruleForm.uname" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="头像" prop="uphoto">
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
                    <el-form-item label="手机号" prop="tel">
                        <el-input v-model="ruleForm.tel" style="width: 400px;" @change="checkTel()"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="upwd">
                        <el-input v-model="ruleForm.upwd" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="ruleForm.email" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄" prop="age">
                        <el-input v-model="ruleForm.age" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                        <el-select v-model="ruleForm.sex" placeholder="请选择性别">
                            <el-option label="男" value="男"></el-option>
                            <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="个人简介" prop="des">
                        <el-input type="textarea" v-model="ruleForm.des" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button :plain="true" @click="regist('ruleForm')">注册</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "regist",
        data(){
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
            return{
                ruleForm:{
                    tel:'',
                    upwd:'',
                    uname:'',
                    uphoto:'',
                    email:'',
                    age:'',
                    sex:'',
                    des:'',
                },
                uploadUrl:'http://localhost:8082/muser/upload',
                imageUrl: '',
                file:'',
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
            };
        },
        methods:{
            checkTel:function(){
                this.$http.get("http://localhost:8082/muser/findTel?tel="+this.ruleForm.tel).then(req=>{
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
                this.ruleForm.uphoto="img/"+file.name;
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


            regist:function (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //alert('submit!');
                        //this.upload();
                        this.$http.get("http://localhost:8082/muser/regist?tel="+this.ruleForm.tel+"&upwd="+this.ruleForm.upwd+"&uname="+this.ruleForm.uname+"&email="+this.ruleForm.email+"&age="+this.ruleForm.age+"&sex="+this.ruleForm.sex+"&des="+this.ruleForm.des+"&uphoto="+this.ruleForm.uphoto).then(req=>{
                            if(req.data!="") {
                                this.$message({
                                    showClose: true,
                                    message: '注册成功，请耐心等待审核！',
                                    type: 'success'
                                });
                                this.upload();
                                this.$router.push({name:'index'});
                            }
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
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
        width : 100px;
        height: 100px;
        display: block;
    }

    #main{
        width: 100%;
        height: 100%;
        overflow: hidden;
        background: url("../assets/img/bg3.png") no-repeat;
        background-size: 100% 100%;
    }
    .right{
        float: right;
        width:680px;
        height: 850px;
        padding-top:50px;
        border-right: 1px solid #d3d3d3;
        box-shadow: 0 0 3px silver;
        background-color:#fff;
    }
    .form{
        -border: 1px solid black;
        width:500px;
        margin:0 auto;
    }
    .el-button{
        background-color: #fff;
        color: #e24534;
        border-color: #e24534;
        font-family: '黑体';
        font-size: 16px;
        padding:10px 25px;
        margin-left: 130px;
        border-radius: 18px;
    }
    .el-button:hover{
        background-color: #e24534;
        border-color: #e24534;
        color: #fff;
    }
</style>