@echo off
cd /d "%~dp0"

netstat -ano | findstr /R /C:":8081 .*LISTENING" >nul
if not errorlevel 1 (
echo Port 8081 is already in use. Stop the existing process and try again.
pause
exit /b 1
)

echo Building fresh version...
call mvnw.cmd clean package

if errorlevel 1 (
echo Build failed. Fix errors first.
pause
exit /b 1
)

echo Starting HabitTracker API...
start "HabitTracker API" cmd /k "java -jar target\habit-tracker-api-0.0.1-SNAPSHOT.jar"

timeout /t 5 /nobreak >nul

start "" http://localhost:8081
