JetBrains Rider Plugin Template [![JetBrains Team][badge.jetbrains-team]][jetbrains.opensource]
===============================
This is a template of plugin for JetBrains Rider, including:
- backend part,
- frontend part,
- protocol model connecting the two,
- a unit test,
- GitHub Actions connecting it all.

Installing template from source code
------------------------------------
To use the template from the source code:

1. Fork or clone the repository.
2. Create a local NuGet package:
   ```console
   $ dotnet pack -o ./output
   ```
   Your NuGet package will be generated in the `./output/` folder.
3. Install the template:
   ```console
   $ dotnet new install ./output/JetBrains.Rider.Plugin.*.nupkg
   ```

Using the Template
------------------
Create a project from the template:
```console
$ dotnet new jetbrains-rider-plugin [--name YourNewPlugin] [--output YourNewPluginPath]
```

Execute the following shell command in the plugin directory. It will prepare it for work:
```console
$ ./gradlew :prepare
```
After that, the plugin is read for work. See the `README.md` file among the generated sources to learn how to build, test, and run the plugin.

Versioning
----------
The template follows the Rider internal version schema: e.g. a version of the template corresponding to Rider 2024.2 would have a version `242.x.y`, where `x.y` partially follows [Semantic Versioning 2.0.0][semver]: `y` is incremented for bugfix releases, `x` is incremented for everything else.

Documentation
-------------
- [Changelog][docs.changelog]
- [Template Contributor Guide][docs.contributing]

License
-------
Apache 2 License, see [the `LICENSE` file][docs.license].

Copyright 2024-2025 rider-plugin-template contributors.

[badge.jetbrains-team]: https://jb.gg/badges/team.svg
[docs.changelog]: CHANGELOG.md
[docs.contributing]: CONTRIBUTING.md
[docs.license]: LICENSE
[jetbrains.opensource]: https://github.com/JetBrains/
[semver]: https://semver.org/spec/v2.0.0.html
