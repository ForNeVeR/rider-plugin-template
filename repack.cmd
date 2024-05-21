:; set -eo pipefail
:; SCRIPT_DIR=$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)
:; git clean -fdx ${SCRIPT_DIR}
:; dotnet pack -o ./output
:; exit $?

@echo off
git clean -fdx %~dp0
dotnet pack -o ./output