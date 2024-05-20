Usage
-----
1. Build your plugin (the built ZIP file goes into `build/distributions`):
   ```console
   $ ./gradlew :buildPlugin
   ```
2. Run the tests:
   ```console
   $ ./gradlew :check
   ```
3. Run a test instance of Rider with your plugin:
   ```console
   $ ./gradlew :runIde
   ```