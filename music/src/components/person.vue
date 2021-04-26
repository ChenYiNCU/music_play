<template>
    <div class="bg" style="padding-bottom: 100px">
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
        <div class="main">
            <div class="top">
                <el-row :gutter="10">
                    <el-col :span="6"><div class="grid-content bg-purple">
                        <div class="face-photo">
                            <el-image
                                    style="width: 200px; height: 200px; margin-top: 5px;"
                                    :src="uphoto"
                                    :fit="fit"></el-image>
                        </div>
                    </div></el-col>
                    <el-col :span="18"><div class="grid-content bg-purple">
                        <div class="user-info">
                            <div class="user-top">
                                <el-row :gutter="0">
                                    <el-col :span="20"><div class="user-top-left"><p v-text="uname" style="display: inline-block"></p></div></el-col>
                                    <el-col :span="4"><div class="user-top-right"><el-button round @click="findById()">编辑个人资料</el-button></div></el-col>
                                </el-row>
                            </div>
                            <div class="follow-info">
                                <div class="follow-box" @click="dialogFollowVisible = true">
                                    <p v-text="follows"></p>
                                    <p>关注</p>
                                </div>
                                <div class="follow-box"></div>
                                <div class="follow-box" @click="dialogFanVisible = true">
                                    <p v-text="fans"></p>
                                    <p>粉丝</p>
                                </div>
                            </div>
                        </div>
                    </div></el-col>
                </el-row>
                <!--显示关注的人的 对话框-->
                <el-dialog title="" :visible.sync="dialogFollowVisible">
                    <el-table :data="followData">
                        <el-table-column property="uname" label="关注" width="120"></el-table-column>
                        <el-table-column property="tel" label="" width="200"></el-table-column>
                        <el-table-column
                                align="right">
                            <template slot-scope="scope">
                                <el-button
                                        size="mini"
                                        round
                                        @click="goto(scope.row)">查看</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-dialog>
                <!--显示粉丝信息的 对话框-->
                <el-dialog title="" :visible.sync="dialogFanVisible">
                    <el-table :data="fanData">
                        <el-table-column property="uname" label="粉丝" width="120"></el-table-column>
                        <el-table-column property="tel" label="" width="200"></el-table-column>
                        <el-table-column
                                align="right">
                            <template slot-scope="scope">
                                <el-button
                                        size="mini"
                                        round
                                        @click="goto(scope.row)">查看</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-dialog>
                <!--修改会员信息 对话框-->
                <el-dialog title="会员信息" :visible.sync="dialogEditVisible">
                    <el-form :model="editForm" status-icon  :rules="rules" ref="editForm">
                        <el-form-item label="网名" :label-width="formLabelWidth" prop="uname">
                            <el-input v-model="editForm.uname" autocomplete="off"></el-input>
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
                        <el-form-item label="手机号" :label-width="formLabelWidth" prop="tel" >
                            <el-input v-model="editForm.tel" autocomplete="off" @change="checkTel()"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                            <el-input v-model="editForm.email" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="年龄" :label-width="formLabelWidth" prop="age">
                            <el-input v-model="editForm.age" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
                            <el-select v-model="editForm.sex" placeholder="">
                                <el-option label="男" value="男"></el-option>
                                <el-option label="女" value="女"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="个人简介" :label-width="formLabelWidth" prop="des">
                            <el-input type="textarea" v-model="editForm.des" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogEditVisible=false">取 消</el-button>
                        <el-button type="primary" :plain="true" @click="edit('editForm')">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
            <div class="recent-music">
                <p class="recent-title">听歌排名</p>
                <hr size="2" color="#c20c0c">
                <el-table
                        :data="recentData"
                        stripe
                        style="width: 100%; font-size: 16px;"
                        :default-sort = "{prop: 'date', order: 'descending'}">
                    <el-table-column
                            prop="mname"
                            label="歌名"
                            width="280">
                        <template slot-scope="scope">
                            <span @click="toPlay(scope.row.mid)" style="cursor: pointer;">{{scope.row.mname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="singer.uname"
                            label="歌手"
                            width="280">
                        <template slot-scope="scope">
                            <span @click="gotoSinger(scope.row)" style="cursor: pointer;">{{scope.row.singer.uname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="time"
                            label="上传时间"
                            sortable>
                    </el-table-column>
                </el-table>
            </div>
            <div class="myplaylist">
                <div class="title">
                    <p>我收藏的歌单 <span @click="playDrawer = true" style="float: right" v-if="playList.length>6">查看全部</span></p>
                    <hr size="2" color="#c20c0c">
                </div>
                <div v-if="playList.length!=0">
                    <el-row :gutter="20">
                        <el-col class="cursor" :span="4" v-for="(item,index) in playList"  :key="item">
                            <el-card shadow="hover" :body-style="{padding: '0'}" v-if="index<6">
                                <div @click="toPlay(item.mid)">
                                    <el-image :src="item.mphoto" fit="fill" style="width: 164px; height: 164px;"></el-image>
                                    <div class="mname">
                                        <span>{{item.mname}}</span>
                                    </div>
                                    <img v-if="playId==item.mid" @click.stop="playNow(item.mid)" :src="start" style="width:25px; position:relative; left:79%;top: -6px;">
                                    <img v-else @click.stop="playNow(item.mid)" :src="stop" style="width:25px; position:relative; left:79%;top: -6px;">
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
                <div v-if="playList.length==0" class="no-data">暂无收藏歌曲</div>
                <el-drawer
                        title="全部收藏"
                        :visible.sync="playDrawer"
                        :with-header="true">
                <span>
                    <el-row :gutter="20">
                        <el-col class="cursor" :span="8" v-for="item in playList" :key="item">
                            <el-card shadow="hover" :body-style="{padding: '0'}">
                                <div @click="toPlay(item.mid)">
                                    <el-image :src="item.mphoto" fit="fill" style="width: 138px; height: 138px;"></el-image>
                                    <div class="mname">
                                        <span>{{item.mname}}</span>
                                    </div>
                                    <img v-if="playId==item.mid" @click.stop="playNow(item.mid)" :src="start" style="width:25px; position:relative; left:79%;top: -6px;">
                                    <img v-else @click.stop="playNow(item.mid)" :src="stop" style="width:25px; position:relative; left:79%;top: -6px;">
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </span>
                </el-drawer>
            </div>
            <div class="mylovelist">
                <div class="title">
                    <p>我喜爱的歌单 <span @click="loveDrawer = true" style="float: right" v-if="likeList.length>6">查看全部</span></p>
                    <hr size="2" color="#c20c0c">
                </div>
                <div v-if="likeList.length!=0">
                    <el-row :gutter="20">
                        <el-col class="cursor" :span="4" v-for="(item,index) in likeList" :key="item">
                            <el-card shadow="hover" :body-style="{padding: '0'}" v-if="index<6">
                                <div @click="toPlay(item.mid)">
                                    <el-image :src="item.mphoto" fit="fill" style="width: 164px; height: 164px;"></el-image>
                                    <div class="mname">
                                        <span>{{item.mname}}</span>
                                    </div>
                                    <img v-if="playId==item.mid" @click.stop="playNow(item.mid)" :src="start" style="width:25px; position:relative; left:79%;top: -6px;">
                                    <img v-else @click.stop="playNow(item.mid)" :src="stop" style="width:25px; position:relative; left:79%;top: -6px;">
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
                <div v-if="likeList.length==0" class="no-data">暂无喜爱歌曲</div>
                <el-drawer
                        title="全部喜爱"
                        :visible.sync="loveDrawer"
                        :with-header="true"
                >
                <span>
                    <el-row :gutter="20">
                        <el-col class="cursor" :span="8" v-for="item in likeList" :key="item">
                            <el-card shadow="hover" :body-style="{padding: '0'}">
                                <div @click="toPlay(item.mid)">
                                    <el-image :src="item.mphoto" fit="fill" style="width: 138px; height: 138px;"></el-image>
                                    <div class="mname">
                                        <span>{{item.mname}}</span>
                                    </div>
                                    <img v-if="playId==item.mid" @click.stop="playNow(item.mid)" :src="start" style="width:25px; position:relative; left:79%;top: -6px;">
                                    <img v-else @click.stop="playNow(item.mid)" :src="stop" style="width:25px; position:relative; left:79%;top: -6px;">
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </span>
                </el-drawer>
            </div>
        </div>

        <div class="audio_con" v-show="isPlay">
            <img @click="toPlay(music.mid)" :src="music.mphoto" alt="" style="cursor:pointer;width:80px;margin:10px 0;margin-left: 20px;float: left">


            <div style="float: left;-width: 100px; height: 80px;text-align: center;margin:10px 10px;padding: 0;">
                <span style="display: inline-block; height: 20px;font-size: 18px;margin: 0 20px">{{music.mname}}</span>
                <span >
                <img :src="isLike?'/img/爱心.png' :'/img/爱.png'" alt="" width="28px" @click="Like(music.mid)" style="z-index: 1000">
            </span>
                <p style="color: #409EFF;padding: 10px">{{music.singer.uname}}</p>
            </div>
            <audio ref='audio' :src="music.murl" controls autoplay loop class="myaudio" ></audio>
        </div>

    </div>
</template>

<script>
    export default {
        inject:['reload'],
        name: "person",
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
                user:{
                    u_id:'',
                    uname:'',
                    uphoto:''
                },//用户
                music:{
                    m_id:'',
                    mphoto:'',
                    mname:'',
                    singer:{
                        uname:''
                    },
                    murl:''
                },
                isLike:false,
                isPlay:false,

                u_id:sessionStorage.getItem("u_id"),
                uname:'',
                uphoto:'',
                follows:0,
                fans:0,
                editForm:{
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
                },
                imageUrl: '',
                uploadUrl:'http://localhost:8082/muser/upload',
                file:'',

                recentData:[],
                playList:[],
                likeList:[],
                followData:[],
                fanData:[],
                stop:'img/播放.png',
                start:'img/正在播放.png',
                playDrawer:false,
                loveDrawer: false,
                dialogFollowVisible:false,
                dialogFanVisible:false,
                dialogEditVisible:false,
                formLabelWidth:'120px',
            }
        },created(){
            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");
            this.$http.get("http://localhost:8082/muser/findById?u_id="+this.u_id).then(req=>{
                if(req!=null){
                    this.uname=req.data.uname;
                    this.uphoto=req.data.uphoto;
                    //alert(req.data.uphoto);
                }
            }),
                this.$http.get("http://localhost:8082/muser/findFF?u_id="+this.u_id).then(req=>{
                    if(req!=null){
                        this.fans=req.data.fans;
                        this.follows=req.data.follows;
                        this.fanData=req.data.fansList;
                        this.followData=req.data.followsList;
                    }
                }),this.$http.get("http://localhost:8082/mplink/findRecent?u_id="+this.u_id).then(req=>{
                if(req.data!=null){
                    this.recentData=req.data;
                }
            }),this.$http.get("http://localhost:8082/mplink//findPlaylist?u_id="+this.u_id).then(req=>{
                //alert(req.data);
                if(req.data!=null){
                    this.playList=req.data;
                }
            }),this.$http.get("http://localhost:8082/mplink//findLikelist?u_id="+this.u_id).then(req=>{
                //alert(req.data);
                if(req.data!=null) {
                    this.likeList = req.data;
                }
            })
        },
        methods:{
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
                //alert("登出");
                this.user='';
                sessionStorage.removeItem("u_id");
                sessionStorage.removeItem("uname");
                sessionStorage.removeItem("uphoto");
            },
            login(){
                this.$router.push({name:"uLogin"});
            },

            checkTel:function(){
                this.$http.get("http://localhost:8082/muser/findTel?tel="+this.editForm.tel).then(req=>{
                    if(req.data!=null){  //说明该电话号码已经存在
                        // alert(req.data);
                        if(req.data !='a') {  //说明当前输入手机号用户存在
                            if (req.data != sessionStorage.getItem("tel")) {  //当前登陆用户的新手机号
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
            Like:function (id) {
                this.isLike= !this.isLike;
                if (this.isLike==true){
                    this.$http.get("http://localhost:8082/mplink/like?mid="+id+"&u_id="+this.u_id).then();
                }else{
                    this.$http.get("http://localhost:8082/mplink/noLike?mid="+id+"&u_id="+this.u_id).then();
                }
            },
            play() {
                this.isPlay = true;
                // 清空mv的信息
                this.$refs.audio.play();
            },
            playNow(id){
                if(this.playId==id){
                    this.isPlay=false;
                    this.$refs.audio.pause();// 暂停歌曲播放
                    this.playId='';
                }else{
                    this.isPlay=true;
                    this.playId = id;
                    this.$refs.audio.play();
                    this.$http.get("http://localhost:8082/mplink/isLike_isStar?mid="+id+"&u_id="+this.u_id).then(req=>{
                        this.isLike=req.data.isLike;
                    });
                    this.$http.get("http://localhost:8082/mplink/findMusicById?mid="+id).then(req=>{
                        this.music= req.data;   //返回一个音乐对象
                    });
                    this.$http.get("http://localhost:8082/mplink/addRecent?mid="+id+"&u_id="+this.u_id);
                    this.$http.get("http://localhost:8082/mplink/playNumUp?mid="+id);
                }
            },
            toPlay(id){
                this.$router.push({name:"play",params:{mid:id}});
            },
            findById:function () {
                this.$http.get("http://localhost:8082/muser/findById?u_id="+this.u_id).then(req=>{
                    if(req!=null){
                        this.editForm.u_id=req.data.u_id;
                        this.editForm.uname=req.data.uname;
                        this.editForm.uphoto=req.data.uphoto;
                        this.imageUrl=req.data.uphoto;
                        this.editForm.tel=req.data.tel;
                        this.editForm.upwd=req.data.upwd;
                        this.editForm.email=req.data.email;
                        this.editForm.age=req.data.age;
                        this.editForm.sex=req.data.sex;
                        this.editForm.des=req.data.des;
                        this.dialogEditVisible=true;
                    }
                })
                this.dialogUpdateVisible = true;
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                this.file=file;
                this.editForm.uphoto="img/"+file.name;
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
            },edit:function(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //alert(this.editForm.u_id);
                        //alert(this.form.des);
                        this.$http.get("http://localhost:8082/muser/edit?u_id="+this.editForm.u_id+"&tel="+this.editForm.tel+"&uname="+this.editForm.uname+"&email="+this.editForm.email+"&age="+this.editForm.age+"&sex="+this.editForm.sex+"&des="+this.editForm.des+"&uphoto="+this.editForm.uphoto).then(req=>{
                            if(req.data!=""){
                                this.upload();
                                this.$message({
                                    showClose: true,
                                    message: '修改成功！',
                                    type: 'success'
                                });
                                sessionStorage.setItem("uphoto",this.editForm.uphoto);
                                sessionStorage.setItem("uname",this.editForm.uname);
                                this.uname=this.editForm.uname;
                                this.uphoto=this.editForm.uphoto;
                                this.dialogEditVisible= false;
                                //this.reload();
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            goto:function (row) {
                //alert(row.u_id);
                sessionStorage.setItem("otherId",row.u_id);
                this.$router.push({name:"others"});
            },
            gotoSinger:function (row) {
                //alert(row.singer.tel);
                this.$http.get("http://localhost:8082/muser/findSingerByTel?tel="+row.singer.tel).then(req=>{
                    if(req.data!=null){
                        sessionStorage.setItem("otherId",req.data.u_id);
                        this.$router.push({name:"others"});
                    }
                })
            }
        }
    }
</script>

<style scoped>
    body{
        margin: 0;
        padding: 0;
    }
    .bg{
        width: 100%;
        height: auto;
        background-color:#f5f5f5;
    }
    .main{
        width: 1100px;
        height: auto;
        padding-left: 50px;
        padding-right: 50px;
        margin: 0 auto;
        background-color:#fff;
        border-left: 1px solid #d3d3d3;
        border-right: 1px solid #d3d3d3;
        box-shadow: 0 0 3px silver;
    }
    .grid-content{
        height: 230px;
        padding-top: 20px;
    }
    .bg-purple {
        -background: #e5e9f2;
    }
    .face-photo{
        width: 210px;
        height: 210px;
        border: 1px solid #d5d5d5;
        background-color: #fff;
        margin:0 auto;
        text-align: center;
        -position: relative;
    }
    .face-photo>img{
        width: 200px;
        height: 200px;
        margin-top: 5px;
    }
    .user-info{
        height: 210px;
        -border: 1px solid black;
    }
    .user-top .user-top-left{
        text-align: left;
        font-size: 26px;
        line-height: 20px;
        font-weight: bold;
        -border: 1px solid black;
    }
    .user-top el-button{
        display: inline-block;
        text-align: right;
    }
    .follow-info{
        border-top: 1px solid #dddddd;
    }
    .follow-box{
        width: 100px;
        -height: 60px;
        -border: 1px solid black;
        float: left;
        cursor: pointer;
    }
    .follow-box:nth-of-type(2){
        width: 20px;
        height: 100px;
        border-left: 1px solid #dddddd;
    }
    .follow-info .follow-box p{
        text-align: left;
        line-height: 0px;
    }
    .follow-info .follow-box p:nth-of-type(1){
        font-size: 40px;
    }
    /*.user-top-left{
        text-align: left;
        border: 1px solid black;
    }*/
    .user-top-right{
        -border: 1px solid black;
        height: 72px;
        line-height: 72px;
    }
    .recent-music{
        margin-top: 50px;
    }
    .recent-title{
        text-align: left;
        font-size: 26px;
        line-height: 0px;
    }

    /*.myplaylist{
        margin-top:50px ;
    }
    .mylovelist{
        margin-top: 50px;
    }*/
    .title{
        margin-top: 45px;
        -border: 1px solid black;
    }
    .title p{
        text-align: left;
        font-size: 26px;
        line-height: 0px;
    }
    .title p span{
        font-size: 18px;
        -text-align: right;
        cursor: pointer;
    }
    /*歌单*/
    .myplaylist .el-row{
        margin-bottom: 70px;
    }
    .myplaylist .el-col{
        margin-bottom: 16px;
    }
    .mylovelist .el-row{
        margin-bottom: 70px;
    }
    .mylovelist .el-col{
        margin-bottom: 16px;
    }
    .cursor{
        cursor: pointer;
    }
    .mname{
        width: 70%;
        padding: 8px;
        padding-right:4px;
        padding-bottom: 0;
        margin-bottom: -16px;
        font-size: 15px;
        font-family: '宋体';
        text-overflow:ellipsis;
        overflow:hidden;
        white-space:nowrap;
    }
    .no-data{
        height: 60px;
        line-height: 60px;
        -width: 50%;
        color: #909399;
        text-align: center;
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


</style>