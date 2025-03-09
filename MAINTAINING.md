<!--
SPDX-FileCopyrightText: 2024-2025 Friedrich von Never <friedrich@fornever.me>

SPDX-License-Identifier: Apache-2.0
-->

Maintainer Guide
================

Publish a New Version
---------------------
1. Update the copyright statement in the `README.md` file, if required.
2. Update the copyright statement in the `Rider.Plugin.Template.csproj` file, if required.
3. Choose a new version according to the versioning policy.
4. Prepare a corresponding entry in the `CHANGELOG.md` file (usually by renaming the "Unreleased" section).
5. Set `<Version>` in the `Rider.Plugin.Template.csproj` file.
6. Merge the aforementioned changes via a pull request.
7. Check if the NuGet key is still valid (see the **Rotate NuGet Publishing Key** section if it isn't).
8. Push a tag in form of `v<VERSION>`, e.g. `v0.0.0`. GitHub Actions will do the rest (push a NuGet package).

Rotate NuGet Publishing Key
---------------------------
CI relies on NuGet API key being added to the secrets. From time to time, this key requires maintenance: it will become obsolete and will have to be updated.

To update the key:

1. Sign in onto nuget.org.
2. Go to the [API keys][nuget.api-keys] section.
3. Update the existing or create a new key named `jetbrains-rider-plugin.github` with a permission to **Push only new package versions** and only allowed to publish the package **FVNever.JetBrains.Rider.Plugin**.

   (If this is the first publication of a new package, upload a temporary short-living key with permission to add new packages, and rotate it afterward.)
4. Paste the generated key to the `NUGET_TOKEN` variable on the [action secrets][github.secrets] section of GitHub settings.

[github.secrets]: https://github.com/ForNeVeR/TruePath/settings/secrets/actions
[nuget.api-keys]: https://www.nuget.org/account/apikeys
