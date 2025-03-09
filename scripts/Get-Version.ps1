# SPDX-FileCopyrightText: 2024-2025 Friedrich von Never <friedrich@fornever.me>
#
# SPDX-License-Identifier: Apache-2.0

param(
    [string] $RefName,
    [string] $RepositoryRoot = "$PSScriptRoot/.."
)

$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

Write-Host "Determining version from ref `"$RefName`"…"
if ($RefName -match '^refs/tags/v') {
    $version = $RefName -replace '^refs/tags/v', ''
    Write-Host "Pushed ref is a version tag, version: $version"
} else {
    $projFilePath = "$RepositoryRoot/Rider.Plugin.Template.csproj"
    [xml] $props = Get-Content $projFilePath
    foreach ($group in $props.Project.PropertyGroup) {
        if ($group.Label -eq 'Package') {
            $version = $group.Version
            break
        }
    }
    Write-Host "Pushed ref is a not version tag, got version from $($projFilePath): $version"
}

Write-Output $version
