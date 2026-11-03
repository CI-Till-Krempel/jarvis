# User Story

- Story ID: US-0009
- Title: Admin Dashboard Job Submission and Status Pipeline View
- Status: Reviewed
- Priority: Must
- Owner: Scrum Team
- Last Updated: 2026-11-03

## As an IT administrator, I want a Compose for Desktop UI to submit batch job configurations and view active job progress in real time, so that I can manage and monitor cluster workload execution.

## Acceptance Criteria
- Given the Compose for Desktop Admin Dashboard, When the admin enters job parameters (docker image, command, target node tags) and clicks Submit, Then the job is posted to the Control Server and appears in the active queue table.
- Given active running jobs in the cluster, When the admin views the Job Pipeline view, Then each job's live state (Queued, Running, Succeeded, Failed) and execution duration are dynamically updated.

## Notes

- ⚠️ REVIEW DENIED at Tested by QA: The `gradlew` script does not have execute permissions (`Permission denied`), so the test suite cannot be run. Please run `git update-index --chmod=+x gradlew` and commit the change to give it execute permissions.
- 🚫 BLOCKED (technical) - raised by QA: advance_story_stage('US-0009', 'Tested') has been rejected 3 times in a row for the same reason - most recently: Cannot mark 'US-0009' Tested - running the test suite found no tests actually ran (gradle could not be executed: [Errno 13] Permission denied: '/app/state_repo/gradlew'). A story can't be Tested with an empty or unrunnable test suite.

## Test Approach
UI tests and mock Control Server responses.

### Tasks
- Create job submission UI
- Implement status pipeline view
- Connect UI to Control Server
