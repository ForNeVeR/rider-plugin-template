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
   dotnet pack -o ./output
   ```
   Your NuGet package will be generated in the `./output/` folder.
3. Install the template:
   ```console
   dotnet new install ./output/JetBrains.Rider.Plugin.*.nupkg
   ```

License
-------
Apache 2 License, see the `LICENSE` file.

Copyright 2024 JetBrains s.r.o.

[badge.jetbrains-team]: https://camo.githubusercontent.com/c5185dff658ed64c46753080436014df7632c4374829409c638fdb4ae0dcdcc0/68747470733a2f2f6a622e67672f6261646765732f7465616d2d706c61737469632e737667
[jetbrains.opensource]: https://github.com/JetBrains/
