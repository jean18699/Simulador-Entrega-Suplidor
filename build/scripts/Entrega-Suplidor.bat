@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Entrega-Suplidor startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and ENTREGA_SUPLIDOR_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Entrega-Suplidor-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javalin-openapi-3.9.1.jar;%APP_HOME%\lib\javalin-3.9.1.jar;%APP_HOME%\lib\slf4j-simple-1.7.28.jar;%APP_HOME%\lib\jsoup-1.11.3.jar;%APP_HOME%\lib\jackson-module-kotlin-2.10.3.jar;%APP_HOME%\lib\kotlin-openapi3-dsl-0.20.2.jar;%APP_HOME%\lib\swagger-parser-2.0.19.jar;%APP_HOME%\lib\swagger-parser-v2-converter-2.0.19.jar;%APP_HOME%\lib\swagger-parser-v3-2.0.19.jar;%APP_HOME%\lib\jackson-module-jsonSchema-2.9.9.jar;%APP_HOME%\lib\jackson-datatype-json-org-2.9.9.jar;%APP_HOME%\lib\openapi-parser-4.0.4.jar;%APP_HOME%\lib\swagger-compat-spec-parser-1.0.50.jar;%APP_HOME%\lib\swagger-parser-1.0.50.jar;%APP_HOME%\lib\swagger-core-1.6.1.jar;%APP_HOME%\lib\swagger-core-2.1.2.jar;%APP_HOME%\lib\jsonoverlay-4.0.4.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.10.1.jar;%APP_HOME%\lib\json-patch-1.6.jar;%APP_HOME%\lib\json-schema-validator-2.2.8.jar;%APP_HOME%\lib\json-schema-core-1.2.8.jar;%APP_HOME%\lib\jackson-coreutils-1.8.jar;%APP_HOME%\lib\jackson-databind-2.10.3.jar;%APP_HOME%\lib\freemarker-2.3.30.jar;%APP_HOME%\lib\thymeleaf-3.0.5.RELEASE.jar;%APP_HOME%\lib\velocity-engine-core-2.2.jar;%APP_HOME%\lib\jetty-http-spi-9.4.30.v20200611.jar;%APP_HOME%\lib\jaxws-rt-2.3.1.jar;%APP_HOME%\lib\rt-2.3.1.jar;%APP_HOME%\lib\jaxb-runtime-2.4.0-b180830.0438.jar;%APP_HOME%\lib\mongo-java-driver-3.12.8.jar;%APP_HOME%\lib\gson-2.8.7.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.3.71.jar;%APP_HOME%\lib\slf4j-ext-1.7.30.jar;%APP_HOME%\lib\swagger-models-1.6.1.jar;%APP_HOME%\lib\slf4j-api-1.7.30.jar;%APP_HOME%\lib\jetty-webapp-9.4.30.v20200611.jar;%APP_HOME%\lib\websocket-server-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-servlet-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-security-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-server-9.4.30.v20200611.jar;%APP_HOME%\lib\swagger-parser-core-2.0.19.jar;%APP_HOME%\lib\swagger-models-2.1.2.jar;%APP_HOME%\lib\jackson-annotations-2.10.3.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.10.2.jar;%APP_HOME%\lib\jackson-core-2.10.3.jar;%APP_HOME%\lib\redoc-2.0.0-rc.23.jar;%APP_HOME%\lib\swagger-ui-3.25.2.jar;%APP_HOME%\lib\classgraph-4.8.66.jar;%APP_HOME%\lib\ognl-3.1.12.jar;%APP_HOME%\lib\attoparser-2.0.3.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.4.RELEASE.jar;%APP_HOME%\lib\commons-lang3-3.9.jar;%APP_HOME%\lib\jaxws-api-2.3.1.jar;%APP_HOME%\lib\jaxb-api-2.4.0-b180830.0359.jar;%APP_HOME%\lib\policy-2.7.5.jar;%APP_HOME%\lib\txw2-2.4.0-b180830.0438.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.7.jar;%APP_HOME%\lib\streambuffer-1.5.6.jar;%APP_HOME%\lib\saaj-impl-1.5.0.jar;%APP_HOME%\lib\stax-ex-1.8.jar;%APP_HOME%\lib\FastInfoset-1.2.15.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\javax.xml.soap-api-1.4.0.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\javax.jws-api-1.1.jar;%APP_HOME%\lib\gmbal-api-only-3.1.0-b001.jar;%APP_HOME%\lib\mimepull-1.9.10.jar;%APP_HOME%\lib\ha-api-3.1.9.jar;%APP_HOME%\lib\woodstox-core-5.1.0.jar;%APP_HOME%\lib\stax2-api-4.1.jar;%APP_HOME%\lib\kotlin-reflect-1.3.61.jar;%APP_HOME%\lib\commons-io-2.6.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.3.71.jar;%APP_HOME%\lib\kotlin-stdlib-1.3.71.jar;%APP_HOME%\lib\javassist-3.20.0-GA.jar;%APP_HOME%\lib\javax.activation-1.2.0.jar;%APP_HOME%\lib\management-api-3.0.0-b012.jar;%APP_HOME%\lib\websocket-servlet-9.4.30.v20200611.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\websocket-client-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-client-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-http-9.4.30.v20200611.jar;%APP_HOME%\lib\websocket-common-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-io-9.4.30.v20200611.jar;%APP_HOME%\lib\jetty-xml-9.4.30.v20200611.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\json-20171018.jar;%APP_HOME%\lib\javax.mail-api-1.6.1.jar;%APP_HOME%\lib\javax.mail-1.6.1.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.3.71.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\mailapi-1.4.3.jar;%APP_HOME%\lib\activation-1.1.jar;%APP_HOME%\lib\jetty-util-9.4.30.v20200611.jar;%APP_HOME%\lib\websocket-api-9.4.30.v20200611.jar;%APP_HOME%\lib\uri-template-0.9.jar;%APP_HOME%\lib\guava-27.0.1-android.jar;%APP_HOME%\lib\httpclient-4.5.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.2.jar;%APP_HOME%\lib\swagger-annotations-2.1.2.jar;%APP_HOME%\lib\jakarta.validation-api-2.0.2.jar;%APP_HOME%\lib\snakeyaml-1.24.jar;%APP_HOME%\lib\swagger-annotations-1.6.1.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\msg-simple-1.1.jar;%APP_HOME%\lib\btf-1.2.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-compat-qual-2.5.2.jar;%APP_HOME%\lib\error_prone_annotations-2.2.0.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.17.jar;%APP_HOME%\lib\joda-time-2.9.7.jar;%APP_HOME%\lib\libphonenumber-8.0.0.jar;%APP_HOME%\lib\jopt-simple-5.0.3.jar;%APP_HOME%\lib\httpcore-4.4.4.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-codec-1.9.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.1.jar;%APP_HOME%\lib\rhino-1.7R4.jar

@rem Execute Entrega-Suplidor
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ENTREGA_SUPLIDOR_OPTS%  -classpath "%CLASSPATH%" Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ENTREGA_SUPLIDOR_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ENTREGA_SUPLIDOR_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
