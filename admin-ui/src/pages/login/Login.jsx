// @flow

import React from 'react';
import { Redirect, withRouter } from 'react-router-dom';
import { I18n, Translate } from 'react-redux-i18n';
import NotificationsSystem from 'reapop';
import { connect } from 'react-redux';
import theme from '../../components/modaleNotification/theme/index';
import { toJsonError } from '../../network/handleError';
import { notifyError } from '../../network/notification';
import { HttpClient } from '../../network/network';
import type { Credentials } from '../../types/loginTypes';
import { IS_CONNECTED } from '../../state/login/loginReducer';
import { store } from '../../network/reduce';
import { Field, Form } from 'react-final-form';
import { isUserLoggedOn, login } from '../../services/sessionService';

type Props = {
  handleSubmit: Function,
  isConnected: boolean,
  dispatch: Function,
}

class Login extends React.Component<Props> {

  onSubmit = (credentials: Credentials) => {
    login(credentials);
  };

  render() {
    return (
      <div>
        <NotificationsSystem theme={theme} />
        {isUserLoggedOn() ? (
          <Redirect to="/home" />
        ) : (
          <div id="login-container">
            <h1>Plume BO</h1>
            <h2><Translate value="login.TITLE" /></h2>
            <Form
              onSubmit={this.onSubmit}
              render={({ handleSubmit }) => (
                <form onSubmit={handleSubmit}>
                  <div className="field-group">
                    <Field
                      name="userName"
                      component="input"
                      placeholder={I18n.t('login.IDENTIFIER')}
                      type="text"
                      className="form-control"
                    />
                    <Field
                      name="password"
                      component="input"
                      placeholder={I18n.t('login.PASSWORD')}
                      type="password"
                      className="form-control"
                    />
                  </div>
                  <button type="submit" className="btn btn-primary"><Translate value="login.SIGNIN" />
                  </button>
                </form>
              )}
            />
          </div>
        )}
      </div>
    );
  }
}

export default withRouter(connect(state => ({
  isConnected: state.login.isConnected,
}))(Login));
