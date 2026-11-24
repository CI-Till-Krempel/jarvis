# User Story

- Story ID: US-0010
- Title: Night-Time Scheduling, Hardware Protection, and Instant User Yield Governance Policy
- Status: Implemented
- Priority: Must
- Owner: Scrum Team
- Last Updated: 2026-11-24

## As an employee and system operator, I want the cluster daemon to enforce strict night-time execution windows, require AC power with >=80% battery, enforce a 75°C thermal ceiling, and instantly yield on user activity (<2s), so that client hardware is protected and daytime work is completely undisturbed.

## Acceptance Criteria
- Given a scheduled batch job and a night-time execution window (22:00 to 06:00), When the current time is outside the allowed window, Then the dispatcher holds the job in Queued state without dispatching to client nodes.
- Given an active job on a client node, When AC power is disconnected, battery drops below 80%, or CPU/GPU temperature exceeds 75°C for >60s, Then the daemon pauses/evicts the container, relinquishes power assertions, and notifies the Control Server.
- Given an active job on a client node during night hours, When user interaction (mouse/keyboard input or display wakeup) is detected, Then the daemon immediately yields system resources, pauses the container, and notifies the Control Server within 2 seconds.

## Notes


## Test Approach

