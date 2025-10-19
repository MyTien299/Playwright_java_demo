# GitHub Actions Workflows

Complete GitHub Actions CI/CD setup for Playwright Java Automation Testing project.

## 📋 Quick Navigation

- **[SETUP_GUIDE.md](./SETUP_GUIDE.md)** - Step-by-step setup instructions
- **[WORKFLOWS.md](./WORKFLOWS.md)** - Detailed workflow documentation

## 🚀 What's Included

### 4 Production-Ready Workflows

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| **automation-tests.yml** | Push, PR, Schedule, Manual | Main CI/CD pipeline with multi-OS/browser testing |
| **nightly-tests.yml** | Daily 11 PM UTC, Manual | Comprehensive nightly test execution |
| **pr-checks.yml** | Pull requests | Code quality & smoke tests on PRs |
| **release.yml** | Tag push, Manual | Release validation & deployment |

## ⚡ Quick Start

### 1️⃣ Enable GitHub Pages (1 minute)
```
Settings → Pages → Deploy from a branch → gh-pages
```

### 2️⃣ Push Workflows (1 minute)
```bash
git add .github/workflows/
git commit -m "Add GitHub Actions workflows"
git push origin main
```

### 3️⃣ View Results (1 minute)
```
Actions tab → Select workflow → View run
```

✅ **Done!** Workflows are now active.

---

## 📊 Workflow Features

### ✨ automation-tests.yml
- **Multi-OS Testing**: Ubuntu, Windows, macOS
- **Multi-Browser Testing**: Chromium, Firefox, WebKit
- **Parallel Execution**: 4-8 threads per run
- **Allure Reports**: Automatic report generation & deployment
- **Artifacts**: Logs, videos, test results (30-day retention)
- **Notifications**: Slack & Email (optional)
- **Quality Gates**: Automatic validation

### 🌙 nightly-tests.yml
- **Full Test Suite**: All tests, all browsers
- **Scheduled**: Daily at 11 PM UTC
- **High Parallelization**: 8 threads
- **Extended Retention**: 90 days
- **Slack Notifications**: Automatic updates

### 🔍 pr-checks.yml
- **Code Quality**: Maven verification
- **Smoke Tests**: Quick validation on PRs
- **Security Scan**: Dependency vulnerability check
- **PR Comments**: Automatic test result comments
- **Fast Feedback**: ~5 minute execution

### 🚀 release.yml
- **Release Validation**: Full test suite before release
- **GitHub Release**: Automatic release creation
- **Release Notes**: Auto-generated from commits
- **Report Deployment**: Separate release reports
- **Notifications**: Release announcements

---

## 🔧 Configuration

### Required (Already Done)
- ✅ Java 24 (configured in pom.xml)
- ✅ Maven 3.9.5 (auto-installed)
- ✅ Playwright (auto-installed)
- ✅ TestNG (in pom.xml)

### Optional Setup

**Slack Notifications:**
1. Create Slack webhook
2. Add `SLACK_WEBHOOK_URL` secret
3. Done!

**Email Notifications:**
1. Get Gmail app password
2. Add 5 email secrets
3. Done!

See [SETUP_GUIDE.md](./SETUP_GUIDE.md) for detailed instructions.

---

## 📈 Monitoring

### View Workflow Runs
```
Repository → Actions tab → Select workflow
```

### Access Test Reports
```
Deployments → github-pages → View deployment
Navigate to: reports/[run-number]/
```

### Download Artifacts
```
Workflow run → Artifacts section → Download
```

---

## 🎯 Common Tasks

### Run Tests Manually
1. Go to **Actions** tab
2. Select workflow
3. Click **Run workflow**
4. Select options
5. Click **Run workflow**

### View Test Report
1. Go to **Deployments** tab
2. Select **github-pages**
3. Click **View deployment**
4. Navigate to report

### Check Test Logs
1. Go to **Actions** tab
2. Click workflow run
3. Click job name
4. Expand steps to view logs

### Create Release
```bash
git tag v1.0.0
git push origin v1.0.0
```
Workflow automatically:
- Runs full test suite
- Creates GitHub release
- Generates release notes
- Deploys test report

---

## 🐛 Troubleshooting

### Workflows Not Running
- Check `.github/workflows/` directory exists
- Verify YAML syntax is correct
- Check branch name matches trigger
- Go to Actions tab for error messages

### Tests Failing on CI but Passing Locally
- Check Java version (should be 24)
- Verify Playwright browsers installed
- Check environment variables
- Review workflow logs for details

### Reports Not Generating
- Verify `pom.xml` has allure-maven plugin
- Check test results in `target/allure-results/`
- Ensure GitHub Pages is enabled
- Wait 1-2 minutes for deployment

### GitHub Pages Not Updating
- Hard refresh browser (Cmd+Shift+R)
- Check `gh-pages` branch exists
- Verify Pages settings
- Wait for GitHub to rebuild

See [SETUP_GUIDE.md](./SETUP_GUIDE.md#-troubleshooting) for more solutions.

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| **SETUP_GUIDE.md** | Step-by-step setup & configuration |
| **WORKFLOWS.md** | Detailed workflow documentation |
| **README.md** | This file - quick reference |

---

## 🔐 Security

- ✅ Secrets stored securely in GitHub
- ✅ No credentials in repository
- ✅ Dependency security scanning
- ✅ Workflow audit logs available

---

## 📊 Workflow Statistics

| Metric | Value |
|--------|-------|
| **Total Workflows** | 4 |
| **Total Jobs** | 12+ |
| **Test Matrix Combinations** | 6 (OS × Browser) |
| **Artifact Retention** | 7-90 days |
| **Report Deployment** | GitHub Pages |
| **Notification Channels** | Slack, Email |

---

## 🎓 Learning Resources

- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [Allure Reports](https://docs.qameta.io/allure/)
- [Playwright Java](https://playwright.dev/java/)
- [TestNG](https://testng.org/)

---

## ✅ Verification Checklist

- [ ] Workflows created in `.github/workflows/`
- [ ] GitHub Pages enabled
- [ ] First workflow run successful
- [ ] Allure report deployed
- [ ] Can access report via GitHub Pages
- [ ] Notifications configured (optional)
- [ ] All tests passing

---

## 📞 Support

**Need Help?**
1. Check [SETUP_GUIDE.md](./SETUP_GUIDE.md) for detailed instructions
2. Review [WORKFLOWS.md](./WORKFLOWS.md) for workflow details
3. Check workflow logs in Actions tab
4. Review troubleshooting section above

---

## 🎉 You're All Set!

Your Playwright Java project now has:
- ✅ Automated testing on every push
- ✅ Pull request validation
- ✅ Nightly regression testing
- ✅ Allure test reports
- ✅ Multi-OS & multi-browser testing
- ✅ Notifications & alerts
- ✅ Release management

**Next Steps:**
1. Push workflows to repository
2. Enable GitHub Pages
3. Configure notifications (optional)
4. View first test report

Happy testing! 🚀

---

**Last Updated:** 2025-10-19
**Version:** 1.0
**Maintained by:** GitHub Actions
