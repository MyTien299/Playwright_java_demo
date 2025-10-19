# GitHub Actions Workflows Documentation

This document describes all GitHub Actions workflows configured for the Playwright Java Automation Testing project.

## 📋 Overview

Three main workflows are configured to handle different testing scenarios:

1. **automation-tests.yml** - Main CI/CD pipeline for all commits and PRs
2. **nightly-tests.yml** - Scheduled nightly test execution
3. **pr-checks.yml** - Pull request validation and code quality checks

---

## 🚀 Workflow 1: automation-tests.yml

**Main CI/CD Pipeline**

### Triggers
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop` branches
- Daily schedule at 2 AM UTC
- Manual trigger via `workflow_dispatch`

### Features

#### Test Matrix
- **Operating Systems**: Ubuntu, Windows, macOS
- **Browsers**: Chromium, Firefox, WebKit
- **Optimized Matrix**: Reduces CI minutes by excluding certain OS/browser combinations

#### Jobs

**1. Test Job**
- Runs on matrix of OS and browsers
- Steps:
  - Checkout repository
  - Setup JDK 24 with Maven caching
  - Install Playwright browsers
  - Run tests with parallel execution (4 threads)
  - Generate Allure reports
  - Publish test results
  - Upload artifacts:
    - Allure results (30 days retention)
    - Allure reports (30 days retention)
    - Test logs (30 days retention)
    - Test videos (30 days retention)

**2. Report Job**
- Aggregates results from all test runs
- Merges Allure results from all matrix combinations
- Generates unified Allure report
- Deploys to GitHub Pages
- Comments on PRs with report link

**3. Notify Job**
- Sends Slack notifications (if webhook configured)
- Sends email notifications on failure (if configured)
- Includes test status, branch, and commit info

**4. Quality Gate Job**
- Validates test execution
- Ensures test results exist
- Fails if any tests failed

### Environment Variables
```
MAVEN_VERSION: 3.9.5
JAVA_VERSION: 24
ALLURE_VERSION: 2.14.0
```

### Required Secrets
- `SLACK_WEBHOOK_URL` (optional) - For Slack notifications
- `EMAIL_SERVER` (optional) - Email server address
- `EMAIL_PORT` (optional) - Email server port
- `EMAIL_USERNAME` (optional) - Email username
- `EMAIL_PASSWORD` (optional) - Email password
- `EMAIL_RECIPIENTS` (optional) - Comma-separated email list

---

## 🌙 Workflow 2: nightly-tests.yml

**Scheduled Nightly Test Execution**

### Triggers
- Daily at 11 PM UTC
- Manual trigger via `workflow_dispatch`

### Features
- Runs full test suite on Ubuntu
- All Playwright browsers installed
- 8 parallel threads for faster execution
- Generates comprehensive Allure reports
- Deploys reports to GitHub Pages under `/nightly/` directory
- Sends Slack notification with results
- 90-day artifact retention

### Use Cases
- Comprehensive testing without time constraints
- Baseline performance metrics
- Historical trend analysis
- Overnight regression testing

---

## 🔍 Workflow 3: pr-checks.yml

**Pull Request Validation & Code Quality**

### Triggers
- Pull request opened, synchronized, or reopened
- Targets `main` or `develop` branches

### Features

**1. Code Quality Analysis Job**
- Compiles project
- Runs Maven verification
- Identifies build issues early

**2. Smoke Tests Job**
- Runs only smoke tests (tagged with `@smoke`)
- Uses Chromium browser
- 4 parallel threads
- Publishes results as PR check
- Comments on PR with test results
- Uploads Allure report

**3. Dependency Check Job**
- Scans for security vulnerabilities
- Checks dependency versions
- Generates security report
- 30-day retention

---

## 📊 GitHub Pages Deployment

### Report Structure
```
https://your-username.github.io/Playwright_java_demo/
├── reports/
│   ├── 1/          (Run #1)
│   ├── 2/          (Run #2)
│   └── ...
└── nightly/
    ├── 1/          (Nightly Run #1)
    ├── 2/          (Nightly Run #2)
    └── ...
```

### Enable GitHub Pages
1. Go to repository Settings
2. Navigate to Pages
3. Set source to `Deploy from a branch`
4. Select `gh-pages` branch
5. Save

---

## 🔧 Configuration & Setup

### Prerequisites
- GitHub repository with Actions enabled
- Java 24 compatible environment
- Maven 3.9.5+
- Playwright browsers

### Step 1: Enable GitHub Pages
1. Repository Settings → Pages
2. Select `gh-pages` branch as source
3. Save

### Step 2: Configure Secrets (Optional)
For notifications, add these secrets in repository settings:

**Slack Integration:**
```
SLACK_WEBHOOK_URL: https://hooks.slack.com/services/YOUR/WEBHOOK/URL
```

**Email Integration:**
```
EMAIL_SERVER: smtp.gmail.com
EMAIL_PORT: 587
EMAIL_USERNAME: your-email@gmail.com
EMAIL_PASSWORD: your-app-password
EMAIL_RECIPIENTS: recipient1@example.com,recipient2@example.com
```

### Step 3: Configure Variables (Optional)
Add these variables in repository settings:

```
SLACK_WEBHOOK_URL: (set if using Slack)
```

---

## 📈 Monitoring & Troubleshooting

### View Workflow Runs
1. Go to repository
2. Click "Actions" tab
3. Select workflow to view runs
4. Click run to see detailed logs

### Common Issues

**Issue: Tests timeout on GitHub Actions**
- Solution: Reduce parallel threads in workflow
- Change `threadCount` from 4 to 2

**Issue: Playwright browsers not installing**
- Solution: Ensure sufficient disk space
- Check runner logs for specific errors

**Issue: Allure report not generating**
- Solution: Verify `pom.xml` has allure-maven plugin
- Check test results exist in `target/allure-results/`

**Issue: GitHub Pages not updating**
- Solution: Verify `gh-pages` branch exists
- Check Pages settings point to correct branch

### View Test Reports
1. After workflow completes, click "Deployments" tab
2. Select "github-pages" deployment
3. Click "View deployment"
4. Navigate to `reports/[run-number]/`

---

## 🎯 Best Practices

### 1. Test Organization
- Use `@smoke` tag for quick PR validation tests
- Use `@regression` tag for full test suite
- Use `@critical` tag for must-pass tests

### 2. Artifact Management
- Retention days set appropriately (7-90 days)
- Large artifacts (videos) retained for 30 days
- Logs retained for debugging

### 3. Parallel Execution
- Ubuntu: 4-8 threads (sufficient resources)
- Windows/macOS: 2-4 threads (limited resources)
- Adjust based on runner performance

### 4. Notifications
- Slack for quick visibility
- Email for critical failures
- PR comments for immediate feedback

### 5. Report Analysis
- Review Allure reports for trends
- Track flaky tests
- Monitor performance metrics

---

## 📝 Customization Guide

### Add New Workflow Trigger
Edit the `on:` section in any workflow file:

```yaml
on:
  push:
    branches: [ main, develop, staging ]  # Add new branch
  pull_request:
    branches: [ main, develop, staging ]
```

### Modify Test Matrix
Edit the `strategy.matrix` section:

```yaml
matrix:
  os: [ ubuntu-latest, windows-latest, macos-latest ]
  browser: [ chromium, firefox, webkit ]
  java-version: [ 21, 24 ]  # Add Java version
```

### Add New Job
Copy an existing job and modify:

```yaml
new-job:
  name: New Job Name
  runs-on: ubuntu-latest
  steps:
    - uses: actions/checkout@v4
    # Add your steps
```

### Adjust Parallel Threads
Find the test execution step and modify:

```yaml
run: |
  mvn clean test \
    -DthreadCount=8  # Change this value
```

---

## 🔐 Security Considerations

1. **Secrets Management**
   - Never commit secrets to repository
   - Use GitHub Secrets for sensitive data
   - Rotate credentials regularly

2. **Dependency Security**
   - Dependency Check job scans for vulnerabilities
   - Review reports regularly
   - Update dependencies promptly

3. **Access Control**
   - Limit who can trigger workflows
   - Review workflow permissions
   - Audit workflow changes

---

## 📞 Support & Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Allure Report Documentation](https://docs.qameta.io/allure/)
- [Playwright Documentation](https://playwright.dev/java/)
- [TestNG Documentation](https://testng.org/)

---

## 📋 Workflow Checklist

- [ ] GitHub Pages enabled
- [ ] Secrets configured (if using notifications)
- [ ] `pom.xml` has correct Java version (24)
- [ ] `testng.xml` configured with test suites
- [ ] Allure plugin configured in `pom.xml`
- [ ] Test tags (@smoke, @regression) applied
- [ ] First workflow run successful
- [ ] Reports accessible via GitHub Pages
- [ ] Notifications working (if configured)

---

**Last Updated:** 2025-10-19
**Version:** 1.0
