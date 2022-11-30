@ECHO OFF

SET IDCS_URL=https://idcs-f0017ddeef0241cfbd70e057c032ff44.identity.oraclecloud.com
SET IDCS_CLIENT_ID=6a7840b6a440462d84407261783327b7
SET IDCS_CLIENT_SECRET=09c12f31-5e67-4230-8ee1-ba2314dcc8e1
set TRIP_SERVICE_URL=http://168.138.54.15:8081/trip-service/api/trip

REM if you want to use the app to do self registration uncomment line
REM and include the ID of a self registration profile here:
REM SET IDCS_SELFREGPROFILES=1234567890abcd1234567890abcdef12

REM setting DEBUG_LOGIN to true will enable logging both in node.js and
REM in your web browser's JavaScript console.
REM this setting is intended for development and debugging purposes and shouldn't
REM be turned on in production
REM SET DEBUG_LOGIN=true
SET DEBUG_LOGIN=true
REM Setting NODE_ENV to "production" disables printing stack traces of any exceptions
REM to the user's browser.
REM If you want to change that for development purposes then comment this line.
REM
REM WARNING: When using this in production you should always have NODE_ENV=production
SET NODE_ENV=production


WHERE >nul 2>nul npm
IF %ERRORLEVEL% NEQ 0 (
  echo ERROR: Node.js not installed
) ELSE (
  IF NOT EXIST ".\node_modules" (
    echo INFO: Required node.js modules are missing! Installing them now...
    npm install
    IF ERRORLEVEL 1 (
      IF EXIST ".\node_modules" RMDIR /S /Q .\node_modules
      ECHO ERROR: npm cannot get the required node.js modules!
      ECHO ERROR: If you need a proxy to reach the internet configure npm to use it with:
      ECHO ERROR:   npm config set proxy="<proxy_url>"
      ECHO ERROR:   npm config set https-proxy="<proxy_url>"
    )
  )

  REM npm config set proxy="http://cn-proxy.jp.oracle.com:80"
  IF NOT ERRORLEVEL 1 npm start
)
