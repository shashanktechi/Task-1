@echo off
setlocal

:: ── Detect Maven from wrapper cache ────────────────────────────────────────
set "MVN=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"

if exist "%MVN%" goto :run

:: Fall back to system mvn if wrapper not found
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    set "MVN=mvn"
    goto :run
)

echo ERROR: Maven not found. Please install Maven or run: mvn wrapper:wrapper
exit /b 1

:run
echo ===========================================
echo  Building Student Management System ...
echo ===========================================
call "%MVN%" -B package -DskipTests --file pom.xml

if %ERRORLEVEL% NEQ 0 (
    echo BUILD FAILED. See output above.
    exit /b %ERRORLEVEL%
)

echo ===========================================
echo  Running Student Management System ...
echo ===========================================
call "%MVN%" -B exec:java --file pom.xml

endlocal
