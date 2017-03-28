@echo off
set /p pass=please input Password:
set /p homed=please input HomeDirectory:
start java -jar myftp.jar -p %pass% -d %homed%