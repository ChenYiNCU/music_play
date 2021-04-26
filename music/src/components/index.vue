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
                <div @click="upload()" class="el-icon-upload">上传歌曲</div>
                <a @click="out()" class="out">登出</a>
            </template>
            <template v-else>
                <span style="color: #f5f5f5; margin-left: 380px;font-size:14px;">世界应你而美丽，欢迎来到音乐播放器！</span>
                <div @click="upload()" class="el-icon-upload">上传歌曲</div>
                <a @click="login()" class="out">登入</a>
            </template>
        </header>

<!--        轮播图-->
        <el-carousel :interval="4000" type="card" height="250px" >
            <el-carousel-item v-for="item in cycleList" :key="item" class="cycle">
                <el-image :src="item.cpurl"></el-image>
            </el-carousel-item>
        </el-carousel>

<!--        推荐歌曲（按点赞前10）-->
        <div class="contaner">
            <div class="title">
                <div class="line"></div>
                <span>Popular&nbsp;Songs</span>
                <span @click="moreM()">更多</span><br>
                <span>推荐歌曲</span>
            </div>

            <el-row :gutter="20">
                <el-col class="cursor" :span="4" v-for="item in popularList" :key="item">
                    <el-card shadow="hover" :body-style="{padding: '0'}">
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

<!--        独家放送（按收藏前三歌曲mv）-->
            <div class="title">
                <div class="line"></div>
                <span>Best&nbsp;Mv</span>
                <span @click="moreM()">更多</span><br>
                <span>独家放送</span>
            </div>
            <el-row :gutter="20">
                <el-col :span="8" v-for="item in storeMvList" :key="item">
                    <div>
                        <video :src="item.mvurl" controls :poster="item.mphoto"></video>
                        <p style="font-size: 20px;font-family: '宋体';text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">{{item.mname}}</p>
                    </div>
                </el-col>
            </el-row>



<!--        最新音乐（按最新发布4行3列 12首）-->
            <div class="title">
                <div class="line"></div>
                <span>Latest&nbsp;Music</span>
                <span @click="moreM()">更多</span><br>
                <span>最新音乐</span>
            </div>

            <el-row :gutter="40">
                <el-col class="latest" :span="8" v-for="item in latestList" :key="item" >
                        <img style="width:55px;height: 55px;border-radius: 2px;" :src="item.music.mphoto">
                        <img v-if="playId==item.music.mid" @click="playNow(item.music.mid)" :src="start" style="width:30px; height: 30px; position:relative;left: -42.5px;top: 15px;">
                        <img v-else @click="playNow(item.music.mid)" :src="stop" style="width:30px; height: 30px; position:relative;left: -42.5px;top: 15px;">
                        <div style="display: inline-block;width: 100%">
                            <span @click="toPlay(item.music.mid)" style="cursor: pointer;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">{{item.music.mname}}</span><br>
                            <span @click="goto(item.music.singer.u_id)" style="cursor: pointer; color: #9F9F9F;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">{{item.music.singer.uname}}</span>
                        </div>
                    <!-- 看一下before有没有mv-->
                </el-col>
            </el-row>


<!--        榜单（第一列最受关注歌手榜  第二列歌曲热门榜 按播放数 第三列收藏榜）-->
            <div class="title">
                <div class="line"></div>
                <span>League&nbsp;Tables</span>
                <span>更多</span><br>
                <span>音乐榜单</span>
            </div>

            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="最受关注歌手榜" name="first">
                    <el-table
                            :data="singerList"
                            stripe
                            style="width: 100%">
                        <el-table-column
                                prop="uphoto"
                                label="歌手头像"
                                width="180">
                            <template slot-scope="scope">
                                <img @click="goto(scope.row.u_id)" style="width: 55px;height: 55px; cursor: pointer;" :src="scope.row.uphoto" />
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="uname"
                                label="歌手名"
                                width="280">
                            <template slot-scope="scope">
                                <span @click="goto(scope.row.u_id)" style="cursor: pointer;">{{scope.row.uname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="sex"
                                label="性别"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="fansNum"
                                label="粉丝数">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>

                <el-tab-pane label="热门歌曲" name="second">
                    <el-table
                            :data="hotList"
                            stripe
                            style="width: 100%;">
                        <el-table-column
                                prop="music.mphoto"
                                label="歌曲封面"
                                width="180">
                            <template slot-scope="scope">
                                <img style="width: 55px;height: 55px;" :src="scope.row.music.mphoto" />
                                <img v-if="playId==scope.row.music.mid" @click="playNow(scope.row.music.mid)" :src="start" style="cursor: pointer; width:30px; height: 30px; position:relative;left: -42.5px;top: -13px;">
                                <img v-else @click="playNow(scope.row.music.mid)" :src="stop" style="cursor: pointer; width:30px; height: 30px; position:relative;left: -42.5px;top: -13px;">
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="music.mname"
                                label="歌曲名"
                                width="280">
                            <template slot-scope="scope">
                                <span @click="toPlay(scope.row.music.mid)" style="cursor: pointer;">{{scope.row.music.mname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="music.singer.uname"
                                label="歌手名"
                                width="180">
                            <template slot-scope="scope">
                                <span @click="goto(scope.row.music.singer.u_id)" style="cursor: pointer;">{{scope.row.music.singer.uname}}</span>
                            </template>
                        </el-table-column>

                        <el-table-column
                                prop="music.likenum"
                                label="喜爱数"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="music.playnum"
                                label="播放数"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="music.storenum"
                                label="收藏数">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="歌曲收藏榜" name="third">
                    <el-table
                            :data="storeList"
                            stripe
                            style="width: 100%;">
                        <el-table-column
                                prop="music.mphoto"
                                label="歌曲封面"
                                width="180">
                            <template slot-scope="scope">
                                <img style="width: 55px;height: 55px;" :src="scope.row.music.mphoto" />
                                <img v-if="playId==scope.row.music.mid" @click="playNow(scope.row.music.mid)" :src="start" style="cursor: pointer; width:30px; height: 30px; position:relative;left: -42.5px;top: -13px;">
                                <img v-else @click="playNow(scope.row.music.mid)" :src="stop" style="cursor: pointer; width:30px; height: 30px; position:relative;left: -42.5px;top: -13px;">
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="music.mname"
                                label="歌曲名"
                                width="280">
                            <template slot-scope="scope">
                                <span @click="toPlay(scope.row.music.mid)" style="cursor: pointer;">{{scope.row.music.mname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="music.singer.uname"
                                label="歌手名"
                                width="180">
                            <template slot-scope="scope">
                                <span @click="goto(scope.row.music.singer.u_id)" style="cursor: pointer;">{{scope.row.music.singer.uname}}</span>
                            </template>
                        </el-table-column>

                        <el-table-column
                                prop="music.likenum"
                                label="喜爱数"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="music.playnum"
                                label="播放数"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="music.storenum"
                                label="收藏数">
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>

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
    export default {
        name: "index",
        data(){
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
                search:'',
                isLike:false,
                cycleList:[],//轮播图
                popularList:[],//推荐歌曲
                latestList:[],//最新歌曲
                hotList:[],//热门歌曲
                storeMvList:[],//收藏MV
                activeName: 'second',
                singerList:[],//歌手榜
                storeList:[],//收藏榜
                stop:'img/播放.png',
                start:'img/正在播放.png',
                playId:'',//正在播放id
                isPlay:false,
            }
        },
        created(){
            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");
            //轮播图
            this.$http.get("http://localhost:8082/cyclephoto/findAll").then(req=>{this.cycleList=req.data;});
            //按喜欢查找
            this.$http.get("http://localhost:8082/mplink/findPage?flag=2").then(req=>{this.popularList=req.data.list;});
            //按最新查找
            this.$http.get("http://localhost:8082/mplink/findPage?flag=5").then(req=>{this.latestList=req.data.list;});
            //按收藏查找mv
            this.$http.get("http://localhost:8082/mplink/findPage?size=3&flag=4").then(req=>{this.storeMvList=req.data.list;});
            //查找最受欢迎歌手
            this.$http.get("http://localhost:8082/muser/findPopSingerPage").then(req=>{this.singerList=req.data.list});
            //按播放数量查找歌曲
            this.$http.get("http://localhost:8082/mplink/findPage?size=6&flag=6").then(req=>{this.hotList=req.data.list;});
            //按收藏查歌曲
            this.$http.get("http://localhost:8082/mplink/findPage?size=6&flag=3").then(req=>{this.storeList=req.data.list;});

        },
        methods:{
            Like:function (id) {
                this.isLike= !this.isLike;
                if (this.isLike==true){
                    this.$http.get("http://localhost:8082/mplink/like?mid="+id+"&u_id="+this.user.u_id).then();
                }else{
                    this.$http.get("http://localhost:8082/mplink/noLike?mid="+id+"&u_id="+this.user.u_id).then();
                }
            },
            index(){
                this.$router.push({name:"index"});
            },
            toPlay(id){
                this.$router.push({name:"play",params:{mid:id}});
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
            searchBtn(){
                this.$router.push({name:"search",params:{search:this.search}});
            },
            person(){
                this.$router.push({name:"person"});
            },
            upload(){
                this.$router.push({name:"upload"});
            },
            out(){
                //alert("登出");
                this.user='';
                sessionStorage.removeItem("u_id");
                sessionStorage.removeItem("uname");
                sessionStorage.removeItem("uphoto");
            },
            moreM(){
                this.$router.push({name:"search"});
            },
            goto:function (id) {
                sessionStorage.setItem("otherId",id);
                /*this.u_id=row.u_id;
                alert(this.u_id);*/
                this.u_id=sessionStorage.getItem("otherId");
                if(this.u_id==sessionStorage.getItem("u_id")){
                    this.$router.push({name:"person"});
                }else{
                    this.$router.push({name:"others"});
                }
            },
            handleClick(tab, event) {
                console.log(tab, event);
            },
            login(){
                this.$router.push({name:"uLogin"});
            }
        }
    }
</script>

<style scoped>
/*<!--    导航栏-->*/
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
        margin-bottom: 35px;
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


    /*轮播图*/
    .cycle{
        border-radius: 14px;
    }

    /* 容器*/
    .contaner{
        width: 1100px;
        margin: 0 auto;
    }
    .title{
        margin-top: 45px;
    }
    .line{
        width: 60px;
        height: 3px;
        margin-top: 12px;
        background-color:brown;
        display: flex;
        float: left;
        margin-right:5px;
    }
    .title span:nth-of-type(1){
        font-size: 18px;
        margin-top: -5px;
        margin-bottom: 12px;
    }
    .title span:nth-of-type(2){
        float: right;
        cursor: pointer;
    }
    .title span:nth-of-type(2)::after{
        content: '';
        display: inline-block;
        width: 0;
        height: 0;
        border: 8px solid transparent;
        border-left: 8px solid #000;
        position: relative;
        top: 2px;
        left: 6px;
    }

    .title span:nth-of-type(3){
        font-family: '宋体';
        font-size: 22px;
        font-weight: 400;
        line-height: 75px;
    }

    /*    推荐歌曲*/
    .el-row{
        margin-bottom: 70px;
    }
    .el-col{
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

    /*    视频*/
    video{
        width:100%;
        border-radius: 10px;
        background-color: black;
    }

    /*    最新音乐*/
    .latest{
        display: flex;
        flex-flow: row;
        font-size: 16px;
        font-family: '宋体';
        line-height: 27px;
    }

    .latest:hover{
        background-color: #EAEAEA;
    }
    .latest img:hover{
        cursor: pointer;
    }

    footer{
        width: 100%;
        height: 180px;
        background-color:#edeeee;
        font-family: '宋体';
        position: relative;
        margin-top: 200px;
        padding-top:20px;
        text-align: center;
        z-index: -1000;
    }
</style>