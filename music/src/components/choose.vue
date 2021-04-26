<template>
    <div class="choose_bg">
        <ul class="cb-slideshow">
            <li><span>Image 01</span><div></div></li>
            <li><span>Image 02</span><div></div></li>
            <li><span>Image 03</span><div></div></li>
            <li><span>Image 04</span><div></div></li>
            <li><span>Image 05</span><div></div></li>
            <li><span>Image 06</span><div></div></li>
        </ul>

        <div class="choose_main" style="z-index: 1">
            <el-button round @click="aLogin()" class="choose_button">管理员</el-button>
            <el-button round @click="uLogin()" class="choose_button">会员</el-button>
                <p @click="regist()" style="cursor: pointer" class="regist">不是会员?&nbsp;现在去注册→</p>
        </div>

    </div>
</template>

<script>
    export default {
        name: "choose",
        data(){
            return{
            }
        },created(){

        },
        methods:{
            aLogin:function () {
                this.$router.push({name:'aLogin'});
            },
            uLogin:function () {
                this.$router.push({name:'uLogin'});
            },
            regist:function () {
                this.$router.push({name:'regist'});
            }
        }
    }
</script>

<style scoped>
    ul{
      list-style: none;
    }
    .choose_bg{
        margin: 0;
        padding: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    .cb-slideshow,
    .cb-slideshow:after
    {
        position: fixed;
        width: 100%;
        height: 100%;
        top: 0px;
        left: 0px;
        z-index: -1;
    }
    .cb-slideshow:after {
        content: '';
        background: transparent url(../assets/img/pattern.png) repeat top left;
    }
    .cb-slideshow li span {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0px;
        left: 0px;
        color: transparent;
        background-size: cover;
        background-position: 50% 50%;
        background-repeat:no-repeat;
        opacity: 0;
        z-index: 0;
        -webkit-backface-visibility: hidden;
        -webkit-animation: imageAnimation 36s linear infinite 0s;
        -moz-animation: imageAnimation 36s linear infinite 0s;
        -o-animation: imageAnimation 36s linear infinite 0s;
        -ms-animation: imageAnimation 36s linear infinite 0s;
        animation: imageAnimation 36s linear infinite 0s;
    }
    /*.cb-slideshow li div {
        z-index: 1000;
        position: absolute;
        bottom: 30px;
        left: 0px;
        width: 100%;
        text-align: center;
        opacity: 0;
        -webkit-animation: titleAnimation 36s linear infinite 0s;
        -moz-animation: titleAnimation 36s linear infinite 0s;
        -o-animation: titleAnimation 36s linear infinite 0s;
        -ms-animation: titleAnimation 36s linear infinite 0s;
        animation: titleAnimation 36s linear infinite 0s;
    }*/
    /*.cb-slideshow li div h3 {
        font-family: 'BebasNeueRegular', 'Arial Narrow', Arial, sans-serif;
        font-size: 240px;
        padding: 0;
        line-height: 200px;
        color: rgba(169,3,41, 0.8);
    }*/
    .cb-slideshow li:nth-child(1) span { background-image: url(../assets/img/01.jpg) }
    .cb-slideshow li:nth-child(2) span {
        background-image: url(../assets/img/02.jpg);
        -webkit-animation-delay: 6s;
        -moz-animation-delay: 6s;
        -o-animation-delay: 6s;
        -ms-animation-delay: 6s;
        animation-delay: 6s;
    }
    .cb-slideshow li:nth-child(3) span {
        background-image: url(../assets/img/03.jpg);
        -webkit-animation-delay: 12s;
        -moz-animation-delay: 12s;
        -o-animation-delay: 12s;
        -ms-animation-delay: 12s;
        animation-delay: 12s;
    }
    .cb-slideshow li:nth-child(4) span {
        background-image: url(../assets/img/04.jpg);
        -webkit-animation-delay: 18s;
        -moz-animation-delay: 18s;
        -o-animation-delay: 18s;
        -ms-animation-delay: 18s;
        animation-delay: 18s;
    }
    .cb-slideshow li:nth-child(5) span {
        background-image: url(../assets/img/05.jpg);
        -webkit-animation-delay: 24s;
        -moz-animation-delay: 24s;
        -o-animation-delay: 24s;
        -ms-animation-delay: 24s;
        animation-delay: 24s;
    }
    .cb-slideshow li:nth-child(6) span {
        background-image: url(../assets/img/06.jpg);
        -webkit-animation-delay: 30s;
        -moz-animation-delay: 30s;
        -o-animation-delay: 30s;
        -ms-animation-delay: 30s;
        animation-delay: 30s;
    }
    /*.cb-slideshow li:nth-child(2) div {
        -webkit-animation-delay: 6s;
        -moz-animation-delay: 6s;
        -o-animation-delay: 6s;
        -ms-animation-delay: 6s;
        animation-delay: 6s;
    }
    .cb-slideshow li:nth-child(3) div {
        -webkit-animation-delay: 12s;
        -moz-animation-delay: 12s;
        -o-animation-delay: 12s;
        -ms-animation-delay: 12s;
        animation-delay: 12s;
    }
    .cb-slideshow li:nth-child(4) div {
        -webkit-animation-delay: 18s;
        -moz-animation-delay: 18s;
        -o-animation-delay: 18s;
        -ms-animation-delay: 18s;
        animation-delay: 18s;
    }
    .cb-slideshow li:nth-child(5) div {
        -webkit-animation-delay: 24s;
        -moz-animation-delay: 24s;
        -o-animation-delay: 24s;
        -ms-animation-delay: 24s;
        animation-delay: 24s;
    }
    .cb-slideshow li:nth-child(6) div {
        -webkit-animation-delay: 30s;
        -moz-animation-delay: 30s;
        -o-animation-delay: 30s;
        -ms-animation-delay: 30s;
        animation-delay: 30s;
    }*/
    /* Animation for the slideshow images */
    @-webkit-keyframes imageAnimation {
        0% {
            opacity: 0;
            -webkit-animation-timing-function: ease-in;
        }
        8% {
            opacity: 1;
            -webkit-transform: scale(1.05);
            -webkit-animation-timing-function: ease-out;
        }
        17% {
            opacity: 1;
            -webkit-transform: scale(1.1);
        }
        25% {
            opacity: 0;
            -webkit-transform: scale(1.1);
        }
        100% { opacity: 0 }
    }
    @-moz-keyframes imageAnimation {
        0% {
            opacity: 0;
            -moz-animation-timing-function: ease-in;
        }
        8% {
            opacity: 1;
            -moz-transform: scale(1.05);
            -moz-animation-timing-function: ease-out;
        }
        17% {
            opacity: 1;
            -moz-transform: scale(1.1);
        }
        25% {
            opacity: 0;
            -moz-transform: scale(1.1);
        }
        100% { opacity: 0 }
    }
    @-o-keyframes imageAnimation {
        0% {
            opacity: 0;
            -o-animation-timing-function: ease-in;
        }
        8% {
            opacity: 1;
            -o-transform: scale(1.05);
            -o-animation-timing-function: ease-out;
        }
        17% {
            opacity: 1;
            -o-transform: scale(1.1);
        }
        25% {
            opacity: 0;
            -o-transform: scale(1.1);
        }
        100% { opacity: 0 }
    }
    @-ms-keyframes imageAnimation {
        0% {
            opacity: 0;
            -ms-animation-timing-function: ease-in;
        }
        8% {
            opacity: 1;
            -ms-transform: scale(1.05);
            -ms-animation-timing-function: ease-out;
        }
        17% {
            opacity: 1;
            -ms-transform: scale(1.1);
        }
        25% {
            opacity: 0;
            -ms-transform: scale(1.1);
        }
        100% { opacity: 0 }
    }
    @keyframes imageAnimation {
        0% {
            opacity: 0;
            animation-timing-function: ease-in;
        }
        8% {
            opacity: 1;
            transform: scale(1.05);
            animation-timing-function: ease-out;
        }
        17% {
            opacity: 1;
            transform: scale(1.1);
        }
        25% {
            opacity: 0;
            transform: scale(1.1);
        }
        100% { opacity: 0 }
    }
    /* Animation for the title */
    @-webkit-keyframes titleAnimation {
        0% {
            opacity: 0;
            -webkit-transform: translateY(200px);
        }
        8% {
            opacity: 1;
            -webkit-transform: translateY(0px);
        }
        17% {
            opacity: 1;
            -webkit-transform: scale(1);
        }
        19% { opacity: 0 }
        25% {
            opacity: 0;
            -webkit-transform: scale(10);
        }
        100% { opacity: 0 }
    }
    @-moz-keyframes titleAnimation {
        0% {
            opacity: 0;
            -moz-transform: translateY(200px);
        }
        8% {
            opacity: 1;
            -moz-transform: translateY(0px);
        }
        17% {
            opacity: 1;
            -moz-transform: scale(1);
        }
        19% { opacity: 0 }
        25% {
            opacity: 0;
            -moz-transform: scale(10);
        }
        100% { opacity: 0 }
    }
    @-o-keyframes titleAnimation {
        0% {
            opacity: 0;
            -o-transform: translateY(200px);
        }
        8% {
            opacity: 1;
            -o-transform: translateY(0px);
        }
        17% {
            opacity: 1;
            -o-transform: scale(1);
        }
        19% { opacity: 0 }
        25% {
            opacity: 0;
            -o-transform: scale(10);
        }
        100% { opacity: 0 }
    }
    @-ms-keyframes titleAnimation {
        0% {
            opacity: 0;
            -ms-transform: translateY(200px);
        }
        8% {
            opacity: 1;
            -ms-transform: translateY(0px);
        }
        17% {
            opacity: 1;
            -ms-transform: scale(1);
        }
        19% { opacity: 0 }
        25% {
            opacity: 0;
            -webkit-transform: scale(10);
        }
        100% { opacity: 0 }
    }
    @keyframes titleAnimation {
        0% {
            opacity: 0;
            transform: translateY(200px);
        }
        8% {
            opacity: 1;
            transform: translateY(0px);
        }
        17% {
            opacity: 1;
            transform: scale(1);
        }
        19% { opacity: 0 }
        25% {
            opacity: 0;
            transform: scale(10);
        }
        100% { opacity: 0 }
    }

    .choose_main{
        width: 210px;
        margin: 310px auto;
        opacity: 0.9;
    }
    .choose_button{
        background-color: #cd4334;
        border-color: #cd4334;
        color: #fff;
        font-family: '黑体';
        font-weight: bold;
        font-size: 18px;
        border-radius: 50px;
        padding:11px 24px;
    }
    /*.el-button.is-round{
        border-radius: 50px;
        padding:11px 24px;
    }*/
    .choose_button:hover{
        background-color: rgba(255, 255, 255, 0.9);
        border-color: #e24534;
        color: #e24534;
    }
    .regist{
        text-align: center;
        font-size: 16px;
        color: #ec473a;
        text-decoration: underline;
        letter-spacing:3px;
        line-height: 30px;
    }
    /* Show at least something when animations not supported */
    .no-cssanimations .cb-slideshow li span{
        opacity: 1;
    }
    @media screen and (max-width: 1140px) {
        .cb-slideshow li div h3 { font-size: 100px }
    }
    @media screen and (max-width: 600px) {
        .cb-slideshow li div h3 { font-size: 50px }
    }


    /*@keyframes fade-in {
        0% {opacity: 0;}!*初始状态 透明度为0*!
        40% {opacity: 0;}!*过渡状态 透明度为0*!
        100% {opacity: 1;}!*结束状态 透明度为1*!
    }
    @-webkit-keyframes fade-in {!*针对webkit内核*!
        0% {opacity: 0;}
        40% {opacity: 0;}
        100% {opacity: 1;}
    }
    .wrapper {
        animation: fade-in;!*动画名称*!
        animation-duration: 2s;!*动画持续时间*!
        -webkit-animation:fade-in 2s;!*针对webkit内核*!
    }
    .main{
        width: 190px;
        margin: 250px auto;
    }
    .el-button{
        background-color: #e24534;
        color: #fff;
        font-family: '黑体';
        font-size: 16px;
    }
    .el-button.is-round{
        border-radius: 50px;
        padding:8px 23px;
    }
    .el-button:hover{
        background-color: #fff;
        border-color: #e24534;
        color: #e24534;
    }
    .regist{
        text-align: center;
        font-size: 14px;
        color: #b73f32;
        text-decoration: underline;
    }*/

</style>