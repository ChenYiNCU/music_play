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
                                    <el-col :span="20"><div class="user-top-left"><p v-text="uname" style="display: inline-block"></p>&nbsp;&nbsp;&nbsp;&nbsp;<el-button round @click="favor" :plain="true" :style="{backgroundColor: but_color,color:ft_color}">{{content}}</el-button></div></el-col>
                                    <!--<el-col :span="4"><div class="user-top-right"><el-button round>编辑个人资料</el-button></div></el-col>-->
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
                <el-dialog title="关注" :visible.sync="dialogFollowVisible">
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
                <el-dialog title="粉丝" :visible.sync="dialogFanVisible">
                    <el-table :data="fanData">
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
                    <p>ta收藏的歌单 <span @click="playDrawer = true" style="float: right" v-if="playList.length>6">查看全部</span></p>
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
                            <el-card shadow="hover" :body-style="{padding: '0'}" v-if="playFlag=='yes'">
                                <div @click="toPlay(item.mid)">
                                    <el-image :src="item.mphoto" fit="fill"></el-image>
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
                    <p>ta喜爱的歌单 <span @click="loveDrawer = true" style="float: right" v-if="likeList.length>6">查看全部</span></p>
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
                                    <el-image :src="item.mphoto" fit="fill"></el-image>
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
        name: "others",
        data(){
            return{
                user:{
                    u_id:sessionStorage.getItem("u_id"),
                    uname:sessionStorage.getItem("uname"),
                    uphoto:sessionStorage.getItem("uphoto")
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

                u_id: 0,
                uname:'',
                uphoto:'',
                follows:0,
                fans:0,
                followFlag:false,
                content:'关注',
                but_color:'',
                ft_color:'',
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
            }
        },created(){
            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");
            if(this.u_id == 0){
                this.u_id=sessionStorage.getItem("otherId");
            }
            this.$http.get("http://localhost:8082/muser/findById?u_id="+this.u_id).then(req=>{
                if(req.data!=null){
                    this.uname=req.data.uname;
                    this.uphoto=req.data.uphoto;
                    this.u_id=req.data.u_id;
                }
            }),
                this.$http.get("http://localhost:8082/muser/findFF?u_id="+this.u_id).then(req=>{
                    if(req.data!=null){
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
                if(req.data!=null){
                    this.playList=req.data;
                }
            }),this.$http.get("http://localhost:8082/mplink//findLikelist?u_id="+this.u_id).then(req=>{
                if(req.data!=null){
                    this.likeList=req.data;
                }
            }),this.$http.get("http://localhost:8082/muser/checkFollow?nowid="+sessionStorage.getItem("u_id")+"&checkid="+this.u_id).then(req=>{
                if (req.data!=null){
                    if(req.data.u_id==this.u_id){
                        this.followFlag=true;
                        this.content='已关注';
                        this.but_color='#e24534';
                        this.ft_color='#fff';
                    }
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

            Like:function (id) {
                this.isLike= !this.isLike;
                if (this.isLike==true){
                    this.$http.get("http://localhost:8082/mplink/like?mid="+id+"&u_id="+this.user.u_id).then();
                }else{
                    this.$http.get("http://localhost:8082/mplink/noLike?mid="+id+"&u_id="+this.user.u_id).then();
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
                    this.$http.get("http://localhost:8082/mplink/isLike_isStar?mid="+id+"&u_id="+this.user.u_id).then(req=>{
                        this.isLike=req.data.isLike;
                    });
                    this.$http.get("http://localhost:8082/mplink/findMusicById?mid="+id).then(req=>{
                        this.music= req.data;   //返回一个音乐对象
                    });
                    this.$http.get("http://localhost:8082/mplink/addRecent?mid="+id+"&u_id="+this.user.u_id);
                    this.$http.get("http://localhost:8082/mplink/playNumUp?mid="+id);
                }
            },
            toPlay(id){
                this.$router.push({name:"play",params:{mid:id}});
            },
            goto:function (row) {
                sessionStorage.setItem("otherId",row.u_id);
                /*this.u_id=row.u_id;
                alert(this.u_id);*/
                this.u_id=sessionStorage.getItem("otherId");
                if(this.u_id==sessionStorage.getItem("u_id")){
                    this.$router.push({name:"person"});
                    this.dialogFollowVisible=false;
                    this.dialogFanVisible=false;
                }else{
                    this.$http.get("http://localhost:8082/muser/findById?u_id="+this.u_id).then(req=>{
                        if(req.data!=null){
                            //alert(req.data.uphoto);
                            this.uname=req.data.uname;
                            this.uphoto=req.data.uphoto;
                            this.u_id=req.data.u_id;
                        }
                    }),
                        this.$http.get("http://localhost:8082/muser/findFF?u_id="+this.u_id).then(req=>{
                            if(req.data!=null){
                                this.fans=req.data.fans;
                                this.follows=req.data.follows;
                                this.fanData=req.data.fansList;
                                this.followData=req.data.followsList;
                            }
                        }),
                        this.$http.get("http://localhost:8082/mplink/findRecent?u_id="+this.u_id).then(req=>{
                            if(req.data!=null){
                                this.recentData=req.data;
                            }
                        }),this.$http.get("http://localhost:8082/mplink//findPlaylist?u_id="+this.u_id).then(req=>{
                        if(req.data!=null){
                            this.playList=req.data;
                        }
                    }),this.$http.get("http://localhost:8082/mplink//findLikelist?u_id="+this.u_id).then(req=>{
                        if(req.data!=null){
                            this.likeList=req.data;
                        }
                    })
                    this.dialogFollowVisible=false;
                    this.dialogFanVisible=false;
                }
            },favor () {
                //followFlag=true 说明已经关注
                //followFalg=false 未关注
                if(this.followFlag){  //关注了进行取消关注操作
                    //alert("当前用户："+sessionStorage.getItem("u_id")+" "+"要取消关注："+this.u_id);
                    this.$http.get("http://localhost:8082/muser/noFollow?u_id="+this.u_id+"&fid="+sessionStorage.getItem("u_id")).then(req=>{
                        if (req.data!=''){
                            /*alert(req.data);*/
                            this.$message({
                                showClose: true,
                                message: '取消关注成功！',
                                type: 'success'
                            });
                            this.followFlag=!this.followFlag;
                            this.content="关注";
                            this.but_color='';
                            this.ft_color='';
                            /*重新加载粉丝和关注列表*/
                            this.$http.get("http://localhost:8082/muser/findFF?u_id="+this.u_id).then(req=>{
                                if(req.data!=null){
                                    this.fans=req.data.fans;
                                    this.follows=req.data.follows;
                                    this.fanData=req.data.fansList;
                                    this.followData=req.data.followsList;
                                }
                            })
                        }
                    });
                }
                else{  //没关注进行关注操作
                    //alert("当前用户："+sessionStorage.getItem("u_id")+" "+"要关注："+this.u_id);
                    this.$http.get("http://localhost:8082/muser/follow?u_id="+this.u_id+"&fid="+sessionStorage.getItem("u_id")).then(req=>{
                        if (req.data!=''){
                            /*alert(req.data);*/
                            this.$message({
                                showClose: true,
                                message: '关注成功！',
                                type: 'success'
                            });
                            this.followFlag=!this.followFlag;
                            this.content="已关注";
                            this.but_color='#e24534';
                            this.ft_color='#fff';
                            /*重新加载粉丝和关注列表*/
                            this.$http.get("http://localhost:8082/muser/findFF?u_id="+this.u_id).then(req=>{
                                if(req.data!=null){
                                    this.fans=req.data.fans;
                                    this.follows=req.data.follows;
                                    this.fanData=req.data.fansList;
                                    this.followData=req.data.followsList;
                                }
                            })
                        }
                    });

                }
            },gotoSinger:function (row) {
                //alert(row.singer.tel);
                this.$http.get("http://localhost:8082/muser/findSingerByTel?tel="+row.singer.tel).then(req=>{
                    this.u_id = sessionStorage.setItem("otherId", req.data.u_id);
                    this.reload();
                })
            }
        }
    }
</script>

<style scoped>
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

    .myplaylist{
        margin-top:50px ;
    }
    .mylovelist{
        margin-top: 50px;
    }
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
    /*    推荐歌曲*/
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