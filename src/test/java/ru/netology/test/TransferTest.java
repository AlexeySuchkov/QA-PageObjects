package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    @Test
    void transferFirstToSecond() {
        int transferAmount = 1000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int firstCardBalanceBefore = DashboardPage.getCurrentFirstCardBalance();
        int secondCardBalanceBefore = DashboardPage.getCurrentSecondCardBalance();
        val transferPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.transferCard(cardInfo, transferAmount);
        int firstCardBalanceAfterTransfer = DataHelper.secondCardBalanceAfterTransfer(secondCardBalanceBefore, transferAmount);
        int secondCardBalanceAfterTransfer = DataHelper.firstCardBalanceAfterTransfer(firstCardBalanceBefore, transferAmount);
        int firstCardBalanceAfter = DashboardPage.getCurrentSecondCardBalance();
        int secondCardBalanceAfter = DashboardPage.getCurrentFirstCardBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardBalanceAfter);
        assertEquals(secondCardBalanceAfterTransfer, secondCardBalanceAfter);
    }

    @Test
    void transferSecondToFirst() {
        int transferAmount = 1500;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int firstCardBalanceBefore = DashboardPage.getCurrentFirstCardBalance();
        int secondCardBalanceBefore = DashboardPage.getCurrentSecondCardBalance();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.transferCard(cardInfo, transferAmount);
        int firstCardBalanceAfterTransfer = DataHelper.secondCardBalanceAfterTransfer(firstCardBalanceBefore, transferAmount);
        int secondCardBalanceAfterTransfer = DataHelper.firstCardBalanceAfterTransfer(secondCardBalanceBefore, transferAmount);
        int firstCardBalanceAfter = DashboardPage.getCurrentFirstCardBalance();
        int secondCardBalanceAfter = DashboardPage.getCurrentSecondCardBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardBalanceAfter);
        assertEquals(secondCardBalanceAfterTransfer, secondCardBalanceAfter);
    }

    @Test
    void transferOverCurrentBalance() {
        int transferAmount = 11000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int firstCardBalanceBefore = DashboardPage.getCurrentFirstCardBalance();
        int secondCardBalanceBefore = DashboardPage.getCurrentSecondCardBalance();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.transferCard(cardInfo, transferAmount);
        int firstCardBalanceAfterTransfer = DataHelper.secondCardBalanceAfterTransfer(firstCardBalanceBefore, transferAmount);
        int secondCardBalanceAfterTransfer = DataHelper.firstCardBalanceAfterTransfer(secondCardBalanceBefore, transferAmount);
        assertEquals(firstCardBalanceBefore, firstCardBalanceAfterTransfer);
        assertEquals(secondCardBalanceBefore, secondCardBalanceAfterTransfer);
    }
}