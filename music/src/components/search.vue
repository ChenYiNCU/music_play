<template>
    <div>
        <header>
            <img class="logo" src="../assets/logo.png" >
            <span @click="toIndex()" class="name">音乐播放器</span>
            <form>
                <input class="search" type="text" v-model="search" name="" id="" value=""/>
            </form>
            <div @click="searchBtn()" class="el-icon-search"></div>
            <template style="background-color:#000;" v-if="user.u_id!=null&&user.u_id!=''">
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


        <div class="container">
            <!--        所有分类-->
            <div class="banner">
                <div style="font-family: '宋体';">———所有分类———</div>
                <br>
                <span @click="searchType('')" class="typeName">全部</span>
                <span v-for="item in typeList" :key="item">
                        <span @click="searchType(item.t_id)" class="typeName">{{item.tname}}</span>
                    </span>
            </div>

            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="歌手" name="first">
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
                                label="性别">
                        </el-table-column>
                    </el-table>
                    <el-pagination @size-change="handleSizeChangeS" @current-change="handleCurrentChangeS" :current-page="indexS"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="sizeS" layout="total, sizes, prev, pager, next, jumper" :total="countS" v-if="pageVisibleS1">
                    </el-pagination>

                    <el-pagination @size-change="handleSizeChangeS1" @current-change="handleCurrentChangeS1" :current-page="indexS1"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="sizeS1" layout="total, sizes, prev, pager, next, jumper" :total="countS" v-if="pageVisibleS2">
                    </el-pagination>
                </el-tab-pane>

                <el-tab-pane label="歌曲" name="second">
                    <el-table
                            :data="musicList"
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
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="index"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible1">
                    </el-pagination>

                    <el-pagination @size-change="handleSizeChange1" @current-change="handleCurrentChange1" :current-page="index1"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="size1" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible2">
                    </el-pagination>
                </el-tab-pane>

                <el-tab-pane label="用户" name="third">
                    <el-table
                            :data="userList"
                            stripe
                            style="width: 100%">
                        <el-table-column
                                prop="uphoto"
                                label="用户头像"
                                width="180">
                            <template slot-scope="scope">
                                <img @click="goto(scope.row.u_id)" style="width: 55px;height: 55px; cursor: pointer;" :src="scope.row.uphoto" />
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="uname"
                                label="用户名"
                                width="280">
                            <template slot-scope="scope">
                                <span @click="goto(scope.row.u_id)" style="cursor: pointer;">{{scope.row.uname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="sex"
                                label="性别">
                        </el-table-column>
                    </el-table>
                    <el-pagination @size-change="handleSizeChangeU" @current-change="handleCurrentChangeU" :current-page="indexU"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="sizeU" layout="total, sizes, prev, pager, next, jumper" :total="countU" v-if="pageVisibleU1">
                    </el-pagination>

                    <el-pagination @size-change="handleSizeChangeU1" @current-change="handleCurrentChangeU1" :current-page="indexU1"
                                   :page-sizes="[10, 20, 30, 40]" :page-size="sizeU1" layout="total, sizes, prev, pager, next, jumper" :total="countU" v-if="pageVisibleU2">
                    </el-pagination>
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
        name: "search",
        data(){
            return{
                //用户
                user:{
                    u_id:'',
                    uname:'',
                    uphoto:'',
                },
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
                search:'',
                activeName: 'second',
                typeList:[],
                singerList:[],
                musicList:[],
                userList:[],
                stop:'img/播放.png',
                start:'img/正在播放.png',
                playId:'',//正在播放id
                t_id:'',//type的ID

                //分页
                index:1,
                size:10,
                index1:1,
                size1:10,
                count:'',
                pageVisible1: true,
                pageVisible2: false,

                //分页2
                indexS:1,
                sizeS:10,
                indexS1:1,
                sizeS1:10,
                countS:'',
                pageVisibleS1: true,
                pageVisibleS2: false,

                //分页3
                indexU:1,
                sizeU:10,
                indexU1:1,
                sizeU1:10,
                countU:'',
                pageVisibleU1: true,
                pageVisibleU2: false,
            }
        },
        created(){
            this.search=this.$route.params.search||'';

            this.user.u_id=sessionStorage.getItem("u_id");
            this.user.uname=sessionStorage.getItem("uname");
            this.user.uphoto=sessionStorage.getItem("uphoto");
            if(this.search==''){
                this.$http.get("http://localhost:8082/mplink/findPage?flag=1&size="+this.size).then(req=>{
                    this.musicList=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                });
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag=2&size="+this.sizeS).then(req=>{
                    this.singerList=req.data.list;
                    this.indexS=req.data.index;
                    this.countS=req.data.count;
                });
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag=1&size="+this.sizeU).then(req=>{
                    this.userList=req.data.list;
                    this.indexU=req.data.index;
                    this.countU=req.data.count;
                });
            }else{
                this.searchBtn();
            }
            this.$http.get("http://localhost:8082/mtype/findAll").then(req=>{this.typeList=req.data;});
        },
        methods:{
            toIndex(){
                this.$router.push({name:"index"});
            },
            Like:function (id) {
                this.isLike= !this.isLike;
                if (this.isLike==true){
                    this.$http.get("http://localhost:8082/mplink/like?mid="+id+"&u_id="+this.user.u_id).then();
                }else{
                    this.$http.get("http://localhost:8082/mplink/noLike?mid="+id+"&u_id="+this.user.u_id).then();
                }
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
            person(){
                this.$router.push({name:"person"});
            },
            handleClick(tab, event) {
                console.log(tab, event);
            },
            login(){
                this.$router.push({name:"uLogin"});
            },
            searchType(id){
                this.t_id=id;
                this.$http.get(`http://localhost:8082/mplink/findSearchPage?index${this.index1}&size=${this.size1}&t_id=${this.t_id}&search=${this.search}`).then(req=>{
                    this.musicList=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                });
            },

            /**
             * 歌分页
             * @param size
             */

            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange(size){
                this.size = size;        //将每页显示的条数 = 改变的值
                console.log(this.size) //每页下拉显示数据
            },
            handleCurrentChange(index) {
                this.index = index;        //然后将当前页 = 改变的值
                this.$http.get(`http://localhost:8082/mplink/findPage?flag=1&index=${index}&size=${this.size}`).then(req=>{
                    this.musicList=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index)        //点击第几页
            },

            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange1(size){
                this.size1 = size;        //将每页显示的条数 = 改变的值
                console.log(this.size1) //每页下拉显示数据
            },
            handleCurrentChange1(index) {
                this.index1 = index;        //然后将当前页 = 改变的值
                this.$http.get(`http://localhost:8082/mplink/findPage?flag=1?&index=${index}&size=${this.size1}&t_id=${this.t_id}&search=${this.search}`).then(req=>{
                    this.musicList=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index1)        //点击第几页
            },

            /**
             * 歌手分页
             * @param size
             */
            handleSizeChangeS(size){
                this.sizeS = size;
            },
            handleCurrentChangeS(index) {
                this.indexS = index;
                this.$http.get(`http://localhost:8082/muser/findPage?flag=2&index=${index}&size=${this.sizeS}`).then(req=>{
                    this.singerList=req.data.list;
                    this.indexS=req.data.index;
                    this.countS=req.data.count;
                })
            },
            handleSizeChangeS1(size){
                this.sizeS1 = size;
            },
            handleCurrentChangeS1(index) {
                this.indexS1 = index;
                this.$http.get(`http://localhost:8082/muser/findSearchPage?flag=2?&index=${index}&size=${this.sizeS1}&search=${this.search}`).then(req=>{
                    this.singerList=req.data.list;
                    this.indexS1=req.data.index;
                    this.countS=req.data.count;
                })
            },

            /**
             * 用户分页
             * @param size
             */
            handleSizeChangeU(size){
                this.sizeU = size;
            },
            handleCurrentChangeU(index) {
                this.indexU = index;
                this.$http.get(`http://localhost:8082/muser/findPage?flag=1&index=${index}&size=${this.sizeU}`).then(req=>{
                    this.userList=req.data.list;
                    this.indexU=req.data.index;
                    this.countU=req.data.count;
                })
            },

            handleSizeChangeU1(size){
                this.sizeU1 = size;
            },
            handleCurrentChangeU1(index) {
                this.indexU1 = index;
                this.$http.get(`http://localhost:8082/muser/findSearchPage?flag=2?&index=${index}&size=${this.sizeU1}&search=${this.search}`).then(req=>{
                    this.userList=req.data.list;
                    this.indexU1=req.data.index;
                    this.countU=req.data.count;
                })
            },

            /**
             * 搜索
             */
            searchBtn:function(){
                this.t_id='';
                if(this.search=='' || this.search==null){
                    this.pageVisible1=true;
                    this.pageVisible2=false;
                    this.pageVisibleS1=true;
                    this.pageVisibleS2=false;
                    this.pageVisibleU1=true;
                    this.pageVisibleU2=false;
                }else{
                    this.pageVisible2=true;
                    this.pageVisible1=false;
                    this.pageVisibleS2=true;
                    this.pageVisibleS1=false;
                    this.pageVisibleU2=true;
                    this.pageVisibleU1=false;
                }
                this.$http.get(`http://localhost:8082/muser/findSearchPage?flag=2&index${this.indexS1}&size=${this.sizeS1}&search=${this.search}`).then(req=>{
                    this.singerList=req.data.list;
                    this.indexS1=req.data.index;
                    this.countS=req.data.count;
                });
                this.$http.get(`http://localhost:8082/muser/findSearchPage?flag=1&index${this.indexS1}&size=${this.sizeU1}&search=${this.search}`).then(req=>{
                    this.userList=req.data.list;
                    this.indexU1=req.data.index;
                    this.countU=req.data.count;
                });
                this.$http.get(`http://localhost:8082/mplink/findSearchPage?index${this.index1}&size=${this.size1}&t_id=${this.t_id}&search=${this.search}`).then(req=>{
                    this.musicList=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                });
            },
        }
    }
</script>

<style scoped>
    /*<!--    导航栏-->*/
    body{
        margin: 0;
        padding: 0;
    }
    ul{list-style: none;}
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
        cursor: pointer;
        font-size: 20px;
        font-family: '宋体';
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

    /*    容器*/
    .container{
        width: 1100px;
        margin: 0 auto;
    }

    /*    banner*/
    .banner{
        margin-bottom: 60px;
    }
    .typeName{
        cursor: pointer;
        padding:8px;
        margin: 0 10px;
        font-size: 17px;
        font-family:'宋体';
    }

    .typeName:active{
        background-color: #f1c9cf;
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
        z-index: -10000;
    }

</style>