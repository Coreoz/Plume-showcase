// @flow



import { RestClientAuthenticated } from '../network';

const logApi = {
  fetchAll: () =>
    new RestClientAuthenticated('/admin/logs', 'GET').execute(),
};

export default logApi;
