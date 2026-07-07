Write-Host "SMS Campaign Manager Pro - Project Verification"
Write-Host "=========================================="
Write-Host ""

$requiredFiles = @(
    "settings.gradle.kts",
    "build.gradle.kts",
    "gradle/libs.versions.toml",
    "app/build.gradle.kts",
    "app/src/main/AndroidManifest.xml",
    "app/src/main/java/com/example/smscampaignpro/MainActivity.kt",
    "app/src/main/java/com/example/smscampaignpro/SmsCampaignApplication.kt",
    "app/src/main/java/com/example/smscampaignpro/service/SmsSenderService.kt",
    "app/src/main/java/com/example/smscampaignpro/receiver/BootReceiver.kt",
    "app/src/main/java/com/example/smscampaignpro/worker/DailyResetWorker.kt",
    "core/build.gradle.kts",
    "core/src/main/java/com/example/core/data/local/database/AppDatabase.kt",
    "core/src/main/java/com/example/core/di/HiltModule.kt"
)

$missingFiles = @()
foreach ($file in $requiredFiles) {
    $path = Join-Path $PWD.Path $file
    if (Test-Path $path) {
        Write-Host "✅ $file"
    } else {
        Write-Host "❌ $file - MISSING"
        $missingFiles += $file
    }
}

Write-Host ""
Write-Host "Total files checked: $($requiredFiles.Count)"
Write-Host "Missing files: $($missingFiles.Count)"

if ($missingFiles.Count -eq 0) {
    Write-Host ""
    Write-Host "✅ All critical project files are present!"
    Write-Host "The project is ready to be imported into Android Studio."
    Write-Host "APK generation steps:"
    Write-Host "1. Open Android Studio and import the project"
    Write-Host "2. Let Gradle sync complete"
    Write-Host "3. Connect an Android device or start an emulator"
    Write-Host "4. Click 'Run' to build and install the debug APK"
    Write-Host "5. Generate signed APK via Build > Generate Signed Bundle / APK"
} else {
    Write-Host ""
    Write-Host "❌ Some files are missing. Please create them before building."
    exit 1
}