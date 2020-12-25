<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
  #department_panel {
    margin: 20px;
  }

  /* 搜索栏 */
  #department_panel .department_search {
    display: flex;
    padding: 20px 0;
  }

  /* 主面板 */
  #department_panel #department_main_panel {
    box-sizing: border-box;
    padding: 20px;
    margin: 20px 0;
    background: #FBFBFB;
    border: 1px solid #F2F2F2;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    border-radius: 5px;
  }
  /* 通用卡片 */
  #department_panel .card {
    margin: 10px;
    padding: 20px;
    background: #FFFFFF;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    border-radius: 5px;
    flex: 1;
  }

  #department_panel .layui-btn .layui-icon {
    vertical-align: middle;
  }

  #department_panel select, 
  #department_panel input {
    height: 28px;
    vertical-align: top;
  }
  #department_panel select {
    width: 140px;
  }

  #department_panel .search-box,
  #department_panel .opt-box {
    display: inline-block;
    vertical-align: top;
  }

  #department_panel .search-box .dash{
    height: 28px;
    line-height: 28px;
    vertical-align: top;
  }
  
  #department_panel .SumoSelect > .CaptionCont {
      height: 18px;
      line-height: 18px
  }

  #department_panel .base-info {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  #department_panel .base-info > .card.base-info-card .statistics {
    margin: 10px;
    font-size: 16px;
    line-height: 24px;
  }
  #department_panel .base-info > .card.base-info-card .statistics .thunder {
    display: inline-block;
    margin-right: 8px;
    width: 20px;
    height: 20px;
    background: #518191;
    border-radius: 50%;
    text-align: center;
    line-height: 20px;
  }
  #department_panel .base-info > .card.base-info-card .statistics .thunder img { vertical-align: 0; }
  #department_panel .base-info > .card.base-info-card .statistics .total {
    font-weight: 500;
  }

  #department_panel .card .base,
  #department_panel .card .data {
    display: flex;
    justify-content: space-between;
    /* flex-wrap: wrap; */
  }
  #department_panel .card .info {
    flex: 1;
    min-width: 160px;
    margin: 10px;
  }
  #department_panel .base {
    display: flex;
    /* flex-wrap: wrap; */
  }
  #department_panel .base > .info {
    flex: 1;
    min-width: 160px;
  }
  #department_panel .card .base .info .item {
    margin-bottom: 17px;
  }
  #department_panel .card .base .info .item .value {
    font-weight: 500;
  }

  #department_panel .card .data .info .item {
    display: flex;
    font-size: 14px;
    line-height: 24px;
  }
  #department_panel .card .data .img {
    width: 50px;
    height: 50px;
    margin-right: 22px;
    border-radius: 50%;
    text-align: center;
    line-height: 48px;
  }
  #department_panel .card .data .img.orange { background: #FF8F28; }
  #department_panel .card .data .img.red { background: #FA1C1C; }
  #department_panel .card .data .img.yellow { background: #FFDB1C; }
  #department_panel .card .data .text {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-top: -3px;
    margin-bottom: 3px;
  }
  #department_panel .card .data .text .title { font-size: 12px; }
  #department_panel .card .data .text .value { font-size: 30px; }


  #department_panel .base-info > .card.department {
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    /* flex-wrap: wrap; */
    min-width: 60%;
  }

  #department_panel .card.department .devices {
    margin: 10px;
    flex: 1;
  }
  #department_panel .card.department .chart {
    flex: 1;
    min-width: 300px;
    width: 300px;
    height: 200px;
  }
  #department_panel .card.department .devices {
    min-width: 160px;
    flex: 1;
    font-size: 14px;
    font-weight: normal;
  }
  #department_panel .devices .device-list {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  #department_panel .devices .device-list .device {
    min-width: 45%;
    line-height: 200%;
  }
  #department_panel .devices .device-list .device .name {
    display: inline-block;
    min-width: 8em;
  }
  #department_panel .devices .device-list .device .status {
    margin-left: 4px;
  }
  #department_panel .device .status.run {
    color: #2BA474;
  }
  #department_panel .device .status.stop {
    color: #EFB60B;
  }
  #department_panel .device .status.offline {
    color: #3385FF;
  }

  #department_panel .card.alarm .title {
    margin-bottom: 16px;
    font-size: 18px;
    line-height: 24px;
    text-align: center;
  }

  #department_panel .charts {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  #department_panel .charts .card {
    flex: 1;
    min-width: 44%;
    /* height: 600px; */
  }

  #department_panel .charts .chart {
    /* width: 100%; */
    height: 300px;
  }
  #department_panel .devices .device-list.center {
    justify-content: center;
  }

  #department_panel #departmentAlarmInfo { text-align: center; }
  #department_panel #departmentAlarmInfo th { text-align: center; }
  #department_panel #departmentAlarmInfo .recovery { color: #AFDDCB; }
  #department_panel #departmentAlarmInfo .warn{ color: #FB9747; }
  #department_panel #departmentAlarmInfo .alarm  { color: #FA1C1C; }

  #department_panel .hide { display: none; }
  
  #department_panel .layui-form-switch {
    margin-top: 0;
  }
  #department_panel .tableSwitch {
    text-align: right;
    height: 28px;
    line-height: 28px;
    margin-bottom: 12px;
  }
  #department_panel .tableSwitch .layui-form-checkbox {
    margin-right: 0;
  }
</style>
<div id="department_panel">
  <div class="department-search">
    <div class="search-box">
      <input 
        id="query_startTime"
        name="query_startTime"
        class="Wdate"
        pattern="yyyy-MM-dd HH:mm:ss"
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
    </div>
		<div class="opt-box layui-form">
		  <button lay-filter="search" class="layui-btn layui-btn-light-blue" id="devSearch">查询</button>
		  <button class="layui-btn layui-btn-light-blue" id="devToday"> 当日</button>
		  <button class="layui-btn layui-btn-primary" id="devWeek">一周内</button>
		  <button class="layui-btn layui-btn-primary" id="devMonth">一个月内</button>
      <button class="layui-btn layui-btn-primary" id="devYear">当年</button>
      <label style="margin-left: 8px;">自动刷新</label>
      <input type="checkbox" checked="" id="refreshSwitch_department" name="open" lay-skin="switch" lay-filter="depSwitch" lay-text="ON|OFF">
    </div>
  </div>
  <div id="department_main_panel">
    <div class="base-info">
      <div class="card base-info-card">
        <div class="statistics">
          <div class="thunder">
            <img src="${ctx}/css/images/common/thunder.png" />
          </div>
          累计用电：<span class="total" id="dep_electric"></span>
        </div>
        <div class="base">
          <div class="info">
            <div class="item">
              <span>车间名称：</span>
              <span class="value" id="dep_departmentName"></span>
            </div>
            <div class="item">
              <span>设备数：</span>
              <span class="value" id="dep_devicesCount"></span>
            </div>
          </div>
          <div class="info">
            <div class="item">
              <span>车间温度：</span>
              <span class="value" id="dep_temperature"></span>
            </div>
            <div class="item">
              <span>车间湿度：</span>
              <span class="value" id="dep_damp"></span>
            </div>
          </div>
        </div>
        <div class="data">
          <div class="info">
            <div class="item">
              <div class="img orange">
                <img src="${ctx}/css/images/common/ring.png"> 
              </div>
              <div class="text">
                <div class="title">设备预警</div>
                <div class="value" id="dep_warn">0</div>
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
                <div class="value" id="dep_alarm">0</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card department">
        <div class="chart" id="mainChart"></div>
        <div class="devices" id="devicesBox">
          <span id="dep_tip" class="hide">暂无设备</span>
          <div class="device-list" id="dep_list">
            <!-- <div class="device">
              <span class="name">设备名称1号</span>
              <span class="status offline">离线</span>
            </div>
            <div class="device">
              <span class="name">设备名称2号</span>
              <span class="status stop">停机</span>
            </div>
            <div class="device">
              <span class="name">设备名称3号</span>
              <span class="status run">运行</span>
            </div> -->
          </div>
        </div>
      </div>
    </div>
    <div class="card alarm">
      <div class="title">报警信息</div>
      <div class="tableSwitch layui-form">
        <input type="checkbox" name="viewAll" lay-skin="primary" title="仅看未恢复" lay-filter="depViewAll">
      </div>
      <table id="departmentAlarmInfo" lay-filter="lalarmInfo" class="layui-table" style="width: 100%">
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
    <div class="charts" id="depChartsBox">
      <!-- <div class="card">
        <div class="chart" id="tempChart"></div>
      </div> -->
    </div>
  </div>
</div>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/remoteMonitor/departmentPanel.js"></script>
<script>

</script>