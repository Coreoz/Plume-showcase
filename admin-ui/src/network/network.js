// @flow

import handleError, {
  jsonResponseCatcher,
  rawResponseCatcher,
  toJsonError,
  unwrap,
} from './handleError';
import { baseApiUrl } from '../const';
import { currentUserSessionToken } from '../services/sessionService';

export function getAuthorization() {
  return `Bearer ${currentUserSessionToken()}`;
}

const stringify = (body) => {
  if (typeof body === 'string') {
    return body;
  }
  return JSON.stringify(body);
};

export class HttpClient {
  apiExtensionUrl: string;
  headersValue: Object;
  methodvalue: string;
  bodyValue: ?string;

  constructor(apiExtensionUrl: string, method: string = 'GET') {
    this.apiExtensionUrl = apiExtensionUrl;
    this.headersValue = {};
    this.methodvalue = method;
    this.bodyValue = null;
  }

  headers(headers: Object) {
    Object.assign(this.headersValue, headers);
    return this;
  }

  body(body: string) {
    this.bodyValue = body;
    return this;
  }

  jsonBody(objectBody: Object) {
    this.headers({ 'Content-Type': 'application/json' });
    this.body(stringify(objectBody));
    return this;
  }

  execute() {
    // $FlowFixMe
    return fetch(baseApiUrl + this.apiExtensionUrl, {
      headers: this.headersValue,
      method: this.methodvalue,
      body: this.bodyValue,
    })
      .catch(rawResponseCatcher)
      .then(handleError);
  }
}

export class RestClient extends HttpClient {
  constructor(apiExtensionUrl: string, method: string = 'GET') {
    super(apiExtensionUrl, method);
  }

  execute() {
    return super
      .execute()
      .then(jsonResponseCatcher)
      .catch(toJsonError);
  }
}

export class RestClientAuthenticated extends RestClient {
  constructor(apiExtensionUrl: string, method: string = 'GET') {
    super(apiExtensionUrl, method);
    this.headers({ Authorization: getAuthorization() });
  }
}
