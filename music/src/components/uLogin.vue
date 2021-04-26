<template>
    <div id="main">
        <el-card shadow="always" class="wrapper">
            <el-form label-width="80px" status-icon :model="ruleForm" :rules="rules" ref="ruleForm">
                <el-form-item label="手机号" prop="tel">
                    <el-input v-model="ruleForm.tel" style="width: 350px;"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="upwd">
                    <el-input v-model="ruleForm.upwd" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" :plain="true" @click="login('ruleForm')">登入</el-button>
                    <p @click="regist()" style="cursor: pointer" class="uregist">不是会员?&nbsp;现在去注册→</p>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "uLogin",
        data(){
            return{
                ruleForm: {
                    tel:'',
                    upwd:'',
                    checkCode:'aaaa'
                },rules:{
                    upwd:[
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    tel:[
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        {min:11, max:11, message: '请输入11位的手机号', trigger: 'blur'}
                    ],
                }
            }
        },
        methods:{
            login:function (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                        //alert(this.checkCode);
                        this.$http.get("http://localhost:8082/muser/login?tel="+this.ruleForm.tel+"&upwd="+this.ruleForm.upwd+"&checkCode="+this.ruleForm.checkCode).then(req=>{
                            if(req.data!=null){
                                if(req.data.flag==0){
                                    this.$message({
                                        showClose: true,
                                        message: '验证码错误，请重新输入！',
                                        type: 'error'
                                    });
                                    //alert("验证码错误，请重新输入！");
                                }else{
                                    if(req.data.flag==1){
                                        this.$message({
                                            showClose: true,
                                            message: '该手机号未注册为会员，请检查手机号是否正确！',
                                            type: 'error'
                                        });
                                        //alert("该手机号未注册为会员，请检查手机号是否正确！");
                                    }
                                    if(req.data.flag==2){
                                        this.$message({
                                            showClose: true,
                                            message: '密码输入错误，请重新输入！',
                                            type: 'error'
                                        });
                                        //alert("密码输入错误，请重新输入！");
                                    }
                                    if(req.data.flag==3){  //账号密码正确
                                        if(req.data.muser.flag==1){
                                            this.$message({
                                                showClose: true,
                                                message: '登陆成功，欢迎会员'+req.data.muser.uname+'!',
                                                type: 'success'
                                            });
                                            //alert("登陆成功，欢迎用户" + req.data.muser.uname+"!");
                                            sessionStorage.setItem("u_id",req.data.muser.u_id);
                                            sessionStorage.setItem("uname",req.data.muser.uname);
                                            sessionStorage.setItem("uphoto",req.data.muser.uphoto);
                                            sessionStorage.setItem("tel",req.data.muser.tel);

                                            this.$router.push({name:'index'});
                                        }else if(req.data.muser.flag==2){
                                            this.$message({
                                                showClose: true,
                                                message: '登陆成功，欢迎歌手'+req.data.muser.uname+'!',
                                                type: 'success'
                                            });
                                            //alert("登陆成功，欢迎歌手" + req.data.muser.uname+"!");
                                            sessionStorage.setItem("u_id",req.data.muser.u_id);
                                            sessionStorage.setItem("uname",req.data.muser.uname);
                                            sessionStorage.setItem("uphoto",req.data.muser.uphoto);
                                            sessionStorage.setItem("tel",req.data.muser.tel);

                                            this.$router.push({name:'index'});
                                        }else if(req.data.muser.flag==0){
                                            this.$message({
                                                showClose: true,
                                                message: '账号审核中！',
                                                type: 'warning'
                                            });
                                            //alert("账号审核中！");
                                        }else if(req.data.muser.flag==3){
                                            this.$message({
                                                showClose: true,
                                                message: '账号封禁中！',
                                                type: 'warning'
                                            });
                                            //alert("账号封禁中！");
                                        }else{
                                            this.$message({
                                                showClose: true,
                                                message: '账号异常！',
                                                type: 'warning'
                                            });
                                            //alert("账号异常！")
                                        }
                                    }
                                }
                            }else{
                                this.$router.push({name:"uLogin"});
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            regist:function () {
                this.$router.push({name:'regist'});
            }
        }
    }
</script>

<style scoped>
    #main{
        width: 100%;
        height: 100%;
        overflow: hidden;
        background: url("../assets/img/bg2.jpg");
        background-size:100%;
    }
.el-card{
    width:480px;
    height: 255px;
    padding-top:55px;
    border-radius: 10px;
    border:none;
    background-color: rgba(255, 255, 255, 0.5);
    margin-top:150px;
    margin-left:950px;
}
.el-button{
    background-color: #e24534;
    color: #ffffff;
    border-color: #e24534;
    font-family: '黑体';
    font-size: 16px;
    padding:11px 25px;
    margin-left: 110px;
    border-radius: 18px;
}
.el-button:hover{
    background-color: #fff;
    border-color: #e24534;
    color: #e24534;
}
.uregist{
    -text-align: center;
    font-size: 14px;
    color: #cf5a3f;
    text-decoration: underline;
    /*letter-spacing:3px;*/
    line-height: 30px;
    margin-left: 80px;
}

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