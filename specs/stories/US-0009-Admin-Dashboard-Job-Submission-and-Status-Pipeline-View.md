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

- ⚠️ REVIEW DENIED at Tested by QA: The HTTP calls in Main.kt for submitting and polling jobs are commented out (`// client.get` and `// setBody(newJob)`). This means the implementation does not actually satisfy the acceptance criteria of communicating with the Control Server. Please implement the actual API calls instead of leaving them commented out.

## Test Approach
UI tests and mock Control Server responses.

### Tasks
- Create job submission UI
- Implement status pipeline view
- Connect UI to Control Server
