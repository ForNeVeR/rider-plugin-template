Template Contributor Guide
==========================
This file includes instructions for how to work on the .NET template (not the plugin produced by the template).

.NET Template
-------------
To change anything about the .NET template side (how it is packed and provided), open the `Rider.Plugin.Template.sln` in the root directory of this repository using a .NET IDE (e.g. JetBrains Rider).

Template Internals
------------------
If you want to build the template in its original location (from the `content` directory), just work with the `content` directory as if it were the root of the project: open the frontend part using IntelliJ IDEA, or the backend part using JetBrains Rider. It is designed to work if it's open from the `content` directory as-is.

Upgrade Rider Version
---------------------
To upgrade the IDE version targeted by the plugin, follow these steps.

1. Update the `riderSdkVersion` in the `content/gradle.properties`.
2. Update the kotlin version in the `plugins` section of the `content/gradle/libs.versions.toml` (see the comment there for the link to the corresponding documentation).
