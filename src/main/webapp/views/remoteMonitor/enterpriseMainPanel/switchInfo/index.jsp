<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/enterperiseMainPanel.js"></script> -->
<style>
  #switch_panel {
    margin: 20px;
  }

  /* 搜索栏 */
  #switch_panel .switch_search {
    display: flex;
    padding: 20px 0;
  }

  /* 主面板 */
  #switch_panel #switch_main_panel {
    box-sizing: border-box;
    padding: 20px;
    margin: 20px 0;
    background: #FBFBFB;
    border: 1px solid #F2F2F2;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    border-radius: 5px;
  }
  /* 通用卡片 */
  #switch_panel .card {
    margin: 10px;
    padding: 20px;
    background: #FFFFFF;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    border-radius: 5px;
    flex: 1;
  }

  #switch_panel .layui-btn .layui-icon {
    vertical-align: middle;
  }

  #switch_panel select, 
  #switch_panel input {
    height: 28px;
    vertical-align: top;
  }
  #switch_panel select {
    width: 140px;
  }

  #switch_panel .search-box,
  #switch_panel .opt-box {
    display: inline-block;
    vertical-align: top;
  }

  #switch_panel .search-box .dash{
    height: 28px;
    line-height: 28px;
    vertical-align: top;
  }
  
  .SumoSelect > .CaptionCont {
    height: 18px;
    line-height: 18px
  }

  #switch_panel .base-info {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  #switch_panel .base-info > .card.base-info-card {
    min-width: 60%;
    flex: 1;
  }

  #switch_panel .card .base,
  #switch_panel .card .data {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  #switch_panel .card .info {
    flex: 1;
    min-width: 180px;
    margin: 10px;
  }
  #switch_panel .base {
    display: flex;
    flex-wrap: wrap;
  }
  #switch_panel .base > .info {
    flex: 1;
    min-width: 180px;
  }
  #switch_panel .card .base .info .item {
    margin-bottom: 17px;
  }
  #switch_panel .card .base .info .item .value {
    font-weight: 500;
  }

  #switch_panel .card .data .info .item {
    display: flex;
    font-size: 14px;
    line-height: 24px;
  }
  #switch_panel .card .data .img {
    width: 50px;
    height: 50px;
    margin-right: 22px;
    border-radius: 50%;
    text-align: center;
    line-height: 48px;
    flex-shrink: 0;
  }
  #switch_panel .card .data .img.orange { background: #FF8F28; }
  #switch_panel .card .data .img.red { background: #FA1C1C; }
  #switch_panel .card .data .img.yellow { background: #FFDB1C; }
  #switch_panel .card .data .text {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-top: -3px;
    margin-bottom: 3px;
  }
  #switch_panel .card .data .text .title { font-size: 12px; }
  #switch_panel .card .data .text .value { font-size: 30px; }

  #switch_panel .card.switch {
    min-width: 200px;
    /* min-width: 35em; */
  }
  #switch_panel .card.switch .lights {
    display: flex;
    flex-wrap: wrap;
    max-height: 200px;
    overflow-y: auto;
    /* justify-content: center; */
  }  
  #switch_panel .card.switch .switch-light {
    width: 11em;
    font-size: 14px;
    margin: 5px 2% 5px 0;
    height: 28px;
    line-height: 28px;
  }
  #switch_panel .card.switch .switch-light.placeholder {
    height: 0;
    margin-top: 0;
    margin-bottom: 0;
  }
  #switch_panel .card.switch .switch-light img {
    margin-right: 8px;
    width: 24px;
    height: 24px;
    vertical-align: middle;
    margin-top: -4px;
  }

  #switch_panel .card.alarm {
    min-height: 120px;
  }
  #switch_panel .card.alarm .title {
    font-size: 18px;
    line-height: 24px;
    text-align: center;
    margin-bottom: 16px;
  }

  #switch_panel .charts {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  #switch_panel .charts .card {
    flex: 1;
    min-width: 44%;
  }
  #switch_panel .charts .card .overflow {
    overflow-x: scroll;
  }

  #switch_panel .charts .chart {
    height: 300px;
  }

  #switch_panel #deviceAlarmInfo { text-align: center; }
  #switch_panel #deviceAlarmInfo th { text-align: center; }
  #switch_panel #deviceAlarmInfo .recovery { color: #AFDDCB; }
  #switch_panel #deviceAlarmInfo .warn{ color: #FB9747; }
  #switch_panel #deviceAlarmInfo .alarm  { color: #FA1C1C; }
  #switch_panel .hide { display: none; }

  #switch_panel .layui-form-switch {
    margin-top: 0;
  }
  #switch_panel .tableSwitch {
    text-align: right;
    height: 28px;
    line-height: 28px;
    margin-bottom: 12px;
  }
  #switch_panel .tableSwitch .layui-form-checkbox {
    margin-right: 0;
  }
</style>
  
<div id="switch_panel">
  <div class="switch-search">
    <div class="search-box">
      <input 
        id="query_startTime"
        name="query_startTime"
        class="Wdate"
        pattern="yyyy-MM-dd 00:00:00"
      />
		  <label class="dash"> -- </label>
      <input
        type="text"
        id="query_endTime"
        name="query_endTime" 
        class="Wdate" 
        pattern="yyyy-MM-dd HH:mm:ss" 
      />
      <select id="department" name="departmentCode">                 
        <c:forEach var="item" items="${department}">
          <option value="${item.itemValue}">${item.itemName}</option>
        </c:forEach>
		  </select>
			<select id="deviceCode" name="deviceCode"></select>
    </div>
		<div class="opt-box layui-form">
		  <button lay-filter="search" class="layui-btn layui-btn-light-blue" id="devSearch">查询</button>
		  <button class="layui-btn layui-btn-light-blue" id="devToday"> 当日</button>
		  <button class="layui-btn layui-btn-primary" id="devWeek">一周内</button>
		  <button class="layui-btn layui-btn-primary" id="devMonth">一个月内</button>
      <button class="layui-btn layui-btn-primary" id="devYear">当年</button>
      <label style="margin-left: 8px;">自动刷新</label>
      <input type="checkbox" checked="" id="refreshSwitch_device" name="open" lay-skin="switch" lay-filter="devSwitch" lay-text="ON|OFF">
    </div>
  </div>
  <div id="switch_main_panel">
    <div class="base-info">
      <div class="card base-info-card">
        <div class="base">
          <div class="info">
            <div class="item">
              <span>车间名称：</span>
              <span class="value" id="dev_departmentName"></span>
            </div>
            <div class="item">
              <span>设备名称：</span>
              <span class="value" id="dev_deviceName"></span>
            </div>
            <div class="item">
              <span>设备编号：</span>
              <span class="value" id="dev_deviceCode"></span>
            </div>
          </div>
          <div class="info">
            <div class="item">
              <span>设备类型：</span>
              <span class="value" id="dev_deviceType"></span>
            </div>
            <div>
              <span>设备型号：</span>
              <span class="value" id="dev_deviceModel"></span>
            </div>
          </div>
          <div class="info" id="dev_infoImg">
          </div>
        </div>
        <div class="data" id="statistics">
          <div class="info">
            <div class="item">
              <div class="img orange">
                <img src="${ctx}/css/images/common/ring.png"> 
              </div>
              <div class="text">
                <div class="title">设备预警</div>
                <div class="value" id="deviceEarlyAlarm">0</div>
              </div>
            </div>
          </div>
          <div class="info">
            <div class="item">
              <div class="img red">
                <img src="${ctx}/css/images/common/alarm.png"> 
              </div>
              <div class="text">
                <div class="title">设备报警</div>
                <div class="value" id="deviceAlarm">0</div>
              </div>
            </div>
          </div>
          <div class="info">
            <div class="item">
              <div class="img yellow">
                <img src="${ctx}/css/images/common/funnel.png"> 
              </div>
              <div class="text">
                <div class="title">运行状态</div>
                <div class="value"><span id="deviceRuntime">0</span> <span style="font-size: 16px;"> 小时</span></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card switch">
        <span id="dev_tip" class="hide">暂无开关</span>
        <div class="lights" id="lights">
          <!-- <span class="switch-light">
            <img src="${ctx}/css/images/common/lighton.png" />
            <span>开关量1</span>
          </span>
          <span class="switch-light">
            <img src="${ctx}/css/images/common/lighton.png" />
            <span>开关量1</span>
          </span> -->
        </div>
      </div>
    </div>
    <div class="card alarm">
      <div class="title">报警信息</div>
      <div class="tableSwitch layui-form">
        <input type="checkbox" name="viewAll" lay-skin="primary" title="仅看未恢复" lay-filter="devViewAll">
      </div>
      <table id="deviceAlarmInfo" lay-filter="lalarmInfo" class="layui-table" style="width: 100%">
        <thead>
          <tr>
            <th>报警时间</th>
            <th>设备</th>
            <th>监控因子</th>
            <th>报警类型</th>
            <th>报警内容</th>
            <th>报警恢复时间</th>
          </tr> 
        </thead>
        <tbody>
          <!-- <tr>
            <td>2020-10-13 11:01:09</td>
            <td>1号机</td>
            <td>
              <span class="warn">预警<span class="recovery">(已恢复)</span></span>
            </td>
            <td>海鲜库温度高报警</td>
            <td>2020-10-13 15:01:09</td>
          </tr>
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
          <tr>
            <td>2020-10-13 11:01:09</td>
            <td>5号机</td>
            <td>
              <span class="alarm">预警<span class="recovery">(已恢复)</span></span>
            </td>
            <td>海鲜库温度高报警</td>
            <td>2020-10-13 15:01:09</td>
          </tr> -->
        </tbody>
      </table>
    </div>
    <div class="charts" id="devChartsBox">
      <!-- <div class="card">
        <div class="chart" id="deviceTempChart"></div>
      </div>
      <div class="card">
        <div class="chart" id="deviceMpaChart"></div>
      </div> -->
    </div>
  </div>
</div>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/devicePanel.js"></script>

<script>
  //Demo

  
</script>