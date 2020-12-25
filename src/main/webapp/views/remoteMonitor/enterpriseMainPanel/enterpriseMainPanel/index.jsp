<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
#enterprisePanel {
	margin: 20px;
  background: #FBFBFB;
  border: 1px solid #F2F2F2;
  box-sizing: border-box;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
  border-radius: 5px;
}

#enterprisePanel #header {
  height: 50px;
  padding: 0 20px;
  color: #000;
  font-size: 14px;
  line-height: 50px;
  background: rgba(140, 213, 255, 0.15);
}
#enterprisePanel #header .broadcast {
  display: inline-block;
  margin-right: 40px;
  font-weight: 500;
  color: #333;
}
#enterprisePanel #header .icon {
  display: inline-block;
  width: 20px;
  height: 18px;
  margin-top: 16px;
  margin-right: 2px;
	line-height: 16px;
	text-align: center;
  vertical-align: top;
  border-radius: 50%;
  background-color: #518191;
}

/* 搜索*/
#enterprisePanel .layui-btn .layui-icon {
  vertical-align: middle;
}
#enterprisePanel input {
  height: 28px;
  vertical-align: top;
}
#enterprisePanel .search-box,
#enterprisePanel .opt-box {
  display: inline-block;
  vertical-align: top;
}
#enterprisePanel .search-box .dash{
  height: 28px;
  line-height: 28px;
  vertical-align: top;
}

.SumoSelect > .CaptionCont {
  height: 18px;
  line-height: 18px
}

/* 主体 */
#enterprisePanel #content {
  padding: 20px;
}
#enterprisePanel #content #search {
  height: 30px;
  line-height: 30px;
  margin: 5px 0 18px 0;
}

#enterprisePanel #content #main {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 0 -7px;
}

#enterprisePanel .card {
  flex: 1;
  min-width: 480px;
  height: 450px;
  margin: 7px;
  padding: 20px;
  background: #FFFFFF;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
  border-radius: 5px;
}

#enterprisePanel #baseInfo .title {
  height: 24px;
  line-height: 24px;
  font-size: 16px;
  color: #333;
}
#enterprisePanel #baseInfo .title .iconCircle {
  display: inline-block;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  border-radius: 50%;
  background: #518191;
  margin-top: 2px;
  vertical-align: top;
}
#enterprisePanel #baseInfo .title .iconCircle img {
  margin-top: -1px;
}
#enterprisePanel #baseInfo .title #total,
#enterprisePanel #baseInfo .title .unit {
  font-weight: 500;
}

#enterprisePanel #baseInfo .info {
  display: flex;
  margin-top: 14px;
}
#enterprisePanel #baseInfo .baseWrapper {
  flex: 1;
  margin-right: 20px;
}
#enterprisePanel #baseInfo .baseCard {
  display: flex;
  margin-bottom: 14px;
  padding: 7px 14px;
  background: rgba(0, 0, 0, 0.02);
  border-radius: 5px;
}
#enterprisePanel #baseInfo .baseCard .name {
  min-width: 54px;
  height: 46px;
  font-size: 14px;
  line-height: 46px;
  text-align: center;
  color: #333333;
  background: #FFFFFF;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
  border-radius: 5px;
}
#enterprisePanel #baseInfo .baseCard .infoList {
  flex: 1;
  height: 46px;
  margin-left: 7px;
  font-size: 0;
  color: #333;
}
#enterprisePanel #baseInfo .baseCard .infoList .inf {
  display: inline-block;
  box-sizing: border-box;
  width: 50%;
  height: 23px;
  line-height: 23px;
  padding-right: 4px;
  font-size: 14px;
  vertical-align: top;
  white-space: nowrap;
}
#enterprisePanel #baseInfo .baseCard .infoList .inf .value {
  font-weight: 500;
}

#enterprisePanel #baseInfo #baseChart {
  height: 200px;
}

#enterprisePanel #baseInfo #statistics {
  min-width: 200px;
}
#enterprisePanel #baseInfo #statistics .item {
  height: 60px;
  margin-bottom: 25px;
  font-size: 0;
}
#enterprisePanel #baseInfo #statistics .item .prefix {
  display: inline-block;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: 50px;
  border-radius: 50%;
  vertical-align: top;
	text-align: center;
}
#enterprisePanel #baseInfo #statistics .item .prefix img {
	margin-top: -1px;
}
#enterprisePanel #baseInfo #statistics .item .prefix.pink {
  background-color: #F4664A;
}
#enterprisePanel #baseInfo #statistics .item .prefix.green {
  background-color: #5BD171;
}
#enterprisePanel #baseInfo #statistics .item .prefix.orange {
  background-color: #FF8F28;
}
#enterprisePanel #baseInfo #statistics .item .prefix.red {
  background-color: #FA1C1C;
}

#enterprisePanel #baseInfo #statistics .item .detail {
  display: inline-block;
  height: 100%;
  margin-left: 20px;
}
#enterprisePanel #statistics .item .detail .name {
  font-size: 14px;
  line-height: 22px;
  color: #888;
  letter-spacing: 1px;
}
#enterprisePanel #statistics .item .detail .value {
  font-size: 30px;
  line-height: 38px;
  color: #000;
}


#enterprisePanel .card.chartList .title {
  height: 24px;
  margin-bottom: 10px;
  line-height: 24px;
  font-size: 18px;
  font-weight: 500;
  color: #333;
}
#enterprisePanel .card.chartList .title .divider {
  display: inline-block;
  width: 3px;
  height: 14px;
  margin: 5px 0;
  margin-right: 8px;
  background-color: #22A3ED;
  vertical-align: top;
}
#enterprisePanel .card.chartList .charts {
  display: flex;
  
}
#enterprisePanel .card.chartList .charts .left {
  width: 25%;
  min-width: 220px;
  height: 300px;
  padding: 20px;
}
#enterprisePanel .card.chartList .charts .right {
  flex: 1;
  height: 300px;
  padding: 20px 0;
}
#enterprisePanel .card .solo {
  height: 300px;
  padding: 20px 0;
}

/* 表格 */
#enterprisePanel .card.table .tableTitle {
	margin-bottom: 10px;
	text-align: center;
	font-size: 14px;
	line-height: 24px;
	font-weight: 500;
	color: #333;
}
#enterprisePanel table { text-align: center; max-height: 30%; }
#enterprisePanel table th { text-align: center; white-space: nowrap; }
#enterprisePanel #alarmInfo .recovery { color: #AFDDCB; }
#enterprisePanel #alarmInfo .warn{ color: #FB9747; }
#enterprisePanel #alarmInfo .alarm  { color: #FA1C1C; }

#enterprisePanel .hide { display: none; }

</style>
  <div id="enterprisePanel">
    <div id="header">
      <div class="broadcast">
        <span class="icon"><img src="${ctx}/css/images/common/cloud.png" /></span>
        天气：多云
      </div>
      <div class="broadcast">
        <span class="icon"><img src="${ctx}/css/images/common/thermometer.png" /></span>
        温度：25℃
      </div>
      <div class="broadcast">
        <span class="icon"><img src="${ctx}/css/images/common/drop.png" /></span>
        湿度：20°
      </div>
      <div class="broadcast">
        <span class="icon"><img src="${ctx}/css/images/common/leaf.png" /></span>
        PM2.5：91
      </div>
    </div>
    <div id="content">
      <div id="search">
				<div class="search-box">
					<input 
						id="query_startTime"
						name="query_startTime"
						class="Wdate"
						pattern="yyyy-MM-dd 00:00:00"
						value="2020-11-03 14:00:00"
					/>
					<label class="dash"> -- </label>
					<input
						type="text"
						id="query_endTime"
						name="query_endTime" 
						class="Wdate" 
						pattern="yyyy-MM-dd HH:mm:ss"
						value="2020-11-03 14:00:00"
					/>
				</div>
				<div class="opt-box">
					<button lay-filter="search" class="layui-btn layui-btn-light-blue">查询</button>
					<button class="layui-btn layui-btn-light-blue"> 当日</button>
					<button class="layui-btn layui-btn-primary">当月</button>
					<button class="layui-btn layui-btn-primary">当季</button>
					<button class="layui-btn layui-btn-primary">当年</button>
				</div>
      </div>
      <div id="main">
        <div id="baseInfo" class="card">
          <div class="title">
            <span class="iconCircle">
							<img src="${ctx}/css/images/common/thunder.png" />
						</span>
            累计用电：
            <span id="total">39481</span>
            <span class="unit">千瓦时</span>
          </div>
          <div class="info">
            <div class="baseWrapper">
              <div class="baseCard">
                <div class="name">一车间</div>
                <div class="infoList">
                  <span class="inf">温度：<span class="value">25℃</span></span>
                  <span class="inf">湿度：<span class="value">20°</span></span>
                  <span class="inf">PM2.5：<span class="value">91</span></span>
                  <span class="inf">VOC：<span class="value">33PPM</span></span>
                </div>
              </div>
              <div class="baseCard">
                <div class="name">二车间</div>
                <div class="infoList">
                  <span class="inf">温度：<span class="value">25℃</span></span>
                  <span class="inf">湿度：<span class="value">20°</span></span>
                  <span class="inf">PM2.5：<span class="value">91</span></span>
                  <span class="inf">VOC：<span class="value">33PPM</span></span>
                </div>
              </div>
              <div id="baseChart"></div>
            </div>
            <div id="statistics">
              <div class="item">
                <div class="prefix pink"><img src="${ctx}/css/images/common/danger.png" /></div>
                <div class="detail">
                  <div class="name">异常数</div>
                  <div class="value">5</div>
                </div>
              </div>
              <div class="item">
                <div class="prefix green"><img src="${ctx}/css/images/common/fix.png" /></div>
                <div class="detail">
                  <div class="name">维修数 / 异常数</div>
                  <div class="value">3 / 4</div>
                </div>
              </div>
              <div class="item">
                <div class="prefix orange"><img src="${ctx}/css/images/common/ring.png" /></div>
                <div class="detail">
                  <div class="name">设备预警</div>
                  <div class="value">2</div>
                </div>
              </div>
              <div class="item">
                <div class="prefix red"><img src="${ctx}/css/images/common/alarm.png" /></div>
                <div class="detail">
                  <div class="name">设备报警</div>
                  <div class="value">5</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card table">
          <div class="tableTitle">
            基础信息
					</div>
					<table id="baseTable" lay-filter="lalarmInfo" class="layui-table">
						<thead>
							<tr>
								<th>车间</th>
								<th>设备运行</th>
								<th>设备离线</th>
								<th>设备停机</th>
								<th>报警数量</th>
								<th>预警数量</th>
							</tr> 
						</thead>
						<tbody>
							<tr>
								<td>一车间</td>
								<td>29</td>
								<td>3</td>
								<td>8</td>
								<td>3</td>
								<td>2</td>
							</tr>
							<tr>
								<td>二车间</td>
								<td>35</td>
								<td>5</td>
								<td>12</td>
								<td>0</td>
								<td>1</td>
							</tr>
						</tbody>
					</table>
          <div class="tableTitle" style="margin-top: 10px">
            报警信息
					</div>
					<table id="alarmInfo" lay-filter="lalarmInfo" class="layui-table">
					<!-- <colgroup>
						<col width="150">
						<col width="150">
						<col width="200">
						<col>
					</colgroup> -->
						<thead>
							<tr>
								<th>报警时间</th>
								<th>机组</th>
								<th>报警类型</th>
								<th>报警内容</th>
								<th>报警恢复时间</th>
							</tr> 
						</thead>
						<tbody>
							<tr>
								<td>2020-10-13 11:01:09</td>
								<td>2号机</td>
								<td>
									<span class="alarm">报警<span class="recovery">(已恢复)</span></span>
								</td>
								<td>海鲜库温度高报警</td>
								<td>2020-10-13 15:01:09</td>
							</tr>
							<tr>
								<td>2020-10-13 11:01:09</td>
								<td>3号机</td>
								<td>
									<span class="warn">预警</span>
								</td>
								<td>海鲜库温度高报警</td>
								<td>2020-10-13 15:01:09</td>
							</tr>
							<tr>
								<td>2020-10-13 11:01:09</td>
								<td>4号机</td>
								<td>
									<span class="warn">预警<span class="recovery">(已恢复)</span></span>
								</td>
								<td>海鲜库温度高报警</td>
								<td>2020-10-13 15:01:09</td>
							</tr>
						</tbody>
					</table>
        </div>
        <div class="card chartList" id="exception">
          <div class="title">
            <span class="divider"></span>异常列表信息
          </div>
          <div class="charts">
            <div class="left" id="excpLeft"></div>
            <div class="right" id="excpRight"></div> 
          </div>
        </div>
        <div class="card chartList" id="dailyCheck">
          <div class="title">
            <span class="divider"></span>日常点检
          </div>
          <div class="charts">
            <div class="left" id="dailyCheckLeft"></div>
            <div class="right" id="dailyCheckRight"></div> 
          </div>
        </div>
        <div class="card chartList" id="periodCheck">
          <div class="title">
            <span class="divider"></span>定期点检
          </div>
          <div class="charts">
            <div class="left" id="periodCheckLeft"></div>
            <div class="right" id="periodCheckRight"></div> 
          </div>
        </div>
        <div class="card chartList" id="periodMaintain">
          <div class="title">
            <span class="divider"></span>定期保养
          </div>
          <div class="charts">
            <div class="left" id="periodMaintainLeft"></div>
            <div class="right" id="periodMaintainRight"></div> 
          </div>
        </div>
        <div class="card chartList">
          <div class="title">
            <span class="divider"></span>日常点检
          </div>
          <div class="solo" id="periodDeviceMaintain">
          </div>
        </div>
      </div>
    </div>
  </div>

<script src="${ctx}/js/plugins/echarts/4.x/echarts.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/enterperiseMainPanel.js"></script>
