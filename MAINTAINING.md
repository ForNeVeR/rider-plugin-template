<!--
SPDX-FileCopyrightText: 2024-2026 Friedrich von Never <friedrich@fornever.me>

SPDX-License-Identifier: Apache-2.0
-->

Maintainer Guide
================

Publish a New Version
---------------------
1. Update the copyright statement in the `README.md` file, if required.
2. Update the `<Copyright>` statement in the `Rider.Plugin.Template.csproj` file, if required.
3. Choose a new version according to the versioning policy.
4. Prepare a corresponding entry in the `CHANGELOG.md` file (usually by renaming the "Unreleased" section).
5. Set `<Version>` in the `Rider.Plugin.Template.csproj` file.
6. Merge the aforementioned changes via a pull request.
7. Push a tag in form of `v<VERSION>`, e.g. `v0.0.0`. GitHub Actions will do the rest (push a NuGet package).

NuGet Publishing Policy
-----------------------
This repository relies on [NuGet Trusted Publishing][docs.nuget-trusted-publishing] policy. In case you need to create it again, follow these steps:

1. Sign in to nuget.org.
2. Go to the [Trusted Publishing][nuget.trusted-publishing] section.
3. Create a new policy:
   - **Policy Name:** `github.rider-plugin-template`.
   - **Repository Owner:** `ForNeVeR`.
   - **Repository**: `rider-plugin-template`.
   - **Workflow File**: `release.yml`.
   - **Environment:** _leave empty_.
   - **Scope:** **push only new packages versions**.
   - **Glob Patterns and Packages**: `FVNever.Rider.Plugin.Template`.
4. Put your nuget.org username into the `NUGET_USER` secret on the [action secrets][github.secrets] section of GitHub settings.

When introducing a new package, you can adjust the policy temporarily to allow it to **push new packages and package versions**.

[docs.nuget-trusted-publishing]: https://learn.microsoft.com/en-us/nuget/nuget-org/trusted-publishing
[github.secrets]: https://github.com/ForNeVeR/rider-plugin-template/settings/secrets/actions
[nuget.trusted-publishing]: https://www.nuget.org/account/trustedpublishing
