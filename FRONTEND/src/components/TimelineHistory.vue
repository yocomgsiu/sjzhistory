<template>
  <el-container class="home_container">
    <el-header>
      <div class="top_bar">时间轴历史</div>
      <div class="demonstration">
        <el-date-picker v-model="timeKey" size="small" type="date" placeholder="定位时间" format="yyyy 年 MM 月 dd 日"
          value-format="timestamp" :picker-options="pickerOptions"></el-date-picker>
        <el-input size="small" placeholder="可输入关键词" v-model="keyWord" clearable>
        </el-input>
        <el-button size="small" icon="el-icon-search" @click="getArticle('new')" circle></el-button>
      </div>
      <div>
        <el-dropdown @command="dropCommand" style="margin-right: 10px;">
          <span class="el-dropdown-link ">
            <i class="el-icon-present "></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <!-- <el-dropdown-item command="sysMsg">登录</el-dropdown-item> -->
            <el-dropdown-item command="record" divided>撰写文章</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>

    <div>
      <!-- <div>
        <el-button v-if="false === firstPageFlag" class="load-more-top" size="small" @click="getArticle('asc')">加载更多</el-button>
      </div> -->
      <el-timeline>
        <el-timeline-item v-for="(tl, index) in timelineList" :key="index" placement="top"
          :timestamp="tl.eventTime | formatDate" :color="tl.accuracy">
          <el-card>
            <el-container style="height: 200px; ">

              <el-aside width="500px">
                <h3 class="article_title">{{tl.title}}</h3>
                <p>{{tl.content}}</p>
              </el-aside>

              <el-aside>
                <div class="demo-image">
                  <el-image style="width: 210px; height: 200px" :fit="'contain'" :src="tl.pic1Url + '?x-oss-process=style/sjzxt'">
                  </el-image>
                </div>
              </el-aside>

            </el-container>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <div v-if="false === lastPageFlag">
        <el-button class="load-more" size="small" @click="getArticle('desc')">加载更多</el-button>
      </div>
    </div>

  </el-container>
</template>


<script>
  import {
    getRequestH
  } from '../utils/api'
  
  export default {

    mounted: function () {
      var _this = this;
      getRequestH("/api/article/timeline", {
        keyWord: '',
        timeKey: 0,
        page: _this.pageNumber,
        size: 30,
        sort: 'eventTime,DESC'
      }).then(function (resp) {
        var contents = resp.data.data.content;
        for (var i = 0; i < resp.data.data.content.length; i++) {
          _this.timelineList.push(resp.data.data.content[i])
        }
        _this.firstPageFlag = resp.data.data.first;
        _this.lastPageFlag = resp.data.data.last;
      }, function (resp) {

      });

    },

    methods: {

      getArticle(way) {
        var _this = this;
        if ("new" === way) {
          _this.pageNumber = 0;
        } else if ("desc" === way) {
          _this.pageNumber++;
        } else if ("asc" === way) {
          _this.pageNumber = (_this.pageNumber === 0) ? 0 : _this.pageNumber--;
        }
        _this.timeKey = (_this.timeKey === null || _this.timeKey === '') ? 0 : _this.timeKey;
        getRequestH("/api/article/timeline", {
          keyWord: _this.keyWord,
          timeKey: _this.timeKey,
          page: _this.pageNumber,
          size: 30,
          sort: 'eventTime,DESC'

        }).then(resp => {
          _this.loading = false;
          if (resp.status == 200) {
            if ("new" === way) {
              _this.timelineList = [];
            }
            for (var i = 0; i < resp.data.data.content.length; i++) {
              _this.timelineList.push(resp.data.data.content[i])
            }
            _this.firstPageFlag = resp.data.data.first;
            _this.lastPageFlag = resp.data.data.last;
          }
        }, resp => {

        })
      },

      dropCommand(command) {
        var _this = this;
        _this.$router.replace({
          path: '/recordArticle'
        });
      }

    },

    data() {
      return {
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        firstPageFlag: '',
        lastPageFlag: '',
        timeKey: '',
        keyWord: '',
        timelineList: [],
        pageNumber: 0

      };
    }
  };

</script>



<style>
  .el-input {
    width: 180px;
  }

  .el-date-editor.el-input,
  .el-date-editor.el-input__inner {
    width: 180px;
  }

  .home_container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .el-header {
    background-color: #fafafa;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 50px;
    position: fixed;
    width: 100%;
    z-index: 100;
    box-shadow: 0px 0px 10px #d1d1d1;
  }

  .el-aside {
    text-align: left;
  }

  .top_bar {
    color: rgb(128, 128, 128);
    font-size: 20px;
    display: inline;
  }

  .el-timeline {
    padding-top: 70px;
    /* padding-top: 10px;  */
  }

  .el-timeline-item__timestamp {
    color: #646464;
    line-height: 1;
    font-size: 14px;
    text-align: left;
  }
  .article_title {
    margin-top: 12px;
  }

  .demo-image {
    text-align: center;
    display: block;
    padding-left: 5px;
  }

  .el-image {
    overflow: initial;
  }

  .el-card__body {
    width: 800px;
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .el-timeline-item__content {
    width: 809px;
  }

  .load-more {
    width: 180px;
  }

  .load-more-top {
    width: 180px;
    margin-top: 70px;
  }

</style>
