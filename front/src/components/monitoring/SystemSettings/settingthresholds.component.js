import React, { Component } from "react";

export default class SettingThresholds extends Component {
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
        임계값 설정
        </header>
      </div>
    );
  }
}
