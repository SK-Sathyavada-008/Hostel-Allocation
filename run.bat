@echo off
echo Compiling Hostel Allocation System...
javac -d . Hostel_allocate.java TestHostelAllocate.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed.
    exit /b %ERRORLEVEL%
)
echo Running Test Suite...
java P1.TestHostelAllocate
