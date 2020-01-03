// @flow

import React from 'react';
import { I18n } from 'react-redux-i18n';
import CodeMirror from 'react-codemirror';
import format from 'xml-formatter';
import pretty from 'pretty';
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/xml/xml';
import 'codemirror/mode/htmlembedded/htmlembedded';
import type { Header } from '../../types/LogApiTypes';

type Props = {
  headerList: Header,
  body: string,
  isCompleteText: boolean,
  isRequest: boolean,
  logId: string,
  method: string,
}
type State = {
  showDetails: boolean,
}

const parseJson = (val) => {
  try {
    return JSON.parse(val);
  } catch (e) {
    return val;
  }
};

const FORMAT_TYPE = {
  XML: 'xml', HTML: 'htmlembedded', JS: 'javascript', MD: 'markdown',
};


const pickFunction = (val, type) => ({
  [FORMAT_TYPE.XML]: () => format(val),
  [FORMAT_TYPE.HTML]: () => pretty(val),
  default: () => val,
  [FORMAT_TYPE.JS]: () => JSON.stringify(parseJson(val), null, 4),
  [FORMAT_TYPE.MD]: () => val,
}[type || 'default']);

class LogContainerDetails extends React.Component<Props, State> {
  getFullResponse = (isRequest: boolean) => `${window.location.origin}/api/admin/logs/${this.props.logId}/${String(isRequest)}`;

  render() {
    return (
      <div className="log-container-details">
        <div className={`title title--${this.props.method}`}>{this.props.isRequest ? I18n.t('apiLog.request') : I18n.t('apiLog.response')}</div>
        <div className="subtitle">
          <div className="api-header-title"> {I18n.t('apiLog.header')}</div>
          {this.props.headerList.header.length > 0 && this.props.headerList.header
            .map(headerInfo => (
              <div className="api-header" key={headerInfo.id}>
                <div className="header-key">{headerInfo.key}</div>
                <div> {headerInfo.value}</div>
              </div>
            ))
          }
          {this.props.headerList.header.length <= 0 &&
          <div className="empty-body"> {I18n.t('apiLog.emptyHeader')} </div>
          }
        </div>
        <div className="subtitle">
          <div className="api-header-title"> {I18n.t('apiLog.body')}</div>
          {!this.props.body &&
          <div className="empty-body"> {I18n.t('apiLog.emptyBody')} </div>
          }
          {this.props.body && !this.props.isCompleteText &&
          <a href={this.getFullResponse(this.props.isRequest)} className="download"> {I18n.t('apiLog.download')} </a>
          }
          {this.props.body && !this.props.headerList.mode &&
          <div className="bloc-body"> {this.props.body}  </div>
          }
          {this.props.body && this.props.headerList.mode &&
          <div className="bloc-body">
            {this.props.headerList.mode &&
            <CodeMirror
              value={pickFunction(this.props.body, this.props.headerList.mode)()}
              options={{
                readOnly: true,
                mode: this.props.headerList.mode,
              }}
            />
            }

          </div>
          }
        </div>
      </div>

    );
  }
}

export default LogContainerDetails;
