<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>动态统计</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!--    <script type="text/javascript" src="https://cdn.bootcss.com/echarts/3.8.4/echarts.js"></script> -->
    
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts-wordcloud.min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.fullPage.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.fullPage.js"></script>
    <script>
        $(function(){
            $('#dowebok').fullpage({
                sectionsColor:['#FFF0AC','#FFF0AC','#FFFFB9','#FFFFB9','#FFFFB9','#FFFFB9','#E2C2DE']
            });
        });
    </script>
    <style>
        #head_pic{
            width: 168px;
            height: 168px;
        }
        a{
        text-decoration: none;
        color: black;
        }
    </style>
  </head>
  
  <body>
<div id="dowebok">

    <div class="section" id="base_info">
        <div align="center" >
            <img id="head_pic" src="${user.headPic}">
            <p>您好！&nbsp;<strong style="color:#FA8072">${user.nickname}</strong></p>
            <p>截止至&nbsp;<b style="color:#FA8072">2018-11-05</b></p>
            <p>您在简书关注了<b style="color:#FA8072">${user.followingNum}</b>个用户，
                拥有粉丝<b style="color:#FA8072">${user.followersNum}</b>个。</p>
            <p>发表文章<b style="color:#FA8072">${user.articlesNum}</b>篇，
                写下文字<b style="color:#FA8072">${user.wordsNum}</b>个，
                文章收获喜欢<b style="color:#FA8072">${user.beLikedNum}</b>个,
                喜欢文章<b style="color:#FA8072">${xhwzCount}</b>篇</p>
            <p>关注专题<b style="color:#FA8072">${gzztCount}</b>个，
                关注文集<b style="color:#FA8072">${gzwjCount}</b>个。</p>
            <p>发表评论<b style="color:#FA8072">${plCount}</b>次，
                点赞别人评论<b style="color:#FA8072">${dzCount}</b>次。</p>
            <p>打赏文章<b style="color:#FA8072">${dsCount}</b>次</p>
        </div>
    </div>

    <div class="section" id="firt_tag_time">
        <div align="center" >
            <h3>加入简书以来的第一次</h3><br><br>
            <p>您于<b style="color:#FA8072">${user.joinTime}</b> 注册，加入简书</p>
            <c:if test="${ not empty likeUsers.time}">
            <p>
                <b style="color:#FA8072">${likeUsers.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/${likeUsers.slug}" target="_blank">第一次关注用户</a>
            </p>
            </c:if>
            <c:if test="${ not empty shareNote.time}">
            <p>
                <b style="color:#FA8072">${shareNote.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/p/${shareNote.noteId}" target="_blank">第一次发表文章</a>
            </p>
            <c:if test="${ not empty likeNotes.time}">
            </c:if>
            <p>
                <b style="color:#FA8072">${likeNotes.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/p/${likeNotes.noteId}" target="_blank">第一次喜欢文章:${likeNotes.commentText}</a>
            </p>
            </c:if>
            <c:if test="${ not empty likeColls.time}">
            <p>
                <b style="color:#FA8072">${likeColls.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/${likeColls.collId}" target="_blank">第一次关注专题</a>
            </p>
            </c:if>
            <c:if test="${ not empty likeNotebooks.time}">
            <p>
                <b style="color:#FA8072">${likeNotebooks.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/${likeNotebooks.notebookId}" target="_blank">第一次关注文集</a>
            </p>
            </c:if>
            <c:if test="${ not empty commentNotes.time}">
            <p>
                <b style="color:#FA8072">${commentNotes.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/p/${commentNotes.noteId}" target="_blank">第一次发表评论:${commentNotes.commentText}</a>
            </p>
            </c:if>
            <c:if test="${ not empty likeComment.time}">
            <p>
                <b style="color:#FA8072">${likeComment.time}</b>
                &nbsp;第一次赞了评论:
                ${likeComment.commentContent}
            </p>
            </c:if>
            <c:if test="${ not empty rewardNotes.time}">
            <p>
                <b style="color:#FA8072">${rewardNotes.time}</b>
                &nbsp;
                <a href="http://www.jianshu.com/${rewardNotes.noteId}" target="_blank">第一次打赏文章</a>
            </p>
            </c:if>
        </div>
    </div>
    
   <!-- 01.用户动态类型 --> 
   <div class="section" id="tags_percent">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="tags_chart" style="width:100%;height:600px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('tags_chart'));

            // 指定图表的配置项和数据

            var option = {
                title : {
                    text: '01.用户动态类型',
                    subtext: '数据来源: www.jianshu.com',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                clockwise:false,
                legend: {
                    orient: 'vertical',
                    left: '10%',
                    data: ['发表评论','关注专题','赞赏文章','喜欢文章','发表文章','关注用户','关注文集']

//                    data: [,'点赞评论']
                },
                color:['#FF6666','#EFE42A','#64BD3D','#EE9201','#29AAE3','#B74AE5','#0AAF9F'],
                series : [
                    {
                        name: '动态类型',
                        type: 'pie',
                        radius : '75%',
                        center: ['50%', '60%'],
                        data:${jsonCateNum},
            itemStyle: {
                emphasis: {
                    shadowBlur: 100,
                            shadowOffsetX: 10,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
            }
            ]
            };

            //*必须，绑定图表自适应功能
            window.onresize = function () {
                myChart.resize();
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        </script>
    </div>


   <!-- 02每月动态次数 -->
   <div class="section" id="all_month">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="monthline_chart" style="width:100%;height:600px;background-color: gray;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart1 = echarts.init(document.getElementById('monthline_chart'));

            // 指定图表的配置项和数据

            var option = {

                    // Make gradient line here
                    visualMap: {
                        show: false,
                        type: 'continuous',
                        seriesIndex: 0,
                        color:['red','orange','yellow','lightskyblue']
                    },

                    title: {
                        left: 'center',
                        text: '02.每月动态次数',
                        subtext:'数据来源: www.jianshu.com'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                    	data: ${months},
                name:'月份'
                },
                yAxis: {
                    splitLine: {show: false},
                    name:'动态次数'
                },
                grid: {
                    bottom: '6%',
                            top: '10%'
                },
                series: {
                    type: 'line',
                            showSymbol: false,
                            data: ${monthsfreq},
                            //data:["4","2","3","4","4"],
                    markPoint : {
                        data : [
                            {type : 'max',
                                name: '最大值'
                            }
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值',
                                label: {
                                    normal: {
                                        position: 'end',
                                        formatter: '月平均值:{c}'
                                    }
                                }},
                            {type: 'max', name: '最大值',
                                label: {
                                    normal: {
                                        position: 'end',
                                        formatter: '最大值'
                                    }
                                }}
                        ]
                    }
                }
                };
            //*必须，绑定图表自适应功能
            window.onresize = function () {
                myChart1.resize();
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option);
        </script>
    </div>
    
    <!-- 03.每天动态次数 -->
    <div class="section" id="all_day">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="dayline_chart" style="width:100%;height:600px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart2 = echarts.init(document.getElementById('dayline_chart'));

            // 指定图表的配置项和数据

            var option = {

                // Make gradient line here
                visualMap: {
                    show: false,
                    type: 'continuous',
                    seriesIndex: 0,
                    color:['red','orange','yellow','lightskyblue']
                },

                title: {
                    left: 'center',
                    text: '03.每天的动态次数(页内滚动鼠标或拖动下方进度条，可缩放数据)',
                    subtext:'数据来源: www.jianshu.com'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    data: ${days},
            name:'日期'
            },
            yAxis: {
                splitLine: {show: false},
                name:'动态次数'
            },
            grid: {
                bottom: '10%',
                        top: '12%'
            },
            series: {
                type: 'line',
                        showSymbol: false,
                        data: ${daysfreq}
            },
            dataZoom: [{
                type: 'slider',
                show:true,
                start: 0,
                end:100
            },
                {
                    type:'inside',
                    start: 0,
                    end:100
                }]
            };
            //*必须，绑定图表自适应功能
            window.onresize = function () {
                myChart2.resize();
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart2.setOption(option);

        </script>
    </div>
    <!-- 04.一天中各时间点的动态次数 -->
    <div class="section" id="all_hour">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="hourline_chart" style="width:100%;height:600px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart3 = echarts.init(document.getElementById('hourline_chart'));

            // 指定图表的配置项和数据

            var option = {

                // Make gradient line here
                visualMap: {
                    show: false,
                    type: 'continuous',
                    seriesIndex: 0,
                    smooth:true,
                    color:['red','orange','yellow','lightskyblue']
                },

                title: {
                    left: 'center',
                    text: '04.一天中各时间点的动态次数(几点最活跃？)',
                    subtext:'数据来源: www.jianshu.com'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    data: ${hours},
            name:'时间（24小时制）'
            },
            yAxis: {
                splitLine: {show: false},
                name:'动态次数'
            },
            grid:{
                bottom: '6%',
                        top: '10%'
            },
            series: {
                type: 'line',
                        showSymbol: false,
                        data: ${hoursfreq},
            }
            };
            //*必须，绑定图表自适应功能
            window.onresize = function () {
                myChart3.resize();
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart3.setOption(option);

        </script>
    </div>
    
    
    <!-- 词云 -->
    <div class="section" id="comment_cloud">
        <div align="center">
            <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
            <h3>评论 ${comment_num} 条，以下词语出现频率较高</h3>
            <div id="word_freq" style="width:100%;height:580px;"></div>

            <script>
                // 基于准备好的dom，初始化echarts图表
                var myChart = echarts.init(document.getElementById('word_freq'));

                function createRandomItemStyle() {
                    return {
                        normal: {
                            color: 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')'
                        }
                    };
                }
                

                option = {
                    title: {
                        text: '',
                        link: 'http://www.google.com/trends/hottrends'
                    },
                    tooltip: {
                        show: true
                    },
                    series: [{
                    	type: 'wordCloud',//类型为字符云
                        gridSize: 2,//网格尺寸
                        sizeRange: [12, 50],
                        rotationRange: [-90, 90],//旋转范围
                        //shape: 'pentagon',
                        shape:'smooth',  //平滑
//                         autoSize: {
//                             enable: true,
//                             minSize: 20
//                         },

                        textStyle: {
                            normal: {
                                color: function () {
                                    return 'rgb(' + [
                                            Math.round(Math.random() * 255),
                                            Math.round(Math.random() * 255),
                                            Math.round(Math.random() * 255)
                                        ].join(',') + ')';
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,//阴影距离
                                shadowColor: '#333'//阴影颜色
                            }
                        },
                           data: ${wordfreq}
//                         data: [{name: "Sam S Club",value: 10000,itemStyle: {normal: {color: 'red'}}},
//                                {name: "Macys",value: 6181,itemStyle: createRandomItemStyle()},
//                                {name: "Amy Schumer",value: 4386,itemStyle: createRandomItemStyle()},
//                                {name: "Roy",value: 14055,itemStyle: createRandomItemStyle()}]
                         }]
                };

                // 为echarts对象加载数据
                myChart.setOption(option);
            </script>
        </div>
    </div>
</div>
</body>
</html>
