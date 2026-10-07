# Compile the independent starting slice and run its smoke test.
# Add remaining contracts only after the custom LinkedList dependency exists.
Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$buildDir = Join-Path $projectRoot 'out'

if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    throw 'javac was not found. Install a JDK and add its bin folder to PATH.'
}
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    throw 'java was not found. Add the JDK bin folder to PATH.'
}

New-Item -ItemType Directory -Path $buildDir -Force | Out-Null

$sourceFiles = @(
    (Join-Path $projectRoot 'contracts/IDateTime.java')
    (Join-Path $projectRoot 'contracts/VehicleType.java')
)
$sourceFiles += @(Get-ChildItem (Join-Path $projectRoot 'src') -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })
$sourceFiles += @(Get-ChildItem (Join-Path $projectRoot 'tests') -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })

& javac -encoding UTF-8 -d $buildDir @sourceFiles
if ($LASTEXITCODE -ne 0) {
    throw "Compilation failed (exit code $LASTEXITCODE). Tests were not run."
}

& java -ea -cp $buildDir SmokeTest
if ($LASTEXITCODE -ne 0) {
    throw "Smoke test failed (exit code $LASTEXITCODE)."
}

Write-Host 'PASS: starting slice compiled and smoke test passed (assertions enabled).'
Write-Host 'This verifies setup only; the ride-sharing system is not implemented yet.'
