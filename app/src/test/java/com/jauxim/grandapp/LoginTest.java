package com.jauxim.grandapp;

import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLoginPresenter;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
    @Mock
    private ActivityLoginView view;
    @Mock
    private Service service;
    private ActivityLoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new ActivityLoginPresenter(service, view);
    }

    @Test
    public void shouldShowUserError() throws Exception {
        presenter.login("", "1234");
        verify(view).showUserError(R.string.user_error);
    }

    @Test
    public void shouldShowUserPasError() throws Exception {
        presenter.login("", "");
        verify(view).showUserError(R.string.user_error);
        verify(view).showPassError(R.string.pass_error);
    }

    @Test
    public void shouldShowPassError() throws Exception {
        presenter.login("raul", "");
        verify(view).showPassError(R.string.pass_error);
    }

    @Test
    public void shouldShowLoginSuccess() throws Exception {
        presenter.login("raul", "1234");
        verify(view).startMainActivity();
    }

    @Test
    public void shouldShowLoginError() throws Exception {
        presenter.login("raul", "1111");
        verify(view).showLoginError(R.string.login_error);
    }
}