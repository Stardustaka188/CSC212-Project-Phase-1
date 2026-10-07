# CSC212 Phase 1 - Ride-Sharing System

Runnable starting skeleton, not a completed assignment.

## Requirements

- A JDK with `java` and `javac` on PATH. This starter was checked locally with JDK 24.0.2; the instructor's required version is not yet confirmed.
- Windows PowerShell 5.1 or PowerShell 7 for the check script.
- No Maven, Gradle or external test dependencies.

## Layout

```text
contracts/       Unmodified supplied interfaces and VehicleType enum
src/Main.java    Entry point (CSV loading and menu still TODO)
tests/           Assertion-based smoke test
scripts/         Repeatable compile/test command
out/             Generated class files; ignored by Git
```

## Compile and test

Open the repository folder in VS Code. In its PowerShell terminal, run:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\check.ps1
```

`-ExecutionPolicy Bypass` applies to this PowerShell process, not the machine-wide policy. The script resolves paths relative to itself, so it can also be called from another directory.

Expected success includes:

```text
PASS: starting slice compiled and smoke test passed (assertions enabled).
```

The script compiles `IDateTime`, `VehicleType`, and the Java files in `src/` and `tests/`. It stops on compiler/test errors and runs with `-ea`, enabling Java assertions.

The smoke test checks that `Main` can be invoked and the supplied enum is available. It does **not** verify CSV parsing, validation, list operations, scheduling or any other required ride-sharing behaviour. `Main` currently does nothing and exits successfully.

## Supplied contracts and incomplete dependencies

All 12 files in `contracts/` were copied unchanged from the instructor-supplied `CSC212/Phase 1/code/` folder. The authoritative specification remains the supplied `RideSharing_Assignment.pdf`, outside this repository.

Most contracts depend on a student-built `LinkedList<T>`, which is not implemented yet. They are intentionally excluded from the initial build rather than patched or replaced with Java collections. Once that dependency exists, expand the contract file list in `scripts/check.ps1`; do not modify supplied signatures.

`DateTime` is an independent candidate for the next implementation slice: its fields must be immutable and `compareTo` chronological (assignment PDF p. 2; `contracts/IDateTime.java`). No `DateTime` implementation exists in this starter.

## First commit and push

Inspect the files, then run the check before committing:

```powershell
git status --short
git add .gitignore README.md contracts src tests scripts
git diff --cached --stat
git commit -m "Add runnable project skeleton and smoke test"
```

`out/` and `.class` files must not be committed. At setup time no remote was configured. If needed, configure your own GitHub repository URL (replace the placeholder):

```powershell
git remote add origin <YOUR_GITHUB_REPOSITORY_URL>
git push -u origin main
```

If `origin` already exists, inspect `git remote -v` before changing it. If the remote contains existing commits, reconcile them normally; do not force-push.

## Next bounded tasks

1. Implement `DateTime` and test earlier/equal/later ordering, including year and month boundaries.
2. Implement the custom linked list, then compile all supplied contracts.
3. Build the entity/list/system layers before implementing the required startup CSV loader and menu.

Scaffolding and smoke-test tooling were prepared with AI assistance. Review and understand them; follow the instructor's policy on permitted assistance.
