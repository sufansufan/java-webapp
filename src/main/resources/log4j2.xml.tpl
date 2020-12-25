<?xml version="1.0" encoding="UTF-8"?>
<!-- 设置log4j2的自身log级别为warn -->
<!-- OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<Configuration status="DEBUG">
	<properties>
        <!-- <property name="LOG_HOME">${webApp.root}</property>  ${web:rootDir} -->
        <property name="LOG_HOME">/iot_factory</property>
    </properties>
    <Appenders>
        <!-- 控制台输出 -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
            <Filters>
                <ThresholdFilter level="DEBUG"/>
            </Filters>
        </Console>
        <!-- ${LOG_HOME} -->
        <RollingFile name="RollingFileDebug" fileName="${LOG_HOME}/logs/debug.log"
            filePattern="${LOG_HOME}/logs/backup/$${date:yyyy-MM}/debug-%d{yyyy-MM-dd}-%i.log.gz">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <Filters>
                <ThresholdFilter level="DEBUG"/>
                <!-- <ThresholdFilter level="INFO" onMatch="DENY" onMismatch="NEUTRAL"/> -->
            </Filters>
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <!-- <CronTriggeringPolicy schedule="0 0 * * * ?"/> -->
                <SizeBasedTriggeringPolicy size="5 MB"/>
            </Policies>
            <!--最多保留20个日志文件-->
            <DefaultRolloverStrategy max="20" min="0" compressionLevel="9"/>
                <!-- <Delete basePath="${LOG_HOME}/logs/backup" maxDepth="2">
                    <IfFileName glob="*/debug/debug-*.log.gz" />
                    <IfLastModified age="5s" />
                </Delete>
            </DefaultRolloverStrategy> -->

        </RollingFile>

        <RollingFile name="RollingFileInfo" fileName="${LOG_HOME}/logs/info.log"
            filePattern="${LOG_HOME}/logs/backup/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="DEBUG"/>
                <!-- <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/> -->
            </Filters>
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="5 MB"/>
            </Policies>
            <!--最多保留20个日志文件-->
            <DefaultRolloverStrategy max="20" min="0" />
        </RollingFile>

        <!-- <RollingFile name="RollingFileWarn" fileName="${LOG_HOME}/logs/warn/warn.log"
            filePattern="${LOG_HOME}/logs/backup/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <ThresholdFilter level="WARN"/>
                <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
            </Filters>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            最多保留20个日志文件
            <DefaultRolloverStrategy max="50" min="0" />
        </RollingFile> -->

        <RollingFile name="RollingFileError" fileName="${LOG_HOME}/logs/error.log"
            filePattern="${LOG_HOME}/logs/backup/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log.gz">
            <ThresholdFilter level="WARN"/>
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="5 MB"/>
            </Policies>
            <!--最多保留20个日志文件-->
            <DefaultRolloverStrategy max="20" min="0" />
        </RollingFile>

        <!-- <RollingFile name="RollingFileFatal" fileName="${LOG_HOME}/logs/fatal.log"
            filePattern="${LOG_HOME}/logs/backup/$${date:yyyy-MM}/fatal-%d{yyyy-MM-dd}-%i.log.gz">
            <ThresholdFilter level="FATAL"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            最多保留20个日志文件
            <DefaultRolloverStrategy max="20" min="0" />
        </RollingFile> -->
    </Appenders>
    <Loggers>
         <!--过滤掉simm.spring.restapi一些无用的DEBUG信息-->
        <logger name="simm.spring.restapi" level="DEBUG"></logger>
        <Root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileDebug"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
            <appender-ref ref="RollingFileFatal"/>
        </Root>

         <!-- 第三方日志系统 -->
        <logger name="org.springframework.core" level="debug" />
        <logger name="org.springframework.beans" level="debug" />
        <logger name="org.springframework.context" level="debug" />
        <logger name="org.springframework.web" level="debug" />
    </Loggers>
</Configuration>
