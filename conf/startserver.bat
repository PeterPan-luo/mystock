echo off
title freemoney

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set current_dir=%~dp0%

set ARGS=
:loop
if [%1] == [] goto endloop
        set ARGS=%ARGS% %1
        shift
        goto loop
:endloop

set program_root=%current_dir%
set server_root=%program_root%..\..\
set install_root=%server_root%
rem set java_home=%install_root%jdk\jdk\windows\
rem set javaext_cp=%install_root%jdk\jdk-ext\windows\uep-prert.jar


set java=%program_root%\jdk\bin\java

set class_path=%program_root%lib;%program_root%;%program_root%conf
set class_path=%class_path%;%program_root%lib\freemoney.jar
set class_path=%class_path%;%program_root%lib\xmlbeans-2.3.0.jar
set class_path=%class_path%;%program_root%lib\poi-scratchpad-3.6-20091214.jar
set class_path=%class_path%;%program_root%lib\poi-ooxml-schemas-3.6-20091214.jar
set class_path=%class_path%;%program_root%lib\poi-ooxml-3.6-20091214.jar
set class_path=%class_path%;%program_root%lib\poi-3.6-20091214.jar
set class_path=%class_path%;%program_root%lib\pdfbox-1.2.1.jar
set class_path=%class_path%;%program_root%lib\mysql-connector-java-5.0.7-bin.jar
set class_path=%class_path%;%program_root%lib\log4j-1.2.13.jar
set class_path=%class_path%;%program_root%lib\htmlparser.jar
set class_path=%class_path%;%program_root%lib\htmllexer.jar
set class_path=%class_path%;%program_root%lib\geronimo-stax-api_1.0_spec-1.0.jar
set class_path=%class_path%;%program_root%lib\fontbox-1.2.1.jar
set class_path=%class_path%;%program_root%lib\dom4j-2.0.0-ALPHA-2.jar
set class_path=%class_path%;%program_root%lib\commons-logging-1.1.jar
set class_path=%class_path%;%program_root%lib\commons-beanutils.jar
set class_path=%class_path%;%program_root%lib\aspectjrt.jar
set class_path=%class_path%;%program_root%lib\bcprov-ext-jdk16-145.jar
set class_path=%class_path%;%program_root%lib\bcprov-jdk16-145.jar
set class_path=%class_path%;%program_root%lib\ojdbc14.jar
set class_path=%class_path%;%program_root%lib\commons-dbcp-1.4.jar
set class_path=%class_path%;%program_root%lib\commons-pool-1.5.5.jar

set java_options=%java_options% -Xms256m -Xmx1024m
rem set java_options=-Djava.rmi.server.useCodebaseOnly=true
rem set java_options=%java_options% -Xbootclasspath/p:%javaext_cp%
rem set java_options=%java_options% -Djava.library.path="%server_root%lib\native\windows"
rem set java_options=%java_options% -Djava.ext.dirs=%program_root%\lib

rem echo ===============================================================================
rem echo program_root =%program_root%
rem echo java         =%java%
rem echo java_options =%java_options%
rem echo class_path   =%class_path%
rem echo ===============================================================================

cd /d "%program_root%"
echo @WORK_DIR@ %program_root%
echo @JAVA_CMD@ 
"%java%"  %java_options% -classpath "%class_path%" org.freemoney.socket.SocketServer
