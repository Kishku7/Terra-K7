# Terra (plugin-only fork) :: Paper/Folia build driver.
# Conforms to the mod-26 template convention: builds the plugin and drops the
# final jar into dist/ as <modid>-<modver>-plugin.jar.
# Plugins are NOT mod-versioned: ONE jar; this fork targets MC 1.21.11 (Paper dev bundle pin).
param([string]$JavaHome)
$ErrorActionPreference = "Stop"
$repo = Split-Path -Parent $MyInvocation.MyCommand.Path
$dist = Join-Path $repo "dist"
New-Item -ItemType Directory -Force -Path $dist | Out-Null

if ($JavaHome) { $env:JAVA_HOME = $JavaHome }

Write-Host "=== Terra (plugin-only fork) :: Paper/Folia 1.21.11 ==="
Write-Host ("JAVA_HOME = {0}" -f $env:JAVA_HOME)

Push-Location $repo
try {
    & (Join-Path $repo "gradlew.bat") ":platforms:bukkit:build" --no-daemon --stacktrace
    if ($LASTEXITCODE -ne 0) { throw "Gradle build FAILED (exit $LASTEXITCODE)" }
} finally {
    Pop-Location
}

$jar = Get-ChildItem (Join-Path $repo "platforms\bukkit\build\libs") -Filter "*-shaded.jar" -ErrorAction SilentlyContinue |
    Sort-Object LastWriteTime | Select-Object -Last 1
if (-not $jar) { throw "No shaded jar produced under platforms\bukkit\build\libs" }

# Terra-bukkit-<ver>-shaded.jar -> <ver>
$ver = ($jar.BaseName -replace '^Terra-bukkit-', '' -replace '-shaded$', '')
$out = Join-Path $dist ("terra-k7-{0}-plugin.jar" -f $ver)
Copy-Item $jar.FullName $out -Force
Write-Host ("  -> {0}  ({1:N0} KB)" -f $out, ($jar.Length / 1KB))
Write-Host "Plugin build complete."

