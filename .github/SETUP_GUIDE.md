# GitHub Actions Setup Guide

Complete step-by-step guide to set up GitHub Actions for your Playwright Java automation testing project.

## 🎯 Quick Start (5 minutes)

### Step 1: Verify Repository Structure
Ensure your repository has these files:
```
.github/
├── workflows/
│   ├── automation-tests.yml
│   ├── nightly-tests.yml
│   └── pr-checks.yml
├── WORKFLOWS.md
└── SETUP_GUIDE.md
```

### Step 2: Enable GitHub Pages
1. Go to your repository on GitHub
2. Click **Settings** (top right)
3. Scroll down to **Pages** (left sidebar)
4. Under "Build and deployment":
   - Source: Select **Deploy from a branch**
   - Branch: Select **gh-pages**
   - Folder: Select **/ (root)**
5. Click **Save**

### Step 3: Push Changes
```bash
git add .github/workflows/
git commit -m "Add GitHub Actions workflows"
git push origin main
```

### Step 4: Verify Workflows
1. Go to your repository
2. Click **Actions** tab
3. You should see workflows running

✅ **Done!** Your basic setup is complete.

---

## 🔔 Optional: Configure Notifications

### Slack Notifications

#### Step 1: Create Slack Webhook
1. Go to [Slack API](https://api.slack.com/apps)
2. Click **Create New App** → **From scratch**
3. Name: `GitHub Actions`
4. Select your workspace
5. Go to **Incoming Webhooks**
6. Click **Add New Webhook to Workspace**
7. Select channel (e.g., #automation-tests)
8. Click **Allow**
9. Copy the **Webhook URL**

#### Step 2: Add Secret to GitHub
1. Go to repository **Settings**
2. Click **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Name: `SLACK_WEBHOOK_URL`
5. Value: Paste the webhook URL
6. Click **Add secret**

#### Step 3: Verify
- Push a commit to trigger workflow
- Check Slack channel for notification

### Email Notifications

#### Step 1: Get Gmail App Password
1. Enable 2-factor authentication on Gmail
2. Go to [Google Account Security](https://myaccount.google.com/security)
3. Search for "App passwords"
4. Select **Mail** and **Windows Computer**
5. Copy the generated password

#### Step 2: Add Secrets to GitHub
1. Go to repository **Settings**
2. Click **Secrets and variables** → **Actions**
3. Add these secrets:

| Name | Value |
|------|-------|
| `EMAIL_SERVER` | `smtp.gmail.com` |
| `EMAIL_PORT` | `587` |
| `EMAIL_USERNAME` | `your-email@gmail.com` |
| `EMAIL_PASSWORD` | App password from Step 1 |
| `EMAIL_RECIPIENTS` | `recipient1@example.com,recipient2@example.com` |

#### Step 3: Verify
- Trigger a test failure
- Check email for notification

---

## 🛠️ Advanced Configuration

### Customize Test Execution

#### Modify Thread Count
Edit `.github/workflows/automation-tests.yml`:
```yaml
- name: Run Tests on ${{ matrix.browser }}
  run: |
    mvn clean test \
      -Dbrowser=${{ matrix.browser }} \
      -Dheadless=true \
      -Dparallel=true \
      -DthreadCount=8  # Change this value
```

#### Add New Browser
Edit matrix in `.github/workflows/automation-tests.yml`:
```yaml
strategy:
  matrix:
    browser: [ chromium, firefox, webkit, edge ]  # Add edge
```

#### Change Schedule
Edit cron expression in `.github/workflows/nightly-tests.yml`:
```yaml
schedule:
  - cron: '0 23 * * *'  # 11 PM UTC daily
  # Other examples:
  # '0 2 * * *'     - 2 AM UTC daily
  # '0 0 * * 0'     - Midnight UTC every Sunday
  # '0 */6 * * *'   - Every 6 hours
```

### Add Custom Test Groups

#### Step 1: Tag Tests in Code
```java
@Test(groups = {"smoke"})
public void testLoginSmoke() {
    // Test code
}

@Test(groups = {"regression"})
public void testLoginRegression() {
    // Test code
}
```

#### Step 2: Create Workflow for Specific Group
Create `.github/workflows/smoke-tests.yml`:
```yaml
name: Smoke Tests Only

on:
  pull_request:
    branches: [ main, develop ]

jobs:
  smoke:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '24'
          distribution: 'temurin'
          cache: maven
      - run: mvn clean test -Dgroups=smoke
```

### Conditional Workflow Execution

#### Run Only on Specific File Changes
```yaml
on:
  push:
    paths:
      - 'src/test/**'
      - 'pom.xml'
      - '.github/workflows/**'
```

#### Run Only on Pull Request Labels
```yaml
on:
  pull_request:
    types: [labeled]

jobs:
  test:
    if: contains(github.event.pull_request.labels.*.name, 'run-tests')
```

---

## 📊 Accessing Test Reports

### View Allure Reports

#### From GitHub Pages
1. Go to your repository
2. Click **Deployments** (right sidebar)
3. Select **github-pages**
4. Click **View deployment**
5. Navigate to `reports/[run-number]/`

#### Direct URL
```
https://your-username.github.io/Playwright_java_demo/reports/[run-number]/
```

### View Workflow Logs

1. Go to **Actions** tab
2. Click on workflow run
3. Click on job name
4. Expand steps to see logs

### Download Artifacts

1. Go to workflow run
2. Scroll to **Artifacts** section
3. Click artifact to download

---

## 🐛 Troubleshooting

### Workflow Not Triggering

**Problem:** Workflow doesn't run on push
- **Solution:** Verify workflow file is in `.github/workflows/` directory
- **Solution:** Check branch name matches trigger condition
- **Solution:** Ensure YAML syntax is correct

**Check:** Go to Actions tab, look for error messages

### Tests Failing on GitHub but Passing Locally

**Problem:** Tests pass locally but fail on GitHub Actions
- **Solution:** Check Java version matches (should be 24)
- **Solution:** Verify Playwright browsers installed correctly
- **Solution:** Check environment variables set correctly
- **Solution:** Review logs for specific error messages

**Debug:** Add verbose logging:
```yaml
- name: Run Tests
  run: |
    mvn clean test \
      -X \  # Enable debug logging
      -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

### Allure Report Not Generating

**Problem:** Report not created or deployed
- **Solution:** Verify `pom.xml` has allure-maven plugin
- **Solution:** Check test results exist: `target/allure-results/`
- **Solution:** Verify GitHub Pages enabled and configured

**Check:** Look for allure report generation step in logs

### GitHub Pages Not Updating

**Problem:** Report deployed but not visible
- **Solution:** Wait 1-2 minutes for GitHub Pages to rebuild
- **Solution:** Hard refresh browser (Cmd+Shift+R on Mac)
- **Solution:** Check `gh-pages` branch exists
- **Solution:** Verify Pages settings point to correct branch

**Check:** Go to Settings → Pages to verify configuration

### Out of Memory Errors

**Problem:** Tests fail with OutOfMemory error
- **Solution:** Reduce parallel threads: `-DthreadCount=2`
- **Solution:** Increase Maven memory:
```yaml
- name: Run Tests
  env:
    MAVEN_OPTS: "-Xmx2048m"
  run: mvn clean test
```

### Timeout Errors

**Problem:** Tests timeout on GitHub Actions
- **Solution:** Increase timeout in workflow:
```yaml
- name: Run Tests
  timeout-minutes: 60
  run: mvn clean test
```
- **Solution:** Reduce number of parallel tests

---

## 🔐 Security Best Practices

### Protect Secrets
- ✅ Use GitHub Secrets for sensitive data
- ❌ Never commit secrets to repository
- ✅ Rotate credentials regularly
- ✅ Use minimal required permissions

### Workflow Security
- ✅ Review workflow changes before merging
- ✅ Use pinned action versions (e.g., `@v4` not `@latest`)
- ✅ Limit workflow permissions
- ✅ Audit workflow execution logs

### Dependency Security
- ✅ Run dependency check job
- ✅ Review security reports
- ✅ Update dependencies promptly
- ✅ Monitor for vulnerabilities

---

## 📈 Performance Optimization

### Reduce Build Time

**Use Maven Cache:**
```yaml
- uses: actions/setup-java@v4
  with:
    cache: maven  # Caches Maven dependencies
```

**Parallel Execution:**
```yaml
run: mvn clean test -DthreadCount=8
```

**Selective Testing:**
```yaml
run: mvn clean test -Dgroups=smoke  # Run only smoke tests
```

### Reduce Artifact Size

**Exclude Unnecessary Artifacts:**
```yaml
- uses: actions/upload-artifact@v4
  with:
    name: test-results
    path: target/surefire-reports/
    # Exclude large files
    exclude: |
      **/*.webm
      **/*.mp4
```

**Set Retention Period:**
```yaml
retention-days: 7  # Delete after 7 days
```

---

## 📚 Additional Resources

### GitHub Actions Documentation
- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [Workflow Syntax](https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions)
- [Expressions](https://docs.github.com/en/actions/learn-github-actions/expressions)

### Allure Report
- [Allure Documentation](https://docs.qameta.io/allure/)
- [Allure Maven Plugin](https://github.com/allure-framework/allure-maven)

### Playwright
- [Playwright Java Docs](https://playwright.dev/java/)
- [Playwright CLI](https://playwright.dev/java/docs/cli)

### TestNG
- [TestNG Documentation](https://testng.org/)
- [TestNG Annotations](https://testng.org/doc/documentation-main.html#annotations)

---

## ✅ Verification Checklist

- [ ] Workflows created in `.github/workflows/`
- [ ] GitHub Pages enabled
- [ ] First workflow run completed successfully
- [ ] Allure report generated and deployed
- [ ] Can access report via GitHub Pages
- [ ] Slack notifications configured (optional)
- [ ] Email notifications configured (optional)
- [ ] Test tags applied (@smoke, @regression)
- [ ] Maven cache working
- [ ] All tests passing

---

## 🆘 Getting Help

If you encounter issues:

1. **Check Workflow Logs**
   - Go to Actions → Select workflow run → View logs

2. **Review Documentation**
   - See `WORKFLOWS.md` for detailed workflow information

3. **Common Issues**
   - See Troubleshooting section above

4. **GitHub Community**
   - [GitHub Actions Community](https://github.community/)
   - [Stack Overflow](https://stackoverflow.com/questions/tagged/github-actions)

---

**Last Updated:** 2025-10-19
**Version:** 1.0
