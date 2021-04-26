<template>
    <div  style="margin-top:0px;-border: 1px solid ;
background: linear-gradient(to right, #3b8d99, #6b6b83, #aa4b6b); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
">
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
        <div class="mcontent">
            <div class="detail">
                <div class="left">
                    <!-- 封面图形 -->
                    <div class="player_con" :class="{playing:isPlay}">
                        <img src="img/player_bar.png" class="play_bar" />
                        <!-- 黑胶碟片 -->
                        <img src="img/disc.png" class="disc autoRotate" />
                        <img :src="music.mphoto" class="cover autoRotate" />
                    </div>
                    <div>
                        <table>
                            <tr>
                                <td>
                                    <img :src="isLike?'/img/爱心.png' :'/img/爱.png'" alt="" @click="Like()">
                                </td>
                                <td>
                                    <img :src="isStar?'/img/收藏2.png' :'/img/收藏.png'" alt="" @click="star()">
                                </td>
                                <td>
                                    <img src="/img/MV.png" alt="" @click="playMv()" v-show="music.mvurl!=null">
                                </td>
                            </tr>
                        </table>

                    </div>

                </div>

                <div class="right" >
                    <h2 style="margin-top: 20px">{{music.mname}}</h2>
                    <table>
                        <tr>
                            <td @click="goto(music.singer.u_id)"><span >歌手:</span> {{music.singer.uname}}</td>
                            <td @click="goto(music.uploader.u_id)"><span >上传者:</span>{{music.uploader.uname}}</td>
                            <td><span>上传时间:</span> {{music.time}}</td>
                            <td><span>歌曲类型:</span> {{music.mtype.tname}}</td>
                        </tr>
                    </table>
                    <textarea name="" id="" v-html="music.words" readonly style="text-align: center;border: 0">
                        到不了说好的承诺
                        到不了你眼里的彩虹
                        遍体鳞伤的我还追到最后
                    </textarea>

                </div>

            </div>

            <div class="eva">
                <h3>评论</h3>

                <textarea placeholder="发表评论" v-model="speak" @keyup.enter="eva()"></textarea>

                <ul v-show="map!={} && total>0">
                    <li v-for="(list,key) in map" :key="key" >
                        <div style="width: 100%;margin: 10px;height: 100%;" >
                            <div style="width: 10%;text-align: center;"><img :src="list[0].speaker.uphoto" alt="" style="width: 60px;height: 60px"></div>
                            <div style="border: 1px yellowgreen solid;width: 80%;">
                                <span >{{list[0].speaker.uname}}</span>
                                <span style="color: #909399;font-size: 14px">{{list[0].etime}}</span>
                                <p >{{list[0].content}}</p>
                                <el-button  round :disabled="(key-k)!=0 & isfalse"  style="background:rgba(255, 255, 255, 0.2);font-size: 10px;float: right" @click.stop="reva(key)">回复</el-button>
                            </div>
                            <div style="width: 100%" v-show="(key-k)==0 & isfalse" >
                                <textarea placeholder="回复" @keyup.enter="rdis(key)" style="height: 60px;font-size: 16px;" v-model="rspeak"></textarea>
                            </div>
                        </div>


                        <table style="margin-left: 100px;width: 90%;" v-for="e in list" :key="e" >
                            <tr v-if="e.rdisname!=null" >
                                <td style="width: 60px;"><img style="width: 60px;height: 60px;" :src="e.rphoto" alt="" ></td>
                                <td style="border-bottom :1px solid darkgray;background: gainsboro;">
                                    <span >{{e.rdisname}}</span>
                                    <span style="color: #909399;font-size: 14px">{{e.rtime}}</span>
                                    <p >{{e.rcontent}}</p>
                                </td>
                            </tr>

                        </table>
                    </li>

                </ul>
                <el-pagination
                        style="width: 80%;text-align: center"
                        background
                        layout="prev, pager, next"
                        :total="total"
                        :page-size="4"
                        @current-change="handle">
                </el-pagination>


            </div>
            <div class="video_con" v-show="showVideo" style="text-align: center">
                <video ref='video' :src="music.mvurl" controls="controls" style="outline: none;box-shadow: grey 0px 0px 50px"></video>
                <div class="mask" @click="closeMv()"></div>
            </div>

        </div>

        <div class="audio_con">
            <img :src="music.mphoto" alt="" style="width:80px;margin:10px 0;margin-left: 20px;float: left">

            <div style="float: left;-width: 100px; height: 80px;text-align: center;margin:10px 10px;padding: 0;">
                <span style="display: inline-block; height: 20px;font-size: 18px;margin: 0 20px">{{music.mname}}</span>
                <span >
                <img :src="isLike?'/img/爱心.png' :'/img/爱.png'" alt="" width="28px" @click="Like" style="z-index: 1000">
            </span>
                <p style="color: #409EFF;padding: 10px" @click="goto(music.singer.u_id)">{{music.singer.uname}}</p>
            </div>
            <audio ref='audio'  @play="play" @pause="pause" :src="music.murl" controls autoplay loop class="myaudio" ></audio>
        </div>

    </div>
</template>

<script>
    export default {
        name: "play",
        data(){
            return{
                user:{
                    u_id:'',
                    uname:'',
                    uphoto:''
                },//用户
                showVideo:false,
                isfalse:false,
                music:{},
                list:[],
                evaid:[],  //评价的id集合
                /*map:{},*/
                index:1,    //当前页
                total:8,    //总记录数
                isStar:false,
                isLike:false,
                isPlay:false,
                mid:'',       //歌曲id
                u_id:0,      //用户id
                uname:'',    //用户名
                speak:'',
                isReva:false,
                k:0,
                rspeak:''
            }
        },
        methods:{
            closeMv(){
                this.showVideo=false;
                this.$refs.video.pause();
            },
            playMv(){
              this.showVideo=true;
              //alert(this.showVideo);
                // 暂停歌曲播放
                this.$refs.audio.pause();
              this.$forceUpdate();

            },
            page:function (index) {
                this.$http.get("http://localhost:8082/mplink/findEva?mid="+this.mid+"&index="+index).then(req=>{
                    this.map=req.data;    //返回四条评论
                    this.$forceUpdate();
                    console.log(this.map);
                    console.log(this.index);
                });
            },
            handle:function (val) {
                this.index=val;     //当前页
                this.page(val);
            },
            star:function () {
                this.isStar= !this.isStar;
                if (this.isStar==true){
                    this.$http.get("http://localhost:8082/mplink/star?mid="+this.mid+"&u_id="+this.u_id).then();
                    //alert('收藏');
                }else{
                    this.$http.get("http://localhost:8082/mplink/noStar?mid="+this.mid+"&u_id="+this.u_id).then();
                    //alert('取消收藏');
                }
                this.$forceUpdate();
                //alert(this.isStar);
                //alert(this.like)
            },
            Like:function () {
                this.isLike= !this.isLike;
                if (this.isLike==true){
                    this.$http.get("http://localhost:8082/mplink/like?mid="+this.mid+"&u_id="+this.u_id).then();
                    //alert('喜欢');
                }else{
                    this.$http.get("http://localhost:8082/mplink/noLike?mid="+this.mid+"&u_id="+this.u_id).then();
                    //alert('取消喜欢');
                }
            },
            // audio的play事件
            play() {
                this.isPlay = true;
                // 清空mv的信息

            },
            // audio的pause事件
            pause() {
                this.isPlay = false;
            },
            reva(key){
                this.isReva = !this.isReva;
                //alert(this.isReva);
                //alert(e);
                if(this.isReva==true){

                    this.k=key;
                    this.isfalse=true;
                    this.$forceUpdate();

                    alert(this.k);

                }else{
                    this.k=0;
                    this.isfalse=false;

                }


            },
            rdis(eid){
                this.rspeak=this.rspeak.trim();
                if (this.rspeak==''){
                    this.k=0;
                    this.isfalse=false;

                }
                else{
                    this.$http.get("http://localhost:8082/eva/reva",{
                        params:{
                            eid:eid,
                            rdisname:this.uname,
                            rdisid:this.u_id,
                            rcontent:this.rspeak,
                        }
                    }).then(req=>{
                        if(req.data!=''){
                            this.rspeak='';
                            this.$http.get("http://localhost:8082/mplink/findEva?mid="+this.mid+"&index="+this.index).then(req=>{
                                //this.map={};
                                //console.log(this.map);
                                this.map=req.data;
                                this.$forceUpdate();
                                console.log(this.map);
                            });
                            //alert(req.data);
                            this.k=0;
                            this.isfalse=false;
                        }
                    });
                }
            },


            eva(){
                //alert(this.speak);
                this.speak=this.speak.trim();
                if (this.speak==''){
                    //无内容什么都不做
                }
                else{
                    //有内容就做
                    this.$http.get("http://localhost:8082/eva/eva",{
                        params:{
                            disid:this.u_id,
                            disname:this.uname,
                            mid:this.mid,
                            content:this.speak,
                        }
                    }).then(req=>{
                        if(req.data!=''){
                            this.$http.get("http://localhost:8082/mplink/findEva?mid="+this.mid+"&index="+this.index).then(req=>{
                                //this.map={};
                                //console.log(this.map);
                                this.map=req.data;
                                this.$forceUpdate();
                                console.log(this.map);
                                this.speak='';  //清空
                            });
                            this.$http.get("http://localhost:8082/mplink/findEvaNum?mid="+this.mid).then(req=>{
                                this.total= req.data;   //返回总评论数
                            });
                            //alert(req.data);
                        }
                    });
                }


            },
            goto:function (id) {
                sessionStorage.setItem("otherId",id);
                /*this.u_id=row.u_id;
                alert(this.u_id);*/
                //alert(id)
                this.u_id=sessionStorage.getItem("otherId");
                if(this.u_id==sessionStorage.getItem("u_id")){
                    this.$router.push({name:"person"});
                }else{
                    this.$router.push({name:"others"});
                }
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
                //alert("登出");
                this.user='';
                sessionStorage.removeItem("u_id");
                sessionStorage.removeItem("uname");
                sessionStorage.removeItem("uphoto");
            },
            login(){
                this.$router.push({name:"uLogin"});
            }

        },
        created(){
            //接受传过来的id*/
            if(this.$route.params.mid){
                sessionStorage.setItem("mid",this.$route.params.mid);
                this.mid=this.$route.params.mid;
            }else{
                this.mid=sessionStorage.getItem("mid");
            }
            //this.mid=1;
            this.u_id=sessionStorage.getItem("u_id");    //存用户id
            this.uname=sessionStorage.getItem("uname");
            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");

            //alert(this.u_id+this.uname);
            this.$http.get("http://localhost:8082/mplink/addRecent?mid="+this.mid+"&u_id="+this.u_id);   //加入最近播放

            this.$http.get("http://localhost:8082/mplink/findMusicById?mid="+this.mid).then(req=>{
                this.music= req.data;   //返回一个音乐对象

            });
            this.$http.get("http://localhost:8082/mplink/isLike_isStar?mid="+this.mid+"&u_id="+this.u_id).then(req=>{

                this.isLike=req.data.isLike;
                this.isStar=req.data.isStar;

            });
            this.$http.get("http://localhost:8082/mplink/findEvaNum?mid="+this.mid).then(req=>{
                this.total= req.data;   //返回总评论数
                //alert(this.total);
            });
            this.$http.get("http://localhost:8082/mplink/findEva?mid="+this.mid).then(req=>{
                //this.map={};
                //alert(this.map);
                this.map=req.data;
                this.$forceUpdate();
                console.log(this.map);
                //alert(this.map);
            });
            this.$http.get("http://localhost:8082/mplink/playNumUp?mid="+this.mid);

        },


    }
</script>

<style scoped>
    *{
        margin: 0;
        padding: 0;
    }
    .video_con video {
        position: fixed;
        width: 800px;
        height: 546px;
        left: 50%;
        top: 50%;
        margin-top: -273px;
        transform: translateX(-50%);
        z-index: 990;
    }
    .video_con .mask {
        position: fixed;
        width: 100%;
        height: 100%;
        left: 0;
        top: 0;
        z-index: 980;
        background-color: rgba(0, 0, 0, 0.8);
    }

    .reva{
        disabled:true;
        cursor: not-allowed;
        width: 500px;

    }

    .mcontent {
        -border: 1px solid red;
        width: 1200px;
        margin: 0 auto;
        padding-bottom: 200px;
        box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.5), 0 25px 50px 0 rgba(0, 0, 0, 0.5);
        background: rgba(255, 255, 255, 0.8);

    }

    .detail {
        overflow: hidden;
    }

    .detail>div {
        width: 48%;
        float: left;

        height: 435px;
        text-align: center;
        -overflow: hidden;
    }

    .player_con {
        width: 400px;
        height: 400px;
        position: relative;
        -border: 1px solid blue;
        margin: 10px auto;
    }

    .disc {
        position: absolute;
        left: 73px;
        top: 60px;
        z-index: 9;
    }

    .cover {
        position: absolute;
        left: 125px;
        top: 112px;
        width: 150px;
        height: 150px;
        border-radius: 75px;
        z-index: 8;
    }

    /* 旋转的动画 */
    @keyframes Rotate {
        from {
            transform: rotateZ(0);
        }
        to {
            transform: rotateZ(360deg);
        }
    }
    /* 旋转的类名 */
    .autoRotate {
        animation-name: Rotate;
        animation-iteration-count: infinite;
        animation-play-state: paused;
        animation-timing-function: linear;
        animation-duration: 5s;
    }
    /* 是否正在播放 */
    .player_con.playing .disc,
    .player_con.playing .cover {
        animation-play-state: running;
    }

    .play_bar {
        position: absolute;
        left: 200px;
        top: -10px;
        z-index: 10;
        transform: rotate(-25deg);
        transform-origin: 12px 12px;
        transition: 1s;
    }

    /* 播放杆 转回去 */
    .player_con.playing .play_bar {
        transform: rotate(0);
    }
    .detail .right table{
        width: 100%;
        margin: 10px auto;
    }
    .detail .right textarea{
        margin: 10px;
        width: 90%;
        resize: none;
        height: 320px;
        outline: none;
        font-size: 18px;
        line-height: 28px;
        background: transparent;
    }

    .detail{
        margin: 20px;
    }
    .detail .left table{
        position: relative;
        top: -50px;
        width: 60%;
        margin: 0 auto;
    }
    .detail .left table img{
        width: 30px;

    }
    .detail .right td {
        color: #409EFF;
    }
    .detail .right td span{
        color: #909399;
    }
    .eva{
        width:80%;
        -border: 1px solid black;
        margin: 20px;
        text-align: left;
    }
    .eva ul{
        width: 80%;
        margin: 10px;
        list-style: none;

    }
    .eva ul li{
        margin-top: 10px;
        -height: 80px;
        -border: 1px solid black;
        overflow: hidden;
        width: 100%;
    }
    .eva li img{
        width: 50px;
        border-radius: 50%;
    }
    .eva ul li div{
        float: left;
        -border: 1px solid black;
        -padding: 20px;
    }
    .eva li span{
        color: #409EFF;
        -border: 1px solid red;
        -display: block;
        -height: 30px;
        font-size: 18px;
        line-height: 30px;
        margin: 10px;
    }
    .eva ul p{
        font-size: 14px;
        margin: 10px;
    }

    .eva textarea{
        width: 80%;
        height: 150px;
        font-size: 18px;
        line-height: 28px;
        margin: 10px;
        background: transparent;
        box-shadow: #99a9bf 0 0 8px;
    }

    .audio_con {
        -height: 100px;
        width: 100%;
        -text-align: center;
        background-color: #f1f3f4;
        border-bottom-left-radius: 4px;
        border-bottom-right-radius: 4px;
        position: fixed;
        bottom: 0px;
    }
    .myaudio {
        width: 800px;
        height: 40px;
        text-align: center;
        margin-top:20px;
        margin-left: 20px;
        float: left;
        outline: none;
        background-color: #f1f3f4;
    }
    textarea{
        -box-shadow: #99a9bf 0 0 8px;
        resize: none;
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


</style>