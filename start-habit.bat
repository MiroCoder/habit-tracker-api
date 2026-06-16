@echo off
cd /d C:\Users\Mirek.MiroslavPK\IdeaProjects\habit-tracker-api

echo Stopping old app on port 8081...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081') do (
taskkill /PID %%a /F >nul 2>&1
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
