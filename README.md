JetBrains Rider Plugin Template [![JetBrains Team][badge.jetbrains-team]][jetbrains.opensource]
===============================
This is a template of plugin for JetBrains Rider, including:
- backend part,
- frontend part,
- protocol model connecting the two,
- a unit test,
- GitHub Actions connecting it all.

Usage
-----
1. Fork or clone the repository.
2. Replace `com.jetbrains.rider.plugins.template` and `Rider.Plugins.Template` with your preferred identifiers for the frontend and for the backend part.
3. Build your plugin (the built ZIP file goes into `build/distributions`):
   ```console
   $ ./gradlew :buildPlugin
   ```
4. Run the tests:
   ```console
   $ ./gradlew :check
   ```
5. Run a test instance of Rider with your plugin:
   ```console
   $ ./gradlew :runIde
   ```

License
-------
Apache 2 License, see the `LICENSE` file.

Copyright 2024 JetBrains s.r.o.

[badge.jetbrains-team]: https://camo.githubusercontent.com/c5185dff658ed64c46753080436014df7632c4374829409c638fdb4ae0dcdcc0/68747470733a2f2f6a622e67672f6261646765732f7465616d2d706c61737469632e737667
[jetbrains.opensource]: https://github.com/JetBrains/
