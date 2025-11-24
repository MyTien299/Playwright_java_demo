package org.example.hrmOrange.keywords;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import io.qameta.allure.Step;
import org.example.hrmOrange.allure.LogUtils;

import java.nio.file.Paths;

public class WebKeyword {

    private final Page page;

    public WebKeyword(Page page) {
        this.page = page;
    }

    // ======================================================
    // ④ HANDLE ELEMENT ACTION
    // ======================================================

    public Page getPage() {
        return page;
    }


    @Step("Navigate to URL: {url}")
    public void navigateToUrl(String url) {
        LogUtils.info("Navigate to URL: " + url);
        page.navigate(url);
    }

    @Step("Click element: {locator}")
    public void click(String locator) {
        LogUtils.info("Click element: " + locator);
        page.locator(locator).click();
    }

    @Step("Double click element: {locator}")
    public void doubleClick(String locator) {
        LogUtils.info("Double click element: " + locator);
        page.locator(locator).dblclick();
    }

    @Step("Right click element: {locator}")
    public void rightClick(String locator) {
        LogUtils.info("Right click element: " + locator);
        page.locator(locator).click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

    @Step("Hover over element: {locator}")
    public void hover(String locator) {
        LogUtils.info("Hover over: " + locator);
        page.locator(locator).hover();
    }

    @Step("Drag and drop from {source} to {target}")
    public void dragAndDrop(String source, String target) {
        LogUtils.info("Drag and drop: " + source + " → " + target);
        page.locator(source).dragTo(page.locator(target));
    }

    @Step("Move mouse to element: {locator}")
    public void mouseMove(String locator) {
        LogUtils.info("Move mouse to: " + locator);
        BoundingBox box = page.locator(locator).boundingBox();
        if (box != null) {
            page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
        }
    }

    @Step("Scroll element into view: {locator}")
    public void scrollIntoView(String locator) {
        LogUtils.info("Scroll element into view: " + locator);
        page.locator(locator).scrollIntoViewIfNeeded();
    }

    @Step("Set value for element {locator} = {value}")
    public void setValue(String locator, String value) {
        LogUtils.info("Set value: " + locator + " = " + value);
        page.locator(locator).fill(value);
    }

    @Step("Get value from element: {locator}")
    public String getValue(String locator) {
        String value = page.locator(locator).inputValue();
        LogUtils.info("Get value of [" + locator + "] = " + value);
        return value;
    }

    @Step("Upload file: {filePath} to element: {locator}")
    public void uploadFile(String locator, String filePath) {
        LogUtils.info("Upload file: " + filePath + " → " + locator);
        page.locator(locator).setInputFiles(Paths.get(filePath));
    }

    @Step("Fill input {locator} with text: {text}")
    public void fill(String locator, String text) {
        LogUtils.info("Fill input " + locator + " with: " + text);
        page.locator(locator).fill(text);
    }

    @Step("Type text {text} into element: {locator}")
    public void type(String locator, String text) {
        LogUtils.info("Type text into " + locator + ": " + text);
        page.locator(locator).pressSequentially(text, new Locator.PressSequentiallyOptions().setDelay(100));
    }

    @Step("Get text of element: {locator}")
    public String getText(String locator) {
        String text = page.locator(locator).textContent();
        LogUtils.info("Text from element [" + locator + "] = " + text);
        return text;
    }

    @Step("Count Xpath: {xpath}")
    public int count(String xpath) {
        return page.locator(xpath).count();
    }

    public String getInputValue(String xpath) {
        return page.inputValue(xpath);
    }


    // ======================================================
    // ⑤ HANDLE VERIFY ELEMENT
    // ======================================================

    @Step("Verify element visible: {locator}")
    public boolean isVisible(String locator) {
        boolean result = page.locator(locator).isVisible();
        LogUtils.info("Verify visible: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element invisible: {locator}")
    public boolean isInvisible(String locator) {
        boolean result = !page.locator(locator).isVisible();
        LogUtils.info("Verify invisible: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element enabled: {locator}")
    public boolean isEnabled(String locator) {
        boolean result = page.locator(locator).isEnabled();
        LogUtils.info("Verify enabled: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element disabled: {locator}")
    public boolean isDisabled(String locator) {
        boolean result = !page.locator(locator).isEnabled();
        LogUtils.info("Verify disabled: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element selected: {locator}")
    public boolean isSelected(String locator) {
        boolean result = page.locator(locator).isChecked();
        LogUtils.info("Verify selected: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element unselected: {locator}")
    public boolean isUnselected(String locator) {
        boolean result = !page.locator(locator).isChecked();
        LogUtils.info("Verify unselected: " + locator + " → " + result);
        return result;
    }

    @Step("Verify element text equals: {locator} = {expectedText}")
    public boolean verifyText(String locator, String expectedText) {
        String actualText = page.locator(locator).innerText().trim();
        boolean match = actualText.equals(expectedText);
        LogUtils.info("Verify text: [" + locator + "] expected=" + expectedText + " actual=" + actualText);
        return match;
    }

    @Step("Verify element value equals: {locator} = {expectedValue}")
    public boolean verifyValue(String locator, String expectedValue) {
        String actual = page.locator(locator).inputValue();
        boolean match = actual.equals(expectedValue);
        LogUtils.info("Verify value: [" + locator + "] expected=" + expectedValue + " actual=" + actual);
        return match;
    }

    @Step("Verify element attribute {attribute} = {expectedValue}")
    public boolean verifyAttribute(String locator, String attribute, String expectedValue) {
        String actual = page.locator(locator).getAttribute(attribute);
        boolean match = expectedValue.equals(actual);
        LogUtils.info("Verify attribute: [" + locator + "] " + attribute + "=" + actual);
        return match;
    }

    // ======================================================
    // ⑥ HANDLE WAIT ELEMENT
    // ======================================================

    @Step("Wait until element visible: {locator}")
    public void waitUntilVisible(String locator, int timeout) {
        LogUtils.info("Wait until visible: " + locator);
        page.locator(locator).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
    }

    @Step("Wait until element invisible: {locator}")
    public void waitUntilInvisible(String locator, int timeout) {
        LogUtils.info("Wait until invisible: " + locator);
        page.locator(locator).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeout));
    }

    @Step("Wait until element enabled: {locator}")
    public void waitUntilEnabled(String locator, int timeout) {
        LogUtils.info("Wait until enabled: " + locator);
        page.waitForCondition(() -> page.locator(locator).isEnabled(), new Page.WaitForConditionOptions().setTimeout(timeout));
    }

    @Step("Wait until element disabled: {locator}")
    public void waitUntilDisabled(String locator, int timeout) {
        LogUtils.info("Wait until disabled: " + locator);
        page.waitForCondition(() -> !page.locator(locator).isEnabled(), new Page.WaitForConditionOptions().setTimeout(timeout));
    }

    @Step("Wait until element selected: {locator}")
    public void waitUntilSelected(String locator, int timeout) {
        LogUtils.info("Wait until selected: " + locator);
        page.waitForCondition(() -> page.locator(locator).isChecked(), new Page.WaitForConditionOptions().setTimeout(timeout));
    }

    @Step("Wait until element unselected: {locator}")
    public void waitUntilUnselected(String locator, int timeout) {
        LogUtils.info("Wait until unselected: " + locator);
        page.waitForCondition(() -> !page.locator(locator).isChecked(), new Page.WaitForConditionOptions().setTimeout(timeout));
    }

    @Step("Wait until element has text: {locator} = {expectedText}")
    public void waitUntilText(String locator, String expectedText, int timeout) {
        LogUtils.info("Wait until text matches: " + locator + " = " + expectedText);
        page.waitForCondition(() -> {
            String current = page.locator(locator).innerText();
            return current != null && current.trim().equals(expectedText);
        }, new Page.WaitForConditionOptions().setTimeout(timeout));
    }
}
