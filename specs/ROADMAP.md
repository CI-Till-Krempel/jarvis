# Product Roadmap

Use this living roadmap to plan releases and track user stories across states. It doubles as a lightweight task board and a release planning tool.

## How to use
- Story IDs should match files in `specs/stories/` (e.g., `ST-001` corresponds to `specs/stories/ST-001-some-title.md`).
- Move story references between states as work progresses.
- Keep titles short; full details live in the story file.
- For each planned version, list goals and the set of stories targeted for that release.
- When a release is cut, freeze the section by adding the actual tag (e.g., `v0.1.0`) and dates.

Legend
- `[ST-###] Title` → a user story reference and its short title
- Checkbox states: `- [ ]` To Do, `- [~]` In Progress (use `- [~]` to signal WIP), `- [R]` In Review, `- [x]` Done

Tip: If you prefer standard checkboxes only, use the Kanban tables below and keep raw lists unchecked.

---

## Release plan (versions → stories)

### v0.1 — MVP (target: YYYY-MM)
Goals

Stories:

### v0.2 — Next iteration (target: YYYY-MM)
Goals

Stories
- [US-0007] Distributed Ray Cluster Bootstrap on Client Nodes
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0010] Night-Time Scheduling, Hardware Protection, and Instant User Yield Governance Policy
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0008] Ollama Model Runner and Local Inference Execution
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0006] Admin Dashboard Node Status and Live Metrics View
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0009] Admin Dashboard Job Submission and Status Pipeline View
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0012] Energy Consumption Correlation and Reporting per Job
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0011] Smart Plug Telemetry Collector for Shelly and Tapo
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED

### Backlog (unplanned)


Stories
- [ISSUE-0015] Implement automated check or pre-push hook ensuring feature branches are rebased onto latest develop before creating pull requests to eliminate merge conflicts on specs/ROADMAP.md.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0011] Product Owner should automatically create and merge the sprint backlog PR immediately after sprint kickoff so DevTeam does not encounter start_feature_branch gate rejections.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0008] Ensure newly filed process and retrospective tracking issues are assigned Won't or lowest priority at creation time so they do not block active sprint user stories in advance_story_stage.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0007] Backlog priority order conflict: US-0004 was marked 'Must' while depending on prerequisite US-0002, causing advance_story_stage to reject advancing US-0002 to Implemented until PO reprioritized US-0004.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0005] Validate story dependency sequences during sprint planning to ensure prerequisite foundational stories always hold higher priority than downstream dependent stories.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0003] Streamline ProductOwner specification drafting and backlog grooming sessions with strict prompt timeboxes and pre-structured templates to keep PO token usage under 30% of sprint budget.
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0014] Establish a structured story readiness checklist between Product Owner and Architect prior to grooming to prevent redundant back-and-forth spec revisions.
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0009] Token budget limit of 6,000,000 was exceeded (reaching 6,073,060 tokens) with ProductOwner accounting for over 50% of token usage and feature implementation taking only 34% of total tokens.
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0016] Pull request #22 (feature/US-0008-ollama-model-runner) cannot be cleanly merged into develop due to a merge conflict on specs/ROADMAP.md.
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ISSUE-0013] Sprint 4 initialization is blocked because release PR #15 (develop -> main) is currently open and unmerged. Merging or closing the release PR is required to start the new sprint.
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ST-1] Updated Story
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [ST-2] Repeated Story
  - [ ] DRAFT
  - [ ] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
---

### v1.0
Goals

Stories


### v0.1.0-MVP
Goals

Stories
- [US-0005] Create GitHub PR with Fix and Audit Info
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0004] Sandbox Validation of Fixes
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0002] Generate Dependency Upgrade Patch
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0003] Handle Breaking API Changes in SCA Fixes
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0001] Ingest GitHub Dependabot Alerts
  - [x] DRAFT
  - [x] READY
  - [x] IMPLEMENTED
  - [x] REVIEWED
  - [x] TESTED
  - [x] ACCEPTED
- [US-0013] Ingest GitHub Dependabot Alerts (Implementation)
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0014] Generate Dependency Upgrade Patch (Implementation)
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0016] Sandbox Validation of Fixes (Implementation)
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0017] Create GitHub PR with Fix and Audit Info (Implementation)
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED
- [US-0015] Handle Breaking API Changes in SCA Fixes (Implementation)
  - [x] DRAFT
  - [x] READY
  - [ ] IMPLEMENTED
  - [ ] REVIEWED
  - [ ] TESTED
  - [ ] ACCEPTED


## Task board (Kanban)

Use either the per-version boards below or one global board; duplicate as needed for each active version.

### v0.1 Kanban

| To Do | In Progress | In Review | Done |
|------|-------------|-----------|------|

Notes
- Update this table in PRs alongside code changes.
- Keep the board limited to the current sprint scope if you’re also running sprints.

### v0.2 Kanban

| To Do | In Progress | In Review | Done |
|------|-------------|-----------|------|

---

## Cross-cutting initiatives (optional)
Track broader themes/epics that span multiple versions. Link constituent stories.

---

## Release checklist (for when cutting a release)
- [ ] All included stories are in `Done` and meet Definition of Done
- [ ] Docs updated (stories, PRD/SRS, ADRs as needed)
- [ ] Version/tag created (e.g., `v0.1.0`) and changelog drafted
- [ ] Known issues captured and follow-ups added to backlog

---

## Index of story references
Group story references by planned version for quick scanning.

- v0.1
- v0.2
- Unplanned

Replace placeholders with your actual story IDs and titles. Keep this file updated in the same PRs that move work forward.