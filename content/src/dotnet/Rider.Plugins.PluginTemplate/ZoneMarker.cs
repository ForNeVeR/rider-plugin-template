using JetBrains.Application.BuildScript.Application.Zones;
using JetBrains.DocumentModel;
using JetBrains.ProjectModel;
using JetBrains.Rider.Model;

namespace Rider.Plugins.PluginTemplate;

[ZoneMarker]
public class ZoneMarker
    : IRequire<IProjectModelZone>,
      IRequire<IRiderModelZone>,
      IRequire<IDocumentModelZone>;
