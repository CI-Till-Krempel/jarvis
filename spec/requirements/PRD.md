PRD: Agentisches Security-Tool zur automatischen Behebung von Schwachstellen
25. Sept. 2026 · @Till
Zusammenfassung & Problemstellung
Das Produkt ist ein KI-Agent, der Schwachstellen aus bestehenden Scannern übernimmt, einen Fix erzeugt, ihn automatisiert validiert und als Pull Request bereitstellt. Ziel ist, die mittlere Behebungszeit (MTTR) kritischer Findings von Wochen auf Tage zu senken.
Security-Scanner (SAST, SCA, Container, IaC) finden heute mehr Schwachstellen, als Teams beheben können. Der Engpass liegt nicht in der Erkennung, sondern in der Behebung: Findings landen im Backlog, werden manuell triagiert und oft monatelang nicht gefixt.
Typische Probleme:
• Alert-Fatigue: Hunderte Findings pro Repository, viele davon False Positives oder nicht erreichbar.
• Kontextwechsel: Entwickler müssen sich in fremden Code, CVE-Details und Upgrade-Pfade einarbeiten.
• Brechende Upgrades: Dependency-Updates scheitern an API-Änderungen, die ein reiner Versions-Bump nicht löst.
• Fehlende Nachweise: Audits (z. B. NIS2, Cyber Resilience Act, ISO 27001) verlangen belegbare Behebungsfristen.
Ziele, Nicht-Ziele und Erfolgsmetriken
Das Tool behebt Schwachstellen dort, wo ein Fix sicher und prüfbar ist, und eskaliert alles andere mit aufbereitetem Kontext an Menschen.
Ziele
• Findings aus vorhandenen Scannern automatisch triagieren und priorisieren.
• Fixes erzeugen, die kompilieren, Tests bestehen und die Schwachstelle nachweislich beseitigen.
• Fixes als reviewbare Pull Requests mit Begründung ausliefern.
• Vollständigen Audit-Trail für Compliance-Nachweise führen.
Nicht-Ziele (v1)
• Kein eigener Scanner; das Tool konsumiert bestehende Scanner-Ergebnisse.
• Kein direktes Deployment in Produktion; Merge bleibt standardmäßig beim Menschen.
• Keine Behebung von Laufzeit-Incidents oder Konfiguration in Live-Systemen.
• Keine Offensive-Funktionen wie Exploit-Generierung.
Erfolgsmetriken
Metrik
Ausgangswert
Ziel nach 6 Monaten
MTTR kritische Findings
~30 Tage (Annahme)
≤ 5 Tage
Anteil Findings mit Auto-Fix-PR
0 %
≥ 50 %
Merge-Rate der Fix-PRs
–
≥ 70 %
Regressionen durch gemergte Fixes
–
< 2 %
Entwicklerzeit pro Finding
~2 h (Annahme)
≤ 15 min Review
Security-Backlog (offen, kritisch/hoch)
Basis messen
−60 %
Zielgruppen & Personas
Primär adressiert das Tool Entwicklungsteams, die Security-Arbeit bisher als Unterbrechung erleben; Käufer sind Security- und Plattformverantwortliche.
Persona
Rolle
Hauptbedürfnis
Nutzt das Tool für
Dana, Entwicklerin
Feature-Team
Wenig Unterbrechung, verständliche Fixes
Review und Merge von Fix-PRs
Marc, AppSec Engineer
Zentrales Security-Team
Backlog abbauen, Policies durchsetzen
Regeln, Autonomiestufen, Eskalationen
Sven, Platform Lead
DevOps / Plattform
Stabile Pipelines, keine Regressionen
CI-Integration, Rollout-Steuerung
Julia, CISO
Management / Compliance
Nachweisbare Fristen, Risikoübersicht
Dashboards, Audit-Reports
User Stories / Use Cases
Die Kern-Use-Cases decken die häufigsten Schwachstellenklassen ab, beginnend mit Dependencies als risikoärmstem Einstieg.
1. Vulnerable Dependency: Als Entwicklerin möchte ich, dass bei einer CVE in einer Bibliothek automatisch ein Upgrade-PR entsteht, der auch nötige API-Anpassungen im Code enthält.
2. Code-Schwachstelle (SAST): Als Entwicklerin möchte ich für eine gefundene SQL-Injection einen PR mit parametrisierter Query und einem Test, der den Angriffsvektor abdeckt.
3. Container-Image: Als Platform Lead möchte ich, dass veraltete Base-Images auf gepatchte Versionen gehoben und neu gebaut werden.
4. IaC-Fehlkonfiguration: Als AppSec Engineer möchte ich, dass offene S3-Buckets oder zu weite IAM-Rechte in Terraform korrigiert werden.
5. Secrets im Code: Als AppSec Engineer möchte ich, dass ein geleaktes Secret aus dem Code entfernt, durch eine Secret-Manager-Referenz ersetzt und die Rotation als Ticket angestoßen wird.
6. Triage: Als AppSec Engineer möchte ich, dass nicht erreichbare Findings begründet als False Positive markiert werden, statt einen PR zu erzeugen.
7. Eskalation: Als Entwicklerin möchte ich bei nicht automatisch lösbaren Findings eine Analyse mit Lösungsvorschlag statt eines halbfertigen PRs.
8. Nachweis: Als CISO möchte ich pro Finding sehen, wann es erkannt, gefixt, reviewt und gemergt wurde.
Funktionale Anforderungen
Der Ablauf besteht aus fünf Stufen; jede Stufe darf abbrechen und an einen Menschen eskalieren.
flowchart LR
  A[Ingest<br/>Scanner-Findings] --> B[Triage &<br/>Priorisierung]
  B -->|fixbar| C[Fix-Generierung]
  B -->|nicht fixbar / FP| E[Eskalation /<br/>Markierung]
  C --> D[Validierung<br/>Sandbox]
  D -->|bestanden| F[Pull Request]
  D -->|fehlgeschlagen| C
  D -->|max. Versuche| E
  F --> G[Review & Merge]
Fehlgeschlagene Validierungen gehen mit Fehlerlog zurück an die Fix-Generierung, begrenzt auf eine konfigurierbare Anzahl Versuche.
ID
Stufe
Anforderung
Priorität
F-01
Ingest
Findings aus SARIF, CycloneDX/SPDX und Scanner-APIs einlesen
Must
F-02
Ingest
Duplikate über Scanner hinweg zusammenführen
Must
F-03
Triage
Priorisierung nach CVSS, EPSS, CISA KEV und Asset-Kritikalität
Must
F-04
Triage
Erreichbarkeitsanalyse: wird der verwundbare Code tatsächlich aufgerufen?
Should
F-05
Triage
False Positives mit Begründung markieren, Mensch bestätigt
Must
F-06
Fix
Minimalen Patch erzeugen, der nur die Schwachstelle adressiert
Must
F-07
Fix
Bei Dependency-Upgrades brechende API-Änderungen im Code nachziehen
Must
F-08
Fix
Regressionstest erzeugen, der die Schwachstelle abdeckt
Should
F-09
Fix
Projektkonventionen (Linter, Formatter, Code-Stil) einhalten
Must
F-10
Validierung
Build, bestehende Tests und Linter in isolierter Sandbox ausführen
Must
F-11
Validierung
Ursprünglichen Scanner erneut laufen lassen; Finding muss verschwinden
Must
F-12
Validierung
Keine neuen Findings durch den Fix einführen
Must
F-13
PR
PR mit Beschreibung: Schwachstelle, Ursache, Änderung, Testergebnis, Risiko
Must
F-14
PR
Review-Kommentare aufnehmen und PR iterativ anpassen
Should
F-15
PR
Zusammengehörige Fixes bündeln, Batch-Größe konfigurierbar
Could
F-16
Steuerung
Policies pro Repo: welche Klassen, welche Severity, welche Autonomiestufe
Must
F-17
Reporting
Dashboard mit MTTR, Backlog, Merge-Rate; Export für Audits
Must
Agenten-Architektur & Autonomiestufen
Ein Orchestrator steuert spezialisierte Sub-Agenten; jeder Agent hat nur die Werkzeuge und Rechte, die seine Aufgabe erfordert.
Komponente
Aufgabe
Werkzeuge / Rechte
Orchestrator
Plant Ablauf, verteilt Aufgaben, entscheidet über Eskalation
Queue, Policy-Engine, keine Schreibrechte auf Repos
Triage-Agent
Bewertet Finding, prüft Erreichbarkeit, recherchiert CVE
Lesezugriff Code, CVE-/Advisory-Datenbanken
Fix-Agent
Analysiert Code-Kontext und erzeugt Patch
Lesezugriff Repo, Schreibzugriff nur im Sandbox-Branch
Validierungs-Agent
Führt Build, Tests, Scanner aus und bewertet Ergebnis
Ephemere Sandbox ohne Netzwerk nach außen
PR-Agent
Erstellt PR, reagiert auf Review-Kommentare
Git-Provider-API, nur PRs, kein Merge
Audit-Log
Protokolliert jede Entscheidung, jeden Prompt, jede Änderung
Append-only Speicher
Autonomiestufen (pro Repository und Schwachstellenklasse einstellbar)
Stufe
Name
Verhalten
L0
Beobachten
Nur Triage und Fix-Vorschlag im Dashboard, keine PRs
L1
Vorschlagen
PR wird erstellt, Merge durch Mensch (Default)
L2
Auto-Merge eingeschränkt
Auto-Merge für Patch-Level-Upgrades mit grüner Pipeline
L3
Auto-Merge erweitert
Auto-Merge für definierte Klassen nach Policy, Mensch wird informiert
Das zugrunde liegende LLM ist austauschbar; für Kunden mit Datenhoheitsanforderungen wird ein selbst gehostetes Modell unterstützt.
Nicht-funktionale Anforderungen
Da das Tool selbst Schreibzugriff auf Quellcode hat, gelten für es strengere Sicherheitsanforderungen als für die Systeme, die es absichert.
Bereich
Anforderung
Least Privilege
Kurzlebige, repo-spezifische Tokens; kein Zugriff auf Produktionssysteme oder Deploy-Pipelines
Isolation
Validierung in ephemeren Containern/VMs, nach jedem Lauf verworfen; ausgehender Netzverkehr nur zu Paket-Registries per Allowlist
Datenschutz
Quellcode verlässt die Kundenumgebung nur mit Zustimmung; Self-Hosted-Option; kein Training auf Kundencode
Auditierbarkeit
Unveränderliches Log aller Agentenaktionen, mindestens 12 Monate aufbewahrt
Compliance
Unterstützung von Nachweisen für ISO 27001, SOC 2, NIS2 und Cyber Resilience Act
Signierung
Alle Commits des Agenten signiert und als Bot-Identität gekennzeichnet
Performance
Triage < 5 min pro Finding; Fix-PR für Dependency-Upgrades < 30 min
Skalierung
≥ 1.000 Repositories pro Mandant, parallele Verarbeitung
Verfügbarkeit
99,5 % für SaaS; Ausfall blockiert keine Kunden-Pipelines
Kostenkontrolle
Budget-Limits für LLM-Tokens und Sandbox-Laufzeit pro Repo und Monat
Guardrails, Risiken & Mitigationen
Das größte Risiko ist ein Fix, der plausibel aussieht, aber funktional oder sicherheitlich falsch ist; deshalb entscheidet nie das Modell allein, ob ein Fix gut ist.
Risiko
Auswirkung
Mitigation
Fehlerhafter Fix mit grüner Pipeline
Regression in Produktion
Default L1 (Mensch mergt); Mindest-Testabdeckung als Voraussetzung für L2/L3
Scheinfix: Scanner still, Lücke bleibt
Falsches Sicherheitsgefühl
Zweiter Scanner oder Regressionstest mit Angriffsvektor; Stichproben durch AppSec
Prompt Injection über Code, Kommentare oder Advisories
Agent führt fremde Anweisungen aus
Eingaben als Daten behandeln; keine Tools mit Außenwirkung im Fix-Agent; Diff-Policy prüft Änderungen
Supply-Chain-Angriff über empfohlenes Paket
Schadcode eingeschleust
Nur Versionen aus vertrauenswürdigen Registries; Mindestalter neuer Releases; Signaturprüfung
Überschreitung des Scope
Unerwartete Änderungen
Diff-Limit (Dateien, Zeilen); keine Änderungen an CI-Konfig, Auth-Code oder Secrets ohne Freigabe
PR-Flut
Review-Fatigue, Ablehnung durch Teams
Rate-Limits pro Repo; Bündelung; Priorisierung nach Risiko
Kompromittierung des Tools selbst
Schreibzugriff auf viele Repos
Least Privilege, Token-Rotation, Pentests, Bot-Commits nie ohne Branch-Protection
Kosten laufen aus dem Ruder
Unwirtschaftlicher Betrieb
Budget-Limits, max. Fix-Versuche, günstigere Modelle für Triage
Harte Regeln (nicht konfigurierbar)
• Kein Push auf geschützte Branches; nur PRs.
• Keine Deaktivierung von Tests, Linter-Regeln oder Security-Checks, um eine Pipeline grün zu bekommen.
• Keine Ausführung von Code aus Findings außerhalb der Sandbox.
Integrationen
Für v1 reicht die Anbindung an die verbreitetsten Git-Provider und Scanner; offene Formate wie SARIF halten den Rest erweiterbar.
Kategorie
v1
Später
Git-Provider
GitHub, GitLab
Bitbucket, Azure DevOps
SAST
Semgrep, CodeQL, SARIF-Import
SonarQube, Checkmarx
SCA / SBOM
Dependabot-/OSV-Daten, Trivy, CycloneDX
Snyk, Mend
Container / IaC
Trivy, Checkov
Grype, KICS
Secrets
Gitleaks
Secret-Manager-Anbindung (Vault, AWS SM)
Ticketing
Jira
Linear, ServiceNow
Kommunikation
Slack, Microsoft Teams
E-Mail-Digest
CI
GitHub Actions, GitLab CI
Jenkins
Roadmap & Release-Phasen
Der Rollout startet mit Dependency-Fixes, weil sie am besten validierbar sind, und erweitert Klassen und Autonomie erst nach belegter Qualität.
Phase
Zeitraum (Annahme)
Umfang
Exit-Kriterium
0 – Discovery
Monat 1
Interviews mit 5–8 Teams, Baseline-MTTR messen
Top-3-Schwachstellenklassen bestätigt
1 – MVP
Monat 2–4
SCA-Fixes inkl. API-Anpassung, GitHub, L0/L1
Merge-Rate ≥ 60 % bei 3 Pilot-Teams
2 – Beta
Monat 5–7
SAST-Fixes (Injection, XSS, Pfad-Traversal), Container-Images, GitLab, Dashboard
Regressionen < 2 %, 10 aktive Teams
3 – GA
Monat 8–10
IaC, Secrets, L2, Audit-Export, Self-Hosted
Security-Review und Pentest bestanden
4 – Erweiterung
ab Monat 11
L3, weitere Provider, Review-Iteration, Batch-PRs
Nach Kundenbedarf
Offene Fragen
[ ] Deployment-Modell: SaaS, Self-Hosted oder beides ab MVP?
[ ] Internes Tool oder Produkt für Kunden (z. B. als Beratungs- oder Managed-Service-Angebot)?
[ ] Welches LLM bzw. welche Modellstrategie (Cloud-API vs. selbst gehostet) erfüllt die Datenschutzanforderungen der Zielkunden?
[ ] Welche Sprachen/Ökosysteme zuerst (z. B. Java/Maven, JavaScript/npm, Python, C/C++ für Embedded)?
[ ] Wer haftet bei einem gemergten Fix, der eine Regression verursacht, und wie wird das vertraglich geregelt?
[ ] Ab welcher Testabdeckung ist L2 (Auto-Merge) pro Repo zulässig?
[ ] Preismodell: pro Repository, pro Entwickler oder pro behobenem Finding?
[ ] Abgrenzung zu bestehenden Angeboten (GitHub Copilot Autofix, Snyk Agent Fix, Dependabot): Was ist der klare Differenzierer?PRD: Agentisches Security-Tool zur automatischen Behebung von Schwachstellen
25. Sept. 2026 · @Till
Zusammenfassung & Problemstellung
Das Produkt ist ein KI-Agent, der Schwachstellen aus bestehenden Scannern übernimmt, einen Fix erzeugt, ihn automatisiert validiert und als Pull Request bereitstellt. Ziel ist, die mittlere Behebungszeit (MTTR) kritischer Findings von Wochen auf Tage zu senken.
Security-Scanner (SAST, SCA, Container, IaC) finden heute mehr Schwachstellen, als Teams beheben können. Der Engpass liegt nicht in der Erkennung, sondern in der Behebung: Findings landen im Backlog, werden manuell triagiert und oft monatelang nicht gefixt.
Typische Probleme:
• Alert-Fatigue: Hunderte Findings pro Repository, viele davon False Positives oder nicht erreichbar.
• Kontextwechsel: Entwickler müssen sich in fremden Code, CVE-Details und Upgrade-Pfade einarbeiten.
• Brechende Upgrades: Dependency-Updates scheitern an API-Änderungen, die ein reiner Versions-Bump nicht löst.
• Fehlende Nachweise: Audits (z. B. NIS2, Cyber Resilience Act, ISO 27001) verlangen belegbare Behebungsfristen.
Ziele, Nicht-Ziele und Erfolgsmetriken
Das Tool behebt Schwachstellen dort, wo ein Fix sicher und prüfbar ist, und eskaliert alles andere mit aufbereitetem Kontext an Menschen.
Ziele
• Findings aus vorhandenen Scannern automatisch triagieren und priorisieren.
• Fixes erzeugen, die kompilieren, Tests bestehen und die Schwachstelle nachweislich beseitigen.
• Fixes als reviewbare Pull Requests mit Begründung ausliefern.
• Vollständigen Audit-Trail für Compliance-Nachweise führen.
Nicht-Ziele (v1)
• Kein eigener Scanner; das Tool konsumiert bestehende Scanner-Ergebnisse.
• Kein direktes Deployment in Produktion; Merge bleibt standardmäßig beim Menschen.
• Keine Behebung von Laufzeit-Incidents oder Konfiguration in Live-Systemen.
• Keine Offensive-Funktionen wie Exploit-Generierung.
Erfolgsmetriken
Metrik
Ausgangswert
Ziel nach 6 Monaten
MTTR kritische Findings
~30 Tage (Annahme)
≤ 5 Tage
Anteil Findings mit Auto-Fix-PR
0 %
≥ 50 %
Merge-Rate der Fix-PRs
–
≥ 70 %
Regressionen durch gemergte Fixes
–
< 2 %
Entwicklerzeit pro Finding
~2 h (Annahme)
≤ 15 min Review
Security-Backlog (offen, kritisch/hoch)
Basis messen
−60 %
Zielgruppen & Personas
Primär adressiert das Tool Entwicklungsteams, die Security-Arbeit bisher als Unterbrechung erleben; Käufer sind Security- und Plattformverantwortliche.
Persona
Rolle
Hauptbedürfnis
Nutzt das Tool für
Dana, Entwicklerin
Feature-Team
Wenig Unterbrechung, verständliche Fixes
Review und Merge von Fix-PRs
Marc, AppSec Engineer
Zentrales Security-Team
Backlog abbauen, Policies durchsetzen
Regeln, Autonomiestufen, Eskalationen
Sven, Platform Lead
DevOps / Plattform
Stabile Pipelines, keine Regressionen
CI-Integration, Rollout-Steuerung
Julia, CISO
Management / Compliance
Nachweisbare Fristen, Risikoübersicht
Dashboards, Audit-Reports
User Stories / Use Cases
Die Kern-Use-Cases decken die häufigsten Schwachstellenklassen ab, beginnend mit Dependencies als risikoärmstem Einstieg.
1. Vulnerable Dependency: Als Entwicklerin möchte ich, dass bei einer CVE in einer Bibliothek automatisch ein Upgrade-PR entsteht, der auch nötige API-Anpassungen im Code enthält.
2. Code-Schwachstelle (SAST): Als Entwicklerin möchte ich für eine gefundene SQL-Injection einen PR mit parametrisierter Query und einem Test, der den Angriffsvektor abdeckt.
3. Container-Image: Als Platform Lead möchte ich, dass veraltete Base-Images auf gepatchte Versionen gehoben und neu gebaut werden.
4. IaC-Fehlkonfiguration: Als AppSec Engineer möchte ich, dass offene S3-Buckets oder zu weite IAM-Rechte in Terraform korrigiert werden.
5. Secrets im Code: Als AppSec Engineer möchte ich, dass ein geleaktes Secret aus dem Code entfernt, durch eine Secret-Manager-Referenz ersetzt und die Rotation als Ticket angestoßen wird.
6. Triage: Als AppSec Engineer möchte ich, dass nicht erreichbare Findings begründet als False Positive markiert werden, statt einen PR zu erzeugen.
7. Eskalation: Als Entwicklerin möchte ich bei nicht automatisch lösbaren Findings eine Analyse mit Lösungsvorschlag statt eines halbfertigen PRs.
8. Nachweis: Als CISO möchte ich pro Finding sehen, wann es erkannt, gefixt, reviewt und gemergt wurde.
Funktionale Anforderungen
Der Ablauf besteht aus fünf Stufen; jede Stufe darf abbrechen und an einen Menschen eskalieren.
flowchart LR
  A[Ingest<br/>Scanner-Findings] --> B[Triage &<br/>Priorisierung]
  B -->|fixbar| C[Fix-Generierung]
  B -->|nicht fixbar / FP| E[Eskalation /<br/>Markierung]
  C --> D[Validierung<br/>Sandbox]
  D -->|bestanden| F[Pull Request]
  D -->|fehlgeschlagen| C
  D -->|max. Versuche| E
  F --> G[Review & Merge]
Fehlgeschlagene Validierungen gehen mit Fehlerlog zurück an die Fix-Generierung, begrenzt auf eine konfigurierbare Anzahl Versuche.
ID
Stufe
Anforderung
Priorität
F-01
Ingest
Findings aus SARIF, CycloneDX/SPDX und Scanner-APIs einlesen
Must
F-02
Ingest
Duplikate über Scanner hinweg zusammenführen
Must
F-03
Triage
Priorisierung nach CVSS, EPSS, CISA KEV und Asset-Kritikalität
Must
F-04
Triage
Erreichbarkeitsanalyse: wird der verwundbare Code tatsächlich aufgerufen?
Should
F-05
Triage
False Positives mit Begründung markieren, Mensch bestätigt
Must
F-06
Fix
Minimalen Patch erzeugen, der nur die Schwachstelle adressiert
Must
F-07
Fix
Bei Dependency-Upgrades brechende API-Änderungen im Code nachziehen
Must
F-08
Fix
Regressionstest erzeugen, der die Schwachstelle abdeckt
Should
F-09
Fix
Projektkonventionen (Linter, Formatter, Code-Stil) einhalten
Must
F-10
Validierung
Build, bestehende Tests und Linter in isolierter Sandbox ausführen
Must
F-11
Validierung
Ursprünglichen Scanner erneut laufen lassen; Finding muss verschwinden
Must
F-12
Validierung
Keine neuen Findings durch den Fix einführen
Must
F-13
PR
PR mit Beschreibung: Schwachstelle, Ursache, Änderung, Testergebnis, Risiko
Must
F-14
PR
Review-Kommentare aufnehmen und PR iterativ anpassen
Should
F-15
PR
Zusammengehörige Fixes bündeln, Batch-Größe konfigurierbar
Could
F-16
Steuerung
Policies pro Repo: welche Klassen, welche Severity, welche Autonomiestufe
Must
F-17
Reporting
Dashboard mit MTTR, Backlog, Merge-Rate; Export für Audits
Must
Agenten-Architektur & Autonomiestufen
Ein Orchestrator steuert spezialisierte Sub-Agenten; jeder Agent hat nur die Werkzeuge und Rechte, die seine Aufgabe erfordert.
Komponente
Aufgabe
Werkzeuge / Rechte
Orchestrator
Plant Ablauf, verteilt Aufgaben, entscheidet über Eskalation
Queue, Policy-Engine, keine Schreibrechte auf Repos
Triage-Agent
Bewertet Finding, prüft Erreichbarkeit, recherchiert CVE
Lesezugriff Code, CVE-/Advisory-Datenbanken
Fix-Agent
Analysiert Code-Kontext und erzeugt Patch
Lesezugriff Repo, Schreibzugriff nur im Sandbox-Branch
Validierungs-Agent
Führt Build, Tests, Scanner aus und bewertet Ergebnis
Ephemere Sandbox ohne Netzwerk nach außen
PR-Agent
Erstellt PR, reagiert auf Review-Kommentare
Git-Provider-API, nur PRs, kein Merge
Audit-Log
Protokolliert jede Entscheidung, jeden Prompt, jede Änderung
Append-only Speicher
Autonomiestufen (pro Repository und Schwachstellenklasse einstellbar)
Stufe
Name
Verhalten
L0
Beobachten
Nur Triage und Fix-Vorschlag im Dashboard, keine PRs
L1
Vorschlagen
PR wird erstellt, Merge durch Mensch (Default)
L2
Auto-Merge eingeschränkt
Auto-Merge für Patch-Level-Upgrades mit grüner Pipeline
L3
Auto-Merge erweitert
Auto-Merge für definierte Klassen nach Policy, Mensch wird informiert
Das zugrunde liegende LLM ist austauschbar; für Kunden mit Datenhoheitsanforderungen wird ein selbst gehostetes Modell unterstützt.
Nicht-funktionale Anforderungen
Da das Tool selbst Schreibzugriff auf Quellcode hat, gelten für es strengere Sicherheitsanforderungen als für die Systeme, die es absichert.
Bereich
Anforderung
Least Privilege
Kurzlebige, repo-spezifische Tokens; kein Zugriff auf Produktionssysteme oder Deploy-Pipelines
Isolation
Validierung in ephemeren Containern/VMs, nach jedem Lauf verworfen; ausgehender Netzverkehr nur zu Paket-Registries per Allowlist
Datenschutz
Quellcode verlässt die Kundenumgebung nur mit Zustimmung; Self-Hosted-Option; kein Training auf Kundencode
Auditierbarkeit
Unveränderliches Log aller Agentenaktionen, mindestens 12 Monate aufbewahrt
Compliance
Unterstützung von Nachweisen für ISO 27001, SOC 2, NIS2 und Cyber Resilience Act
Signierung
Alle Commits des Agenten signiert und als Bot-Identität gekennzeichnet
Performance
Triage < 5 min pro Finding; Fix-PR für Dependency-Upgrades < 30 min
Skalierung
≥ 1.000 Repositories pro Mandant, parallele Verarbeitung
Verfügbarkeit
99,5 % für SaaS; Ausfall blockiert keine Kunden-Pipelines
Kostenkontrolle
Budget-Limits für LLM-Tokens und Sandbox-Laufzeit pro Repo und Monat
Guardrails, Risiken & Mitigationen
Das größte Risiko ist ein Fix, der plausibel aussieht, aber funktional oder sicherheitlich falsch ist; deshalb entscheidet nie das Modell allein, ob ein Fix gut ist.
Risiko
Auswirkung
Mitigation
Fehlerhafter Fix mit grüner Pipeline
Regression in Produktion
Default L1 (Mensch mergt); Mindest-Testabdeckung als Voraussetzung für L2/L3
Scheinfix: Scanner still, Lücke bleibt
Falsches Sicherheitsgefühl
Zweiter Scanner oder Regressionstest mit Angriffsvektor; Stichproben durch AppSec
Prompt Injection über Code, Kommentare oder Advisories
Agent führt fremde Anweisungen aus
Eingaben als Daten behandeln; keine Tools mit Außenwirkung im Fix-Agent; Diff-Policy prüft Änderungen
Supply-Chain-Angriff über empfohlenes Paket
Schadcode eingeschleust
Nur Versionen aus vertrauenswürdigen Registries; Mindestalter neuer Releases; Signaturprüfung
Überschreitung des Scope
Unerwartete Änderungen
Diff-Limit (Dateien, Zeilen); keine Änderungen an CI-Konfig, Auth-Code oder Secrets ohne Freigabe
PR-Flut
Review-Fatigue, Ablehnung durch Teams
Rate-Limits pro Repo; Bündelung; Priorisierung nach Risiko
Kompromittierung des Tools selbst
Schreibzugriff auf viele Repos
Least Privilege, Token-Rotation, Pentests, Bot-Commits nie ohne Branch-Protection
Kosten laufen aus dem Ruder
Unwirtschaftlicher Betrieb
Budget-Limits, max. Fix-Versuche, günstigere Modelle für Triage
Harte Regeln (nicht konfigurierbar)
• Kein Push auf geschützte Branches; nur PRs.
• Keine Deaktivierung von Tests, Linter-Regeln oder Security-Checks, um eine Pipeline grün zu bekommen.
• Keine Ausführung von Code aus Findings außerhalb der Sandbox.
Integrationen
Für v1 reicht die Anbindung an die verbreitetsten Git-Provider und Scanner; offene Formate wie SARIF halten den Rest erweiterbar.
Kategorie
v1
Später
Git-Provider
GitHub, GitLab
Bitbucket, Azure DevOps
SAST
Semgrep, CodeQL, SARIF-Import
SonarQube, Checkmarx
SCA / SBOM
Dependabot-/OSV-Daten, Trivy, CycloneDX
Snyk, Mend
Container / IaC
Trivy, Checkov
Grype, KICS
Secrets
Gitleaks
Secret-Manager-Anbindung (Vault, AWS SM)
Ticketing
Jira
Linear, ServiceNow
Kommunikation
Slack, Microsoft Teams
E-Mail-Digest
CI
GitHub Actions, GitLab CI
Jenkins
Roadmap & Release-Phasen
Der Rollout startet mit Dependency-Fixes, weil sie am besten validierbar sind, und erweitert Klassen und Autonomie erst nach belegter Qualität.
Phase
Zeitraum (Annahme)
Umfang
Exit-Kriterium
0 – Discovery
Monat 1
Interviews mit 5–8 Teams, Baseline-MTTR messen
Top-3-Schwachstellenklassen bestätigt
1 – MVP
Monat 2–4
SCA-Fixes inkl. API-Anpassung, GitHub, L0/L1
Merge-Rate ≥ 60 % bei 3 Pilot-Teams
2 – Beta
Monat 5–7
SAST-Fixes (Injection, XSS, Pfad-Traversal), Container-Images, GitLab, Dashboard
Regressionen < 2 %, 10 aktive Teams
3 – GA
Monat 8–10
IaC, Secrets, L2, Audit-Export, Self-Hosted
Security-Review und Pentest bestanden
4 – Erweiterung
ab Monat 11
L3, weitere Provider, Review-Iteration, Batch-PRs
Nach Kundenbedarf
Offene Fragen
[ ] Deployment-Modell: SaaS, Self-Hosted oder beides ab MVP?
[ ] Internes Tool oder Produkt für Kunden (z. B. als Beratungs- oder Managed-Service-Angebot)?
[ ] Welches LLM bzw. welche Modellstrategie (Cloud-API vs. selbst gehostet) erfüllt die Datenschutzanforderungen der Zielkunden?
[ ] Welche Sprachen/Ökosysteme zuerst (z. B. Java/Maven, JavaScript/npm, Python, C/C++ für Embedded)?
[ ] Wer haftet bei einem gemergten Fix, der eine Regression verursacht, und wie wird das vertraglich geregelt?
[ ] Ab welcher Testabdeckung ist L2 (Auto-Merge) pro Repo zulässig?
[ ] Preismodell: pro Repository, pro Entwickler oder pro behobenem Finding?
[ ] Abgrenzung zu bestehenden Angeboten (GitHub Copilot Autofix, Snyk Agent Fix, Dependabot): Was ist der klare Differenzierer?
