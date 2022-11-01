import React, { Component } from "react";

export default class MonitorRemainingUsefulLife extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: ""
    };
  }


  render() {
    return (
      <div className="container">
        <header className="jumbotron">
        잔존 수명 예지 모니터링
        </header>
      </div>
    );
  }
}
