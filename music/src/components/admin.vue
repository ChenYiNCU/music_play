<template>
    <el-container class="index-container">
        <!--头部-->
        <el-header>
            <div class="head-logo">
                <!-- <img src="../assets/img/2020-07-17_162648.jpg">-->
                <span>音乐后台管理系统</span>
            </div>
            <div class="head-button"> <el-button type="info" @click="loginOut">退出</el-button></div>

        </el-header>
        <!--页面主体区域-->
        <el-container>
            <!--页面左侧区域-->
            <el-aside width="200px">
                <!--侧边栏菜单区域-->
                <el-menu
                        background-color="#333744"
                        text-color="#fff"
                        active-text-color="#409eff"
                        :router="true"><!--二级菜单颜色,unique-opened菜单只打开一个.:router="true":开启菜单路由模式-->
                    <!--一级菜单-->
                    <el-submenu index="1">
                        <!--一级菜单模板区-->
                        <template slot="title">
                            <!--图标-->
                            <i class="el-icon-user"></i>
                            <!--文本-->
                            <span>用户管理</span>
                        </template>
                        <!--二级菜单-->
                        <el-menu-item index="userList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i><!--二级菜单图标-->
                                <!--文本-->
                                <span>会员列表</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="singerList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i><!--二级菜单图标-->
                                <!--文本-->
                                <span>歌手列表</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="examineList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i><!--二级菜单图标-->
                                <!--文本-->
                                <span>待审核会员列表</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="freezeList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i><!--二级菜单图标-->
                                <!--文本-->
                                <span>冻结会员列表</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>


                <!--侧边栏菜单区域-->
                <el-menu
                        background-color="#333744"
                        text-color="#fff"
                        active-text-color="#409eff"
                        :router="true">
                    <!--一级菜单-->
                    <el-submenu index="1">
                        <!--一级菜单模板区-->
                        <template slot="title">
                            <!--图标-->
                            <i class="el-icon-video-play"></i>
                            <!--文本-->
                            <span>歌曲管理</span>
                        </template>
                        <!--二级菜单-->
                        <el-menu-item index="musicList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i>
                                <!--文本-->
                                <span>歌曲列表</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>

                <!--侧边栏菜单区域-->
                <el-menu
                        background-color="#333744"
                        text-color="#fff"
                        active-text-color="#409eff"
                        :router="true">
                    <!--一级菜单-->
                    <el-submenu index="1">
                        <!--一级菜单模板区-->
                        <template slot="title">
                            <!--图标-->
                            <i class="el-icon-s-help"></i>
                            <!--文本-->
                            <span>类别管理</span>
                        </template>
                        <!--二级菜单-->
                        <el-menu-item index="typeList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i>
                                <!--文本-->
                                <span>类别列表</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>

                <!--侧边栏菜单区域-->
                <el-menu
                        background-color="#333744"
                        text-color="#fff"
                        active-text-color="#409eff"
                        :router="true">
                    <!--一级菜单-->
                    <el-submenu index="1">
                        <!--一级菜单模板区-->
                        <template slot="title">
                            <!--图标-->
                            <i class="el-icon-refresh"></i>
                            <!--文本-->
                            <span>轮播管理</span>
                        </template>
                        <!--二级菜单-->
                        <el-menu-item index="cycleList">
                            <template slot="title">
                                <!--图标-->
                                <i class="el-icon-menu"></i>
                                <!--文本-->
                                <span>轮播列表</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>

            </el-aside>
            <!--页面右侧区域-->
            <el-main>
                <!--路由的占位符-->
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        name: "admin",
        data(){
            return{

            }
        },
        methods:{

        }
    }
</script>

<style scoped>
    .index-container{
        height: 100%;
    }
    .el-header{
        background-color: #373d41;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color:#fff;

    }
    span{
        margin-left: 20px;
    }
    .head-logo{
        display: flex;
        align-items: center;

    }
    .font-left{
        font-size: 20px;
        color: white;
        font-weight: bolder;
        margin-left: -920px;
        margin-top: 10px;
    }
    .head-button{
        margin-left: 800px;
        margin-top: -20px;
    }
    .el-aside{
        background-color: #333744;
    }
    .el-main{
        background-color: #eaedf1;
    }
</style>