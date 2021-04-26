<template>
    <!--<div id="main">
        <el-form label-width="80px">
            <el-form-item label="账号">
                <el-input v-model="aname"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="apwd"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :plain="true" @click="login()">登入</el-button>
            </el-form-item>
        </el-form>
    </div>-->
    <div id="main">
        <el-card shadow="always" class="wrapper">
            <el-form label-width="80px" status-icon :model="ruleForm" :rules="rules" ref="ruleForm">
                <el-form-item label="账号" prop="aname">
                    <el-input v-model="ruleForm.aname" style="width: 350px;"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="apwd">
                    <el-input v-model="ruleForm.apwd" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" :plain="true" @click="login('ruleForm')">登入</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "login",
        data(){
            return{
                ruleForm: {
                    aname:'',
                    apwd:'',
                    checkCode:'aaaa',
                },rules:{
                    apwd:[
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    aname:[
                        {required: true, message: '请输入账号', trigger: 'blur'},
                    ],
                }
            }
        },
        methods:{
            login:function (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.get("http://localhost:8082/madmin/login?aname="+this.ruleForm.aname+"&apwd="+this.ruleForm.apwd+"&checkCode="+this.ruleForm.checkCode).then(req=>{
                            if(req.data!=null){
                                if(req.data.flag==0){
                                    this.$message({
                                        showClose: true,
                                        message: '验证码错误，请重新输入！',
                                        type: 'error'
                                    });
                                }else{
                                    if(req.data.flag==1){
                                        this.$message({
                                            showClose: true,
                                            message: '该管理员不存在，请重新输入管理员账号！',
                                            type: 'error'
                                        });
                                    }
                                    if(req.data.flag==2){
                                        this.$message({
                                            showClose: true,
                                            message: '密码输入错误，请重新输入！',
                                            type: 'error'
                                        });
                                    }
                                    if(req.data.flag==3){  //账号密码正确
                                        this.$message({
                                            showClose: true,
                                            message: '登陆成功，欢迎管理员'+req.data.madmin.aname+'!',
                                            type: 'success'
                                        });
                                        sessionStorage.setItem("admin",req.data.madmin.aname);
                                        this.$router.push({name:'admin'});
                                    }
                                }
                            }else{
                                this.$router.push({name:"aLogin"});
                            }
                        })
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
    #main{
        width: 100%;
        height: 100%;
        overflow: hidden;
        background: url("../assets/img/bg1.jpg");
        background-size:100%;
    }
    .el-card{
        width:480px;
        height: 255px;
        padding-top:55px;
        border-radius: 10px;
        border:none;
        background-color: rgba(255, 255, 255, 0.8);
        margin-top:150px;
        margin-left:950px;
    }

    .el-button{
        /*background-color: #fff;
        color: #e24534;
        border-color: #e24534;*/
        font-family: '黑体';
        font-size: 16px;
        padding:11px 25px;
        margin-left: 110px;
        border-radius: 18px;
    }
    /*.el-button:hover{
        background-color: #e24534;
        border-color: #e24534;
        color: #fff;
    }*/

    @keyframes fade-in {
        0% {opacity: 0;}
        40% {opacity: 0;}
        100% {opacity: 1;}
    }
    @-webkit-keyframes fade-in {
        0% {opacity: 0;}
        40% {opacity: 0;}
        100% {opacity: 1;}
    }
    .wrapper {
        animation: fade-in;
        animation-duration: 2s;
        -webkit-animation:fade-in 1s;
    }
</style>