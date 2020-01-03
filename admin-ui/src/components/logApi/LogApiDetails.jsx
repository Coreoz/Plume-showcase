// @flow

import React from 'react';
import { I18n } from 'react-redux-i18n';
import moment from 'moment';
import type { Log } from '../../types/LogApiTypes';
import LogContainerDetails from './LogApiContainerDetails';

type Props = {
  log: Log,
}
type State = {
  showDetails: boolean,
}

class LogApiDetails extends React.Component<Props, State> {
  state = {
    showDetails: false,
  };

  handleDetailOpen = () => {
    this.setState({ showDetails: !this.state.showDetails });
  };

  render() {
    const date = moment(this.props.log.date).format('DD-MM-YYYY HH:mm');
    const isError = this.props.log.statusCode > '400';
    const isEmpty = !this.props.log.bodyRequest && !this.props.log.bodyResponse &&
      this.props.log.headerRequest.header.length === 0 &&
      this.props.log.headerResponse.header.length === 0;
    return (
      <div className={`log-api-details log-api-details--${this.props.log.method}`}>
        <div className="container-log">
          <div
            className="container-items"
            onClick={() => {
              this.handleDetailOpen();
            }}
          >
            <div className="bloc bloc--date">
              <div className="value value--date">{date}</div>
            </div>
            <div className={`bloc bloc--${this.props.log.method}`}>
              <div className="value value--white">{this.props.log.method}</div>
            </div>
            <div className="bloc bloc--api">
              <div className="value value--date">{this.props.log.api}</div>
            </div>
            <div className="bloc bloc--url">
              <div className="value ">{this.props.log.url}</div>
            </div>
            <div className={`bloc ${isError ? 'bloc--error' : 'bloc--ok'}`}>
              <div className="value value--white">{this.props.log.statusCode}</div>
            </div>
            <i className={`fa fa-chevron-${this.state.showDetails ? 'up' : 'down'} fa-lg`} />
          </div>
          {this.state.showDetails && isEmpty &&
          <div className="empty-container">{I18n.t('apiLog.empty')}</div>
          }
          {this.state.showDetails &&
          <div>
            <LogContainerDetails
              headerList={this.props.log.headerRequest}
              body={this.props.log.bodyRequest}
              isCompleteText={this.props.log.isCompleteTextRequest}
              isRequest
              logId={this.props.log.id}
              method={this.props.log.method}
            />
            <LogContainerDetails
              headerList={this.props.log.headerResponse}
              body={this.props.log.bodyResponse}
              isCompleteText={this.props.log.isCompleteTextResponse}
              isRequest={false}
              logId={this.props.log.id}
              method={this.props.log.method}
            />
          </div>
          }
        </div>
      </div>
    );
  }
}

export default LogApiDetails;
