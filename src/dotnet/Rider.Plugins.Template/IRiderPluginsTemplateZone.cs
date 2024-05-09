using JetBrains.Application.BuildScript.Application.Zones;
using JetBrains.ProjectModel;

namespace Rider.Plugins.Template;

[ZoneMarker]
public class ZoneMarker : IRequire<IProjectModelZone>;
