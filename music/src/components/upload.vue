<template>
    <div>
    <header>
        <img class="logo" src="../assets/logo.png" >
        <span @click="toIndex()" class="name">音乐播放器</span>
        <form>
            <input class="search" type="text" v-model="search" name="" id="" value=""/>
        </form>
        <div @click="searchBtn()" class="el-icon-search"></div>
        <template v-if="user.u_id!=null&&user.u_id!=''">
            <img @click="person()" class="tx" :src="user.uphoto">
            <a @click="person()" class="uName">{{user.uname}}</a>
            <div @click="Upload()" class="el-icon-upload">上传歌曲</div>
            <a @click="out()" class="out">登出</a>
        </template>
        <template v-else>
            <span style="color: #f5f5f5; margin-left: 380px;font-size:14px;">世界应你而美丽，欢迎来到音乐播放器！</span>
            <div @click="upload()" class="el-icon-upload">上传歌曲</div>
            <a @click="login()" class="out">登入</a>
        </template>
    </header>
    <div class="container">
        <div class="box">
        <el-form label-width="80px">
            <el-form-item label="歌曲名">
                <el-input v-model="mname"></el-input>
            </el-form-item>

            <el-form-item label="歌曲封面">
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8082/muser/upload"
                        :show-file-list="false"
                        multiple
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>

            <el-form-item label="歌曲类型">
                <el-select style="width: 300px;" v-model="t_id" placeholder="请选择">
                    <el-option
                            v-for="item in typeList"
                            :key="item.t_id"
                            :label="item.tname"
                            :value="item.t_id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="歌手">
                <input class="sinerIn" @keyup="searchByName()" v-model="uname" placeholder="请输入歌手"/>
            </el-form-item>
            <div class="singerBox" v-if="singerList!=''&&singerList!=null">
                <span v-for="item in singerList" :key="item" @click="check(item)" >{{item.uname}}</span>
            </div>
            <el-form-item label="歌词">
                <el-input type="textarea" v-model="words"></el-input>
            </el-form-item>

            <el-form-item label="音乐地址">
                <el-upload
                        action="http://localhost:8082/muser/upload"
                        :show-file-list="true"
                        :multiple="false"
                        limit="1"
                        :file-list="fileList2"
                        :on-success="handleAvatarSuccess2"
                        :on-remove="handleRemove2"
                        :before-upload="beforeAvatarUpload2">
                    <el-button>点击上传</el-button>
                    <div slot="tip" class="el-upload__tip"></div>
                </el-upload>
            </el-form-item>

            <el-form-item label="Mv">
                <el-upload
                        action="http://localhost:8082/muser/upload"
                        :show-file-list="true"
                        :multiple="false"
                        limit="1"
                        :file-list="fileList1"
                        :on-success="handleAvatarSuccess1"
                        :on-remove="handleRemove1"
                        :before-upload="beforeAvatarUpload1">
                    <el-button>点击上传</el-button>
                    <div slot="tip" class="el-upload__tip"></div>
                </el-upload>
            </el-form-item>

            <el-form-item>
                <el-button @click="uploadMusic()" style="width: 200px;" type="danger" round>上传</el-button>
            </el-form-item>
        </el-form>
    </div>
    </div>
        <footer>
            <p>COPYRIGHT &copy; 陈益 &emsp; 董丽荣 &emsp; 王晏</p>
            <p>南昌大学软件学院</p>
            <p>电话：19979404672&emsp; &emsp;邮箱1159428961@qq.com</p>
            <div style="width: 100%;height: 40px;background-color:#d20d16;position: absolute;bottom: 0;left: 0;">
            </div>
        </footer>
    </div>
</template>

<script>
    import singerList from "@/components/singerList";

    export default {
        name: "upload",
        data(){
            return{
                user:{
                    u_id:'',
                    uname:'',
                    uphoto:''
                },//用户
                mname:'',
                words:'',
                t_id:'',
                sid:'',
                uname:'',//歌手名
                murl:'',
                mvurl:'',
                typeList:[],
                singerList:[],
                fileList1:[],
                fileList2:[],

                uploadUrl:'http://localhost:8082/muser/upload2',
                imageUrl: '',
                file:'',
                file1:'',
                file2:''
            }
        },
        created(){
            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");
            //读取所有类型
            this.$http.get("http://localhost:8082/mtype/findAll").then(req=>{this.typeList=req.data;});
        },
        methods:{

            //传封面
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                if (isJPG||isPNG){
                    this.file=file;
                    this.mphoto=file.name;
                    return true;
                }
                else{
                    this.$message.error('上传文件只能是 JPG或PNG 格式!');
                    return false;
                }
            },

            //传mv
            handleRemove1(file, fileList) {
                console.log(file, fileList);
                this.fileList1=[];
            },
            handleAvatarSuccess1(res, file) {
                let mvUrl = URL.createObjectURL(file.raw);
                this.fileList1.push({"name":this.mvurl,"url":mvUrl});
            },
            beforeAvatarUpload1(file) {
                const isMP4 = file.type === 'video/mp4';
                if (isMP4) {
                    this.file1 = file;
                    this.mvurl = file.name;
                    return true;
                }
                else{
                    this.$message.error('上传文件只能是 MP4 格式!');
                    return false;
                }
            },
            //传音乐
            handleRemove2(file, fileList) {
                console.log(file, fileList);
                this.fileList2=[];
            },
            handleAvatarSuccess2(res, file) {
                let mUrl = URL.createObjectURL(file.raw);
                this.fileList2.push({"name":this.murl,"url":mUrl});
            },
            beforeAvatarUpload2(file) {
                const isMP3 = file.type === 'audio/mpeg';
                if (isMP3) {
                    this.file2=file;
                    this.murl=file.name;
                    return true;
                }
                else{
                    this.$message.error('上传文件只能是 MP3 格式!');
                    return false;
                }
            },
            //传到文件夹
            upload(){
                console.log("开始上传，上传的文件为："+this.file)
                let formData = new FormData();
                formData.append("multipartFiles", this.file);
                formData.append("multipartFiles", this.file1);
                formData.append("multipartFiles", this.file2);
                formData.append("path", this.mname+"/");
                this.$http({
                    method: 'post',
                    url:'http://localhost:8082/muser/upload2',
                    data: formData,
                    headers: {'Content-Type': 'multipart/form-data' }
                }).then((res) => {
                    console.log("文件上传返回："+res)
                }).catch(error => {
                    console.log("文件上传异常:"+error)
                });
            },
            //存到数据库
            uploadMusic(){
                if(this.user.u_id==''||this.user.u_id==null){
                    this.$message.error('请先登入!');
                    return false;
                }
                if(this.mname==''||this.mname==null){
                    this.$message.error('请输入歌曲名!');
                    return false;
                }if(this.mphoto==''||this.mphoto==null){
                    this.$message.error('请上传歌曲封面!');
                    return false;
                }if(this.t_id==''||this.t_id==null){
                    this.$message.error('请选择类型!');
                    return false;
                }if(this.sid==''||this.sid==null){
                    this.$message.error('请选择歌手!');
                    return false;
                }if(this.words==''||this.words==null){
                    this.$message.error('请输入歌词!');
                    return false;
                }if(this.murl==''||this.murl==null){
                    this.$message.error('请输入音乐地址!');
                    return false;
                }
                else{
                    this.mphoto="music/"+this.mname+"/"+this.mphoto;
                    if(this.mvurl!=''&&this.mvurl!=null){
                        this.mvurl="music/"+this.mname+"/"+this.mvurl;
                    }
                    this.murl="music/"+this.mname+"/"+this.murl;
                    this.$http.get("http://localhost:8082/mplink/add?mname="+this.mname+"&sid="+this.sid+"&u_id="+this.user.u_id+"&tid="+this.t_id+"&words="+this.words+"&mphoto="+this.mphoto+"&murl="+this.murl+"&mvurl="+this.mvurl).then(req=>{
                        if(req.data!="") {
                            this.$message({
                                showClose: true,
                                message: '上传成功！',
                                type: 'success'
                            });
                            this.upload();
                            this.$router.push({name:'index'});
                        }
                    });
                }
            },
            //选歌手
            searchByName(){
                this.$http.get("http://localhost:8082/muser/searchByName?uname="+this.uname).then(req=>{this.singerList=req.data;});
            },
            check(item){
                this.singerList=this.singerList.splice(0,singerList.length);
                this.singerList.push(item);
                this.uname=item.uname;
                this.sid=item.u_id;
            },


            //头加尾
            toIndex(){
                this.$router.push({name:"index"});
            },
            searchBtn(){
                this.$router.push({name:"search",params:{search:this.search}});
            },
            person(){
                this.$router.push({name:"person"});
            },
            Upload(){
                this.$router.push({name:"upload"});
            },
            out(){
                alert("登出");
                this.user='';
                sessionStorage.removeItem("u_id");
                sessionStorage.removeItem("uname");
                sessionStorage.removeItem("uphoto");
            },
            login(){
                this.$router.push({name:"uLogin"});
            }
        }
    }
</script>

<style scoped>
    .avatar-uploader  {
        border: 1px solid #d9d9d9;
        border-radius: 6px;
        width: 150px;
        height: 150px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader :hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 150px;
        height: 150px;
        line-height: 150px;
        text-align: center;
    }
    .avatar {
        width: 150px;
        height: 150px;
        display: block;
    }
    .container{
        background-image: url('../assets/img/bg5.png');
        /*width: 1100px;*/
        /*margin: 0 auto;*/
        background-position: 770px 70px;
        background-repeat: no-repeat;
        background-size:40%;
    }
    .box{
        padding-top:50px ;
        padding-bottom:50px;
        padding-left:70px ;
        width: 600px;
    }
    .singerBox{
        width: 500px;
        height: 100px;
        margin-left: 80px;
        margin-bottom: 20px;
        padding: 10px;
        border-radius: 4px;
        border: 1px solid #d9d9d9;
        overflow:auto;
    }
    .singerBox span{
        padding: 4px;
        margin: 9px;
        line-height: 30px;
        cursor: pointer;
        border-radius: 4px;
        background-color: #eeeeee;
    }
    .sinerIn{
        width: 280px;
        padding: 12px 10px;
        border-radius: 4px;
        border: 1px solid #d9d9d9;
        outline: none;
    }

    body{
        margin: 0;
        padding: 0;
    }
    a{text-decoration: none;color:#333;}
    a:hover, a:visited, a:link, a:active {
        color:white;
    }
    input {
        outline: none
    }
    header{
        display: flex;
        align-items: center;
        height: 76px;
        background-color:#EC4141;
        color: white;
    }
    .logo{
        margin-left:50px ;
        width: 32px;
    }
    .name{
        font-size: 20px;
        font-family: '宋体';
        cursor: pointer;
    }
    .search{
        margin-left: 160px;
        margin-right: 10px;
        padding: 5px 22px;
        border-radius: 14px;
        border: 1px solid transparent;
        background: #F2F2F2;
    }
    .el-icon-search{
        font-size: 20px;
        padding: 5px;
        cursor: pointer;
    }
    .tx{
        width: 35px;
        height: 35px;
        border-radius: 50%;
        margin-left: 600px;
        margin-right: 10px;
        cursor: pointer;
    }
    .uName{
        color: #eaedf1;
        cursor: pointer;
        font-size: 14px;
    }
    .uName::after{
        content: '';
        display: inline-block;
        width: 0;
        height: 0;
        border: 5px solid transparent;
        border-top: 5px solid #eaedf1;
        position: relative;
        top: 2px;
        left: 6px;
    }
    .el-icon-upload{
        margin-left: 22px;
        font-size: 16px;
        padding: 5px;
        cursor: pointer;
    }
    .out{
        margin-left: 48px;
        font-size: 13px;
        padding: 5px;
        cursor: pointer;
        color: #eaedf1;
    }

    footer{
        width: 100%;
        height: 180px;
        background-color:#edeeee;
        font-family: '宋体';
        position: relative;
        padding-top:20px;
        text-align: center;
    }
</style>