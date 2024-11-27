@echo off
chcp 65001
set PROJECT_PATH=%~dp0
powershell -ExecutionPolicy Bypass -File "%PROJECT_PATH%test.ps1"
pause